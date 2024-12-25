<#assign sec=JspTaglibs["/static/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/menu.js"></script>
</head>
<body class="menu">
	<input type="hidden" id="isUseIframe" value="<#if isUseIframe>true<#else>false</#if>" />
  	<image src='${base}/static/admin/images/header-logo.jpg'/>
  	<div class="body menulist">
		<dl class="cur">
			<dt id="index" class="ico3 notop singleItem cur">
				<a target-href="${base}/page/index">我的首页</a>
				<i></i>
			</dt>
		</dl>
		<dl>
			<dt class="ico4 notop">
				微信公众号<span class="num">2</span>
				<i></i>
			</dt>
			<dd>
				<a target-href="${base}/wechatConfig/index">公众号菜单</a>
			</dd>
			<dd>
				<a target-href="${base}/wechatUser/list">微信用户</a>
			</dd>
			<dd>
				<a target-href="${base}/wechatReply/list?type=defaults">默认回复</a>
			</dd>
			<dd>
				<a target-href="${base}/wechatReply/list?type=attention">关注回复</a>
			</dd>
			<dd>
				<a target-href="${base}/wechatReply/list?type=keyword">关键字回复</a>
			</dd>
			<dd>
				<a target-href="${base}/wechatReply/list?type=click">菜单事件回复</a>
			</dd>	
		</dl>
		<dl>
			<dt class="ico4 notop">
				其他功能<span class="num">2</span>
				<i></i>
			</dt>
			<dd>
				<a target-href="${base}/codeSource/list">代码生成</a>
			</dd>
			<dd>
				<a target-href="${base}/imgCropper/index">图片裁剪</a>
			</dd>
			<dd>
				<a target-href="${base}/pay/index">第三方支付</a>
			</dd>
			<dd>
				<a target-href="${base}/pageTemplate/index">静态页模板</a>
			</dd>
			<dd>
				<a target-href="${base}/fileUpload/index">文件异步上传</a>
			</dd>
		</dl>
		<dl>
			<dt class="ico4 notop">
				配置文件<span class="num">2</span>
				<i></i>
			</dt>
			<#list configFileList as configFile>
			<dd>
				<a target-href="${base}/config/index?propertiesFileName=${configFile.realName}">
					${configFile.displayName} 配置
				</a>
			</dd>
			</#list>
		</dl>
		<@sec.authorize access="hasAnyRole('ROLE_ADMIN','ROLE_ROLE')">
		<dl>
			<dt class="ico4 notop">
				系统管理<span class="num">2</span>
				<i></i>
			</dt>
			<@sec.authorize access="hasAnyRole('ROLE_ADMIN')">
			<dd>
				<a target-href="${base}/admin/list">用户管理</a>
			</dd>
			</@sec.authorize>
			<@sec.authorize access="hasAnyRole('ROLE_ROLE')">
			<dd>
				<a target-href="${base}/role/list">角色管理</a>
			</dd>
			</@sec.authorize>
			<dd>
				<a target-href="${base}/resource/list">资源管理</a>
			</dd>
		</dl>
		</@sec.authorize>

		<dl>
			<dt class="ico4 notop">
				学生成绩管理<span class="num">2</span>
				<i></i>
			</dt>
			<dd>
				<a target-href="${base}/student/index">学生管理</a>
			</dd>
			<dd>
				<a target-href="${base}/course/index">课程管理</a>
			</dd>
			<dd>
				<a target-href="${base}/stu-course/index">成绩管理</a>
			</dd>
		</dl>

	</div>   
	<!-- <script>
		$(function(){
			
			$("body").on("mouseover",".menulist dl dt",function(){
				$(this).find("i").css("opacity",1);
			})
			$("body").on("mouseout",".menulist dl dt",function(){
			$(this).find("i").css("opacity",0);
			})
		})	
	</script> -->
</body>
</html>