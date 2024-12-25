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
	
	// 判断是否为Iframe多标签
	var isUseIframe = null;
	if($("#isUseIframe").val() == 'true'){
		isUseIframe = true;
	}
	if($("#isUseIframe").val() == 'false'){
		isUseIframe = false;
	}
	
	// 菜单点击事件
 	$('.menulist  dl dd').click(function(){
 	  	$('.singleItem').removeClass('cur');
 	 	$('.menulist  dl dd').removeClass('cur');
	  	$(this).addClass('cur');
	  	var st = $(this).find('a').attr("target-href");
	  	var title = $(this).find('a').html();
	  	menuSelect(st, title);
 	});
 	$('.singleItem').click(function(){
	  	$('.menulist  dl dd').removeClass('cur');
 	 	$('.singleItem').removeClass('cur');
	  	$('.menulist  dl dd').removeClass('cur');
	  	$(this).addClass('cur');
	  	var st = $(this).find('a').attr("target-href");
	  	var title = $(this).find('a').html();
	  	if($(this).attr("id") == 'index'){
	  		menuSelect(st, title);
	  	}
	  	else{
	  		menuSelect(st, title);
	  	}
 	});
	$('.menulist  dl dt').click(function(){
	 	$('.menulist  dl').removeClass('cur');
     	$this=$(this);
	 	$this.parent('dl').addClass('cur');
 	});	
	
	// 菜单选中处理
	function menuSelect(url, title){
		if(isUseIframe){
			var contentFrame = window.parent.headerFrame;
			
			// 当前先选中的菜单取消选中，且iframe隐藏
			var curItem = contentFrame.$(".cur");
			var curBoxName = curItem.attr("box");
			var curBox =  contentFrame.$("div[box='"+ curBoxName +"']");
			curItem.attr("class", "tabItem");
			
			//	获取ul的left,宽度及li的个数	
			var ulLeft = parseInt(contentFrame.$(".top_left").css('left'));
			var topMenuWidth = contentFrame.$(".top_left").parent().width();
			var liLen = contentFrame.$(".top_left").find("li").length;
			
			var totalWidth = 0;
			var beforeLiWidth = 0;
			var afterLiWidth = 0;
			var maxIndex = 0;
			
			
			// 查询当前菜单中是否有本次点击链接的菜单
			var nowItem = contentFrame.$("li[box='"+ url +"']");
			if(nowItem.length > 0){
				// 若有则选中并展示iframe
				var nowBox =  contentFrame.$("div[box='"+ url +"']");
				nowItem.attr("class", "cur");
				
				// 淡入淡出
				boxFade(curBox, nowBox, true);
				
				var thisIndex = contentFrame.$(".top_left").find("li.cur").index();
				//	获取当前元素的宽度			
				var indexWidth = contentFrame.$(".top_left").find("li").eq(thisIndex).outerWidth(true);
				
				// 获取前索引值前面的li的总宽度	
				for(var i=0;i<thisIndex;i++){
					beforeLiWidth += contentFrame.$(".top_left").find("li").eq(i).outerWidth(true);
				}

				for(var i=liLen-1;i>-1;i--){
					afterLiWidth += contentFrame.$(".top_left").find("li").eq(i).outerWidth(true);
					if(afterLiWidth<topMenuWidth){
						maxIndex = i;
					}
				}
				
				// 获取li的总宽度	
				for(var n=0;n<liLen;n++){
					var liWidth = contentFrame.$(".top_left").find("li").eq(n).outerWidth(true);
					totalWidth += liWidth;
				}
				
				if(ulLeft <= 0  && totalWidth > topMenuWidth ){
					if(thisIndex >= maxIndex){
						contentFrame.$(".top_left").stop().animate({"left":topMenuWidth-totalWidth},500);
					}else{
						if(thisIndex == 1){
							contentFrame.$(".top_left").stop().animate({"left":-(beforeLiWidth -39)},500);
						}else{
							contentFrame.$(".top_left").stop().animate({"left":-(beforeLiWidth)},500);
						}
					}
				}
			}
			else{
				// 若无则新增菜单及frame
				var newItem = 
					'<li class="cur" box="'+ url +'"><span>'+ title +'</span><i>×</i></li>';
				var newBox = 
					'<div box="'+ url +'" class="mainBox">'+
					'<iframe src="'+ url +'" frameborder="0" width="100%" height="100%" />'+
					'</div>';
				contentFrame.$(".top_left").append(newItem);
				contentFrame.$("body").append(newBox);
				var nowBox =  contentFrame.$("div[box='"+ url +"']");
				
				// 淡入淡出
				setTimeout(function(){boxFade(curBox, nowBox, true);},0)
				
				for(var i=0;i<liLen+1;i++){
					var liWidth = contentFrame.$(".top_left").find("li").eq(i).outerWidth(true);
					totalWidth += liWidth;
				}
				
				if(totalWidth>topMenuWidth){
					contentFrame.$(".top_left").stop().animate({"left":-(totalWidth-topMenuWidth)},500);
				}
			}
			
	    }
	    else{
	    	window.parent.mainFrame.location = url;
	    }
	}
});