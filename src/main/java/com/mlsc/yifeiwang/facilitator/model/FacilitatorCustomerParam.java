package com.mlsc.yifeiwang.facilitator.model;

/**
 * Created by user on 2018/8/14.
 */
public class FacilitatorCustomerParam {
    private String entType;
    private String responsibleArea;
    private String customerName;
    private String facilitatorEntId;
    private String customerId;
    private int startRowIndex;
    private int rows;

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getResponsibleArea() {
        return responsibleArea;
    }

    public void setResponsibleArea(String responsibleArea) {
        this.responsibleArea = responsibleArea;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFacilitatorEntId() {
        return facilitatorEntId;
    }

    public void setFacilitatorEntId(String facilitatorEntId) {
        this.facilitatorEntId = facilitatorEntId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getStartRowIndex() {
        return startRowIndex;
    }

    public void setStartRowIndex(int startRowIndex) {
        this.startRowIndex = startRowIndex;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
