package com.mlsc.yifeiwang.sms.service;

import com.mlsc.rpc.thrift.api.dto.ReObject;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yinxl on 2017/7/19.
 */
@Service
public interface SmsService {
    /**
     * 向手机发送业务信息短信(批量短信)
     * @param sendMsgParameters 需要发送短信的模板参数
     * @return
     */
    void sendMsgs(List<SendMsgParameter> sendMsgParameters) throws Exception;

    /**
     * 向手机发送业务信息短信(单条短信)
     * @param sendMsgParameter  注册，重置密码时等没有ticket值时，null,其他不能为null需要发送短信的模板参数
     * @return
     */
    ReObject sendMsg(SendMsgParameter sendMsgParameter) throws Exception;

    /**
     * 向手机发送验证码短信
     * @param sendMsgParameter 短信模板，根据业务不同，发送不同的短信内容
     * @param ticketId
     * @return
     */
    void getIdentifyCode(SendMsgParameter sendMsgParameter, String ticketId) throws Exception;

}
