package com.mlsc.yunxin.common;

/**
 * Created by yinxl on 2017/7/13.
 */
public enum SmsTemplate {

    FREE_SIGN_NAME("易废网", "短信签名"),
    SMS_77140046("SMS_77140046", "\"${entName}\"在易废网中向你发送了在线消息，请登录平台或APP查看。（www.yifeiwang.com）");


    private String value;

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    SmsTemplate(String value, String desc) {
        this.desc = desc;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
