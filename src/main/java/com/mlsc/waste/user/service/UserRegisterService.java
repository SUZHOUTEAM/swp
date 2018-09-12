/**
 * 
 */
package com.mlsc.waste.user.service;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.UserExtended;

/**
 * @author zhugl
 *
 */
public interface UserRegisterService {
    
    /**
     * 手机号码是否被注册验证
     * ture:未被注册;false:已经被注册
     * @author zhugl date 2016-07-29
     */
    boolean isPhoneNumExist(String phoneNum) throws Exception;
    
    /**
     * 用户注册时向手机发送验证码短信
     * 
     * @author zhugl date 2016-07-21
     */
    void getIdentifyCode(String phoneNum,String smsType) throws Exception;
    
    /**
     * 短信验证码正确性验证
     * 
     * @author zhugl date 2016-07-21
     */
    boolean checkIdentifyCode(String phoneNum, String identifyCode) throws Exception;
    
    /**
     * 用户注册时，用户信息保存
     * 
     * @author zhugl date 2016-07-21
     */
    JSONObject userRegisterSave(RPCSysUser sysUser,String openId) throws Exception;
    
    /**
     * 用户注册时，用户信息保存
     * 
     * @author zhugl date 2016-07-21
     */
    JSONObject userRegisterSaveForClient(RPCSysUser sysUser,String clientMessage,String openId) throws Exception;

    UserExtended saveUserExtended(RPCSysUser sysUser, String ticketId,String openId) throws Exception;
}
