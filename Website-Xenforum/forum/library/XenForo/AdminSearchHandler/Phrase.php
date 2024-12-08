<?php

class XenForo_AdminSearchHandler_Phrase extends XenForo_AdminSearchHandler_Abstract
{
	protected function _getTemplateName()
	{
		return 'quicksearch_phrases';
	}

	public function getPhraseKey()
	{
		return 'phrases';
	}

	public function search($searchText, array $phraseMatches = null)
	{
		/* @var $phraseModel XenForo_Model_Phrase */
		$phraseModel = $this->getModelFromCache('XenForo_Model_Phrase');

		/* @var $languageModel XenForo_Model_Language */
		$languageModel = $this->getModelFromCache('XenForo_Model_Language');

		return $phraseModel->getEffectivePhraseListForLanguage(
			$languageModel->getLanguageIdFromCookie(),
			array('title' => $searchText)
		);
	}

	public function getAdminPermission()
	{
		return 'language';
	}
}