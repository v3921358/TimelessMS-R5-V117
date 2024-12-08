                        <div id='content_wrapper'>
                                <div id='content'>
                                	<div id='content_wrapper_left'>

<?php
	if(basename($_SERVER["PHP_SELF"]) == "vote.php"){
		die("Error 404");
	}
?>
<br/>
<div align="center">
	You may vote twice every 6 hours. <br/>You will recieve 5k NX &amp; 1 Vote points per a vote.<br/>
	<hr/>
<form action="?page=vote" method="POST">
				<h2>Vote on GTOP100</h2>
				<input type="text" name="name" maxlength="12" class="input" placeholder="Username" required/><br/>
				<input type="submit" class="btn" name="doVote" value="Vote" class="doVote">
				<input type="hidden" class="button" name="doVote" value="1">
</form>
</div>
<div align="center">
<?php
	if (@$_POST["doVote"] != "1") {
} else {
		$earnednx = false;
		$account = $_POST['name'];
		$account = mysql_real_escape_string($account);
		mysql_select_db($host['database']);
		$query=mysql_query("SELECT * FROM accounts WHERE name='" . mysql_real_escape_string($_POST["name"]) . "'");
		$info=mysql_fetch_assoc($query);
		if($_POST["name"] == "") {
			echo 'Please fill in the correct account credentials.';
		} elseif(mysql_num_rows($query) < 1) {
			echo 'Please fill in the correct account credentials.';
		} elseif($info["loggedin"] > 0) {
			echo 'You must be logged out to vote for rewards.';
		} else {
			$ip = $_SERVER['REMOTE_ADDR'];
			$time = time();
			$get = "SELECT *, SUM(`times`) as amount FROM voterecords WHERE account='$account'";
			$query1 = mysql_query($get);
			$lasttime = mysql_fetch_array($query1);
			$amount = $lasttime['amount'];
			$insertnew = false;	
			if ($amount == "") {
				$insertnew = true;
			}
			$timecalc = $time - $lasttime['date'];
			if (!$insertnew) {
				if ($timecalc < 22100) {
					date_default_timezone_set(''.$timezone.'');
					$_SESSION['vote_date'] = date('M d\, h:i:s A', $lasttime['date']);
					echo 'You\'ve already voted with this account in the past 6 hours!<br />Last time you voted was on: '.$_SESSION['vote_date'].'!';
				} else {			
					$update = mysql_query("SELECT * from voterecords WHERE ip='$ip' AND account = '$account'");
					if ($update) {
						mysql_query("UPDATE voterecords SET account='$account', date='$time', times=times+1 WHERE account='$account'");
						mysql_query("UPDATE voterecords SET account='$account', date='$time', times=times+1 WHERE ip='$ip'");
						$earnednx = true;
					} elseif (!$update) {	
						$ipinsert = mysql_query("INSERT INTO voterecords (`account`, `ip`, `date`, `times`) VALUES ('$account', '$ip', '$time', 1)");
						if (!$ipinsert) {
							$message  = 'Invalid query: ' . mysql_error() . "\n";
							$message .= 'Whole query: ' . $ipinsert;
							die($message);
						} else {
							$earnednx = true;
						}
					} else {
						$message  = 'Invalid query: ' . mysql_error() . "\n";
						$message .= 'Whole query: ' . $update;
						die($message);
					}
				}
			} else {
				$success = mysql_query("INSERT INTO voterecords (`account`, `ip`, `date`, `times`) VALUES ('$account', '$ip', '$time', 1)");
				if (!$success) {
					$message  = 'Invalid query: ' . mysql_error() . "\n";
					$message .= 'Whole query: ' . $success;
					die($message);
				} else {
					$earnednx = true;
				}
			}
			if ($earnednx) {
				mysql_query("UPDATE accounts SET vpoints = vpoints + '2' WHERE name='" . mysql_real_escape_string($_POST["name"]) . "'");
				mysql_query("UPDATE accounts SET lastvote='" .time(). "' WHERE name='" . mysql_real_escape_string($_POST["name"]) . "'");
				mysql_close();
				echo '<html>';
				echo '<head>';
				unset($_SESSION['vote_err']);
				echo '<meta HTTP-EQUIV="REFRESH" content="0; url='.$gtop.'">';
				echo '</head>';
				echo '</html>';
			}
		}
}
	if (@$_POST["doVote"] != "2") {
} else {
		$earnednx = false;
		$account = $_POST['name'];
		$account = mysql_real_escape_string($account);
		mysql_select_db($host['database']);
		$query=mysql_query("SELECT * FROM accounts WHERE name='" . mysql_real_escape_string($_POST["name"]) . "'");
		$info=mysql_fetch_assoc($query);
		if($_POST["name"] == "") {
			echo 'Please fill in the correct account credentials.';
		} elseif(mysql_num_rows($query) < 1) {
			echo 'Please fill in the correct account credentials.';
		} elseif($info["loggedin"] > 0) {
			echo 'You must be logged out to vote for rewards.';
		} else {
			$ip = $_SERVER['REMOTE_ADDR'];
			$time = time();
			$get = "SELECT *, SUM(`times`) as amount FROM voterecords WHERE account='$account'";
			$query1 = mysql_query($get);
			$lasttime = mysql_fetch_array($query1);
			$amount = $lasttime['amount'];
			$insertnew = false;	
			if ($amount == "") {
				$insertnew = true;
			}
			$timecalc = $time - $lasttime['date'];
			if (!$insertnew) {
				if ($timecalc < 22100) {
					date_default_timezone_set(''.$timezone.'');
					$_SESSION['vote_date'] = date('M d\, h:i:s A', $lasttime['date']);
					echo 'You\'ve already voted with this account in the past 6 hours!<br />Last time you voted was on: '.$_SESSION['vote_date'].'!';
				} else {			
					$update = mysql_query("SELECT * from voterecords WHERE ip='$ip' AND account = '$account'");
					if ($update) {
						mysql_query("UPDATE voterecords SET account='$account', date='$time', times=times+1 WHERE account='$account'");
						mysql_query("UPDATE voterecords SET account='$account', date='$time', times=times+1 WHERE ip='$ip'");
						$earnednx = true;
					} elseif (!$update) {	
						$ipinsert = mysql_query("INSERT INTO voterecords (`account`, `ip`, `date`, `times`) VALUES ('$account', '$ip', '$time', 1)");
						if (!$ipinsert) {
							$message  = 'Invalid query: ' . mysql_error() . "\n";
							$message .= 'Whole query: ' . $ipinsert;
							die($message);
						} else {
							$earnednx = true;
						}
					} else {
						$message  = 'Invalid query: ' . mysql_error() . "\n";
						$message .= 'Whole query: ' . $update;
						die($message);
					}
				}
			} else {
				$success = mysql_query("INSERT INTO voterecords (`account`, `ip`, `date`, `times`) VALUES ('$account', '$ip', '$time', 1)");
				if (!$success) {
					$message  = 'Invalid query: ' . mysql_error() . "\n";
					$message .= 'Whole query: ' . $success;
					die($message);
				} else {
					$earnednx = true;
				}
			}
			if ($earnednx) {
				mysql_query("UPDATE accounts SET vpoints = vpoints + '2' WHERE name='" . mysql_real_escape_string($_POST["name"]) . "'");
				mysql_query("UPDATE accounts SET lastvote='" .time(). "' WHERE name='" . mysql_real_escape_string($_POST["name"]) . "'");
				mysql_close();
				echo '<html>';
				echo '<head>';
				unset($_SESSION['vote_err']);
				echo '<meta HTTP-EQUIV="REFRESH" content="0; url='.$gtop.'">';
				echo '</head>';
				echo '</html>';
			}
		}
}
?>
</div>
</div>
                                </div>
                        </div>
