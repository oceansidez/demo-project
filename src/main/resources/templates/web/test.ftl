<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>测试页</title>
<link href="${base}/static/admin/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript">

$().ready( function() {

	var $loginForm = $("#loginForm");

	$("#submitBtn").click(function(){
		$loginForm.submit();
	});
	
	// 回车即提交
	document.onkeydown = function (e) { 
		var theEvent = window.event || e; 
		var code = theEvent.keyCode || theEvent.which; 
		if (code == 13) { 
			$("#submitBtn").click();
		} 
	}; 
	
});
</script>
</head>
<body>
	<form id="loginForm" action="${base}/web/submit" method="post">
	<img class="bgimg" src="${base}/static/admin/images/bg.jpg" width="100%" />
	<div class="logincontent animation" id="logincontent">
	 <div class="top">
	 	<div style="color: white; font-size: 25px; padding-top: 25px; margin:0 auto; width: 300px; text-align:center">
			测试
		</div>
	 </div>
	 <div class="con">
	  <span class="font">用户名参数名：</span>
	  <span class="input"><input id="usernameParam" name="usernameParam" value="username" placeholder="" type="text" style="width:150px" autocomplete="off"/></span><br />
	  <span class="font">用户名Value：&nbsp;</span>
	  <span class="input"><input id="username" name="username" value="15172412751@189.cn" placeholder="" type="text" style="width:150px" autocomplete="off"/></span><br />
	  <span class="font">密码参数名：</span>
	  <span class="input"><input id="passwordParam" name="passwordParam" value="password" placeholder="" type="text" style="width:150px" autocomplete="off"/></span><br />
	  <span class="font">密码Value：&nbsp;</span>
	  <span class="input"><input id="password" name="password" value="123456" placeholder="" type="text" style="width:150px" autocomplete="off"/></span><br />
	  <span class="font">登录Action地址：</span>
	  <span class="input"><input id="loginUrl" name="loginUrl" value="https://show.yeee.me/index.php?c=user&a=login" placeholder="" type="text" style="width:250px" autocomplete="off"/></span><br />
	  <span class="font">登录后跳转Url：&nbsp;&nbsp;&nbsp;</span>
	  <span class="input"><input id="loginRedirect" name="loginRedirect" value="https://show.yeee.me/#/main" placeholder="" type="text" style="width:250px" autocomplete="off"/></span><br />
	  <span class="font">Domain：</span>
	  <span class="input"><input id="loginDomain" name="loginDomain" value="show.yeee.me" placeholder="" type="text" style="width:150px" autocomplete="off"/></span><br />
	  <br />
	 </div>
	 <div class="bottombtn">
	  <div class="brnlogin" id="submitBtn">登录</div>
	 </div>
	</div>
	<div class="copy">© 个人版权所有 </div>
	</form>
</body>
</html>