<?php

class XFCliImporter_Importer_vBulletinCli extends XenForo_Importer_vBulletin
{
	public static function getName()
	{
		return 'vBulletin 3.7/3.8 Multi-Threaded (Alpha)';
	}

	public function configStepThreads(array $options)
	{
		if (!file_exists('library/XFCliImporter/import.php'))
		{
			// proceed directly to web-based importer
			return false;
		}

		if ($options)
		{
			if (isset($options['importMode']) && $options['importMode'] == 'cli')
			{
				$session = $this->_session;

				if (!empty($options['taskset']))
				{
					$session->setExtraData('cli', 'taskset', $options['taskset']);
					$session->setExtraData('cli', 'numCores', $options['numCores']);
				}
				else
				{
					$session->setExtraData('cli', 'taskset', 0);
					$session->setExtraData('cli', 'numCores', 0);
				}
				$session->setExtraData('cli', 'phpBinary', $options['phpBinary']);
				$session->setExtraData('cli', 'numProcesses', $options['numProcesses']);
				$session->save();

				$this->_bootstrap($session->getConfig());

				$sDb = $this->_sourceDb;
				$prefix = $this->_prefix;

				$sDb->query('DROP TABLE IF EXISTS xf_import_thread');
				$sDb->query('
					CREATE TABLE xf_import_thread (
						threadid INT UNSIGNED NOT NULL,
						rownum INT UNSIGNED NOT NULL,
						PRIMARY KEY (threadid)
					)');
				$sDb->query('SET @i = -1');
				$sDb->query('
					INSERT INTO xf_import_thread (threadid, rownum)
					SELECT thread.threadid, @i AS rownum
					FROM ' . $prefix . 'thread AS thread
					INNER JOIN ' . $prefix . 'forum AS forum ON
						(thread.forumid = forum.forumid AND forum.link = \'\' AND forum.options & 4)
					WHERE thread.open <> 10
					HAVING (@i:=@i+1)  % 100 = 0
					ORDER BY thread.threadid
				');

				$viewParams = array(
					'options' => $options,
					'scriptPath' => dirname($_SERVER['SCRIPT_FILENAME'])
				);

				return $this->_controller->responseView(
					'XenForo_ViewAdmin_Import_ImportThreadsCli',
					'import_data_cli',
					$viewParams);
			}
			else
			{
				return false;
			}
		}

		return $this->_controller->responseView('XenForo_ViewAdmin_Import_ConfigThreads', 'import_config_threads');
	}

	public function stepThreads($start, array $options)
	{
		$options = array_merge(array(
			'limit' => 100,
			'postDateStart' => 0,
			'postLimit' => 0,
			'max' => false,
			'uniqueChecks' => true,
			'threadWatchDeferred' => false,
		), $options);

		if (!$options['uniqueChecks'])
		{
			$this->_db->query('SET unique_checks = 0');
		}

		$sDb = $this->_sourceDb;
		$prefix = $this->_prefix;

		$postsDb = Zend_Db::factory('mysqli', array(
			'host' => $this->_config['db']['host'],
			'port' => $this->_config['db']['port'],
			'username' => $this->_config['db']['username'],
			'password' => $this->_config['db']['password'],
			'dbname' => $this->_config['db']['dbname'],
			'charset' => $this->_config['db']['charset']
		));

		/* @var $model XenForo_Model_Import */
		$model = $this->_importModel;

		if ($options['max'] === false)
		{
			$options['max'] = $sDb->fetchOne('
				SELECT MAX(threadid)
				FROM ' . $prefix . 'thread
			');
		}

		// pull threads from things we actually imported as forums
		$threads = $sDb->fetchAll($sDb->limit(
		'
			SELECT thread.*,
				IF(user.username IS NULL, thread.postusername, user.username) AS postusername
			FROM ' . $prefix . 'thread AS thread FORCE INDEX (PRIMARY)
			LEFT JOIN ' . $prefix . 'user AS user ON (thread.postuserid = user.userid)
			INNER JOIN ' . $prefix . 'forum AS forum ON
				(thread.forumid = forum.forumid AND forum.link = \'\' AND forum.options & 4)
			WHERE thread.threadid >= ' . $sDb->quote($start) . '
				AND thread.open <> 10
			ORDER BY thread.threadid
		', $options['limit']));

		if (!$threads)
		{
			return true;
		}

		$next = 0;
		$total = 0;
		$totalPosts = 0;

		$nodeMap = $model->getImportContentMap('node');
		$threadPrefixMap = $model->getImportContentMap('threadPrefix');

		XenForo_Db::beginTransaction();

		foreach ($threads AS $thread)
		{
			if (trim($thread['title']) === '')
			{
				continue;
			}

			$threadId = $model->mapThreadId($thread['threadid']);
			if ($threadId)
			{
				// already imported this
				continue;
			}

			$postDateStart = $options['postDateStart'];

			$next = $thread['threadid'] + 1; // uses >=, will be moved back down if need to continue
			$options['postDateStart'] = 0;

			if ($options['postLimit'])
			{
				$maxPosts = $options['postLimit'] - $totalPosts;
			}
			else
			{
				$maxPosts = 0;
			}
			$postsStatement = $this->_getPostsForThread($thread['threadid'], $postDateStart, $maxPosts, $postsDb);

			$numPosts = $postsStatement->rowCount();
			if ($numPosts < 1)
			{
				if ($postDateStart)
				{
					// continuing thread but it has no more posts
					$total++;
				}
				continue;
			}

			if ($postDateStart)
			{
				// continuing thread we already imported
				$threadId = $model->mapThreadId($thread['threadid']);

				$position = $this->_db->fetchOne('
					SELECT MAX(position)
					FROM xf_post
					WHERE thread_id = ?
				', $threadId);
			}
			else
			{
				$forumId = $this->_mapLookUp($nodeMap, $thread['forumid']);
				if (!$forumId)
				{
					continue;
				}

				if (trim($thread['postusername']) === '')
				{
					$thread['postusername'] = 'Guest';
				}

				$import = array(
					'title' => $this->_convertToUtf8($thread['title'], true),
					'node_id' => $forumId,
					'user_id' => $model->mapUserId($thread['postuserid'], 0),
					'username' => $this->_convertToUtf8($thread['postusername'], true),
					'discussion_open' => $thread['open'],
					'post_date' => $thread['dateline'],
					'reply_count' => $thread['replycount'],
					'view_count' => $thread['views'],
					'sticky' => $thread['sticky'],
					'last_post_date' => $thread['lastpost'],
					'last_post_username' => $this->_convertToUtf8($thread['lastposter'], true)
				);
				if (isset($thread['prefixid']))
				{
					$import['prefix_id'] = $this->_mapLookUp($threadPrefixMap, $thread['prefixid'], 0);
				}
				switch ($thread['visible'])
				{
					case 0: $import['discussion_state'] = 'moderated'; break;
					case 2: $import['discussion_state'] = 'deleted'; break;
					default: $import['discussion_state'] = 'visible'; break;
				}

				$threadId = $model->importThread($thread['threadid'], $import);
				if (!$threadId)
				{
					continue;
				}

				$position = -1;

				$subs = $sDb->fetchPairs('
					SELECT userid, emailupdate
					FROM ' . $prefix . 'subscribethread
					WHERE threadid = ' . $sDb->quote($thread['threadid'])
				);
				if ($subs)
				{
					$userIdMap = $model->getImportContentMap('user', array_keys($subs));
					foreach ($subs AS $userId => $emailUpdate)
					{
						$newUserId = $this->_mapLookUp($userIdMap, $userId);
						if (!$newUserId)
						{
							continue;
						}

						$model->importThreadWatch($newUserId, $threadId, ($emailUpdate ? 1 : 0), $options['threadWatchDeferred']);
					}
				}
			}

			if ($threadId)
			{
				$quotedPostIds = array();
				$quotedPosts = array();

				$threadTitleRegex = '#^(re:\s*)?' . preg_quote($thread['title'], '#') . '$#i';

				$userIdMap = $model->getImportContentMap('user', $this->_getUsersForThread($thread['threadid'], $postDateStart, $maxPosts));

				while ($post = $postsStatement->fetch())
				{
					if ($post['title'] !== '' && !preg_match($threadTitleRegex, $post['title']))
					{
						$post['pagetext'] = '[b]' . htmlspecialchars_decode($post['title']) . "[/b]\n\n" . ltrim($post['pagetext']);
					}

					if (trim($post['username']) === '')
					{
						$post['username'] = 'Guest';
					}

					$post['pagetext'] = $this->_convertPostPageText($post['pagetext'], $post);

					$import = array(
						'thread_id' => $threadId,
						'user_id' => $this->_mapLookUp($userIdMap, $post['userid'], 0),
						'username' => $this->_convertPostUsername($post['username'], $post),
						'post_date' => $post['dateline'],
						'message' => $post['pagetext'],
						'attach_count' => 0,
						'ip' => $post['ipaddress']
					);
					switch ($post['visible'])
					{
						case 0: $import['message_state'] = 'moderated'; $import['position'] = $position; break;
						case 2: $import['message_state'] = 'deleted'; $import['position'] = $position; break;
						default: $import['message_state'] = 'visible'; $import['position'] = ++$position; break;
					}

					$post['xf_post_id'] = $model->importPost($post['postid'], $import);

					$options['postDateStart'] = $post['dateline'];
					$totalPosts++;

					$this->_getQuotedPostIds($post, $quotedPostIds, $quotedPosts);
				}
				$postsStatement->closeCursor();
				unset($postsStatement);

				$postIdMap = (empty($quotedPostIds) ? array() : $model->getImportContentMap('post', array_unique($quotedPostIds)));

				$db = XenForo_Application::getDb();

				foreach ($quotedPosts AS $xfPostid => $quotedPost)
				{
					$postQuotesRewrite = $this->_rewriteQuotes($quotedPost['pagetext'], $quotedPost['quotes'], $postIdMap);

					if ($quotedPost['pagetext'] != $postQuotesRewrite)
					{
						$db->update('xf_post', array('message' => $postQuotesRewrite), 'post_id = ' . $db->quote($xfPostid));
					}
				}
			}

			if (!$options['postLimit'] || $numPosts < $maxPosts)
			{
				// done this thread
				$total++;
				$options['postDateStart'] = 0;
			}
			else
			{
				// not necessarily done the thread; need to pick it up next page
				break;
			}
		}

		$postsDb->closeConnection();
		unset($postsDb);

		if ($options['threadWatchDeferred'])
		{
			$model->insertDeferredThreadWatch();
		}

		if ($options['postDateStart'])
		{
			// not done this thread, need to continue with it
			$next--;
		}

		XenForo_Db::commit();

		$this->_session->incrementStepImportTotal($total);

		if (!$options['uniqueChecks'])
		{
			$this->_db->query('SET unique_checks = 1');
		}

		return array($next, $options, $this->_getProgressOutput($next, $options['max']));
	}

	/**
	 * @return Zend_Db_Statement_Interface
	 */
	protected function _getPostsForThread($threadId, $postStartTime, $maxPosts, Zend_Db_Adapter_Abstract $postsDb)
	{
		$prefix = $this->_prefix;

		$postsSql = '
			SELECT post.*,
				IF(user.username IS NULL, post.username, user.username) AS username
			FROM ' . $prefix . 'post AS post
			LEFT JOIN ' . $prefix . 'user AS user ON (post.userid = user.userid)
			WHERE post.threadid = ' . $postsDb->quote($threadId) . '
				AND post.dateline > ' . $postsDb->quote($postStartTime) . '
			ORDER BY post.dateline
		';

		if ($maxPosts)
		{
			$postsSql = $postsDb->limit($postsSql, $maxPosts);
		}

		return $postsDb->query($postsSql);
	}

	/**
	 * @return array
	 */
	protected function _getUsersForThread($threadId, $postStartTime, $maxPosts)
	{
		$sDb = $this->_sourceDb;
		$prefix = $this->_prefix;

		$usersSql = '
			SELECT post.userid
			FROM ' . $prefix . 'post AS post
			WHERE post.threadid = ' . $sDb->quote($threadId) . '
				AND post.dateline > ' . $sDb->quote($postStartTime) . '
			ORDER BY post.dateline
		';

		if ($maxPosts)
		{
			$usersSql = $sDb->limit($usersSql, $maxPosts);
		}

		return $sDb->fetchCol($usersSql);
	}

	protected function _convertPostUsername($username, array $post)
	{
		return $this->_convertToUtf8($username, true);
	}

	protected function _convertPostPageText($pagetext, array $post)
	{
		return $this->_convertToUtf8($pagetext);
	}

	protected function _getQuotedPostIds(array $post, array &$quotedPostIds, array &$quotedPosts)
	{
		if (stripos($post['pagetext'], '[quote=') !== false)
		{
			if (preg_match_all('/\[quote=("|\'|)(?P<username>[^;]*);\s*(?P<postid>\d+)\s*\1\]/siU', $post['pagetext'], $quotes, PREG_SET_ORDER))
			{
				$quotedPost = array(
					'pagetext' => $post['pagetext'],
					'quotes' => array()
				);

				foreach ($quotes AS $quote)
				{
					$quotedPostId = intval($quote['postid']);

					$quotedPostIds[] = $quotedPostId;

					$quotedPost['quotes'][$quote[0]] = array($quote['username'], $quotedPostId);
				}

				$quotedPosts[$post['xf_post_id']] = $quotedPost;
			}
		}
	}

	public function __destruct()
	{
		if ($this->_sourceDb instanceof Zend_Db_Adapter_Abstract)
		{
			$this->_sourceDb->closeConnection();
		}
	}
}