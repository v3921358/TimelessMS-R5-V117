                        <div id='content_wrapper'>
                                <div id='content'>
                                	<div id='content_wrapper_left'>
	<center>
<?php
	if(basename($_SERVER["PHP_SELF"]) == "rankings.php"){
		die("Error 404");
	}
?>
<?php
# Import Database&Properties
require_once("config.php");

$jobs = array(
  1000 =>'Noblesse',
  1100 =>'Dawn Warrior',
  1110 =>'Dawn Warrior 2',
  1111 =>'Dawn Warrior 3',
  1112 =>'Dawn Warrior 4',
  1200 =>'Blaze Wizard',
  1200 =>'Blaze Wizard',
  1210 =>'Blaze Wizard 2',
  1211 =>'Blaze Wizard 3',
  1212 =>'Blaze Wizard 3',
  1300 =>'Wind Archer',
  1310 =>'Wind Archer 2',
  1311 =>'Wind Archer 3',
  1312 =>'Wind Archer 4',
  1400 =>'Night Walker',
  1410 =>'Night Walker 2',
  1411 =>'Night Walker 3',
  1412 =>'Night Walker 4',
  1500 =>'Thunder Breaker',
  1510 =>'Thunder Breaker 2',
  1511 =>'Thunder Breaker 3',
  1512 =>'Thunder Breaker 4',
  2001 =>'Evan 1',
  2200 =>'Evan 2',
  2210 =>'Evan 3',
  2211 =>'Evan 4',
  2212 =>'Evan 5',
  2213 =>'Evan 6',
  2214 =>'Evan 7',
  2215 =>'Evan 8',
  2216 =>'Evan 9',
  2217 =>'Evan 10',
  2218 =>'Evan 11',
  2000 =>'Legend',
  2100 =>'Aran 1',
  2110 =>'Aran 2',
  2111 =>'Aran 3',
  2112 =>'Aran 4',
  2300 =>'Mercedes 1',
  2310 =>'Mercedes 2',
  2311 =>'Mercedes 3',
  2312 =>'Mercedes 4',
  2003 =>'Phantom',
  2400 =>'Phantom 1',
  2410 =>'Phantom 2',
  2411 =>'Phantom 3',
  2412 =>'Phantom 4',
  3000 => 'Citizen',
  3100 =>'Demon Slayer 1',
  3110 =>'Demon Slayer 2',
  3111 =>'Demon Slayer 3',
  3112 =>'Demon Slayer 4',
  3200 =>'Battle Mage 1',
  3210 =>'Battle Mage 2',
  3211 =>'Battle Mage 3',
  3212 =>'Battle Mage 4',
  3300 =>'Wild Hunter 1',
  3310 =>'Wild Hunter 2',
  3311 =>'Wild Hunter 3',
  3312 =>'Wild Hunter 4',
  3500 =>'Mechanic 1',
  3510 =>'Mechanic 2',
  3511 =>'Mechanic 3',
  3512 =>'Mechanic 4',
  900 =>'GameMaster',
  910 =>'Super GM',
  800 =>'Manager',
  500 =>'Pirate',
  510 =>'Brawler',
  520 =>'Gunslinger',
  511 =>'Marauder',
  521 =>'Outlaw',
  512 =>'Buccaneer',
  522 =>'Corsair',
  501 =>'Cannon shooter 1',
  530 =>'Cannon shooter 2',
  531 =>'Cannon shooter 3',
  532 =>'Cannon shooter 4',
  508 =>'Jett 1',
  570 =>'Jett 2',
  571 =>'Jett 3',
  572 =>'Jett 4',
  0   =>'Beginner',
  100 =>'Warrior',
  110 =>'Fighter',
  120 =>'Page',
  130 =>'Spearman',
  111 =>'Crusader',
  121 =>'White Knight',
  131 =>'Dragon Knight',
  112 =>'Hero',
  122 =>'Paladin',
  132 =>'Dark Knight',
  200 =>'Magician',
  210 =>'Fire/Poison Wizard',
  220 =>'Ice/Lightning Wizard',
  230 =>'Cleric',
  211 =>'Fire/Poison Mage',
  221 =>'Ice/Lightning Mage',
  231 =>'Priest',
  212 =>'Fire/Poison Arch Mage',
  222 =>'Ice/Lightning Arch Mage',
  232 =>'Bishop',
  300 =>'Bowman',
  310 =>'Hunter',
  320 =>'Crossbowman',
  311 =>'Ranger',
  321 =>'Sniper',
  312 =>'Bow Master',
  322 =>'Crossbow Master',
  400 =>'Thief',
  410 =>'Assassin',
  420 =>'Bandit',
  411 =>'Hermit',
  421 =>'Chief Bandit',
  412 =>'Night Lord',
  422 =>'Shadower',
  430 =>'Blade Recruit',
  431 =>'Blade Acolyte',
  432 =>'Blade Specialist',
  433 =>'Blade Lord',
  434 =>'Blade Master',
  5100 =>'Mihile 1',
  5110 =>'Mihile 2',
  5111 =>'Mihile 3',
  5112 =>'Mihile 4'
);

$job = array(
  1000 =>'<img src="inc/inc/img/ranks/icon-job-beginner.gif">',
  1100 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  1110 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  1111 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  1112 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  1200 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  1210 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  1211 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  1212 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  1300 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  1310 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  1311 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  1312 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  1400 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  1410 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  1411 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  1412 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  1500 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  1510 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  1511 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  1512 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  2000 =>'<img src="inc/img/ranks/icon-job-beginner.gif">',
  2100 =>'<img src="inc/img/ranks/icon-job-aran.gif">',
  2110 =>'<img src="inc/img/ranks/icon-job-aran.gif">',
  2111 =>'<img src="inc/img/ranks/icon-job-aran.gif">',
  2112 =>'<img src="inc/img/ranks/icon-job-aran.gif">',
  900 =>'<img src="inc/img/ranks/icon-job-gm.gif">',
  910 =>'<img src="inc/img/ranks/icon-job-gm.gif">',
  500 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  510 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  520 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  511 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  521 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  512 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  522 =>'<img src="inc/img/ranks/icon-job-pirate.gif">',
  0   =>'<img src="inc/img/ranks/icon-job-beginner.gif">',
  100 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  110 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  120 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  130 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  111 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  121 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  131 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  112 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  122 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  132 =>'<img src="inc/img/ranks/icon-job-warrior.gif">',
  200 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  210 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  220 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  230 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  211 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  221 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  231 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  212 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  222 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  232 =>'<img src="inc/img/ranks/icon-job-magician.gif">',
  300 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  310 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  320 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  311 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  321 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  312 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  322 =>'<img src="inc/img/ranks/icon-job-bowman.gif">',
  400 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  410 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  420 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  411 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  421 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  412 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  422 =>'<img src="inc/img/ranks/icon-job-thief.gif">',
  430 =>'<img src="inc/img/ranks/icon-job-dual_blade.gif">',
  431 =>'<img src="inc/img/ranks/icon-job-dual_blade.gif">',
  432 =>'<img src="inc/img/ranks/icon-job-dual_blade.gif">',
  433 =>'<img src="inc/img/ranks/icon-job-dual_blade.gif">',
  434 =>'<img src="inc/img/ranks/icon-job-dual_blade.gif">', 
  501 =>'<img src="inc/img/ranks/Icon-job-cannon_shooter.png">', //icon-job-beginner.gif
  530 =>'<img src="inc/img/ranks/Icon-job-cannon_shooter.png">', //icon-job-beginner.gif
  531 =>'<img src="inc/img/ranks/Icon-job-cannon_shooter.png">', //icon-job-beginner.gif
  532 =>'<img src="inc/img/ranks/Icon-job-cannon_shooter.png">', //icon-job-beginner.gif
  508 =>'<img src="inc/img/ranks/icon-job-jett.gif">',
  570 =>'<img src="inc/img/ranks/icon-job-jett.gif">',
  571 =>'<img src="inc/img/ranks/icon-job-jett.gif">',
  572 =>'<img src="inc/img/ranks/icon-job-jett.gif">', 
  2300 =>'<img src="inc/img/ranks/icon-job-mercedes.gif">',
  2310 =>'<img src="inc/img/ranks/icon-job-mercedes.gif">',
  2311 =>'<img src="inc/img/ranks/icon-job-mercedes.gif">',
  2312 =>'<img src="inc/img/ranks/icon-job-mercedes.gif">',  
  2003 =>'<img src="inc/img/ranks/icon-job-phantom.gif">',
  2400 =>'<img src="inc/img/ranks/icon-job-phantom.gif">',
  2410 =>'<img src="inc/img/ranks/icon-job-phantom.gif">',
  2411 =>'<img src="inc/img/ranks/icon-job-phantom.gif">',
  2412 =>'<img src="inc/img/ranks/icon-job-phantom.gif">',
  3000 =>'<img src="inc/img/ranks/icon-job-citizen.gif">',
  3100 =>'<img src="inc/img/ranks/icon-job-demonslayer.gif">',
  3110 =>'<img src="inc/img/ranks/icon-job-demonslayer.gif">',
  3111 =>'<img src="inc/img/ranks/icon-job-demonslayer.gif">',
  3112 =>'<img src="inc/img/ranks/icon-job-demonslayer.gif">',  
  3200 =>'<img src="inc/img/ranks/icon-job-battle_mage.gif">',
  3210 =>'<img src="inc/img/ranks/icon-job-battle_mage.gif">',
  3211 =>'<img src="inc/img/ranks/icon-job-battle_mage.gif">',
  3212 =>'<img src="inc/img/ranks/icon-job-battle_mage.gif">',  
  3300 =>'<img src="inc/img/ranks/icon-job-wild_hunter.gif">',
  3310 =>'<img src="inc/img/ranks/icon-job-wild_hunter.gif">',
  3311 =>'<img src="inc/img/ranks/icon-job-wild_hunter.gif">',
  3312 =>'<img src="inc/img/ranks/icon-job-wild_hunter.gif">',  
  3500 =>'<img src="inc/img/ranks/icon-job-mechanic.gif">',
  3510 =>'<img src="inc/img/ranks/icon-job-mechanic.gif">',
  3511 =>'<img src="inc/img/ranks/icon-job-mechanic.gif">',
  3512 =>'<img src="inc/img/ranks/icon-job-mechanic.gif">', 
  2001 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2200 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2210 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2211 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2212 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2213 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2214 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2215 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2216 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2217 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  2218 =>'<img src="inc/img/ranks/icon-job-evan.gif">',
  5100 =>'<img src="inc/img/ranks/icon-job-mihile.gif">',
  5110 =>'<img src="inc/img/ranks/icon-job-mihile.gif">',
  5111 =>'<img src="inc/img/ranks/icon-job-mihile.gif">',
  5112 =>'<img src="inc/img/ranks/icon-job-mihile.gif">'
);

?>

<center>

<br>
<table border='3' bordercolor='#000000' style='border-style: solid;  border-collapse: collapse' >

<tr >
<center>
<h2>GM</h2>
<th bgcolor='#2b2b2b' style='height: 30px;width: 90px;'><font color="#fff">&nbsp;Rank&nbsp;</font></th>
<th bgcolor='#2b2b2b'   style='height: 30px;width: 50px;'><font color="#fff">&nbsp;job&nbsp;</font></th>
<th bgcolor='#2b2b2b'   style='height: 30px;width: 50px;'><font color="#fff">&nbsp;Level&nbsp;</font></th>
<th bgcolor='#2b2b2b'   style='height: 30px;width: 110px;'><font color="#fff">&nbsp;Name&nbsp;</font></th>
<th bgcolor='#2b2b2b' style='height: 30px;width: 100px;'><font color="#fff">&nbsp;Job name&nbsp;</font></th>
<th bgcolor='#2b2b2b' style='height: 30px;width: 90px;'><font color="#fff">&nbsp;GM level&nbsp;</font></th>
</center>
</tr>
</table>
<table border='3' bordercolor='#eee' style='border-style: solid;  border-collapse: collapse' >
<?php

$number = 1;
$q = mysql_query('
SELECT characters.name, characters.rank, characters.job , characters.level, characters.gm, characters.level FROM characters, accounts WHERE characters.accountid=accounts.id and characters.gm > 1 and accounts.banned = 0 ORDER BY characters.level DESC LIMIT 1000');
while ($r = mysql_fetch_array($q)) {
?><tr>
<font color="#fff">
<td bgcolor='#848484'  style='width: 90px; '><center>&nbsp;<?php echo $number; $number = $number+1;?>&nbsp;</center></td>
<td bgcolor='#848484' border  style='width: 50px; '><center>&nbsp;<?php echo $job[$r['job']];?>&nbsp;</center></td>
<td bgcolor='#848484'  style='width: 50px;'><center>&nbsp;<?php echo $r['level'];?>&nbsp;</center></b></td>
<td bgcolor='#848484'  style='width: 110px; '><center>&nbsp;<?php echo $r['name'];?>&nbsp;</center></td>
<td bgcolor='#848484'  style='width: 100px; '><center>&nbsp;<?php echo $jobs[$r['job']];?>&nbsp;</center></td>
<td bgcolor='#848484'  style='width: 90px; '><center>&nbsp;<?php echo $r['gm'];?>&nbsp;</center></td>
</font>

</tr>

<?php } ?>
</table>


<center>

<br>
<table border='3' bordercolor='#000000' style='border-style: solid;  border-collapse: collapse' >

<tr >
<center>
<h2>Players</h2>
<th bgcolor='#2b2b2b' style='height: 30px;width: 90px;'><font color="#fff">&nbsp;Rank&nbsp;</font></th>
<th bgcolor='#2b2b2b'   style='height: 30px;width: 50px;'><font color="#fff">&nbsp;Job&nbsp;</font></th>
<th bgcolor='#2b2b2b'   style='height: 30px;width: 50px;'><font color="#fff">&nbsp;Level&nbsp;</font></th>
<th bgcolor='#2b2b2b'   style='height: 30px;width: 110px;'><font color="#fff">&nbsp;Name&nbsp;</font></th>
<th bgcolor='#2b2b2b' style='height: 30px;width: 100px;'><font color="#fff">&nbsp;Job name&nbsp;</font></th>
<th bgcolor='#2b2b2b' style='height: 30px;width: 90px;'><font color="#fff">&nbsp;Fame&nbsp;</font></th>
</tr>
</center>
</table>
<table border='3' bordercolor='#eee' style='border-style: solid;  border-collapse: collapse' >
<?php

$number = 1;
$q = mysql_query('
SELECT characters.name, characters.rank, characters.job , characters.level, characters.fame, characters.level FROM characters, accounts WHERE characters.accountid=accounts.id and characters.gm < 2 and accounts.banned = 0 ORDER BY characters.level DESC LIMIT 1000');
while ($r = mysql_fetch_array($q)) {
?><tr>
<font color="#fff">
<td bgcolor='#848484'  style='width: 90px; '><center>&nbsp;<?php echo $number; $number = $number+1;?>&nbsp;</center></td>
<td bgcolor='#848484' border  style='width: 50px; '><center>&nbsp;<?php echo $job[$r['job']];?>&nbsp;</center></td>
<td bgcolor='#848484'  style='width: 50px;'><center>&nbsp;<?php echo $r['level'];?>&nbsp;</center></b></td>
<td bgcolor='#848484'  style='width: 110px; '><center>&nbsp;<?php echo $r['name'];?>&nbsp;</center></td>
<td bgcolor='#848484'  style='width: 100px; '><center>&nbsp;<?php echo $jobs[$r['job']];?>&nbsp;</center></td>
<td bgcolor='#848484'  style='width: 90px; '><center>&nbsp;<?php echo $r['fame'];?>&nbsp;</center></td>
</font>

</tr>

<?php } ?>
</table>

</center>
</div>
</div>
		</tr></table></center></div>
