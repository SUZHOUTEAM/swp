package com.mlsc.yifeiwang.weixin.controller;

import com.jpay.vo.AjaxResult;
import com.mlsc.epdp.common.data.Result;
import com.mlsc.epdp.common.data.ResultStatus;
import com.mlsc.task.JsApiTicketTimeTask;
import com.mlsc.yifeiwang.activity.common.Sign;
import com.mlsc.yifeiwang.bindserve.model.EntRechargeParam;
import com.mlsc.yifeiwang.bindserve.service.IEntRechargeService;
import com.mlsc.yifeiwang.weixin.common.Constant;
import com.mlsc.yifeiwang.weixin.model.WeiXinModel;
import com.mlsc.yifeiwang.weixin.model.WeiXinParam;
import com.mlsc.yifeiwang.weixin.service.IWeiXinService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jpay.ext.kit.HttpKit;
import com.jpay.ext.kit.IpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.ext.kit.StrKit;
import com.jpay.weixin.api.WxPayApiConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/7/17.
 */
@Controller
@RequestMapping("/weixin")
public class WeiXinController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private AjaxResult result = new AjaxResult();
    private String notify_url = "http://www.yifeiwang.com/swp/weixin/pay_notify";

    @Autowired
    private IWeiXinService weiXinService;

    @Autowired
    private IEntRechargeService entRechargeService;

    public static WxPayApiConfig getApiConfig() {
        return WxPayApiConfig.New()
                .setAppId(Constant.WEIXIN_APPID)
                .setMchId(Constant.WEIXIN_MCHID)
                .setPaternerKey(Constant.WEIXIN_PARTNERKEY)
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);
    }


    @ResponseBody
    @RequestMapping(value = "/getWeiXinConfig", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Map<String, Object>> getWeiXinConfig(String url) {
        Result<Map<String, Object>> result = new Result<Map<String, Object>>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String jsapi_ticket = JsApiTicketTimeTask.jsapi_ticket;
        if (StringUtils.isNotBlank(jsapi_ticket)) {
            try {
                //进行数据的加密(url,jsapi_ticket,nonceStr,timestamp)等参数进行SHA1加密
                Map<String, String> ret = Sign.sign(jsapi_ticket, url);
                String timestamp = ret.get("timestamp");
                String nonceStr = ret.get("nonceStr");
                String signature = ret.get("signature");
                resultMap.put("timestamp", timestamp);
                resultMap.put("appId", Constant.WEIXIN_APPID);
                resultMap.put("nonceStr", nonceStr);
                resultMap.put("signature", signature);
                resultMap.put("appId", Constant.WEIXIN_APPID);
                result.setData(resultMap);
            } catch (Exception e) {
                result.setStatus(ResultStatus.Error);
            }
        } else {
            result.setStatus(ResultStatus.Error);
        }
        return result;
    }

    @RequestMapping("/getOpenId")
    public ModelAndView getOpenId(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        String code = request.getParameter("code");
        String redirectUrl = request.getParameter("redirectUrl");
        List<Object> list = weiXinService.getAccessToken(code);
        String openId = list.get(1).toString();
        if (StringUtils.isBlank(code) || StringUtils.isBlank(openId)) {
            mv.setViewName("redirect:/" + "http://www.yifeiwang.com/swp/main/mobile/view/attention.html");
        } else {
            mv.setViewName("redirect:/" + redirectUrl + "?openId=" + openId);
        }
        return mv;
    }


    @RequestMapping("/payfailed")
    public ModelAndView list(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/weixin/payfailed");
        return mv;
    }


    /**
     * 公众号支付
     */
    @RequestMapping(value = "/gzhPay", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView webPay(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("weixin/payFailed");
        String orderNo = request.getParameter("total_fee");
        EntRechargeParam entRechargeParam = new EntRechargeParam();
        entRechargeParam.setOrderNo(orderNo);
        try {
            String code = request.getParameter("code");
            String ip = IpKit.getRealIp(request);
            if (StrKit.isBlank(ip)) {
                ip = "127.0.0.1";
            }
            WeiXinParam weiXinParam = new WeiXinParam();
            weiXinParam.setIp(ip);
            weiXinParam.setCode(code);
            weiXinParam.setOrderNo(orderNo);
            WeiXinModel weiXinModel = weiXinService.weiXinPay(weiXinParam);

            if (weiXinModel != null && PaymentKit.codeIsOK(weiXinModel.getReturnCode()) && PaymentKit.codeIsOK(weiXinModel.getResultCode())) {
                mv.addObject("orderNo", orderNo);
                mv.addObject("appid", Constant.WEIXIN_APPID);
                mv.addObject("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
                mv.addObject("nonceStr", String.valueOf(System.currentTimeMillis()));
                mv.addObject("packageValue", "prepay_id=" + weiXinModel.getPrepayId());
                mv.addObject("paySign", weiXinModel.getPaySign());
                mv.addObject("success", "ok");
                mv.setViewName("weixin/pay");
            }

        } catch (Exception e) {
            log.error("微信支付异常", e);
        }

        return mv;
    }


    @RequestMapping(value = "/pay_notify", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void payNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("---------------支付回调----------------");
        String xmlMsg = HttpKit.readData(request);
        log.info("pay notice---------" + xmlMsg);
        Map<String, String> params = PaymentKit.xmlToMap(xmlMsg);
        try {
            String resXml = weiXinService.weiXinNotify(params);
            if (StringUtils.isNotBlank(resXml)) {
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            log.error("微信支付回调失败", e);
        }

    }
}
