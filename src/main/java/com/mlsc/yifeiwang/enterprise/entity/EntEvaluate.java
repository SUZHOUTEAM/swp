package com.mlsc.yifeiwang.enterprise.entity;

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
 * @since 2017-10-17
 */
@TableName("ent_evaluate")
public class EntEvaluate extends Model<EntEvaluate> {

    private static final long serialVersionUID = 1L;

	private String id;
	private String orderId; //评价订单id
	private Integer score;//
	private String comment;//评价分数
	private String evaluatedEntId;//被评价企业
	private String evaluatedBy;//评价企业
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getEvaluatedEntId() {
		return evaluatedEntId;
	}

	public void setEvaluatedEntId(String evaluatedEntId) {
		this.evaluatedEntId = evaluatedEntId;
	}

	public String getEvaluatedBy() {
		return evaluatedBy;
	}

	public void setEvaluatedBy(String evaluatedBy) {
		this.evaluatedBy = evaluatedBy;
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
		return "EntEvaluate{" +
			"id=" + id +
			", orderId=" + orderId +
			", score=" + score +
			", comment=" + comment +
			", evaluatedEntId=" + evaluatedEntId +
			", evaluatedBy=" + evaluatedBy +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", deleteFlag=" + deleteFlag +
			"}";
	}
}
