package com.telecom.manage.service;

import com.telecom.base.BaseService;
import com.telecom.manage.entity.Resource;

import java.util.List;

public interface ResourceService extends BaseService<Resource>{

	/**
	 * 查询放行/拦截/忽略的资源列表
	 * @param allowType
	 * @return
	 */
	List<Resource> selectAllowResource(String allowType);
}

