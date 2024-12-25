package com.telecom.manage.entity;

import com.telecom.base.BaseEntity;

import javax.persistence.Table;

@Table(name = "t_job")
public class Job extends BaseEntity {

	private static final long serialVersionUID = 701544317214584908L;

	// 任务执行状态 ：未执行，已执行
	public enum OperateState {
		unexecuted, executed
	}

	private String name;

	private String jobGroup;

	private String dateKey;

	private String taskClass;

	private String state;

	private String cronExpression;

	private String operateDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDateKey() {
		return dateKey;
	}

	public void setDateKey(String dateKey) {
		this.dateKey = dateKey;
	}

	public String getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

}
