<?php

class XenForo_Model_Stats extends XenForo_Model
{
	protected $_statsHandlerCache = array();

	protected $_statsTypes = array();

	protected $_statsTypeHandlerLookupMap = array();

	public function getStatsTypePhrases(array $statsTypes)
	{
		$phrases = array();

		foreach ($this->_getStatsContentTypeHandlerNames() AS $statsType => $statsHandlerName)
		{
			$statsHandler = $this->_getStatsHandler($statsHandlerName);

			$phrases = array_merge($phrases, $statsHandler->getStatsTypes());
		}

		return $phrases;
	}

	public function getStatsData($start, $end, array $statsTypes)
	{
		$db = $this->_getDb();

		$data = $db->fetchAll('
			SELECT stats_date, stats_type, counter
			FROM xf_stats_daily
			WHERE stats_date BETWEEN ? AND ?
			AND stats_type IN(' . $db->quote($statsTypes) . ')
		', array($start, $end));

		$dataGrouped = array();

		$handlerNames = $this->getStatsTypeHandlerLookupMap();

		foreach ($data AS $stat)
		{
			$handler = $this->_getStatsHandler($handlerNames[$stat['stats_type']]);

			$dataGrouped[$stat['stats_type']][$stat['stats_date']] = $handler->getCounterForDisplay($stat['stats_type'], $stat['counter']);
		}

		return $dataGrouped;
	}

	public function prepareGraphData(array $data)
	{
		$keys = array_keys($data);

		$date = reset($keys);
		$maxDate = end($keys);

		$output = array();

		while ($date <= $maxDate)
		{
			$value = (isset($data[$date]) ? $data[$date] : 0);

			$output[] = array($date * 1000, $value);

			$date += 86400;
		}

		return $output;
	}

	/**
	 * Fetch all stats handler content types
	 *
	 * @return array
	 */
	protected function _getStatsContentTypeHandlerNames()
	{
		return $this->getContentTypesWithField('stats_handler_class');
	}

	/**
	 * Fetch all stats types
	 *
	 * @return array
	 */
	public function getStatsTypes()
	{
		if (empty($this->_statsTypes))
		{
			$this->_statsTypes = array();

			foreach ($this->_getStatsContentTypeHandlerNames() AS $contentType => $statsHandlerName)
			{
				$this->_statsTypes[$contentType] = $this->_getStatsHandler($statsHandlerName)->getStatsTypes();
			}
		}

		return $this->_statsTypes;
	}

	/**
	 * Fetch an array allowing a stats type to be mapped back to its stats handler
	 *
	 * @return array
	 */
	public function getStatsTypeHandlerLookupMap()
	{
		if (empty($this->_statsTypeHandlerLookupMap))
		{
			$this->_statsTypeHandlerLookupMap = array();

			foreach ($this->_getStatsContentTypeHandlerNames() AS $contentType => $statsHandlerName)
			{
				foreach ($this->_getStatsHandler($statsHandlerName)->getStatsTypes() AS $statsType => $_null)
				{
					$this->_statsTypeHandlerLookupMap[$statsType] = $statsHandlerName;
				}
			}
		}

		return $this->_statsTypeHandlerLookupMap;
	}

	/**
	 * Fetch options for a list of stats types to be used with <xen:options source="{this}" />
	 *
	 * @param array $selected Selected options
	 *
	 * @return array
	 */
	public function getStatsTypeOptions(array $selected = array())
	{
		$statsTypeOptions = array();

		foreach ($this->getStatsTypes() AS $contentType => $statsTypes)
		{
			foreach ($statsTypes AS $statsType => $statsTypePhrase)
			{
				$statsTypeOptions[$contentType][] = array(
					'name' => "statsTypes[]",
					'value' => $statsType,
					'label' => $statsTypePhrase,
					'selected' => in_array($statsType, $selected)
				);
			}
		}

		return $statsTypeOptions;
	}

	/**
	 * Fetch a stats handler
	 *
	 * @param string $statsHandlerName
	 *
	 * @return XenForo_StatsHandler_Abstract
	 */
	protected function _getStatsHandler($statsHandlerName)
	{
		if (!isset($this->_statsHandlerCache[$statsHandlerName]))
		{
			$this->_statsHandlerCache[$statsHandlerName] = new $statsHandlerName;
		}

		return $this->_statsHandlerCache[$statsHandlerName];
	}

	/**
	 * Deletes ALL data from the xf_stats_daily table. Use with care!
	 */
	public function deleteStats()
	{
		$this->_getDb()->delete('xf_stats_daily');
	}

	public function buildStatsData($start, $end)
	{
		$db = $this->_getDb();

		foreach ($this->_getStatsContentTypeHandlerNames() AS $contentType => $handlerClassName)
		{
			$handlerClass = $this->_getStatsHandler($handlerClassName);

			$data = $handlerClass->getData($start, $end);

			foreach ($data AS $statsType => $records)
			{
				$statsType = $db->quote($statsType);

				foreach ($records AS $date => $counter)
				{
					$date = $db->quote($date);
					$counter = $db->quote($counter);

					$db->query("
						INSERT INTO xf_stats_daily
							(stats_date, stats_type, counter)
						VALUES
							($date, $statsType, $counter)
						ON DUPLICATE KEY UPDATE
							counter = $counter
					");
				}
			}
		}
	}
}