<?php

class XenForo_ControllerPublic_Warning extends XenForo_ControllerPublic_Abstract
{
	public function actionIndex()
	{
		$warningId = $this->_input->filterSingle('warning_id', XenForo_Input::UINT);
		$warning = $this->_getWarningOrError($warningId);

		if (!$this->_getUserModel()->canViewWarnings())
		{
			return $this->responseNoPermission();
		}

		$user = $this->_getUserModel()->getUserById($warning['user_id']);
		if (!$user)
		{
			return $this->responseError(new XenForo_Phrase('user_who_received_this_warning_no_longer_exists'));
		}

		$handler = $this->_getWarningModel()->getWarningHandler($warning['content_type']);
		$contentUrl = '';
		$canViewContent = false;
		if ($handler)
		{
			$content = $handler->getContent($warning['content_id']);
			if ($content)
			{
				$contentUrl = $handler->getContentUrl($content);
				$canViewContent = $handler->canView($content);
			}
		}

		$viewParams = array(
			'warning' => $warning,
			'user' => $user,
			'contentUrl' => $contentUrl,
			'canViewContent' => $canViewContent,
			'canDeleteWarning' => $this->_getWarningModel()->canDeleteWarning($warning),
			'redirect' => $this->getDynamicRedirect()
		);
		return $this->responseView('XenForo_ViewPublic_Warning_Info', 'warning_info', $viewParams);
	}

	/**
	 * Deletes a warning.
	 *
	 * @return XenForo_ControllerResponse_Abstract
	 */
	public function actionDelete()
	{
		$warningId = $this->_input->filterSingle('warning_id', XenForo_Input::UINT);
		$warning = $this->_getWarningOrError($warningId);

		if (!$this->_getUserModel()->canViewWarnings() || !$this->_getWarningModel()->canDeleteWarning($warning))
		{
			return $this->responseNoPermission();
		}

		if ($this->isConfirmedPost())
		{
			return $this->_deleteData(
				'XenForo_DataWriter_Warning', 'warning_id',
				$this->getDynamicRedirect()
			);
		}
		else
		{
			return $this->responseReroute(__CLASS__, 'index');
		}
	}

	/**
	 * Gets the specified warning or throws an error.
	 *
	 * @param integer $id
	 *
	 * @return array
	 */
	protected function _getWarningOrError($id)
	{
		return $this->_getWarningModel()->prepareWarning($this->getRecordOrError(
			$id, $this->_getWarningModel(), 'getWarningById',
			'requested_warning_not_found'
		));
	}

	/**
	 * @return XenForo_Model_Warning
	 */
	protected function _getWarningModel()
	{
		return $this->getModelFromCache('XenForo_Model_Warning');
	}

	/**
	 * @return XenForo_Model_User
	 */
	protected function _getUserModel()
	{
		return $this->getModelFromCache('XenForo_Model_User');
	}
}