package com.mlsc.yunxin.common;

/**
 * Created by yinxl on 2017/7/12.
 * 抄送消息类型
 */
public enum EventType {
    CONVERSATION("1", "表示CONVERSATION消息，即会话类型的消息（目前包括P2P聊天消息，群组聊天消息，群组操作，好友操作）"),
    LOGIN("2", "表示LOGIN消息，即用户登录事件的消息"),
    LOGOUT("3", "表示LOGOUT消息，即用户登出事件的消息"),
    CHATROOM("4", "表示CHATROOM消息，即聊天室中聊天的消息"),
    AUDIO_TIME("5", "表示AUDIO/VEDIO/DataTunnel消息，即汇报实时音视频通话时长、白板事件时长的消息"),
    AUDIO_ADDR("6", "表示音视频/白板文件存储信息，即汇报音视频/白板文件的大小、下载地址等消息"),
    SINGLE_CC("7", "表示单聊消息撤回抄送"),
    GROUP_CC("8", "表示群聊消息撤回抄送"),
    CHATROOM_INOUT("9", "表示CHATROOM_INOUT信息，即汇报主播或管理员进出聊天室事件消息"),
    ECP_CALLBACK("10", "表示ECP_CALLBACK信息，即汇报专线电话通话结束回调抄送的消息"),
    SMS_CALLBACK("11", "表示SMS_CALLBACK信息，即汇报短信回执抄送的消息");

    private String eventType;
    private String desc;

    EventType(String eventType, String desc) {
        this.eventType = eventType;
        this.desc = desc;
    }

    public String getEventType() {
        return eventType;
    }

    public static EventType getEventType(String eventType) {
        EventType type = null;
        for (EventType et : EventType.values()) {
            if (et.getEventType().equalsIgnoreCase(eventType)) {
                type = et;
                break;
            }
        }
        return type;
    }
}
