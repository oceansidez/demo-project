<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${fieldBeanModel.tableDescription}列表</title>
<link href="${r'${base}'}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${r'${base}'}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${r'${base}'}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		${fieldBeanModel.tableDescription}列表&nbsp;总记录数: ${r'${pager.totalCount}'} (共${r'${pager.pageCount}'}页)
	</div>
	<div class="body">
		<form id="listForm" action="${r'${base}/'}${methodName}/list" method="get">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='${r'${base}'}/${methodName}/add'" value="添加${fieldBeanModel.tableDescription}" hidefocus />
				&nbsp;&nbsp;
				<label>查找:</label>
				&nbsp;&nbsp;
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
				<label>每页显示: </label>
				<select name="pageSize" id="pageSize">
					<option value="10"${r'<#if pager.pageSize == 10> selected</#if>'}>
						10
					</option>
					<option value="20"${r'<#if pager.pageSize == 20> selected</#if>'}>
						20
					</option>
					<option value="50"${r'<#if pager.pageSize == 50> selected</#if>'}>
						50
					</option>
					<option value="100"${r'<#if pager.pageSize == 100> selected</#if>'}>
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
                    <#list fieldList as field>
						<th>
							<a href="#" name="${field.field}" hidefocus>${field.description}</a>
						</th>
                    </#list>
						<th>
							<span>操作</span>
						</th>
					</tr>
					${r'<#list'} pager.result as ${methodName}>
						<tr>
							<td>
								<input type="checkbox" name="ids" value="${r'${'}${methodName}.id}" />
							</td>
                        <#list fieldList as field>
                            <#if field.dataType == "Date">
                            <td>
								<span title="${r'${'}${methodName}.${field.field}?string("yyyy-MM-dd HH:mm")}">${r'${'}${methodName}.${field.field}?string("yyyy-MM-dd HH:mm")}</span>
							</td>
                            <#else>
							<td>
								${r'${('}${methodName}.${field.field})!}
							</td>
                            </#if>
                        </#list>
							<td>
								<a href="${r'${base}'}/${methodName}/view/${r'${'}${methodName}.id}" title="查看">[查看]</a>
								<a href="${r'${base}'}/${methodName}/edit/${r'${'}${methodName}.id}" title="编辑">[编辑]</a>
							</td>
						</tr>
					${r'</#list>'}
				</table>
				${r'<#if (pager.result?size > 0)>'}
					<div class="pagerBar">
						<div class="delete">
							<input type="button" id="deleteButton" class="formButton" url="${r'${base}'}/${methodName}/delete" value="删 除" disabled hidefocus />
						</div>
						<div class="pager">
							${r'<#include "pager.ftl" />'}
						</div>
					<div>
				${r'<#else>'}
					<div class="noRecord">没有找到任何记录!</div>
				${r'</#if>'}
			</div>
		</form>
	</div>
</body>
</html>