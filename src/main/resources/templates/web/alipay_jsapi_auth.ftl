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
<title>支付宝JSAPI授权</title>
<body>
<script src="https://gw.alipayobjects.com/as/g/h5-lib/alipayjsapi/3.1.1/alipayjsapi.min.js"></script> 
<script> 
   ap.getAuthCode ({
      appId : '你的APPID',
      scopes : ['auth_user'],
   },  function (res){
      ap.alert(JSON.stringify(res));
   });
</script>
</body>
</html>
