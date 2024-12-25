<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>查看t_admin</title>
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
    查看t_admin
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
                    :
                </th>
                <td>
                    ${(admin.mobile)!}
                </td>
            </tr>
            <tr>
                <th>
                    :
                </th>
                <td>
                    ${(admin.username)!}
                </td>
            </tr>
            <tr>
                <th>
                    :
                </th>
                <td>
                    ${(admin.password)!}
                </td>
            </tr>
            <tr>
                <th>
                    姓名:
                </th>
                <td>
                    ${(admin.name)!}
                </td>
            </tr>
            <tr>
                <th>
                    :
                </th>
                <td>
                    ${(admin.headImg)!}
                </td>
            </tr>
            <tr>
                <th>
                    :
                </th>
                <td>
                    <span title="${admin.loginDate?string("yyyy-MM-dd HH:mm")}">${admin.loginDate?string("yyyy-MM-dd HH:mm")}</span>
                </td>
            </tr>
            <tr>
                <th>
                    :
                </th>
                <td>
                    ${(admin.loginIp)!}
                </td>
            </tr>
            <tr>
                <th>
                    :
                </th>
                <td>
                    ${(admin.isLock)!}
                </td>
            </tr>
            <tr>
                <th>
                    :
                </th>
                <td>
                    ${(admin.loginFailureCount)!}
                </td>
            </tr>
            <tr>
                <th>
                    :
                </th>
                <td>
                    <span title="${admin.lockDate?string("yyyy-MM-dd HH:mm")}">${admin.lockDate?string("yyyy-MM-dd HH:mm")}</span>
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