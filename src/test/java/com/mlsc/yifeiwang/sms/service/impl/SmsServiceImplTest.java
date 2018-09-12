package com.mlsc.yifeiwang.sms.service.impl;

import com.mlsc.BaseTest;
import com.mlsc.common.util.RSAEncryptUtils;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import com.mlsc.yifeiwang.sms.service.SmsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.mlsc.yifeiwang.sms.common.SmsTemplateCode.SMS_21235045;

/**
 * Created by zhanghj on 2017/8/2.
 */
public class SmsServiceImplTest extends BaseTest {

    @Autowired
    private SmsService smsService;

    @Test
    public void sendMsg() throws Exception {
        SendMsgParameter parameter = new SendMsgParameter();
        parameter.setPhoneNum("18013142638,18051116827,18934570843");
        parameter.setSmsTemplateCode(SMS_21235045);
        smsService.sendMsg(parameter);
    }

    @Test
    public void RESEncrypt() throws Exception {
        String enpas = RSAEncryptUtils.encrypt("test123");
        String deEnc = RSAEncryptUtils.decrypt(enpas);
        System.out.println(enpas);
        System.out.println(deEnc);
    }


}