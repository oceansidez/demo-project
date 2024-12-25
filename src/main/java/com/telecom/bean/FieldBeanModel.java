package com.telecom.bean;

import java.util.List;

/**
 * 用于代码生成table bean
 *
 */
public class FieldBeanModel {

	private String tableName;
	private String entityName;
	private String codeFileName;
	private String tableDescription;

	private List<FieldBean> fieldBeanList;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getTableDescription() {
		return tableDescription;
	}

	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}

	public List<FieldBean> getFieldBeanList() {
		return fieldBeanList;
	}

	public void setFieldBeanList(List<FieldBean> fieldBeanList) {
		this.fieldBeanList = fieldBeanList;
	}

	public String getCodeFileName() {
		return codeFileName;
	}

	public void setCodeFileName(String codeFileName) {
		this.codeFileName = codeFileName;
	}

}
