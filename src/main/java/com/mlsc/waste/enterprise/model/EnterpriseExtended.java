package com.mlsc.waste.enterprise.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * 企业扩展表
 * @author zhugl
 */
@Table(TableNameConstants.TABLE_ENTERPRISE_EXTENDED)
public class EnterpriseExtended extends Entity {
    
    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id;  //主键
   
    @Column("sys_enterprise_base_id")
    private String sysEnterpriseBaseId;  //企业ID
    
    @Column("enterprise_status")
    private String enterpriseStatus;  //企业状态：待审核，审核通过，审核退回,已撤销申请
    
    @Column("enterprise_icon")
    private String enterpriseIcon;  //企业图标
    
    @Column("valid")
    private String valid;  //是否有效
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the sysEnterpriseBaseId
     */
    public String getSysEnterpriseBaseId() {
        return sysEnterpriseBaseId;
    }

    /**
     * @param sysEnterpriseBaseId the sysEnterpriseBaseId to set
     */
    public void setSysEnterpriseBaseId(String sysEnterpriseBaseId) {
        this.sysEnterpriseBaseId = sysEnterpriseBaseId;
    }

    /**
     * @return the enterpriseStatus
     */
    public String getEnterpriseStatus() {
        return enterpriseStatus;
    }

    /**
     * @param enterpriseStatus the enterpriseStatus to set
     */
    public void setEnterpriseStatus(String enterpriseStatus) {
        this.enterpriseStatus = enterpriseStatus;
    }

    /**
     * @return the enterpriseIcon
     */
    public String getEnterpriseIcon() {
        return enterpriseIcon;
    }

    /**
     * @param enterpriseIcon the enterpriseIcon to set
     */
    public void setEnterpriseIcon(String enterpriseIcon) {
        this.enterpriseIcon = enterpriseIcon;
    }

    /**
     * @return the valid
     */
    public String getValid() {
        return valid;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(String valid) {
        this.valid = valid;
    }
    
}
