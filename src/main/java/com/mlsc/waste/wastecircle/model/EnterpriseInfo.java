package com.mlsc.waste.wastecircle.model;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class EnterpriseInfo extends Entity {
    private static final long serialVersionUID = 2821591994970094341L;
    private String id;
    private String enterName; // 企业名称
    private String enterCode; // 企业组织代码
    private String enterStatus; //企业状态
    private CodeValue enterType;// 企业类型 产废企业，处置企业
    private boolean enterWasteStatus; //是否含有危废或是否有有效许可证
    private String responsibleArea;
    private String responsibleCantonCode;
    private List<CoopBusVo> coopBusList = new ArrayList<CoopBusVo>(); //企业服务

    private String businessCode;
    private String fileId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterName() {
        return enterName;
    }

    public void setEnterName(String enterName) {
        this.enterName = enterName;
    }

    public String getEnterCode() {
        return enterCode;
    }

    public void setEnterCode(String enterCode) {
        this.enterCode = enterCode;
    }

    public boolean isEnterWasteStatus() {
        return enterWasteStatus;
    }

    public void setEnterWasteStatus(boolean enterWasteStatus) {
        this.enterWasteStatus = enterWasteStatus;
    }

    public CodeValue getEnterType() {
        return enterType;
    }

    public void setEnterType(CodeValue enterType) {
        this.enterType = enterType;
    }

    public String getResponsibleArea() {
        return responsibleArea;
    }

    public void setResponsibleArea(String responsibleArea) {
        this.responsibleArea = responsibleArea;
    }

    public String getResponsibleCantonCode() {
        return responsibleCantonCode;
    }

    public void setResponsibleCantonCode(String responsibleCantonCode) {
        this.responsibleCantonCode = responsibleCantonCode;
    }

    public List<CoopBusVo> getCoopBusList() {
        return coopBusList;
    }

    public void setCoopBusList(List<CoopBusVo> coopBusList) {
        this.coopBusList = coopBusList;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getEnterStatus() {
        return enterStatus;
    }

    public void setEnterStatus(String enterStatus) {
        this.enterStatus = enterStatus;
    }
}
