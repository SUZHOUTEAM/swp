package com.mlsc.yifeiwang.operaction.model;

import java.util.Date;
import java.util.List;

/**
 * Created by user on 2017/11/21.
 */
public class WebsiteOperationParam {
    private String operationId;
    private String operationName;
    private Double detailStartAmount;
    private Double detailEndAmount;
    private Double planStartAmount;
    private Double planEndAmount;
    private Integer qualificationMatch;
    private List<String> outSourceDisposalEnterName;
    private List<String> licenceIdList;
    private List<String> cantonCodes;
    private List<String> wasteTypes;
    private String hasPlan;
    private String applyType;
    private String cronJob;
    private Date startTime;
    private Date endTime;
    private Integer startRowIndex;//第几页
    private Integer rows;//每页多少行
    private Integer totalRecord; //总共多少条记录

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Double getDetailStartAmount() {
        return detailStartAmount;
    }

    public void setDetailStartAmount(Double detailStartAmount) {
        this.detailStartAmount = detailStartAmount;
    }

    public Double getDetailEndAmount() {
        return detailEndAmount;
    }

    public void setDetailEndAmount(Double detailEndAmount) {
        this.detailEndAmount = detailEndAmount;
    }

    public Double getPlanStartAmount() {
        return planStartAmount;
    }

    public void setPlanStartAmount(Double planStartAmount) {
        this.planStartAmount = planStartAmount;
    }

    public Double getPlanEndAmount() {
        return planEndAmount;
    }

    public void setPlanEndAmount(Double planEndAmount) {
        this.planEndAmount = planEndAmount;
    }

    public Integer getQualificationMatch() {
        return qualificationMatch;
    }

    public void setQualificationMatch(Integer qualificationMatch) {
        this.qualificationMatch = qualificationMatch;
    }

    public List<String> getOutSourceDisposalEnterName() {
        return outSourceDisposalEnterName;
    }

    public void setOutSourceDisposalEnterName(List<String> outSourceDisposalEnterName) {
        this.outSourceDisposalEnterName = outSourceDisposalEnterName;
    }

    public List<String> getLicenceIdList() {
        return licenceIdList;
    }

    public void setLicenceIdList(List<String> licenceIdList) {
        this.licenceIdList = licenceIdList;
    }

    public List<String> getCantonCodes() {
        return cantonCodes;
    }

    public void setCantonCodes(List<String> cantonCodes) {
        this.cantonCodes = cantonCodes;
    }

    public String getHasPlan() {
        return hasPlan;
    }

    public void setHasPlan(String hasPlan) {
        this.hasPlan = hasPlan;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getCronJob() {
        return cronJob;
    }

    public void setCronJob(String cronJob) {
        this.cronJob = cronJob;
    }

    public List<String> getWasteTypes() {
        return wasteTypes;
    }

    public void setWasteTypes(List<String> wasteTypes) {
        this.wasteTypes = wasteTypes;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }
}
