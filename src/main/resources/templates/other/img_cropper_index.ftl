<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>图片裁剪</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/common_util/cropper/css/cropper.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.img-preview{
	width: 200px;
	height: 150px;
	float: left;
	overflow: hidden;
	border: 1px silver solid;
}
.imageBox {
	width: 800px;
	height: 600px;
}
</style>
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript" src="${base}/static/common_util/cropper/js/cropper.js"></script>
<script type="text/javascript" src="${base}/static/common_util/cropper/js/jquery-cropper.js"></script>
<script type="text/javascript">
$().ready(function() {
	var options = {
		aspectRatio: 16 / 9,
		preview: '.img-preview',
		strict: true,
		crop: function (e) {
		
	    }
	};
	
	$("#imageFile").cropper(options);
})
</script>
</head>
<body class="input admin">
    <div class="bar">
        图片裁剪
    </div>
    <div class="body">
        <form id="validateForm"  method="post">
            <ul id="tab" class="tab">
                <li>
                    <input type="button" value="基本信息" hidefocus />
                </li>
            </ul>
            <table class="inputTable tabContent">
            	<tr>
                    <th>预览区</th>
                    <td>
						<div class="img-preview"></div>
                    </td>
                </tr>
                <tr>
                    <th>裁剪区</th>
                    <td>
						<div class="imageBox">
				          <img id="imageFile" src="${base}/static/common_util/cropper/images/picture.jpg" alt="Picture">
				        </div>
                    </td>
                </tr>
            </table>
            <div class="buttonArea">
            	<input type="submit" class="formButton submitBtn" value="保存" data-action="${base}/pageTemplate/generate" hidefocus />&nbsp;&nbsp;
            </div>
        </form>
    </div>
</body>
</html>
