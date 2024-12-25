package com.telecom.manage.controller.other;

import com.telecom.base.BaseController;
import com.telecom.config.WebConfig;
import com.telecom.util.FreemarkerTemplateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "pageTemplate")
public class PageTemplateController extends BaseController {

	@Autowired
	WebConfig webConfig;
	
	@Autowired
	FreemarkerTemplateUtil freemarkerTemplateUtil;
	
	// 展示模板
	@GetMapping("index")
	public String index(ModelMap model) {
		String rootPath = null;
		try {
			rootPath = ResourceUtils.getURL("classpath:").getPath();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		File templateFile = new File(rootPath + "/static/template/page_template.ftl");
		String templateFileContent = null;
		try {
			templateFileContent = FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("content", templateFileContent);
		return "/other/page_template_index";
	}
	
	// 保存模板
	@PostMapping("submit")
	public String submit(@RequestParam(value="content") String content, ModelMap model) {
		String rootPath = null;
		try {
			rootPath = ResourceUtils.getURL("classpath:").getPath();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		File templateFile = new File(rootPath + "/static/template/page_template.ftl");
		try {
			FileUtils.writeStringToFile(templateFile, content, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSuccess(model, "保存成功", "pageTemplate/index");
		return SUCCESS;
	}
	
	// 测试按模板生成文件
	@PostMapping("generate")
	public String generate(ModelMap model) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sourceUrl", "http://www.baidu.com");
			map.put("imgUrl", "https://www.baidu.com/img/bd_logo1.png");
			map.put("statScript", "<script>console.log(1234)</script>");
			freemarkerTemplateUtil.generateFile("page_template.ftl", webConfig.getUploadPath(), "index.html", map);
			
		} catch (Exception e) {
			e.printStackTrace();
			setError(model, "保存失败", "pageTemplate/index");
			return ERROR;
		}
		setSuccess(model, "保存成功", "pageTemplate/index");
		return SUCCESS;
	}

	// 获取展示地址
	@ModelAttribute
	public void getViewUrl(ModelMap model) {
		model.addAttribute("viewUrl", webConfig.getViewPath());
	}
	
	// 获取FTP地址
	@ModelAttribute
	public void getFtpUrl(ModelMap model) {
		model.addAttribute("ftpUrl", webConfig.getFtp());
	}
	
	// 获取Upload地址
	@ModelAttribute
	public void getUploadUrl(ModelMap model) {
		model.addAttribute("uploadUrl", webConfig.getUploadPath());
	}
}
