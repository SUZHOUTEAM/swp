package com.mlsc.waste.userenterpriseapproverecord.model;

import com.mlsc.epdp.common.annotation.Column;

public class AuditUserRecordVo  {

	@Column("id")
    private String id;  //record表的ID
	
	@Column("chineseName")
    private String chineseName;  //姓名
	
	@Column("phoneNum")
	private String phoneNum; // 电话

	@Column("enterpriseId")
	private String enterpriseId; // 企业ID

	@Column("userId")
	private String userId; // 电话
	
	@Column("eventType")
	private String eventType; // 事件类型:加入企业

	@Column("eventStatus")
	private String eventStatus; // 事件状态：申请已提交
	
	@Column("eventTypeCode")
	private String eventTypeCode; // 事件类型:JOIN

	@Column("eventStatusCode")
	private String eventStatusCode; // 事件状态：SUBMIT
	
	@Column("userType")
	private String userType;//用户类型
	
	@Column("gender")
	private String gender;//性别
	
	
	public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
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

	public String getEventTypeCode() {
		return eventTypeCode;
	}

	public void setEventTypeCode(String eventTypeCode) {
		this.eventTypeCode = eventTypeCode;
	}

	public String getEventStatusCode() {
		return eventStatusCode;
	}

	public void setEventStatusCode(String eventStatusCode) {
		this.eventStatusCode = eventStatusCode;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}

	@Column("applicationTime")
	private String applicationTime;//申请时间

	
	
	
}
