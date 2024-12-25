package com.telecom.manage.controller.manage;

import com.telecom.base.BaseController;
import com.telecom.bean.ConfigFileName;
import com.telecom.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RefreshScope
@Controller
@RequestMapping(value = "menu")
public class MenuController extends BaseController {

	@Value("${spring.profiles.include}")
	private String allConfigFile;
	
	@Value("${default.application.prefix}")
	private String prefix;
	
	@Value("${default.application.suffix}")
	private String suffix;
	
	@Autowired
	WebConfig webConfig;
	
	@GetMapping("mainMenu")
	public String mainMenu() {
		return "/manage/menu_main_menu";
	}
	
	@GetMapping("index")
    public String index() {
    	return "/manage/menu_index";
    }
	
	// 获取配置文件列表（系统设置中会使用）
	@ModelAttribute
	public void getConfigFileList(ModelMap model) {
		String[] array = allConfigFile.split(",");
		List<ConfigFileName> list = new ArrayList<ConfigFileName>();
		ConfigFileName cfn = new ConfigFileName();
		cfn.setDisplayName(prefix);
		cfn.setRealName(prefix + suffix);
		list.add(cfn);
		if(array.length > 0){
			for(String name : array){
				ConfigFileName temp = new ConfigFileName();
				temp.setDisplayName(name);
				temp.setRealName(prefix + "-" + name + suffix);
				list.add(temp);
			}
		}
		model.addAttribute("configFileList", list);
	}
	
	// 获取是否用Iframe多标签
	@ModelAttribute
	public void getIsUseIframe(ModelMap model) {
		Boolean flag = webConfig.getIsUseIframe();
		model.addAttribute("isUseIframe", flag);
	}
}
