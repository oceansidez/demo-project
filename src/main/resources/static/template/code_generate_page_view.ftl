<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看${fieldBeanModel.tableDescription}</title>
<link href="${r'${base}'}/static/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${r'${base}'}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${r'${base}'}/static/common/js/jquery.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/admin/js/base.js"></script>
<script type="text/javascript" src="${r'${base}'}/static/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $tab = $("#tab");

	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
});
</script>
</head>
<body class="input admin">
	<div class="bar">
		查看${fieldBeanModel.tableDescription}
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="基本信息" hidefocus />
			</li>
		</ul>
		<div class="inputTable_outer">
			<table class="inputTable tabContent">
                <#list fieldList as field>
				<tr>
					<th>
						${field.description}: 
					</th>
					<td>
                        <#if field.dataType == "Date">
                        <span title="${r'${'}${methodName}.${field.field}?string("yyyy-MM-dd HH:mm")}">${r'${'}${methodName}.${field.field}?string("yyyy-MM-dd HH:mm")}</span>
                        <#else>
						${r'${('}${methodName}.${field.field})!}
                        </#if>
					</td>
				</tr>
                </#list>
			</table>
			<div class="buttonArea">
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</div>
	</div>
</body>
</html>