package com.telecom.manage.service;

import com.telecom.base.BaseService;
import com.telecom.manage.entity.Job;

import java.util.List;

public interface JobService extends BaseService<Job> {

	/**
	 * 判断任务名称是否存在
	 */
	public boolean isExistByName(String name);

	/**
	 * 判断任务组是否存在
	 */
	public boolean isExistByGroup(String group);
	
	/**
	 * 获取任务
	 */
	public Job getJob();

	/**
	 * 通过任务名获取任务信息
	 */
	public Job getName(String jobName);

	/**
	 * 通过任务名删除任务信息
	 */
	public int deleteByName(String jobName);
	
	/**
	 * 任务执行完毕之后，修改任务状态
	 */
	public boolean updateState(String state,String name);
	
	/**
	 * 初始化加载未执行任务
	 */
	public  List<Job>getJobByState(String state);

}
