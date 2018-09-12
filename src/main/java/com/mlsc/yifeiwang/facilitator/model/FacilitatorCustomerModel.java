package com.mlsc.yifeiwang.facilitator.model;

import com.mlsc.yifeiwang.facilitator.entity.FacilitatorCustomer;

/**
 * Created by user on 2018/1/17.
 */
public class FacilitatorCustomerModel extends FacilitatorCustomer {
    private String customerName;
    private String customerAdd;
    private String contacts;
    private String contactsTel;
    private String responsibleArea;
    private String busiLicImg;
    private String licImg;
    private String authImg;
    private String facilitatorBusiLicImg;
    private String entType;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAdd() {
        return customerAdd;
    }

    public void setCustomerAdd(String customerAdd) {
        this.customerAdd = customerAdd;
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

    public String getResponsibleArea() {
        return responsibleArea;
    }

    public void setResponsibleArea(String responsibleArea) {
        this.responsibleArea = responsibleArea;
    }

    public String getBusiLicImg() {
        return busiLicImg;
    }

    public void setBusiLicImg(String busiLicImg) {
        this.busiLicImg = busiLicImg;
    }

    public String getLicImg() {
        return licImg;
    }

    public void setLicImg(String licImg) {
        this.licImg = licImg;
    }

    public String getAuthImg() {
        return authImg;
    }

    public void setAuthImg(String authImg) {
        this.authImg = authImg;
    }

    public String getFacilitatorBusiLicImg() {
        return facilitatorBusiLicImg;
    }

    public void setFacilitatorBusiLicImg(String facilitatorBusiLicImg) {
        this.facilitatorBusiLicImg = facilitatorBusiLicImg;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }
}
