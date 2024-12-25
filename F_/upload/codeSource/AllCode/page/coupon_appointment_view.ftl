<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>查看t_coupon_appointment</title>
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
    查看t_coupon_appointment
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
                    ${(couponAppointment.isDelete)!}
                </td>
            </tr>
            <tr>
                <th>
                    卡券主键:
                </th>
                <td>
                    ${(couponAppointment.couponId)!}
                </td>
            </tr>
            <tr>
                <th>
                    星期:
                </th>
                <td>
                    ${(couponAppointment.week)!}
                </td>
            </tr>
            <tr>
                <th>
                    开始时间:
                </th>
                <td>
                    <span title="${couponAppointment.beginTime?string("yyyy-MM-dd HH:mm")}">${couponAppointment.beginTime?string("yyyy-MM-dd HH:mm")}</span>
                </td>
            </tr>
            <tr>
                <th>
                    结束时间:
                </th>
                <td>
                    <span title="${couponAppointment.endTime?string("yyyy-MM-dd HH:mm")}">${couponAppointment.endTime?string("yyyy-MM-dd HH:mm")}</span>
                </td>
            </tr>
            <tr>
                <th>
                    总名额:
                </th>
                <td>
                    ${(couponAppointment.totalQuantity)!}
                </td>
            </tr>
            <tr>
                <th>
                    预约策略时间选择方式:
                </th>
                <td>
                    ${(couponAppointment.preType)!}
                </td>
            </tr>
            <tr>
                <th>
                    按天多选:
                </th>
                <td>
                    <span title="${couponAppointment.selectDay?string("yyyy-MM-dd HH:mm")}">${couponAppointment.selectDay?string("yyyy-MM-dd HH:mm")}</span>
                </td>
            </tr>
            <tr>
                <th>
                    按时间段，开始时间:
                </th>
                <td>
                    <span title="${couponAppointment.startDate?string("yyyy-MM-dd HH:mm")}">${couponAppointment.startDate?string("yyyy-MM-dd HH:mm")}</span>
                </td>
            </tr>
            <tr>
                <th>
                    按时间段，结束时间:
                </th>
                <td>
                    <span title="${couponAppointment.endDate?string("yyyy-MM-dd HH:mm")}">${couponAppointment.endDate?string("yyyy-MM-dd HH:mm")}</span>
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