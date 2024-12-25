<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑资源</title>
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
			"name": "required",
            "path": "required"
		},
		messages: {
			"name": "请填写资源名称",
            "path": "请填写资源路径"
		},
		submitHandler: function(form) {
			//$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("roleAuthorityListRequired", $.validator.methods.required, "请选择拦截权限");
	
	$.validator.addClassRules("roleAuthorityList", {
		roleAuthorityListRequired: true
	});
	
	$("input[name='allowType']").change(function() {
		if($(this).val() == 'permit' || $(this).val() == 'ignore'){
			$(".allow").hide();
		}
		else if($(this).val() == 'intercept') {
			$(".allow").show();
		}
	})
	
	<#if isAdd>
        $(".allow").hide();
    <#else>
    	<#if resource.allowType == 'permit' || resource.allowType == 'ignore' >
    		$(".allow").hide();
    	<#else>
    		$(".allow").show();
    	</#if>
    </#if>
});
</script>
</head>
<body class="input admin">
    <div class="bar">
        <#if isAdd>添加资源<#else>编辑资源</#if>
    </div>
    <div id="validateErrorContainer" class="validateErrorContainer">
        <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
        <ul></ul>
    </div>
    <div class="body">
        <form id="validateForm" action="<#if isAdd>${base}/resource/save<#else>${base}/resource/update</#if>" method="post">
            <#if !isAdd>
                <input type="hidden" name="id" value="${resource.id}" />
            </#if>
            <input type="hidden" name="type" value="resource" />
            <ul id="tab" class="tab">
                <li>
                    <input type="button" value="基本信息" hidefocus />
                </li>
            </ul>
            <table class="inputTable tabContent">
                <tr>
                    <th>资源名称</th>
                    <td>
						<input type="text" name="name" class="formText" value="${(resource.name)!}"/>
						<label class="requireField">*</label>
                    </td>
                </tr>
                <tr>
                    <th>资源路径 </th>
                    <td>
						<input type="text" name="path" class="formText" value="${(resource.path)!}"/>
						<label class="requireField">*</label>
                    </td>
                </tr>
                <tr>
                    <th>拦截类型</th>
                    <td>
						<label>
							<input type="radio" name="allowType" <#if isAdd || (resource?? && resource.allowType == 'permit')>checked</#if> value="permit" /> 放行
						</label>
						<label>
							<input type="radio" name="allowType" <#if resource?? && resource.allowType == 'intercept'>checked</#if> value="intercept" /> 拦截
						</label>
						<label>
							<input type="radio" name="allowType" <#if resource?? && resource.allowType == 'ignore'>checked</#if> value="ignore" /> 忽略
						</label>
                    </td>
                </tr>
                <tr class="allow">
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
                <tr class="allow">
					<th>
						拦截权限:
					</th>
					<td>
						&nbsp;
					</td>
				</tr>
                <#list authRoles as authRole>
				<tr class="authorityList allow">
					<th>
						<a href="#" class="allChecked" title="点击全选此类权限">${authRole.name}: </a>
					</th>
					<td>
						<#list authRole.roles as item>
						<label>
							<input type="checkbox" name="authorityList" class="roleAuthorityList" value="${item.code}"
							<#if (resource?? && resource.authorityList?? && resource.authorityList?seq_contains(item.code))> checked</#if> />${item.name}
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
