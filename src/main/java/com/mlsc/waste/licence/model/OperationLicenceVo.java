/**
 * 
 */
package com.mlsc.waste.licence.model;

import com.mlsc.epdp.common.annotation.Column;


/**
 * 经营许可证-基本信息
 * @author zhugl
 *
 */
public class OperationLicenceVo extends OperationLicence {

    private static final long serialVersionUID = 1L;
    
    @Column("validityPeriod")
    private String validityPeriod;//有效期
    
    @Column("licenceStatus")
    private String licenceStatus;//许可证状态
    
    @Column("auditStatus")
    private String auditStatus;//审批状态
    
    @Column("auditStatusCode")
    private String auditStatusCode;//审批状态
    
    @Column("operationMode")
    private String operationMode;//核准经营方式
    
    @Column("comName")
    private String comName;//组织机构名称

    /**
     * @return the validityPeriod
     */
    public String getValidityPeriod() {
        return validityPeriod;
    }

    /**
     * @param validityPeriod the validityPeriod to set
     */
    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    /**
     * @return the licenceStatus
     */
    public String getLicenceStatus() {
        return licenceStatus;
    }

    /**
     * @param licenceStatus the licenceStatus to set
     */
    public void setLicenceStatus(String licenceStatus) {
        this.licenceStatus = licenceStatus;
    }

    /**
     * @return the auditStatus
     */
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * @param auditStatus the auditStatus to set
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * @return the auditStatusCode
     */
    public String getAuditStatusCode() {
        return auditStatusCode;
    }

    /**
     * @param auditStatusCode the auditStatusCode to set
     */
    public void setAuditStatusCode(String auditStatusCode) {
        this.auditStatusCode = auditStatusCode;
    }

    /**
     * @return the operationMode
     */
    public String getOperationMode() {
        return operationMode;
    }

    /**
     * @param operationMode the operationMode to set
     */
    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    /**
     * @return the comName
     */
    public String getComName() {
        return comName;
    }

    /**
     * @param comName the comName to set
     */
    public void setComName(String comName) {
        this.comName = comName;
    }

}
