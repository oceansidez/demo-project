package com.telecom.manage.entity;

import com.telecom.base.BaseEntity;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "t_auth_code")
public class AuthCode extends BaseEntity {

    private static final long serialVersionUID = -4495601153799974751L;

    private String mobile;// 电话（用户名）
    private String authCode;// 验证码
    private Date sendTime;// 发送时间
    private Boolean isEnabled;// 是否被使用

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
