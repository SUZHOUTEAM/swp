package com.mlsc.yifeiwang.activity.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yinxl on 2017/7/31.
 */
public class ActivityQueryParam implements Serializable {
    /**
     *
     */
    private String ticketId;
    /**
     * 活动id
     */
    private String activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 开始时间
     */

    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 参与企业名称
     */
    private String entName;


    private String entId;

    private String responseEnterId;

    /**
     * 活动状态
     */
    private String status;

    private String activityType;

    private String orderBy;

    //产废企业id
    private String wasteEnterpriseId;
    //危废id
    private String wasteId;

    private String releaseEnterpriseId;

    private String dispositionTypeId;

    private String responseEnterpriseId;

    private String cantonCode;

    private List<String> cantonCodeList;

    private String licenceId;

    private String dateType;

    private String payStatus;

    private String activityContent;

    private int startRowIndex;//第几页

    private int rows;//每页多少行


    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return null == activityName ? null : "%" + activityName + "%";
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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

    public String getEntName() {
        return null == entName ? null : "%" + entName + "%";
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public String getResponseEnterId() {
        return responseEnterId;
    }

    public void setResponseEnterId(String responseEnterId) {
        this.responseEnterId = responseEnterId;
    }

    public String getWasteEnterpriseId() {
        return wasteEnterpriseId;
    }

    public void setWasteEnterpriseId(String wasteEnterpriseId) {
        this.wasteEnterpriseId = wasteEnterpriseId;
    }

    public String getWasteId() {
        return wasteId;
    }

    public void setWasteId(String wasteId) {
        this.wasteId = wasteId;
    }

    public String getReleaseEnterpriseId() {
        return releaseEnterpriseId;
    }

    public void setReleaseEnterpriseId(String releaseEnterpriseId) {
        this.releaseEnterpriseId = releaseEnterpriseId;
    }

    public String getDispositionTypeId() {
        return dispositionTypeId;
    }

    public void setDispositionTypeId(String dispositionTypeId) {
        this.dispositionTypeId = dispositionTypeId;
    }

    public String getResponseEnterpriseId() {
        return responseEnterpriseId;
    }

    public void setResponseEnterpriseId(String responseEnterpriseId) {
        this.responseEnterpriseId = responseEnterpriseId;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public List<String> getCantonCodeList() {
        return cantonCodeList;
    }

    public void setCantonCodeList(List<String> cantonCodeList) {
        this.cantonCodeList = cantonCodeList;
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

    public String getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }
}
