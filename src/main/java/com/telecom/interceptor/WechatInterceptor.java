package com.telecom.interceptor;

import com.telecom.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 微信公众号拦截器
 * WechatInterceptor
 *
 */
@Component
public class WechatInterceptor implements HandlerInterceptor {
	
	@Autowired
	WebConfig webConfig;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取session
        HttpSession session = request.getSession(true);
        // 判断用户ID是否存在，不存在就跳转到登录界面
        if(session.getAttribute("currentLoginMember") == null){
    		response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }else{
            session.setAttribute("currentLoginMember", session.getAttribute("currentLoginMember"));
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
