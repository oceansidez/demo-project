package com.telecom.manage.service;

import com.telecom.base.BaseService;
import com.telecom.manage.entity.AuthCode;

public interface AuthCodeService extends BaseService<AuthCode> {

    /**
     * 根据号码查询验证码
     * @param mobile
     * @return
     */
    public AuthCode getAuthCodeByMobile(String mobile);
    
    /**
     * 生成验证码
     * @param mobile
     * @return
     */
    public AuthCode generateAuthCode(String mobile);
    
    /**
     * 发送验证码
     * @param mobile
     * @param type
     * @return
     */
    public String sendAuthCode(String mobile);
    
    /**
     * 校验验证码
     * @param mobile
     * @param authCode
     * @return
     */
    public String checkAuthCode(String mobile, String authCode);
}
