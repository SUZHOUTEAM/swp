package com.mlsc.yifeiwang.sms.model;

import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.waste.user.model.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghj on 2017/7/21.
 */
public class SysMsgParameter implements Serializable{

    private static final long serialVersionUID = -3814644186527654570L;

    private User sendUser;

    private List<User> receiveUserList;

    private SmsAction smsAction;

    private String messageId;

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public List<User> getReceiveUserList() {
        return receiveUserList;
    }

    public void setReceiveUserList(List<User> receiveUserList) {
        this.receiveUserList = receiveUserList;
    }

    public SmsAction getSmsAction() {
        return smsAction;
    }

    public void setSmsAction(SmsAction smsAction) {
        this.smsAction = smsAction;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
