<?php
if(basename($_SERVER["PHP_SELF"]) == "config.php"){
	die("Error 404");
} 
session_start();

// Database
$host['hostname'] = "localhost";
$host['user'] 	  = "root";
$host['password'] = "";
$host['database'] = "116";
$serverip  		  = "";
$loginport 		  = "8484";
// Server
$servername		  = "TimelessMS";
$version 		  = "1.17";
$exp 			  = "440x";
$meso			  = "220x"; // 
$drop 			  = "3x";
$forums 		  = "/forums"; //Forum Link

// Downloads
$client 		  = "";//Client Download
$setup 			  = "";//Setup Download
$patch            = "";//Patch from a previous version to next. (EX: 116 - 117)
// Voting
$gtop			  = "";//vote link

$features = //Server Features
    "
	<li>Rates are 600x/200x/3x</li>
	<li>v1.17.1</li>
    <li>Diablo 3 Hardcore Mode System (Optional)</li>
    <li>v1.17 Drops</li>
    <li>All jobs fully working</li>
    <li>Attacking Pets</li>
    <li>Party Levelling System</li>
    <li>Weapon Levelling</li>
    <li>Armour Levelling</li>
    <li>Maple Leafs Exchanger</li>
    <li>Boss Party Quest</li>
    <li>BPQ Points Exchanger</li>
    <li>Every job fully functional</li>
    <li>99% Skills fixed</li>
    <li>@whosfirst - solution for those getting kill stolen!</li>
    <li>Striving gameplay</li>
    <li>Dedicated Server</li>
    <li>24/7 Uptime</li>
    <li>Achievement System</li>
    <li>XP Support</li>
    <li>Amazing Community</li>
    <li>Constant Updates & Development</li>
    <li>All cubes & Super Megaphones are rare drops from all mobs</li>
    <li>Great sense of achievement from playing!</li>";
	
$notice           = "Burblish Coded~";
// Social
$facebook		  = "http://www.facebook.com/pages/Ingenious-wire/425023840849360?ref=hl";
$youtube		  = "";
$twitter		  = "";
$offline = "<center><img src='images/offline.png' /></center>"; // Online indicator
$online = "<center><img src='images/online.png' /></center>"; // Offline indicator
// Do not touch
mysql_connect($host['hostname'],$host['user'],$host['password']) OR die("Can't connect to server");
mysql_select_db($host['database']) OR die("Please, configure your Database Settings in config.php");

function sql_injectionproof($sCode) {
	if (function_exists("mysql_real_escape_string")) {
		$sCode = mysql_real_escape_string($sCode);
	} else {
		$sCode = addslashes($sCode);
	}
	return $sCode;
}
?>