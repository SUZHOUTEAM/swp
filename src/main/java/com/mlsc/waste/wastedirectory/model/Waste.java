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
 * @author sunjl
 * 危废（八位码）
 */
@Table(TableNameConstants.TABLE_WASTE)
public class Waste extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;
    @Id @Column
    private String id;//主键
    @Column
    private String waste_type_id;//危废类别id
    @Column
    private String calling_id;//行业id
    @Column
    private String code;//八位码
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
    @Column
    private String corrosivity;//腐蚀性
    @Column
    private String toxicity;//毒性
    @Column
    private String ignitability;//易燃性
    @Column
    private String reactivity;//反应性
    @Column
    private String infectivity;//感染性
    private String riskCharacteristics;//危险特性
    private String callingCode;//行业码
    private String threeYardsCode;//3位码
    private String wasteTypeCode;//危废类别码
    private String isChecked;//是否选中
    private String name;//危废名称
    private String wasteType;//危废类别
    private String wasteTypeDesc;//危废类别
    private String callingName;//行业名
    private String w_name;//危废名称
    
    /**
     * @return the wasteType
     */
    public String getWasteType() {
        return wasteType;
    }
    /**
     * @param wasteType the wasteType to set
     */
    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }
    
    /**
     * @return the callingName
     */
    public String getCallingName() {
        return callingName;
    }
    
    /**
     * @param callingName the callingName to set
     */
    public void setCallingName(String callingName) {
        this.callingName = callingName;
    }
    
    /**
     * @return the w_name
     */
    public String getW_name() {
        return w_name;
    }
    
    /**
     * @param w_name the w_name to set
     */
    public void setW_name(String w_name) {
        this.w_name = w_name;
    }
    
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
     * @return the waste_type_id
     */
    public String getWaste_type_id() {
        return waste_type_id;
    }
    
    /**
     * @param waste_type_id the waste_type_id to set
     */
    public void setWaste_type_id(String waste_type_id) {
        this.waste_type_id = waste_type_id;
    }
    
    /**
     * @return the calling_id
     */
    public String getCalling_id() {
        return calling_id;
    }
    
    /**
     * @param calling_id the calling_id to set
     */
    public void setCalling_id(String calling_id) {
        this.calling_id = calling_id;
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
    
    /**
     * @return the corrosivity
     */
    public String getCorrosivity() {
        return corrosivity;
    }
    
    /**
     * @param corrosivity the corrosivity to set
     */
    public void setCorrosivity(String corrosivity) {
        this.corrosivity = corrosivity;
    }
    
    /**
     * @return the toxicity
     */
    public String getToxicity() {
        return toxicity;
    }
    
    /**
     * @param toxicity the toxicity to set
     */
    public void setToxicity(String toxicity) {
        this.toxicity = toxicity;
    }
    
    /**
     * @return the ignitability
     */
    public String getIgnitability() {
        return ignitability;
    }
    
    /**
     * @param ignitability the ignitability to set
     */
    public void setIgnitability(String ignitability) {
        this.ignitability = ignitability;
    }
    
    /**
     * @return the reactivity
     */
    public String getReactivity() {
        return reactivity;
    }
    
    /**
     * @param reactivity the reactivity to set
     */
    public void setReactivity(String reactivity) {
        this.reactivity = reactivity;
    }
    
    /**
     * @return the infectivity
     */
    public String getInfectivity() {
        return infectivity;
    }
    
    /**
     * @param infectivity the infectivity to set
     */
    public void setInfectivity(String infectivity) {
        this.infectivity = infectivity;
    }
    
    /**
     * @return the riskCharacteristics
     */
    public String getRiskCharacteristics() {
        return riskCharacteristics;
    }
    
    /**
     * @param riskCharacteristics the riskCharacteristics to set
     */
    public void setRiskCharacteristics(String riskCharacteristics) {
        this.riskCharacteristics = riskCharacteristics;
    }
    
    /**
     * @return the callingCode
     */
    public String getCallingCode() {
        return callingCode;
    }
    
    /**
     * @param callingCode the callingCode to set
     */
    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }
    
    /**
     * @return the threeYardsCode
     */
    public String getThreeYardsCode() {
        return threeYardsCode;
    }
    
    /**
     * @param threeYardsCode the threeYardsCode to set
     */
    public void setThreeYardsCode(String threeYardsCode) {
        this.threeYardsCode = threeYardsCode;
    }
    
    /**
     * @return the wasteTypeCode
     */
    public String getWasteTypeCode() {
        return wasteTypeCode;
    }
    
    /**
     * @param wasteTypeCode the wasteTypeCode to set
     */
    public void setWasteTypeCode(String wasteTypeCode) {
        this.wasteTypeCode = wasteTypeCode;
    }
    
    /**
     * @return the isChecked
     */
    public String getIsChecked() {
        return isChecked;
    }
    
    /**
     * @param isChecked the isChecked to set
     */
    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
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
	public String getWasteTypeDesc() {
		return wasteTypeDesc;
	}
	public void setWasteTypeDesc(String wasteTypeDesc) {
		this.wasteTypeDesc = wasteTypeDesc;
	}
}
