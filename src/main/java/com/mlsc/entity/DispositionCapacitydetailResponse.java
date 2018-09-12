package com.mlsc.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 处置资源明细-应答
 * </p>
 *
 * @author yinxl
 * @since 2017-08-25
 */
@TableName("disposition_capacitydetail_response")
public class DispositionCapacitydetailResponse extends Model<DispositionCapacitydetailResponse> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("capacity_response_id")
	private String capacityResponseId;
	@TableField("capacitydetai_release_id")
	private String capacitydetaiReleaseId;
	@TableField("waste_id")
	private String wasteId;
	@TableField("waste_name_id")
	private String wasteNameId;
	@TableField("response_quantity")
	private Double responseQuantity;
	@TableField("accept_quantity")
	private Double acceptQuantity;
	@TableField("response_price")
	private Double responsePrice;
	@TableField("response_amount")
	private Double responseAmount;
	@TableField("disposition_type_id")
	private String dispositionTypeId;
	@TableField("operating_party_payment")
	private String operatingPartyPayment;
	@TableField("is_cfr")
	private String isCfr;
	@TableField("response_date")
	private Date responseDate;
	@TableField("accept_date")
	private Date acceptDate;
	@TableField("response_status")
	private String responseStatus;
	@TableField("create_by")
	private String createBy;
	@TableField("create_time")
	private Date createTime;
	@TableField("edit_by")
	private String editBy;
	@TableField("edit_time")
	private Date editTime;
	private String valid;
	private String remark;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCapacityResponseId() {
		return capacityResponseId;
	}

	public void setCapacityResponseId(String capacityResponseId) {
		this.capacityResponseId = capacityResponseId;
	}

	public String getCapacitydetaiReleaseId() {
		return capacitydetaiReleaseId;
	}

	public void setCapacitydetaiReleaseId(String capacitydetaiReleaseId) {
		this.capacitydetaiReleaseId = capacitydetaiReleaseId;
	}

	public String getWasteId() {
		return wasteId;
	}

	public void setWasteId(String wasteId) {
		this.wasteId = wasteId;
	}

	public String getWasteNameId() {
		return wasteNameId;
	}

	public void setWasteNameId(String wasteNameId) {
		this.wasteNameId = wasteNameId;
	}

	public Double getResponseQuantity() {
		return responseQuantity;
	}

	public void setResponseQuantity(Double responseQuantity) {
		this.responseQuantity = responseQuantity;
	}

	public Double getAcceptQuantity() {
		return acceptQuantity;
	}

	public void setAcceptQuantity(Double acceptQuantity) {
		this.acceptQuantity = acceptQuantity;
	}

	public Double getResponsePrice() {
		return responsePrice;
	}

	public void setResponsePrice(Double responsePrice) {
		this.responsePrice = responsePrice;
	}

	public Double getResponseAmount() {
		return responseAmount;
	}

	public void setResponseAmount(Double responseAmount) {
		this.responseAmount = responseAmount;
	}

	public String getDispositionTypeId() {
		return dispositionTypeId;
	}

	public void setDispositionTypeId(String dispositionTypeId) {
		this.dispositionTypeId = dispositionTypeId;
	}

	public String getOperatingPartyPayment() {
		return operatingPartyPayment;
	}

	public void setOperatingPartyPayment(String operatingPartyPayment) {
		this.operatingPartyPayment = operatingPartyPayment;
	}

	public String getIsCfr() {
		return isCfr;
	}

	public void setIsCfr(String isCfr) {
		this.isCfr = isCfr;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
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

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "DispositionCapacitydetailResponse{" +
			"id=" + id +
			", capacityResponseId=" + capacityResponseId +
			", capacitydetaiReleaseId=" + capacitydetaiReleaseId +
			", wasteId=" + wasteId +
			", wasteNameId=" + wasteNameId +
			", responseQuantity=" + responseQuantity +
			", acceptQuantity=" + acceptQuantity +
			", responsePrice=" + responsePrice +
			", responseAmount=" + responseAmount +
			", dispositionTypeId=" + dispositionTypeId +
			", operatingPartyPayment=" + operatingPartyPayment +
			", isCfr=" + isCfr +
			", responseDate=" + responseDate +
			", acceptDate=" + acceptDate +
			", responseStatus=" + responseStatus +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", valid=" + valid +
			", remark=" + remark +
			"}";
	}
}
