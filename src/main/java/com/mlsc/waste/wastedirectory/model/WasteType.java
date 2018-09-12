/**
 * 
 */
package com.mlsc.waste.wastedirectory.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

import java.util.List;

/**
 * @author chenhh
 * 危废类别
 */
@Table(TableNameConstants.TABLE_WASTE_TYPE)
public class WasteType extends Entity {

    private static final long serialVersionUID = 1117236828773294523L;
    @Id @Column
    private String id;//主键
    @Column
    private String code;//二位码
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
    
    List<Waste> wasteList = null;
    
    
    
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
     * @return the code
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
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

	public List<Waste> getWasteList() {
		return wasteList;
	}

	public void setWasteList(List<Waste> wasteList) {
		this.wasteList = wasteList;
	}
}