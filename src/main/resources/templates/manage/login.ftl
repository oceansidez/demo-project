<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${manageName}登录</title>
<link href="${base}/static/admin/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/common_util/cryptojs/core.js"></script>
<script type="text/javascript" src="${base}/static/common_util/cryptojs/enc-base64.js"></script>
<script type="text/javascript" src="${base}/static/common_util/cryptojs/md5.js"></script>
<script type="text/javascript" src="${base}/static/common_util/cryptojs/cipher-core.js"></script>
<script type="text/javascript" src="${base}/static/common_util/cryptojs/aes.js"></script>
<script type="text/javascript">
function encryptName(username, key, iv) {
	var encrypted = CryptoJS.AES.encrypt(username, key,
			{iv: iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.Pkcs7}); 
	return encrypted;
}

function encryptPasswd(passwd) {
	var encrypted = CryptoJS.MD5(passwd);
	return encrypted;
}

$().ready( function() {

	var $loginForm = $("#loginForm");
	var $username = $("#username");
	var $password = $("#password");

	// 错误提示
	<#if error??>
		$.message({type: "error", content: "${error}"});
	</#if>

	$("#submitBtn").click(function(){
		if ($username.val() == "") {
			$.message({type: "warn", content: "请输入您的用户名!"});
			return false;
		}
		if ($password.val() == "") {
			$.message({type: "warn", content: "请输入您的密码!"});
			return false;
		}
		
		var now = new Date();
		now.setSeconds(0);
		
		var key = CryptoJS.enc.Utf8.parse("${adminKey}"); 
		var iv  = CryptoJS.enc.Utf8.parse("000" + Date.parse(now).toString()); 
		
		$("#username").attr("disabled","disabled");
		$("#username").css("background-color","silver");
		$("#hiddenUsername").val(encryptName($("#username").val(),key,iv));
		
		$("#password").attr("disabled","disabled");
		$("#password").css("background-color","silver");
		$("#hiddenPasswd").val(encryptPasswd($("#password").val()));
	
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
	<script type="text/javascript">

		// 登录页面若在框架内，则跳出框架
		if (self != top) {
			top.location = self.location;
		};

	</script>
	<form id="loginForm" action="${base}/login_check" method="post">
	<input type="hidden" id="hiddenUsername" name="hiddenUsername"/>
	<input type="hidden" id="hiddenPasswd" name="hiddenPasswd"/>
	<img class="bgimg" src="${base}/static/admin/images/bg.jpg" width="100%" />
	<div class="logincontent animation" id="logincontent">
	 <div class="top">
	 	<div style="color: white; font-size: 25px; padding-top: 25px; margin:0 auto; width: 300px; text-align:center">
			${manageName}
		</div>
	 </div>
	 <div class="con">
	  <span class="font">用户名：</span>
	  <span class="input"><input id="username" name="username" placeholder="请输入用户名" type="text" autocomplete="off"/></span><br />
	  <span class="font">密&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
	  <span class="input"><input id="password" name="password" placeholder="请输入密码" type="password" autocomplete="off"/></span><br />
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