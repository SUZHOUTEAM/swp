package com.mlsc.waste.wastecircle.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

@Table(TableNameConstants.TABLE_COOPERATION_COMMENT)
public class CoopComment extends Entity {

    private static final long serialVersionUID = 2821591994970094341L;
    
    @Id @Column("id")
    private String id;

    @Column("comment_type")
    private String commentType; // 评论类型

    @Column("commentator_id")
    private String commentatorId;// 评论者ID

    @Column("enterprise_id")
    private String enterId;// 评论企业ID

    @Column("message_id")
    private String msgId;// 消息ID

    @Column("parent_comment_id")
    private String parentId;// 上级评论ID

    @Column("comment_detail")
    private String commentDetail;// 评论内容

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

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getCommentatorId() {
        return commentatorId;
    }

    public void setCommentatorId(String commentatorId) {
        this.commentatorId = commentatorId;
    }

    public String getEnterId() {
        return enterId;
    }

    public void setEnterId(String enterId) {
        this.enterId = enterId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCommentDetail() {
        return commentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        this.commentDetail = commentDetail;
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

}
