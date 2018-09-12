package com.mlsc.mobileservice.model;

import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.utils.Constant;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhugl
 * 手机端首页上处置企业购买产废的页面填充数据
 *
 */
public class BuyWasteResourcePageData extends Entity{
    
    private static final long serialVersionUID = 2821591994970094340L;
    
    private String planReleaseId;  //当前发布的资源ID（disposition_plan_release的ID）
    
    private String planReleaseUser;
    
	private String planReleaseItemId;  //当前发布的资源ID（disposition_planitem_release的ID）
    
    private String enterpriseWasteId;  //当前发布的资源ID关联的企业危废
    
    private String wasteNameId;  //危废名称ID
    
    private String wasteName;  //危废名称
    
    private String wasteTypeId;  //二位码ID
    
    private String wasteTypeCode;  //二位码code
    
    private String wasteTypeValue;  //二位码描述
    
    private String wasteId;  //八位码ID
    
    private String wasteCode;  //八位码
    
    private String enterpriseWasteDescription;  //有毒有害物质
    
    private String planStartDate;  //处置时间开始
    
    private String planEndDate;  //处置时间结束
    
    private BigDecimal planDisposeQuantity;  //需要处理数量
    
    private String unitCode;  //单位名称（吨:T/只:C）
    
    private String dispositionTypeId;  //选择的处置方式(页面上填写)
    
    private BigDecimal price;  //填写的单价(页面上填写)
    
    private String operatingPartyPayment;  //填写的【经营方支付】(页面上填写)
    
    private String isCrf = Constant.IS_YSE;  //填写的【是否包含运费】(页面上填写)
    
    private BigDecimal totalAmount;  //总金额(页面上填写)

    private String dispositionTypeSuggest ; //可以处理该危废的许可证里面的处置方式(页面上处置方式的下拉框值)
    
    private List<Uploadfile> imgList;//危废图片列表
    
    private String planVersionCode;

    private String remark;

    /**
     * @return the planReleaseId
     */
    public String getPlanReleaseId() {
        return planReleaseId;
    }

    /**
     * @param planReleaseId the planReleaseId to set
     */
    public void setPlanReleaseId(String planReleaseId) {
        this.planReleaseId = planReleaseId;
    }

    /**
     * @return the planReleaseItemId
     */
    public String getPlanReleaseItemId() {
        return planReleaseItemId;
    }

    /**
     * @param planReleaseItemId the planReleaseItemId to set
     */
    public void setPlanReleaseItemId(String planReleaseItemId) {
        this.planReleaseItemId = planReleaseItemId;
    }

    /**
     * @return the enterpriseWasteId
     */
    public String getEnterpriseWasteId() {
        return enterpriseWasteId;
    }

    /**
     * @param enterpriseWasteId the enterpriseWasteId to set
     */
    public void setEnterpriseWasteId(String enterpriseWasteId) {
        this.enterpriseWasteId = enterpriseWasteId;
    }

    /**
     * @return the wasteNameId
     */
    public String getWasteNameId() {
        return wasteNameId;
    }

    /**
     * @param wasteNameId the wasteNameId to set
     */
    public void setWasteNameId(String wasteNameId) {
        this.wasteNameId = wasteNameId;
    }

    /**
     * @return the wasteName
     */
    public String getWasteName() {
        return wasteName;
    }

    /**
     * @param wasteName the wasteName to set
     */
    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    /**
     * @return the wasteTypeId
     */
    public String getWasteTypeId() {
        return wasteTypeId;
    }

    /**
     * @param wasteTypeId the wasteTypeId to set
     */
    public void setWasteTypeId(String wasteTypeId) {
        this.wasteTypeId = wasteTypeId;
    }

    /**
     * @return the wasteTypeCode
     */
    public String getWasteTypeCode() {
        return wasteTypeCode;
    }

    /**
     * @param wasteTypeCode the wasteTypeCode to set
     */
    public void setWasteTypeCode(String wasteTypeCode) {
        this.wasteTypeCode = wasteTypeCode;
    }

    /**
     * @return the wasteTypeValue
     */
    public String getWasteTypeValue() {
        return wasteTypeValue;
    }

    /**
     * @param wasteTypeValue the wasteTypeValue to set
     */
    public void setWasteTypeValue(String wasteTypeValue) {
        this.wasteTypeValue = wasteTypeValue;
    }

    /**
     * @return the wasteId
     */
    public String getWasteId() {
        return wasteId;
    }

    /**
     * @param wasteId the wasteId to set
     */
    public void setWasteId(String wasteId) {
        this.wasteId = wasteId;
    }

    /**
     * @return the wasteCode
     */
    public String getWasteCode() {
        return wasteCode;
    }

    /**
     * @param wasteCode the wasteCode to set
     */
    public void setWasteCode(String wasteCode) {
        this.wasteCode = wasteCode;
    }

    /**
     * @return the enterpriseWasteDescription
     */
    public String getEnterpriseWasteDescription() {
        return enterpriseWasteDescription;
    }

    /**
     * @param enterpriseWasteDescription the enterpriseWasteDescription to set
     */
    public void setEnterpriseWasteDescription(String enterpriseWasteDescription) {
        this.enterpriseWasteDescription = enterpriseWasteDescription;
    }

    /**
     * @return the planStartDate
     */
    public String getPlanStartDate() {
        return planStartDate;
    }

    /**
     * @param planStartDate the planStartDate to set
     */
    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    /**
     * @return the planEndDate
     */
    public String getPlanEndDate() {
        return planEndDate;
    }

    /**
     * @param planEndDate the planEndDate to set
     */
    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    /**
     * @return the planDisposeQuantity
     */
    public BigDecimal getPlanDisposeQuantity() {
        return planDisposeQuantity;
    }

    /**
     * @param planDisposeQuantity the planDisposeQuantity to set
     */
    public void setPlanDisposeQuantity(BigDecimal planDisposeQuantity) {
        this.planDisposeQuantity = planDisposeQuantity;
    }

    /**
     * @return the unitCode
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * @param unitCode the unitCode to set
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    /**
     * @return the dispositionTypeId
     */
    public String getDispositionTypeId() {
        return dispositionTypeId;
    }

    /**
     * @param dispositionTypeId the dispositionTypeId to set
     */
    public void setDispositionTypeId(String dispositionTypeId) {
        this.dispositionTypeId = dispositionTypeId;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the operatingPartyPayment
     */
    public String getOperatingPartyPayment() {
        return operatingPartyPayment;
    }

    /**
     * @param operatingPartyPayment the operatingPartyPayment to set
     */
    public void setOperatingPartyPayment(String operatingPartyPayment) {
        this.operatingPartyPayment = operatingPartyPayment;
    }

    /**
     * @return the isCrf
     */
    public String getIsCrf() {
        return isCrf;
    }

    /**
     * @param isCrf the isCrf to set
     */
    public void setIsCrf(String isCrf) {
        this.isCrf = isCrf;
    }

    /**
     * @return the totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the dispositionTypeSuggest
     */
    public String getDispositionTypeSuggest() {
        return dispositionTypeSuggest;
    }

    /**
     * @param dispositionTypeSuggest the dispositionTypeSuggest to set
     */
    public void setDispositionTypeSuggest(String dispositionTypeSuggest) {
        this.dispositionTypeSuggest = dispositionTypeSuggest;
    }
    
    
    public String getPlanReleaseUser() {
		return planReleaseUser;
	}

	public void setPlanReleaseUser(String planReleaseUser) {
		this.planReleaseUser = planReleaseUser;
	}

	public List<Uploadfile> getImgList() {
		return imgList;
	}

	public void setImgList(List<Uploadfile> imgList) {
		this.imgList = imgList;
	}

	public String getPlanVersionCode() {
		return planVersionCode;
	}

	public void setPlanVersionCode(String planVersionCode) {
		this.planVersionCode = planVersionCode;
	}

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
