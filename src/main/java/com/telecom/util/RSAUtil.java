package com.telecom.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
	/**
	 * 定义加密方式
	 */
	private final static String KEY_RSA = "RSA";
	/**
	 * 定义签名算法
	 */
	private final static String KEY_RSA_SIGNATURE = "MD5withRSA";
	/**
	 * 定义公钥算法
	 */
	private final static String KEY_RSA_PUBLICKEY = "RSAPublicKey";
	/**
	 * 定义私钥算法
	 */
	private final static String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";

	/**
	 * 初始化密钥
	 * 
	 * @return
	 */
	public static Map<String, Object> init() {
		Map<String, Object> map = null;
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
			generator.initialize(1024);
			KeyPair keyPair = generator.generateKeyPair();
			// 公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// 私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 将密钥封装为map
			map = new HashMap<String, Object>();
			map.put(KEY_RSA_PUBLICKEY, publicKey);
			map.put(KEY_RSA_PRIVATEKEY, privateKey);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String sign(byte[] data, String privateKey) {
		String str = "";
		try {
			// 解密由base64编码的私钥
			byte[] bytes = decryptBase64(privateKey);
			// 构造PKCS8EncodedKeySpec对象
			PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
			// 指定的加密算法
			KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
			// 取私钥对象
			PrivateKey key = factory.generatePrivate(pkcs);
			// 用私钥对信息生成数字签名
			Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
			signature.initSign(key);
			signature.update(data);
			str = encryptBase64(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * @return 校验成功返回true，失败返回false
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) {
		boolean flag = false;
		try {
			// 解密由base64编码的公钥
			byte[] bytes = decryptBase64(publicKey);
			// 构造X509EncodedKeySpec对象
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
			// 指定的加密算法
			KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
			// 取公钥对象
			PublicKey key = factory.generatePublic(keySpec);
			// 用公钥验证数字签名
			Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
			signature.initVerify(key);
			signature.update(data);
			flag = signature.verify(decryptBase64(sign));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            私钥
	 * @return
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key) {
		byte[] result = null;
		try {
			// 对私钥解密
			byte[] bytes = decryptBase64(key);
			
			// 取得私钥
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
			PrivateKey privateKey = factory.generatePrivate(keySpec);
			// 对数据解密
			Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 *            加密数据
	 * @param key
	 *            公钥
	 * @return
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) {
		byte[] result = null;
		try {
			// 对公钥解密
			byte[] bytes = decryptBase64(key);
			// 取得公钥
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
			KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
			PublicKey publicKey = factory.generatePublic(keySpec);
			// 对数据解密
			Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            公钥
	 * @return
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key) {
		byte[] result = null;
		try {
			byte[] bytes = decryptBase64(key);
			// 取得公钥
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
			KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
			PublicKey publicKey = factory.generatePublic(keySpec);
			// 对数据加密
			Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            私钥
	 * @return
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key) {
		byte[] result = null;
		try {
			byte[] bytes = decryptBase64(key);
			// 取得私钥
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
			KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
			PrivateKey privateKey = factory.generatePrivate(keySpec);
			// 对数据加密
			Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取公钥
	 * 
	 * @param map
	 * @return
	 */
	public static String getPublicKey(Map<String, Object> map) {
		String str = "";
		try {
			Key key = (Key) map.get(KEY_RSA_PUBLICKEY);
			str = encryptBase64(key.getEncoded());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 获取私钥
	 * 
	 * @param map
	 * @return
	 */
	public static String getPrivateKey(Map<String, Object> map) {
		String str = "";
		try {
			Key key = (Key) map.get(KEY_RSA_PRIVATEKEY);
			str = encryptBase64(key.getEncoded());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * BASE64 解密
	 * 
	 * @param key
	 *            需要解密的字符串
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] decryptBase64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64 加密
	 * 
	 * @param key
	 *            需要加密的字节数组
	 * @return 字符串
	 * @throws Exception
	 */
	public static String encryptBase64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}
	
	/**
	 * 字节数组转字符串
	 * @param str
	 * @return
	 */
	public static String encodeBytes(byte[] bytes) {
		StringBuffer strBuf = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
			strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
		}

		return strBuf.toString();
	}
	
	/**
	 * 转字节数组
	 * @param str
	 * @return
	 */
	public static byte[] decodeBytes(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length(); i += 2) {
			char c = str.charAt(i);
			bytes[i / 2] = (byte) ((c - 'a') << 4);
			c = str.charAt(i + 1);
			bytes[i / 2] += (c - 'a');
		}
		return bytes;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		String privateKey = "";
//		String publicKey = "";
//		// 生成公钥私钥
//		Map<String, Object> map = init();
//		publicKey = getPublicKey(map);
//		privateKey = getPrivateKey(map);
//		
////		publicKey =     "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIp16985J7qYqoRBSu+aKXa8h54YrVVWPuuePe"+
////					    "MXE8ehlj1MrZggPXkGsBGiPzrac2RxehqxQ9nOGWeGh2oM70uELsd9pZIPnj524FIJVFeoMxNnGI"+
////						"c10/Df2dcldFoHaAv18JZVclPOuhNF4wxDmM5vyhWsQEGisiXzhuhGQWRQIDAQAB";
////		
////		privateKey =    "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIinXr3zknupiqhEFK75opdryHnh"+
////						"itVVY+65494xcTx6GWPUytmCA9eQawEaI/OtpzZHF6GrFD2c4ZZ4aHagzvS4Qux32lkg+ePnbgUg"+
////						"lUV6gzE2cYhzXT8N/Z1yV0WgdoC/XwllVyU866E0XjDEOYzm/KFaxAQaKyJfOG6EZBZFAgMBAAEC"+
////						"gYBC6YEwjYWaW6fzQHLIQp/kePhPBasPTqKCEC+NarRBop+wDE/PNSRgduyOO5iDBvKrVyAxHy3o"+
////						"BVtIbGjqPU9YNwDNcv9BJg/QbDb6OPpfdj8exnnLqj55sJ2jV/gxIJekE6b+Aw8d6pC6xUy4wAeZ"+
////						"PtY/iLQTvDSWi15MNWqxuQJBANyF0+BknGGt3dX/VKCqnR3Adi7CmgHhTUvc6daPToG2++Hb25Q2"+
////						"H0QjP8OWHrF/DblCXeAqxDO//Mq5H9v41usCQQCeo2uwAl1XT0pnVu3EiO3TdlA2stLG2+IQ0uOG"+
////						"X3+T+vJOoDAlXPFoftEcOTEp6JS7A6d6YNxWfBnio4ddW9uPAkEAoBtSD5M8rvarH1n4OYZl+Fnp"+
////						"9F05r6/CjRfhdvQpDairC/qyW9NHtBLbkseFlV0nu5/hxIWIWgJEKXNrHCx47QJBAJnqcPdKtAmc"+
////						"MtJk8zh2qlk7N8R6aCrs+D/efUbIrV2EY0iJ/2yg6tC6CmVZ0CubL4LrnrkL9Xwr2GmexB1+nF0C"+
////						"QQDDcRwx0V4hHkW5ngcw24d9TwpCDSlatApUm90bJuPDZNNehuk251jL1zBii53Z5wkuRUOwzGv5"+
////						"ao6yCTwADmOs";
//		
//		
//		System.out.println("公钥: \n\r" + publicKey);
//		System.out.println("私钥： \n\r" + privateKey);
//		
//		System.out.println("公钥加密--------私钥解密");
//		String word = "你好，世界！";
//		byte[] encWord = encryptByPublicKey(word.getBytes(), publicKey);
//		String decWord = new String(decryptByPrivateKey(encWord, privateKey));
//		System.out.println("加密前: " + word + "\n\r" + "解密后: " + decWord);
//		System.out.println("私钥加密--------公钥解密");
//		String english = "Hello, World!";
//		byte[] encEnglish = encryptByPrivateKey(english.getBytes(), privateKey);
//		String decEnglish = new String(
//				decryptByPublicKey(encEnglish, publicKey));
//		System.out.println("加密前: " + english + "\n\r" + "解密后: " + decEnglish);
//		System.out.println("私钥签名——公钥验证签名");
//		// 产生签名
//		String sign = sign(encEnglish, privateKey);
//		System.out.println("签名:\r" + sign);
//		// 验证签名
//		boolean status = verify(encEnglish, publicKey, sign);
//		System.out.println("状态:\r" + status);
//		
////		String word = "你好，世界！";
////		byte[] encWord = encryptByPublicKey(word.getBytes(), akey);
////		String decWord = new String(decryptByPrivateKey(encWord, bkey));
////		System.out.println(decWord);
////		
////		// 产生签名
////		String sign = sign(encWord, bkey);
////		System.out.println("签名:\r" + sign);
////		// 验证签名
////		boolean status = verify(encWord, akey, sign);
////		System.out.println("状态:\r" + status);
//		
//	}
}
