package com.mlsc.waste.userenterpriseapproverecord.model;

import com.mlsc.epdp.common.annotation.Column;

public class AuditEnterpriseRecordVo  {

	@Column("id")
    private String id;  //record表的ID
	
	@Column("enterpriseId")
    private String enterpriseId;  //企业ID
	
	@Column("enpName")
	private String enpName; // 企业名称

	@Column("eventType")
	private String eventType; // 事件类型:创建企业

	@Column("eventStatus")
	private String eventStatus; // 事件状态：申请已提交
	
	@Column("eventTypeCode")
	private String eventTypeCode; // 事件类型:创建企业

	@Column("eventStatusCode")
	private String eventStatusCode; // 事件状态：申请已提交
	
	
	@Column("enterpriseTypeId")
	private String enterpriseTypeId;//企业类型id
	
	@Column("enterpriseType")
	private String enterpriseType;//企业类型
	
	@Column("applicationTime")
	private String applicationTime;//申请时间

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnpName() {
		return enpName;
	}

	public void setEnpName(String enpName) {
		this.enpName = enpName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getEnterpriseTypeId() {
		return enterpriseTypeId;
	}

	public void setEnterpriseTypeId(String enterpriseTypeId) {
		this.enterpriseTypeId = enterpriseTypeId;
	}

	public String getEnterpriseType() {
		return enterpriseType;
	}

	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}

	public String getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	
	
	
}
