package com.telecom.miniprogram;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

/**
 * 对微信小程序用户加密数据的解密.
 * 
 */
public class WXBizDataCrypt {

	/**
	 * 检验数据的真实性，并且获取解密后的明文.
	 * 
	 * @param encryptedData
	 *            string 加密的用户数据
	 * @param iv
	 *            string 与用户数据一同返回的初始向量
	 * @param sessionKey
	 * 			  string 会话key
	 *
	 * @return data string 解密后的原文
	 */
	public String decryptData(String encryptedData, String iv, String sessionKey) {
		if (StringUtils.length(sessionKey) != 24) {
			return "ERROR";
		}
		// 对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
		byte[] aesKey = Base64.decodeBase64(sessionKey);

		if (StringUtils.length(iv) != 24) {
			return "ERROR";
		}
		// 对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
		byte[] aesIV = Base64.decodeBase64(iv);

		// 对称解密的目标密文为 Base64_Decode(encryptedData)
		byte[] aesCipher = Base64.decodeBase64(encryptedData);

		try {
			byte[] resultByte = AESUtils.decrypt(aesCipher, aesKey, aesIV);

			if (null != resultByte && resultByte.length > 0) {
				String userInfo = new String(resultByte, "UTF-8");
				return userInfo;
			}
			else return "ERROR";
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			return "ERROR";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "ERROR";
		}

	}

}
