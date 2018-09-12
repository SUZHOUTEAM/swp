package com.mlsc.yifeiwang.sms.model;


import com.mlsc.yifeiwang.sms.entity.SysNotice;

public class SysNoticeVo extends SysNotice {

    private static final long serialVersionUID = -1067195503797444931L;

    private String senderName; // 发布者名字
    
    private String receiverName; // 被通知者名字

    private String receiverTypeLabel; // 被通知者类型Label

    private String noticeTypeLabel; // 通知类型Label

    private String noticeCategoryLabel; // 通知分类Label

    private String hasReadLabel; // 通知阅读状态 已读（Constant.IS_YSE）未读（Constant.IS_NO）

    private String directUrl; //跳转地址

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverTypeLabel() {
        return receiverTypeLabel;
    }

    public void setReceiverTypeLabel(String receiverTypeLabel) {
        this.receiverTypeLabel = receiverTypeLabel;
    }

    public String getNoticeTypeLabel() {
        return noticeTypeLabel;
    }

    public void setNoticeTypeLabel(String noticeTypeLabel) {
        this.noticeTypeLabel = noticeTypeLabel;
    }

    public String getNoticeCategoryLabel() {
        return noticeCategoryLabel;
    }

    public void setNoticeCategoryLabel(String noticeCategoryLabel) {
        this.noticeCategoryLabel = noticeCategoryLabel;
    }

    public String getHasReadLabel() {
        return hasReadLabel;
    }

    public void setHasReadLabel(String hasReadLabel) {
        this.hasReadLabel = hasReadLabel;
    }

    public String getDirectUrl() {
        return directUrl;
    }

    public void setDirectUrl(String directUrl) {
        this.directUrl = directUrl;
    }
}
