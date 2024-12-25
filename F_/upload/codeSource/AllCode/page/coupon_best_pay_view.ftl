<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>查看t_coupon_best_pay</title>
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
    查看t_coupon_best_pay
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
                    ${(couponBestPay.isDelete)!}
                </td>
            </tr>
            <tr>
                <th>
                    卡券主键id:
                </th>
                <td>
                    ${(couponBestPay.couponId)!}
                </td>
            </tr>
            <tr>
                <th>
                    翼支付卡券的支付券号:
                </th>
                <td>
                    ${(couponBestPay.bestPayNo)!}
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