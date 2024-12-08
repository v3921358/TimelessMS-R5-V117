<?php

class XenForo_ControllerAdmin_Stats extends XenForo_ControllerAdmin_Abstract
{
	protected function _preDispatch($action)
	{
		$this->assertAdminPermission('viewStatistics');
	}

	public function actionIndex()
	{
		return $this->responseReroute(__CLASS__, 'daily');
	}

	public function actionDaily()
	{
		if (!$start = $this->_input->filterSingle('start', XenForo_Input::DATE_TIME))
		{
			$start = XenForo_Application::$time - 28 * 86400;
		}

		if (!$end = $this->_input->filterSingle('end', XenForo_Input::DATE_TIME))
		{
			$end = XenForo_Application::$time;
		}

		if (!$statsTypes = $this->_input->filterSingle('statsTypes', XenForo_Input::ARRAY_SIMPLE))
		{
			$statsTypes = array('post', 'post_like');
		}

		$statsModel = $this->_getStatsModel();

		$plots = $statsModel->getStatsData($start, $end, $statsTypes);
		$dateMap = array();

		foreach ($plots AS $type => &$plot)
		{
			foreach ($plot AS $ts => $count)
			{
				$dateMap[$type][$ts] = XenForo_Locale::date($ts, 'absolute');
			}
			$plot = json_encode($statsModel->prepareGraphData($plot));
		}

		$viewParams = array(
			'plots' => $plots,
			'dateMap' => $dateMap,
			'start' => $start,
			'end' => $end,
			'endDisplay' => ($end >= XenForo_Application::$time ? 0 : $end),
			'statsTypeOptions' => $statsModel->getStatsTypeOptions($statsTypes),
			'statsTypePhrases' => $statsModel->getStatsTypePhrases($statsTypes),
			'datePresets' => XenForo_Helper_Date::getDatePresets(),
		);

		return $this->responseView('XenForo_ViewAdmin_Stats_Daily', 'stats_daily', $viewParams);
	}

	/**
	 * @return XenForo_Model_Stats
	 */
	protected function _getStatsModel()
	{
		return $this->getModelFromCache('XenForo_Model_Stats');
	}
}