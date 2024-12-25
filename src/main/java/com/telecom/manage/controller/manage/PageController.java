package com.telecom.manage.controller.manage;

import com.telecom.base.BaseController;
import com.telecom.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "page")
public class PageController extends BaseController {
	
	@Autowired
	WebConfig webConfig;
	
	// 首页
	@GetMapping("index")
    public String index() {
    	return "/manage/page_index";
    }
	
	// 主界面
	@GetMapping("main")
    public String main() {
    	return "/manage/page_main";
    }
	
	// 后台顶部
	@GetMapping("header")
	public String header() {
		return "/manage/page_header";
	}
	
	// 后台菜单
	@GetMapping("menu")
	public String menu() {
		return "/manage/page_menu";
	}
	
	// 后台中间(显示/隐藏菜单)
	@GetMapping("middle")
	public String middle() {
		return "/manage/page_middle";
	}
	
	// 获取ManageName/manage/page_middle
	@ModelAttribute
	public void getManageName(ModelMap model) {
		String manageName = webConfig.getManageName();
		model.addAttribute("manageName", manageName);
	}
	
	// 获取是否用Iframe多标签
	@ModelAttribute
	public void getIsUseIframe(ModelMap model) {
		Boolean flag = webConfig.getIsUseIframe();
		model.addAttribute("isUseIframe", flag);
	}
}
