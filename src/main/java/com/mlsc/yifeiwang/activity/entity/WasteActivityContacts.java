package com.mlsc.yifeiwang.activity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yxl
 * @since 2017-08-01
 */
@TableName("waste_activity_contacts")
public class WasteActivityContacts extends Model<WasteActivityContacts> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("activity_id")
	private String activityId;
	@TableField("user_name")
	private String userName;
	@TableField("user_phone")
	private String userPhone;
	private String status;
	@TableField("create_time")
	private Date createTime;
	@TableField("create_by")
	private String createBy;
	@TableField("update_time")
	private Date updateTime;
	@TableField("update_by")
	private String updateBy;
	@TableField("ent_id")
	private String entId;
	@TableField("ent_name")
	private String entName;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WasteActivityContacts{" +
			"id=" + id +
			", activityId=" + activityId +
			", userName=" + userName +
			", userPhone=" + userPhone +
			", status=" + status +
			", createTime=" + createTime +
			", createBy=" + createBy +
			"}";
	}
}
