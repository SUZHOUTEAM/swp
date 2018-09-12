package com.mlsc.waste.user.model;

public class UserApproveVo extends UserExtended {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5481574861375004543L;

	private String chineseName;

	private String phoneNum;

	private String userStatus;
	
	private String userStatusCode;

	private String entName;

	private String role;

	private String roleName;

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

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
	public String getUserStatusCode() {
		return userStatusCode;
	}

	public void setUserStatusCode(String userStatusCode) {
		this.userStatusCode = userStatusCode;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	@Override
	public String getRole() {
		return role;
	}

	@Override
	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
