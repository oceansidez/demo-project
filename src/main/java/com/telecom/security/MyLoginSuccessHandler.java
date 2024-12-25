package com.telecom.security;

import com.telecom.manage.entity.Admin;
import com.telecom.manage.service.AdminService;
import com.telecom.util.CommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 *	登录成功的自定义处理 
 *	LoginSuccessHandler
 *
 */
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {  
      
    protected Log logger = LogFactory.getLog(getClass());  
      
    @Autowired
	AdminService adminService;
    
    private String myTargetUrl;
    
    public MyLoginSuccessHandler(String targetUrl) {
		this.myTargetUrl = targetUrl;
	}
      
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }) 
    public void onAuthenticationSuccess(HttpServletRequest request,  
            HttpServletResponse response, Authentication authentication)  
            throws IOException, ServletException {  
        this.saveLoginInfo(request, authentication);  
        super.setDefaultTargetUrl(myTargetUrl);
        super.setAlwaysUseDefaultTargetUrl(true);
        super.onAuthenticationSuccess(request, response, authentication);    
    }  
      
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
    public void saveLoginInfo(HttpServletRequest request, Authentication authentication){  
		Admin user = (Admin) authentication.getPrincipal();  
        try {  
        	Admin loginAdmin = adminService.get(user.getId());
            String ip = CommonUtil.getRemoteHost(request);  
            Date date = new Date();  
            loginAdmin.setLoginDate(date);
            loginAdmin.setLoginIp(ip);  
            adminService.update(loginAdmin);
            
        } catch (DataAccessException e) {  
        	e.printStackTrace();
            if(logger.isWarnEnabled()){  
                logger.error("无法更新用户登录信息至数据库");  
            }  
        }  
    }  
      
	public String getMyTargetUrl() {
		return myTargetUrl;
	}

	public void setMyTargetUrl(String myTargetUrl) {
		this.myTargetUrl = myTargetUrl;
	}  
  
}  
