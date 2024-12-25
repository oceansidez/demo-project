package com.telecom.security;

import com.telecom.manage.entity.Admin;
import com.telecom.manage.entity.Role;
import com.telecom.manage.service.AdminRoleService;
import com.telecom.manage.service.AdminService;
import com.telecom.manage.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminDetailsService implements
		UserDetailsService {

	private final static Logger logger = LoggerFactory.getLogger(AdminDetailsService.class);
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
    AdminRoleService adminRoleService;

	@Transactional
	public Admin loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Admin admin = adminService.getByUsername(username);
		if (admin == null) {
			logger.error("用户名" + username + "不存在");
			throw new BadCredentialsException("用户名或密码错误");
		}
		admin.setAuthorities(getGrantedAuthorities(admin));
		return admin;
	}

	private Collection<GrantedAuthority> getGrantedAuthorities(Admin admin) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		List<Role> roleList = adminRoleService.getRoleList(admin.getId());
		for (Role role : roleList) {
			for (String authority : role.getAuthorityList()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(authority));
			}
		}
		return grantedAuthorities;
	}
	
	// 获取当前登录用户
	public Admin getLoginAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof Admin)) {
			return null;
		} else {
			return (Admin) principal;
		}
	}
}