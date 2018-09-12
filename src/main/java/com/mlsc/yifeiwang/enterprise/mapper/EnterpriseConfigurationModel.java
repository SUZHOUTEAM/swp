package com.mlsc.yifeiwang.enterprise.mapper;

import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;

import java.util.List;

/**
 * Created by user on 2017/12/26.
 */
public class EnterpriseConfigurationModel {
    private String entId;
    private String licenceNo;
    private String entName;
    private String wasteType;
    private String fileId;
    private String entAddress;
    private String licenceId;
    private String configId;
    private String cantonCode;
    private String cantonName;
    private String startDate;
    private String endDate;
    private String page;
    private String index;
    private String sectionValue;
    private String section;
    private String dispositionType;
    private int processableCount;
    private String licenceEndDate;

    private List<EntWasteModel> entWasteModels;

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }

    public String getLicenceId() {
        return licenceId;
    }

    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public String getCantonName() {
        return cantonName;
    }

    public void setCantonName(String cantonName) {
        this.cantonName = cantonName;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSectionValue() {
        return sectionValue;
    }

    public void setSectionValue(String sectionValue) {
        this.sectionValue = sectionValue;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDispositionType() {
        return dispositionType;
    }

    public void setDispositionType(String dispositionType) {
        this.dispositionType = dispositionType;
    }

    public int getProcessableCount() {
        return processableCount;
    }

    public void setProcessableCount(int processableCount) {
        this.processableCount = processableCount;
    }

    public List<EntWasteModel> getEntWasteModels() {
        return entWasteModels;
    }

    public void setEntWasteModels(List<EntWasteModel> entWasteModels) {
        this.entWasteModels = entWasteModels;
    }

    public String getLicenceEndDate() {
        return licenceEndDate;
    }

    public void setLicenceEndDate(String licenceEndDate) {
        this.licenceEndDate = licenceEndDate;
    }
}
