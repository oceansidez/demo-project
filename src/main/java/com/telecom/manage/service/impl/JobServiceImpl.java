package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.manage.entity.Job;
import com.telecom.manage.mapper.JobMapper;
import com.telecom.manage.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl extends BaseServiceImpl<Job> implements
		JobService {

	@Autowired
	JobMapper jobMapper;

	@Override
	public BaseMapper<Job> getBaseMapper() {
		return jobMapper;
	}

	@Override
	public Job get(String id) {
		
		return jobMapper.selectByPrimaryKey(id);
	}

	//校验任务是否已被占用
	@Override
	public boolean isExistByName(String name) {
		
		Job job=jobMapper.getJobByName(name);
		if (job != null) {
			return true;
		} else {
			return false;
		}
	}

	//校验任务组是否已被占用
	@Override
	public boolean isExistByGroup(String group) {
		
		Job job=jobMapper.getJobByGroup(group);
		if (job != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Job getJob() {
	
		Job job=jobMapper.getJob();
		return job;
	}

	@Override
	public Job getName(String jobName) {
		
		Job job=jobMapper.getJobByName(jobName);
		return job;
	}

	@Override
	public int deleteByName(String jobName) {
		
		int count = jobMapper.deleteByJobName(jobName);
		return count;
	}

	//修改已执行任务的状态
	@Override
	public boolean updateState(String state,String name) {
		
		boolean falge=false;
		try {
			 falge=jobMapper.updateState(state, name);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return falge;
	}

	//获取未执行任务
	@Override
	public List<Job> getJobByState(String state) {
		
		List<Job>List=jobMapper.getJobByState(state);
		return List;
	}
}
