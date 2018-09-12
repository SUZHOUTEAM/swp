package com.mlsc.waste.utils;

public enum UserStatus {
	
	SUBMIT("SUBMIT", "提交"), PASS("PASS", "审批通过"),REJECT("REJECT","审批拒绝"),QUIT("QUIT","被退出企业");
	
	private String statusCode;
	private String statusName;

	UserStatus(String statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName = statusName;
	}
	
    public static String statusName(String statusCode) {  
        for (UserStatus c : UserStatus.values()) {  
            if (c.getStatusCode().equals(statusCode) ) {  
                return c.getStatusName();  
            }  
        }  
        return null;  
    }
    
    public static String statusCode(String statusName) {  
        for (UserStatus c : UserStatus.values()) {  
            if (c.getStatusName().equals(statusName) ) {  
                return c.getStatusCode();
            }  
        }  
        return null;  
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
