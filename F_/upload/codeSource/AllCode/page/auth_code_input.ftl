<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>添加/编辑t_auth_code</title>
    <link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/static/common/js/jquery.tools.js"></script>
    <script type="text/javascript" src="${base}/static/common/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${base}/static/common/js/jquery.validate.methods.js"></script>
    <script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
    <script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
    <script type="text/javascript">
        $().ready(function () {

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
                    "mobile": "required",
                    "authCode": "required",
                    "sendTime": "required",
                    "isEnabled": "required",
                },
                messages: {
                    "mobile": "请填写",
                    "authCode": "请填写",
                    "sendTime": "请填写",
                    "isEnabled": "请填写",
                },
                submitHandler: function (form) {
                    //$(form).find(":submit").attr("disabled", true);
                    form.submit();
                }
            });

        });
    </script>
</head>
<body class="input admin">
<div class="bar">
    <#if isAdd>添加t_auth_code<#else>编辑t_auth_code</#if>
</div>
<div id="validateErrorContainer" class="validateErrorContainer">
    <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
    <ul></ul>
</div>
<div class="body">
    <form id="validateForm" action="<#if isAdd>${base}/authCode/save<#else>${base}/authCode/update</#if>" method="post">
        <#if !isAdd>
            <input type="hidden" name="id" value="${authCode.id}"/>
        </#if>
        <input type="hidden" name="type" value="authCode"/>
        <ul id="tab" class="tab">
            <li>
                <input type="button" value="基本信息" hidefocus/>
            </li>
        </ul>
        <table class="inputTable tabContent">
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="mobile" class="formText" value="${(authCode.mobile)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="authCode" class="formText" value="${(authCode.authCode)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="sendTime" class="formText" value="${(authCode.sendTime)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <label>
                        <input type="radio" name="isEnabled"
                               <#if isAdd || (authCode?? && authCode.isEnabled == true)>checked</#if> value="true"/> 是
                    </label>
                    <label>
                        <input type="radio" name="isEnabled"
                               <#if authCode?? && authCode.isEnabled == false>checked</#if> value="false"/> 否
                    </label>
                </td>
            </tr>
        </table>
        <div class="buttonArea">
            <input type="submit" class="formButton" value="确  定" hidefocus/>&nbsp;&nbsp;
            <input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回"
                   hidefocus/>
        </div>
    </form>
</div>
</body>
</html>
