<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑${(messageForI18n("ReplyType."+type))!}</title>
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

// 监控回复类型的值
function changeContentType(type){
	if(type=="graphics"){
		$("#tr1")[0].style.display="";
		$("#tr2")[0].style.display="";
		$("#tr3")[0].style.display="";
		$("#tr4")[0].style.display="";
	}else if(type=="text"){
		$("#tr1")[0].style.display="none";
		$("#tr2")[0].style.display="none";
		$("#tr3")[0].style.display="";
		$("#tr4")[0].style.display="none";
	}else if(type=="image"){
		$("#tr1")[0].style.display="none";
		$("#tr2")[0].style.display="";
		$("#tr3")[0].style.display="none";
		$("#tr4")[0].style.display="none";
	}
}

</script>
</head>
<body class="input admin">
	<div class="bar">
		<#if isAdd>添加${(messageForI18n("ReplyType."+type))!}<#else>编辑${(messageForI18n("ReplyType."+type))!}</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAdd>${base}/wechatReply/save<#else>${base}/wechatReply/update</#if>" method="post">
			<#if !isAdd>
			<input type="hidden" name="id" value="${reply.id}" />
			</#if>
			<input type="hidden" name="type" value="${type}" />
			<table class="inputTable tabContent">
				<tr>
					<th>
						<label class="requireField">*</label>回复类型: 
					</th>
					<td>
						<#list contentTypeList as contentType>
							<label>
								<input type="radio" name="contentType" onclick="changeContentType('${contentType}')"
								 <#if (reply?? && reply.contentType==contentType) || (isAdd && contentType_index==0)>checked</#if>
								 value="${contentType}" /> ${messageForI18n("ContentType."+contentType)}
							</label>	
						</#list>
					</td>
				</tr>
				<#if type?? && type=='keyword'>
				<tr>
					<th>
						<label class="requireField">*</label>关键字: 
					</th>
					<td>
						<input type="text" name="keyword" class="formText" value="${(reply.keyword)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				</#if>
				<#if type?? && type=='click'>
				<tr>
					<th>
						<label class="requireField">*</label>菜单Key值: 
					</th>
					<td>
						<input type="text" name="keyword" class="formText" value="${(reply.keyword)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				</#if>
				<tr <#if reply?? && (reply.contentType=='text' || reply.contentType=='image')>
					style="display:none"</#if> id="tr1">
					<th>
						<label class="requireField">*</label>标题: 
					</th>
					<td>
						<input type="text" name="title" class="formText" value="${(reply.title)!}" />
					</td>
				</tr>
				<tr <#if reply?? && reply.contentType=='text'>
					style="display:none"</#if> id="tr2">
					<th>
						<label class="requireField">*</label>图片: 
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
				<tr <#if reply?? && reply.contentType=='image'>
					style="display:none"</#if> id="tr3">
					<th>
						<label class="requireField">*</label>内容: 
					</th>
					<td>
						<textarea type="text" name="content" class="formTextarea">${(reply.content)!}</textarea>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr <#if reply?? && (reply.contentType=='text' || reply.contentType=='image')>
					style="display:none"</#if> id="tr4">
					<th>
						<label class="requireField">*</label>链接地址: 
					</th>
					<td>
						<input type="text" name="url" class="formText" value="${(reply.url)!}" />
					</td>
				</tr>
				<tr>
					<th>
						<label class="requireField">*</label>状态: 
					</th>
					<td>
						<label>
							<input type="radio" name="isOpen" <#if isAdd || (reply?? && reply.isOpen == false)>checked</#if> value="false" /> 关闭
						</label>
						<label>
							<input type="radio" name="isOpen" <#if reply?? && reply.isOpen == true>checked</#if> value="true" /> 开启
						</label>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>