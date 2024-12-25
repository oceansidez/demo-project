package com.telecom.manage.entity;

import com.telecom.base.BaseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;

@Table(name = "t_admin")
public class Admin extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = -2410919001253099860L;

	private String mobile;
	private String username;
	private String name;
	private String password;
	private String headImg;
	private Date loginDate;
	private String loginIp;
	private Boolean isLock;
	private Integer loginFailureCount;
	private Date lockDate;
	
	@Transient
	private Collection<GrantedAuthority> authorities;// 角色信息
	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Boolean getIsLock() {
		return isLock;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockDate() {
		return lockDate;
	}

	public void setLockDate(Date lockDate) {
		this.lockDate = lockDate;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
	
	public static Admin getLoginAdmin(HttpSession session) {
		try {
			// 若初次请求则视为无登录
			if(session == null) return null;
			
			SecurityContextImpl securityContextImpl = (SecurityContextImpl) 
					session.getAttribute("SPRING_SECURITY_CONTEXT");
			if(securityContextImpl == null) {
				return null;
			}
			
			Authentication authentication = securityContextImpl.getAuthentication();
			if (authentication == null) {
				return null;
			}
			Object principal = securityContextImpl.getAuthentication().getPrincipal();
			if (principal == null || !(principal instanceof Admin)) {
				return null;
			} else {
				return (Admin) principal;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
