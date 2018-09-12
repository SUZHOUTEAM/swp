package com.mlsc.yifeiwang.im.model;

public class ImNotice {

    private String entName;//企业名称
    
    private String latestChatLog;//最新的一条聊天记录
    
    private String chatTime;//最新的聊天时间
    
    private String entId;//企业id
    
    private String phoneNum;//电话号码

    private String picUrl;//图片路径
    /**
     * @return the entName
     */
    public String getEntName() {
        return entName;
    }

    /**
     * @param entName the entName to set
     */
    public void setEntName(String entName) {
        this.entName = entName;
    }

    /**
     * @return the latestChatLog
     */
    public String getLatestChatLog() {
        return latestChatLog;
    }

    /**
     * @param latestChatLog the latestChatLog to set
     */
    public void setLatestChatLog(String latestChatLog) {
        this.latestChatLog = latestChatLog;
    }

    /**
     * @return the chatTime
     */
    public String getChatTime() {
        return chatTime;
    }

    /**
     * @param chatTime the chatTime to set
     */
    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }

    /**
     * @return the entId
     */
    public String getEntId() {
        return entId;
    }

    /**
     * @param entId the entId to set
     */
    public void setEntId(String entId) {
        this.entId = entId;
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
     * @return the picUrl
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * @param picUrl the picUrl to set
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
