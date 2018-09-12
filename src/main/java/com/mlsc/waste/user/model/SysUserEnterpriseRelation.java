package com.mlsc.waste.user.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;

/**
 * Created by zhanghj on 2017/7/4.
 */
@Table("sys_user_enterprise_relation")
public class SysUserEnterpriseRelation extends Entity {

    private static final long serialVersionUID = -6163790552615351878L;

    @Id
    @Column("RelId")
    private String relId;

    @Column("UserId")
    private String userId;

    @Column("EntId")
    private String entId;

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntId() {
        return entId;
    }

    public void setEntId(String entId) {
        this.entId = entId;
    }
}
