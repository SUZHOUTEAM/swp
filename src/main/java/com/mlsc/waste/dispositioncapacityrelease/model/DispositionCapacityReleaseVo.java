package com.mlsc.waste.dispositioncapacityrelease.model;

import com.mlsc.epdp.common.annotation.Column;

import java.util.ArrayList;
import java.util.List;

public class DispositionCapacityReleaseVo {
    
    @Column("capacity_release_id")
    private String capacityReleaseId; //发布id
    
    @Column("item_id")
    private String itemId;//可处置信息Id
    
    @Column("disposition_type")
    private String dispositionTypeId;//处置类型id
    
    @Column("disposition_type_name")
    private String dispositionTypeName;//处置名称
    
    @Column("quota_quantity")
    private String quotaQuantity;//处置数量
    
    @Column("waste_type_code_id")
    private String wasteTypeCodeId;//二位码id
    
    @Column("waste_type_code")
    private String wasteTypeCode;//二位码
    
    @Column("disposition_code")
    private String dispositionCode;//处置类型Code
    
    private List<DispositionCapacityDetailReleaseVo> capacityDetailList = new ArrayList<DispositionCapacityDetailReleaseVo>();

    public String getCapacityReleaseId() {
        return capacityReleaseId;
    }

    public void setCapacityReleaseId(String capacityReleaseId) {
        this.capacityReleaseId = capacityReleaseId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDispositionTypeId() {
        return dispositionTypeId;
    }

    public void setDispositionTypeId(String dispositionTypeId) {
        this.dispositionTypeId = dispositionTypeId;
    }

    public String getDispositionTypeName() {
        return dispositionTypeName;
    }

    public void setDispositionTypeName(String dispositionTypeName) {
        this.dispositionTypeName = dispositionTypeName;
    }

    public String getQuotaQuantity() {
        return quotaQuantity;
    }

    public void setQuotaQuantity(String quotaQuantity) {
        this.quotaQuantity = quotaQuantity;
    }

    public String getWasteTypeCodeId() {
        return wasteTypeCodeId;
    }

    public void setWasteTypeCodeId(String wasteTypeCodeId) {
        this.wasteTypeCodeId = wasteTypeCodeId;
    }

    public String getDispositionCode() {
        return dispositionCode;
    }

    public void setDispositionCode(String dispositionCode) {
        this.dispositionCode = dispositionCode;
    }

    public String getWasteTypeCode() {
        return wasteTypeCode;
    }

    public void setWasteTypeCode(String wasteTypeCode) {
        this.wasteTypeCode = wasteTypeCode;
    }

    public List<DispositionCapacityDetailReleaseVo> getCapacityDetailList() {
        return capacityDetailList;
    }

    public void setCapacityDetailList(List<DispositionCapacityDetailReleaseVo> capacityDetailList) {
        this.capacityDetailList = capacityDetailList;
    }


}
