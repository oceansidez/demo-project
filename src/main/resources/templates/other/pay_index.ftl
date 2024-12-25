<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>第三方支付</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {
	$(".submitBtn").click(function(){
		var action = $(this).attr("data-action");
		$("#validateForm").attr("action", action);
		$("#validateForm").submit();
	})
})

</script>
</head>
<body class="input admin">
    <div class="bar">
        第三方支付
    </div>
    <div class="body">
        <form id="validateForm" action="${base}/pay/submit" method="post">
            <ul id="tab" class="tab">
                <li>
                    <input type="button" value="基本信息" hidefocus />
                </li>
            </ul>
            <table class="inputTable tabContent">
            	<tr>
                    <th>商户订单号：</th>
                    <td>
						<input size="30" name="tradeNo" class="formText" />
						<span class="warnInfo"><span class="icon">&nbsp;</span>商户网站订单系统中唯一订单号</span>
						
                    </td>
                </tr>
                <tr>
                    <th>订单名称：</th>
                    <td>
						<input size="30" name="name" class="formText" />
						<span class="warnInfo"><span class="icon">&nbsp;</span>商户网站订单系统中的订单名称</span>
                    </td>
                </tr>
                <tr>
                    <th>付款金额：</th>
                    <td>
						<input name="fee" value="0.01" class="formText" />
						<span class="warnInfo"><span class="icon">&nbsp;</span>保留小数点后两位，单位：元</span>
                    </td>
                </tr>
                <tr>
                    <th>订单描述：</th>
                    <td>
						<textarea type="text" name="body" style="width: 400px; height: 200px; "></textarea>
                    </td>
                </tr>
                <tr>
                    <th>支付方式</th>
                    <td>
						<select class="formText" name="payType" class="formText">
          					<option value="alipayPC">支付宝PC支付</option>
          					<option value="wechatPC">微信PC支付</option>
          					<option value="bestpayPC">翼支付PC支付</option>
          					<option value="alipayWap">支付宝移动端H5支付</option>
          					<option value="wechatWap">微信移动端H5支付</option>
          					<option value="bestpayWap">翼支付移动端H5支付</option>
          				</select>
                    </td>
                </tr>
            </table>
            <div class="buttonArea">
                <input type="submit" class="formButton submitBtn" value="保  存"  hidefocus />&nbsp;&nbsp;
            </div>
        </form>
    </div>
</body>
</html>
