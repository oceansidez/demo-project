<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Logo设置</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/uploadFile.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $tab = $("#tab");

	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
		},
		messages: {
		},
		submitHandler: function(form) {
			form.submit();
		}
	});
});

</script>
</head>
<body class="input admin">
	<div class="bar">
		Logo设置
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="/system/submit" method="post">
			<table class="inputTable tabContent">
				<tr>
					<th>
						<label class="requireField">*</label>Logo图: 
					</th>
					<td>
						<div class="uploadImgBtnBox">
							<!-- 图片展示区 -->
                    		<div id="myImgDiv" class="myDiv">
	                    		<#if !isAdd>	
	                    			<div class="theImg">
	                    				<img src="${(viewUrl)!}${(reply.imgUrl)!}" width="400px" height="200px"/>
	                				</div>
	                			</#if>	
	                		</div>	
	                		<!-- 图片上传按钮Input标签-->
							<input id="uploadImg" name="uploadImg" type="file"
							 onChange="imgFileChange(
							 'uploadImg', 'jpg,png,gif,jpeg', 2, '${base}/wechatReply/ajaxUpload',
							 'myImgDiv', 'imgUrl', 'imgContent', '|#|dataUrl|#|')" 
							 accept="image/jpg,image/png,image/gif,image/jpeg" />
							<!-- 图片隐藏Input标签（value存放图片url）-->
							<input id="imgUrl" type="hidden" value="${(reply.imgUrl)!}" />
							<!-- 图片隐藏Div标签（value存放需要填充至展示区的Html内容）-->
							<input id="imgContent" type="hidden" 
							 value="<div class='theImg'><img src='${(viewUrl)!}|#|dataUrl|#|' width='400px' height='200px'/></div>" />
						</div>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
			</div>
		</form>
	</div>
</body>
</html>