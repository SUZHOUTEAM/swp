package com.mlsc.yifeiwang.entorder.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017/9/15.
 */
public class OrderParam {
    private String orderId;
    private String releaseEntId;//发布企业id
    private String releaseEntType;//发布企业id
    private String inquiryEntId;
    private String releaseEnterName; //发布企业
    private String inquiryPersonId;//询价人
    private int startRowIndex;//第几页
    private int rows;//每页多少行
    private String dateType; //(今天（TODAY）本周(WEEK)，本月(MONTH))
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDateTime; //起始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDateTime; // 结束日期
    private String year; //年份
    private String orderStatus; //订单状态
    private String loginUserEntId; //登录用户所属企业id
    private String activityId ; //订单id

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReleaseEntId() {
        return releaseEntId;
    }

    public void setReleaseEntId(String releaseEntId) {
        this.releaseEntId = releaseEntId;
    }

    public String getReleaseEntType() {
        return releaseEntType;
    }

    public void setReleaseEntType(String releaseEntType) {
        this.releaseEntType = releaseEntType;
    }

    public String getInquiryEntId() {
        return inquiryEntId;
    }

    public void setInquiryEntId(String inquiryEntId) {
        this.inquiryEntId = inquiryEntId;
    }

    public String getReleaseEnterName() {
        return releaseEnterName;
    }

    public void setReleaseEnterName(String releaseEnterName) {
        this.releaseEnterName = releaseEnterName;
    }

    public String getInquiryPersonId() {
        return inquiryPersonId;
    }

    public void setInquiryPersonId(String inquiryPersonId) {
        this.inquiryPersonId = inquiryPersonId;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getLoginUserEntId() {
        return loginUserEntId;
    }

    public void setLoginUserEntId(String loginUserEntId) {
        this.loginUserEntId = loginUserEntId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
