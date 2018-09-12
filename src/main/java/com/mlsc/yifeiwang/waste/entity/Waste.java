package com.mlsc.yifeiwang.waste.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangj
 * @since 2017-08-23
 */
public class Waste extends Model<Waste> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("waste_type_id")
	private String wasteTypeId;
	@TableField("calling_id")
	private String callingId;
	private String code;
	private String description;
    /**
     * 状态(0未启用、1启用)
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
    /**
     * 腐蚀性(0有腐蚀性、1无腐蚀性)
     */
	private String corrosivity;
    /**
     * 毒性(0有毒性、1无毒性)
     */
	private String toxicity;
    /**
     * 易燃性(0有易燃性、1无易燃性)
     */
	private String ignitability;
    /**
     * 反应性(0有反应性、1无反应性)
     */
	private String reactivity;
    /**
     * 感染性(0有感染性、1无感染性)
     */
	private String infectivity;
	@TableField("waste_type_code")
	private String wasteTypeCode;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWasteTypeId() {
		return wasteTypeId;
	}

	public void setWasteTypeId(String wasteTypeId) {
		this.wasteTypeId = wasteTypeId;
	}

	public String getCallingId() {
		return callingId;
	}

	public void setCallingId(String callingId) {
		this.callingId = callingId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getCorrosivity() {
		return corrosivity;
	}

	public void setCorrosivity(String corrosivity) {
		this.corrosivity = corrosivity;
	}

	public String getToxicity() {
		return toxicity;
	}

	public void setToxicity(String toxicity) {
		this.toxicity = toxicity;
	}

	public String getIgnitability() {
		return ignitability;
	}

	public void setIgnitability(String ignitability) {
		this.ignitability = ignitability;
	}

	public String getReactivity() {
		return reactivity;
	}

	public void setReactivity(String reactivity) {
		this.reactivity = reactivity;
	}

	public String getInfectivity() {
		return infectivity;
	}

	public void setInfectivity(String infectivity) {
		this.infectivity = infectivity;
	}

	public String getWasteTypeCode() {
		return wasteTypeCode;
	}

	public void setWasteTypeCode(String wasteTypeCode) {
		this.wasteTypeCode = wasteTypeCode;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Waste{" +
			"id=" + id +
			", wasteTypeId=" + wasteTypeId +
			", callingId=" + callingId +
			", code=" + code +
			", description=" + description +
			", status=" + status +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", corrosivity=" + corrosivity +
			", toxicity=" + toxicity +
			", ignitability=" + ignitability +
			", reactivity=" + reactivity +
			", infectivity=" + infectivity +
			", wasteTypeCode=" + wasteTypeCode +
			"}";
	}
}
