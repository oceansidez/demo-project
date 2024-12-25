package com.telecom.filter;

import com.telecom.bean.CommonConstant;
import com.telecom.config.WebConfig;
import com.telecom.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 过滤器---CSRF防护
 *
 */
@WebFilter(filterName = "csrfFilter", urlPatterns = "/*")
@Order(1)
public class CsrfFilter implements Filter {
	
	private final static Logger logger = LoggerFactory.getLogger(CsrfFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		WebConfig webConfig = (WebConfig) SpringUtil.getBean("webConfig");
		
		// 线上环境启用CSRF防护
		if(CommonConstant.Environment.ONLINE.equals(webConfig.getEnvironment())){
			HttpServletRequest request = (HttpServletRequest)servletRequest; 
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
			// 排除掉部分路径
	        if (isAffowedPath(path, webConfig)) {
	        	filterChain.doFilter(request, response);
	        	return;
	        }
	        
			String host = request.getHeader("host");
			String referer = request.getHeader("Referer");
			// Referer不为空才能正常访问
			if ((referer != null) && (referer.trim().startsWith(webConfig.getSystemUrl()))){
				// 验证host
				String validateHost = webConfig.getHost();
				if(validateHost.equals(host)){
					filterChain.doFilter(servletRequest, servletResponse);
				}
				else {
					logger.info("====================");
					logger.info("过滤原因：Host不一致");
					logger.info("当前请求:" + request.getRequestURL());
					logger.info("当前Host:" + host);
					logger.info("当前校验Host（配置文件）:" + validateHost);
					logger.info("当前referer:" + referer);
					logger.info("====================");
					response.setHeader("host", webConfig.getHost());
					response.setHeader("location", webConfig.getSystemUrl());
					response.sendRedirect(webConfig.getSystemUrl());
				}
			} 
			// Referer为空则直接跳转首页
			else {
				logger.info("====================");
				logger.info("过滤原因：Referer为null");
				logger.info("当前请求:" + request.getRequestURL());
				logger.info("当前Host:" + host);
				logger.info("当前Referer:" + referer);
				logger.info("====================");
				response.setHeader("host", webConfig.getHost());
				response.setHeader("location", webConfig.getSystemUrl());
				response.sendRedirect(webConfig.getSystemUrl());
			}	
		}
		// 本地环境不经过该过滤器
		else{
			HttpServletRequest request = (HttpServletRequest)servletRequest; 
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			filterChain.doFilter(request, response);
        	return;
		}
	}
	
	@Override
	public void destroy() {}

	/**
	 * 判断path是否在白名单内
	 * @param path
	 * @return
	 */
	private boolean isAffowedPath(String path, WebConfig webConfig){
		String suffixStr = webConfig.getCsrfAllowPath();
		String[] suffixArray = suffixStr.split(",");
		List<String> suffixList = new ArrayList<>(Arrays.asList(suffixArray));
		suffixList.add("");
		for (String afflowedPath : suffixList) {
			if(afflowedPath.contains("/**")){
				afflowedPath = afflowedPath.replace("/**", "");
				if(path.startsWith(afflowedPath)){
					return true;
				}
			}else{
				if(path.equals(afflowedPath)){
					return true;
				}
			}
		}
		return false;
	}
}
