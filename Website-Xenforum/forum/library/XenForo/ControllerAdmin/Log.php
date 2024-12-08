<?php

class XenForo_ControllerAdmin_Log extends XenForo_ControllerAdmin_Abstract
{
	protected function _preDispatch($action)
	{
		$this->assertAdminPermission('viewLogs');
	}

	public function actionServerError()
	{
		$id = $this->_input->filterSingle('id', XenForo_Input::UINT);
		if ($id)
		{
			$entry = $this->_getLogModel()->getServerErrorLogById($id);
			if (!$entry)
			{
				return $this->responseError(new XenForo_Phrase('requested_server_error_log_entry_not_found'), 404);
			}

			$entry['requestState'] = unserialize($entry['request_state']);

			$viewParams = array(
				'entry' => $entry
			);
			return $this->responseView('XenForo_ViewAdmin_Log_ServerErrorView', 'log_server_error_view', $viewParams);
		}
		else
		{
			$page = $this->_input->filterSingle('page', XenForo_Input::UINT);
			$perPage = 20;

			$viewParams = array(
				'entries' => $this->_getLogModel()->getServerErrorLogs(array(
					'page' => $page,
					'perPage' => $perPage
				)),

				'page' => $page,
				'perPage' => $perPage,
				'total' => $this->_getLogModel()->countServerErrors()
			);
			return $this->responseView('XenForo_ViewAdmin_Log_ServerError', 'log_server_error', $viewParams);
		}
	}

	public function actionServerErrorDelete()
	{
		$id = $this->_input->filterSingle('id', XenForo_Input::UINT);
		$entry = $this->_getLogModel()->getServerErrorLogById($id);
		if (!$entry)
		{
			return $this->responseError(new XenForo_Phrase('requested_server_error_log_entry_not_found'), 404);
		}

		if ($this->isConfirmedPost())
		{
			$this->_getLogModel()->deleteServerErrorLog($id);

			return $this->responseRedirect(
				XenForo_ControllerResponse_Redirect::SUCCESS,
				XenForo_Link::buildAdminLink('logs/server-error')
			);
		}
		else
		{
			$viewParams = array(
				'entry' => $entry
			);
			return $this->responseView('XenForo_ViewAdmin_Log_ServerErrorDelete', 'log_server_error_delete', $viewParams);
		}
	}

	public function actionServerErrorClear()
	{
		if ($this->isConfirmedPost())
		{
			$this->_getLogModel()->clearServerErrorLog();

			return $this->responseRedirect(
				XenForo_ControllerResponse_Redirect::SUCCESS,
				XenForo_Link::buildAdminLink('logs/server-error')
			);
		}
		else
		{
			$viewParams = array();
			return $this->responseView('XenForo_ViewAdmin_Log_ServerErrorDelete', 'log_server_error_clear', $viewParams);
		}
	}

	public function actionAdmin()
	{
		$this->assertSuperAdmin();

		$logModel = $this->_getLogModel();

		$id = $this->_input->filterSingle('id', XenForo_Input::UINT);
		if ($id)
		{
			$entry = $logModel->getAdminLogById($id);
			if (!$entry)
			{
				return $this->responseError(new XenForo_Phrase('requested_log_entry_not_found'), 404);
			}

			$entry['requestData'] = json_decode($entry['request_data'], true);

			$viewParams = array(
				'entry' => $logModel->prepareAdminLogEntry($entry)
			);
			return $this->responseView('XenForo_ViewAdmin_Log_AdminView', 'log_admin_view', $viewParams);
		}
		else
		{
			$userId = $this->_input->filterSingle('user_id', XenForo_Input::UINT);
			$page = $this->_input->filterSingle('page', XenForo_Input::UINT);
			$perPage = 20;

			$pageParams = array();
			if ($userId)
			{
				$pageParams['user_id'] = $userId;
			}

			$entries = $logModel->getAdminLogEntries($userId, array('page' => $page, 'perPage' => $perPage));

			$viewParams = array(
				'entries' => $logModel->prepareAdminLogEntries($entries),
				'total' => $logModel->countAdminLogEntries($userId),
				'page' => $page,
				'perPage' => $perPage,
				'pageParams' => $pageParams,

				'logUsers' => $logModel->getUsersWithAdminLogs(),
				'userId' => $userId
			);

			return $this->responseView('XenForo_ViewAdmin_Log_Admin', 'log_admin', $viewParams);
		}
	}

	public function actionModerator()
	{
		$logModel = $this->_getLogModel();

		$id = $this->_input->filterSingle('id', XenForo_Input::UINT);
		if ($id)
		{
			$entry = $logModel->getModeratorLogById($id);
			if (!$entry)
			{
				return $this->responseError(new XenForo_Phrase('requested_log_entry_not_found'), 404);
			}

			$viewParams = array(
				'entry' => $logModel->prepareModeratorLogEntry($entry)
			);
			return $this->responseView('XenForo_ViewAdmin_Log_ModeratorView', 'log_moderator_view', $viewParams);
		}
		else
		{
			$userId = $this->_input->filterSingle('user_id', XenForo_Input::UINT);
			$page = $this->_input->filterSingle('page', XenForo_Input::UINT);
			$perPage = 20;

			$pageParams = array();
			if ($userId)
			{
				$pageParams['user_id'] = $userId;
			}

			$entries = $logModel->getModeratorLogEntries($userId, array('page' => $page, 'perPage' => $perPage));

			$viewParams = array(
				'entries' => $logModel->prepareModeratorLogEntries($entries),
				'total' => $logModel->countModeratorLogEntries($userId),
				'page' => $page,
				'perPage' => $perPage,
				'pageParams' => $pageParams,

				'logUsers' => $logModel->getUsersWithModeratorLogs(),
				'userId' => $userId
			);

			return $this->responseView('XenForo_ViewAdmin_Log_Moderator', 'log_moderator', $viewParams);
		}
	}

	/**
	 * @return XenForo_Model_Log
	 */
	protected function _getLogModel()
	{
		return $this->getModelFromCache('XenForo_Model_Log');
	}
}