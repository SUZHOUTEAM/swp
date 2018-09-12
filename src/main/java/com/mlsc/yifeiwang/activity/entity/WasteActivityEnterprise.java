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
 * @since 2017-07-31
 */
@TableName("waste_activity_enterprise")
public class WasteActivityEnterprise extends Model<WasteActivityEnterprise> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据id
     */
	private String id;
    /**
     * 活动id
     */
	@TableField("activity_id")
	private String activityId;
    /**
     * 参与企业id
     */
	@TableField("ent_id")
	private String entId;
    /**
     * 参与企业名称
     */
	@TableField("ent_name")
	private String entName;
    /**
     * 企业说明
     */
	@TableField("ent_remark")
	private String entRemark;
    /**
     * 价格类型
     */
	@TableField("start_price")
	private String startPrice;
    /**
     * 价格类型
     */
	@TableField("end_price")
	private String endPrice;
    /**
     * 数据状态
     */
	private String status;
	@TableField("create_by")
	private String createBy;
	@TableField("create_time")
	private Date createTime;
	@TableField("edit_by")
	private String editBy;
	@TableField("edit_time")
	private Date editTime;


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

	public String getEntRemark() {
		return entRemark;
	}

	public void setEntRemark(String entRemark) {
		this.entRemark = entRemark;
	}

	public String getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}

	public String getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(String endPrice) {
		this.endPrice = endPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "WasteActivityEnterprise{" +
			"id=" + id +
			", activityId=" + activityId +
			", entId=" + entId +
			", entName=" + entName +
			", entRemark=" + entRemark +
			", startPrice=" + startPrice +
			", endPrice=" + endPrice +
			", status=" + status +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			"}";
	}
}
