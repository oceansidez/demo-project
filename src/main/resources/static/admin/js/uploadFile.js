
/** 上传图片方法
 * 
 * @param fileId --- Type为file的input标签id
 * @param allowExtensions --- 允许的文件后缀集合（“,”逗号分隔）示例：jpg,png,gif，若为null则无限制
 * @param fileSize --- 文件大小（单位：mb）
 * @param uploadMethod --- 后台上传方法名
 * @param divId --- 图片Div标签id
 * @param hiddenUrl --- 用于存放imgUrl的隐藏标签id
 * @param hiddenContent --- 用于存放图片填充区展示图片内容的隐藏标签id
 * @param replaceName --- 图片填充区展示内容需要替换url的部分（一般可使用|#|dataUrl|#|）
 */

function imgFileChange(fileId, allowExtensions,
		fileSize, uploadMethod, divId, hiddenUrl, hiddenContent, replaceName){
	
	// 获取文件及Reader
	var myfile = document.getElementById(fileId);
	if(!(myfile.files[0] == undefined)){
		var reader = new FileReader();
		reader.readAsDataURL(myfile.files[0]);
		reader.onload = function(e){
			
			// 判断文件大小
			var size = Math.floor(myfile.files[0].size/1024);
			if(size > (fileSize * 1024)){
				$.message({type: "error", content: "图片不能大于"+ fileSize.toString() +"M"});
				imgResetFileInput(fileId);
				return;
			}
			
			// 判断文件类型
			var fileName = myfile.files[0].name;
			var extensionFlag = false;
			if(allowExtensions != null && allowExtensions != ''){
				var array = allowExtensions.split(",");
				var len = array.length;
				for (var i = 0; i < len; i++){
					if(fileName.indexOf("." + array[i])!= -1){
						extensionFlag = true;
						break;
					}
				}
			}else{
				extensionFlag = true;
			}
			if(!extensionFlag){
				$.message({type: "error", content: "只能上传"+ allowExtensions +"类型文件"});
				imgResetFileInput(fileId);
				return;
			}
			
			// 执行上传
			$.ajaxFileUpload({
		    	url: uploadMethod, 
		    	type: 'post',
		    	fileElementId: fileId, 
		    	dataType: 'json', 
		    	success: function(data, status){  
					if(data.error == '0') {
						$("#" + divId).html($("#" + hiddenContent).val().replace(replaceName, data.url));
						$("#" + hiddenUrl).val(data.url);
					}else {
						$.message({type: "error", content: data.message});
					}
					imgResetFileInput(fileId);
			    },
		    	error: function(data, status, e){ 
			        $.message({type: "error", content: "执行上传异常"});
			        imgResetFileInput(fileId);
			    }
			})
		}
	}
	else{
		$.message({type: "error", content: "文件未定义"});
	}
}

/** 清空图片file里面的内容
 * 
 * @param fileId --- Type为file的input标签id
 */
function imgResetFileInput(fileId){   
	var file = $("#" + fileId);
	file.after(file.clone().val(""));   
    file.remove();  
}