<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>用户文件列表</title>
    <link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css"/>
    <link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
    <script type="text/javascript" src="${base}/static/common/js/jquery.pager.js"></script>
    <script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
    <script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
</head>
<body class="list">
<div class="bar">
    用户文件列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
</div>
<div class="body">
    <form id="listForm" action="${base}/userFile/list" method="get">
        <div class="listBar">
            <input type="button" class="formButton" onclick="location.href='${base}/userFile/add'" value="添加用户文件"
                   hidefocus/>
            &nbsp;&nbsp;
            <label>查找:</label>
            &nbsp;&nbsp;
            <input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus/>
            &nbsp;&nbsp;
            <label>每页显示: </label>
            <select name="pageSize" id="pageSize">
                <option value="10"<#if pager.pageSize == 10> selected</#if>>
                    10
                </option>
                <option value="20"<#if pager.pageSize == 20> selected</#if>>
                    20
                </option>
                <option value="50"<#if pager.pageSize == 50> selected</#if>>
                    50
                </option>
                <option value="100"<#if pager.pageSize == 100> selected</#if>>
                    100
                </option>
            </select>
        </div>
        <div class="listTable_outer">
            <table id="listTable" class="listTable">
                <tr class="noHover">
                    <th class="check">
                        <input type="checkbox" class="allCheck"/>
                    </th>
                    <th>
                        <a href="#" name="uName" hidefocus>name</a>
                    </th>
                    <th>
                        <span>操作</span>
                    </th>
                </tr>
                <#list pager.result as userFile>
                    <tr>
                        <td>
                            <input type="checkbox" name="ids" value="${userFile.id}"/>
                        </td>
                        <td>
                            ${(userFile.uName)!}
                        </td>
                        <td>
                            <a href="${base}/userFile/view/${userFile.id}" title="查看">[查看]</a>
                            <a href="${base}/userFile/edit/${userFile.id}" title="编辑">[编辑]</a>
                        </td>
                    </tr>
                </#list>
            </table>
            <#if (pager.result?size > 0)>
            <div class="pagerBar">
                <div class="delete">
                    <input type="button" id="deleteButton" class="formButton" url="${base}/userFile/delete" value="删 除"
                           disabled hidefocus/>
                </div>
                <div class="pager">
                    <#include "pager.ftl" />
                </div>
                <div>
                    <#else>
                        <div class="noRecord">没有找到任何记录!</div>
                    </#if>
                </div>
    </form>
</div>
</body>
</html>