package com.mlsc.waste.enterprise.model;

import com.mlsc.epdp.common.annotation.Column;

public class WasteEnterpriseTypeBusiness extends WasteEnterpriseType {
    private static final long serialVersionUID = -6149669525938148510L;

    private String enterpriseTypeId; // 企业类别id

    @Column("enterpriseTypeCode")
    private String enterpriseTypeCode; // 企业类别代码

    @Column("enterpriseTypeName")
    private String enterpriseTypeName; // 企业类别名称

    private String enterpriseTypeBusiness; // 企业类别相关业务
    
    private String businessName;// 企业类别相关业务名称

    public String getEnterpriseTypeId() {
        return enterpriseTypeId;
    }

    public void setEnterpriseTypeId(String enterpriseTypeId) {
        this.enterpriseTypeId = enterpriseTypeId;
    }

    public String getEnterpriseTypeCode() {
        return enterpriseTypeCode;
    }

    public void setEnterpriseTypeCode(String enterpriseTypeCode) {
        this.enterpriseTypeCode = enterpriseTypeCode;
    }

    public String getEnterpriseTypeName() {
        return enterpriseTypeName;
    }

    public void setEnterpriseTypeName(String enterpriseTypeName) {
        this.enterpriseTypeName = enterpriseTypeName;
    }

    public String getEnterpriseTypeBusiness() {
        return enterpriseTypeBusiness;
    }

    public void setEnterpriseTypeBusiness(String enterpriseTypeBusiness) {
        this.enterpriseTypeBusiness = enterpriseTypeBusiness;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

}
