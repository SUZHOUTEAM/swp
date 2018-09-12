package com.mlsc.yifeiwang.enterprise.model;

import com.mlsc.yifeiwang.enterprise.entity.EntCustomer;
import com.mlsc.yifeiwang.enterprise.entity.EntEvaluate;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.waste.licence.model.OperationLicence;

import java.util.List;

/**
 * Created by user on 2017/10/24.
 */
public class SysEnterpriseModel extends SysEnterpriseBase {
    private List<EntCustomer> customerList;
    private List<EntGloryModel> entGloryList;
    private List<String> attachFiles;
    private List<String> contractFiles;
    private List<EntEvaluate> entEvaluates;
    private OperationLicence operationLicence;
    private String licenceFileId;
    private Double approvedQuantity;
    private boolean isAllCompleted;
    private boolean hasSummary;
    private boolean hasLicence;
    private boolean hasSalesNote;
    private boolean licenceAuditStatus;


    public List<EntCustomer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<EntCustomer> customerList) {
        this.customerList = customerList;
    }

    public List<EntGloryModel> getEntGloryList() {
        return entGloryList;
    }

    public void setEntGloryList(List<EntGloryModel> entGloryList) {
        this.entGloryList = entGloryList;
    }

    public List<String> getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(List<String> attachFiles) {
        this.attachFiles = attachFiles;
    }

    public List<String> getContractFiles() {
        return contractFiles;
    }

    public void setContractFiles(List<String> contractFiles) {
        this.contractFiles = contractFiles;
    }

    public List<EntEvaluate> getEntEvaluates() {
        return entEvaluates;
    }

    public void setEntEvaluates(List<EntEvaluate> entEvaluates) {
        this.entEvaluates = entEvaluates;
    }

    public OperationLicence getOperationLicence() {
        return operationLicence;
    }

    public void setOperationLicence(OperationLicence operationLicence) {
        this.operationLicence = operationLicence;
    }

    public String getLicenceFileId() {
        return licenceFileId;
    }

    public void setLicenceFileId(String licenceFileId) {
        this.licenceFileId = licenceFileId;
    }

    public Double getApprovedQuantity() {
        return approvedQuantity;
    }

    public void setApprovedQuantity(Double approvedQuantity) {
        this.approvedQuantity = approvedQuantity;
    }

    public boolean isAllCompleted() {
        return isAllCompleted;
    }

    public void setAllCompleted(boolean allCompleted) {
        isAllCompleted = allCompleted;
    }

    public boolean isHasSummary() {
        return hasSummary;
    }

    public void setHasSummary(boolean hasSummary) {
        this.hasSummary = hasSummary;
    }

    public boolean isHasLicence() {
        return hasLicence;
    }

    public void setHasLicence(boolean hasLicence) {
        this.hasLicence = hasLicence;
    }

    public boolean isHasSalesNote() {
        return hasSalesNote;
    }

    public void setHasSalesNote(boolean hasSalesNote) {
        this.hasSalesNote = hasSalesNote;
    }

    public boolean isLicenceAuditStatus() {
        return licenceAuditStatus;
    }

    public void setLicenceAuditStatus(boolean licenceAuditStatus) {
        this.licenceAuditStatus = licenceAuditStatus;
    }
}
