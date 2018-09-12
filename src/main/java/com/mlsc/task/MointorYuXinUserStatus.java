package com.mlsc.task;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.epdp.common.utils.StringUtils;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yunxin.common.ClientType;
import com.mlsc.yunxin.common.DateUtil;
import com.mlsc.yunxin.common.EventType;
import com.mlsc.yunxin.common.MessageType;
import com.mlsc.yunxin.entity.Onlinestatus;
import com.mlsc.yunxin.model.UserInfo;
import com.mlsc.yunxin.model.YunXinResult;
import com.mlsc.yunxin.service.IOnlinestatusService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.mlsc.yunxin.common.ClientType.AOS;
import static com.mlsc.yunxin.common.ClientType.IOS;
import static com.mlsc.yunxin.common.ClientType.WEB;
import static com.mlsc.yunxin.common.OnlineType.OFFLINE;
import static com.mlsc.yunxin.common.OnlineType.ONLINE;

import static com.mlsc.yunxin.common.SmsTemplate.FREE_SIGN_NAME;
import static com.mlsc.yunxin.common.SmsTemplate.SMS_77140046;

/**
 * 监听云信用户状态
 */
@Component
@Scope("prototype")
public class MointorYuXinUserStatus extends BaseThreadTask {
    private Logger logger = Logger.getLogger(MointorYuXinUserStatus.class);
    @Autowired
    private IOnlinestatusService onlinestatusServiceImpl;

    @Autowired
    private IRPCServiceClient client;

    private String requestBody;

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    protected Object execute() {
        try {
            logger.info("开始监听云信用户状态："+requestBody);
            updateOnlieStatusOrCheckMsg(requestBody);
        } catch (Exception e) {
            logger.error("监听云信用户时异常", e);
        }
        return null;
    }

    /**
     * 更新云信用户状态
     * @param requestBody
     */
    private void updateOnlieStatusOrCheckMsg(String requestBody) {
        YunXinResult yunXinResult = JSONObject.parseObject(requestBody, YunXinResult.class);
        EventType eventType = EventType.getEventType(yunXinResult.getEventType());
        ClientType clientType = ClientType.getClientType(yunXinResult.getClientType());

        Onlinestatus line = new Onlinestatus();
        line.setAccid(yunXinResult.getAccid());
        if (eventType.equals(EventType.LOGIN)) {
            if (AOS.equals(clientType)) {
                line.setClientAosStatus(ONLINE.getValue());
            }
            if (IOS.equals(clientType)) {
                line.setClientIosStatus(ONLINE.getValue());
            }
            if (WEB.equals(clientType)) {
                line.setClientWebStatus(ONLINE.getValue());
            }
            line.setLastClientIp(yunXinResult.getClientIp());
            line.insertOrUpdate();
        } else if (eventType.equals(EventType.LOGOUT)) {
            if (AOS.equals(clientType)) {
                line.setClientAosStatus(OFFLINE.getValue());
            }
            if (IOS.equals(clientType)) {
                line.setClientIosStatus(OFFLINE.getValue());
            }
            if (WEB.equals(clientType)) {
                line.setClientWebStatus(OFFLINE.getValue());
            }
            line.setLastClientIp(yunXinResult.getClientIp());
            line.insertOrUpdate();
        }else if (eventType.equals(EventType.CONVERSATION)
                && MessageType.PERSON.name().equalsIgnoreCase(yunXinResult.getConvType())) { //验证用户状态和发送时间间隔，离线并且在规定时间内发送短信
            logger.error(yunXinResult.toString());

            line.setAccid(yunXinResult.getTo());
            line = line.selectById();

            if (line == null) {
                line = new Onlinestatus(0, 0, 0);
                line.setAccid(yunXinResult.getTo());
            }
            UserInfo userInfo = onlinestatusServiceImpl.getUserInfoByAccid(yunXinResult.getFromAccount());
            boolean offline = (line.getClientAosStatus() + line.getClientIosStatus() + line.getClientWebStatus()) == 0;
            logger.info("判断用户状态 "+yunXinResult.getTo()+": "+offline);
            if (userInfo != null && offline ) {
                logger.info("开始发送短信 from:"+yunXinResult.getFromAccount()+"to: "+yunXinResult.getTo());
                Map<String, String> smsParamString = new HashMap<>(1);
                smsParamString.put("entName", userInfo.getEntName());
                client.getSmsServiceManager().sendIdentifyCode(yunXinResult.getTo(), null, FREE_SIGN_NAME.getValue(),
                        SMS_77140046.getValue(), smsParamString);
                line.setLastWarnTime(DateUtil.getDateStr(DateTime.now()));
                line.insertOrUpdate();
            }
        }
    }
}
