<?php

// CLI only
if (PHP_SAPI != 'cli')
{
	die('This script may only be run at the command line.');
}

if (!class_exists('XenForo_Autoloader', false))
{
	$fileDir = realpath(dirname(__FILE__) . '/../../');
	chdir($fileDir);

	require_once($fileDir . '/library/XenForo/Autoloader.php');
	XenForo_Autoloader::getInstance()->setupAutoloader($fileDir . '/library');

	XenForo_Application::initialize($fileDir . '/library', $fileDir);
}

$dependencies = new XenForo_Dependencies_Admin();
$dependencies->preLoadData();

$startTime = microtime(true);

new XenForo_Cli_Importer($argv);

class XenForo_Cli_Importer
{
	protected $_processId = 0;

	protected $_numProcesses = 0;

	/**
	 * @var XenForo_ImportSession
	 */
	protected $_session = null;

	/**
	 * @var Zend_Db_Adapter_Abstract
	 */
	protected $_sourceDb = null;

	public function __construct($args)
	{
		$this->_session = new XenForo_ImportSession();

		if (!$this->_session->isRunning())
		{
			die('Please begin an import using the web interface before using this tool.' . PHP_EOL);
		}

		if (!$this->_numProcesses = $this->_session->getExtraData('cli', 'numProcesses'))
		{
			die('The import session does not include a numProcesses extra data record.' . PHP_EOL);
		}

		$config = $this->_session->getConfig();
		$this->_sourceDb = Zend_Db::factory('mysqli', $config['db']);


		if (isset($args[1]) && strpos($args[1], '--process=') !== false)
		{
			list($processId) = sscanf($args[1], '--process=%d');
			$this->_runImport($processId);
		}
		else
		{
			$this->_startProcesses($args[0]);
		}
	}

	protected function _startProcesses($importScript)
	{
		$this->_session->startStep('threads');

		/*$db = XenForo_Application::getDb();
		$db->query('ALTER TABLE xf_thread ENGINE MYISAM');
		$db->query('ALTER TABLE xf_post ENGINE MYISAM');
		$db->query('ALTER TABLE xf_thread DISABLE KEYS');
		$db->query('ALTER TABLE xf_post DISABLE KEYS');*/

		$phpBinary = $this->_session->getExtraData('cli', 'phpBinary');

		$processes = array();

		for ($i = 0; $i < $this->_numProcesses; $i++)
		{
			if ($this->_session->getExtraData('cli', 'taskset') && $numCores = $this->_session->getExtraData('cli', 'numCores'))
			{
				$affinity = 'taskset -c ' . ($i % $numCores);
			}
			else
			{
				$affinity = '';
			}

			$cmd = "nohup $affinity $phpBinary $importScript '--process=$i' >> /tmp/import.log 2>&1 & echo $!";

			//echo $cmd . PHP_EOL;

			if ($processId = trim(shell_exec($cmd)))
			{
				$processIds[] = $processId;
			}
			else
			{
				die('Unable to launch processes with the specified command.' . PHP_EOL);
			}
		}

		$threadCountStore = 0;

		while ($threadCount = $this->_sourceDb->fetchOne('SELECT COUNT(threadid) FROM xf_import_thread'))
		{
			if (!$threadCountStore)
			{
				$threadCountStore = $threadCount * 100;
			}

			echo $this->_dateStamp('H:i:s') . ' 	Approximately ' . number_format($threadCount * 100) . ' threads remaining to import.' . PHP_EOL;
			sleep(15);

			// just keep the connection alive
			XenForo_Application::getDb()->query('SELECT 1 + 1');
		}

		echo $this->_dateStamp('H:i:s') . '	Waiting for processes to terminate...';

		while ($this->_processesRunning($processIds))
		{
			echo '.';
			sleep(5);
		}

		/*$db->query('ALTER TABLE xf_thread ENGINE INNODB');
		$db->query('ALTER TABLE xf_post ENGINE INNODB');*/

		$this->_session->incrementStepImportTotal($threadCountStore, 'threads');
		$this->_session->completeStep('threads');
		$this->_session->save();

		echo PHP_EOL . $this->_dateStamp('H:i:s') . '	All done. Imported ' . number_format($threadCountStore) . ' threads.' . PHP_EOL . PHP_EOL;
	}

	protected function _processesRunning(array $processIds)
	{
		static $processRegex = null;

		if (is_null($processRegex))
		{
			$processRegex = implode('|', $processIds);
		}

		exec('ps', $processState);

		if (preg_match('/(^|[^\d])(' . $processRegex . ')\s+/sU', implode(' ', $processState)))
		{
			return true;
		}

		return false;
	}

	protected function _runImport($processId)
	{
		echo $this->_dateStamp() . "	#$processId Starting" . PHP_EOL;

		while ($threadId = $this->_sourceDb->fetchOne('SELECT MIN(threadid) FROM xf_import_thread'))
		{
			if ($affected = $this->_sourceDb->delete('xf_import_thread', 'threadid = ' . $this->_sourceDb->quote($threadId)))
			{
				$failed = 0;

				do
				{
					if (function_exists('gc_collect_cycles'))
					{
						gc_collect_cycles();
					}

					try
					{
						$start = microtime(true);

						$importer = $this->_getImportModel()->getImporter($this->_session->getImporterKey());

						$results = $importer->runStep(null, $this->_session, 'threads', $threadId, array(
							'postLimit' => 0,
							'uniqueChecks' => false,
							'threadWatchDeferred' => true,
						));

						$failed = 0;


						echo $this->_dateStamp('H:i:s') . " #$processId @ " . number_format($threadId)
							. " in " . number_format(microtime(true) - $start, 2) . " seconds." . PHP_EOL;
					}
					catch (Exception $e)
					{
						XenForo_Db::rollbackAll();
						sleep(5);

						if (strpos($e->getMessage(), 'Deadlock found') !== false)
						{
							// this doesn't increment the failed counter, as it should resolve itself eventually
							echo PHP_EOL . $this->_dateStamp('H:i:s') . " #$processId @ DEADLOCK while importing starting from $threadId. Retrying..." . PHP_EOL;
						}
						else if (++$failed == 5)
						{
							echo PHP_EOL . $this->_dateStamp('H:i:s') . " #$processId @ FAILED to import 100 threads starting from thread ID $threadId. Skipping." . PHP_EOL;
							echo "\t" . $e->getMessage() . PHP_EOL;
							echo "\t" . $e->getTraceAsString() . PHP_EOL . PHP_EOL;
						}
						else
						{
							echo PHP_EOL . $this->_dateStamp('H:i:s') . " #$processId @ FAILED to import 100 threads starting from thread ID $threadId. Retrying ($failed)..." . PHP_EOL;
							echo "\t" . $e->getMessage() . PHP_EOL;
						}
					}

					unset($importer);
				}
				while ($failed > 0 && $failed < 5);
			}
		}

		echo $this->_dateStamp() . "	#$processId Completed" . PHP_EOL;
	}

	protected function _dateStamp()
	{
		return date('H:i:s');
	}

	/**
	 * @return XenForo_Model_Import
	 */
	protected function _getImportModel()
	{
		return XenForo_Model::create('XenForo_Model_Import');
	}
}
