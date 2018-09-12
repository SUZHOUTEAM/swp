package com.mlsc.yifeiwang.wasterealase.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author yinxl
 * @since 2017-10-12
 */
@TableName("ent_release")
public class EntRelease extends Model<EntRelease> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 企业ID
     */
	private String entId;
	/**
	 * 服务商企业ID
	 */
	private String facilitatorEntId;

	private String remark;
    /**
     * 发布危废记录数
     */
	private Integer releaseCount;
	private String totalAmountDesc;
    /**
     * 总量
     */
	private Double totalAmount;
    /**
     * 发布类型（产废、处置-预留）
     */
	private String releaseType;
    /**
     * 业务状态
     */
	private String busiStatus;
	private String createBy;
	private Date createTime;
	private String editBy;
	private Date editTime;
	private Integer deleteFlag;
	private String activityId;


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

	public String getFacilitatorEntId() {
		return facilitatorEntId;
	}

	public void setFacilitatorEntId(String facilitatorEntId) {
		this.facilitatorEntId = facilitatorEntId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getReleaseCount() {
		return releaseCount;
	}

	public void setReleaseCount(Integer releaseCount) {
		this.releaseCount = releaseCount;
	}

	public String getTotalAmountDesc() {
		return totalAmountDesc;
	}

	public void setTotalAmountDesc(String totalAmountDesc) {
		this.totalAmountDesc = totalAmountDesc;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
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

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "EntRelease{" +
			"id=" + id +
			", entId=" + entId +
			", remark=" + remark +
			", releaseCount=" + releaseCount +
			", totalAmountDesc=" + totalAmountDesc +
			", totalAmount=" + totalAmount +
			", releaseType=" + releaseType +
			", busiStatus=" + busiStatus +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			"}";
	}
}
