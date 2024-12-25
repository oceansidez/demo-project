package com.telecom.manage.controller.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.telecom.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Web端 支付宝授权
 *
 */
@Controller
@RequestMapping(value = "/web/alipay")
public class AlipayWebController extends BaseController {

	private final static String PAGE_PRE = "/web/alipay_";

	/**
	 * PC或Wap授权页
	 */
	@GetMapping("auth")
	public String auth(@RequestParam(value = "auth_code", required = false) String authCode) {
		String appId = "2021002123671348";
		String appPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCaJF6tq9soza7TZkmZN0WHRWQfNWvqluRqqRqd4KLnbkk5bYegK43wjnhY8+XMZJk/4I2wUhFcaAtNLVg3y7gCGleHuIpQX89RAVo/23dBbBmv5SvjJmN6/4ASvF+jrSFAT52HaAMc0hbdcbXEqsRI1wuOXUIQW1TvP84hQmncCNqWACKZrJWtAOjRuc+OMRKfAmIzRsKUL96J8Zkv9KyaL3hXgnAXWj4wu120dh6az2eb/nh9jc2481WU1Sl0WbuDmTm9AYaroZK0npleNDI9IJ7tFiLebfPBS1Lqoq1qNoVuYV/h/lgIgTF8BsM9rCdT+4qzBgj/q7atr+IjhmcXAgMBAAECggEBAJi6gLVEGxsK8ba1GyedBF9L+jCSFiOS7zBf7eeQtNjrBdLJHCEwBqvb/pLk10T2SIJqVPnn1xvgW5JU45wlVmV/BEuOJj4NMLXqySmMo1VHbwxB3oJrfxZYIuZtj7X1zYSTXlrUTIE6CT3RdXEwYtxpdyHQda0PUdDdP7jkvU8uQINd63bQi10ZP2X5a0s/VmM2AmdAyX48ghnnV9Z30T+d7IqKXXGX2Hz01ucYsgEq4OO1mLZxxd8B2c9hL8fyBqvHIkR0pMcX6lsOORDKToWKd3K62N0WZhks+2kKREYG1o4yqfeJrxYTNr8NvAW35CKTMijJgc2whNRe/51OvUECgYEA/1+HyNfSMnxP376B7tSqQLULMJ1H+OfYwwgyeBYbR07fuCvqO0ZSB3uqb3IOM5yXgGO2uSKPsLm3Yv49OzJTuWIkJ/RCbAyFKwcGSZZnb33ACq4ypd5VogSwdBCjqlk3cynRxHc/YTPZs/p97WjJiUqLkVIg/FfwJZGglpJxk9UCgYEAmoU6gftlPnT0aoBWz/WQ5+r9MgnK/4uUUqSPRzVaM3cXL2/payx5PYhClsl02DBIW7rDE/1QEMHEKQTvjHOoiG5rpae6z1LlFI/R8r68JHybGGdwO0YdKeesvCKbhJA2HrStKIM8tu8jQsWb53K4oce+rRojjIxI6d8015TvgTsCgYBXwIPwR/k6z+zCfbw4TWuTgKKvudwZc9HQcuikyzPkdP37U1vRsoBNgfk3AoJOyehlfd40D+RAXhDeO27fAJQ0h7zU3eJVLpg0CN3oO36fmR58jQ9qX9QdMG9R8sXKq42aTSx/zlpBFIgiiFjtU6ytOKhs70PWcoFa54e/NeNBYQKBgElUImDe6bwLCpakEw6bjcnRhZjCdvhwx9G0UF5NnmHACVu0s0PuFhcyW89tCSszNpN5fOw2kVFycuG4eunfVUJxjFpPz+Jlh1sLqWmWRHjYPU4GgIdQjbR+QkYB8mWlfllyCp6oMR7rO4OLfaIHmkEEJqPFPMuNI2kBSPVps4NTAoGAcD3NJRv/ids7IvFZOiHYWUc0+Ohvdd0hCasiWy5hIGZBABmycFe9+I9yydFM0LKTY0XMP58UbTdVftWYMG1lFIGWh+OZUWKR/Q0/d4cwc9f83v7bfYC7p0e4t4nrEOaT9GMmGH26f6XPnHPm3ngqZi4+mOy1P5OHYj1sLHSdg0g=";
		CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
		certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
		certAlipayRequest.setAppId(appId);
		certAlipayRequest.setPrivateKey(appPrivateKey);
		certAlipayRequest.setFormat("JSON");
		certAlipayRequest.setCharset("UTF-8");
		certAlipayRequest.setSignType("RSA2");
		certAlipayRequest.setCertPath("F:/appCertPublicKey.crt");
		certAlipayRequest.setAlipayPublicCertPath("F:/alipayCertPublicKey_RSA2.crt");
		certAlipayRequest.setRootCertPath("F:/alipayRootCert.crt");
		try {
			DefaultAlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
			AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
			request.setCode(authCode);
			request.setGrantType("authorization_code");
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.certificateExecute(request);
			System.out.println(oauthTokenResponse.getAccessToken());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		return PAGE_PRE + "web_auth";
	}

	/**
	 * JSAPI 专用H5授权页
	 */
	@GetMapping("jsAuth")
	public String jsAuth() {
		return PAGE_PRE + "jsapi_auth";
	}
}
