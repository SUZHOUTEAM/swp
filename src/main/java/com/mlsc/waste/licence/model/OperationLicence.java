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
 * 经营许可证-基本信息
 * @author sunjl
 *
 */
@Table(TableNameConstants.TABLE_OPERATION_LICENCE)
public class OperationLicence extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;
    
    @Id @Column("id")
    private String id;  //主键
   
    @Column("enterprise_id")
    private String enterprise_id;  //企业主键

    @Column("enterprise_name")
    private String enterpriseName;  //企业主键
 
    @Column("licence_no")
    private String licence_no;  //许可证号
    
    @Column("licence_org")
    private String licence_org;  //发证机关
    
    @Column("licence_date")
    private String licence_date;  //发证日期
    
    @Column("initiallic_date")
    private String initiallic_date;  //初次发证日期
    
    @Column("corporate")
    private String corporate;  //法定代表人
    
    @Column("register_addr")
    private String register_addr;  //注册地址
    
    @Column("machine_addr")
    private String machine_addr;  //经营设施地址
    
    @Column("start_date")
    private String start_date;  //有效期-起始日期
    
    @Column("end_date")
    private String end_date;  //有效期-结束日期
    
    @Column("licence_status")
    private String licence_status;  //许可证状态
    
    @Column("operation_mode")
    private String operation_mode;  //核准经营方式
    
    @Column("application_time")
    private String application_time;  //申请发证日期
    
    @Column("audit_status")
    private String audit_status;  //许可证审核状态
    
    @Column("approved_by")
    private String approved_by;  //许可证审核人
    
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
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }
    
    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getLicence_no() {
        return licence_no;
    }

    public void setLicence_no(String licence_no) {
        this.licence_no = licence_no;
    }

    public String getLicence_org() {
        return licence_org;
    }

    public void setLicence_org(String licence_org) {
        this.licence_org = licence_org;
    }

    public String getLicence_date() {
        return licence_date;
    }

    public void setLicence_date(String licence_date) {
        this.licence_date = licence_date;
    }

    public String getInitiallic_date() {
        return initiallic_date;
    }

    public void setInitiallic_date(String initiallic_date) {
        this.initiallic_date = initiallic_date;
    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public String getRegister_addr() {
        return register_addr;
    }

    public void setRegister_addr(String register_addr) {
        this.register_addr = register_addr;
    }

    public String getMachine_addr() {
        return machine_addr;
    }

    public void setMachine_addr(String machine_addr) {
        this.machine_addr = machine_addr;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getLicence_status() {
        return licence_status;
    }

    public void setLicence_status(String licence_status) {
        this.licence_status = licence_status;
    }

    public String getOperation_mode() {
        return operation_mode;
    }

    public void setOperation_mode(String operation_mode) {
        this.operation_mode = operation_mode;
    }

    public String getApplication_time() {
        return application_time;
    }

    public void setApplication_time(String application_time) {
        this.application_time = application_time;
    }

    public String getAudit_status() {
        return audit_status;
    }

    public void setAudit_status(String audit_status) {
        this.audit_status = audit_status;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(String approved_by) {
        this.approved_by = approved_by;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getEdit_by() {
        return edit_by;
    }

    public void setEdit_by(String edit_by) {
        this.edit_by = edit_by;
    }

    public String getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(String edit_time) {
        this.edit_time = edit_time;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getEnterprise_name() {
        return enterpriseName;
    }

    public void setEnterprise_name(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    
}
