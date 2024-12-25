package com.telecom.manage.service;

import com.telecom.base.BaseService;
import com.telecom.bean.FieldBean;
import com.telecom.bean.FieldBeanModel;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.entity.CodeSource;

import java.util.List;

public interface CodeSourceService extends BaseService<CodeSource>{

	/**
	 * 封装创建table的SQL语句
	 * @param tableName
	 * @param primaryKey
	 * @param fieldBeanList
	 */
	public String getCreateTableSql(String tableName, String primaryKey, List<FieldBean> fieldBeanList);
	
	/**
	 * 代码生成处理
	 * @param fieldBeanModel
	 * 
	 * @return
	 */
	public Boolean codeGenerate(Admin loginAdmin, Boolean isAll, FieldBeanModel fieldBeanModel);
	
	/**
	 * 根据文件名查询
	 * @param fileName
	 * @return
	 */
	public CodeSource getByFileName(String fileName);
}
