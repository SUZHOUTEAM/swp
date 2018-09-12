package com.mlsc.waste.user.service;

import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;

import java.util.List;


public interface UserService {
//    void saveEntinfoToLoginStatusUtils (String ticketId,String enterpriseId) throws  Exception;
//    void addUserInfoToLoginStatusUtilsForvalid (String ticketId, RPCSysUser sysUser,String startTime,String endTime) throws Exception;
    /**
     * 查询手机号码是否已经存在，
     * @param phoneNum
     * @param ticketId 
     * @return true:存在 , false:不存在
     */
    Boolean isPhoneNumExist( String ticketId,String phoneNum) throws Exception; 
    

    /**
     * 校验短信验证码是否正确
     * @param phoneNum
     * @param identifyCode
     * @return errorCode "1"：验证码失效了；"2"：验证码正确；"3"：验证码错误
     */
    boolean checkIdentifyCode(String phoneNum,String identifyCode) throws Exception;
    
    /**
     * 更新用户姓名
     * @param user
     * @return
     * @throws Exception
     */
    Boolean updateUserName(User user) throws Exception;

    /**
     * 更新用户密码
     * 
     * @param userId
     * @param password
     * @return
     * @
     */
    String updateUserPassword(String ticketId,String userId, String password) throws Exception;
    
    /**
     * 通过手机号码更新用户密码
     * 
     * @param ticketId 可以为null
     * @param phoneNum
     * @param password
     * @return
     * @
     */
    Boolean updateUserPasswordByPhoneNum(String ticketId,String phoneNum, String password) throws Exception;
    
    /**
     * 校验用户密码是否正确
     * 
     * @param userId
     * @param password
     * @return
     * @
     */
    Boolean isPasswordCorrect(String ticketId,String userId, String password) throws Exception;
    
    /**
     * 通过手机号码，密码来校验用户密码是否正确（用户登录时）
     * 
     * @param ticketId 可以为null
     * @param phoneNum
     * @param password
     * @return
     * @
     */
    Boolean isPasswordCorrectByPhoneNum(String ticketId,String phoneNum, String password) throws Exception;

    Boolean checkPassword4GF(String ticketId,
                             String phoneNum, String password) throws Exception;

    /**
     * 校验用户邮箱是否被使用
     * 
     * @param mailAddress
     * @return
     * @
     */
    String isMailAddressExist(String ticketId,String mailAddress) throws Exception;

    /**
     * 更新用户邮箱
     * 
     * @param mailAddress
     * @return
     * @
     */
    String updateUserMailAddress(String ticketId,String userId, String mailAddress) throws Exception;


    /**
     * 更新用户电话
     * @param ticketId
     * @param userId
     * @param phoneNo
     * @return
     * @throws Exception
     */
    void updateUserPhone(String ticketId,String userId,String phoneNo) throws Exception;

    /**
     * 更新用户运行token
     * @param ticketId
     * @param userExtended
     * @return
     * @throws DaoAccessException
     */
    Boolean updateUserIMToken(String ticketId, UserExtended userExtended) throws DaoAccessException;

    /**
     * 查询用户信息（用户ID）
     * @param userId
     * @return
     * @
     */
    RPCSysUser getUserInfoByUserId(String ticketId,String userId) throws Exception;

    /**
     * 根据用户Id批量查询用户信息
     * @param ticketId
     * @param userIdList
     * @return
     * @throws Exception
     */
    List<User> getUserListByUserId(String ticketId,List<String> userIdList) throws Exception;

    /**
     * 查询用户信息（用手机号码）
     * @param ticketId 可以为null
     * @param phoneNum
     * @return
     * @
     */
    RPCSysUser getUserInfoByPhoneNum(String ticketId,String phoneNum) throws Exception;
    
    /**
     * 用户注册时的信息保存
     * @param user RPCSysUser
     * @return
     * @
     */
    RPCSysUser userRegisterSave(RPCSysUser user) throws Exception;
    
    /**
     *根据userId获取扩展表信息
     * @param userId
     */
    UserExtended getUserExtendedInfoByUserId(String userId) throws Exception;
    
    /**
     * 计算个人信息安全等级
     * @param user
     * @param userExtended
     * @return
     */
    void getMailStatusAndMobileStatus(User user, UserExtended userExtended) throws Exception;
    
    /**
     * 根据平台系统返回User 对像
     * @param user 
     * @param sysUser
     * @return
     */
    User getUserInfo(User user, RPCSysUser sysUser) throws Exception;
    
    /**
     * 查询指定企业里面所有的的人员信息
     * @param enterpriseId
     * @return
     */
    List<RPCSysUser> getUserInfoByEntId(String enterpriseId) throws Exception;
    

    String getTicketIdByAccount(String phoneNum,String password,String clientMessage) throws Exception;

	RPCSysUser getUserInfoByBusinessReleaseId(String releaseId) throws DaoAccessException;

	RPCSysUser getUserInfoByOrderId(String releaseId) ;

	List<User> listUser(User param) throws DaoAccessException;

	List<User> listValidUserByEnterpriseId(String enterpriseId) throws DaoAccessException;

	User getUserByUserId(String userId) throws DaoAccessException;
}
