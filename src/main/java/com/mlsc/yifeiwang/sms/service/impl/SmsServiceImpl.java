package com.mlsc.yifeiwang.sms.service.impl;

import com.mlsc.common.cache.CacheUtil;
import com.mlsc.common.exception.BusinessException;
import com.mlsc.common.exception.ParamterException;
import com.mlsc.rpc.thrift.api.dto.ReObject;
import com.mlsc.rpc.thrift.api.service.ISmsService;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.sms.model.ParamterMessageBean;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import com.mlsc.yifeiwang.sms.service.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yinxl on 2017/7/19.
 */
@Service
public class SmsServiceImpl implements SmsService {

    private Logger logger = Logger.getLogger(SmsServiceImpl.class);

    private static final Integer INTERVAL_TIME = 120;

    @Autowired
    private IRPCServiceClient client;


    @Override
    public void getIdentifyCode(SendMsgParameter sendMsgParameter, String ticketId)
            throws BusinessException, ParamterException {

        try {
            sendMsg(sendMsgParameter);
            CacheUtil.put(sendMsgParameter.getPhoneNum(), sendMsgParameter.getSmsParam().get("code"),
                    INTERVAL_TIME, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new BusinessException("发送短信验证码时异常,可能是短信发送频率过高");
        }
    }

    //新的业务单据产生，短信通知相关用户及时查看商业信息
    @Override
    public ReObject sendMsg(SendMsgParameter sendMsgParameter) throws Exception {
        if (sendMsgParameter == null) {
            return null;
        }
        // 发送短信参数合法性验证
        List<ParamterMessageBean> messageBeans = sendMsgParameterValidate(sendMsgParameter);
        if (messageBeans != null && messageBeans.size() > 0) {
            throw new ParamterException(messageBeans);
        }
        //所有的短信模板参数合法，发送短信
        ISmsService.Iface smsManager = client.getSmsServiceManager();
        ReObject reObject = smsManager.sendIdentifyCode(sendMsgParameter.getPhoneNum(),
                sendMsgParameter.getExtend(),
                sendMsgParameter.getSmsFreeSignName(),
                sendMsgParameter.getSmsTemplateCode(),
                sendMsgParameter.getSmsParam());
        return reObject;

    }

    //新的业务单据产生，短信通知相关用户及时查看商业信息
    @Override
    public void sendMsgs(List<SendMsgParameter> sendMsgParameters) throws Exception {
        if (sendMsgParameters == null || sendMsgParameters.isEmpty()) {
            return;
        }
        // 发送短信参数合法性验证
        List<ParamterMessageBean> messageBeans = new ArrayList<ParamterMessageBean>();
        for (SendMsgParameter sendMsgParameter : sendMsgParameters) {
            messageBeans.addAll(sendMsgParameterValidate(sendMsgParameter));
        }
        if (messageBeans != null && messageBeans.size() > 0) {
//            throw new ParamterException(messageBeans);
            logger.error("发送短信传入参数错误");
            return;
        }
        ISmsService.Iface smsManager = client.getSmsServiceManager();
        //所有的短信模板参数合法，发送短信
        for (SendMsgParameter sendMsgParameter : sendMsgParameters) {
            try {
                smsManager.sendIdentifyCode(sendMsgParameter.getPhoneNum(),
                        sendMsgParameter.getExtend(),
                        sendMsgParameter.getSmsFreeSignName(),
                        sendMsgParameter.getSmsTemplateCode(),
                        sendMsgParameter.getSmsParam());
            } catch (Exception e) {
                logger.error("发送短信出错",e);
            }
        }
    }


    private List<ParamterMessageBean> sendMsgParameterValidate(SendMsgParameter sendMsgParameter) {
        List<ParamterMessageBean> messageBeans = new ArrayList<ParamterMessageBean>();
        if (StringUtils.isBlank(sendMsgParameter.getSmsFreeSignName())) {
            messageBeans.add(new ParamterMessageBean("SendMsgParameter.smsFreeSignName", "短信签名不能为空"));
        }
        if (StringUtils.isBlank(sendMsgParameter.getSmsTemplateCode())) {
            messageBeans.add(new ParamterMessageBean("SendMsgParameter.smsTemplateCode", "短信模板不能为空"));
        }
        if (StringUtils.isBlank(sendMsgParameter.getPhoneNum())) {
            messageBeans.add(new ParamterMessageBean("SendMsgParameter.phoneNum", "手机号码不能为空"));
        }
        //smsParamValidate(sendMsgParameter.getSmsTemplateCode(), sendMsgParameter.getSmsParam(), messageBeans);
        return messageBeans;
    }


}
