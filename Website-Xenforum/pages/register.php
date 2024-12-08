                        <div id='content_wrapper'>
                                <div id='content'>
                                	<div id='content_wrapper_left'>
<?php
	if(basename($_SERVER["PHP_SELF"]) == "register.php"){
		die("Error 404");
	}
?>
By registering for <?php echo $servername; ?>, you agree to abide by the following:
<ul>
	<li>No hacking or third party program.</li>
	<li>No advertising other sites or servers.</li>
</ul>
<center>

	<?php
        if (isset($_POST['register'])) {
            $username = mysql_real_escape_string($_POST['username']);
            $password = mysql_real_escape_string($_POST['password']);
            $cpassword = mysql_real_escape_string($_POST['cpassword']);
            $email = mysql_real_escape_string($_POST['email']);
            $birth = mysql_real_escape_string($_POST['birth']);

            $ucheck = mysql_query("SELECT * FROM `accounts` WHERE `name`='" . $username . "'") or die(mysql_error());
            if ($username == "") {
                echo "Please enter in a username.";
            } elseif (mysql_num_rows($ucheck) >= 1) {
                echo "Username is already being used.";
            } elseif ($password == "") {
                echo "Please enter in a password.";
            } elseif ($password != $cpassword) {
                echo "The passwords do not match.";
            } elseif ($email == "") {
                echo "Please enter in an email.";
            } elseif (strlen($username) < 4) {
                echo "Username must be between 4 and 12 characters!";
            } elseif (strlen($username) > 12) {
                echo "Username must be between 4 and 12 characters!";
            } elseif (strlen($password) < 6) {
                echo "Password must be between 6 and 12 characters!";
            } elseif (strlen($password) > 12) {
                echo "Password must be between 6 and 12 characters!";
            } elseif (strlen($birth) > 10) {
                echo "Please enter in a username.";
            } else {
                $ia = mysql_query("INSERT INTO `accounts` (`name`,`password`,`birthday`,`email`) VALUES ('" . $username . "','" . sha1($password) . "','" . $birth . "','" . $email . "')") or die(mysql_error());
                echo "You are now registered to ".$servername."!";
            }
        }
    ?>
	<br/>
	<form method="POST" action="">
		<table cellspacing="0" cellpadding="0">
            <tr>
				<td><img src="inc/images/inputs/user.png" alt=""/></td>
				<td><input type="text" name="username" maxlength="12" title="Enter the username you wish to use." autocomplete="off" placeholder="Username" required>
				</td>
			</tr>
		</table>
		<hr />
		<table id="panel" cellspacing="0" cellpadding="0">
            <tr>
				<td><img src="inc/images/inputs/pass.png" alt=""/></td>
				<td><input type="password" name="password" maxlength="12" title="Enter the password you wish to use." placeholder="Password" required></td>
			</tr>
		</table>
		<hr />
		<table id="panel" cellspacing="0" cellpadding="0">
            <tr>
				<td><img src="inc/images/inputs/pass.png" alt=""/></td>
				<td><input type="password" name="cpassword" maxlength="12" title="Comfirm your password." placeholder="Comfirm Password" required></td>
			</tr>
		</table>
		<hr />
		<table id="panel" cellspacing="0" cellpadding="0">
            <tr>
				<td><img src="inc/images/inputs/email.png" alt=""/></td>
				<td><input type="text" name="email" title="Please enter your Email-Address. Example: email@address.com" placeholder="Email" required></td>
			</tr>
		</table>
		<hr />
		<table id="panel" cellspacing="0" cellpadding="0">
            <tr>
				<td><img src="inc/images/inputs/bday.png" alt=""/></td>
				<td><input id="datepicker" type="text" maxlength="10" title="Please enter the date of your birth. Example: YYYY/MM/DD" name="birth" placeholder="Birthday" required></td>
			</tr>
        </table>
        <input type="submit" value="Register" style=""name="register">
        </form>
</center>
									
</div>									
</div>
                                </div>