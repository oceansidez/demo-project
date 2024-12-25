package com.telecom.manage.entity;

import com.telecom.base.BaseEntity;
import com.telecom.manage.entity.Admin;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *	此表为使用MyBatis进行联表查询的示例，展示用法
 *  此表无实际业务意义
 *  Score分数表关联Admin用户表
 *  adminId为关联外键
 *  @Transient admin 关联的属性注解
 *
 */
@Table(name = "t_score")
public class Score extends BaseEntity {

	private static final long serialVersionUID = -2451958011497841809L;

	private String math; // 数学分数
	
	private String art; // 美术分数
	
	private String adminId; // 用户外键关联
	
	@Transient
	private Admin admin; // 包含所有admin属性的外键关联对象

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getMath() {
		return math;
	}

	public void setMath(String math) {
		this.math = math;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
