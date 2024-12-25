<#assign sec=JspTaglibs["/static/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${manageName}</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/header_tab.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<#if isUseIframe>
<script type="text/javascript" src="${base}/static/admin/js/header.js"></script>
</#if>
</head>
<body style="position: relative;">
	<div class="body page-top w2">
		<#if isUseIframe>
		<div class="topMenu">
			<div class="L_arrow"></div>
			<ul class="top_left">
				 <li class="cur" name="index" box="${base}/page/index"><span class="index_icon"><!-- 首页 --></span></li>
			</ul>
			<div class="R_arrow"></div>
		</div>
		<div class="dropDown">
			<div class="dropDown_icon"></div>
			<ul>
				<li class="itemCloseBtn" close-type="cur">关闭当前标签页</li>
				<li class="itemCloseBtn" close-type="other">关闭其他标签页</li>
				<li class="itemCloseBtn" close-type="all">关闭全部标签页</li>
			</ul>
		</div>
		<#else>
		<ul class="top_left"></ul>
		</#if>
		<div class="top_right">
			
			<span class='username'><b><@sec.authentication property="name" /></b>，欢迎您</span>
			<a class="exit cursor" href="${base}/login/logout">退出</a>
		</div>
	</div>
	<#if isUseIframe>
		<div box="${base}/page/index" class="mainBox showTheBox">
			<iframe id="mainFrame" name="mainFrame" src="${base}/page/index" frameborder="0" width="100%" height="100%" />
		</div>
	</#if>
</body>
</html>