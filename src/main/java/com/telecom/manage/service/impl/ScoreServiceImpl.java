package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.manage.entity.Score;
import com.telecom.manage.mapper.ScoreMapper;
import com.telecom.manage.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl extends BaseServiceImpl<Score> implements ScoreService{

    @Autowired
    ScoreMapper scoreMapper;
    
    @Override
    public BaseMapper<Score> getBaseMapper() {
    	return scoreMapper;
    }

	@Override
	public Score getWithFKData(String id) {
		return scoreMapper.getWithFKData(id);
	}

}
