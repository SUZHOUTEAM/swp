package com.mlsc.waste.wastecircle.model;

import com.mlsc.epdp.common.annotation.Column;

public class CoopMsgVo extends CoopMsg {
    private static final long serialVersionUID = 2821591994970094341L;

    @Column("distance")
    private String distance;//消息检索时需要加一个距离 

    @Column("enterBusId")
    private String enterBusId;

    @Column("processableTotalCount")
    private String processableTotalCount;//可处置危废数量

    @Column("processableCount")
    private String processableCount;//可处置危废数量

    @Column("busCode")
    private String busCode;

    @Column("entName")
    private String entName;

    @Column("licenceId")
    private String licenceId;

    @Column("businessCode")
    private String businessCode;

    @Column("fileId")
    private String fileId;

    @Column("favorited")
    private boolean favorited;

    private String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProcessableCount() {
        return processableCount;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public void setProcessableCount(String processableCount) {
        this.processableCount = processableCount;
    }

    public String getProcessableTotalCount() {
        return processableTotalCount;
    }

    public void setProcessableTotalCount(String processableTotalCount) {
        this.processableTotalCount = processableTotalCount;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEnterBusId() {
        return enterBusId;
    }

    public void setEnterBusId(String enterBusId) {
        this.enterBusId = enterBusId;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getLicenceId() {
        return licenceId;
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

    public void setLicenceId(String licenceId) {
        this.licenceId = licenceId;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
