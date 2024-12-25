<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="minimal-ui,width=750,user-scalable=no,target-densitydpi=device-dpi">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="msapplication-tap-highlight" content="no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<title>公众号手动授权</title>
<link rel="stylesheet" type="text/css" href="${base}/static/web/wechat_official/css/base.css" />
<link rel="stylesheet" type="text/css" href="${base}/static/web/wechat_official/css/my.css" />
<script src="${base}/static/web/wechat_official/js/adaptive.js"></script>
<script src="${base}/static/web/wechat_official/js/jquery-3.3.1.min.js"></script>
</head>

<body>
<div class="wapcon incontent">
		<div class="my_card1 detail_card1">
			<img class="pay" src="${base}/static/web/wechat_official/images/img.jpg">
		</div>
		<div class="my_card2 detail_card2">
			<ul>
				<li class="head">
					<div>手动授权</div>
				</li>
				<li>
					<div class="use">
						您好，微信用户：${username!} 
						<p/>OpenId：${openId!}
					</div>
				</li>
				
			</ul>
		</div>
	</div>
</body>

<script>
	$(function(){
		
	});
</script>
</html>
