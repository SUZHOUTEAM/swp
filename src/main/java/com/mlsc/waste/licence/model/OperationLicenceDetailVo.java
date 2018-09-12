/**
 * 
 */
package com.mlsc.waste.licence.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.base.entity.Entity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 经营许可证-核准经营内容封装类
 * @author zhugl
 *
 */
@Repository
public class OperationLicenceDetailVo extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;
    
    @Column("licenceId")
    private String licenceId;
    
    @Column("itemId")
    private String itemId;
    
    @Column("detailId")
    private String detailId;
    
    @Column("wasteTypeId")
    private String wasteTypeId;
    
    @Column("wasteTypeCode") 
    private String wasteTypeCode;// 二位码code值
    
    @Column("wasteTypeValue")
    private String wasteTypeValue;  //二位码code + 描述
    
    @Column("wasteId")
    private String wasteId;
    
    @Column("wasteCode")
    private String wasteCode;  //八位码的code值
    
    @Column("wasteNameId")
    private String wasteNameId;
    
    @Column("wasteName")
    private String wasteName;
    
    @Column("approved_quantity")
    private String approvedQuantity;  //许可数量
    
    @Column("excuted_quantity")
    private String excutedQuantity;  //已执行数量
    
    private List<String> listWasteVo;  //八位码list

    /**
     * @  return the detailId
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * @param detailId the detailId to set
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

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
     * @  return the wasteTypeId
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
     * @  return the wasteTypeCode
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
     * @  return the wasteTypeValue
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
     * @param wasteCode the wasteCode to set
     */
    public void setWasteCode(String wasteCode) {
        this.wasteCode = wasteCode;
    }

    /**
     * @  return the wasteCode
     */
    public String getWasteCode() {
        return wasteCode;
    }

    /**
     * @return the listWasteVo
     */
    public List<String> getListWasteVo() {
        return listWasteVo;
    }

    /**
     * @param listWasteVo the listWasteVo to set
     */
    public void setListWasteVo(List<String> listWasteVo) {
        this.listWasteVo = listWasteVo;
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
     * @return the approvedQuantity
     */
    public String getApprovedQuantity() {
        return approvedQuantity;
    }

    /**
     * @param approvedQuantity the approvedQuantity to set
     */
    public void setApprovedQuantity(String approvedQuantity) {
        this.approvedQuantity = approvedQuantity;
    }

    /**
     * @return the excutedQuantity
     */
    public String getExcutedQuantity() {
        return excutedQuantity;
    }

    /**
     * @param excutedQuantity the excutedQuantity to set
     */
    public void setExcutedQuantity(String excutedQuantity) {
        this.excutedQuantity = excutedQuantity;
    }
}
