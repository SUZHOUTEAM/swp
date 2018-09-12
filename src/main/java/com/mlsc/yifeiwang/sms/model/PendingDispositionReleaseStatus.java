package com.mlsc.yifeiwang.sms.model;

public enum PendingDispositionReleaseStatus {
	ACCEPT("ACCEPT", "已发布"),SUBMIT("SUBMIT", "已提交");
	
	private String statusCode;
	private String statusName;


	PendingDispositionReleaseStatus(String statusCode, String statusName) {
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
