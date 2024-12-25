
 package com.telecom.manage.controller.manage;

 import com.telecom.base.BaseController;
 import com.telecom.config.WebConfig;
 import com.telecom.util.FileUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.multipart.MultipartFile;

 import java.util.HashMap;
 import java.util.Map;

@Controller
@RequestMapping(value = "system")
public class SystemController extends BaseController {

	@Autowired
	WebConfig webConfig;
	
	// Logo设置
	@GetMapping("logo")
	public String logo(ModelMap model) {
		return "/manage/system_logo";
	}
	
	// 上传Logo图
	@ResponseBody
	@RequestMapping("ajaxUpload")
	public String ajaxUpload(@RequestParam(value = "uploadImg", required=false) MultipartFile uploadImg){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		try {
			if (uploadImg == null) {
				jsonMap.put("error", 1);
				jsonMap.put("message", "请选择上传文件!");
				return ajax(jsonMap);
			}

			FileUtil.uploadFile(uploadImg, webConfig.getUploadPath() + "/logo/", "");
			String filePath = "/wechat/" + uploadImg.getOriginalFilename();
			jsonMap.put("error", 0);
			jsonMap.put("url", filePath);
			return ajax(jsonMap);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("error", 1);
			jsonMap.put("message", "处理异常!");
			return ajax(jsonMap);
		}

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
	
}
