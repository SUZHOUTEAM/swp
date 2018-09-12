package com.mlsc.solr.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Map;

/**
 * 企业索引
 */
public class EnterpriseIndex {

    @Field
    private String id;

    @Field
    private String entName;

    @Field
    private String shortName;

    @Field
    private String nameSpelling;

    private String entType;

    @Field
    private String entCode;

    @Field
    private String legalName;

    @Field
    private String contacts;

    @Field
    private String contactsTel;

    @Field
    private String contactsEMail;

    @Field
    private String fax;

    @Field
    private String zipCode;

    @Field
    private String entAddress;

    @Field
    private int entStatus;

    @Field
    private String wasteName;

    @Field
    private Map<String, StringBuilder> wasteCode;

    @Field
    private String dispositionType;

    @Field
    private String liceneNo;

    @Field
    private String endDate;

    private String abilityInformation;

    @Field
    private String approvedQuantity;

    private String fileId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getNameSpelling() {
        return nameSpelling;
    }

    public void setNameSpelling(String nameSpelling) {
        this.nameSpelling = nameSpelling;
    }


    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getEntCode() {
        return entCode;
    }

    public void setEntCode(String entCode) {
        this.entCode = entCode;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
    }

    public String getContactsEMail() {
        return contactsEMail;
    }

    public void setContactsEMail(String contactsEMail) {
        this.contactsEMail = contactsEMail;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEntAddress() {
        return entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.entAddress = entAddress;
    }

    public int getEntStatus() {
        return entStatus;
    }

    public void setEntStatus(int entStatus) {
        this.entStatus = entStatus;
    }

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public Map<String, StringBuilder> getWasteCode() {
        return wasteCode;
    }

    public void setWasteCode(Map<String, StringBuilder> wasteCode) {
        this.wasteCode = wasteCode;
    }

    public String getDispositionType() {
        return dispositionType;
    }

    public void setDispositionType(String dispositionType) {
        this.dispositionType = dispositionType;
    }

    public String getLiceneNo() {
        return liceneNo;
    }

    public void setLiceneNo(String liceneNo) {
        this.liceneNo = liceneNo;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAbilityInformation() {
        return abilityInformation;
    }

    public void setAbilityInformation(String abilityInformation) {
        this.abilityInformation = abilityInformation;
    }

    public String getApprovedQuantity() {
        return approvedQuantity;
    }

    public void setApprovedQuantity(String approvedQuantity) {
        this.approvedQuantity = approvedQuantity;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
