package com.mlsc.yifeiwang.sms.model;

import com.mlsc.yifeiwang.sms.common.JPushTarget;

import java.io.Serializable;

/**
 * Created by zhanghj on 2017/7/21.
 */
public class JPushMsgParameter implements Serializable{

    private static final long serialVersionUID = 4142253337085568335L;

    private JPushTarget jpushTarget;

    private String registrationId;

    private String notificationTitle;

    private String msgTitle;

    private String msgContent;

    private String extraParam;

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam;
    }

    public JPushTarget getJpushTarget() {
        return jpushTarget;
    }

    public void setJpushTarget(JPushTarget jpushTarget) {
        this.jpushTarget = jpushTarget;
    }
}
