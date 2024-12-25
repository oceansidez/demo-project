<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>公众号菜单配置</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/official/css/official_base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/official/css/official_other.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {
	
	// 初始化页面配置
	var menusJson={
		menus:[]
	}
	
	// 初始化菜单
	<#if (menuList?? && menuList?size>0)>
		<#list menuList as menu>
			var $menu_ul = $(".wx_nav").find("ul").eq(${(menu.index)!});
			var $menu_top = $menu_ul.find("li").eq(0);
			$menu_top.attr("menu-type", '${(menu.type)!}');
			$menu_top.attr("menu-name", '${(menu.name)!}');
			$menu_top.attr("data-value", '${(menu.value)!}');
			$menu_top.html('${(menu.name)!}');
			<#if (menu.items?? && menu.items?size>0)>
				<#list menu.items as item>
					$menu_ul.prepend('<li class="two normal_menu" menu-type="${(item.type)!}" menu-name="${(item.name)!}" data-value="${(item.value)!}">${(item.name)!}<i>x</i></li>');
					var liLen = $menu_ul.find('li').length;
					$menu_ul.css('height',liLen*40);
				</#list>
			</#if>
		</#list>
	</#if>
	
	var defaultItem = $("li.selected_menu");
	initItem(defaultItem);
	
	// 定义父级菜单ul和默认Item
	var $thisul = $('.wx_nav').find('ul').eq(0);
	
	// 父级菜ul单选择事件
	$('.wx_nav').on('click','ul li',function(){
		$thisul=$('.wx_nav').find('ul').eq($(this).parent().index());
	})
	
	// 菜单类型下拉框Change
	$("#menuType").change(function() {
		var menuType = $(this).val();
		if(menuType == 'father'){
			$("#urlTr").hide();
			$("#targetUrl").val("");
			$("#keyTr").hide();
			$("#keyword").val("");
			$("#addSubMenuBtn").show();
		}
		else if(menuType == 'view'){
			$("#urlTr").show();
			$("#targetUrl").val("");
			$("#keyTr").hide();
			$("#keyword").val("");
			$("#addSubMenuBtn").hide();
		}
		else if(menuType == 'click'){
			$("#urlTr").hide();
			$("#targetUrl").val("");
			$("#keyTr").show();
			$("#keyword").val("");
			$("#addSubMenuBtn").hide();
		}
	})
	
	// 菜单Item选中
	$("body").on('click', '.one,.two', function() {
		var item = $(this);
		$("li.selected_menu").addClass("normal_menu");
		$("li.selected_menu").removeClass("selected_menu");
		item.removeClass("normal_menu");
		item.addClass("selected_menu");
		
		// 菜单Item加载
		initItem(item);
	})
	
	// 将菜单Item加载至操作栏
	function initItem(obj){
	
		// 操作栏位加载
		changeOperateBox(obj);
		
		if( obj.attr("menu-type") && 
			obj.attr("menu-type") != null && 
			obj.attr("menu-type") != '' ){
				$("#menuType").val(obj.attr("menu-type"));
		}
		
		if( obj.attr("menu-name") && 
			obj.attr("menu-name") != null && 
			obj.attr("menu-name") != '' ){
				$("#menuName").val(obj.attr("menu-name"));
		}
		else{
			$("#menuName").val("");
		}
		
		// 根据类型加载对应的值
		// 链接类型：data-value赋值给targetUrl跳转链接
		// 事件类型：data-value赋值给keyword触发事件key值
		if(obj.attr("menu-type") == 'view'){
			$("#targetUrl").val(obj.attr("data-value"));
		}
		else if(obj.attr("menu-type") == 'click'){
			$("#keyword").val(obj.attr("data-value"));
		}
	}
	
	// 根据菜单Item类型MenuType修改操作栏位
	// 1、父级类型：无跳转链接、无触发事件Key值、有【新增子菜单】按钮
	// 2、链接类型：有跳转链接、无触发事件Key值、无【新增子菜单】按钮
	// 3、事件类型：无跳转链接、有触发事件Key值、无【新增子菜单】按钮
	// 4、全新的父菜单：无跳转链接、无触发事件Key值、无【新增子菜单】按钮
	// 5、全新的子菜单：有跳转链接（默认选中菜单跳转链接事件）、无触发事件Key值、无【新增子菜单】按钮
	function changeOperateBox(obj) {
		var menuType = obj.attr("menu-type");
		if(obj.hasClass("one")){
			$("#menuType").html('<option value="father">父级菜单</option>'+
	          					'<option value="view">菜单跳转链接事件</option>'+
	          					'<option value="click">菜单点击触发事件</option>');
		}
		else if(obj.hasClass("two")){
			$("#menuType").html('<option value="view">菜单跳转链接事件</option>'+
	          					'<option value="click">菜单点击触发事件</option>');
		}
	
		if( menuType && 
			menuType != null && 
			menuType != '' ){
			if(menuType == 'father'){
				$("#urlTr").hide();
				$("#targetUrl").val("");
				$("#keyTr").hide();
				$("#keyword").val("");
				$("#addSubMenuBtn").show();
			}
			else if(menuType == 'view'){
				$("#urlTr").show();
				$("#targetUrl").val("");
				$("#keyTr").hide();
				$("#keyword").val("");
				$("#addSubMenuBtn").hide();
			}
			else if(menuType == 'click'){
				$("#urlTr").hide();
				$("#targetUrl").val("");
				$("#keyTr").show();
				$("#keyword").val("");
				$("#addSubMenuBtn").hide();
			}
			
			if(obj.hasClass("one")){
				$("#deleteFatherBtn").show();
			}
			else if(obj.hasClass("two")){
				$("#deleteFatherBtn").hide();
			}
		}
		else{
			if(obj.hasClass("one")){
				$("#urlTr").hide();
				$("#addSubMenuBtn").show();
				$("#deleteFatherBtn").show();
			}
			else if(obj.hasClass("two")){
				$("#urlTr").show();
				$("#addSubMenuBtn").hide();
				$("#deleteFatherBtn").hide();
			}
			$("#targetUrl").val("");
			$("#keyTr").hide();
			$("#keyword").val("");
		}
	}
	
	// 保存按钮，将当前操作栏属性更新至选中菜单Item
	$("body").on('click', '#saveBtn', function() {
		var menuType = $("#menuType").val();
		var menuName = $("#menuName").val();
		var targetUrl = $("#targetUrl").val();
		var keyword = $("#keyword").val();
		var selectedItem = $("li.selected_menu");
		
		// 验证
		// 1、父级类型：菜单名称不能为空
		// 2、链接类型：不含任何子菜单（仅针对父级菜单），菜单名称不能为空，链接不能为空
		// 3、事件类型：不含任何子菜单（仅针对父级菜单），菜单名称不能为空，事件Key不能为空
		if(menuType == 'father'){
			if(menuName == null || menuName == ''){
				$.message({type: "error", content: "菜单名称不能为空"});
				return;
			}
		}
		else if(menuType == 'view'){
			if(menuName == null || menuName == ''){
				$.message({type: "error", content: "菜单名称不能为空"});
				return;
			}
			if(targetUrl == null || targetUrl == ''){
				$.message({type: "error", content: "跳转链接不能为空"});
				return;
			}
			if(!checkUrl(targetUrl)) {
				$.message({type: "error", content: "跳转链接格式不正确，参照“http://www.baidu.com”"});
				return;
			}
			if(selectedItem.hasClass("one") && selectedItem.parent().find('li').length > 1){
				$.message({type: "error", content: "当前父菜单存在子菜单，不可修改"});
				return;
			}
		}
		else if(menuType == 'click'){
			if(menuName == null || menuName == ''){
				$.message({type: "error", content: "菜单名称不能为空"});
				return;
			}
			
			if(keyword == null || keyword == ''){
				$.message({type: "error", content: "触发事件Key不能为空"});
				return;
			}
			if(selectedItem.hasClass("one") && selectedItem.parent().find('li').length > 1){
				$.message({type: "error", content: "当前父菜单存在子菜单，不可修改"});
				return;
			}
		}
		
		// 赋值给标签
		selectedItem.attr("menu-type", menuType);
		selectedItem.attr("menu-name", menuName);
		if(selectedItem.hasClass("one")){
			selectedItem.html(menuName);
		}
		else if(selectedItem.hasClass("two")){
			selectedItem.html(menuName + "<i>x</i>");
		}
		
		if(menuType == 'view'){
			selectedItem.attr("data-value", targetUrl);
		}
		else if(menuType == 'click'){
			selectedItem.attr("data-value", keyword);
		}
		
	})
	
	// 新增子菜单按钮，为当前菜单Item新增子菜单
	$("body").on('click', '#addSubMenuBtn', function() {
		var liLen = $thisul.find('li').length;
		if(liLen<6){
			$thisul.prepend('<li class="two normal_menu" data-value="">新菜单<i>x</i></li>');
			$thisul.css('height',(liLen+1)*40);
		}
	})
	
	// 删除父菜单按钮，将当前父菜单置空清除（若有子菜单请先清除子菜单）
	$("body").on('click', '#deleteFatherBtn', function() {
		var selectedItem = $("li.selected_menu");
		if(selectedItem.hasClass("one") && selectedItem.parent().find('li').length > 1){
			$.message({type: "error", content: "当前父菜单存在子菜单，不可删除"});
			return;
		}
		selectedItem.removeAttr("menu-type");
		selectedItem.removeAttr("menu-name");
		selectedItem.attr("data-value", "");
		selectedItem.html("+")
		initItem(selectedItem);
	})
	
	// 子菜单删除
	$('.wx_nav').on('click','ul li.two i',function(){
		var liLen = $(this).parents('ul').find('li').length;
		$(this).parents('ul').css('height',(liLen-1)*40);
		$(this).parent().remove();
	})
	
	// 菜单发布
	$("#submitBtn").click(function(){
		menusJson.menus=[];
		for(var i=0; i<3; i++) {
			topMenuStr(i);
		}
		if(menusJson==null || menusJson.menus==null || menusJson.menus.length==0) {
			$.message({type: "error", content: "请配置菜单"});
			return;
		}
		if(validateMenu()) {
			//将 JavaScript 值转换为 JSON 字符串。
			var menuString = JSON.stringify(menusJson);
			$("#menuString").val(menuString);
			$("#submitForm").submit();
		}
	})
	
	// 验证父级菜单下是否有子菜单
	function validateMenu() {
		var flag = true;
		$('.wx_nav ul').each(function(index, element) {
			var $this = $(this);
			// 父级菜单
			var $topMenu = $this.find(".one");
			var menuType = $topMenu.attr("menu-type");
			var menuName = $topMenu.attr("menu-name");
			if(menuType=='father' && menuName!="" && menuName!=undefined) {
				var len = 0;
				$this.find(".two").each(function(i,val){
					var $this2 = $(this);
					var menuType2 = $this2.attr("menu-type");
					if(menuType2!="" && menuType2!=undefined) {
						len++;
					}
				})
				if(len==0) {
					flag = false;
					$.message({type: "error", content: "父级菜单下至少要有一个子菜单"});
					return;
				}
			}
		})
		return flag;
	}
	
	// 验证链接
	function checkUrl(urlString){
		var reg=/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
		if(!reg.test(urlString)){
			return false;
        }
        return true;
	}

	// 菜单组装（父级）	
	function topMenuStr(i) {
		var childMenu = $(".wx_nav").find("ul").eq(i).find("li");
		var childMenuLen = childMenu.length;
		var el = childMenu.eq(childMenuLen-1);
		
		var name = el.attr("menu-name");
		var type = el.attr("menu-type");
		var value = el.attr("data-value");
		if(name!="" && name!=undefined && type!="" && type!=undefined) {
			var menu={
				name:name,
				type:type
			}
			if(type=='view'){
				menu.url=value;
			}else if(type=='click'){
				menu.key=value;
			}else if(type=='father'){
				var items = childMenuStr(childMenu, childMenuLen);
				menu.items=items;
			}
			menu.index = i;
			menusJson.menus.push(menu);
		}	
	}
	
	// 菜单组装（子级）	
	function childMenuStr(childMenu, childMenuLen) {
	 	var items=[];
	 	if(childMenuLen>1) {
	 		for(var j=childMenuLen-2; j>=0; j--) {
	 			var el = childMenu.eq(j);
	 			var name = el.attr("menu-name");
				var type = el.attr("menu-type");
				var value = el.attr("data-value");
				if(name!="" && name!=undefined && type!="" && type!=undefined
						&& value!="" && value!=undefined) {
					var menu={
						name:name,
						type:type
					}
					if(type=="view"){
			    		menu.url=value;
			    	}else if(type=="click"){
			    		menu.key=value;
			    	}
			    	items.push(menu);
				}
	 		}
	 	}
    	return items;
	}
	
});
</script>
</head>
<body class="input admin">
	<div class="bar">
		公众号菜单配置
	</div>
	<div>
		<div class="menu_box" style="float:left">
			<div class="wx_box">
				<div class="wx_nav">
					<ul>
						<li class="one selected_menu" data-value="">+</li>
					</ul>
					<ul>
						<li class="one normal_menu" data-value="">+</li>
					</ul>
					<ul>
						<li class="one normal_menu" data-value="">+</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="body" style="float:left;padding-top:45px;width:600px;">
			<form id="submitForm" action="${base}/wechatConfig/menu" method="post">
				<input type="hidden" id="menuString" name="menuString" value="${(menuString)!}">
			</form>
			<table class="inputTable tabContent">
				<tr>
					<th colspan="2" style="text-align:center">
						微信公众号菜单
					</th>
				</tr>
				<tr>
					<th>
						菜单类型: 
					</th>
					<td>
						<select id="menuType" class="formText" >
          					<option value="father">父级菜单</option>
          					<option value="view">菜单跳转链接事件</option>
          					<option value="click">菜单点击触发事件</option>
          				</select>
					</td>
				</tr>
				<tr>
					<th>
						菜单名称: 
					</th>
					<td>
						<input type="text" id="menuName" class="formText"  style="width:170px"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr id="urlTr">
					<th>
						跳转链接: 
					</th>
					<td>
						<input type="text" id="targetUrl" class="formText" style="width:300px"  />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr id="keyTr">
					<th>
						触发事件Key值: 
					</th>
					<td>
						<input type="text" id="keyword" class="formText" style="width:300px"  />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						当前选中菜单: 
					</th>
					<td>
						<input id="addSubMenuBtn" type="button" class="formButton" value="新增子菜单" hidefocus />
						<input id="deleteFatherBtn" type="button" class="formButton" value="删除父菜单" hidefocus />
						<input id="saveBtn" type="button" class="formButton" value="保  存" hidefocus />&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<th>
						公众号操作: 
					</th>
					<td>
						<input type="submit" id="submitBtn" class="formButton" value="发布菜单" hidefocus />&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<th>
						公众号二维码：
					</th>
					<td>
						<img style="width:200px;height:200px;border:1px solid silver;"
				 		 src="https://open.weixin.qq.com/qr/code?username=${officialAccount}" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>