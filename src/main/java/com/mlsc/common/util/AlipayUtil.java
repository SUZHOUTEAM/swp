package com.mlsc.common.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 支付宝支付工具类
 */
public class AlipayUtil {
    public final static Logger logger = LoggerFactory.getLogger(AlipayUtil.class);
    private final static String TRADE_SUCCESS = "TRADE_SUCCESS";
    private final static String QUICK_WAP_PAY = "QUICK_WAP_PAY";

    public static String alipay(String orderNo, String amount, String subject) throws Exception {
        try {
            Map<String, String> orderMap = new LinkedHashMap<String, String>();            //订单实体
            Map<String, String> bizModel = new LinkedHashMap<String, String>();
            orderMap.put("out_trade_no", orderNo);
            // 订单名称，必填
            orderMap.put("subject", subject);
            // 付款金额，必填
            orderMap.put("total_amount", amount);
            // 商品描述，可空
            orderMap.put("body", subject + amount + "元");
            // 超时时间 可空
            orderMap.put("timeout_express", "30m");
            // 销售产品码 必填
            orderMap.put("product_code", QUICK_WAP_PAY);

            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

            AlipayTradeAppPayRequest aliRequest = new AlipayTradeAppPayRequest();

            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setPassbackParams(URLEncoder.encode((String) orderMap.get("body").toString())); //描述信息  添加附加数据
            model.setBody(orderMap.get("body"));                        //商品信息
            model.setSubject(orderMap.get("subject"));                  //商品名称
            model.setOutTradeNo(orderMap.get("out_trade_no"));          //商户订单号(自动生成)
            model.setTimeoutExpress(orderMap.get("timeout_express"));     //交易超时时间
            model.setTotalAmount(orderMap.get("total_amount"));         //支付金额
            model.setProductCode(orderMap.get("product_code"));         //销售产品码
            model.setSellerId(AlipayConfig.sellerId);                        //商家id
            aliRequest.setBizModel(model);
            aliRequest.setNotifyUrl(AlipayConfig.notify_url);
            aliRequest.setReturnUrl(AlipayConfig.return_url);
            logger.info("调用支付宝接口，参数为： " + "out_trade_no: " + orderNo + "; subject: " + subject + "; total_amount: " + amount + "; body: " + subject + amount + "元" + "; timeout_express: " + "30m" + "; product_code: " + QUICK_WAP_PAY);
            AlipayTradeAppPayResponse response = client.sdkExecute(aliRequest);
            String responseBody = response.getBody();
            logger.info("支付宝接口返回信息： " + responseBody);
            return response.getBody();
        } catch (Exception e) {
            logger.error("支付宝充值失败", e);
            throw e;
        }
    }

    /**
     * 获取支付成功后的订单号
     * @param requestParams
     * @return
     * @throws Exception
     */
    public static String getOrderNo(Map<String, String[]> requestParams) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean signVerified = false;
        // 商户订单号
        String orderNo = params.get("out_trade_no");
        //支付宝交易号
        String tradeNo = params.get("trade_no");

        //调用SDK验证签名
        logger.info("开始验签，支付宝交易号： " + tradeNo + " 订单号为：" + orderNo );
        signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
        if (signVerified ) {    //验签通过
            logger.info("验签通过，支付宝交易号： " + tradeNo + " 订单号为：" + orderNo );
            String tradeStatus = params.get("trade_status");            //交易状态
            if (TRADE_SUCCESS.equalsIgnoreCase(tradeStatus)) {
                logger.info("支付成功,支付宝交易号" + tradeNo + " 订单号为：" + orderNo + " 支付状态为：" + tradeStatus);
                //只处理支付成功的订单: 修改交易表状态,支付成功
                return orderNo;
            } else {
                logger.error("支付失败,支付宝交易号：" + tradeNo + " 订单号为：" + orderNo + " 支付状态为：" + tradeStatus);
                throw new Exception("支付失败,支付宝交易号：" + tradeNo + " 订单号为：" + orderNo + " 支付状态为：" + tradeStatus);
            }
        } else {  //验签不通过
            logger.error("验签失败,支付宝交易号：" + tradeNo + "订单号为：" + orderNo);
            throw new Exception("验签失败,支付宝交易号：" + tradeNo + "订单号为：" + orderNo);
        }
    }
}
