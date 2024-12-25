<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>查看t_coupon</title>
    <link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
    <script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
    <script type="text/javascript">
        $().ready(function () {

            var $tab = $("#tab");

            // Tab效果
            $tab.tabs(".tabContent", {
                tabs: "input"
            });

        });
    </script>
</head>
<body class="input admin">
<div class="bar">
    查看t_coupon
</div>
<div id="validateErrorContainer" class="validateErrorContainer">
    <div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
    <ul></ul>
</div>
<div class="body">
    <ul id="tab" class="tab">
        <li>
            <input type="button" value="基本信息" hidefocus/>
        </li>
    </ul>
    <div class="inputTable_outer">
        <table class="inputTable tabContent">
            <tr>
                <th>
                    删除 1.yes 0.no:
                </th>
                <td>
                    ${(coupon.isDelete)!}
                </td>
            </tr>
            <tr>
                <th>
                    卡券名称:
                </th>
                <td>
                    ${(coupon.couponName)!}
                </td>
            </tr>
            <tr>
                <th>
                    卡券logo:
                </th>
                <td>
                    ${(coupon.couponLogoPath)!}
                </td>
            </tr>
            <tr>
                <th>
                    创建人 id:
                </th>
                <td>
                    ${(coupon.creatorId)!}
                </td>
            </tr>
            <tr>
                <th>
                    创建人 name:
                </th>
                <td>
                    ${(coupon.creatorName)!}
                </td>
            </tr>
            <tr>
                <th>
                    创建人 type 例如：管理员创建，商户创建:
                </th>
                <td>
                    ${(coupon.creatorType)!}
                </td>
            </tr>
            <tr>
                <th>
                    发券时间-起始时间:
                </th>
                <td>
                    <span title="${coupon.couponIssueStartDate?string("yyyy-MM-dd HH:mm")}">${coupon.couponIssueStartDate?string("yyyy-MM-dd HH:mm")}</span>
                </td>
            </tr>
            <tr>
                <th>
                    发券时间-终止时间:
                </th>
                <td>
                    <span title="${coupon.couponIssueEndDate?string("yyyy-MM-dd HH:mm")}">${coupon.couponIssueEndDate?string("yyyy-MM-dd HH:mm")}</span>
                </td>
            </tr>
            <tr>
                <th>
                    卡券类型 1.满减券 2.折扣券 3.预约券 4.代金券 5.满反券 6.翼支付券:
                </th>
                <td>
                    ${(coupon.couponType)!}
                </td>
            </tr>
            <tr>
                <th>
                    卡券说明:
                </th>
                <td>
                    ${(coupon.couponDesc)!}
                </td>
            </tr>
            <tr>
                <th>
                    卡券使用规则:
                </th>
                <td>
                    ${(coupon.couponUseRules)!}
                </td>
            </tr>
            <tr>
                <th>
                    有效时间-在某一时间之前都是有效的 例如：2021.12.31日，表示都是有效的:
                </th>
                <td>
                    <span title="${coupon.couponEffectiveTimeDate?string("yyyy-MM-dd HH:mm")}">${coupon.couponEffectiveTimeDate?string("yyyy-MM-dd HH:mm")}</span>
                </td>
            </tr>
            <tr>
                <th>
                    领取时间-在领取后的 effective_time_day 天里面都是有效的 :
                </th>
                <td>
                    ${(coupon.couponEffectiveTimeDay)!}
                </td>
            </tr>
            <tr>
                <th>
                    领取规则 1.按有效时间 0.按领取时间:
                </th>
                <td>
                    ${(coupon.couponGetRules)!}
                </td>
            </tr>
            <tr>
                <th>
                    卡券价值金额:
                </th>
                <td>
                    ${(coupon.couponAmount)!}
                </td>
            </tr>
            <tr>
                <th>
                    购买卡券需要的金额:
                </th>
                <td>
                    ${(coupon.couponBuyAmount)!}
                </td>
            </tr>
            <tr>
                <th>
                    是否上架 1.yes 0.no:
                </th>
                <td>
                    ${(coupon.enabled)!}
                </td>
            </tr>
            <tr>
                <th>
                    总库存:
                </th>
                <td>
                    ${(coupon.totalStock)!}
                </td>
            </tr>
            <tr>
                <th>
                    总使用(核销):
                </th>
                <td>
                    ${(coupon.totalUse)!}
                </td>
            </tr>
            <tr>
                <th>
                    最后一次库存数量:
                </th>
                <td>
                    ${(coupon.saveQuantity)!}
                </td>
            </tr>
            <tr>
                <th>
                    是否已提醒库存预警:
                </th>
                <td>
                    ${(coupon.isInformStore)!}
                </td>
            </tr>
            <tr>
                <th>
                    审核人id:
                </th>
                <td>
                    ${(coupon.checkId)!}
                </td>
            </tr>
            <tr>
                <th>
                    审核人 name:
                </th>
                <td>
                    ${(coupon.checkName)!}
                </td>
            </tr>
            <tr>
                <th>
                    审核状态 1.未审核 2.审核通过 3.未通过:
                </th>
                <td>
                    ${(coupon.checkStatus)!}
                </td>
            </tr>
            <tr>
                <th>
                    审核时间:
                </th>
                <td>
                    <span title="${coupon.checkDate?string("yyyy-MM-dd HH:mm")}">${coupon.checkDate?string("yyyy-MM-dd HH:mm")}</span>
                </td>
            </tr>
            <tr>
                <th>
                    审核通过时的总库存:
                </th>
                <td>
                    ${(coupon.lastCheckTotalQuantity)!}
                </td>
            </tr>
            <tr>
                <th>
                    是否模板 1.yes 0.no:
                </th>
                <td>
                    ${(coupon.isTemplate)!}
                </td>
            </tr>
        </table>
        <div class="buttonArea">
            <input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回"
                   hidefocus/>
        </div>
    </div>
</div>
</body>
</html>