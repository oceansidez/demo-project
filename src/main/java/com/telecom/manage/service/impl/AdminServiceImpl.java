package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.manage.entity.Admin;
import com.telecom.manage.mapper.AdminMapper;
import com.telecom.manage.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    AdminMapper adminMapper;
    
    @Override
    public BaseMapper<Admin> getBaseMapper() {
    	return adminMapper;
    }

    public Admin getByUsername(String username) {
    	return adminMapper.getByUsername(username);
    }
    
    public Admin getByMobile(String mobile) {
    	return adminMapper.getByMobile(mobile);
    }
    
    public Boolean isExistByUsername(String username) {
		Admin admin = adminMapper.getByUsername(username);
		if(admin != null){
			return true;
		}
		else return false;
	}
    
    public List<String> getAdminIdList() {
    	List<String> list = new ArrayList<String>();
    	List<Admin> adminList = adminMapper.selectAll();
    	if(adminList != null && adminList.size() > 0){
    		for(Admin admin : adminList) {
    			list.add(admin.getId());
    		}
    		return list;
    	}
    	else return null;
    }
}
