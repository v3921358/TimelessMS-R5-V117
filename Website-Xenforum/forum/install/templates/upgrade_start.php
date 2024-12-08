<?php
	class_exists('XenForo_Application', false) || die('Invalid');

	$__extraData['title'] = 'Upgrade System';
?>

<?php if ($errors) { ?>
	<p class="text">The following errors occurred while verifying that your server can run XenForo:</p>
	<div class="baseHtml">
		<ul>
		<?php foreach ($errors AS $error) { ?>
			<li><?php echo $error; ?></li>
		<?php } ?>
		</ul>
	</div>
	<p class="text">Please correct these errors and try again.</p>
<?php } else { ?>
	<form action="index.php?upgrade/run" method="post" class="xenForm">
		<?php if ($fileErrors) { ?>
			<div class="errorMessage">
				There are at least <?php echo count($fileErrors); ?> file(s) that do not appear to have the expected contents.
				Reupload the XenForo files and refresh this page.
				Only continue if you are sure all files have been uploaded properly.
			</div>
		<?php } else if (!$hashesExist) { ?>
			<div class="errorMessage">
				One or more files appears to be missing. Please reupload the XenForo files and refresh this page.
				Only continue if you are sure all files have been uploaded properly.
			</div>
		<?php } ?>

		<dl class="ctrlUnit fullWidth">
			<dt></dt>
			<dd>Click the button below to begin the upgrade to <b><?php echo $targetVersion; ?></b>.</dd>
		</dl>

		<dl class="ctrlUnit submitUnit">
			<dt></dt>
			<dd><input type="submit" value="Begin Upgrade" accesskey="s" class="button primary" /></dd>
		</dl>

		<input type="hidden" name="_xfToken" value="<?php echo htmlspecialchars($visitor['csrf_token_page']); ?>" />
	</form>
<?php } ?>