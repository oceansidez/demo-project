package com.telecom.pay.core;

import com.telecom.pay.PayBean;
import com.telecom.pay.PayResult;
import com.telecom.pay.alipay.wap.AlipayNotify;
import com.telecom.pay.alipay.wap.AlipaySubmit;
import com.telecom.pay.alipay.wap.UtilDate;
import com.telecom.util.DateOperateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝-WAP端
 */
public class AlipayWap {

    public static String pay(PayBean bean, String partner, String key, String seller) {
        try {
            //支付宝网关地址
            String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";

            //返回格式
            String format = "xml";
            //必填，不需要修改

            //返回格式
            String v = "2.0";
            //必填，不需要修改

            //请求号
            String req_id = UtilDate.getOrderNum();
            //必填，须保证每次请求都是唯一

            //req_data详细信息

            //服务器异步通知页面路径
            String notify_url = bean.getNotifyUrl();
            //需http://格式的完整路径，不能加?id=123这类自定义参数

            //页面跳转同步通知页面路径
            String call_back_url = bean.getReturnUrl();
            //需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

            //操作中断返回地址
            String merchant_url = "";
            //用户付款中途退出返回商户的地址。需http://格式的完整路径，不允许加?id=123这类自定义参数

            //卖家支付宝帐户
            String seller_email = seller;
            //必填

            //商户订单号
            String out_trade_no = bean.getTradeNo();
            //商户网站订单系统中唯一订单号，必填

            //订单名称
            String subject = bean.getBody();
            //必填

            //付款金额
            String total_fee = bean.getFee();
            //必填

            //请求业务参数详细
            String req_dataToken = "<direct_trade_create_req><notify_url>"
                    + notify_url + "</notify_url><call_back_url>" + call_back_url
                    + "</call_back_url><seller_account_name>" + seller_email
                    + "</seller_account_name><out_trade_no>" + out_trade_no
                    + "</out_trade_no><subject>" + subject
                    + "</subject><total_fee>" + total_fee
                    + "</total_fee><merchant_url>" + merchant_url
                    + "</merchant_url></direct_trade_create_req>";

            //把请求参数打包成数组
            Map<String, String> sParaTempToken = new HashMap<String, String>();
            sParaTempToken.put("app_pay", "Y");
            sParaTempToken.put("service", "alipay.wap.trade.create.direct");
            sParaTempToken.put("partner", partner);
            sParaTempToken.put("_input_charset", "UTF-8");
            sParaTempToken.put("sec_id", "MD5");
            sParaTempToken.put("format", format);
            sParaTempToken.put("v", v);
            sParaTempToken.put("req_id", req_id);
            sParaTempToken.put("req_data", req_dataToken);

            //建立请求
            String sHtmlTextToken = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, "", "", sParaTempToken, key);
            //URLDECODE返回的信息
            sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, "UTF-8");
            //获取token
            String request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);

            //业务详细
            String req_data = "<auth_and_execute_req><request_token>"
                    + request_token + "</request_token></auth_and_execute_req>";
            //必填

            //把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("app_pay", "Y");
            sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
            sParaTemp.put("partner", partner);
            sParaTemp.put("_input_charset", "UTF-8");
            sParaTemp.put("sec_id", "MD5");
            sParaTemp.put("format", format);
            sParaTemp.put("v", v);
            sParaTemp.put("req_data", req_data);

            //建立请求
            String sHtmlText = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, sParaTemp, "get", "确认", key);
            return sHtmlText;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static PayResult returnResult(HttpServletRequest request) {
        try {
            PayResult payResult = new PayResult();
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            String result = new String(request.getParameter("result").getBytes("ISO-8859-1"), "UTF-8");
            // 判断结果
            if (result.equals("success")) {
                payResult.setPayFlag(true);
            } else {
                payResult.setPayFlag(false);
            }
            payResult.setTradeNo(out_trade_no);
            payResult.setPaymentSn(trade_no);
            return payResult;
        } catch (Exception e) {
            e.printStackTrace();
            PayResult result = new PayResult();
            result.setPayFlag(false);
            result.setDesc("支付宝Wap端处理异常");
            return result;
        }

    }

    @SuppressWarnings("rawtypes")
    public static PayResult notifyResult(HttpServletRequest request, String key, String partner) {
        if (validate(request, key, partner)) {
            try {
                PayResult payResult = new PayResult();
                Map<String, String> params = new HashMap<String, String>();
                Map requestParams = request.getParameterMap();
                for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                    String name = (String) iter.next();
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

                //XML解析notify_data数据
                Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
                //商户订单号
                String out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
                //支付宝交易号
                String trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
                //交易状态
                String trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();
                //交易时间
                String gmt_payment = doc_notify_data.selectSingleNode("//notify/gmt_payment").getText();
                //买家账号
                String buyer_email = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();

                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                    payResult.setPayFlag(true);
                } else {
                    payResult.setPayFlag(false);
                }

                payResult.setTradeNo(out_trade_no);
                payResult.setPaymentSn(trade_no);
                if (StringUtils.isNotEmpty(gmt_payment)) {
                    payResult.setPayTime(DateOperateUtil.stringToDateWithFormat(gmt_payment, "yyyy-MM-dd HH:mm:ss"));
                }
                payResult.setPayer(buyer_email);

                return payResult;
            } catch (Exception e) {
                e.printStackTrace();
                PayResult result = new PayResult();
                result.setPayFlag(false);
                result.setDesc("支付宝Wap端处理异常");
                return result;
            }
        } else {
            PayResult result = new PayResult();
            result.setPayFlag(false);
            result.setDesc("支付宝Wap端Validate验证未通过");
            return result;
        }
    }

    @SuppressWarnings("rawtypes")
    public static boolean validate(HttpServletRequest request, String key, String partner) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();

        System.out.println("====================");
        System.out.println("支付宝WAP端validate验证:");
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

        try {
            Boolean flag = AlipayNotify.verifyNotify(params, partner, key);
            return flag;
        } catch (Exception e) {
            return false;
        }
    }
}
