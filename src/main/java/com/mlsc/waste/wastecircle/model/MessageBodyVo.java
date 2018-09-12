package com.mlsc.waste.wastecircle.model;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityReleaseVo;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;

import java.util.List;

public class MessageBodyVo {
    private String msgId;
    private String enterId; //企业id
    private String enterName;//企业名称
    private String busCode; //业务类型Code
    private String busType; //业务类型
    private String area;//企业地域
    private String messageDetail;//发布信息
    private String enterBus;//企业业务名称
    private String enterBusUrl;//企业业务url
    private String publisherId; //发布消息人id
    private String publisherName;
    private String publisherInfo;//发布人的名字及发布时间
    private String enterBusId; //企业业务id
    private int totalAmount; //总共数量
    private String processDate;//处理时期
    private String enterBusSummary;//业务单据概述
    private String distance;//计算距离
    private List<String> enterBusDetail;//业务单据描述
    private boolean isMine; //是否当前用户自己发布
    private int responseCount;//回复条数
    private String licenceNo; //许可证编号
    private String processableCount;//可处置危废数量
    private String licenceAbledDate;//许可证时间
    private String region;
    private String enterImgSrc;
    private String imgBusinessCode;
    private String imgFileId;
    private CodeValue enterType;
    private String enterTypeCode;
    private String enterTypeName;
    private String createTime;
    private boolean favorited;
    private boolean weight;
    private List<DispositionCapacityReleaseVo> dispositionCapacityList;
    private List<EntWasteModel> entWasteModels;
    private int matchCount; //地区模糊匹配


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public void setEnterId(String enterId) {
        this.enterId = enterId;
    }

    public String getEnterId() {
        return enterId;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getEnterName() {
        return enterName;
    }

    public void setEnterName(String enterName) {
        this.enterName = enterName;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public String getEnterBus() {
        return enterBus;
    }

    public void setEnterBus(String enterBus) {
        this.enterBus = enterBus;
    }

    public String getEnterBusUrl() {
        return enterBusUrl;
    }

    public void setEnterBusUrl(String enterBusUrl) {
        this.enterBusUrl = enterBusUrl;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherInfo() {
        return publisherInfo;
    }

    public void setPublisherInfo(String publisherInfo) {
        this.publisherInfo = publisherInfo;
    }

    public String getEnterBusId() {
        return enterBusId;
    }

    public void setEnterBusId(String enterBusId) {
        this.enterBusId = enterBusId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public String getEnterBusSummary() {
        return enterBusSummary;
    }

    public void setEnterBusSummary(String enterBusSummary) {
        this.enterBusSummary = enterBusSummary;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<String> getEnterBusDetail() {
        return enterBusDetail;
    }

    public void setEnterBusDetail(List<String> enterBusDetail) {
        this.enterBusDetail = enterBusDetail;
    }

    public boolean getIsMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

    public int getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(int responseCount) {
        this.responseCount = responseCount;
    }

    public String getEnterImgSrc() {
        return enterImgSrc;
    }

    public void setEnterImgSrc(String enterImgSrc) {
        this.enterImgSrc = enterImgSrc;
    }

    public CodeValue getEnterType() {
        return enterType;
    }

    public void setEnterType(CodeValue enterType) {
        this.enterType = enterType;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getProcessableCount() {
        return processableCount;
    }

    public void setProcessableCount(String processableCount) {
        this.processableCount = processableCount;
    }

    public String getLicenceAbledDate() {
        return licenceAbledDate;
    }

    public void setLicenceAbledDate(String licenceAbledDate) {
        this.licenceAbledDate = licenceAbledDate;
    }

    public String getImgBusinessCode() {
        return imgBusinessCode;
    }

    public void setImgBusinessCode(String imgBusinessCode) {
        this.imgBusinessCode = imgBusinessCode;
    }

    public String getImgFileId() {
        return imgFileId;
    }

    public void setImgFileId(String imgFileId) {
        this.imgFileId = imgFileId;
    }

    public List<DispositionCapacityReleaseVo> getDispositionCapacityList() {
        return dispositionCapacityList;
    }

    public void setDispositionCapacityList(
            List<DispositionCapacityReleaseVo> dispositionCapacityList) {
        this.dispositionCapacityList = dispositionCapacityList;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEnterTypeCode() {
        return enterTypeCode;
    }

    public void setEnterTypeCode(String enterTypeCode) {
        this.enterTypeCode = enterTypeCode;
    }

    public String getEnterTypeName() {
        return enterTypeName;
    }

    public void setEnterTypeName(String enterTypeName) {
        this.enterTypeName = enterTypeName;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isWeight() {
        return weight;
    }

    public void setWeight(boolean weight) {
        this.weight = weight;
    }

    public List<EntWasteModel> getEntWasteModels() {
        return entWasteModels;
    }

    public void setEntWasteModels(List<EntWasteModel> entWasteModels) {
        this.entWasteModels = entWasteModels;
    }
}
