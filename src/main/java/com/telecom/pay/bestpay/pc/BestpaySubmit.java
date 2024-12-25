package com.telecom.pay.bestpay.pc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BestpaySubmit {
    
	// 支付请求URL
	public static final String PAYMENT_URL = "https://webpaywg.bestpay.com.cn/payWeb.do";
	
    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sPara, String strMethod, String strButtonName, String key) {
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"bestpaysubmit\" name=\"bestpaysubmit\" action=\"" + PAYMENT_URL
                      + "\" method=\"" + strMethod
                      + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['bestpaysubmit'].submit();</script>");

        return sbHtml.toString();
    }
    
}
