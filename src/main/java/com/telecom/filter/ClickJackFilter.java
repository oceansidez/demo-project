package com.telecom.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器---点击劫持防护
 *
 */
@WebFilter(filterName = "clickJackFilter", urlPatterns = "/*", 
	initParams = { @WebInitParam(name = "mode", value = "SAMEORIGIN") })
@Order(3)
public class ClickJackFilter implements Filter {

	private String mode = "DENY";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("X-FRAME-OPTIONS", mode);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String configMode = config.getInitParameter("mode");
		if (configMode != null) {
			mode = configMode;
		}
	}

}
