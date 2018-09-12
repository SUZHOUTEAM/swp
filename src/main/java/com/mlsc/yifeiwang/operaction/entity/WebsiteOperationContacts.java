package com.mlsc.yifeiwang.operaction.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
@TableName("website_operation_contacts")
public class WebsiteOperationContacts extends Model<WebsiteOperationContacts> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String operationId;
	private String entId;
	private String entName;
	private String contactsName;
	private String contactsTel;
	private String busiStatus;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}


	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getBusiStatus() {
		return busiStatus;
	}

	public void setBusiStatus(String busiStatus) {
		this.busiStatus = busiStatus;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEditBy() {
		return editBy;
	}

	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WebsiteOperationContacts{" +
			"id=" + id +
			", operationId=" + operationId +
			", entName=" + entName +
			", contactsName=" + contactsName +
			", contactsTel=" + contactsTel +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			"}";
	}
}
