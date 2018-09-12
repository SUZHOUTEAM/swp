package com.mlsc.waste.userenterpriseapproverecord.model;

import com.mlsc.epdp.common.annotation.Column;

public class AuditUserEnterpriseRecordVo extends UserEnterpriseApproveRecord {

	private static final long serialVersionUID = 2821591994970094340L;
	
	@Column("phonenum")
	private  String phonenum;//电话号码
	
	@Column("chineseName")
	private String chineseName;//姓名
	
	//头像
	
	//邮箱是否已验证

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	
}
