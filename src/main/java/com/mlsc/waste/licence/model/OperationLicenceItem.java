/**
 * 
 */
package com.mlsc.waste.licence.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

import java.util.List;

/**
 * 经营许可证-核准经营内容
 * @author sunjl
 *
 */
@Table(TableNameConstants.TABLE_OPERATION_LICENCE_ITEM)
public class OperationLicenceItem extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id;  //主键
   
    @Column("licence_id")
    private String licence_id;  //许可证基本信息主键
    
    @Column("disposition_type")
    private String disposition_type;  //处置方式
    
    @Column("approved_quantity")
    private String approved_quantity;  //许可数量
    
    @Column("excuted_quantity")
    private String excuted_quantity;  //已执行数量
    
    @Column("remark")
    private String remark;  //许可描述
    
    @Column("create_by")
    private String create_by;  //创建人
    
    @Column("create_time")
    private String create_time;  //创建时间
    
    @Column("edit_by")
    private String edit_by;  //修改人
    
    @Column("edit_time")
    private String edit_time;  //修改时间
    
    @Column("valid")
    private String valid;  //是否有效
    
    private List<OperationLicenceDetail> lienceDetails;  //与item表对应的detai详情

    /**
     * @  return the id
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
     * @  return the licence_id
     */
    public String getLicence_id() {
        return licence_id;
    }

    /**
     * @param licence_id the licence_id to set
     */
    public void setLicence_id(String licence_id) {
        this.licence_id = licence_id;
    }

    /**
     * @  return the disposition_type
     */
    public String getDisposition_type() {
        return disposition_type;
    }

    /**
     * @param disposition_type the disposition_type to set
     */
    public void setDisposition_type(String disposition_type) {
        this.disposition_type = disposition_type;
    }

    /**
     * @  return the approved_quantity
     */
    public String getApproved_quantity() {
        return approved_quantity;
    }

    /**
     * @param approved_quantity the approved_quantity to set
     */
    public void setApproved_quantity(String approved_quantity) {
        this.approved_quantity = approved_quantity;
    }

    /**
     * @  return the excuted_quantity
     */
    public String getExcuted_quantity() {
        return excuted_quantity;
    }

    /**
     * @param excuted_quantity the excuted_quantity to set
     */
    public void setExcuted_quantity(String excuted_quantity) {
        this.excuted_quantity = excuted_quantity;
    }

    /**
     * @  return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @  return the create_by
     */
    public String getCreate_by() {
        return create_by;
    }

    /**
     * @param create_by the create_by to set
     */
    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    /**
     * @  return the create_time
     */
    public String getCreate_time() {
        return create_time;
    }

    /**
     * @param create_time the create_time to set
     */
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    /**
     * @  return the edit_by
     */
    public String getEdit_by() {
        return edit_by;
    }

    /**
     * @param edit_by the edit_by to set
     */
    public void setEdit_by(String edit_by) {
        this.edit_by = edit_by;
    }

    /**
     * @  return the edit_time
     */
    public String getEdit_time() {
        return edit_time;
    }

    /**
     * @param edit_time the edit_time to set
     */
    public void setEdit_time(String edit_time) {
        this.edit_time = edit_time;
    }

    /**
     * @  return the valid
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

    /**
     * @return the lienceDetails
     */
    public List<OperationLicenceDetail> getLienceDetails() {
        return lienceDetails;
    }

    /**
     * @param lienceDetails the lienceDetails to set
     */
    public void setLienceDetails(List<OperationLicenceDetail> lienceDetails) {
        this.lienceDetails = lienceDetails;
    }
}
