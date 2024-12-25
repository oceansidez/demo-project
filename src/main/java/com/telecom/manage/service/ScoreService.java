package com.telecom.manage.service;

import com.telecom.base.BaseService;
import com.telecom.manage.entity.Score;

public interface ScoreService extends BaseService<Score>{

	public Score getWithFKData(String id);
	
}
