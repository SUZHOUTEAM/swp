package com.mlsc.yifeiwang.sms.model;


import com.mlsc.yifeiwang.sms.entity.CooperationNotice;

public class CooperationNoticeVo extends CooperationNotice {
    
    private static final long serialVersionUID = 5498442899998043322L;

    private String senderName; // 发布者名字
    
    private String receiverName; // 被通知者名字
    
    private String receiverTypeCode; // 被通知者类型Code
    
    private String receiverTypeLabel; // 被通知者类型Label
    
    private String noticeTypeCode; // 通知类型Code
    
    private String noticeTypeLabel; // 通知类型Label
    
    private String isReadLabel; // 通知阅读状态 已读（Constant.IS_YSE）未读（Constant.IS_NO）
    
    private String noticeContentSummary; // 通知内容的概要
    
    private String routeCode;
    
    private String recivedEnterpriseName; 
    
    private String senderEnterpriseId; 
    
    private String senderEnterpriseName; 

    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the receiverName
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * @param receiverName the receiverName to set
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * @return the receiverTypeCode
     */
    public String getReceiverTypeCode() {
        return receiverTypeCode;
    }

    /**
     * @param receiverTypeCode the receiverTypeCode to set
     */
    public void setReceiverTypeCode(String receiverTypeCode) {
        this.receiverTypeCode = receiverTypeCode;
    }

    /**
     * @return the receiverTypeLabel
     */
    public String getReceiverTypeLabel() {
        return receiverTypeLabel;
    }

    /**
     * @param receiverTypeLabel the receiverTypeLabel to set
     */
    public void setReceiverTypeLabel(String receiverTypeLabel) {
        this.receiverTypeLabel = receiverTypeLabel;
    }

    /**
     * @return the noticeTypeCode
     */
    public String getNoticeTypeCode() {
        return noticeTypeCode;
    }

    /**
     * @param noticeTypeCode the noticeTypeCode to set
     */
    public void setNoticeTypeCode(String noticeTypeCode) {
        this.noticeTypeCode = noticeTypeCode;
    }

    /**
     * @return the noticeTypeLabel
     */
    public String getNoticeTypeLabel() {
        return noticeTypeLabel;
    }

    /**
     * @param noticeTypeLabel the noticeTypeLabel to set
     */
    public void setNoticeTypeLabel(String noticeTypeLabel) {
        this.noticeTypeLabel = noticeTypeLabel;
    }

    /**
     * @return the isReadLabel
     */
    public String getIsReadLabel() {
        return isReadLabel;
    }

    /**
     * @param isReadLabel the isReadLabel to set
     */
    public void setIsReadLabel(String isReadLabel) {
        this.isReadLabel = isReadLabel;
    }

    /**
     * @return the noticeContentSummary
     */
    public String getNoticeContentSummary() {
        return noticeContentSummary;
    }

    /**
     * @param noticeContentSummary the noticeContentSummary to set
     */
    public void setNoticeContentSummary(String noticeContentSummary) {
        this.noticeContentSummary = noticeContentSummary;
    }

   

    public String getRecivedEnterpriseName() {
        return recivedEnterpriseName;
    }

    public void setRecivedEnterpriseName(String recivedEnterpriseName) {
        this.recivedEnterpriseName = recivedEnterpriseName;
    }

    public String getSenderEnterpriseName() {
        return senderEnterpriseName;
    }

    public void setSenderEnterpriseName(String senderEnterpriseName) {
        this.senderEnterpriseName = senderEnterpriseName;
    }

    public String getSenderEnterpriseId() {
        return senderEnterpriseId;
    }

    public void setSenderEnterpriseId(String senderEnterpriseId) {
        this.senderEnterpriseId = senderEnterpriseId;
    }

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
}
