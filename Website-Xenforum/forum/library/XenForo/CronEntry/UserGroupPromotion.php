<?php

/**
 * Cron entry for executing user group promotions.
 */
class XenForo_CronEntry_UserGroupPromotion
{
	/**
	 * Runs the cron-based check for new promotions that users should be awarded.
	 */
	public static function runPromotions()
	{
		/* @var $promotionModel XenForo_Model_UserGroupPromotion */
		$promotionModel = XenForo_Model::create('XenForo_Model_UserGroupPromotion');
		$promotions = $promotionModel->getPromotions(array(
			'active' => 1
		));
		if (!$promotions)
		{
			return;
		}

		/* @var $userModel XenForo_Model_User */
		$userModel = XenForo_Model::create('XenForo_Model_User');

		$users = $userModel->getUsers(array(
			'user_state' => 'valid',
			'is_banned' => 0,
			'last_activity' => array('>', XenForo_Application::$time - 86400 * 3)
		), array(
			'join' => XenForo_Model_User::FETCH_USER_FULL
		));

		$userPromotionStates = $promotionModel->getPromotionStatesByUserIds(array_keys($users));

		foreach ($users AS $userId => $user)
		{
			foreach ($promotions AS $promotionId => $promotion)
			{
				if (isset($userPromotionStates[$userId][$promotionId]))
				{
					$skip = false;
					switch ($userPromotionStates[$userId][$promotionId])
					{
						case 'manual': // has it, don't take it away
						case 'disabled': // never give it
							$skip = true;
					}
					if ($skip)
					{
						continue;
					}
					$hasPromotion = true;
				}
				else
				{
					$hasPromotion = false;
				}

				if (XenForo_Helper_Criteria::userMatchesCriteria($promotion['user_criteria'], false, $user))
				{
					if (!$hasPromotion)
					{
						$promotionModel->promoteUser($promotion, $userId);
					}
				}
				else if ($hasPromotion)
				{
					$promotionModel->demoteUser($promotion, $userId);
				}
			}
		}
	}
}