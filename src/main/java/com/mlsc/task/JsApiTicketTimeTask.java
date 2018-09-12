package com.mlsc.task;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.util.WXUtils;
import com.mlsc.yifeiwang.weixin.common.Constant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by user on 2018/1/24.
 */
@Component
public class JsApiTicketTimeTask {
    public static String jsapi_ticket = "";
    private Logger logger = Logger.getLogger(JsApiTicketTimeTask.class);

    /**
     * 每隔一个小时调用一次微信获取jsapi的接口的任务调用器，在spring配置里面调用
     */
    public void getTicket() {
        //调用微信接口获取access_token凭证
        String tokenUrl = Constant.WEIXIN_ACCESS_TOKEN.replace("APPID", Constant.WEIXIN_APPID).replace("APPSECRET", Constant.WEIXIN_APPSECRET);
        String tokenStr = WXUtils.httpsRequest(tokenUrl, "GET", null);
        try {
            JSONObject tokenJson = JSONObject.parseObject(tokenStr);
            String access_token = (String) tokenJson.get("access_token");
            if (access_token != null && !"".equals(access_token)) {
                //如果可以获取access_token，即可以调用jsapi_tiket的凭证了
                String ticketUrl = Constant.JSAPI_TICKET.replace("ACCESS_TOKEN", access_token);
                String ticketStr = WXUtils.httpsRequest(ticketUrl, "GET", null);
                JSONObject ticketJson = JSONObject.parseObject(ticketStr);
                String errmsg = (String) ticketJson.get("errmsg");
                //如果调用成功，返回ok
                if ("ok".equals(errmsg)) {
                    jsapi_ticket = (String) ticketJson.get("ticket");
                    logger.info("微信的ticket为:" + jsapi_ticket);
                }
            }
        } catch (JSONException e) {
            logger.error("微信更新token时异常", e);
        }
    }

}
