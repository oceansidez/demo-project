<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>查看t_business_hall</title>
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
    查看t_business_hall
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
                    厅店地址:
                </th>
                <td>
                    ${(businessHall.address)!}
                </td>
            </tr>
            <tr>
                <th>
                    区局编码:
                </th>
                <td>
                    ${(businessHall.areaCode)!}
                </td>
            </tr>
            <tr>
                <th>
                    厅店编码:
                </th>
                <td>
                    ${(businessHall.code)!}
                </td>
            </tr>
            <tr>
                <th>
                    gps:
                </th>
                <td>
                    ${(businessHall.gpsLat)!}
                </td>
            </tr>
            <tr>
                <th>
                    gps:
                </th>
                <td>
                    ${(businessHall.gpsLng)!}
                </td>
            </tr>
            <tr>
                <th>
                    厅店名称:
                </th>
                <td>
                    ${(businessHall.name)!}
                </td>
            </tr>
            <tr>
                <th>
                    厅店联系电话:
                </th>
                <td>
                    ${(businessHall.phone)!}
                </td>
            </tr>
            <tr>
                <th>
                    管理员名称:
                </th>
                <td>
                    ${(businessHall.adminName)!}
                </td>
            </tr>
            <tr>
                <th>
                    管理员电话:
                </th>
                <td>
                    ${(businessHall.adminPhone)!}
                </td>
            </tr>
            <tr>
                <th>
                    所属地区id:
                </th>
                <td>
                    ${(businessHall.areaId)!}
                </td>
            </tr>
            <tr>
                <th>
                    所属区局id:
                </th>
                <td>
                    ${(businessHall.hallId)!}
                </td>
            </tr>
            <tr>
                <th>
                    所属区局code:
                </th>
                <td>
                    ${(businessHall.hallCode)!}
                </td>
            </tr>
            <tr>
                <th>
                    所属区局名称:
                </th>
                <td>
                    ${(businessHall.hallName)!}
                </td>
            </tr>
            <tr>
                <th>
                    厅店内装维人员数量:
                </th>
                <td>
                    ${(businessHall.installWorkerCount)!}
                </td>
            </tr>
            <tr>
                <th>
                    :
                </th>
                <td>
                    ${(businessHall.isEnable)!}
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