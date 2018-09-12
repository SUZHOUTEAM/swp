/**
 * 
 */
package com.mlsc.waste.licence.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * 经营许可证—核准经营内容详细信息
 * @author sunjl
 *
 */
@Table(TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL)
public class OperationLicenceDetail extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id;  //主键
   
    @Column("licence_id")
    private String licence_id;  //许可证基本信息主键
    
    @Column("operation_item_id")
    private String operation_item_id;  //许可证核准经营内容主键
    
    @Column("waste_type")
    private String waste_type;  //二位码
    
    @Column("waste_id")
    private String waste_id;  //八位码
    
    @Column("waste_name_id")
    private String waste_name_id;  //危废名称

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
     * @  return the operation_item_id
     */
    public String getOperation_item_id() {
        return operation_item_id;
    }

    /**
     * @param operation_item_id the operation_item_id to set
     */
    public void setOperation_item_id(String operation_item_id) {
        this.operation_item_id = operation_item_id;
    }

    /**
     * @  return the waste_type
     */
    public String getWaste_type() {
        return waste_type;
    }

    /**
     * @param waste_type the waste_type to set
     */
    public void setWaste_type(String waste_type) {
        this.waste_type = waste_type;
    }

    /**
     * @  return the waste_id
     */
    public String getWaste_id() {
        return waste_id;
    }

    /**
     * @param waste_id the waste_id to set
     */
    public void setWaste_id(String waste_id) {
        this.waste_id = waste_id;
    }
    
    /**
     * @return the waste_name_id
     */
    public String getWaste_name_id() {
        return waste_name_id;
    }

    /**
     * @param waste_name_id the waste_name_id to set
     */
    public void setWaste_name_id(String waste_name_id) {
        this.waste_name_id = waste_name_id;
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
}
