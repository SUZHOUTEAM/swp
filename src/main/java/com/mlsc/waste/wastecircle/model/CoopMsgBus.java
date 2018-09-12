package com.mlsc.waste.wastecircle.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

@Table(TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS)
public class CoopMsgBus extends Entity {
    private static final long serialVersionUID = 2821591994970094341L;
    
    @Id @Column("id")
    private String id;

    @Column("message_id")
    private String msgId; // 发布消息体的id.

    @Column("business_type_code")
    private String busTypeCode; // 根据业务类型确定是哪张表，和哪个jsp. 委托处理：001 ，转移计划 ：002

    @Column("business_id")
    private String busId;// 具体关联哪个张业务表里id

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getBusTypeCode() {
        return busTypeCode;
    }

    public void setBusTypeCode(String busTypeCode) {
        this.busTypeCode = busTypeCode;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEditBy() {
        return editBy;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public CoopMsgBus(String msgId, String valid) {
        this.msgId = msgId;
        this.valid = valid;
    }

    public CoopMsgBus() {
        super();
    }

    

}
