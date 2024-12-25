/*自适应宽度meta*/
(function () {
// 页面的默认宽度
     var DEFAULT_WIDTH = 750, ua = navigator.userAgent.toLowerCase(),deviceWidth = window.screen.width,devicePixelRatio = window.devicePixelRatio || 1, targetDensitydpi;   
	 deviceWidth=750;
     deviceWidth=deviceWidth/devicePixelRatio;
	 // Android4.0以下手机不支持viewport的width，需要设置target-densitydpi,或者低端大屏小分辨率的手机
	 if (ua.indexOf("android") !== -1 && ( parseFloat(ua.slice(ua.indexOf("android")+8)) < 4) || (window.screen.width < 542 && devicePixelRatio < 1.5)){
		  targetDensitydpi = DEFAULT_WIDTH/deviceWidth * devicePixelRatio * 160;
		  document.querySelector("meta[name=viewport]").setAttribute('content','width=device-width,target-densitydpi='+targetDensitydpi+', width=device-width, user-scalable=no,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no');  
	  };   
})();

