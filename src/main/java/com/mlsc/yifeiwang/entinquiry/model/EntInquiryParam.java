package com.mlsc.yifeiwang.entinquiry.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017/9/15.
 */
public class EntInquiryParam {
    private String inquiryId; //询价id
    private String releaseEntId; //发布企业id
    private String inquiryEntId; // 询价企业Id
    private String releaseEntType; //发布企业类型
    private String releaseId;//发布企业危废Id
    private String facilitatorEntId; //服务商企业Id
    private String disEntId; //处置企业Id
    private int quotedType;//报价类型 打包报价：0，单独报价：1
    private String totalAmount;//总量
    private Double totalPrice;//总价
    private String remark;//备注
    private int startRowIndex;//第几页
    private int rows;//每页多少行
    private String dateType; //(今天（TODAY）本周(WEEK)，本月(MONTH))
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDateTime; //起始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDateTime; // 结束日期
    private String inquiryStatus; //询价状态
    private String activityId; //活动
    private String responsibleUserId; //询价人Id
    private String loginEntId;
    private String tagType; //标记类型
    private String tagTypeStatus;//小类
    private String priority;
    private String userAngle;  // 用户角色
    private String inquiryType; // 询价类型 ：系统报价 处置企业报价

    public String getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(String inquiryId) {
        this.inquiryId = inquiryId;
    }

    private List<EntInquiryDetailParam> inquiryDetail = new ArrayList<EntInquiryDetailParam>();

    public String getReleaseEntId() {
        return releaseEntId;
    }

    public void setReleaseEntId(String releaseEntId) {
        this.releaseEntId = releaseEntId;
    }

    public String getInquiryEntId() {
        return inquiryEntId;
    }

    public void setInquiryEntId(String inquiryEntId) {
        this.inquiryEntId = inquiryEntId;
    }

    public String getReleaseEntType() {
        return releaseEntType;
    }

    public void setReleaseEntType(String releaseEntType) {
        this.releaseEntType = releaseEntType;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public String getFacilitatorEntId() {
        return facilitatorEntId;
    }

    public void setFacilitatorEntId(String facilitatorEntId) {
        this.facilitatorEntId = facilitatorEntId;
    }

    public String getDisEntId() {
        return disEntId;
    }

    public void setDisEntId(String disEntId) {
        this.disEntId = disEntId;
    }

    public int getQuotedType() {
        return quotedType;
    }

    public void setQuotedType(int quotedType) {
        this.quotedType = quotedType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRows() {
        return this.rows;
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

    public String getInquiryStatus() {
        return inquiryStatus;
    }

    public void setInquiryStatus(String inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }

    public List<EntInquiryDetailParam> getInquiryDetail() {
        return inquiryDetail;
    }

    public void setInquiryDetail(List<EntInquiryDetailParam> inquiryDetail) {
        this.inquiryDetail = inquiryDetail;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }


    public String getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(String responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    public String getLoginEntId() {
        return loginEntId;
    }

    public void setLoginEntId(String loginEntId) {
        this.loginEntId = loginEntId;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTagTypeStatus() {
        return tagTypeStatus;
    }

    public void setTagTypeStatus(String tagTypeStatus) {
        this.tagTypeStatus = tagTypeStatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getUserAngle() {
        return userAngle;
    }

    public void setUserAngle(String userAngle) {
        this.userAngle = userAngle;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }
}
