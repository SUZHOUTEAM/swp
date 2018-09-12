package com.mlsc.yifeiwang.weixin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.jpay.weixin.api.WxPayApiConfigKit;
import com.mlsc.yifeiwang.bindserve.common.RechargeStatus;
import com.mlsc.yifeiwang.bindserve.entity.EntRecharge;
import com.mlsc.yifeiwang.bindserve.model.EntRechargeParam;
import com.mlsc.yifeiwang.bindserve.service.IEntRechargeService;
import com.mlsc.yifeiwang.weixin.common.Constant;
import com.mlsc.yifeiwang.weixin.model.WeiXinModel;
import com.mlsc.yifeiwang.weixin.model.WeiXinParam;
import com.mlsc.yifeiwang.weixin.service.IWeiXinService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/8/10.
 */
@Service
public class WeiXinServiceImpl implements IWeiXinService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IEntRechargeService entRechargeService;


    public static WxPayApiConfig getApiConfig() {
        return WxPayApiConfig.New()
                .setAppId(Constant.WEIXIN_APPID)
                .setMchId(Constant.WEIXIN_MCHID)
                .setPaternerKey(Constant.WEIXIN_PARTNERKEY)
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
    }


    @Override
    public List<Object> getAccessToken(String code) throws IOException {
        try {
            List<Object> list = new ArrayList<Object>();
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                    + Constant.WEIXIN_APPID + "&secret=" + Constant.WEIXIN_APPSECRET + "&code=" + code + "&grant_type=authorization_code";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                String str = EntityUtils.toString(entity, "utf-8");
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> jsonOb = mapper.readValue(str, Map.class);
                list.add(jsonOb.get("access_token"));
                list.add(jsonOb.get("openid"));
            }
            return list;
        } catch (Exception e) {
            logger.error("获取用户openid时异常", e);
            throw e;
        }


    }

    @Override
    public WeiXinModel weiXinPay(WeiXinParam weiXinParam) throws Exception {
        try {
            WeiXinModel weiXinModel = pushOrder(weiXinParam);
            if ((PaymentKit.codeIsOK(weiXinModel.getReturnCode()) && PaymentKit.codeIsOK(weiXinModel.getResultCode()))) {
                String paySign = verifySign(weiXinModel);
                weiXinModel.setPaySign(paySign);
            }
            return weiXinModel;
        } catch (Exception e) {
            logger.error("微信支付时异常", e);
            throw e;
        }
    }

    @Override
    public String weiXinNotify(Map<String, String> params) throws Exception {
        String resultCode = params.get("result_code"); //回调状态
        String orderNo = params.get("out_trade_no"); //订单号
        EntRechargeParam entRechargeParam = new EntRechargeParam();
        entRechargeParam.setOrderNo(orderNo);
        EntRecharge entRecharge = entRechargeService.selectEntRecharge(entRechargeParam);
        if (entRecharge != null && !entRecharge.getBusiStatus().equalsIgnoreCase(RechargeStatus.SUCCESS.getCode())) {
            String resXml = "";
            WxPayApiConfigKit.setThreadLocalWxPayApiConfig(getApiConfig());
            if (PaymentKit.verifyNotify(params, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey())) {
                logger.info("pay_notify-------verifyNotify result " + resultCode);
                if (Constant.RESULT_CODE_SUCCESS.equals(resultCode)) {
                    Map<String, String> xml = new HashMap<String, String>();
                    xml.put("return_code", "SUCCESS");
                    xml.put("return_msg", "OK");
                    resXml = PaymentKit.toXml(xml);
                    entRechargeService.updateUserAccount(orderNo);
                } else {
                    Map<String, String> xml = new HashMap<String, String>();
                    xml.put("return_code", "FAIL");
                    xml.put("return_msg", "报文为空");
                    resXml = PaymentKit.toXml(xml);
                }
                return resXml;
            }
        }
        return null;
    }


    private WeiXinModel pushOrder(WeiXinParam weiXinParam) throws Exception {
        WeiXinModel weiXinModel = null;
        try {
            EntRechargeParam entRechargeParam = new EntRechargeParam();
            entRechargeParam.setOrderNo(weiXinParam.getOrderNo());
            EntRecharge entRecharge = entRechargeService.selectEntRecharge(entRechargeParam);

            if (entRecharge != null) {
                List<Object> list = getAccessToken(weiXinParam.getCode());
                String openId = list.get(1).toString();
                WxPayApiConfigKit.putApiConfig(getApiConfig());
                Map<String, String> params = WxPayApiConfigKit.getWxPayApiConfig()
                        .setAttach(entRecharge.getRemark())
                        .setBody(entRecharge.getRemark())
                        .setOpenId(openId)
                        .setSpbillCreateIp(weiXinParam.getIp())
                        .setTotalFee(String.valueOf(Math.round(entRecharge.getPrice() * 100)))
                        .setTradeType(WxPayApi.TradeType.JSAPI)
                        .setNotifyUrl(Constant.NOTIFY_URL)
                        .setOutTradeNo(entRecharge.getOrderNo())
                        .build();
                logger.info("unifiedorder 请求：" + params);
                String xmlResult = WxPayApi.pushOrder(false, params);
                logger.info("unifiedorder 返回结果：" + xmlResult);
                Map<String, String> resultMap = PaymentKit.xmlToMap(xmlResult);
                String returnCode = resultMap.get("return_code");
                String resultCode = resultMap.get("result_code");
                String prepay_id = resultMap.get("prepay_id");
                weiXinModel = new WeiXinModel();
                weiXinModel.setReturnCode(returnCode);
                weiXinModel.setResultCode(resultCode);
                weiXinModel.setOrderNo(weiXinParam.getOrderNo());
                weiXinModel.setPrepayId(prepay_id);
            }
        } catch (Exception e) {
            logger.error("pushOrder时异常", e);
            throw e;
        }
        return weiXinModel;
    }


    private String verifySign(WeiXinModel weiXinModel) {
        String prepay_id = weiXinModel.getPrepayId();
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce_str = String.valueOf(System.currentTimeMillis());
        String packages = "prepay_id=" + prepay_id;
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appId", Constant.WEIXIN_APPID);
        packageParams.put("timeStamp", timeStamp);
        packageParams.put("nonceStr", nonce_str);
        packageParams.put("package", "prepay_id=" + prepay_id);
        packageParams.put("signType", "MD5");
        String packageSign = PaymentKit.createSign(packageParams, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey());
        logger.info("验签结果：" + packageSign);
        return packageSign;
    }
}
