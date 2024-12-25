<#assign sec=JspTaglibs["/static/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>微信用户</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {
	// 冻结/激活
	$("body").on("click", ".switchBtn", function(){
		var button = $(this);
		var dataId = $(this).attr("data-id");
		var dataType = $(this).attr("data-type");
		$.ajax({
			url:"${base}/wechatUser/switch/"+dataId,
			type:"post",
			dataType:"json",
			data:{},
			success:function(data){
				$.message({type: data.status, content: data.message});
				if (data.status == "success") {
					if(dataType == "on"){
						var status = button.parent().parent().find("span.red");
						status.attr("class", "green");
						status.html("正常");
						
						var op = button.parent();
						op.html('<a class="switchBtn" href="javascript:void(0)" data-type="off" data-id="'+ dataId +'" title="冻结">[冻结]</a>');
					}
					else if(dataType == "off"){
						var status = button.parent().parent().find("span.green");
						status.attr("class", "red");
						status.html("已锁定");
						
						var op = button.parent();
						op.html('<a class="switchBtn" href="javascript:void(0)" data-type="on" data-id="'+ dataId +'" title="激活">[激活]</a>');
					}
				}
			}
		});
	})
})
</script>
</head>
<body class="list">
	<div class="bar">
		微信用户&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="${base}/wechatUser/list" method="get">
			<div class="listBar">
				<label>查找: </label>
				&nbsp;&nbsp;
				openId：
				<input type="text" name="searchMap['openId']" 
				value="<#if pager.searchMap?? && pager.searchMap['openId']??>${pager.searchMap['openId']!}</#if>" />
				&nbsp;&nbsp;
				昵称：
				<input type="text" name="searchMap['nick']" 
				value="<#if pager.searchMap?? && pager.searchMap['nick']??>${pager.searchMap['nick']!}</#if>" />
				&nbsp;&nbsp;
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
				<label>每页显示: </label>
				<select name="pager.pageSize" id="pageSize">
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
						<a href="#"  name="openId" hidefocus>openId</a>
					</th>
					<th>
						<a href="#"  name="picUrl" hidefocus>头像</a>
					</th>
					<th>
						<a href="#"  name="nick" hidefocus>昵称</a>
					</th>
					<th>
						<a href="#"  name="sex" hidefocus>性别</a>
					</th>
					<th>
						<a href="#"  name="attentionType" hidefocus>关注状态</a>
					</th>
					<th>
						<a href="#"  name="isLock" hidefocus>状态</a>
					</th>
					<th>
						<a href="#"  name="refereeId" hidefocus>邀请人</a>
					</th>
					<th>
						<a href="#"  name="loginLastTime" hidefocus>最后登录时间</a>
					</th>
					<th>
						<a href="#"  name="createDate" hidefocus>首次登录日期</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as wechatUser>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${wechatUser.id}" />
						</td>
						<td>
							${(wechatUser.openId)!}
						</td>
						<td>
							<img src="${(wechatUser.picUrl)!}" width="30px" height="30px">
						</td>
						<td>
							${(wechatUser.nick)!}
						</td>
						<td>
							${(messageForI18n("Sex."+wechatUser.sex))!}
						</td>
						<td>
							${(messageForI18n("AttentionType."+wechatUser.attentionType))!}
						</td>
						<td>
							<#if !wechatUser.isLock>
								<span class="green">正常</span>
							<#else>
								<span class="red">已锁定</span>
							</#if>
						</td>
						<td>
							<img src="${(wechatUser.refereePicUrl)!}">${(wechatUser.refereeNick)!}
						</td>
						<td>
							<span title="${wechatUser.loginLastTime?string("yyyy-MM-dd HH:mm")}">
								${wechatUser.loginLastTime?string("yyyy-MM-dd HH:mm")}
							</span>
						</td>
						<td>
							<span title="${wechatUser.createDate?string("yyyy-MM-dd HH:mm")}">
								${wechatUser.createDate?string("yyyy-MM-dd HH:mm")}
							</span>
						</td>
						<td>
							<#if wechatUser.isLock>
								<a class="switchBtn" href="javascript:void(0)" data-type="on" data-id="${wechatUser.id}"  title="激活">[激活]</a>
							<#else>
								<a class="switchBtn" href="javascript:void(0)" data-type="off" data-id="${wechatUser.id}"  title="冻结">[冻结]</a>
							</#if>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="${base}/wechatUser/delete" value="删 除" disabled hidefocus />
					</div>
					<div class="pager">
						<#include "../manage/pager.ftl" />
					</div>
				<div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>