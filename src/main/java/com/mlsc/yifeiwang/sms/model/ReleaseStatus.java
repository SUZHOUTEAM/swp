package com.mlsc.yifeiwang.sms.model;

public enum ReleaseStatus {
	TERMINATION("TERMINATION", "终止"),NORMAL("NORMAL", "正常");
	private String statusCode;
	private String statusName;


	ReleaseStatus(String statusCode, String statusName) {
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
