package com.telecom.manage.controller.other;

import com.telecom.base.BaseController;
import com.telecom.config.PayConfig;
import com.telecom.pay.PayBean;
import com.telecom.pay.PayResult;
import com.telecom.pay.PayUtil;
import com.telecom.pay.core.WechatPayWap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "pay")
public class PayController extends BaseController {

	@Autowired
	PayConfig payConfig;
	
	@Autowired
	PayUtil payUtil;
	
	// 首页
	@GetMapping("index")
	public String index(ModelMap model) {
		return "/other/pay_index";
	}
	
	// 支付提交
	@ResponseBody
	@PostMapping("submit")
	public String submit(@RequestParam(value="payType") String payType, PayBean bean) {
		try {
			// 设置相关参数
			bean.setClientIp("27.17.43.67");
			bean.setServerIp(payConfig.getServerIp());
			if(StringUtils.isNotEmpty(payConfig.getProjectName())){
				bean.setReturnUrl(payConfig.getDomain() + "/" + payConfig.getProjectName() + "/pay/payReturn/"+ payType +"/"+ bean.getTradeNo());
				bean.setNotifyUrl(payConfig.getDomain() + "/" + payConfig.getProjectName() + "/pay/payNotify/"+ payType +"/"+ bean.getTradeNo());
			}
			else{
				bean.setReturnUrl(payConfig.getDomain() + "/pay/payReturn/"+ payType +"/"+ bean.getTradeNo());
				bean.setNotifyUrl(payConfig.getDomain() + "/pay/payNotify/"+ payType +"/"+ bean.getTradeNo());
			}
			
			// 执行session相关设置
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("PayTradeNo", bean.getTradeNo());
			
			// 是否为免费支付（测试时开启）
			if(payConfig.getIsFree()){
				bean.setFee("0.01");
			}
			
			// 执行支付处理，跳转页面
			String page = payUtil.pay(bean, payType);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return "处理异常";
		}
	}
	
	// 同步跳转（页面跳转）
	@RequestMapping("payReturn/{payType}/{payNo}")
	public String payReturn(ModelMap model,
			@PathVariable(value="payType") String payType, 
			@PathVariable(value="payNo") String tradeNo) {
		
		System.out.println("订单【" + tradeNo + "】进入同步跳转");
		PayResult payResult = payUtil.returnResult(request, payType);
		model.addAttribute("payResult", payResult);
		
		// 页面跳转
		if(payResult.getPayFlag()){
			// 交易成功跳转
			return "/other/pay_success";
		}
		else {
			// 交易失败跳转
			return "/other/pay_fail";
		}
	}
	
	// 异步回调（业务处理）
	@ResponseBody
	@RequestMapping("payNotify/{payType}/{payNo}")
	public String payNotify(
			@PathVariable(value="payType") String payType, 
			@PathVariable(value="payNo") String tradeNo) {
		
		System.out.println("订单【" + tradeNo + "】进入异步回调");
		// 备注：由于UrlRewrite工具重定向为RESTful风格的地址，则有两个重要参数被携带
		// 参数分别是1、payType（支付方式）2、payNo（支付订单号）
		// 以上参数均为本系统内部使用的参数，不应参与支付接口提供方（支付宝、微信支付等第三方支付）的验证，所以验证回调的时候需要去除该两参数
		PayResult payResult = payUtil.notifyResult(request, payType);
		
		try {
			// 业务处理
			if(payResult.getPayFlag()){
				
			}
			else {
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return payUtil.notifyResponse(payResult.getPayFlag(), payType);
	}
	
	// 微信H5支付查询接口
	@ResponseBody
	@PostMapping("wechatWapPayQuery")
	public String wechatWapPayQuery() {
		return ajax(WechatPayWap.queryForPage(request));
	}
}
