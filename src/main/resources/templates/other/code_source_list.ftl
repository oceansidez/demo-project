<#assign sec=JspTaglibs["/static/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>代码生成文件列表</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/common/js/jquery-ui-1.11.0/jquery-ui.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery-ui-1.11.0/jquery-ui.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

})
</script>
</head>
<body class="list">
	<div class="bar">
		代码生成文件列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="${base}/codeSource/list" method="get">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='${base}/codeSource/add'" value="新生成代码" hidefocus />
				<input type="button" class="formButton" onclick="location.href='${base}/codeSource/db'" value="数据库读取" hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				&nbsp;&nbsp;
				<label>名称：</label>
				<input type="text" name="searchMap['adminName']" 
				value="<#if pager.searchMap?? && pager.searchMap['adminName']??>${pager.searchMap['adminName']!}</#if>" />
				&nbsp;&nbsp;
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
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
			<table id="listTable" class="listTable">
				<tr class="noHover">
                    <th class="check">
                        <input type="checkbox" class="allCheck" />
                    </th>
                    <th>
                        <a href="fileUrl" hidefocus>文件名称</a>
                    </th>
					<th>
						<a href="#" name="adminName" hidefocus>用户名称</a>
					</th>
					<th>
						<a href="createDate" hidefocus>创建日期</a>
					</th>
                    <th>
                        <a href="javascript:void(0)" hidefocus>操作</a>
                    </th>
				</tr>
				<#if pager.result??>
				<#list pager.result as codeSource>
					<tr>
					    <td>
                            <input type="checkbox" name="ids" value="${codeSource.id}" />
                        </td>
                        <td>
						    ${(codeSource.fileUrl)!}
						</td>
						<td>
							${(codeSource.adminName)!}
						</td>
						<td>
							<span title="${codeSource.createDate?string("yyyy-MM-dd HH:mm")}">${codeSource.createDate?string("yyyy-MM-dd HH:mm")}</span>
						</td>
						<td>
						    <a href="${viewUrl}/${createPath}/${codeSource.fileUrl}" title="下载">[下载]</a>
						</td>
					</tr>
				</#list>
				
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
				    <div class="delete">                
                        <input type="button" id="deleteButton" class="formButton" url="${base}/codeSource/delete" value="删 除" disabled hidefocus />
                    </div>
					<div class="pager">
						<#include "../manage/pager.ftl" />
					</div>
				<div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
			</#if>
		</form>
	</div>
</body>
</html>