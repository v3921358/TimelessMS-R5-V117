<?php

/**
 * Model for BB code related behaviors.
 *
 * @package XenForo_BbCode
 */
class XenForo_Model_BbCode extends XenForo_Model
{
	/**
	 * Gets the specified BB code media site.
	 *
	 * @param string $id
	 *
	 * @return array|false
	 */
	public function getBbCodeMediaSiteById($id)
	{
		return $this->_getDb()->fetchRow('
			SELECT *
			FROM xf_bb_code_media_site
			WHERE media_site_id = ?
		', $id);
	}

	/**
	 * Gets all BB code media sites, ordered by title.
	 *
	 * @return array [site id] => info
	 */
	public function getAllBbCodeMediaSites()
	{
		return $this->getBbCodeMediaSites();
	}

	/**
	 * Gets all BB code media sites belonging to a particular add-on
	 *
	 * @param string $addOnId
	 *
	 * @return array [site id] => info
	 */
	public function getBbCodeMediaSitesByAddOnId($addOnId)
	{
		return $this->getBbCodeMediaSites(array('addOnId' => $addOnId));
	}

	/**
	 * Gets BB code media sites matching the specified conditions

	 * @param array $conditions
	 * @param array $fetchOptions
	 *
	 * @return array [site id] => info
	 */
	public function getBbCodeMediaSites(array $conditions = array(), array $fetchOptions = array())
	{
		$whereClause = $this->prepareOptionConditions($conditions, $fetchOptions);

		$orderClause = $this->prepareOptionOrderOptions($fetchOptions, 'xf_bb_code_media_site.site_title');
		$limitOptions = $this->prepareLimitFetchOptions($fetchOptions);


		return $this->fetchAllKeyed($this->limitQueryResults('
			SELECT *
			FROM xf_bb_code_media_site
			WHERE ' . $whereClause . '
			' . $orderClause . '
		', $limitOptions['limit'], $limitOptions['offset']
		), 'media_site_id');
	}

	public function getBbCodeMediaSitesForAdminQuickSearch($searchText)
	{
		$quotedString = XenForo_Db::quoteLike($searchText, 'lr', $this->_getDb());

		return $this->fetchAllKeyed('
			SELECT * FROM xf_bb_code_media_site
			WHERE site_title LIKE ' . $quotedString . '
			ORDER BY site_title',
		'media_site_id');
	}

	/**
	 * Prepares an SQL 'WHERE' clause for use in getBbCodeMediaSites()
	 *
	 * @param array $conditions
	 * @param array $fetchOptions
	 *
	 * @return string
	 */
	public function prepareOptionConditions(array $conditions = array(), array $fetchOptions = array())
	{
		$db = $this->_getDb();
		$sqlConditions = array();

		if (!empty($conditions['all']))
		{
			$sqlConditions[] = '1=1';
		}

		if (!empty($conditions['mediaSiteIds']))
		{
			$sqlConditions[] = 'xf_bb_code_media_site.media_site_id IN (' . $db->quote($conditions['mediaSiteIds']) . ')';
		}

		if (!empty($conditions['addOnId']))
		{
			$sqlConditions[] = 'xf_bb_code_media_site.addon_id = ' . $db->quote($conditions['addOnId']);
		}

		return $this->getConditionsForClause($sqlConditions);
	}

	/**
	 * Prepares an SQL 'ORDER' clause for use in getBbCodeMediaSites()
	 *
	 * @param array $fetchOptions
	 *
	 * @return string
	 */
	public function prepareOptionOrderOptions(array $fetchOptions = array(), $defaultOrderSql = '')
	{
		$choices = array(
			'media_site_id' => 'xf_bb_code_media_site.media_site_id',
			'site_title' => 'xf_bb_code_media_site.site_title',
		);
		return $this->getOrderByClause($choices, $fetchOptions, $defaultOrderSql);
	}

	/**
	 * Converts a sring of line-break-separated BB code media site match URLs into
	 * an array of regexes to match against.
	 *
	 * @param string $urls
	 * @param boolean $urlsAreRegex - If true, the individual entries are already regular expressions
	 *
	 * @return array
	 */
	public function convertMatchUrlsToRegexes($urls, $urlsAreRegex = false)
	{
		if (!$urls)
		{
			return array();
		}

		$urls = preg_split('/(\r?\n)+/', $urls, -1, PREG_SPLIT_NO_EMPTY);
		$regexes = array();
		foreach ($urls AS $url)
		{
			if (!$urlsAreRegex)
			{
				$url = preg_quote($url, '#');
				$url = str_replace('\\*', '.*', $url);
				$url = str_replace('\{\$id\}', '(?P<id>[^"\'?&;/<>\#\[\]]+)', $url);
				$url = str_replace('\{\$id\:digits\}', '(?P<id>[0-9]+)', $url);
				$url = str_replace('\{\$id\:alphanum\}', '(?P<id>[a-z0-9]+)', $url);
				$url = '#' . $url . '#i';
			}

			$regexes[] = $url;
		}

		return $regexes;
	}

	/**
	 * Gets the BB code media site data for the cache.
	 *
	 * @return array
	 */
	public function getBbCodeMediaSitesForCache()
	{
		$sites = $this->getAllBbCodeMediaSites();
		$cache = array();
		foreach ($sites AS &$site)
		{
			$cache[$site['media_site_id']] = array(
				'embed_html' => $site['embed_html']
			);

			if ($site['embed_html_callback_class'] && $site['embed_html_callback_method'])
			{
				$cache[$site['media_site_id']]['callback'] = array($site['embed_html_callback_class'], $site['embed_html_callback_method']);
			}
		}

		return $cache;
	}

	/**
	 * Gets the BB code cache data.
	 *
	 * @return array
	 */
	public function getBbCodeCache()
	{
		return array(
			'mediaSites' => $this->getBbCodeMediaSitesForCache()
		);
	}

	/**
	 * Rebuilds the BB code cache.
	 *
	 * @return array
	 */
	public function rebuildBbCodeCache()
	{
		$cache = $this->getBbCodeCache();

		$this->_getDataRegistryModel()->set('bbCode', $cache);
		return $cache;
	}

	/**
	 * Appends the add-on BB code media sites XML to a given DOM element.
	 *
	 * @param DOMElement $rootNode Node to append all elements to
	 * @param string $addOnId Add-on ID to be exported
	 */
	public function appendBbCodeMediaSitesAddOnXml(DOMElement $rootNode, $addOnId)
	{
		$document = $rootNode->ownerDocument;

		$siteFields = XenForo_DataWriter::create('XenForo_DataWriter_BbCodeMediaSite')->getFieldNames();

		$childTags = array('match_urls', 'embed_html');

		foreach ($this->getBbCodeMediaSitesByAddOnId($addOnId) AS $site)
		{
			$siteNode = $document->createElement('site');

			foreach ($siteFields AS $fieldName)
			{
				if ($fieldName != 'addon_id')
				{
					if (in_array($fieldName, $childTags))
					{
						$fieldNode = $document->createElement($fieldName);
						$fieldNode->appendChild(XenForo_Helper_DevelopmentXml::createDomCdataSection($document, $site[$fieldName]));

						$siteNode->appendChild($fieldNode);
					}
					else
					{
						$siteNode->setAttribute($fieldName, $site[$fieldName]);
					}
				}
			}

			$rootNode->appendChild($siteNode);
		}
	}

	/**
	 * Imports the BB code media sites for an add-on.
	 *
	 * @param SimpleXMLElement $xml XML element pointing to the root of the event data
	 * @param string $addOnId Add-on to import for
	 */
	public function importBbCodeMediaSitesAddOnXml(SimpleXMLElement $xml, $addOnId)
	{
		$db = $this->_getDb();

		XenForo_Db::beginTransaction($db);

		$db->delete('xf_bb_code_media_site', 'addon_id = ' . $db->quote($addOnId));

		$xmlSites = XenForo_Helper_DevelopmentXml::fixPhpBug50670($xml->site);

		$siteIds = array();
		foreach ($xmlSites AS $site)
		{
			$siteIds[] = (string)$site['media_site_id'];
		}

		$sites = $this->getBbCodeMediaSites(array('mediaSiteIds' => $siteIds));

		foreach ($xmlSites AS $site)
		{
			$siteId = (string)$site['media_site_id'];

			$dw = XenForo_DataWriter::create('XenForo_DataWriter_BbCodeMediaSite');
			if (isset($sites[$siteId]))
			{
				$dw->setExistingData($sites[$siteId]);
			}
			$dw->bulkSet(array(
				'media_site_id' => $siteId,
				'site_title' => (string)$site['site_title'],
				'site_url' => (string)$site['site_url'],
				'match_urls' => (string)XenForo_Helper_DevelopmentXml::processSimpleXmlCdata($site->match_urls),
				'match_is_regex' => (int)$site['match_is_regex'],
				'match_callback_class' => (string)$site['match_callback_class'],
				'match_callback_method' => (string)$site['match_callback_method'],
				'embed_html' => (string)XenForo_Helper_DevelopmentXml::processSimpleXmlCdata($site->embed_html),
				'embed_html_callback_class' => (string)$site['embed_html_callback_class'],
				'embed_html_callback_method' => (string)$site['embed_html_callback_method'],
				'supported' => (int)$site['supported'],
				'addon_id' => $addOnId,
			));
			$dw->save();
		}

		XenForo_Db::commit($db);
	}

	/**
	 * Deletes all BB code media sites for the specified add-on
	 *
	 * @param string $addOnId
	 */
	public function deleteBbCodeMediaSitesForAddOn($addOnId)
	{
		$db = $this->_getDb();

		$db->delete('xf_bb_code_media_site', 'addon_id = ' . $db->quote($addOnId));

		$this->rebuildBbCodeCache();
	}
}