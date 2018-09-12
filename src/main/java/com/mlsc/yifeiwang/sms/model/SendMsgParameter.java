/**
 *
 */
package com.mlsc.yifeiwang.sms.model;

import com.mlsc.yifeiwang.sms.common.SmsAction;

import java.util.HashMap;
import java.util.Map;

import static com.mlsc.yifeiwang.sms.common.SmsTemplateCode.SMS_FREE_SIGN;

/**
 * @author zhugl
 *
 */
public class SendMsgParameter {

    private static final long serialVersionUID = 2821591994970094340L;

    private String phoneNum; //需要发送短信的手机号码

    private String smsTemplateCode; //短信的模板代码

    private String extend; ////模板里面定义的，可以根据该字段进行统计信息 (暂时用不到)

    private String smsFreeSignName = SMS_FREE_SIGN; // 短信的签名(默认是【易废网】)

    private Map<String,String> smsParam = new HashMap<String,String>();//替换模板中的参数值

    private SmsAction smsAction;

    public SendMsgParameter(String phoneNum, SmsAction smsAction) {
        this.phoneNum = phoneNum;
        this.smsAction = smsAction;
    }
    public SendMsgParameter(SmsAction smsAction) {
        this.smsAction = smsAction;
    }

    /**
     * 构造方法，
     */
    public SendMsgParameter() {
    }

    /**
     * 构造方法
     * @param phoneNum  需要发送短信的手机号码
     * @param smsTemplateCode  短信的模板代码
     */
    public SendMsgParameter(String phoneNum, String smsTemplateCode) {
        this.phoneNum = phoneNum;
        this.extend = phoneNum;// 暂时用不到
        this.smsTemplateCode = smsTemplateCode;
    }
    /**
     * 构造方法
     * @param smsTemplateCode  短信的模板代码
     */
    public SendMsgParameter(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }

    public SmsAction getSmsAction() {
        return smsAction;
    }

    public void setSmsAction(SmsAction smsAction) {
        this.smsAction = smsAction;
    }
    /**
     * @return the phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * @param phoneNum the phoneNum to set
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * @return the smsTemplateCode
     */
    public String getSmsTemplateCode() {
        if(getSmsAction() == null){
            return smsTemplateCode;
        }else {
            return this.smsAction.getSmsTemplateCode();
        }
    }

    /**
     * @param smsTemplateCode the smsTemplateCode to set
     */
    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }

    /**
     * @return the extend
     */
    public String getExtend() {
        return extend;
    }

    /**
     * @param extend the extend to set
     */
    public void setExtend(String extend) {
        this.extend = extend;
    }

    /**
     * @return the smsFreeSignName
     */
    public String getSmsFreeSignName() {
        return smsFreeSignName;
    }

    /**
     * @param smsFreeSignName the smsFreeSignName to set
     */
    public void setSmsFreeSignName(String smsFreeSignName) {
        this.smsFreeSignName = smsFreeSignName;
    }

    /**
     * @return the smsParam
     */
    public Map<String, String> getSmsParam() {
        return smsParam;
    }

    /**
     * @param smsParam the smsParam to set
     */
    public void setSmsParam(Map<String, String> smsParam) {
        this.smsParam = smsParam;
    }
}
