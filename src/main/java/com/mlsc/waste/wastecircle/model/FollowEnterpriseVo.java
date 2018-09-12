package com.mlsc.waste.wastecircle.model;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.annotation.Column;

import java.util.List;

public class FollowEnterpriseVo extends CooperationRelation {
    
    private static final long serialVersionUID = 5498442899998043322L;

    private String entLogo;// 被关注企业的logo
    
    @Column("entName")
    private String entName;// 被关注企业的名字
    
    @Column("entAddr")
    private String entAddr; // 被关注企业的地址
    
    @Column("isFollow")
    private String isFollow; // 是否被关注
    
    private List<CodeValue> entType; // 被关企业的所属类型（产废企业or处置企业or鉴定机构等）

    /**
     * @return the entLogo
     */
    public String getEntLogo() {
        return entLogo;
    }

    /**
     * @param entLogo the entLogo to set
     */
    public void setEntLogo(String entLogo) {
        this.entLogo = entLogo;
    }

    /**
     * @return the entName
     */
    public String getEntName() {
        return entName;
    }

    /**
     * @param entName the entName to set
     */
    public void setEntName(String entName) {
        this.entName = entName;
    }

    /**
     * @return the entAddr
     */
    public String getEntAddr() {
        return entAddr;
    }

    /**
     * @param entAddr the entAddr to set
     */
    public void setEntAddr(String entAddr) {
        this.entAddr = entAddr;
    }

    /**
     * @return the isFollow
     */
    public String getIsFollow() {
        return isFollow;
    }

    /**
     * @param isFollow the isFollow to set
     */
    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    /**
     * @return the entType
     */
    public List<CodeValue> getEntType() {
        return entType;
    }

    /**
     * @param entType the entType to set
     */
    public void setEntType(List<CodeValue> entType) {
        this.entType = entType;
    }
}
