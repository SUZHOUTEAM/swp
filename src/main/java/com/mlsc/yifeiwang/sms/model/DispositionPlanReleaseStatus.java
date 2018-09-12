package com.mlsc.yifeiwang.sms.model;

public enum DispositionPlanReleaseStatus {
	ACCEPT("ACCEPT", "已确认"),RELEASE("RELEASE", "已发布");
	private String statusCode;
	private String statusName;


	DispositionPlanReleaseStatus(String statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName = statusName;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
