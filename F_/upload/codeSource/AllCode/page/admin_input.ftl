<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>添加/编辑t_admin</title>
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
                    "username": "required",
                    "password": "required",
                    "name": "required",
                    "headImg": "required",
                    "loginDate": "required",
                    "loginIp": "required",
                    "isLock": "required",
                    "loginFailureCount": "required",
                    "lockDate": "required",
                },
                messages: {
                    "mobile": "请填写",
                    "username": "请填写",
                    "password": "请填写",
                    "name": "请填写姓名",
                    "headImg": "请填写",
                    "loginDate": "请填写",
                    "loginIp": "请填写",
                    "isLock": "请填写",
                    "loginFailureCount": "请填写",
                    "lockDate": "请填写",
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
    <#if isAdd>添加t_admin<#else>编辑t_admin</#if>
</div>
<div id="validateErrorContainer" class="validateErrorContainer">
    <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
    <ul></ul>
</div>
<div class="body">
    <form id="validateForm" action="<#if isAdd>${base}/admin/save<#else>${base}/admin/update</#if>" method="post">
        <#if !isAdd>
            <input type="hidden" name="id" value="${admin.id}"/>
        </#if>
        <input type="hidden" name="type" value="admin"/>
        <ul id="tab" class="tab">
            <li>
                <input type="button" value="基本信息" hidefocus/>
            </li>
        </ul>
        <table class="inputTable tabContent">
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="mobile" class="formText" value="${(admin.mobile)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="username" class="formText" value="${(admin.username)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="password" class="formText" value="${(admin.password)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>姓名:</th>
                <td>
                    <input type="text" name="name" class="formText" value="${(admin.name)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="headImg" class="formText" value="${(admin.headImg)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="loginDate" class="formText" value="${(admin.loginDate)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="loginIp" class="formText" value="${(admin.loginIp)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <label>
                        <input type="radio" name="isLock" <#if isAdd || (admin?? && admin.isLock == true)>checked</#if>
                               value="true"/> 是
                    </label>
                    <label>
                        <input type="radio" name="isLock" <#if admin?? && admin.isLock == false>checked</#if>
                               value="false"/> 否
                    </label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="loginFailureCount" class="formText" value="${(admin.loginFailureCount)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <input type="text" name="lockDate" class="formText" value="${(admin.lockDate)!}"/>
                    <label class="requireField">*</label>
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
