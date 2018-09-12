package com.mlsc.yifeiwang.operaction.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 *
 * </p>
 *
 * @author caoyy
 * @since 2017-11-30
 */
@TableName("website_operation")
public class WebsiteOperation extends Model<WebsiteOperation> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String operationName;
    private String operationCode;
    private String areaCode;
    private String hasPlan;
    private String applyType;
    private String wasteType;
    private Double startAmount;
    private Double endAmount;
    private Integer qualificationMatch;
    private Date startTime;
    private Date endTime;
    private String cronJob;
    private String busiStatus;
    private String createBy;
    private Date createTime;
    private String editBy;
    private Date editTime;
    private String smsTemplate;
    private Integer deleteFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public Double getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(Double startAmount) {
        this.startAmount = startAmount;
    }

    public Double getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(Double endAmount) {
        this.endAmount = endAmount;
    }

    public Integer getQualificationMatch() {
        return qualificationMatch;
    }

    public void setQualificationMatch(Integer qualificationMatch) {
        this.qualificationMatch = qualificationMatch;
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

    public String getCronJob() {
        return cronJob;
    }

    public void setCronJob(String cronJob) {
        this.cronJob = cronJob;
    }

    public String getBusiStatus() {
        return busiStatus;
    }

    public void setBusiStatus(String busiStatus) {
        this.busiStatus = busiStatus;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }


    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WebsiteOperation{" +
                "id=" + id +
                ", operationName=" + operationName +
                ", operationCode=" + operationCode +
                ", areaCode=" + areaCode +
                ", applyType=" + applyType +
                ", startAmount=" + startAmount +
                ", endAmount=" + endAmount +
                ", qualificationMatch=" + qualificationMatch +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", cronJob=" + cronJob +
                ", busiStatus=" + busiStatus +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", editBy=" + editBy +
                ", editTime=" + editTime +
                ", deleteFlag=" + deleteFlag +
                "}";
    }
}
