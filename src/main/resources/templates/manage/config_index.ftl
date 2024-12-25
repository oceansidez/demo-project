<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${propertiesFileName} 配置文件</title>
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
        ${propertiesFileName} 配置文件
    </div>
    <div class="body">
        <form id="validateForm"  method="post" >
            <ul id="tab" class="tab">
                <li>
                    <input type="button" value="基本信息" hidefocus />
                </li>
            </ul>
            <table class="inputTable tabContent">
            	<tr>
                    <th>内容</th>
                    <td>
						<textarea type="text" name="content"
						 style="font-size: 16px; width: 100%; height: 700px; padding: 2px;">${content?html}</textarea>
                    </td>
                </tr>
            </table>
            <div class="buttonArea">
            	<input type="hidden" name="propertiesFileName" value="${propertiesFileName}" />
                <input type="submit" class="formButton submitBtn" value="保  存" data-action="${base}/config/update" hidefocus />&nbsp;&nbsp;
                <span class="warnInfo"><span class="icon">&nbsp;</span>本地运行请关闭热部署，开启热部署会导致动态刷新配置文件无效！</span>
            </div>
        </form>
    </div>
</body>
</html>
