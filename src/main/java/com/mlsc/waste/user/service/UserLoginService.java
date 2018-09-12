/**
 *
 */
package com.mlsc.waste.user.service;


/**
 * @author zhugl
 */
public interface UserLoginService {

    /**
     * 根据手机号码来更新用户密码
     *
     * @author zhugl date 2016-08-05
     */
    boolean updateUserPasswordByPhoneNum(String phoneNum, String password) throws Exception;

    /**
     * 用户登录
     *
     * @author zhugl date 2016-07-21
     */
    String userLogin(String phoneNum, String password) throws Exception;

    String userLogin(String phoneNum, String password, String resource) throws Exception;

    String userLoginForClient(String phoneNum, String password, String clientMessage) throws Exception;

    void updateUserExtended(String userId, String openId);
}
