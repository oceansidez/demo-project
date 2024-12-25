<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑角色</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $allChecked = $("#validateForm .allChecked");
	
	$allChecked.click( function() {
		var $this = $(this);
		var $thisCheckbox = $this.parent().parent().find(":checkbox");
		if ($thisCheckbox.filter(":checked").length > 0) {
			$thisCheckbox.attr("checked", false);
		} else {
			$thisCheckbox.attr("checked", true);
		}
		return false;
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"name": "required"
		},
		messages: {
			"name": "请填写角色名称"
		},
		submitHandler: function(form) {
			//$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("roleAuthorityListRequired", $.validator.methods.required, "请选择管理权限");
	
	$.validator.addClassRules("roleAuthorityList", {
		roleAuthorityListRequired: true
	});
	
})
</script>
</head>
<body class="input role">
	<div class="bar">
		<#if isAdd>添加角色<#else>编辑角色</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAdd>${base}/role/save<#else>${base}/role/update</#if>" method="post">
			<#if !isAdd>
			<input type="hidden" name="id" value="${role.id}" />
			</#if>
			<table class="inputTable">
				<tr>
					<th>
						角色名称: 
					</th>
					<td>
						<#if role?? && role.name='超级管理员'>
							${(role.name)!}
							<input type="hidden" name="name" class="formText" value="${(role.name)!}" />
						<#else>
							<input type="text" name="name" class="formText" value="${(role.name)!}" />
							<label class="requireField">*</label>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						描述: 
					</th>
					<td>
						<textarea name="description" class="formTextarea">${(role.description)!}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				
				<#list authRoles as authRole>
					<tr class="authorityList">
						<th>
							<a href="#" class="allChecked" title="点击全选此类权限">${authRole.name}: </a>
						</th>
						<td>
							<#list authRole.roles as item>
							<label>
								<input type="checkbox" name="authorityList" class="roleAuthorityList" value="${item.code}"
								<#if (isAdd || (role.authorityList?? && role.authorityList?seq_contains(item.code)))> checked</#if> />${item.name}
							</label>
							</#list>
						</td>
					</tr>
				</#list>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>