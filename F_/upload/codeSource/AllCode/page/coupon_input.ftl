<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>添加/编辑t_coupon</title>
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
                    "isDelete": "required",
                    "couponName": "required",
                    "couponLogoPath": "required",
                    "creatorId": "required",
                    "creatorName": "required",
                    "creatorType": "required",
                    "couponIssueStartDate": "required",
                    "couponIssueEndDate": "required",
                    "couponType": "required",
                    "couponDesc": "required",
                    "couponUseRules": "required",
                    "couponEffectiveTimeDate": "required",
                    "couponEffectiveTimeDay": "required",
                    "couponGetRules": "required",
                    "couponAmount": "required",
                    "couponBuyAmount": "required",
                    "enabled": "required",
                    "totalStock": "required",
                    "totalUse": "required",
                    "saveQuantity": "required",
                    "isInformStore": "required",
                    "checkId": "required",
                    "checkName": "required",
                    "checkStatus": "required",
                    "checkDate": "required",
                    "lastCheckTotalQuantity": "required",
                    "isTemplate": "required",
                },
                messages: {
                    "isDelete": "请填写删除 1.yes 0.no",
                    "couponName": "请填写卡券名称",
                    "couponLogoPath": "请填写卡券logo",
                    "creatorId": "请填写创建人 id",
                    "creatorName": "请填写创建人 name",
                    "creatorType": "请填写创建人 type 例如：管理员创建，商户创建",
                    "couponIssueStartDate": "请填写发券时间-起始时间",
                    "couponIssueEndDate": "请填写发券时间-终止时间",
                    "couponType": "请填写卡券类型 1.满减券 2.折扣券 3.预约券 4.代金券 5.满反券 6.翼支付券",
                    "couponDesc": "请填写卡券说明",
                    "couponUseRules": "请填写卡券使用规则",
                    "couponEffectiveTimeDate": "请填写有效时间-在某一时间之前都是有效的 例如：2021.12.31日，表示都是有效的",
                    "couponEffectiveTimeDay": "请填写领取时间-在领取后的 effective_time_day 天里面都是有效的 ",
                    "couponGetRules": "请填写领取规则 1.按有效时间 0.按领取时间",
                    "couponAmount": "请填写卡券价值金额",
                    "couponBuyAmount": "请填写购买卡券需要的金额",
                    "enabled": "请填写是否上架 1.yes 0.no",
                    "totalStock": "请填写总库存",
                    "totalUse": "请填写总使用(核销)",
                    "saveQuantity": "请填写最后一次库存数量",
                    "isInformStore": "请填写是否已提醒库存预警",
                    "checkId": "请填写审核人id",
                    "checkName": "请填写审核人 name",
                    "checkStatus": "请填写审核状态 1.未审核 2.审核通过 3.未通过",
                    "checkDate": "请填写审核时间",
                    "lastCheckTotalQuantity": "请填写审核通过时的总库存",
                    "isTemplate": "请填写是否模板 1.yes 0.no",
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
    <#if isAdd>添加t_coupon<#else>编辑t_coupon</#if>
</div>
<div id="validateErrorContainer" class="validateErrorContainer">
    <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
    <ul></ul>
</div>
<div class="body">
    <form id="validateForm" action="<#if isAdd>${base}/coupon/save<#else>${base}/coupon/update</#if>" method="post">
        <#if !isAdd>
            <input type="hidden" name="id" value="${coupon.id}"/>
        </#if>
        <input type="hidden" name="type" value="coupon"/>
        <ul id="tab" class="tab">
            <li>
                <input type="button" value="基本信息" hidefocus/>
            </li>
        </ul>
        <table class="inputTable tabContent">
            <tr>
                <th>删除 1.yes 0.no:</th>
                <td>
                    <label>
                        <input type="radio" name="isDelete"
                               <#if isAdd || (coupon?? && coupon.isDelete == true)>checked</#if> value="true"/> 是
                    </label>
                    <label>
                        <input type="radio" name="isDelete" <#if coupon?? && coupon.isDelete == false>checked</#if>
                               value="false"/> 否
                    </label>
                </td>
            </tr>
            <tr>
                <th>卡券名称:</th>
                <td>
                    <input type="text" name="couponName" class="formText" value="${(coupon.couponName)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>卡券logo:</th>
                <td>
                    <input type="text" name="couponLogoPath" class="formText" value="${(coupon.couponLogoPath)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>创建人 id:</th>
                <td>
                    <input type="text" name="creatorId" class="formText" value="${(coupon.creatorId)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>创建人 name:</th>
                <td>
                    <input type="text" name="creatorName" class="formText" value="${(coupon.creatorName)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>创建人 type 例如：管理员创建，商户创建:</th>
                <td>
                    <input type="text" name="creatorType" class="formText" value="${(coupon.creatorType)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>发券时间-起始时间:</th>
                <td>
                    <input type="text" name="couponIssueStartDate" class="formText"
                           value="${(coupon.couponIssueStartDate)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>发券时间-终止时间:</th>
                <td>
                    <input type="text" name="couponIssueEndDate" class="formText"
                           value="${(coupon.couponIssueEndDate)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>卡券类型 1.满减券 2.折扣券 3.预约券 4.代金券 5.满反券 6.翼支付券:</th>
                <td>
                    <input type="text" name="couponType" class="formText" value="${(coupon.couponType)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>卡券说明:</th>
                <td>
                    <input type="text" name="couponDesc" class="formText" value="${(coupon.couponDesc)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>卡券使用规则:</th>
                <td>
                    <input type="text" name="couponUseRules" class="formText" value="${(coupon.couponUseRules)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>有效时间-在某一时间之前都是有效的 例如：2021.12.31日，表示都是有效的:</th>
                <td>
                    <input type="text" name="couponEffectiveTimeDate" class="formText"
                           value="${(coupon.couponEffectiveTimeDate)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>领取时间-在领取后的 effective_time_day 天里面都是有效的 :</th>
                <td>
                    <input type="text" name="couponEffectiveTimeDay" class="formText"
                           value="${(coupon.couponEffectiveTimeDay)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>领取规则 1.按有效时间 0.按领取时间:</th>
                <td>
                    <label>
                        <input type="radio" name="couponGetRules"
                               <#if isAdd || (coupon?? && coupon.couponGetRules == true)>checked</#if> value="true"/> 是
                    </label>
                    <label>
                        <input type="radio" name="couponGetRules"
                               <#if coupon?? && coupon.couponGetRules == false>checked</#if> value="false"/> 否
                    </label>
                </td>
            </tr>
            <tr>
                <th>卡券价值金额:</th>
                <td>
                    <input type="text" name="couponAmount" class="formText" value="${(coupon.couponAmount)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>购买卡券需要的金额:</th>
                <td>
                    <input type="text" name="couponBuyAmount" class="formText" value="${(coupon.couponBuyAmount)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>是否上架 1.yes 0.no:</th>
                <td>
                    <label>
                        <input type="radio" name="enabled"
                               <#if isAdd || (coupon?? && coupon.enabled == true)>checked</#if> value="true"/> 是
                    </label>
                    <label>
                        <input type="radio" name="enabled" <#if coupon?? && coupon.enabled == false>checked</#if>
                               value="false"/> 否
                    </label>
                </td>
            </tr>
            <tr>
                <th>总库存:</th>
                <td>
                    <input type="text" name="totalStock" class="formText" value="${(coupon.totalStock)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>总使用(核销):</th>
                <td>
                    <input type="text" name="totalUse" class="formText" value="${(coupon.totalUse)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>最后一次库存数量:</th>
                <td>
                    <input type="text" name="saveQuantity" class="formText" value="${(coupon.saveQuantity)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>是否已提醒库存预警:</th>
                <td>
                    <label>
                        <input type="radio" name="isInformStore"
                               <#if isAdd || (coupon?? && coupon.isInformStore == true)>checked</#if> value="true"/> 是
                    </label>
                    <label>
                        <input type="radio" name="isInformStore"
                               <#if coupon?? && coupon.isInformStore == false>checked</#if> value="false"/> 否
                    </label>
                </td>
            </tr>
            <tr>
                <th>审核人id:</th>
                <td>
                    <input type="text" name="checkId" class="formText" value="${(coupon.checkId)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>审核人 name:</th>
                <td>
                    <input type="text" name="checkName" class="formText" value="${(coupon.checkName)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>审核状态 1.未审核 2.审核通过 3.未通过:</th>
                <td>
                    <input type="text" name="checkStatus" class="formText" value="${(coupon.checkStatus)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>审核时间:</th>
                <td>
                    <input type="text" name="checkDate" class="formText" value="${(coupon.checkDate)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>审核通过时的总库存:</th>
                <td>
                    <input type="text" name="lastCheckTotalQuantity" class="formText"
                           value="${(coupon.lastCheckTotalQuantity)!}"/>
                    <label class="requireField">*</label>
                </td>
            </tr>
            <tr>
                <th>是否模板 1.yes 0.no:</th>
                <td>
                    <label>
                        <input type="radio" name="isTemplate"
                               <#if isAdd || (coupon?? && coupon.isTemplate == true)>checked</#if> value="true"/> 是
                    </label>
                    <label>
                        <input type="radio" name="isTemplate" <#if coupon?? && coupon.isTemplate == false>checked</#if>
                               value="false"/> 否
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
