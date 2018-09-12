package com.mlsc.waste.user.model;

import com.mlsc.epdp.common.annotation.Column;

public class UserEnterprise {
	
	@Column("userId")
	private String userId;
	
	@Column("phoneNo")
	private String phoneNo;
	
	@Column("enterName")
	private String enterName;
	
	@Column("token")
	private String token;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEnterName() {
		return enterName;
	}

	public void setEnterName(String enterName) {
		this.enterName = enterName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
