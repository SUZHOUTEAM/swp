package com.mlsc.waste.enterprise.model;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;

import java.util.List;

/**
 * 平台企业基本表的扩展实体
 *
 * @author zhugl
 */
public class RPCSysEnterpriseBaseVo extends RPCSysEnterpriseBase {

    private static final long serialVersionUID = 2821591994970094340L;

    private String userName;

    private String phoneNum;

    private String enterpriseStatus;  //企业状态

    private String enterpriseStatusLabel;  //企业状态Label

    private String createTime;  //企业创建时间

    private String region;  //行政区

    private List<CodeValue> entTypes;  //企业类型

    private String wasteCode;//推荐企业对应的八位码

    private String distance;//距离

    private String entType;//企业类型《直接在一条sql语句查出》

    private String CantonName;//行政区划名称

    private String imgBusinessCode;

    private String imgFileId;

    private String responsibleArea;

    private String responsibleAreaName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * @return the cantonName
     */
    public String getCantonName() {
        return CantonName;
    }

    /**
     * @param cantonName the cantonName to set
     */
    public void setCantonName(String cantonName) {
        CantonName = cantonName;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getWasteCode() {
        return wasteCode;
    }

    public void setWasteCode(String wasteCode) {
        this.wasteCode = wasteCode;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }


    public String getEnterpriseStatus() {
        return enterpriseStatus;
    }

    public void setEnterpriseStatus(String enterpriseStatus) {
        this.enterpriseStatus = enterpriseStatus;
    }

    public String getEnterpriseStatusLabel() {
        return enterpriseStatusLabel;
    }

    public void setEnterpriseStatusLabel(String enterpriseStatusLabel) {
        this.enterpriseStatusLabel = enterpriseStatusLabel;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the createTime
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the entTypes
     */
    public List<CodeValue> getEntTypes() {
        return entTypes;
    }

    /**
     * @param entTypes the entTypes to set
     */
    public void setEntTypes(List<CodeValue> entTypes) {
        this.entTypes = entTypes;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getResponsibleArea() {
        return responsibleArea;
    }

    public void setResponsibleArea(String responsibleArea) {
        this.responsibleArea = responsibleArea;
    }

    public String getResponsibleAreaName() {
        return responsibleAreaName;
    }

    public void setResponsibleAreaName(String responsibleArea) {
        this.responsibleAreaName = responsibleArea;
    }
}
