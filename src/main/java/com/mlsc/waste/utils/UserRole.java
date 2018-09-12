package com.mlsc.waste.utils;

public enum UserRole {
	ADMIN("ADMIN", "管理员"), REGULAR("REGULAR", "普通用户");
	private String roleCode;
	private String roleName;

	UserRole(String roleCode, String roleName) {
		this.roleCode = roleCode;
		this.roleName = roleName;
	}
	
    public static String roleName(String roleCode) {  
        for (UserRole c : UserRole.values()) {  
            if (c.getRoleCode().equals(roleCode) ) {  
                return c.getRoleName();  
            }  
        }  
        return null;  
    }
    
    public static String roleCode(String roleName) {  
        for (UserRole c : UserRole.values()) {  
            if (c.getRoleName().equals(roleName) ) {  
                return c.getRoleCode();
            }  
        }  
        return null;  
    }

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}  
}
