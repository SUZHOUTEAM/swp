package com.mlsc.mobileservice.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityDetailReleaseDto;

public class DispositionCapacityDetailReleaseVo extends DispositionCapacityDetailReleaseDto {

    private static final long serialVersionUID = 2821591994970094340L;

    @Column("waste_name")
    private String wasteName; // 危废名称id

    @Column("flag")
    private String flag;// 是否可处置的标志

    @Column("waste_type_description")
    private String wasteTypeDescription;// 二位码描述

    @Column("waste_type_code")
    private String wasteTypeCode;// 二位码

    @Column("disposition_type_id")
    private String dispositionTypeId;// 处置方式id

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getWasteTypeDescription() {
        return wasteTypeDescription;
    }

    public void setWasteTypeDescription(String wasteTypeDescription) {
        this.wasteTypeDescription = wasteTypeDescription;
    }

    public String getWasteTypeCode() {
        return wasteTypeCode;
    }

    public void setWasteTypeCode(String wasteTypeCode) {
        this.wasteTypeCode = wasteTypeCode;
    }

    public String getDispositionTypeId() {
        return dispositionTypeId;
    }

    public void setDispositionTypeId(String dispositionTypeId) {
        this.dispositionTypeId = dispositionTypeId;
    }

}
