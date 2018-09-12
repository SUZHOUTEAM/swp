package com.mlsc.waste.dispositioncapacityrelease.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;


@Table(TableNameConstants.TABLE_DISPOSITION_CAPACITYDETAIL_RELEASE)
public class DispositionCapacityDetailRelease  extends Entity{
    
    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id; // 主键

    @Column("capacity_release_id")
    private String capacityReleaseId; //发布信息主表ID
    
    @Column("capacityitem_id")
    private String capacityitemId; //发布信息主表ID
    
    @Column("waste_id")
    private String wasteId; //发布信息主表ID
    
    @Column("waste_name_id")
    private String wasteNameId;  //危废名称id
    
    @Column("used_quantity")
    private String usedQuantity; //已用数量
    
    @Column("release_status")
    private String releaseStatus; //发布状态
    
    @Column("create_by")
    private String createBy; // 创建人
    
    @Column("create_time")
    private String createTime; // 创建时间
    
    @Column("edit_by")
    private String editBy; // 修改人
    
    @Column("edit_time")
    private String editTime; // 修改时间
    
    @Column("valid")
    private String valid; // 是否有效




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
     * @return the capacityReleaseId
     */
    public String getCapacityReleaseId() {
        return capacityReleaseId;
    }

    /**
     * @param capacityReleaseId the capacityReleaseId to set
     */
    public void setCapacityReleaseId(String capacityReleaseId) {
        this.capacityReleaseId = capacityReleaseId;
    }

    /**
     * @return the capacityitemId
     */
    public String getCapacityitemId() {
        return capacityitemId;
    }

    /**
     * @param capacityitemId the capacityitemId to set
     */
    public void setCapacityitemId(String capacityitemId) {
        this.capacityitemId = capacityitemId;
    }

    /**
     * @return the wasteId
     */
    public String getWasteId() {
        return wasteId;
    }

    /**
     * @param wasteId the wasteId to set
     */
    public void setWasteId(String wasteId) {
        this.wasteId = wasteId;
    }

    public String getWasteNameId() {
        return wasteNameId;
    }

    public void setWasteNameId(String wasteNameId) {
        this.wasteNameId = wasteNameId;
    }

    /**
     * @return the usedQuantity
     */
    public String getUsedQuantity() {
        return usedQuantity;
    }

    /**
     * @param usedQuantity the usedQuantity to set
     */
    public void setUsedQuantity(String usedQuantity) {
        this.usedQuantity = usedQuantity;
    }

    /**
     * @return the releaseStatus
     */
    public String getReleaseStatus() {
        return releaseStatus;
    }

    /**
     * @param releaseStatus the releaseStatus to set
     */
    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
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
