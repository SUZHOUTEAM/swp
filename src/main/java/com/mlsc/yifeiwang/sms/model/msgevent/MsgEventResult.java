package com.mlsc.yifeiwang.sms.model.msgevent;

import com.mlsc.yifeiwang.sms.entity.SysNotice;
import com.mlsc.yifeiwang.sms.model.JPushMsgParameter;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghj on 2017/7/21.
 */
public class MsgEventResult implements Serializable{

    private List<SysNotice> noticeList;

    private List<SendMsgParameter> sendMsgParameterList;

    private List<JPushMsgParameter> jpushMsgParameterList;

    public List<SysNotice> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<SysNotice> noticeList) {
        this.noticeList = noticeList;
    }

    public List<SendMsgParameter> getSendMsgParameterList() {
        return sendMsgParameterList;
    }

    public void setSendMsgParameterList(List<SendMsgParameter> sendMsgParameterList) {
        this.sendMsgParameterList = sendMsgParameterList;
    }

    public List<JPushMsgParameter> getJpushMsgParameterList() {
        return jpushMsgParameterList;
    }

    public void setJpushMsgParameterList(List<JPushMsgParameter> jpushMsgParameterList) {
        this.jpushMsgParameterList = jpushMsgParameterList;
    }
}
