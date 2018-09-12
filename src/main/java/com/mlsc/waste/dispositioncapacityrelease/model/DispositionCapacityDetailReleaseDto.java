package com.mlsc.waste.dispositioncapacityrelease.model;

import com.mlsc.epdp.common.annotation.Column;

public class DispositionCapacityDetailReleaseDto extends DispositionCapacityDetailRelease {

    private static final long serialVersionUID = 2821591994970094340L;

    @Column("wasteCode")
    private String wasteCode; // 八位码Code

    @Column("dispositionTypeCode")
    private String dispositionTypeCode; //

    @Column("dispositionTypeValue")
    private String dispositionTypeValue; //

    @Column("unUsedQuantity")
    private String unUsedQuantity; // 该处置方式剩余可处置量

    @Column("releaseStatusCode")
    private String releaseStatusCode; // 发布信息状态Code

    /**
     * @return the wasteCode
     */
    public String getWasteCode() {
        return wasteCode;
    }

    /**
     * @param wasteCode
     *            the wasteCode to set
     */
    public void setWasteCode(String wasteCode) {
        this.wasteCode = wasteCode;
    }

    /**
     * @return the dispositionTypeCode
     */
    public String getDispositionTypeCode() {
        return dispositionTypeCode;
    }

    /**
     * @param dispositionTypeCode
     *            the dispositionTypeCode to set
     */
    public void setDispositionTypeCode(String dispositionTypeCode) {
        this.dispositionTypeCode = dispositionTypeCode;
    }

    /**
     * @return the dispositionTypeValue
     */
    public String getDispositionTypeValue() {
        return dispositionTypeValue;
    }

    /**
     * @param dispositionTypeValue
     *            the dispositionTypeValue to set
     */
    public void setDispositionTypeValue(String dispositionTypeValue) {
        this.dispositionTypeValue = dispositionTypeValue;
    }

    /**
     * @return the unUsedQuantity
     */
    public String getUnUsedQuantity() {
        return unUsedQuantity;
    }

    /**
     * @param unUsedQuantity
     *            the unUsedQuantity to set
     */
    public void setUnUsedQuantity(String unUsedQuantity) {
        this.unUsedQuantity = unUsedQuantity;
    }

    /**
     * @return the releaseStatusCode
     */
    public String getReleaseStatusCode() {
        return releaseStatusCode;
    }

    /**
     * @param releaseStatusCode
     *            the releaseStatusCode to set
     */
    public void setReleaseStatusCode(String releaseStatusCode) {
        this.releaseStatusCode = releaseStatusCode;
    }
}
