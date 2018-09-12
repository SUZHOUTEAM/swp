package com.mlsc.waste.wastecircle.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

@Table(TableNameConstants.TABLE_COOPERATION_RELATION)
public class CooperationRelation extends Entity {
    
    private static final long serialVersionUID = 5498442899998043322L;

    @Id @Column("id")
    private String id;
    
    @Column("user_id")
    private String userId; // 用户ID
    
    @Column("follow_id")
    private String followId; // 被关系的人或企业Id
    
    @Column("follow_type")
    private String followType; // 关系类型
    
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
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the followId
     */
    public String getFollowId() {
        return followId;
    }

    /**
     * @param followId the followId to set
     */
    public void setFollowId(String followId) {
        this.followId = followId;
    }

    /**
     * @return the followType
     */
    public String getFollowType() {
        return followType;
    }

    /**
     * @param followType the followType to set
     */
    public void setFollowType(String followType) {
        this.followType = followType;
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
