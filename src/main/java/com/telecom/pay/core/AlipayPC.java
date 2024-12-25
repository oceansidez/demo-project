package com.telecom.pay.core;

import com.telecom.pay.PayBean;
import com.telecom.pay.PayResult;
import com.telecom.pay.alipay.pc.AlipayNotify;
import com.telecom.pay.alipay.pc.AlipaySubmit;
import com.telecom.util.DateOperateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝-PC端
 */
public class AlipayPC {

    public static String pay(PayBean bean, String partner, String key, String seller) {
        // 支付类型
        String payment_type = "1";
        //系统回调异步通知页面路径
        //需http://格式的完整路径，不能加?id=123这类自定义参数
        String notify_url = bean.getNotifyUrl();
        //页面跳转同步通知页面路径
        //需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
        String return_url = bean.getReturnUrl();
        //卖家支付宝帐户
        String seller_email = seller;
        //必填		//商户订单号
        String out_trade_no = bean.getTradeNo();
        //商户网站订单系统中唯一订单号，必填		//订单名称
        String subject = bean.getName();
        //必填		//付款金额
        String total_fee = bean.getFee();
        //必填		//订单描述
        String body = bean.getBody();
        //商品展示地址
        //需以http://开头的完整路径，例如：http://www.商户网址.com/myorder.html
        String show_url = "";
        //防钓鱼时间戳
        //若要使用请调用类文件submit中的query_timestamp函数
        String anti_phishing_key = "";
        //客户端的IP地址
        String exter_invoke_ip = bean.getClientIp();

        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", partner);
        sParaTemp.put("_input_charset", "UTF-8");
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

        // 建立请求
        String submitHtml = AlipaySubmit.buildRequest(sParaTemp, "get", "确认", key);
        return submitHtml;
    }

    public static PayResult returnResult(HttpServletRequest request) {
        try {
            PayResult result = new PayResult();
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            String payTime = null;
            if (request.getParameter("gmt_payment") != null) {
                payTime = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"), "UTF-8");
            }
            String payer = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"), "UTF-8");
            // 判断结果
            if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
                result.setPayFlag(true);
            } else {
                result.setPayFlag(false);
            }

            result.setTradeNo(outTradeNo);
            result.setPaymentSn(tradeNo);
            if (StringUtils.isNotEmpty(payTime)) {
                result.setPayTime(DateOperateUtil.stringToDateWithFormat(payTime, "yyyy-MM-dd HH:mm:ss"));
            }
            result.setPayer(payer);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            PayResult result = new PayResult();
            result.setPayFlag(false);
            result.setDesc("支付宝PC端处理异常");
            return result;
        }
    }

    public static PayResult notifyResult(HttpServletRequest request, String key, String partner) {
        if (validate(request, key, partner)) {
            try {
                PayResult result = new PayResult();
                String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                String payTime = null;
                if (request.getParameter("gmt_payment") != null) {
                    payTime = new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"), "UTF-8");
                }
                String payer = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"), "UTF-8");
                // 判断结果
                if (tradeStatus.equals("TRADE_FINISHED") || tradeStatus.equals("TRADE_SUCCESS")) {
                    result.setPayFlag(true);
                } else {
                    result.setPayFlag(false);
                }

                result.setTradeNo(outTradeNo);
                result.setPaymentSn(tradeNo);
                if (StringUtils.isNotEmpty(payTime)) {
                    result.setPayTime(DateOperateUtil.stringToDateWithFormat(payTime, "yyyy-MM-dd HH:mm:ss"));
                }
                result.setPayer(payer);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                PayResult result = new PayResult();
                result.setPayFlag(false);
                result.setDesc("支付宝PC端处理异常");
                return result;
            }
        } else {
            PayResult result = new PayResult();
            result.setPayFlag(false);
            result.setDesc("支付宝PC端Validate验证未通过");
            return result;
        }
    }

    @SuppressWarnings("rawtypes")
    public static boolean validate(HttpServletRequest request, String key, String partner) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();

        System.out.println("====================");
        System.out.println("支付宝PC端validate验证:");
        System.out.println(JSONObject.fromObject(requestParams));
        System.out.println("====================");

        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();

            // 验证参数剔除
            // 参数分别是1、payType（支付方式）2、payNo（支付订单号）
            if ("payType".equals(name) || "payNo".equals(name)) {
                continue;
            }

            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        return AlipayNotify.verify(params, key, partner);
    }


}
