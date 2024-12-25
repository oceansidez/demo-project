package com.telecom.manage.controller.other;

import com.telecom.base.BaseController;
import com.telecom.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "imgCropper")
public class ImgCropperController extends BaseController {

	@Autowired
	WebConfig webConfig;
	
	// 图片裁剪
	@GetMapping("index")
	public String index(ModelMap model) {
		return "/other/img_cropper_index";
	}
	
	// 获取Upload地址
	@ModelAttribute
	public void getUploadUrl(ModelMap model) {
		model.addAttribute("uploadUrl", webConfig.getUploadPath());
	}
}
