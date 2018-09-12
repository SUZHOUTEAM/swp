package com.mlsc.yifeiwang.sms.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by yinxl on 2017/7/27.
 * 消息查询参数
 */
public class SysNoticeQueryParam {

    private String senderId;
    private String receiverId;
    private String hasRead;
    private Integer startRowIndex;
    private Integer rows;
    private String noticeType;
    private String noticeCategory;
    private String clientType;

    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date startDate;
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date endDate;

    public SysNoticeQueryParam(){

    }

    public SysNoticeQueryParam(String receiverId, String hasRead, Integer startRowIndex, Integer rows) {
        this.receiverId = receiverId;
        this.hasRead = hasRead;
        this.startRowIndex = startRowIndex;
        this.rows = rows;
    }

    public SysNoticeQueryParam(String receiverId, String hasRead, Integer startRowIndex, Integer rows, String noticeCategory,
            Date startDate, Date endDate) {
        this.receiverId = receiverId;
        this.hasRead = hasRead;
        this.startRowIndex = startRowIndex;
        this.rows = rows;
        this.noticeCategory = noticeCategory;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SysNoticeQueryParam(String receiverId, String hasRead) {
        this.receiverId = receiverId;
        this.hasRead = hasRead;
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

    public String getHasRead() {
        return hasRead;
    }

    public void setHasRead(String hasRead) {
        this.hasRead = hasRead;
    }


    public Integer getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(Integer startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeCategory() {
        return noticeCategory;
    }

    public void setNoticeCategory(String noticeCategory) {
        this.noticeCategory = noticeCategory;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
