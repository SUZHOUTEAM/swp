/**
 * 
 */
package com.mlsc.waste.wastedirectory.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * @author chenhh
 * 危废名称
 */
@Table(TableNameConstants.TABLE_WASTE_NAME)
public class WasteName extends Entity {

    private static final long serialVersionUID = 1978233463259886748L;
    
    @Id @Column
    private String id;//主键
    @Column
    private String waste_id;//危废id
    @Column
    private String name;//危废名称
    @Column
    private String description;//描述
    @Column
    private String status;//状态(0未启用、1启用)
    @Column
    private String create_by;//创建人
    @Column
    private String create_time;//创建时间
    @Column
    private String edit_by;//编辑人
    @Column
    private String edit_time;//编辑时间
    
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
     * @return the waste_id
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
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * @return the create_by
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
     * @return the create_time
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
     * @return the edit_by
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
     * @return the edit_time
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
}