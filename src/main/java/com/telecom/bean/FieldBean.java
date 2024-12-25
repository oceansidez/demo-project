package com.telecom.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于代码生成字段属性Bean
 *
 */
public class FieldBean {
	
	private String field;
	private String fieldU;
	private String dataType;
	private String sqlType;
	private String description;
	
	private Map<String, String> selectData = new HashMap<String, String>();

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getFieldU() {
		return fieldU;
	}

	public void setFieldU(String fieldU) {
		this.fieldU = fieldU;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getSelectData() {
		return selectData;
	}

	public void setSelectData(Map<String, String> selectData) {
		this.selectData = selectData;
	}
	
}
