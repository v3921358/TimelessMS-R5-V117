<?php

/**
 * View handling for displaying a list of all prefixes available to a node
 *
 * @package XenForo_Nodes
 */
class XenForo_ViewAdmin_Forum_Prefixes extends XenForo_ViewAdmin_Base
{
	public function renderJson()
	{
		$prefixGroups = array();

		foreach ($this->_params['prefixGroups'] AS $prefixGroupId => $prefixGroup)
		{
			$prefixGroups[$prefixGroupId] = array();

			if ($prefixGroupId)
			{
				$prefixGroups[$prefixGroupId]['title'] = $prefixGroup['title'];
			}

			foreach ($prefixGroup['prefixes'] AS $prefixId => $prefix)
			{
				$prefixGroups[$prefixGroupId]['prefixes'][$prefixId] = array(
					'title' => new XenForo_Phrase('thread_prefix_' . $prefixId),
					'css' => $prefix['css_class']
				);
			}
		}

		return XenForo_ViewRenderer_Json::jsonEncodeForOutput(array(
			'node_id' => $this->_params['nodeId'],
			'prefixGroups' => $prefixGroups
		));
	}
}