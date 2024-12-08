<?php

class XFCliImporter_CodeEvent_Listener
{
	public static function loadClassModel($class, array &$extend)
	{
		switch ($class)
		{
			case 'XenForo_Model_Import':
				XenForo_Model_Import::$extraImporters['vbcli'] = 'XFCliImporter_Importer_vBulletinCli';
				break;
		}
	}
}