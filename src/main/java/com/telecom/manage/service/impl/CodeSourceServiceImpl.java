package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.bean.FieldBean;
import com.telecom.bean.FieldBeanModel;
import com.telecom.config.CodeGenerateConfig;
import com.telecom.config.WebConfig;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.entity.CodeSource;
import com.telecom.manage.mapper.CodeSourceMapper;
import com.telecom.manage.service.CodeSourceService;
import com.telecom.util.CommonUtil;
import com.telecom.util.FreemarkerTemplateUtil;
import com.telecom.util.TxtReaderWriteUtil;
import com.telecom.util.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CodeSourceServiceImpl extends BaseServiceImpl<CodeSource> implements CodeSourceService {
    
    @Autowired
    FreemarkerTemplateUtil freemarkerTemplateUtil;
    
    @Autowired
    CodeGenerateConfig codeGenerateConfig;
    
    @Autowired
    WebConfig webConfig;
    
    @Autowired
    CodeSourceMapper codeSourceMapper;
    
    @Override
    public BaseMapper<CodeSource> getBaseMapper() {
    	return codeSourceMapper;
    }

    @Override
	public CodeSource getByFileName(String fileName) {
    	return codeSourceMapper.getByFileName(fileName);
	}
    
	@Override
	public String getCreateTableSql(String tableName, String primaryKey, List<FieldBean> fieldBeanList) {
		String columns = "`id` varchar(45) NOT NULL,"
				+ " `create_date` DATETIME DEFAULT NULL,"
				+ " `modify_date` DATETIME DEFAULT NULL,";
		for (FieldBean fieldBean : fieldBeanList) {
			columns += "`" + fieldBean.getField() + "`" + " "
					+ fieldBean.getSqlType() + " " + "DEFAULT NULL COMMENT '"
					+ fieldBean.getDescription() + "',";
		}
		String sql = "DROP TABLE IF EXISTS `t_" + tableName + "`; "
				+ "CREATE TABLE `t_" + tableName + "` (" + columns
				+ "PRIMARY KEY (`id`));";

		return sql;
	}

	@Override
	public Boolean codeGenerate(Admin loginAdmin, Boolean isAll, FieldBeanModel fieldBeanModel) {
		try {
			String tableName = fieldBeanModel.getTableName();
	        String entityName = CommonUtil.toUpper(tableName);
	        List<FieldBean> fieldBeanList = fieldBeanModel.getFieldBeanList();
	        if (fieldBeanList != null && fieldBeanList.size() > 0) {
	            for (FieldBean fieldBean : fieldBeanList) {
	                fieldBean.setFieldU(CommonUtil.toUpper(fieldBean.getField()));
	                fieldBean.setField(CommonUtil.toLowerCaseFirstOne(CommonUtil.toUpper(fieldBean.getField())));
	            }
	            
	            // 加载Map
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("package", codeGenerateConfig.getPackageName());
	            map.put("controllerPackage", codeGenerateConfig.getControllerPackage());
	            map.put("controllerPrefix", codeGenerateConfig.getControllerPrefix());
	            map.put("entityPackage", codeGenerateConfig.getEntityPackage());
	            map.put("servicePackage", codeGenerateConfig.getServicePackage());
	            map.put("serviceImplPackage", codeGenerateConfig.getServiceImplPackage());
	            map.put("mapperPackage", codeGenerateConfig.getMapperPackage());
	            map.put("fieldList", fieldBeanList);
	            map.put("fieldBeanModel", fieldBeanModel);
	            map.put("tableName", tableName);
	            map.put("entityName", entityName);
	            map.put("methodName", CommonUtil.toLowerCaseFirstOne(entityName));

	            // 组装上传路径
	            String uploadPath = webConfig.getUploadPath() + "/" + codeGenerateConfig.getCreatePath() + "/";
	            String entityPath = uploadPath + fieldBeanModel.getCodeFileName();
	            String servicePath = uploadPath + fieldBeanModel.getCodeFileName();
	            String serviceImplPath = uploadPath + fieldBeanModel.getCodeFileName();
	            String mapperPath = uploadPath + fieldBeanModel.getCodeFileName();
	            String controllerPath = uploadPath + fieldBeanModel.getCodeFileName();
	            String sqlPath = uploadPath + fieldBeanModel.getCodeFileName();
	            String ftlPath = uploadPath + fieldBeanModel.getCodeFileName();
	            
	            // 若是从数据库读取，则将文件分类
	            if(isAll){
	            	 entityPath += "/entity";
		             servicePath += "/service";
		             serviceImplPath += "/serviceImpl";
		             mapperPath += "/mapper";
		             controllerPath += "/controller";
		             sqlPath += "/sql";
		             ftlPath += "/page";
	            }
	            
	            // 生成Entity实体类
                freemarkerTemplateUtil.generateFile("code_generate_entity.ftl",
                		entityPath, entityName + ".java", map);

	            // 生成Service服务类
                freemarkerTemplateUtil.generateFile("code_generate_service.ftl",
                		servicePath, entityName + "Service.java", map);

	            // 生成ServiceImpl服务实现类
                freemarkerTemplateUtil.generateFile("code_generate_service_impl.ftl",
                		serviceImplPath, entityName + "ServiceImpl.java", map);

	            // 生成Mapper类
                freemarkerTemplateUtil.generateFile("code_generate_mapper.ftl",
                		mapperPath, entityName + "Mapper.java", map);

	            // 生成Controller控制类
                freemarkerTemplateUtil.generateFile("code_generate_controller.ftl",
                		controllerPath, entityName + "Controller.java", map);

                if(!isAll){
                	 // 生成SQL语句
    	            String sqlFilePath = sqlPath + "/" + entityName + "Sql.sql";
    	            File file = new File(sqlFilePath);
    	            TxtReaderWriteUtil.createFile(file);
    	            String content = this.getCreateTableSql(tableName, "", fieldBeanList);
    	            TxtReaderWriteUtil.writeTxtFile(content, file);
                }

	            // 生成Freemarker的ftl文件
                freemarkerTemplateUtil.generateFile("code_generate_page_view.ftl",
                		ftlPath, tableName + "_view.ftl", map);
                freemarkerTemplateUtil.generateFile("code_generate_page_input.ftl",
                		ftlPath, tableName + "_input.ftl", map);
                freemarkerTemplateUtil.generateFile("code_generate_page_list.ftl",
                		ftlPath, tableName + "_list.ftl", map);

	            // 若是单个生成，则保存并压缩
	            // 若是从数据库统一读取，则无需保存，完成后统一保存并压缩
	            if(!isAll){
	            	
	            	// 将所有文件压缩至zip文件
		            String zipName = fieldBeanModel.getCodeFileName() + ".zip";
		            String fileUrl = uploadPath + zipName;
		            try {
		                ZipUtil.createZipFiles(uploadPath + fieldBeanModel.getCodeFileName(), fileUrl);
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
	            	
		            // 保存
	            	CodeSource codeSource = new CodeSource();
	 	            codeSource.setAdminId(loginAdmin.getId());
	 	            codeSource.setAdminName(loginAdmin.getName());
	 	            codeSource.setFileUrl(zipName);
	 	            codeSource.setFileName(fieldBeanModel.getCodeFileName());
	 	            this.save(codeSource);
	            }
	           
	            return true;
	        } else return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

}
