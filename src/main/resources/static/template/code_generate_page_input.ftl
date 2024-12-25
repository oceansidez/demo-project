<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑${fieldBeanModel.tableDescription}</title>
<link href="${r'${base}'}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${r'${base}'}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${r'${base}'}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/admin/js/admin.js"></script>
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
        <#list fieldList as field>
            "${field.field}": "required",
        </#list>
        },
        messages: {
        <#list fieldList as field>
            "${field.field}": "请填写${field.description}",
        </#list>
        },
        submitHandler: function(form) {
            //$(form).find(":submit").attr("disabled", true);
            form.submit();
        }
    });

});
</script>
</head>
<body class="input admin">
    <div class="bar">
        ${r'<#if isAdd>'}添加${fieldBeanModel.tableDescription}${r'<#else>'}编辑${fieldBeanModel.tableDescription}${r'</#if>'}
    </div>
    <div id="validateErrorContainer" class="validateErrorContainer">
        <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
        <ul></ul>
    </div>
    <div class="body">
        <form id="validateForm" action="${r'<#if isAdd>${base}'}/${methodName}/save${r'<#else>${base}'}/${methodName}/update${r'</#if>'}" method="post">
            ${r'<#if !isAdd>'}
                <input type="hidden" name="id" value="${r'${'}${methodName}.id}" />
            ${r'</#if>'}
            <input type="hidden" name="type" value="${methodName}" />
            <ul id="tab" class="tab">
                <li>
                    <input type="button" value="基本信息" hidefocus />
                </li>
            </ul>
            <table class="inputTable tabContent">
            <#list fieldList as field>
                <tr>
                    <th>${field.description}: </th>
                    <td>
						<#if (field.dataType == 'Boolean' || field.dataType == 'boolean')>
						<label>
							<input type="radio" name="${field.field}" ${r'<#if'} isAdd || (${methodName}?? && ${methodName}.${field.field} == true)>checked${r'</#if>'} value="true" /> 是
						</label>
						<label>
							<input type="radio" name="${field.field}" ${r'<#if'} ${methodName}?? && ${methodName}.${field.field} == false>checked${r'</#if>'} value="false" /> 否
						</label>
						<#else>
						<input type="text" name="${field.field}" class="formText" value="${r'${('}${methodName}.${field.field})!}"/>
						<label class="requireField">*</label>
						</#if>
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
