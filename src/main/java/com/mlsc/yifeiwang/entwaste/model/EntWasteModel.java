package com.mlsc.yifeiwang.entwaste.model;

import com.mlsc.entity.UploadFile;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2017/9/12.
 */
public class EntWasteModel implements Serializable{
    private String entWasteId; //企业危废id
    private String wasteId;//危废Id
    private String wasteCode;//8位码
    private String wasteNameId;//危废名称id
    private String wasteName;//危废名称
    private String unitCode;//单位code
    private String unitValue;//单位
    private String wasteTypeDesc;//二位码和描述
    private Boolean inquiried;//是否已询价
    private String harmfulSubstance; //有害物质名称和含量
    private List<UploadFile> fileList; //危废图片
    private List<String> wasteNameList;

    public String getEntWasteId() {
        return entWasteId;
    }

    public void setEntWasteId(String entWasteId) {
        this.entWasteId = entWasteId;
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

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
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

    public String getHarmfulSubstance() {
        return harmfulSubstance;
    }

    public void setHarmfulSubstance(String harmfulSubstance) {
        this.harmfulSubstance = harmfulSubstance;
    }

    public List<UploadFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<UploadFile> fileList) {
        this.fileList = fileList;
    }

    public List<String> getWasteNameList() {
        return wasteNameList;
    }

    public void setWasteNameList(List<String> wasteNameList) {
        this.wasteNameList = wasteNameList;
    }
}
