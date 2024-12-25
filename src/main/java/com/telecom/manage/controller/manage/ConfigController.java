package com.telecom.manage.controller.manage;

import com.telecom.base.BaseController;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping(value = "config")
public class ConfigController extends BaseController {
	
	// 配置文件获取
	@GetMapping("index")
    public String index(@RequestParam(value="propertiesFileName") 
    	String propertiesFileName, ModelMap model) {
		String rootPath = null;
		try {
			rootPath = ResourceUtils.getURL("classpath:").getPath();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		File propertiesFile = new File(rootPath + "/" + propertiesFileName);
		String propertiesContent = null;
		try {
			propertiesContent = FileUtils.readFileToString(propertiesFile, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("content", unicodeToUtf8(propertiesContent));
		model.addAttribute("propertiesFileName", propertiesFileName);
    	return "/manage/config_index";
    }
	
	// 配置文件更新
	@PostMapping("update")
	public String update(@RequestParam(value="content") String content,
			@RequestParam(value="propertiesFileName") String propertiesFileName,
			ModelMap model) {
		String rootPath = null;
		try {
			rootPath = ResourceUtils.getURL("classpath:").getPath();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		File propertiesFile = new File(rootPath + "/" + propertiesFileName);
		try {
			FileUtils.writeStringToFile(propertiesFile, content, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setSuccess(model, "保存成功", "config/refresh?propertiesFileName=" + propertiesFileName);
		return SUCCESS;
	}
	
	// 配置文件刷新
	@GetMapping("refresh")
	public String refresh(@RequestParam(value="propertiesFileName") String propertiesFileName,
			ModelMap model){
		model.addAttribute("propertiesFileName", propertiesFileName);
    	return "/manage/config_refresh";
	}


	/**
	 * unicode转utf-8
	 * @param theString
	 * @return
	 */
	public static String unicodeToUtf8(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException(
										"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}


}
