package com.mlsc.yifeiwang.activity.common;

/**
 * 活动联系人短信发送状态 SENDED("1") 已发送, NOT_SENDED("0") 未发送
 * Created by zhj on 2017/8/1.
 */
public enum ActivityContactsStatus {

    SENDED("1"), NOT_SENDED("0");

    private String value;

    ActivityContactsStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
