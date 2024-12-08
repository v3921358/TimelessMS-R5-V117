<?php

/**
 * Cache rebuilder for admin templates.
 *
 * @package XenForo_CacheRebuild
 */
class XenForo_CacheRebuilder_AdminTemplate extends XenForo_CacheRebuilder_Abstract
{
	/**
	 * Gets rebuild message.
	 */
	public function getRebuildMessage()
	{
		return new XenForo_Phrase('admin_templates');
	}

	/**
	 * Rebuilds the data.
	 *
	 * @see XenForo_CacheRebuilder_Abstract::rebuild()
	 */
	public function rebuild($position = 0, array &$options = array(), &$detailedMessage = '')
	{
		$options = array_merge(array(
			'startTemplate' => 0,
			'maxExecution' => XenForo_Application::getConfig()->rebuildMaxExecution
		), $options);

		/* @var $templateModel XenForo_Model_AdminTemplate */
		$templateModel = XenForo_Model::create('XenForo_Model_AdminTemplate');

		$priority = array('PAGE_CONTAINER_SIMPLE', 'page_container_js', 'tools_cache_rebuild');
		$result = $templateModel->compileAllParsedAdminTemplates($options['maxExecution'], $options['startTemplate'], $priority);

		if ($result === true)
		{
			return true;
		}
		else
		{
			$options['startTemplate'] = $result;

			$detailedMessage = str_repeat(' . ', $position + 1);

			return $position + 1; // continue again
		}
	}
}