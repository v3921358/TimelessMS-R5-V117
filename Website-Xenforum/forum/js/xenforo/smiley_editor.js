/*
 * XenForo smiley_editor.min.js
 * Copyright 2010-2012 XenForo Ltd.
 * Released under the XenForo License Agreement: http://xenforo.com/license-agreement
 */
(function(d){XenForo.SmileyEditor=function(a){var b=d(a.data("smiley-output"));a.find('input[name="image_url"]');var e=a.find('input[name="sprite_mode"]'),f=a.find('input[name="sprite_params[w]"]'),g=a.find('input[name="sprite_params[h]"]'),h=a.find('input[name="sprite_params[x]"]'),i=a.find('input[name="sprite_params[y]"]');b.length?a.find("input").not("input[type=button]").not("input[type=submit]").bind("change",function(){console.log("Form interaction... width = %d",a.find("#ctrl_sprite_paramswidth").val());
var c=a.find("#ctrl_image_url");e.is(":checked")?b.attr("src","styles/default/xenforo/clear.png").css({width:f.val(),height:g.val(),background:"url("+c.val()+") no-repeat "+h.val()+"px "+i.val()+"px"}):b.attr("src",c.val()).css({width:"auto",height:"auto",background:"none"})}):console.warn("Unable to locate the smiley output element as specified by data-smiley-output on the form %o",a)};XenForo.register("form.SmileyEditor","XenForo.SmileyEditor")})(jQuery,this,document);
