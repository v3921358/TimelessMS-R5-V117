<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.1 plus MathML 2.0 plus SVG 1.1//EN' 'http://www.w3.org/2002/04/xhtml-math-svg/xhtml-math-svg.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='en'>
        <head>
		<link rel="shortcut icon" href="inc/images/favicon.ico" />
                <meta http-equiv='content-type' content='text/html; charset=utf-8' />
                <title><?php echo $servername; ?></title>
                <link href='inc/template.css' rel='stylesheet' type='text/css' />
                <style>
#donate                         {
                                        position: relative;
                                        display: block;
                                        background-image: url('inc/images/donate.png');
                                        width: 137px;
                                        height: 60px;
                                        }
#vote                           {
                                        position: relative;
                                        display: block;
                                        background-image: url('inc/images/vote.png');
                                        width: 137px;
                                        height: 60px;
                                        }
#unstuck                        {      
                                        display: block;
                                        background-image: url('inc/images/buttons/unstuck.png');
                                        width: 137px;
                                        height: 60px;
                                        }
#chat                   {      
                                        display: block;
                                        background-image: url('inc/images/buttons/chat.png');
                                        width: 137px;
                                        height: 60px;
				
                <script type='text/javascript' src='js/jquery.js'> </script>
<!--            <script type='text/javascript'>
                        $(function() {
       
                                $('#donate').mouseover(function() { $(this).css('background-image', 'url(images/buttons/'); });
                                $('#donate').mouseout(function() { $(this).css('background-image', 'url(images/buttons/donate.png)'); });
                                $('#vote').mouseover(function() { $(this).css('background-image', 'url(images/buttons/vote.png)'); });
                                $('#vote').mouseout(function() { $(this).css('background-image', 'url(images/buttons/vote.png)'); });
                                $('#chat').mouseout(function() { $(this).css('background-image', 'url(images/buttons/chat.png)'); });
                                $('#chat').mouseout(function() { $(this).css('background-image', 'url(images/buttons/chat.png)'); });
                        });
                </script>-->
        </style></head>
        <body>
                <div id='wrapper'>
                        <div id='header_wrapper'>
                                <a href='?page=home'><img src='inc/images/logo.png' />
                                <a href='?page=home'><img src='inc/images/navigation/home.png' /></a>
								<a href='?page=register'><img src='inc/images/navigation/register.png' /></a>
                                <a href='?page=downloads'><img src='inc/images/navigation/download.png' /></a>
                                <a href='<?php echo $forums; ?>'><img src='inc/images/navigation/community.png' /></a>
                                <a href='?page=rankings'><img src='inc/images/navigation/rankings.png' /></a>
                        </div>
                        <div id='top_wrapper'>
                                <div id='stats'>
								<img src='inc/images/status/online.png' /><br /><center>
														<?php
							$countonline 	= mysql_query("SELECT * FROM accounts where loggedin = 2");
							$online 		= mysql_num_rows($countonline); 
						?>
								<font color='green'>Players: <?php echo $online ?></center></font></div>
                                <div id='noticeupdates'><?php echo $notice; ?></div>
                        </div>