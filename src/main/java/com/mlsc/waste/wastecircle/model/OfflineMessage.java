package com.mlsc.waste.wastecircle.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

@Table(TableNameConstants.TABLE_OFFLINE_MESSAGE)
public class OfflineMessage extends Entity {

    private static final long serialVersionUID = 2822291994970094341L;

    @Id
    @Column("id")
    private String id;

    @Column("fromEntId")
    private String fromEntId;

    @Column("fromUserId")
    private String fromUserId;

    @Column("toEntId")
    private String toEntId;

    @Column("context")
    private String context;

    @Column("createBy")
    private String createBy;

    @Column("createTime")
    private String createTime;

    @Column("editBy")
    private String editBy;

    @Column("editTime")
    private String editTime;

    @Column("status")
    private String status; // 标志位

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the fromEntId
     */
    public String getFromEntId() {
        return fromEntId;
    }

    /**
     * @param fromEntId
     *            the fromEntId to set
     */
    public void setFromEntId(String fromEntId) {
        this.fromEntId = fromEntId;
    }

    /**
     * @return the fromUserId
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * @param fromUserId
     *            the fromUserId to set
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * @return the toEntId
     */
    public String getToEntId() {
        return toEntId;
    }

    /**
     * @param toEntId
     *            the toEntId to set
     */
    public void setToEntId(String toEntId) {
        this.toEntId = toEntId;
    }

    /**
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * @param context
     *            the context to set
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * @return the createBy
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     *            the createBy to set
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
     * @param createTime
     *            the createTime to set
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
     * @param editBy
     *            the editBy to set
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
     * @param editTime
     *            the editTime to set
     */
    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
