package com.telecom.filter;

import com.telecom.bean.CommonConstant;
import com.telecom.config.WebConfig;
import com.telecom.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * 过滤器---XSS防护与SQL注入防护
 *
 */
@WebFilter(filterName = "xssFilter", urlPatterns = "/*")
@Order(2)
public class XssFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	
    	WebConfig webConfig = (WebConfig) SpringUtil.getBean("webConfig");
    	
		// 线上环境启用XSS防护
		if (CommonConstant.Environment.ONLINE.equals(webConfig.getEnvironment())) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String enctype = httpRequest.getContentType();
			String xssRtfNameList = webConfig.getXssRtfNameList();
			String[] xssRtfNameArray = xssRtfNameList.split(",");
			Set<String> rtfList = new HashSet<String>(Arrays.asList(xssRtfNameArray));
			// 含文件上传的请求
			if (StringUtils.isNotBlank(enctype) && enctype.contains("multipart/form-data")) {
				CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
						httpRequest.getSession().getServletContext());
				MultipartHttpServletRequest multipartRequest = commonsMultipartResolver
						.resolveMultipart(httpRequest);
				XssWrapper xssRequest = new XssWrapper(multipartRequest, rtfList);
				chain.doFilter(xssRequest, response);
			} 
			// 常规请求
			else {
				XssWrapper xssRequest = new XssWrapper((HttpServletRequest) httpRequest, rtfList);
				chain.doFilter(xssRequest, response);
			}
		}
		// 本地环境不经过该过滤器
		else{
			chain.doFilter(request, response);
		}
    }

    @Override
    public void destroy() {}

}
