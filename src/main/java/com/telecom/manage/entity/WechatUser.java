package com.telecom.manage.entity;

import com.telecom.base.BaseEntity;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 微信用户
 *
 */
@Table(name="t_wechat_user")
public class WechatUser extends BaseEntity{

	private static final long serialVersionUID = 8966614290669992778L;

	// 关注状态（已关注，取消关注，再关注）
	public enum AttentionType{
		concerned, unsubscribe, again
	}
	
	// 性别（男， 女）
	public enum Sex{
		male, female
	}
	
	private String openId; // 用户openId
	private String nick; //昵称
	private String sex; //性别
	private String picUrl; //头像地址
	private String country; //国家
	private String province; //省
	private String city; //市
	
	private String phone; //关联手机号
	private Date bindTime; //绑定手机号时间
	
	private String attentionType; //关注类型
	private Date loginLastTime; //最后登录时间
	private Boolean isLock; //是否锁定
	private String refereeId; //邀请人openId
	
	@Transient
	private String refereeNick; //邀请人昵称
	@Transient
	private String refereePicUrl; //邀请人头像地址
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAttentionType() {
		return attentionType;
	}

	public void setAttentionType(String attentionType) {
		this.attentionType = attentionType;
	}

	public Date getLoginLastTime() {
		return loginLastTime;
	}

	public void setLoginLastTime(Date loginLastTime) {
		this.loginLastTime = loginLastTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public Boolean getIsLock() {
		return isLock;
	}

	public void setIsLock(Boolean isLock) {
		this.isLock = isLock;
	}

	public String getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(String refereeId) {
		this.refereeId = refereeId;
	}

	public String getRefereeNick() {
		return refereeNick;
	}

	public void setRefereeNick(String refereeNick) {
		this.refereeNick = refereeNick;
	}

	public String getRefereePicUrl() {
		return refereePicUrl;
	}

	public void setRefereePicUrl(String refereePicUrl) {
		this.refereePicUrl = refereePicUrl;
	}
}
