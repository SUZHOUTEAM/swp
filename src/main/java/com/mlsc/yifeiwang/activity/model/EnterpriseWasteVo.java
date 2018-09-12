package com.mlsc.yifeiwang.activity.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
public class EnterpriseWasteVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private  String wasteName;

    private  String wasteId;

    private  String unitId;

    private  String unitCode;

    private  String unitValue;


    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public String getWasteId() {
        return wasteId;
    }

    public void setWasteId(String wasteId) {
        this.wasteId = wasteId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }
}
