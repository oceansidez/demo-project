<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>代码生成</title>
<link href="${base}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/static/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	var $tab = $("#tab");

	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"fieldBeanList.field": "required",
			"fieldBeanList.dataType": "required",
			"fieldBeanList.decription": "required"
		},
		messages: {
			"fieldBeanList.field": "请填写字段名称",
			"fieldBeanList.dataType": "请填写类型",
			"fieldBeanList.decription": "请添加描述"
		},
		submitHandler: function(form) {
			//$(form).find(":submit").attr("disabled", true);
		 	form.submit();
		}
	});
	
	$("#addButton").click(function() {
		var row = $(".tabContent tr").length - 2;
		var trHTML = '<tr><th>字段名称:</th>' +
					'<td>' +
						'<input type="text" name="fieldBeanList[' + row + '].field" class="formText" placeholder="例如：name或者name_detail"/>' +
						'<label class="requireField">*</label>' +
					'</td>' +
					'<th>SQL字段类型:</th>' +
                    '<td>' +
                        '<input type="text" list="sqlTypeList" name="fieldBeanList[' + row + '].sqlType" class="formText" placeholder="例如：varchar(45)"/>' +
                        '<label class="requireField">*</label>' +
                        '<datalist id="sqlTypeList">' +
                            '<option value="VARCHAR(45)">VARCHAR(45)</option>' +
                            '<option value="INT(11)">INT(11)</option>' +
                            '<option value="FLOAT">FLOAT</option>' +
                            '<option value="DATETIME">DATETIME</option>' +
                            '<option value="CHAR(1)">CHAR(1)</option>' +
                            '<option value="DECIMAL(16)">DECIMAL(16)</option>' +
                            '<option value="BIT(1)">BIT(1)</option>' +
                            '<option value="TEXT">TEXT</option>' +
                        '</select>' +
                    '</td>' +
					'<th>字段类型:</th>' +
					'<td>' +
						'<input type="text" list="dataTypeList" name="fieldBeanList[' + row + '].dataType" class="formText" placeholder="例如：String"/>' +
						'<label class="requireField">*</label>' +
						'<datalist id="dataTypeList">' +
							'<option value="String">String</option>' +
							'<option value="Integer">int</option>' +
							'<option value="Boolean">boolean</option>' +
							'<option value="Date">date</option>' +
							'<option value="Double">double</option>' +
							'<option value="Char">char</option>' +
							'<option value="BigDecimal">BigDecimal</option>' +
							'<option value="Short">short</option>' +
							'<option value="Long">long</option>' +
							'<option value="Byte">byte</option>' +
							'<option value="Float">float</option>' +
						'</select>' +
					'</td>' +
					'<th>描述:</th>' +
					'<td>' +
						'<input type="text" name="fieldBeanList[' + row + '].description" class="formText" placeholder="例如：姓名"/>' +
						'<label class="requireField">*</label>' +
					'</td>' +
				'</tr>';
		$(".tabContent").append(trHTML);
	})
	
	$("#removeButton").click(function() {
		var row = $(".tabContent tr").length;
		if(row > 1) {
			$(".tabContent tr:last").remove();
		}
	})
});

</script>
</head>
<body class="input admin">
	<div class="bar">
		代码生成
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="${base}/codeSource/save" method="post">
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						生成文件名:
					</th>
					<td colspan="3">
						<input type="text" name="codeFileName" class="formText" placeholder="例如: userFile"/>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						表名:
					</th>
					<td>
						<input type="text" name="tableName" class="formText" placeholder="例如: user或者user_detail"/>
						<label class="requireField">*</label>
					</td>
					<th>
                       	表名描述:
                    </th>
                    <td>
                        <input type="text" name="tableDescription" class="formText" placeholder="例如: 用户或者用户详情"/>
                        <label class="requireField">*</label>
                    </td>
				</tr>
				
			</table>
			<div align="center">
				<button type="button" id="addButton" value="新增" class="formButton">+</button>&nbsp;&nbsp;
				<button type="button" id="removeButton" value="删除" class="formButton">-</button>
			</div>
			</br>
			<div align="center">
				<input type="submit" class="formButton" value="完成添加" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>