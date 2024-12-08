<?php
include_once('inc/config.php');

$page = @$_GET['page'];
switch ($page) {
	case null:
	case "index":
		$getpage = "pages/home";
		$header = "Home";
		break;
	case "home":
		$getpage = "pages/home";
		$header = "Home";
		break;
	case "register":
		$getpage = "pages/register";
		$header = "Register";
		break;
	case "downloads":
		$getpage = "pages/downloads";
		$header = "Downloads";
		break;
	case "vote":
		$getpage = "pages/vote";
		$header = "Vote";
		break;
	case "donate":
		$getpage = "pages/donate";
		$header = "Donate";
		break;
	case "rankings":
		$getpage = "pages/rankings";
		$header = "Ranking";
		break;
	case "status":
		$getpage = "pages/status";
		$header = "Status";
		break;
	case "chat":
		$getpage = "pages/chat";
		$header = "Chat";
		break;
	default:
		$title = $servername."";
		$getpage = "pages/home";
		break;
}

include_once('inc/top.php');
include_once($getpage.".php");
include_once('inc/bottom.php');
?>