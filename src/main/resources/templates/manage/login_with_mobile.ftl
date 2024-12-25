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

$().ready( function() {

	var $loginForm = $("#loginForm");
	var $mobile = $("#mobile");
	var $authCode = $("#authCode");

	// 错误提示
	<#if error??>
		 $.message({type: "error", content: "${error}"});
	</#if>
	
	$("#getAuthCodeBtn").click(function(){
		 // 正则表达式判断手机号码
        var isMobile=/^(?:13\d|14\d|15\d|16\d|17\d|18\d|19\d)\d{5}(\d{3}|\*{3})$/;
        if(!isMobile.test($mobile.val())){
            $.message({type: "warn", content: "请输入正确的手机号!"});
            return;
        }
        
        var now = new Date();
		now.setSeconds(0);
		
		var key = CryptoJS.enc.Utf8.parse("${adminKey}"); 
		var iv  = CryptoJS.enc.Utf8.parse("000" + Date.parse(now).toString()); 
        
		var encrypted = encryptName($mobile.val(),key,iv);
	
        $.ajax({
			url: "${base}/login/getAuthCode",
			data: {hiddenMobile: encrypted.toString()},
			type: "GET",
			dataType: "json",
			cache: false,
			beforeSend: function() {
				
			},
			success: function(data) {
				$.message({type: data.status, content: data.message});
			}
		});
	});
	
	$("#submitBtn").click(function(){
		if ($mobile.val() == "") {
			$.message({type: "warn", content: "请输入您的手机号!"});
			return false;
		}
		if ($authCode.val() == "") {
			$.message({type: "warn", content: "请输入您的验证码!"});
			return false;
		}
		
		var now = new Date();
		now.setSeconds(0);
		
		var key = CryptoJS.enc.Utf8.parse("${adminKey}"); 
		var iv  = CryptoJS.enc.Utf8.parse("000" + Date.parse(now).toString()); 
		
		$("#mobile").attr("disabled","disabled");
		$("#mobile").css("background-color","silver");
		$("#hiddenUsername").val(encryptName($("#mobile").val(),key,iv));
		
		$("#authCode").attr("disabled","disabled");
		$("#authCode").css("background-color","silver");
		$("#hiddenPasswd").val(encryptName($("#authCode").val(),key,iv));
	
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
	var wait=120;
	var setTime;
	function time(o) {
		if(wait == 0) {
			o.style.backgroundColor = "#37bde0";
			o.style.color="#fff"
			o.removeAttribute("disabled");
			o.innerHTML = "获取验证码";
			wait = 120;
		} else {
			o.style.backgroundColor = "#bcbcbc";
			o.style.color="#fff"
			o.setAttribute("disabled", true);
			o.innerHTML = "重新发送(" + wait + ")";
			wait--;
			setTime = setTimeout(function() {
					time(o)
				},
				1000)
	}
};
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
	  <span class="font">手机号：</span>
	  <span class="input"><input id="mobile" name="mobile" placeholder="请输入手机号" type="text" autocomplete="off"/></span><br />
	  <span class="font">验证码：</span>
	  <span class="input">
	  <input id="authCode" name="authCode" placeholder="请输入验证码" type="text" autocomplete="off"
	  style="width:200px;display:inline-block;margin-right:6px"/>
	  <button class="brnlogin" id="getAuthCodeBtn" onclick="time(this)" style="width:120px; height: 44px; font-size:14px; line-height: 44px; display:inline-block;border-radius:24px; border: none;">获取验证码</button>
	  </span>
	  <br />
	  <br />
	 </div>
	 <div class="bottombtn">
	  <div class="brnlogin" id="submitBtn">登录</div>
	 </div>
	</div>
	<div class="copy"> 个人版权所有 </div>
	</form>
</body>
</html>
