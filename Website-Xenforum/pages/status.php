                        <div id='content_wrapper'>
                                <div id='content'>
                                	<div id='content_wrapper_left'>
						<?php

$countonline = mysql_query("SELECT * FROM accounts where loggedin = 2");
$countaccs = mysql_query("SELECT * FROM accounts");
$countchars = mysql_query("SELECT * FROM characters");
$countguilds = mysql_query("SELECT * FROM guilds");
$onlineppl = mysql_num_rows($countonline);
$totalaccs = mysql_num_rows($countaccs);
$totalchars = mysql_num_rows($countchars);
$guilds = mysql_num_rows($countguilds);
        echo  ' <br>Server Status :  ';

        $fp = @fsockopen($serverip, $loginport, $errno, $errstr, 1);
	  if (!$fp) 
	{ echo $offline; } else { echo $online; }
        @fclose($fp);

echo "<br>Players Online : ".$onlineppl;
echo "<br><font size='1'>Accounts : </font>".$totalaccs;
echo "<br><font size='1'>Characters : </font>".$totalchars;
echo "<br><font size='1'>Guilds : </font>".$guilds;
/**
echo "<br><br><font size='1'>Exp Rate : </font>".$rate['exp'];
echo "<br><font size='1'>Meso Rate : </font>".$rate['meso'];
echo "<br><font size='1'>Drop Rate : </font>".$rate['drop'];
echo "<br><font size='1'>Version : v. </font>".$version;
**/

?>
</div>									
</div>
                                </div>