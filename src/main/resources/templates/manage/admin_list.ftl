<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>用户列表</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		用户列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="${base}/admin/list" method="get">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='${base}/admin/add'" value="添加用户" hidefocus />
				&nbsp;&nbsp;
				<label>查找: </label>
				&nbsp;&nbsp;
				用户名：
				<input type="text" name="searchMap['username']" 
				value="<#if pager.searchMap?? && pager.searchMap['username']??>${pager.searchMap['username']!}</#if>" />
				&nbsp;&nbsp;
				手机号
				<input type="text" name="searchMap['mobile']" 
				value="<#if pager.searchMap?? && pager.searchMap['mobile']??>${pager.searchMap['mobile']!}</#if>" />
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
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" name="username" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" name="name" hidefocus>姓名</a>
					</th>
					<th>
						<a href="#" name="mobile" hidefocus>手机号码</a>
					</th>
					<th>
						<a href="#" name="loginDate" hidefocus>最后登录时间</a>
					</th>
					<th>
						<a href="#" name="loginIp" hidefocus>最后登录IP</a>
					</th>
					<th>
						<a href="#" name="status" hidefocus>状态</a>
					</th>
					<th>
						<a href="#" name="createDate" hidefocus>创建日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as admin>
					<#if admin.username!='admin'>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${admin.id}" />
						</td>
						<td>
							${admin.username}
						</td>
						<td>
							${admin.name}
						</td>
						<td>
							${admin.mobile}
						</td>
						<td>
							<#if admin.loginDate??>
								<span title="${admin.loginDate?string("yyyy-MM-dd HH:mm")}">${admin.loginDate?string("yyyy-MM-dd HH:mm")}</span>
							<#else>
								&nbsp;
							</#if>
						</td>
						<td>
							${(admin.loginIp)!}
						</td>
						<td>
							<#if !admin.isLock>
								<span class="green">正常</span>
							<#else>
								<span class="red"> 已锁定 </span>
							</#if>
						</td>
						<td>
							<span title="${admin.createDate?string("yyyy-MM-dd HH:mm")}">${admin.createDate?string("yyyy-MM-dd HH:mm")}</span>
						</td>
						<td>
							<a href="${base}/admin/view/${admin.id}" title="查看">[查看]</a>
							<a href="${base}/admin/edit/${admin.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
					</#if>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="${base}/admin/delete" value="删 除" disabled hidefocus />
					</div>
					<div class="pager">
						<#include "pager.ftl" />
					</div>
				<div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>