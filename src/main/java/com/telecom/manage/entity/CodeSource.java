package com.telecom.manage.entity;

import com.telecom.base.BaseEntity;

import javax.persistence.Table;

@Table(name = "t_code_source")
public class CodeSource extends BaseEntity {

	private static final long serialVersionUID = -8388354768881411498L;

	private String adminId;
	private String adminName;
	private String fileUrl;
	private String fileName;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
