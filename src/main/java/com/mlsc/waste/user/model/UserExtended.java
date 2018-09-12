package com.mlsc.waste.user.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;
import com.mlsc.waste.utils.TableNameConstants;

/**
 * 用户扩展表
 *
 * @author zhugl
 */
@Table(TableNameConstants.TABLE_USER_EXTENDED)
public class UserExtended extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;

    @Id
    @Column("id")
    private String id;  //主键

    @Column("sys_user_id")
    private String sysUserId;  //用户ID

    @Column("email_status")
    private String emailStatus;  //邮箱地址认证状态

    @Column("mobile_status")
    private String mobileStatus;  //手机号码认证状态

    @Column("user_icon")
    private String userIcon;  //申请人图像

    @Column("is_wastecycle_init")
    private String isWastecycleInit;

    @Column("registration_code")
    private String registrationCode;

    @Column("token")
    private String token;

    @Column("valid")
    private String valid;

    @Column("role")
    private String role;

    @Column("user_status")
    private String userStatus;

    @Column("update_time")
    private String updateTime;

    @Column("weixin_open_id")
    private String weXinOpenId;

    @Column("angle")
    private String angle;

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
     * @return the sysUserId
     */
    public String getSysUserId() {
        return sysUserId;
    }

    /**
     * @param sysUserId the sysUserId to set
     */
    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    /**
     * @return the emailStatus
     */
    public String getEmailStatus() {
        return emailStatus;
    }

    /**
     * @param emailStatus the emailStatus to set
     */
    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    /**
     * @return the mobileStatus
     */
    public String getMobileStatus() {
        return mobileStatus;
    }

    /**
     * @param mobileStatus the mobileStatus to set
     */
    public void setMobileStatus(String mobileStatus) {
        this.mobileStatus = mobileStatus;
    }

    /**
     * @return the userIcon
     */
    public String getUserIcon() {
        return userIcon;
    }

    /**
     * @param userIcon the userIcon to set
     */
    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    /**
     * @return the isWastecycleInit
     */
    public String getIsWastecycleInit() {
        return isWastecycleInit;
    }

    /**
     * @param isWastecycleInit the isWastecycleInit to set
     */
    public void setIsWastecycleInit(String isWastecycleInit) {
        this.isWastecycleInit = isWastecycleInit;
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

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWeXinOpenId() {
        return weXinOpenId;
    }

    public void setWeXinOpenId(String weXinOpenId) {
        this.weXinOpenId = weXinOpenId;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }
}
