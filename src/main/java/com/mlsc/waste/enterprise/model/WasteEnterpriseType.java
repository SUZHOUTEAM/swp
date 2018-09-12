package com.mlsc.waste.enterprise.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * 企业类型
 * @author sunjl
 */
@Table(TableNameConstants.TABLE_WASTE_ENTERPRISE_TYPE)
public class WasteEnterpriseType extends Entity {
    
    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id;  //主键
   
    @Column("enterprise_id")
    private String enterpriseId;  //企业ID
    
    @Column("enterprise_type_id")
    private String enterpriseTypeId;  //企业类别
    
    @Column("create_by")
    private String createBy;  //创建人
    
    @Column("create_time")
    private String createTime;  //创建时间
    
    @Column("edit_by")
    private String editBy;  //修改人
    
    @Column("edit_time")
    private String editTime;  //修改时间
    
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
     * @return the enterpriseId
     */
    public String getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * @param enterpriseId the enterpriseId to set
     */
    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * @return the enterpriseTypeId
     */
    public String getEnterpriseTypeId() {
        return enterpriseTypeId;
    }

    /**
     * @param enterpriseTypeId the enterpriseTypeId to set
     */
    public void setEnterpriseTypeId(String enterpriseTypeId) {
        this.enterpriseTypeId = enterpriseTypeId;
    }

    /**
     * @return the createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy the createBy to set
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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
     * @return the editBy
     */
    public String getEditBy() {
        return editBy;
    }

    /**
     * @param editBy the editBy to set
     */
    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    /**
     * @return the editTime
     */
    public String getEditTime() {
        return editTime;
    }

    /**
     * @param editTime the editTime to set
     */
    public void setEditTime(String editTime) {
        this.editTime = editTime;
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
