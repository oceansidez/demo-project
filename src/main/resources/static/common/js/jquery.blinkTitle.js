;(function($) {
	$.extend({

		/**

		 * 调用方法： var timerArr = $.blinkTitle.show();

		 *          $.blinkTitle.clear(timerArr);

		 */

		blinkTitle : {

			show : function(message) { //有新消息时在title处闪烁提示

				var step = 0, _title = "test平台";

				var timer = setInterval(function() {

					step++;

					if (step == 3) {
						step = 1;
					}
					;

					if (step == 1) {
						var blank = '';
						for(var index = 0; index < message.length; index++) {
							blank = blank + '　';
						}
						document.title = '【' + blank + '】' + _title;
					}
					;

					if (step == 2) {
						document.title = '【' + message + '】' + _title;
					}
					;

				}, 500);

				return [ timer, _title ];

			},

			/**

			 * @param timerArr[0], timer标记

			 * @param timerArr[1], 初始的title文本内容

			 */

			clear : function(timerArr) { //去除闪烁提示，恢复初始title文本

				if (timerArr) {

					clearInterval(timerArr[0]);

					document.title = timerArr[1];

				}
				;

			}

		}

	});

})(jQuery);
