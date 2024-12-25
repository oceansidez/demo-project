<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>静态页模板</title>
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
        静态页模板
    </div>
    <div class="body">
        <form id="validateForm"  method="post">
            <ul id="tab" class="tab">
                <li>
                    <input type="button" value="基本信息" hidefocus />
                </li>
            </ul>
            <table class="inputTable tabContent">
            	<tr>
                    <th>模板内容</th>
                    <td>
						<textarea type="text" name="content" style="width: 100%; height: 500px; padding: 0px;">${content?html}</textarea>
                    </td>
                </tr>
                <tr>
                    <th>生成文件路径</th>
                    <td>
						${uploadUrl}
                    </td>
                </tr>
            </table>
            <div class="buttonArea">
            	<input type="submit" class="formButton submitBtn" value="生成文件" data-action="${base}/pageTemplate/generate" hidefocus />&nbsp;&nbsp;
                <input type="submit" class="formButton submitBtn" value="保存模板" data-action="${base}/pageTemplate/submit" hidefocus />&nbsp;&nbsp;
            </div>
        </form>
    </div>
</body>
</html>
