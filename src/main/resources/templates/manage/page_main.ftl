<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${manageName}</title>
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
</head>
<frameset id="mainFrameset" name="mainFrameset" cols="189,7,*" frameborder="no" border="0" framespacing="0">
  <frame id="menuFrame" name="menuFrame" src="${base}/menu/mainMenu" frameborder="no" scrolling="no" noresize="noresize" />
  <frame id="middleFrame" name="middleFrame" src="${base}/page/middle" frameborder="no" scrolling="no" noresize="noresize" />
  
  <#if isUseIframe>
  	  <frame id="headerFrame" name="headerFrame" src="${base}/page/header" frameborder="no" scrolling="no" noresize="noresize" />
  <#else>
	  <frameset id="parentFrameset" rows="39,*" cols="*" frameborder="no" border="0" framespacing="0">
	  		<frame id="headerFrame" name="headerFrame" src="${base}/page/header" frameborder="no" scrolling="no" noresize="noresize" />
			<frame id="mainFrame" name="mainFrame" src="${base}/page/index" frameborder="no" noresize="noresize" />
	  </frameset>
  </#if>
</frameset>

<noframes>
	<body>
		noframes
	</body>
</noframes>
</html>