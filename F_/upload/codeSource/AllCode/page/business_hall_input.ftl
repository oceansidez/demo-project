<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>添加/编辑t_business_hall</title>
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
                    "address": "required",
                    "areaCode": "required",
                    "code": "required",
                    "gpsLat": "required",
                    "gpsLng": "required",
                    "name": "required",
                    "phone": "required",
                    "adminName": "required",
                    "adminPhone": "required",
                    "areaId": "required",
                    "hallId": "required",
                    "hallCode": "required",
                    "hallName": "required",
                    "installWorkerCount": "required",
                    "isEnable": "required",
                },
                messages: {
                    "address": "请填写厅店地址",
                    "areaCode": "请填写区局编码",
                    "code": "请填写厅店编码",
                    "gpsLat": "请填写gps",
                    "gpsLng": "请填写gps",
                    "name": "请填写厅店名称",
                    "phone": "请填写厅店联系电话",
                    "adminName": "请填写管理员名称",
                    "adminPhone": "请填写管理员电话",
                    "areaId": "请填写所属地区id",
                    "hallId": "请填写所属区局id",
                    "hallCode": "请填写所属区局code",
                    "hallName": "请填写所属区局名称",
                    "installWorkerCount": "请填写厅店内装维人员数量",
                    "isEnable": "请填写",
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
    <#if isAdd>添加t_business_hall<#else>编辑t_business_hall</#if>
</div>
<div id="validateErrorContainer" class="validateErrorContainer">
    <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
    <ul></ul>
</div>
<div class="body">
    <form id="validateForm" action="<#if isAdd>${base}/businessHall/save<#else>${base}/businessHall/update</#if>"
          method="post">
        <#if !isAdd>
            <input type="hidden" name="id" value="${businessHall.id}"/>
        </#if>
        <input type="hidden" name="type" value="businessHall"/>
        <ul id="tab" class="tab">
            <li>
                <input type="button" value="基本信息" hidefocus/>
            </li>
        </ul>
        <table class="inputTable tabContent">
            <tr>
                <th>厅店地址:</th>
                <td>
                    <input type="text" name="address" class="formText" value="${(businessHall.address)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>区局编码:</th>
                <td>
                    <input type="text" name="areaCode" class="formText" value="${(businessHall.areaCode)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>厅店编码:</th>
                <td>
                    <input type="text" name="code" class="formText" value="${(businessHall.code)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>gps:</th>
                <td>
                    <input type="text" name="gpsLat" class="formText" value="${(businessHall.gpsLat)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>gps:</th>
                <td>
                    <input type="text" name="gpsLng" class="formText" value="${(businessHall.gpsLng)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>厅店名称:</th>
                <td>
                    <input type="text" name="name" class="formText" value="${(businessHall.name)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>厅店联系电话:</th>
                <td>
                    <input type="text" name="phone" class="formText" value="${(businessHall.phone)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>管理员名称:</th>
                <td>
                    <input type="text" name="adminName" class="formText" value="${(businessHall.adminName)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>管理员电话:</th>
                <td>
                    <input type="text" name="adminPhone" class="formText" value="${(businessHall.adminPhone)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>所属地区id:</th>
                <td>
                    <input type="text" name="areaId" class="formText" value="${(businessHall.areaId)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>所属区局id:</th>
                <td>
                    <input type="text" name="hallId" class="formText" value="${(businessHall.hallId)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>所属区局code:</th>
                <td>
                    <input type="text" name="hallCode" class="formText" value="${(businessHall.hallCode)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>所属区局名称:</th>
                <td>
                    <input type="text" name="hallName" class="formText" value="${(businessHall.hallName)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>厅店内装维人员数量:</th>
                <td>
                    <input type="text" name="installWorkerCount" class="formText"
                           value="${(businessHall.installWorkerCount)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>:</th>
                <td>
                    <label>
                        <input type="radio" name="isEnable"
                               <#if isAdd || (businessHall?? && businessHall.isEnable == true)>checked</#if>
                               value="true"/> 是
                    </label>
                    <label>
                        <input type="radio" name="isEnable"
                               <#if businessHall?? && businessHall.isEnable == false>checked</#if> value="false"/> 否
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
