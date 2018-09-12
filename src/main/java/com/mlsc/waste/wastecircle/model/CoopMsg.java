package com.mlsc.waste.wastecircle.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

@Table(TableNameConstants.TABLE_COOPERATION_MESSAGE)
public class CoopMsg extends Entity {
    private static final long serialVersionUID = 2821591994970094341L;

    public CoopMsg(String enterId, String valid) {
        super();
        this.enterId = enterId;
        this.valid = valid;
    }
    
    @Id @Column("id")
    private String id;
    
    @Column("publisher_id")
    private String publisherId; // 发布者id
    
    @Column("enterprise_Id")
    private String enterId; // 所属企业id
    
    @Column("message_detail")
    private String messageDetail; // 消息正文
    
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
    
    private String businessTypeCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getEnterId() {
        return enterId;
    }

    public void setEnterId(String enterId) {
        this.enterId = enterId;
    }

    public String getMessageDetail() {
        return messageDetail;
    }

    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
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

    public CoopMsg() {
        
    }

    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }
}
