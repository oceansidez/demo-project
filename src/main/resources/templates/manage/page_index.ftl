<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理中心首页</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript" src="${base}/static/common/js/clipboard.js"></script>
<script>
$().ready(function() {

	var clipboard = new ClipboardJS('#copyBtn');

	clipboard.on('success', function(e) {
	    alert("复制成功");
	    e.clearSelection();
	});
	
	clipboard.on('error', function(e) {
	     alert("复制失败");
	});
})
</script>
</head>
<body class="index">
	<div class="bar">
		欢迎使用管理平台！
	</div>
	<div class="body">
		<div class="bodyLeft">
			<table class="listTable">
				<tr>
					<th colspan="2">
						待处理任务
					</th>
				</tr>
			</table>
		</div>
		<div class="bodyRight">
			<table class="listTable">
				<tr>
					<th colspan="2">
						信息统计
					</th>
				</tr>
			</table>
			<div class="blank"></div>
		</div>
		
		
		<!-- Target -->
		<!--<textarea id="bar">请输入后点击复制按钮，可复制至剪切板</textarea>-->
		
		<!-- Trigger -->
		<!--
		<button id="copyBtn" data-clipboard-action="copy" data-clipboard-target="#bar">
		    复制文字
		</button>
		-->
	</div>
</body>
</html>