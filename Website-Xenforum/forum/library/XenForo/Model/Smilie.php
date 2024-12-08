<?php

/**
 * Model for smilies.
 *
 * @package XenForo_Smilie
 */
class XenForo_Model_Smilie extends XenForo_Model
{
	/**
	 * Gets the named smilie by ID.
	 *
	 * @param integer $smilieId
	 *
	 * @return array|false
	 */
	public function getSmilieById($smilieId)
	{
		return $this->_getDb()->fetchRow('
			SELECT *
			FROM xf_smilie
			WHERE smilie_id = ?
		', $smilieId);
	}

	/**
	 * Gets all smilies that match the given smilie text. This text may
	 * be an array of text, or a string with each match on separate lines.
	 *
	 * @param string|array $matchText
	 *
	 * @return array [text] => smilie that matched
	 */
	public function getSmiliesByText($matchText)
	{
		if (!is_array($matchText))
		{
			$matchText = preg_split('/\r?\n/', $matchText, -1, PREG_SPLIT_NO_EMPTY);
		}

		if (!$matchText)
		{
			return array();
		}

		$matches = array();
		foreach ($this->getAllSmilies() AS $smilie)
		{
			$smilieText = preg_split('/\r?\n/', $smilie['smilie_text'], -1, PREG_SPLIT_NO_EMPTY);

			$textMatch = array_intersect($matchText, $smilieText);
			foreach ($textMatch AS $text)
			{
				$matches[$text] = $smilie;
			}
		}

		return $matches;
	}

	/**
	 * Gets all smilies ordered by their title.
	 *
	 * @return array Format: [smilie id] => info
	 */
	public function getAllSmilies()
	{
		return $this->fetchAllKeyed('
			SELECT *
			FROM xf_smilie
			ORDER BY title
		', 'smilie_id');
	}

	/**
	 * Get the smilie data needed for the smilie cache.
	 *
	 * @return array Format: [smilie id] => info
	 */
	public function getAllSmiliesForCache()
	{
		$smilies = $this->fetchAllKeyed('
			SELECT smilie_id, title, smilie_text, image_url,
				sprite_mode, sprite_params
			FROM xf_smilie
			ORDER BY smilie_id
		', 'smilie_id');

		$smilies = $this->prepareSmilies($smilies);

		foreach ($smilies AS &$smilie)
		{
			$smilie['smilieText'] = preg_split('/\r?\n/', $smilie['smilie_text'], -1, PREG_SPLIT_NO_EMPTY);

			if (!$smilie['sprite_mode'] || !$smilie['sprite_params'])
			{
				unset($smilie['sprite_params']);
			}

			unset($smilie['sprite_mode'], $smilie['smilie_text']);
		}

		return $smilies;
	}

	/**
	 * Rebuilds the smilie cache.
	 *
	 * @return array Smilie cache
	 */
	public function rebuildSmilieCache()
	{
		$smilies = $this->getAllSmiliesForCache();
		$this->_getDataRegistryModel()->set('smilies', $smilies);

		$this->rebuildSpriteCss();

		return $smilies;
	}

	public function rebuildSpriteCss()
	{
		$spriteCss = array();

		foreach ($this->getAllSmilies() AS $smilieId => $smilie)
		{
			$smilie = $this->prepareSmilie($smilie);

			if ($smilie['sprite_mode'] && !empty($smilie['sprite_params']))
			{
				$spriteCss[$smilieId] = array('sprite_css' => sprintf('width: %1$dpx; height: %2$dpx; background: url(\'%3$s\') no-repeat %4$dpx %5$dpx;',
					(int)$smilie['sprite_params']['w'],
					(int)$smilie['sprite_params']['h'],
					htmlspecialchars($smilie['image_url']),
					(int)$smilie['sprite_params']['x'],
					(int)$smilie['sprite_params']['y']
				));
			}
		}

		$this->_getDataRegistryModel()->set('smilieSprites', $spriteCss);

		// need to force css updates
		$this->getModelFromCache('XenForo_Model_Style')->updateAllStylesLastModifiedDate();

		return $spriteCss;
	}

	/**
	 * Adds a 'smilieTextArray' array to each smilie in the provided array,
	 * where each array item contains one possible smilie_text search string
	 * as its key. Value is false (no rotation), 90 or 270
	 *
	 * @param array $smilies
	 *
	 * @return array
	 */
	public function prepareSmiliesForList(array $smilies)
	{
		$smilies = $this->prepareSmilies($smilies);

		foreach ($smilies AS &$smilie)
		{
			 $smilieTextArray = preg_split('/\r?\n/', $smilie['smilie_text'], -1, PREG_SPLIT_NO_EMPTY);

			 $out = array();

			 foreach ($smilieTextArray AS $smilieText)
			 {
			 	$out[$smilieText] = false;

			 	if (strlen($smilieText) > 4 || preg_match('#^:.*:$#', $smilieText))
			 	{
			 		continue;
			 	}

			 	if (!preg_match('#[:;8]#', $smilieText))
			 	{
			 		continue;
			 	}

			 	if (preg_match('#(:|;)$#', $smilieText))
			 	{
			 		$out[$smilieText] = 270;
			 	}
			 	else
			 	{
			 		$out[$smilieText] = 90;
			 	}
			 }

			 $smilie['smilieTextArray'] = $out;
		}

		return $smilies;
	}

	/**
	 * Prepares a number of smilies for use
	 *
	 * @param array $smilies
	 *
	 * @return array
	 */
	public function prepareSmilies(array $smilies)
	{
		return array_map(array($this, 'prepareSmilie'), $smilies);
	}

	/**
	 * Prepares a single smilie for use
	 *
	 * @param array $smilie
	 * @param boolean $getSmilieText Prepare a 'smilieText' key that contains the first item in smilie_text
	 *
	 * @return array
	 */
	public function prepareSmilie(array $smilie, $getSmilieText = false)
	{
		if (is_string($smilie['sprite_params']))
		{
			$smilie['sprite_params'] = unserialize($smilie['sprite_params']);
		}

		if ($getSmilieText)
		{
			$smilieText = preg_split('/\r?\n/', $smilie['smilie_text'], -1, PREG_SPLIT_NO_EMPTY);
			$smilie['smilieText'] = reset($smilieText);
		}

		return $smilie;
	}

	/**
	 * Gets the default values for smilie sprite params
	 *
	 * @return array
	 */
	public function getDefaultSmilieSpriteParams()
	{
		return array(
			'w' => 18,
			'h' => 18,
			'x' => 0,
			'y' => 0
		);
	}

	public function getSmiliesForAdminQuickSearch($searchText)
	{
		$quotedText = XenForo_Db::quoteLike($searchText, 'lr', $this->_getDb());

		return $this->fetchAllKeyed('
			SELECT *
			FROM xf_smilie
			WHERE title LIKE ' . $quotedText . '
				OR smilie_text LIKE ' . $quotedText
		, 'smilie_id');
	}
}