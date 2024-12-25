$().ready( function() {
	
	var boxFade = function(hideBox, showBox, isNeedHide) {
		if(isNeedHide){
			hideBox.removeClass("showTheBox");
			showBox.addClass("showTheBox");
		}
		else{
			showBox.addClass("showTheBox");
		}
	};
	
	$(document).on("click", "li span", function(){
		// 当前先选中的菜单取消选中，且iframe隐藏
		var curItem = $(".cur");
		var curBoxName = curItem.attr("box");
		var curBox =  $("div[box='"+ curBoxName +"']");
		curItem.attr("class", "tabItem");
		
		// 选中现在点击的菜单，并展示iframe
		var nowItem = $(this).parent();
		var nowBoxName = nowItem.attr("box");
		var nowBox =  $("div[box='"+ nowBoxName +"']");
		nowItem.attr("class", "cur");
		
		// 淡入淡出
		boxFade(curBox, nowBox, true);
	})
	
	$(document).on("click", "li i", function(){
		// 当前先选中的菜单取消选中，且iframe隐藏
		var curItem = $(".cur");
		var curBoxName = curItem.attr("box");
		var curBox =  $("div[box='"+ curBoxName +"']");
		curItem.attr("class", "tabItem");
		
		// 获取当前需要关闭的菜单的前一个菜单
		// PS：前一个永远存在，因为最左端的首页永远不可关闭
		// 前一个菜单选中，并展示iframe
		var prevItem = $(this).parent().prev();
		var prevBoxName = prevItem.attr("box");
		var prevBox =  $("div[box='"+ prevBoxName +"']");
		prevItem.attr("class", "cur");
		
		// 当前需要关闭的菜单清除，iframe清除
		var nowItem = $(this).parent();
		var nowBoxName = nowItem.attr("box");
		var nowBox =  $("div[box='"+ nowBoxName +"']");
		nowItem.remove();
		nowBox.remove();
		
		// 淡入淡出
		boxFade(curBox, prevBox, true);
	})
	
	$(".itemCloseBtn").click(function() {
		var type = $(this).attr("close-type");
		if(type == 'cur'){
			// 获取当前选中的菜单
			var nowItem = $(".cur");
			var nowBoxName = nowItem.attr("box");
			var nowBox =  $("div[box='"+ nowBoxName +"']");
			
			// 获取当前选中菜单的前一个菜单，并展示
			var prevItem = nowItem.prev();
			var prevBoxName = prevItem.attr("box");
			var prevBox =  $("div[box='"+ prevBoxName +"']");
			prevItem.attr("class", "cur");
			
			// 关闭当前选中的菜单
			nowItem.remove();
			nowBox.remove();
			
			// 淡入淡出
			boxFade(null, prevBox, false);
			
		}
		if(type == 'other') {
			// 关闭并删除其他的菜单及Iframe
			var otherItems = $(".tabItem[name!='index']");
			otherItems.each(function() {
				var otherBoxName = $(this).attr("box");
				var otherBox =  $("div[box='"+ otherBoxName +"']");
				otherBox.remove();
			})
			otherItems.remove();
			
			// 焦点归位至最左端
			$(".top_left").stop().animate({"left":0},100);
		}
		if(type == 'all') {
			// 获取首页
			var indexItem = $(".top_left").find("li[name='index']");
			var indexBoxName = indexItem.attr("box");
			var indexBox =  $("div[box='"+ indexBoxName +"']");
			indexItem.attr("class", "cur");
			
			// 淡入淡出
			boxFade(null, indexBox, false);
			
			// 关闭并删除除了首页以外的菜单及Iframe
			var allItems = $(".top_left").find("li[name!='index']");
			allItems.each(function() {
				var boxName = $(this).attr("box");
				var box =  $("div[box='"+ boxName +"']");
				box.remove();
			})
			allItems.remove();
			
			// 焦点归位至最左端
			$(".top_left").stop().animate({"left":0},100);
		}
	})
	
	// 左箭头
	$(".L_arrow").click(function() {
		var totalWidth = 0;
		var topMenuWidth = $(".top_left").parent().width();
		var topLi = parseInt($(".top_left").css("left"));
		var liLen = $(".top_left").find("li").length;
		for (var i = 0; i < liLen; i++) {
			var liWidth = $(".top_left").find("li").eq(i).outerWidth(true);
			totalWidth += liWidth;
		}

		var newLeft = topLi + topMenuWidth * 0.75;
		if (newLeft > 0) {
			newLeft = 0;
		}
		$(".top_left").stop().animate({
			"left" : newLeft
		}, 500);

	});
	
	// 右箭头
	$(".R_arrow").click(function() {
		var totalWidth = 0;
		var topMenuWidth = $(".top_left").parent().width();
		var topLi = parseInt($(".top_left").css("left"));
		var liLen = $(".top_left").find("li").length;
		for (var i = 0; i < liLen; i++) {
			var liWidth = $(".top_left").find("li").eq(i).outerWidth(true);
			totalWidth += liWidth;
		}
		if (totalWidth > topMenuWidth) {
			var newLeft = topLi - topMenuWidth * 0.75;
			if (newLeft <= (topMenuWidth - totalWidth)) {
				newLeft = topMenuWidth - totalWidth
			}
			$(".top_left").stop().animate({
				"left" : newLeft
			}, 500);
		}
	})

	// 点击下箭头显示下拉选项
	$(".dropDown_icon").click(function() {
		$(this).siblings("ul").show();
	});
	$(".dropDown").mouseleave(function() {
		$(this).find("ul").hide();
	})			
});