package com.mlsc.yifeiwang.wasterealase.model;

import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseDetail;

/**
 * Created by user on 2018/4/2.
 */
public class EntReleaseDetailParam extends EntReleaseDetail {
    private String wasteId;
    private String wasteCode;
    private String wasteNameId;
    private String wasteName;
    private String unitCode;

    public String getWasteId() {
        return wasteId;
    }

    public void setWasteId(String wasteId) {
        this.wasteId = wasteId;
    }

    public String getWasteCode() {
        return wasteCode;
    }

    public void setWasteCode(String wasteCode) {
        this.wasteCode = wasteCode;
    }

    public String getWasteNameId() {
        return wasteNameId;
    }

    public void setWasteNameId(String wasteNameId) {
        this.wasteNameId = wasteNameId;
    }

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
}
