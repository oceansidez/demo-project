package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.manage.entity.Resource;
import com.telecom.manage.mapper.ResourceMapper;
import com.telecom.manage.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService {

    @Autowired
    ResourceMapper resourceMapper;
    
    @Override
    public BaseMapper<Resource> getBaseMapper() {
    	return resourceMapper;
    }

	@Override
	public List<Resource> selectAllowResource(String allowType) {
		return resourceMapper.selectAllowResource(allowType);
	}

}

