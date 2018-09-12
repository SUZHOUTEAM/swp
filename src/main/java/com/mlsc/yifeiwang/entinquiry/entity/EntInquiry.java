package com.mlsc.yifeiwang.entinquiry.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author yinxl
 * @since 2017-10-12
 */
@TableName("ent_inquiry")
public class EntInquiry extends Model<EntInquiry> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 询价企业ID
     */
    private String entId;
    /**
     * 发布企业ID
     */
    private String releaseEntId;

    /**
     * 服务商企业id
     */
    private String facilitatorEntId;

    /**
     * 处置企业id
     */
    private String disEntId;
    /**
     * 发布主表ID
     */
    private String releaseId;
    /**
     * 报价类型
     */
    private Integer quotedType;
    /**
     * 总量
     */
    private String totalAmount;
    /**
     * 总价
     */
    private Double totalPrice;
    private String remark;
    /**
     * 业务状态
     */
    private String busiStatus;
    private String createBy;
    private Date createTime;
    private String editBy;
    private Date editTime;
    private Integer deleteFlag;
    private String responseId;
    private String activityId;
    private String responsiblePerson;
    private String inquiryType;

    private String priority;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public String getReleaseEntId() {
        return releaseEntId;
    }

    public void setReleaseEntId(String releaseEntId) {
        this.releaseEntId = releaseEntId;
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

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public Integer getQuotedType() {
        return quotedType;
    }

    public void setQuotedType(Integer quotedType) {
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

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EntInquiry{" +
                "id=" + id +
                ", entId=" + entId +
                ", releaseEntId=" + releaseEntId +
                ", releaseId=" + releaseId +
                ", quotedType=" + quotedType +
                ", totalAmount=" + totalAmount +
                ", totalPrice=" + totalPrice +
                ", remark=" + remark +
                ", busiStatus=" + busiStatus +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", editBy=" + editBy +
                ", editTime=" + editTime +
                ", deleteFlag=" + deleteFlag +
                ", responseId=" + responseId +
                ", activityId=" + activityId +
                "}";
    }
}
