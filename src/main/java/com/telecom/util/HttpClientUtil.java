package com.telecom.util;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

	private static Log log = LogFactory.getLog(HttpClientUtil.class);
	
	@SuppressWarnings("deprecation")
	public static String httpGet(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		Iterator<String> keysIte = params.keySet().iterator();
		int index = 0;
		while (keysIte.hasNext()) {
			String key = keysIte.next();
			if (index == 0) {
				url = url + "?" + key + "=" + params.get(key);
			} else {
				url = url + "&" + key + "=" + params.get(key);
			}
			index++;
		}
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse response = null;
		StringBuilder result = new StringBuilder();

		// 设置请求和传输超时时间5s,设置cookie策略
		RequestConfig requestconfig = RequestConfig.custom()
				.setSocketTimeout(5000).setConnectTimeout(5000)
				.setCookieSpec(CookieSpecs.BEST_MATCH).build();
		httpget.setConfig(requestconfig);

		try {
			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream in = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, "UTF-8"));
				String line;
				while ((line = br.readLine()) != null) {
					result.append(line);
					result.append("\n");
				}
				in.close();
				br.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	public static String httpPost(String url, String body) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String result = null;

		try {
			StringEntity stringEntity = new StringEntity(body, "UTF-8");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String httpPost(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String result = null;

		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for(String key : params.keySet()){
			    nvps.add(new BasicNameValuePair(key, params.get(key)));  
			}
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));  
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static Cookie[] httpPostForLogin(String loginUrl, Map<String, String> params) {
		HttpClient httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true)); 
		PostMethod postMethod = new PostMethod(loginUrl);
		Cookie[] cookies = null;
		
		try {
			List<org.apache.commons.httpclient.NameValuePair> nvps = 
					new ArrayList<org.apache.commons.httpclient.NameValuePair>();
			for(String key : params.keySet()){
			    nvps.add(new org.apache.commons.httpclient.NameValuePair(key, params.get(key)));  
			}
	       
			org.apache.commons.httpclient.NameValuePair[] array = 
					new org.apache.commons.httpclient.NameValuePair[nvps.size()];
			nvps.toArray(array);
			postMethod.setRequestBody((org.apache.commons.httpclient.NameValuePair[]) array);
			
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
	        httpClient.executeMethod(postMethod);
	        
	        // 获得登陆后的 Cookie
			cookies = httpClient.getState().getCookies();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				postMethod.releaseConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cookies;
	}
	
	public static String httpPost(String url, List<? extends NameValuePair> body,String iflytekUser,String smsId,String Authentication) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String result = null;

		try {
//			StringEntity stringEntity = new StringEntity(body, "UTF-8");
			UrlEncodedFormEntity stringEntity = new UrlEncodedFormEntity(body, "UTF-8");//首先将参数设置为utf-8的形式，
			RequestConfig requestconfig = RequestConfig.custom().setSocketTimeout(15000).build();
			httpPost.setConfig(requestconfig);
			httpPost.setEntity(stringEntity);
			httpPost.setHeader("X-IFLYFDP-USER", iflytekUser);
			Long ts = System.currentTimeMillis();
			httpPost.setHeader("X-IFLYFDP-TS", String.valueOf(ts));
			httpPost.setHeader("X-IFLYFDP-NONCE", smsId);
			String token = Authentication+smsId+String.valueOf(ts);
			httpPost.setHeader("X-IFLYFDP-TOKEN", AESUtil.MD5Encode(token));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response != null ? response.getEntity() : null;

			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null) response.close();
				if(httpClient != null) httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
     * 发送pos请求，直接发送整个请求信息
     *
     * @param url      接口地址
     * @param reqInfo  请求信息
     * @param encoding 编码
     */
    public static String sendPostInfo(String url, String reqInfo, String encoding) {
        log.info("发送请求报文，url：" + url + "，reqInfo：" + reqInfo + "，encoding：" + encoding);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse res;
        String result = null;
        try {
            StringEntity stringEntity = new StringEntity(reqInfo, encoding);
            // 设置文件头
            httpPost.setHeader("Accept-Language", "zh-cn");
            httpPost.setHeader("Connection", "Keep-Alive");
            //httpPost.setHeader("Content-Length", reqInfo.length() + "");
            httpPost.setEntity(stringEntity);
            res = httpClient.execute(httpPost);
            HttpEntity entity = res.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, encoding);
            }
            log.info("获取到响应报文：" + result);
        } catch (ClientProtocolException e) {
            log.error("发送请求报文异常：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.info("发送请求报文异常：" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

	public static String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
			br.close();
		} catch (IOException e) {
			System.err.println("IOException: " + e);
		}
		return str;
	}
	
	/**
	 * 设置url以及键值对参数
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getUrlWithParams(String url, Map<String,String> params) {
		Iterator<String> keysIte = params.keySet().iterator();
		int index = 0;
		while (keysIte.hasNext()) {
			String key = keysIte.next();
			if (index == 0) {
				url = url + "?" + key + "=" + params.get(key);
			} else {
				url = url + "&" + key + "=" + params.get(key);
			}
			index++;
		}
		return url;
	}
	
	/**
	 * 按照一定顺序设置url以及键值对参数
	 * @param url
	 * @param params
	 * @param orderList
	 * @return
	 */
	public static String getUrlWithParams(String url, Map<String,String> params, List<String> orderList) {
		int index = 0;
		for(String key : orderList){
			if (index == 0) {
				url = url + "?" + key + "=" + params.get(key);
			} else {
				url = url + "&" + key + "=" + params.get(key);
			}
			index++;
		}
		return url;
	}

}
