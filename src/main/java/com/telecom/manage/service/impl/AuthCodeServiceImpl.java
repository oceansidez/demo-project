package com.telecom.manage.service.impl;

import com.telecom.base.BaseMapper;
import com.telecom.base.BaseServiceImpl;
import com.telecom.bean.CommonConstant;
import com.telecom.manage.entity.AuthCode;
import com.telecom.manage.mapper.AuthCodeMapper;
import com.telecom.manage.service.AuthCodeService;
import com.telecom.util.CommonUtil;
import com.telecom.util.SendSmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class AuthCodeServiceImpl extends BaseServiceImpl<AuthCode> implements
		AuthCodeService {

	@Autowired
	SendSmsUtil sendSmsUtil;
	
	@Autowired
	AuthCodeMapper authCodeMapper;

	@Override
	public BaseMapper<AuthCode> getBaseMapper() {
		return authCodeMapper;
	}

	@Override
	public AuthCode getAuthCodeByMobile(String mobile) {
		return authCodeMapper.getAuthCodeByMobile(mobile);
	}

	@Override
	public AuthCode generateAuthCode(String mobile) {
		AuthCode authCode = authCodeMapper.getAuthCodeByMobile(mobile);
		Random random = new Random();
		Integer authCodeNumber = Math.abs(random.nextInt(999999));
		String authCodeStr = CommonUtil.fillStr(authCodeNumber, 6);
		if (ObjectUtils.isEmpty(authCode)) {
			authCode = new AuthCode();
			authCode.setAuthCode(authCodeStr);
			authCode.setMobile(mobile);
			authCode.setIsEnabled(false);
			authCode.setSendTime(new Date());
			authCodeMapper.insertSelective(authCode);
		} else {
			authCode.setAuthCode(authCodeStr);
			authCode.setIsEnabled(true);
			authCodeMapper.updateByPrimaryKeySelective(authCode);
		}
		return authCode;
	}

	@Override
	public String sendAuthCode(String mobile) {
		String statusCode = "";
		boolean flag = false;
		AuthCode authCode = getAuthCodeByMobile(mobile);
		if (ObjectUtils.isEmpty(authCode)) {
			flag = true;
		} else {
			if (authCode.getIsEnabled()) {
				Date sendTime = authCode.getSendTime();
				Date now = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(sendTime);
				cal.add(Calendar.MINUTE, +2);

				if (cal.getTime().after(now)) {
					// 2分钟无法重复获取
					return CommonConstant.AuthCodeResult.TIMELIMIT;
				} else {
					flag = true;
				}
			} else {
				flag = true;
			}
		}
		if (flag) {
			authCode = generateAuthCode(mobile);

			try {
				String result = null;
				Map<String, Object> map = sendSmsUtil.sendSms(mobile, "{authCode:'" + authCode.getAuthCode() + "'}");
				if (CommonConstant.Response.CODE_1009.equals(map.get("status").toString())) {
					result = "0";
				} else {
					result = map.get("status").toString();
				}

				if ("0".equals(result)) {
					// 发送成功
					statusCode = CommonConstant.AuthCodeResult.SUCCESS;
					authCode.setIsEnabled(true);
				} else {
					// 发送失败
					statusCode = CommonConstant.AuthCodeResult.FAILURE;
				}

				authCode.setSendTime(new Date());
				authCodeMapper.updateByPrimaryKeySelective(authCode);
			} catch (Exception e) {
				e.printStackTrace();
				// 发送异常
				return CommonConstant.AuthCodeResult.EXCEPTION;
			}
		}
		return statusCode;
	}

	@Override
	public String checkAuthCode(String mobile, String authCode) {
		AuthCode authCodeEntity = getAuthCodeByMobile(mobile);
		// 未获取过验证码
		if (authCodeEntity == null) {
			return CommonConstant.AuthCodeResult.NONE;
		} else {
			// 验证码已被使用过
			if (!authCodeEntity.getIsEnabled()) {
				return CommonConstant.AuthCodeResult.NONE;
			} else {
				Date sendTime = authCodeEntity.getSendTime();
				Calendar cal = Calendar.getInstance();
				cal.setTime(sendTime);
				cal.add(Calendar.MINUTE, +30);
				// 超过30分钟
				if (cal.getTime().before(new Date())) {
					return CommonConstant.AuthCodeResult.TIMEOUT;
				} else {
					// 验证码是否正确
					if (authCodeEntity.getAuthCode().equals(authCode)) {
						authCodeEntity.setIsEnabled(false);
						authCodeMapper.updateByPrimaryKeySelective(authCodeEntity);
						return CommonConstant.AuthCodeResult.CHECK_SUCCESS;
					} else {
						return CommonConstant.AuthCodeResult.CHECK_FAILURE;
					}
				}
			}
		}
	}
}
