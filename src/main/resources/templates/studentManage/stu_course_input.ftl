<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>添加/编辑用户</title>
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
                    "password": {
                        <#if isAdd>
                        required: true,
                        </#if>
                        minlength: 4,
                        maxlength: 20
                    }
                },
                messages: {
                    "stuName": "请填写姓名",
                    "stuSorc": {
                        <#if isAdd>
                        required: "请填写成绩",
                        </#if>
                        minlength: "成绩必须大于等于0",
                        maxlength: "成绩必须小于等于100"
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
    <#if isAdd>添加分数<#else>编辑分数</#if>
</div>
<div id="validateErrorContainer" class="validateErrorContainer">
    <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
    <ul></ul>
</div>
<div class="body">
    <form id="validateForm" action="<#if isAdd>${base}/stu-course/save<#else>${base}/stu-course/update</#if>" method="post">
        <#if !isAdd>
            <input type="hidden" name="id" value="${stuCourse.id}"/>
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
                    <select name="stuId" id="courseType" class="formText">
                        <option selected="selected">==请选择学生==</option>
                        <#list studentList as student>
                            <option value="${student.id}"
                               <#if student.id == stuCourse.stuId>selected="selected"</#if>>
                                ${student.name}
                            </option>
                        <#--当value时提交的为value的值，当属性为name时提交的的为外面的值-->
                        </#list>
                    </select>
                </td>
            </tr>
            <tr class="roleList">
                <th>
                    课程名称:
                </th>
                <td>
                    <select name="courseId" id="courseType" class="formText">
                        <option selected="selected">==请选择课程==</option>
                        <#list courseList as course>
                            <option value="${course.id}"
                                <#if course.id == stuCourse.courseId>selected="selected"</#if>>
                                ${course.name}
                            </option>
                        <#--当value时提交的为value的值，当属性为name时提交的的为外面的值-->
                        </#list>
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    学生成绩:
                </th>
                <td>
                    <input type="text" name="score" class="formText" value="${(stuCourse.score)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>
                    描述:
                </th>
                <td>
                    <textarea name="remark" class="formTextarea">${(stuCourse.remark)!}</textarea>
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