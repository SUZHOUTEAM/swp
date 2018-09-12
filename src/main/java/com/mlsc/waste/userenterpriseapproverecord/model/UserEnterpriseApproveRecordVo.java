package com.mlsc.waste.userenterpriseapproverecord.model;

import com.mlsc.epdp.common.annotation.Column;

/**
 * 用户/企业关系审核记录扩展
 * @author zhugl
 */
public class UserEnterpriseApproveRecordVo extends UserEnterpriseApproveRecord {
    
    private static final long serialVersionUID = 2821591994970094340L;
    
    @Column("applicationItem")
    private String applicationItem;  // 申请事项
   
    @Column("applicationStatus")
    private String applicationStatus;  // 申请状态
    
    @Column("applicationTime")
    private String applicationTime;  // 申请时间
    
    @Column("eventTypeCode")
    private String eventTypeCode;  // 事件类型Code
    
    @Column("eventStatusCode")
    private String eventStatusCode;  // 事件状态Code
    
    @Column("enterpriseName")
    private String enterpriseName;  // 企业名称

    /**
     * @return the applicationItem
     */
    public String getApplicationItem() {
        return applicationItem;
    }

    /**
     * @param applicationItem the applicationItem to set
     */
    public void setApplicationItem(String applicationItem) {
        this.applicationItem = applicationItem;
    }

    /**
     * @return the applicationStatus
     */
    public String getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * @param applicationStatus the applicationStatus to set
     */
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
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
     * @return the eventTypeCode
     */
    public String getEventTypeCode() {
        return eventTypeCode;
    }

    /**
     * @param eventTypeCode the eventTypeCode to set
     */
    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

    /**
     * @return the eventStatusCode
     */
    public String getEventStatusCode() {
        return eventStatusCode;
    }

    /**
     * @param eventStatusCode the eventStatusCode to set
     */
    public void setEventStatusCode(String eventStatusCode) {
        this.eventStatusCode = eventStatusCode;
    }

    /**
     * @return the enterpriseName
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }

    /**
     * @param enterpriseName the enterpriseName to set
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
