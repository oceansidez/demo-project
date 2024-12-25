package com.telecom.mongo.base;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class BaseEntityForMongo implements Serializable {

	private static final long serialVersionUID = 7461422227505458243L;

	public BaseEntityForMongo() {
		this.createDate = new Date();
		this.modifyDate = createDate;
	}

	@Id
	protected String id;// ID
	
	@Field("create_date")
	protected Date createDate; 
	
	@Field("modify_date")
	protected Date modifyDate; 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
