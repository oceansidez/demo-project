package com.telecom.manage.controller.manage;

import com.github.pagehelper.util.StringUtil;
import com.telecom.base.BaseController;
import com.telecom.bean.CommonConstant;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseErrorController extends BaseController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(@RequestParam(value = "errorType", required = false) String errorType,
    		HttpServletRequest request, ModelMap model){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        
        if(StringUtil.isNotEmpty(errorType)){
        	switch (errorType) {
				case CommonConstant.InterceptorErrorType.FILE:
					setError(model, "上传文件错误", null);
					return ERROR;
				case CommonConstant.InterceptorErrorType.LOGIN:
					setError(model, "用户已存在", null);
					return ERROR;
				default:
					break;
			}
        }
        
        if(statusCode != null){
        	switch (statusCode) {
			case 400:
				return "error/400";
			case 403:
				return "error/403";
			case 404:
				return "error/404";
			default:
				return "error/500";
			}
        }
        
		return "error/500";

    }

    public String getErrorPath() {
        return "/error";
    }
    
    
}
