package com.mlsc.waste.dispositioncapacityrelease.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * 
 * @author WangWang 处置能力发布信息
 *
 */
@Table(TableNameConstants.TABLE_DISPOSITION_CAPACITY_RELEASE)
public class DispositionCapacityRelease extends Entity{

    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id; // 主键

    @Column("release_enterprise_id")
    private String releaseEnterpriseId; // 发布处置能力的企业ID
    
    @Column("capacity_release_code")
    private String capacityReleaseCode; // 处置能力发布编号
    
    @Column("release_startdate")
    private String releaseStartdate; // 发布信息有效日期-起
    
    @Column("release_enddate")
    private String releaseEnddate; // 发布信息有效日期-止
    
    @Column("operation_licence_id")
    private String operationLicenceId; // 许可证主表ID
    
    @Column("remark")
    private String remark; // 说明
    
    @Column("versioncode")
    private String versionCode; // 更新时间戳
    
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
    
    @Column("release_status")
    private String releaseStatus; //发布状态

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
     * @return the releaseEnterpriseId
     */
    public String getReleaseEnterpriseId() {
        return releaseEnterpriseId;
    }

    /**
     * @param releaseEnterpriseId the releaseEnterpriseId to set
     */
    public void setReleaseEnterpriseId(String releaseEnterpriseId) {
        this.releaseEnterpriseId = releaseEnterpriseId;
    }

    /**
     * @return the capacityReleaseCode
     */
    public String getCapacityReleaseCode() {
        return capacityReleaseCode;
    }

    /**
     * @param capacityReleaseCode the capacityReleaseCode to set
     */
    public void setCapacityReleaseCode(String capacityReleaseCode) {
        this.capacityReleaseCode = capacityReleaseCode;
    }

    /**
     * @return the releaseStartdate
     */
    public String getReleaseStartdate() {
        return releaseStartdate;
    }

    /**
     * @param releaseStartdate the releaseStartdate to set
     */
    public void setReleaseStartdate(String releaseStartdate) {
        this.releaseStartdate = releaseStartdate;
    }

    /**
     * @return the releaseEnddate
     */
    public String getReleaseEnddate() {
        return releaseEnddate;
    }

    /**
     * @param releaseEnddate the releaseEnddate to set
     */
    public void setReleaseEnddate(String releaseEnddate) {
        this.releaseEnddate = releaseEnddate;
    }

    /**
     * @return the operationLicenceId
     */
    public String getOperationLicenceId() {
        return operationLicenceId;
    }

    /**
     * @param operationLicenceId the operationLicenceId to set
     */
    public void setOperationLicenceId(String operationLicenceId) {
        this.operationLicenceId = operationLicenceId;
    }

    /**
     * @return the remark
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
     * @return the versionCode
     */
    public String getVersionCode() {
        return versionCode;
    }

    /**
     * @param versionCode the versionCode to set
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
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

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
}
