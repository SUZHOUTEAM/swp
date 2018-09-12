package com.mlsc.yifeiwang.activity.entity;

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
 * @since 2018-02-05
 */
@TableName("waste_activity_enroll")
public class WasteActivityEnroll extends Model<WasteActivityEnroll> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String entId;
	private String userId;
	private Double fee;
	private String activityId;
    /**
     * 业务状态
     */
	private String busiStatus;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private Integer deleteFlag;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
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

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "WasteActivityEnroll{" +
			"id=" + id +
			", entId=" + entId +
			", userId=" + userId +
			", fee=" + fee +
			", activityId=" + activityId +
			", busiStatus=" + busiStatus +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			"}";
	}
}
