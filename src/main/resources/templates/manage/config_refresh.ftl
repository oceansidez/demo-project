<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>自动刷新配置文件</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${base}/static/common_util/loading/css/animate.css" type="text/css">
<link rel="stylesheet" href="${base}/static/common_util/loading/css/global.css" type="text/css">
<link rel="stylesheet" href="${base}/static/common_util/loading/css/loading.css" type="text/css">
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/common_util/loading/js/loading.js"></script>
<script type="text/javascript">
$().ready(function() {
	$.ajax({
		url: "${base}/refresh",
		type: "POST",
		dataType: "json",
		cache: false,
		success: function(data) {
			$('body').loading({
				loadingWidth:240,
				title:'正在刷新，请稍等!',
				name:'test',
				discription:'',
				direction:'column',
				type:'origin',
				// originBg:'#71EA71',
				originDivWidth:40,
				originDivHeight:40,
				originWidth:6,
				originHeight:6,
				smallLoading:false,
				loadingMaskBg:'rgba(0,0,0,0.2)'
			});
		
			setInterval(function() {
				window.location.href = "${base}/config/index?propertiesFileName=${propertiesFileName}"
			},1000);
		}
	});
})

</script>
</head>
<body>
</body>
