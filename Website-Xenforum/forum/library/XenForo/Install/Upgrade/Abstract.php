<?php

abstract class XenForo_Install_Upgrade_Abstract
{
	protected $_globalModPermCache = null;
	protected $_contentModPermCache = null;

	abstract public function getVersionName();

	public function executeUpgradeQuery($sql, array $bind = array())
	{
		try
		{
			return $this->_getDb()->query($sql, $bind);
		}
		catch (Zend_Db_Exception $e)
		{
			return false;
		}
	}

	protected function _getGlobalModPermissions()
	{
		if ($this->_globalModPermCache === null)
		{
			$moderators = $this->_getDb()->fetchPairs('
				SELECT user_id, moderator_permissions
				FROM xf_moderator
			');
			foreach ($moderators AS $userId => &$permissions)
			{
				$permissions = unserialize($permissions);
			}

			$this->_globalModPermCache = $moderators;
		}

		return $this->_globalModPermCache;
	}

	protected function _getContentModPermissions()
	{
		if ($this->_contentModPermCache === null)
		{
			$moderators = $this->_getDb()->fetchPairs('
				SELECT moderator_id, moderator_permissions
				FROM xf_moderator_content
			');
			foreach ($moderators AS $moderatorId => &$permissions)
			{
				$permissions = unserialize($permissions);
			}

			$this->_contentModPermCache = $moderators;
		}

		return $this->_contentModPermCache;
	}

	protected function _updateGlobalModPermissions($userId, array $permissions)
	{
		$this->_globalModPermCache[$userId] = $permissions;

		$this->_getDb()->query('
			UPDATE xf_moderator
			SET moderator_permissions = ?
			WHERE user_id = ?
		', array(serialize($permissions), $userId));
	}

	protected function _updateContentModPermissions($moderatorId, array $permissions)
	{
		$this->_contentModPermCache[$moderatorId] = $permissions;

		$this->_getDb()->query('
			UPDATE xf_moderator_content
			SET moderator_permissions = ?
			WHERE moderator_id = ?
		', array(serialize($permissions), $moderatorId));
	}

	public function applyGlobalPermission($applyGroupId, $applyPermissionId, $dependGroupId, $dependPermissionId, $checkModerator = true)
	{
		$db = $this->_getDb();

		XenForo_Db::beginTransaction($db);

		$db->query("
			INSERT IGNORE INTO xf_permission_entry
				(user_group_id, user_id, permission_group_id, permission_id, permission_value, permission_value_int)
			SELECT user_group_id, user_id, ?, ?, 'allow', 0
			FROM xf_permission_entry
			WHERE permission_group_id = ?
				AND permission_id = ?
				AND permission_value = 'allow'
		", array ($applyGroupId, $applyPermissionId, $dependGroupId, $dependPermissionId));

		if ($checkModerator)
		{
			$moderators = $this->_getGlobalModPermissions();
			foreach ($moderators AS $userId => $permissions)
			{
				if (!empty($permissions[$dependGroupId][$dependPermissionId]))
				{
					$permissions[$applyGroupId][$applyPermissionId] = '1'; // string 1 is stored by the code
					$this->_updateGlobalModPermissions($userId, $permissions);
				}
			}
		}

		XenForo_Db::commit($db);
	}

	public function applyContentPermission($applyGroupId, $applyPermissionId, $dependGroupId, $dependPermissionId, $checkModerator = true)
	{
		$db = $this->_getDb();

		XenForo_Db::beginTransaction($db);

		$db->query("
			INSERT IGNORE INTO xf_permission_entry_content
				(content_type, content_id, user_group_id, user_id,
				permission_group_id, permission_id, permission_value, permission_value_int)
			SELECT content_type, content_id, user_group_id, user_id, ?, ?, 'content_allow', 0
			FROM xf_permission_entry_content
			WHERE permission_group_id = ?
				AND permission_id = ?
				AND permission_value = 'content_allow'
		", array ($applyGroupId, $applyPermissionId, $dependGroupId, $dependPermissionId));

		if ($checkModerator)
		{
			$moderators = $this->_getContentModPermissions();
			foreach ($moderators AS $moderatorId => $permissions)
			{
				if (!empty($permissions[$dependGroupId][$dependPermissionId]))
				{
					$permissions[$applyGroupId][$applyPermissionId] = '1'; // string 1 is stored by the code
					$this->_updateContentModPermissions($moderatorId, $permissions);
				}
			}
		}

		XenForo_Db::commit($db);
	}

	/**
	 * @return Zend_Db_Adapter_Abstract
	 */
	protected function _getDb()
	{
		return XenForo_Application::getDb();
	}
}