package com.mlsc.yifeiwang.bindserve.entity;

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
 * @since 2018-04-26
 */
@TableName("ent_bind_serve")
public class EntBindServe extends Model<EntBindServe> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String bindServiceId;
	private String serviceType; // 绑定服务
	private String bindEntId;
	private String bindUserId;
	private Integer consumeAmount; //消费豆
	private Integer remainAmount; //剩余豆
	private Date consumeTime; // 消费日期
	private String remark;
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

	public String getBindServiceId() {
		return bindServiceId;
	}

	public void setBindServiceId(String bindServiceId) {
		this.bindServiceId = bindServiceId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getBindEntId() {
		return bindEntId;
	}

	public void setBindEntId(String bindEntId) {
		this.bindEntId = bindEntId;
	}

	public String getBindUserId() {
		return bindUserId;
	}

	public void setBindUserId(String bindUserId) {
		this.bindUserId = bindUserId;
	}

	public Integer getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(Integer consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public Integer getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(Integer remainAmount) {
		this.remainAmount = remainAmount;
	}

	public Date getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(Date consumeTime) {
		this.consumeTime = consumeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return "EntBindServe{" +
			"id=" + id +
			", bindServiceId=" + bindServiceId +
			", serviceType=" + serviceType +
			", bindEntId=" + bindEntId +
			", consumeAmount=" + consumeAmount +
			", remainAmount=" + remainAmount +
			", consumeTime=" + consumeTime +
			", remark=" + remark +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			"}";
	}
}
