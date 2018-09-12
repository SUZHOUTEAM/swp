package com.mlsc.yifeiwang.sms.model;


public enum NoticeRouteEnum {
	MYORDER("myOrder", "确认了你的购买信息"), DISPOSTIONCAPACITYRELEASE("dispostionCapacityRelease", "购买了您的处置能力，敬请关注"),WASTECIRCLE("wasteCircle","发布了最新需要处理的危废信息，敬请关注"),DISPOSTIONRELEASE("dispostionRelease","购买了您的危废，敬请关注");
   
    
    public static String rountCode(String context) {  
        for (NoticeRouteEnum c : NoticeRouteEnum.values()) {  
            if (context.contains(c.getContext())) {  
                return c.getRountCode();
            }  
        }  
        return null;  
    }
    
	private String rountCode;
	private String context;
	
	NoticeRouteEnum(String rountCode, String context) {
		this.rountCode = rountCode;
		this.context = context;
	}
	
	
	
	public String getRountCode() {
		return rountCode;
	}
	public void setRountCode(String rountCode) {
		this.rountCode = rountCode;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
	
}
