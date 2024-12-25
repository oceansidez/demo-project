<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>添加/编辑学生</title>
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
                    "name": "required",
                    "age": "required"
                },
                messages: {
                    "name": "请填写姓名",
                    "age": {
                        required: "请填写年龄",
                    }
                },
                submitHandler: function (form) {
                    //$(form).find(":submit").attr("disabled", true);
                    form.submit();
                }
            });

            $(function () {
                //隐藏div
                $("#hideid").hide();
            })

        });
    </script>
</head>
<body class="input admin">
<div class="bar">
    <#if isAdd>添加学生<#else>编辑学生</#if>
</div>
<div id="validateErrorContainer" class="validateErrorContainer">
    <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
    <ul></ul>
</div>
<div class="body">
    <form id="validateForm" action="<#if isAdd>${base}/student/save<#else>${base}/student/update</#if>" method="post">
        <#if !isAdd>
            <input type="hidden" name="id" value="${student.id}"/>
        </#if>
        <ul id="tab" class="tab">
            <li>
                <input type="button" value="基本信息" hidefocus/>
            </li>
        </ul>
        <table class="inputTable tabContent">
            <tr>
                <th>
                    学生姓名:
                </th>
                <td>
                    <input type="text" name="name" class="formText" value="${(student.name)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    学生年龄:
                </th>
                <td>
                    <input type="text" name="age" class="formText" value="${(student.age)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    备注:
                </th>
                <td>
                    <input type="text" name="remark" class="formText" value="${(student.remark)!}"/>
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