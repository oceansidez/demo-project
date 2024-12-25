package com.telecom.manage.controller.other;

import com.telecom.base.BaseController;
import com.telecom.bean.FieldBean;
import com.telecom.bean.FieldBeanModel;
import com.telecom.bean.Pager;
import com.telecom.config.CodeGenerateConfig;
import com.telecom.config.WebConfig;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.entity.CodeSource;
import com.telecom.manage.service.CodeSourceService;
import com.telecom.util.FileUtil;
import com.telecom.util.FreemarkerTemplateUtil;
import com.telecom.util.ZipUtil;
import com.telecom.util.database.DataBaseUtil;
import com.telecom.util.database.TableHelper;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "codeSource")
public class CodeSourceController extends BaseController {
	
	private final String PAGE_PRE = "code_source_";
	private final String CODE_FROM_DB = "AllCode";

    @Autowired
    CodeGenerateConfig codeGenerateConfig;
    
    @Autowired
    WebConfig webConfig;
    
    @Autowired
	FreemarkerTemplateUtil freemarkerTemplateUtil;
    
    @Autowired
    CodeSourceService codeSourceService;

    @GetMapping("list")
    public String list(Pager pager, ModelMap modelMap) {
        pager = codeSourceService.findPager(pager);
        modelMap.put("pager", pager);
        modelMap.put("createPath", codeGenerateConfig.getCreatePath());
        return "/other/" + PAGE_PRE + "list";
    }

    @GetMapping("add")
    public String add() {
        return "/other/" + PAGE_PRE + "input";
    }
    
    @GetMapping("db")
    public String db(ModelMap modelMap) {
    	try {
    		if(codeSourceService.getByFileName(CODE_FROM_DB) != null){
    			setError(modelMap, "已生成代码，请删除后重新生成", null);
                return ERROR;
    		}
    		
    		// 获取所有表
    		Admin admin = Admin.getLoginAdmin(request.getSession());
    		List<String> tableList = TableHelper.table(
        			codeGenerateConfig.getJdbcUrl(),
        			codeGenerateConfig.getUsername(),
        			codeGenerateConfig.getPassword());
    		if (tableList != null && tableList.size() > 0) {
    			// 遍历table表
    			for(String table : tableList){
    				
    				// 将column栏位转换为FieldBeanModel专用bean对象
    				FieldBeanModel fbm = new FieldBeanModel();
					fbm.setCodeFileName(CODE_FROM_DB);
					fbm.setTableName(DataBaseUtil.tableToEntityName(table, codeGenerateConfig.getTablePrefix()));
					fbm.setTableDescription(table);
					List<FieldBean> fbList = new ArrayList<FieldBean>();
					
    				List<Map<String, String>> list = TableHelper.getField(table, 
	    				codeGenerateConfig.getJdbcUrl(),
	        			codeGenerateConfig.getUsername(),
	        			codeGenerateConfig.getPassword());
    				
    				if(list != null && list.size() > 0){
    					for(Map<String, String> map : list){
    						// id、create_date、modify_date自动跳过
    						if(map.get("field").equals("id")||
    								map.get("field").equals("create_date") ||
    								map.get("field").equals("modify_date")){
    							continue;
    						}
    						
        					FieldBean fb = new FieldBean();
        					fb.setField(map.get("field"));
        					fb.setDataType(DataBaseUtil.mysqlTypeToClassType(map.get("type")));
        					fb.setSqlType(map.get("type"));
        					fb.setDescription(map.get("comment"));
        					fbList.add(fb);
        				}
    				}
    				fbm.setFieldBeanList(fbList);
    				codeSourceService.codeGenerate(admin, true, fbm);
    			}
    			
    			// 将所有文件压缩至zip文件
    			String uploadPath = webConfig.getUploadPath() + "/" + codeGenerateConfig.getCreatePath() + "/";
	            String zipName = CODE_FROM_DB + ".zip";
	            String fileUrl = uploadPath + zipName;
	            try {
	                ZipUtil.createZipFiles(uploadPath + CODE_FROM_DB, fileUrl);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
    			
    			CodeSource codeSource = new CodeSource();
 	            codeSource.setAdminId(admin.getId());
 	            codeSource.setAdminName(admin.getName());
 	            codeSource.setFileUrl(CODE_FROM_DB + ".zip");
 	            codeSource.setFileName(CODE_FROM_DB);
 	            codeSourceService.save(codeSource);
    		}
    		else{
    			setError(modelMap, "数据库没有任何表", null);
                return ERROR;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			setError(modelMap, "处理异常", null);
            return ERROR;
		}
		setSuccess(modelMap, "已从数据库读取生成", "codeSource/list");
		return SUCCESS;
    }
    
    @GetMapping("dataStructure")
    public String dataStructure(ModelMap modelMap) {
    	Reflections f = new Reflections("com.telecom.entity");
		Set<Class<?>> set = f.getTypesAnnotatedWith(Table.class);
		List<Class<?>> result = new ArrayList<Class<?>>(set);
		for(Class<?> clazz : result) {
			System.out.println(clazz.getName());
		}
    	
		setSuccess(modelMap, "已将数据结构导出为Excel文档", "codeSource/list");
		return SUCCESS;
    }

    @PostMapping("save")
    public String save(FieldBeanModel fieldBeanModel, ModelMap modelMap) {
        if (StringUtils.isEmpty(fieldBeanModel.getTableName())) {
            setError(modelMap, "请填写表名", null);
            return ERROR;
        }
        //codeSourceService.getByFileName 根据名称查出所有字段  fieldBeanModel.getCodeFileName() 前端提交过来的字段
        if(codeSourceService.getByFileName(fieldBeanModel.getCodeFileName()) != null){
			setError(modelMap, "该表已生成代码，请删除后重新生成", null);
            return ERROR;
		}
        
        Admin admin = Admin.getLoginAdmin(request.getSession());
        Boolean flag = codeSourceService.codeGenerate(admin, false, fieldBeanModel);
        if(flag){
            setSuccess(modelMap, "创建成功", "codeSource/list");
            return SUCCESS;
        } else {
            setError(modelMap, "处理异常", null);
            return ERROR;
        }
    }
    
    @PostMapping("delete")
    @ResponseBody
    public String delete(@RequestParam(value = "ids") String[] ids) {
        for (String id : ids) {
            CodeSource codeSource = codeSourceService.get(id);
            
            // 删除zip文件 getFileUrl 文件名
    		String zipPath = codeSource.getFileUrl();
    		File zipFile = new File(webConfig.getUploadPath() + "/" +
    				codeGenerateConfig.getCreatePath() + "/" + zipPath);
    		FileUtil.delFile(zipFile);
			System.gc();
    		
    		// 删除代码文件夹
    		String filesPath = codeSource.getFileName();
    		File files = new File(webConfig.getUploadPath() + "/" +
    				codeGenerateConfig.getCreatePath() + "/" + filesPath);
    		FileUtil.delFile(files);
    		System.gc();
    		//将本地文件删除，并且将数据库中的文件也删除
    		codeSourceService.delete(id);
        }
        return ajax(Status.success, "删除成功！");
    }
    
    // 获取展示地址
 	@ModelAttribute
 	public void getViewUrl(ModelMap model) {
 		model.addAttribute("viewUrl", webConfig.getViewPath());
 	}
}
