/**
 *
 */
package com.mlsc.waste.user.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;

import java.util.Date;
import java.util.List;

/**
 * @author zhugl
 */
@Table("sys_user")
public class User extends Entity {

    private static final long serialVersionUID = 2821591994970094340L;

    //用户id
    @Id
    @Column("UserId")
    private String userId;
    //用户姓名
    private String userName;

    //用户角色（平台管理员，普通用户）
    private int userType;

    //企业名称（经营单位）
    private String enterpriseName;
    //企业id
    private String enterpriseId;

    //法人代表
    private String corporate;

    //注册地址
    private String registerAddr;

    //经营设施地址
    private String machineAddr;

    private String phoneNo;

    private String mailAddress;

    private String password;

    private boolean phoneValid;

    private boolean mailValid;

    private String newPassword;

    private String newPasswordAgain;

    private String userNewName;

    private String userNewMail;

    private String userVerificationCode;

    private String newPhoneNo;

    private int scurityScore;

    private String personalImg;

    private List<String> scurityDesc;

    //缓存信息保存有效开始时间
    private Date userInfoValidStartDate;
    //缓存信息保存有效开始结束时间
    private Date userInfoValidEndDate;

    private String imToken;

    private String userStatus;

    private String userStatusName;

    private String userRole;

    private String userRoleName;

    private String applyTime;

    private String extendId;

    private String userIds;

    private List<String> userIdList;

    private String registrationCode;

    private String entType;

    private String userAngle;

    private double posx;
    private double posy;

    private String cantonCode;
    private String updaterID;
    private Date updateTime;

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public String getNewPhoneNo() {
        return newPhoneNo;
    }

    public void setNewPhoneNo(String newPhoneNo) {
        this.newPhoneNo = newPhoneNo;
    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public String getMachineAddr() {
        return machineAddr;
    }

    public void setMachineAddr(String machineAddr) {
        this.machineAddr = machineAddr;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    /**
     * @ return the userId
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
     * @ return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the userType
     */
    public int getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(int userType) {
        this.userType = userType;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the enterpriseId
     */
    public String getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * @param enterpriseId the enterpriseId to set
     */
    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * @ return the enterpriseName
     */
    public String getEnterpriseName() {
        return enterpriseName;
    }

    /**
     * @param enterpriseName the enterpriseName to set
     */
    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getPhoneValid() {
        return phoneValid;
    }

    public void setPhoneValid(boolean phoneValid) {
        this.phoneValid = phoneValid;
    }

    public boolean getMailValid() {
        return mailValid;
    }

    public void setMailValid(boolean mailValid) {
        this.mailValid = mailValid;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }

    public String getUserNewName() {
        return userNewName;
    }

    public void setUserNewName(String userNewName) {
        this.userNewName = userNewName;
    }

    public String getUserNewMail() {
        return userNewMail;
    }

    public void setUserNewMail(String userNewMail) {
        this.userNewMail = userNewMail;
    }

    public String getUserVerificationCode() {
        return userVerificationCode;
    }

    public void setUserVerificationCode(String userVerificationCode) {
        this.userVerificationCode = userVerificationCode;
    }


    public int getScurityScore() {
        return scurityScore;
    }

    public void setScurityScore(int scurityScore) {
        this.scurityScore = scurityScore;
    }

    public String getPersonalImg() {
        return personalImg;
    }

    public void setPersonalImg(String personalImg) {
        this.personalImg = personalImg;
    }

    public List<String> getScurityDesc() {
        return scurityDesc;
    }

    public void setScurityDesc(List<String> scurityDesc) {
        this.scurityDesc = scurityDesc;
    }

    /**
     * @return the userInfoValidStartDate
     */
    public Date getUserInfoValidStartDate() {
        return userInfoValidStartDate;
    }

    /**
     * @param userInfoValidStartDate the userInfoValidStartDate to set
     */
    public void setUserInfoValidStartDate(Date userInfoValidStartDate) {
        this.userInfoValidStartDate = userInfoValidStartDate;
    }

    /**
     * @return the userInfoValidEndDate
     */
    public Date getUserInfoValidEndDate() {
        return userInfoValidEndDate;
    }

    /**
     * @param userInfoValidEndDate the userInfoValidEndDate to set
     */
    public void setUserInfoValidEndDate(Date userInfoValidEndDate) {
        this.userInfoValidEndDate = userInfoValidEndDate;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserStatusName() {
        return userStatusName;
    }

    public void setUserStatusName(String userStatusName) {
        this.userStatusName = userStatusName;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        if (applyTime != null && applyTime.length() > 19) {
            applyTime = applyTime.substring(0, 19);
        }
        this.applyTime = applyTime;
    }

    public String getExtendId() {
        return extendId;
    }

    public void setExtendId(String extendId) {
        this.extendId = extendId;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public String getUserAngle() {
        return userAngle;
    }

    public void setUserAngle(String userAngle) {
        this.userAngle = userAngle;
    }

    public double getPosx() {
        return posx;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public double getPosy() {
        return posy;
    }

    public void setPosy(double posy) {
        this.posy = posy;
    }

    public String getUpdaterID() {
        return updaterID;
    }

    public void setUpdaterID(String updaterID) {
        this.updaterID = updaterID;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
