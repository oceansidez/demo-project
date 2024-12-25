// JavaScript Document
$(document).ready(function(e) {
	changeheight();
    setInterval(function(){
	   $(".con").toggleClass('conadd');	
	},30);
	var num=3;
	var numset=setInterval(function(){
		num--;
		if(num<0){
			clearInterval(numset);
			firemove();
			setTimeout(function(){
			  window.history.go(-1);	
			},1000)
			
		}else{
			$(".num").html(num)
		}
	},1000);
	setTimeout(function(){
	  window.location.href='/test-web/login';
	},5100)
});

//火箭动画
function firemove(){
	$('.fire .con').animate({
				top:-360,opacity:0},{
				easing: 'easeOutQuad',
				duration: 200
	 });
}

//页面大小改变


window.onresize=changeheight;
function changeheight(e){
	if($(window).height()<900){
		$("body").addClass('add');
	}else{
		$("body").removeClass('add')
	}
}