<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>HTML5异步上传文件</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {
	var addVersionBtn=$('#uploadBtn')  // 上传按钮
    var cancelUploadBtn=$('#cancelBtn') // 取消按钮
    var fileInput=$("#uploadFile")  // 文件选择框
    var processBar = $("#progressBar"); //进度条
    
    var fileNameLab=$("label[name=upfileName]")
    var fileSizeLab=$("label[name=upfileSize]")
    var fileTypeLab=$("label[name=upfileType]")
    var speedLab=$("#showSpeed") //<label>
    
    var url='/api/file'

    //获取文件上传实例
    var upload=UploadCommon(url,processBar,speedLab,addVersionBtn,cancelUploadBtn,initPageInfo)
    
    // 文件选择框变更事件
    fileInput.change(function() {
        var fileObj = fileInput.get(0).files[0]; // js获取文件对象
        if (fileObj) {
            var fileSize = getSize(fileObj.size);
            fileNameLab.text('文件名：' + fileObj.name);
            fileSizeLab.text('文件大小：' + fileSize);
            fileTypeLab.text('文件类型：' + fileObj.type);
            addVersionBtn.attr('disabled', false);
        }
    });
    
    // 点击上传固件事件
    addVersionBtn.click(function(){
        var versionInfo=$('#version').val()
        var file = fileInput.get(0).files[0]
        var strategyInfo=$('#versionType').val()
        if(file==null){
            alert("固件文件不能为空")
            return
        }
        if(versionInfo==''){
            alert("版本号不能为空")
            return
        }
        if(strategyInfo==''){
            alert("升级策略不能为空")
            return
        }
        
        // 创建提交数据
        var formData = new FormData();
        formData.append('firmFile', fileInput.get(0).files[0]); 
        formData.append('version', versionInfo);
        formData.append('strategy', strategyInfo); 
        
        // 上传文件
        upload.uploadFile(formData)        
    })
})

</script>
</head>
<body class="input admin">
    <div class="bar">
        HTML5异步上传文件
    </div>
    <div class="body">
        <form id="validateForm" method="post" enctype="multipart/form-data">
            <ul id="tab" class="tab">
                <li>
                    <input type="button" value="基本信息" hidefocus />
                </li>
            </ul>
            <table class="inputTable tabContent">
            	<tr>
                    <th>需要提交的信息：</th>
                    <td>
						<input name="otherInfo" class="formText" />
                    </td>
                </tr>
                <tr>
                    <th>选择要上传的文件：</th>
                    <td>
						<input type="file" id="uploadFile" name="file" />
                    </td>
                </tr>
                <tr>
                    <th>上传进度：</th>
                    <td>
						<div style="padding-left: 0; padding-right: 0; margin-bottom: 0px;">
				            <div class="progress progress-striped active" style="display: ">
				                <div id="progressBar" class="progress-bar progress-bar-success"
				                    role="progressbar" aria-valuemin="0" aria-valuenow="0"
				                    aria-valuemax="100" style="width: 20%">
				                </div>
				            </div>
				        </div>
                    </td>
                </tr>
                <tr>
                    <th>上传速度：</th>
                    <td>
						<div id="showInfo">0KB/s</div>
                    </td>
                </tr>
                <tr>
                    <th>上传详情：</th>
                    <td>
						<div id="showFieInfo">
					        <label name="upfileName"></label><br /> 
					        <label name="upfileSize"></label><br />
					        <label name="upfileType"></label><br />
					    </div>
                    </td>
                </tr>
            </table>
            <div class="buttonArea">
                <button type="button" id="uploadBtn" class="formButton">上传</button> &nbsp;&nbsp;
				<button type="button" id="cancelBtn" class="formButton">取消</button>
            </div>
        </form>
    </div>
</body>
</html>
