package com.mlsc.yifeiwang.sms.model;

/**
 * Created by yinxl on 2017/7/27.
 * 消息查询参数
 */
public class NoticeQueryParam {
    private String senderId;
    private String receiverId;
    private String isRead;
    private Integer start;
    private Integer rows;
    private String infotype;
    private String startDate;
    private String endDate;

    public NoticeQueryParam(String senderId, String receiverId, String isRead, Integer start, Integer rows) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.isRead = isRead;
        this.start = start;
        this.rows = rows;
    }

    public NoticeQueryParam(String senderId, String receiverId, String isRead, Integer start, Integer rows, String infotype, String startDate, String endDate) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.isRead = isRead;
        this.start = start;
        this.rows = rows;
        this.infotype = infotype;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public NoticeQueryParam(String senderId, String receiverId, String isRead) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.isRead = isRead;
    }
    public NoticeQueryParam(String senderId, String receiverId, String isRead, String infotype, String startDate, String endDate) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.isRead = isRead;
        this.infotype = infotype;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getInfotype() {
        return infotype;
    }

    public void setInfotype(String infotype) {
        this.infotype = infotype;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
