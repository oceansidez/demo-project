<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑用户</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
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
			<#if isAdd>
				"username": {
					required: true,
					username: true,
					minlength: 2,
					maxlength: 	 20,
					remote: "${base}/admin/checkUsername"
				},
			</#if>
			"name": "required",
			"mobile": {
				required:true,
				validateMobile:true
			},
			"password": {
				<#if isAdd>
					required: true,
				</#if>
				minlength: 4,
				maxlength: 	 20
			},
			"rePassword": {
				<#if isAdd>
					required: true,
				</#if>
				equalTo: "#password"
			}
		},
		messages: {
			<#if isAdd>
				"username": {
					required: "请填写用户名",
					username: "用户名只允许包含中文、英文、数字和下划线",
					minlength: "用户名必须大于等于2",
					maxlength: 	 "用户名必须小于等于20",
					remote: "用户名已存在"
				},
			</#if>
			"name": "请填写姓名",
			"mobile": {
				required:"请填写电话",
				validateMobile:"请输入正确的手机号码"
			},
			"password": {
				<#if isAdd>
					required: "请填写密码",
				</#if>
				minlength: "密码必须大于等于4",
				maxlength: 	 "密码必须小于等于20"
			},
			"rePassword": {
				<#if isAdd>
					required: "请填写重复密码",
				</#if>
				equalTo: "两次密码输入不一致"
			}
		},
		submitHandler: function(form) {
			//$(form).find(":submit").attr("disabled", true);
			
			form.submit();
		}
	});
	
    $.validator.addMethod("validateMobile",function(value,element,params){  
        var validateMobile = /^1[3,5,7,8]\d{9}$/;  
        return this.optional(element)||(validateMobile.test(value));  
    },"电话格式错误");  
	
 	$(':checkbox[type="checkbox"]').each(function(){
    	$(this).click(function(){
    		if($(this).attr('checked')){
            	$(':checkbox[type="checkbox"]').removeAttr('checked');
                $(this).attr('checked','checked');
       	 	}
    	});
    });
});
</script>
</head>
<body class="input admin">
	<div class="bar">
		<#if isAdd>添加用户<#else>编辑用户</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAdd>${base}/admin/save<#else>${base}/admin/update</#if>" method="post">
			<#if !isAdd>
			<input type="hidden" name="id" value="${admin.id}" />
			</#if>
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						<#if isAdd>
							<input type="text" name="username" class="formText" title="用户名只允许包含中文、英文、数字和下划线" />
							<label class="requireField">*</label>
						<#else>
							${(admin.username)!}
							<input type="hidden" name="username" class="formText" value="${(admin.username)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						姓名: 
					</th>
					<td>
						<input type="text" name="name" class="formText" value="${(admin.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						手机: 
					</th>
					<td>
						<input type="text" name="mobile" class="formText" value="${(admin.mobile)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						密码: 
					</th>
					<td>
						<input type="password" name="password" id="password" class="formText" title="密码长度只允许在4-20之间" autocomplete="off" />
						<#if isAdd><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						重复密码: 
					</th>
					<td>
						<input type="password" name="rePassword" class="formText" autocomplete="off" />
						<#if isAdd><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr class="roleList">
					<th>
						管理角色: 
					</th>
					<td>
						<#list roleList as role>
							<#if role.name!='超级管理员'>
							<label>
								<input type="checkbox" name="roleIds" value="${role.id}"<#if roleSet?? && (roleSet?seq_contains(role))!> checked</#if> />${role.name}
							</label>
							</#if>
						</#list>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<input type="radio" name="isLock" <#if isAdd || (admin?? && admin.isLock == false)>checked</#if> value="false" /> 启用
						</label>
						<label>
							<input type="radio" name="isLock" <#if admin?? && admin.isLock == true>checked</#if> value="true" /> 锁定
						</label>
					</td>
				</tr>
				<#if !isAdd>
					<tr>
						<th>&nbsp;</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>如果要修改密码,请填写密码,若留空,密码将保持不变!</span>
						</td>
					</tr>
				</#if>
				
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>