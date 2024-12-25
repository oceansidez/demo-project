package com.telecom.manage.service;

import com.telecom.base.BaseService;
import com.telecom.manage.entity.Admin;

import java.util.List;

public interface AdminService extends BaseService<Admin>{

	/**
	 * 根据用户名username查找用户
	 * @param username
	 * @return
	 */
    public Admin getByUsername(String username);
    
	/**
	 * 根据电话查找用户
	 * @param username
	 * @return
	 */
    public Admin getByMobile(String mobile);
    
    /**
     * 根据用户名username判断是否存在用户
     * @param username
     * @return
     */
    public Boolean isExistByUsername(String username);
    
    /**
     * 获取全部用户ID列表
     * @return
     */
    public List<String> getAdminIdList();
}
