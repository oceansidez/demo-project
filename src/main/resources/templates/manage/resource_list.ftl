<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>资源列表</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		资源列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="${base}/resource/list" method="get">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='${base}/resource/add'" value="添加资源" hidefocus />
				<input type="button" class="formButton" onclick="location.href='${base}/resource/refresh'" value="权限刷新" hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				&nbsp;&nbsp;
				资源路径：
				<input type="text" name="searchMap['path']" 
				value="<#if pager.searchMap?? && pager.searchMap['path']??>${pager.searchMap['path']!}</#if>" />
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
			<div class="listTable_outer">
				<table id="listTable" class="listTable">
					<tr class="noHover">
						<th class="check">
							<input type="checkbox" class="allCheck" />
						</th>
						<th>
							<a href="#" name="name" hidefocus>资源名称</a>
						</th>
						<th>
							<a href="#" name="path" hidefocus>资源路径</a>
						</th>
						<th>
							<a href="#" name="allowType" hidefocus>拦截类型</a>
						</th>
						<th>
							<a href="#" name="authorityListStore" hidefocus>拦截权限</a>
						</th>
						<th>
							<span>操作</span>
						</th>
					</tr>
					<#list pager.result as resource>
						<tr>
							<td>
								<input type="checkbox" name="ids" value="${resource.id}" />
							</td>
							<td>
								${(resource.name)!}
							</td>
							<td>
								${(resource.path)!}
							</td>
							<td>
								<#if resource.allowType == 'permit'>
									<span class="green">${(messageForI18n("AllowType."+resource.allowType))!}</span>
								<#elseif resource.allowType == 'intercept'>
									<span class="red"> ${(messageForI18n("AllowType."+resource.allowType))!} </span>
								<#else>
									<span style="color:#FF9900"> ${(messageForI18n("AllowType."+resource.allowType))!} </span>
								</#if>
							</td>
							<td>
								${(resource.authorityListStore)!}
							</td>
							<td>
								<a href="${base}/resource/edit/${resource.id}" title="编辑">[编辑]</a>
							</td>
						</tr>
					</#list>
				</table>
				<#if (pager.result?size > 0)>
					<div class="pagerBar">
						<div class="delete">
							<input type="button" id="deleteButton" class="formButton" url="${base}/resource/delete" value="删 除" disabled hidefocus />
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