/**
 * 
 */
package com.mlsc.waste.licence.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.base.entity.Entity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 经营许可证-核准经营内容封装类
 * @author zhugl
 *
 */
@Repository
public class OperationLicenceItemVo extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;
    
    @Column("licenceId")
    private String licenceId;  //主键
    
    @Column("itemId")
    private String itemId;  //主键
   
    @Column("approved_quantity") 
    private String approved_quantity;// 年许可量
    
    @Column("excuted_quantity")
    private String excuted_quantity;  //已处理量
    
    @Column("surplus_quantity")
    private String surplus_quantity;  //剩余量
    
    @Column("surplus_percentage")
    private String surplus_percentage;  //剩余百分比
    
    @Column("dispositionType")
    private String dispositionType;  //处置方式（value值）
    
    @Column("disposition_type_id")
    private String dispositionTypeId;  //处置方式Id
    
    @Column("itemRemark")
    private String itemRemark;  //备注
    
    
    private List<OperationLicenceDetailVo> listWasteTypeVo = new ArrayList<OperationLicenceDetailVo>();  //二位码list

    /**
     * @  return the itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @  return the approved_quantity
     */
    public String getApproved_quantity() {
        return approved_quantity;
    }

    /**
     * @param approved_quantity the approved_quantity to set
     */
    public void setApproved_quantity(String approved_quantity) {
        this.approved_quantity = approved_quantity;
    }

    /**
     * @  return the excuted_quantity
     */
    public String getExcuted_quantity() {
        return excuted_quantity;
    }

    /**
     * @param excuted_quantity the excuted_quantity to set
     */
    public void setExcuted_quantity(String excuted_quantity) {
        this.excuted_quantity = excuted_quantity;
    }

    /**
     * @  return the surplus_quantity
     */
    public String getSurplus_quantity() {
        return surplus_quantity;
    }

    /**
     * @param surplus_quantity the surplus_quantity to set
     */
    public void setSurplus_quantity(String surplus_quantity) {
        this.surplus_quantity = surplus_quantity;
    }

    /**
     * @  return the surplus_percentage
     */
    public String getSurplus_percentage() {
        return surplus_percentage;
    }

    /**
     * @param surplus_percentage the surplus_percentage to set
     */
    public void setSurplus_percentage(String surplus_percentage) {
        this.surplus_percentage = surplus_percentage;
    }

    /**
     * @  return the dispositionType
     */
    public String getDispositionType() {
        return dispositionType;
    }

    /**
     * @param dispositionType the dispositionType to set
     */
    public void setDispositionType(String dispositionType) {
        this.dispositionType = dispositionType;
    }
    
    public String getDispositionTypeId() {
		return dispositionTypeId;
	}

	public void setDispositionTypeId(String dispositionTypeId) {
		this.dispositionTypeId = dispositionTypeId;
	}

	/**
     * @return the itemRemark
     */
    public String getItemRemark() {
        return itemRemark;
    }

    /**
     * @param itemRemark the itemRemark to set
     */
    public void setItemRemark(String itemRemark) {
        this.itemRemark = itemRemark;
    }

    /**
     * @return the listWasteTypeVo
     */
    public List<OperationLicenceDetailVo> getListWasteTypeVo() {
        return listWasteTypeVo;
    }

    /**
     * @param listWasteTypeVo the listWasteTypeVo to set
     */
    public void setListWasteTypeVo(List<OperationLicenceDetailVo> listWasteTypeVo) {
        this.listWasteTypeVo = listWasteTypeVo;
    }

    /**
     * @return the licenceId
     */
    public String getLicenceId() {
        return licenceId;
    }

    /**
     * @param licenceId the licenceId to set
     */
    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }
}
