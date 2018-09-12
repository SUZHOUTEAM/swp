package com.mlsc.yifeiwang.entwaste.model;

/**
 * Created by user on 2017/9/12.
 */
public class EntWasteParams {
    private String entWasteId; //企业危废Id
    private String entId; //企业Id
    private String wasteId; //危废id
    private String wasteCode;//8位码
    private String wasteName; //危废名称
    private String wasteNameId; //危废名称Id
    private String harmfulSubstance ; //有害物质
    private String wasteTypeDesc; //二位码描述
    private String unitCode; //单位编码（T/KG）
    private String businessCode;//上传文件id
    private Integer startRowIndex;//第几页
    private Integer rows;//每页多少行
    private Boolean inquiried;//是否已询价

    public String getEntWasteId() {
        return entWasteId;
    }

    public void setEntWasteId(String entWasteId) {
        this.entWasteId = entWasteId;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

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

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public String getWasteNameId() {
        return wasteNameId;
    }

    public void setWasteNameId(String wasteNameId) {
        this.wasteNameId = wasteNameId;
    }

    public String getHarmfulSubstance() {
        return harmfulSubstance;
    }

    public void setHarmfulSubstance(String harmfulSubstance) {
        this.harmfulSubstance = harmfulSubstance;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Integer getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(Integer startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getWasteTypeDesc() {
        return wasteTypeDesc;
    }

    public void setWasteTypeDesc(String wasteTypeDesc) {
        this.wasteTypeDesc = wasteTypeDesc;
    }

    public Boolean getInquiried() {
        return inquiried;
    }

    public void setInquiried(Boolean inquiried) {
        this.inquiried = inquiried;
    }
}
