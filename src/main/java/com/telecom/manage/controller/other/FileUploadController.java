package com.telecom.manage.controller.other;

import com.telecom.base.BaseController;
import com.telecom.config.WebConfig;
import com.telecom.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "fileUpload")
public class FileUploadController extends BaseController {

	@Autowired
	WebConfig webConfig;
	
	// 首页
	@GetMapping("index")
	public String index(ModelMap model) {
		return "/other/file_upload_index";
	}

	// 上传
	@ResponseBody
	@RequestMapping("/upload")
	public String upload(@RequestParam("uploadFile") MultipartFile file, ModelMap modelMap) {

		// 判断文件是否为空
		if (file == null || file.isEmpty()) {
			return ajax(Status.error, "文件为空");
		}
		
		// 获取文件名
		String fileName = file.getOriginalFilename();
		try {
			FileUtil.uploadFile(file, webConfig.getUploadPath() + "/file/", fileName);
		} catch (Exception e) {
			return ajax(Status.error, "上传处理异常");
		}
		return ajax(Status.success, "上传成功");
	}
}
