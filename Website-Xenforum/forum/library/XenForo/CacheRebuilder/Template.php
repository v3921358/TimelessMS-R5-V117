<?php

/**
 * Cache rebuilder for templates.
 *
 * @package XenForo_CacheRebuild
 */
class XenForo_CacheRebuilder_Template extends XenForo_CacheRebuilder_Abstract
{
	/**
	 * Gets rebuild message.
	 */
	public function getRebuildMessage()
	{
		return new XenForo_Phrase('templates');
	}

	/**
	 * Rebuilds the data.
	 *
	 * @see XenForo_CacheRebuilder_Abstract::rebuild()
	 */
	public function rebuild($position = 0, array &$options = array(), &$detailedMessage = '')
	{
		$options = array_merge(array(
			'startStyle' => 0,
			'startTemplate' => 0,
			'maxExecution' => XenForo_Application::getConfig()->rebuildMaxExecution,
			'mapped' => false
		), $options);

		/* @var $templateModel XenForo_Model_Template */
		$templateModel = XenForo_Model::create('XenForo_Model_Template');

		if ($options['startStyle'] == 0 && $options['startTemplate'] == 0 && !$options['mapped'])
		{
			$s = microtime(true);
			$templateModel->insertTemplateMapForStyles($templateModel->buildTemplateMapForStyleTree(0), true);
			$options['mapped'] = true;

			$maxExec = $options['maxExecution'] - ($s - microtime(true));
			if ($maxExec < 1)
			{
				$detailedMessage = str_repeat(' . ', $position + 1);
				return $position + 1;
			}
		}
		else
		{
			$maxExec = $options['maxExecution'];
		}

		$result = $templateModel->compileAllTemplates(
			$maxExec, $options['startStyle'], $options['startTemplate']
		);
		if ($result === true)
		{
			return true;
		}
		else
		{
			$options['startStyle'] = $result[0];
			$options['startTemplate'] = $result[1];

			$detailedMessage = str_repeat(' . ', $position + 1);

			return $position + 1; // continue again
		}
	}
}