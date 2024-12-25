<#assign sec=JspTaglibs["/static/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${(messageForI18n("ReplyType."+type))!}列表</title>
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
			url:"${base}/wechatReply/switch/"+dataId,
			type:"post",
			dataType:"json",
			data:{},
			success:function(data){
				$.message({type: data.status, content: data.message});
				if (data.status == "success") {
					if(dataType == "on"){
						var status = button.parent().parent().find("span.red");
						status.attr("class", "green");
						status.html("是");
						
						var op = button.parent();
						op.html('<a class="switchBtn" href="javascript:void(0)" data-type="off" data-id="'+ dataId +'" title="关闭">[关闭]</a>');
					}
					else if(dataType == "off"){
						var status = button.parent().parent().find("span.green");
						status.attr("class", "red");
						status.html("否");
						
						var op = button.parent();
						op.html('<a href="${base}/wechatReply/edit/'+ dataId +'" title="编辑">[编辑]</a>' +
								'<a class="switchBtn" href="javascript:void(0)" data-type="on" data-id="'+ dataId +'" title="开启">[开启]</a>');
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
		${(messageForI18n("ReplyType."+type))!}列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="${base}/wechatReply/list?type=${type}" method="get">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='${base}/wechatReply/add?type=${type}'" value="添加" hidefocus />
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
						<a href="#"  name="contentType" hidefocus>回复类型</a>
					</th>
					<th>
						<a href="#"  name="imgUrl" hidefocus>图片</a>
					</th>
					<th>
						<a href="#"  name="title" hidefocus>标题</a>
					</th>
					<#if type?? && type == 'keyword'>
					<th>
						<a href="#"  name="keyword" hidefocus>关键字</a>
					</th>
					</#if>
					<#if type?? && type == 'click'>
					<th>
						<a href="#"  name="keyword" hidefocus>菜单Key值</a>
					</th>
					</#if>
					<th>
						<a href="#"  name="content" hidefocus>回复内容</a>
					</th>
					<th>
						<a href="#"  name="isOpen" hidefocus>是否开启</a>
					</th>
					<th>
						<a href="#"  name="createDate" hidefocus>创建时间</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as reply>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${reply.id}" />
						</td>
						<td>
							${(messageForI18n("ContentType."+reply.contentType))!}
						</td>
						<td>
							<#if reply.imgUrl?? >
								<div style="margin:10px">
									<a href="${viewUrl}${(reply.imgUrl)!}" target="_blank">
										<img src="${viewUrl}${(reply.imgUrl)!}" width="100px" height="70px">
									</a>
								</div>
							<#else>
								<div style="width:100px;height:70px;margin:10px;
											border:silver solid 1px;line-height: 70px;text-align: center">
									<span>暂无图片</span>
								</div>
							</#if>
						</td>
						<#if type?? && type == 'keyword'>
						<td>
							${(reply.keyword)!}
						</td>
						</#if>
						<#if type?? && type == 'click'>
						<td>
							${(reply.keyword)!}
						</td>
						</#if>
						<td>
							${(reply.title)!'暂无'}
						</td>
						<td>
							${(reply.content)!'暂无'}
						</td>
						<td>
							<#if reply.isOpen>
								<span class="green">是</span>
							<#else>
								<span class="red">否</span>
							</#if>
						</td>
						<td>
							<span title="${reply.createDate?string("yyyy-MM-dd HH:mm")}">
								${reply.createDate?string("yyyy-MM-dd HH:mm")}
							</span>
						</td>
						<td>
							<#if reply.isOpen>
								<a class="switchBtn" href="javascript:void(0)" data-type="off" data-id="${reply.id}" title="关闭">[关闭]</a>
							<#else>
								<a href="${base}/wechatReply/edit/${reply.id}" title="编辑">[编辑]</a>
								<a class="switchBtn" href="javascript:void(0)" data-type="on" data-id="${reply.id}" title="开启">[开启]</a>
							</#if>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="${base}/wechatReply/delete" value="删 除" disabled hidefocus />
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