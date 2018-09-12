package com.mlsc.waste.dispositioncapacityrelease.model;

import com.mlsc.epdp.common.annotation.Column;

public class DispositionCapacityDetailReleaseVo {

    @Column("detailReleaseId")
    private String detailReleaseId;  //disposition_capacitydetail_release表的ID

    @Column("waste_id")
    private String wasteId;

    @Column("eightCode")
    private String eightCode;

    @Column("capacityitemId")
    private String capacityitemId;

    @Column("dispositionId")
    private String dispositionId;

    @Column("dispostionName")
    private String dispositionName;

    @Column("disType")
    private String disType;

    @Column("distValue")
    private String distValue;

    @Column("waste_type_id")
    private String wasteTypeId;

    private String wasteType;

    @Column("is_dis")
    private String isDis; //是否是处置过的

    @Column("wasteName")
    private String wasteName;

    @Column("waste_name_id")
    private String wasteNameId;

    @Column("capacity_release_id")
    private String capacityReleaseId;

    @Column("response_Count")
    private String responseCount;


    /**
     * @return the detailReleaseId
     */
    public String getDetailReleaseId() {
        return detailReleaseId;
    }

    /**
     * @param detailReleaseId the detailReleaseId to set
     */
    public void setDetailReleaseId(String detailReleaseId) {
        this.detailReleaseId = detailReleaseId;
    }

    /**
     * @return the wasteId
     */
    public String getWasteId() {
        return wasteId;
    }

    /**
     * @param wasteId the wasteId to set
     */
    public void setWasteId(String wasteId) {
        this.wasteId = wasteId;
    }

    /**
     * @return the eightCode
     */
    public String getEightCode() {
        return eightCode;
    }

    /**
     * @param eightCode the eightCode to set
     */
    public void setEightCode(String eightCode) {
        this.eightCode = eightCode;
    }

    /**
     * @return the capacityitemId
     */
    public String getCapacityitemId() {
        return capacityitemId;
    }

    /**
     * @param capacityitemId the capacityitemId to set
     */
    public void setCapacityitemId(String capacityitemId) {
        this.capacityitemId = capacityitemId;
    }

    public String getDispositionId() {
        return dispositionId;
    }

    public void setDispositionId(String dispositionId) {
        this.dispositionId = dispositionId;
    }

    public String getDispositionName() {
        return dispositionName;
    }

    public void setDispositionName(String dispositionName) {
        this.dispositionName = dispositionName;
    }

    /**
     * @return the disType
     */
    public String getDisType() {
        return disType;
    }

    /**
     * @param disType the disType to set
     */
    public void setDisType(String disType) {
        this.disType = disType;
    }

    /**
     * @return the distValue
     */
    public String getDistValue() {
        return distValue;
    }

    /**
     * @param distValue the distValue to set
     */
    public void setDistValue(String distValue) {
        this.distValue = distValue;
    }

    /**
     * @return the wasteTypeId
     */
    public String getWasteTypeId() {
        return wasteTypeId;
    }

    /**
     * @param wasteTypeId the wasteTypeId to set
     */
    public void setWasteTypeId(String wasteTypeId) {
        this.wasteTypeId = wasteTypeId;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    /**
     * @return the isDis
     */
    public String getIsDis() {
        return isDis;
    }

    /**
     * @param isDis the isDis to set
     */
    public void setIsDis(String isDis) {
        this.isDis = isDis;
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

    public String getCapacityReleaseId() {
        return capacityReleaseId;
    }

    public void setCapacityReleaseId(String capacityReleaseId) {
        this.capacityReleaseId = capacityReleaseId;
    }

    public String getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(String responseCount) {
        this.responseCount = responseCount;
    }

}
