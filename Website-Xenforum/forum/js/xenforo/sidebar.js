/*
 * XenForo sidebar.min.js
 * Copyright 2010-2012 XenForo Ltd.
 * Released under the XenForo License Agreement: http://xenforo.com/license-agreement
 */
(function(b,c){XenForo.FixedSidebar=function(a){if(!XenForo.isTouchBrowser()){var d=b(c),e=function(){a.offset().top+a.height()>d.scrollTop()+d.height()?a.css("position","static"):a.css("position","fixed")};b(c).resize(e);e()}};XenForo.register(".FixedSidebar","XenForo.FixedSidebar")})(jQuery,this,document);
