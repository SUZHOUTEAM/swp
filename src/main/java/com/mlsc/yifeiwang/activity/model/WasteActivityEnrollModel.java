package com.mlsc.yifeiwang.activity.model;

import com.mlsc.yifeiwang.activity.entity.WasteActivityEnroll;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by user on 2018/2/5.
 */
public class WasteActivityEnrollModel extends WasteActivityEnroll {
    private String entName;
    private String activityName;
    private String enrollStatusValue;
    private String userName;
    private String phoneNum;
    private String logoFileId;
    private String entRemark;
    private String videoResource;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date riseEndDate;//众筹结束日期

    private double discount;//折扣

    private double enrollFee;//报名费用

    private double numberGroupBuying; //众筹人数

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;//活动开始时间
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;//活动结束时间


    private int enrollActivityAcount; //报名人数
    private int pendingPayCount;//待付款人数
    private String activityStatus;
    private Integer startRowIndex;//第几页
    private Integer rows;//每页多少行


    public String getEntRemark() {
        return entRemark;
    }

    public void setEntRemark(String entRemark) {
        this.entRemark = entRemark;
    }

    public String getVideoResource() {
        return videoResource;
    }

    public void setVideoResource(String videoResource) {
        this.videoResource = videoResource;
    }

    public String getLogoFileId() {
        return logoFileId;
    }

    public void setLogoFileId(String logoFileId) {
        this.logoFileId = logoFileId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getEnrollStatusValue() {
        return enrollStatusValue;
    }

    public void setEnrollStatusValue(String enrollStatusValue) {
        this.enrollStatusValue = enrollStatusValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getEnrollActivityAcount() {
        return enrollActivityAcount;
    }

    public void setEnrollActivityAcount(int enrollActivityAcount) {
        this.enrollActivityAcount = enrollActivityAcount;
    }

    public int getPendingPayCount() {
        return pendingPayCount;
    }

    public void setPendingPayCount(int pendingPayCount) {
        this.pendingPayCount = pendingPayCount;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Date getRiseEndDate() {
        return riseEndDate;
    }

    public void setRiseEndDate(Date riseEndDate) {
        this.riseEndDate = riseEndDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getEnrollFee() {
        return enrollFee;
    }

    public void setEnrollFee(double enrollFee) {
        this.enrollFee = enrollFee;
    }

    public double getNumberGroupBuying() {
        return numberGroupBuying;
    }

    public void setNumberGroupBuying(double numberGroupBuying) {
        this.numberGroupBuying = numberGroupBuying;
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
}
