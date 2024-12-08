/** @param {jQuery} $ jQuery Object */
!function($, window, document, _undefined)
{
	/**
	 * Activates the XenForo smiley editor form
	 *
	 * @param jQuery form.SmileyEditor[data-smiley-output]
	 */
	XenForo.SmileyEditor = function($form)
	{
		var $output = $($form.data('smiley-output')),

			$url     = $form.find('input[name="image_url"]'),
			$sprite  = $form.find('input[name="sprite_mode"]'),
			$w       = $form.find('input[name="sprite_params[w]"]'),
			$h       = $form.find('input[name="sprite_params[h]"]'),
			$x       = $form.find('input[name="sprite_params[x]"]'),
			$y       = $form.find('input[name="sprite_params[y]"]');

		if (!$output.length)
		{
			console.warn('Unable to locate the smiley output element as specified by data-smiley-output on the form %o', $form);
			return;
		}

		$form.find('input').not('input[type=button]').not('input[type=submit]').bind('change', function(e)
		{
			console.log('Form interaction... width = %d', $form.find('#ctrl_sprite_paramswidth').val());

			var $url = $form.find('#ctrl_image_url')

			if ($sprite.is(':checked'))
			{
				$output.attr('src', 'styles/default/xenforo/clear.png').css(
				{
					width: $w.val(),
					height: $h.val(),
					background: 'url(' + $url.val() + ') no-repeat ' + $x.val() + 'px ' + $y.val() + 'px'
				});
			}
			else
			{
				$output.attr('src', $url.val()).css(
				{
					width: 'auto',
					height: 'auto',
					background: 'none'
				});
			}
		});
	};

	XenForo.register('form.SmileyEditor', 'XenForo.SmileyEditor');
}
(jQuery, this, document);
