package com.telecom.interceptor;

import com.telecom.bean.CommonConstant;
import com.telecom.bean.FileType;
import com.telecom.config.WebConfig;
import com.telecom.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 文件上传判断类型拦截器
 * FileUploadInterceptor
 *
 */
@Component
public class FileUploadInterceptor implements HandlerInterceptor {
	
	@Autowired
	WebConfig webConfig;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		boolean flag = true;
		
		// 判断是否为文件上传请求
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> files = multipartRequest.getFileMap();
			Iterator<String> iterator = files.keySet().iterator();
			// 对多部件请求资源进行遍历
			while (iterator.hasNext()) {
				String formKey = (String) iterator.next();
				MultipartFile multipartFile = multipartRequest.getFile(formKey);
				/***** 暂不使用，判断不够精准*****
				// 判断是否为限制文件魔数
				if (!checkFileType(multipartFile)) {
					flag = false;
				}
				*/
				
				// 判断是否为限制文件后缀
				if (!checkFileType(multipartFile)) {
					flag = false;
				}
				
				// 判断是否为限制文件后缀
				if (!checkFileExtension(multipartFile)) {
					flag = false;
				}
				
				if (!flag) {
					response.sendRedirect(request.getContextPath()+"/error?errorType=" + CommonConstant.InterceptorErrorType.FILE);
					return false;
				} else {
					return true;
				}
			}
		}

		return flag;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// System.out.println("--------------处理请求完成后视图渲染之前的处理操作---------------");
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// System.out.println("---------------视图渲染之后的操作-------------------------");
	}

	/**
	 * 判断是否为允许的上传文件（魔数值判断）,true表示允许
	 */
	@SuppressWarnings("unused")
	private boolean checkFileMagicNumber(MultipartFile multipartFile) {
		try {
			String suffixStr = webConfig.getFileUploadAllowExtensions();
			String[] suffixArray = suffixStr.split(",");
			List<String> suffixList = Arrays.asList(suffixArray);
			
			// 获取文件魔数
			InputStream inputStream = multipartFile.getInputStream();
			FileType type = FileUtil.getType(inputStream);
			String suffix = type.toString().trim().toLowerCase();
			if (suffixList.contains(suffix)) {
				return true;
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 判断是否为允许的上传文件类型,true表示允许
	 */
	private boolean checkFileType(MultipartFile multipartFile) {
		String suffixStr = webConfig.getFileUploadAllowTypes();
		String[] suffixArray = suffixStr.split(",");
		List<String> suffixList = Arrays.asList(suffixArray);
		
		// 获取文件后缀
		String fileType = multipartFile.getContentType();
		if (suffixList.contains(fileType)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为允许的上传文件后缀,true表示允许
	 */
	private boolean checkFileExtension(MultipartFile multipartFile) {
		
		String suffixStr = webConfig.getFileUploadAllowExtensions();
		String[] suffixArray = suffixStr.split(",");
		List<String> suffixList = Arrays.asList(suffixArray);
		
		// 获取文件后缀
		String fileName = multipartFile.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
		if (suffixList.contains(suffix.trim().toLowerCase())) {
			return true;
		}
		return false;
	}
}
