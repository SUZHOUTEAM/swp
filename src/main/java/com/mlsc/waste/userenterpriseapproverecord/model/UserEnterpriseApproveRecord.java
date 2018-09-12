package com.mlsc.waste.userenterpriseapproverecord.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * 用户/企业关系审核记录表
 * @author zhugl
 */
@Table(TableNameConstants.TABLE_USER_ENTERPRISE_APPROVE_RECORD)
public class UserEnterpriseApproveRecord extends Entity {
    
    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id;  //主键
   
    @Column("user_id")
    private String userId;  //用户ID
    
    @Column("enterprise_id")
    private String enterpriseId;  //企业ID
    
    @Column("event_type")
    private String eventType;  //事件类型:加入企业，申请企业
    
    @Column("event_status")
    private String eventStatus;  //事件状态：待审核，审核通过，审核退回,已撤销申请
    
    @Column("application_by")
    private String applicationBy;  //申请人
    
    @Column("application_time")
    private String applicationTime;  //申请时间
    
    @Column("approved_by")
    private String approvedBy;  //审核人
    
    @Column("approved_time")
    private String approvedTime;  //审核时间
    
    @Column("create_by")
    private String createBy;  //创建人
    
    @Column("create_time")
    private String createTime;  //创建时间
    
    @Column("edit_by")
    private String editBy;  //修改人
    
    @Column("edit_time")
    private String editTime;  //修改时间
    
    @Column("valid")
    private String valid;  //是否有效

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the enterpriseId
     */
    public String getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * @param enterpriseId the enterpriseId to set
     */
    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the eventStatus
     */
    public String getEventStatus() {
        return eventStatus;
    }

    /**
     * @param eventStatus the eventStatus to set
     */
    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    /**
     * @return the applicationBy
     */
    public String getApplicationBy() {
        return applicationBy;
    }

    /**
     * @param applicationBy the applicationBy to set
     */
    public void setApplicationBy(String applicationBy) {
        this.applicationBy = applicationBy;
    }

    /**
     * @return the applicationTime
     */
    public String getApplicationTime() {
        return applicationTime;
    }

    /**
     * @param applicationTime the applicationTime to set
     */
    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    /**
     * @return the approvedBy
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * @param approvedBy the approvedBy to set
     */
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     * @return the approvedTime
     */
    public String getApprovedTime() {
        return approvedTime;
    }

    /**
     * @param approvedTime the approvedTime to set
     */
    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }

    /**
     * @return the createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy the createBy to set
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the editBy
     */
    public String getEditBy() {
        return editBy;
    }

    /**
     * @param editBy the editBy to set
     */
    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    /**
     * @return the editTime
     */
    public String getEditTime() {
        return editTime;
    }

    /**
     * @param editTime the editTime to set
     */
    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    /**
     * @return the valid
     */
    public String getValid() {
        return valid;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(String valid) {
        this.valid = valid;
    }
}
