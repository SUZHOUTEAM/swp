package com.mlsc.waste.user.service.impl;

import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.yifeiwang.sms.model.MessageBean;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhugl
 */
@Service("UserLoginService")
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private IRPCServiceClient client;
    @Autowired
    private UserService userService;

    @Autowired
    private UserExtendedService userExtendedService;

    @Override
    public boolean updateUserPasswordByPhoneNum(String phoneNum, String password) throws Exception {
        boolean result = false;//用户找回密码
        result = userService.updateUserPasswordByPhoneNum(null, phoneNum, password);
        return result;
    }

    @Override
    public String userLogin(String phoneNum, String password) throws Exception {
        String ticketId = "";
        // 验证手机号码是否存在
        boolean isPhoneNumExist = userService.isPhoneNumExist(null, phoneNum);

        if (isPhoneNumExist) {
            boolean isLoginSuccess = userService.isPasswordCorrectByPhoneNum(null, phoneNum, password);
            if (isLoginSuccess) {
                ticketId = LoginStatusUtils.putUserToCacheByUserPhoneNum(phoneNum, ticketId);
            } else {// 验证用户名，密码时发生异常，请稍后再试
                throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "用户名或密码不正确,请重新输入。"));
            }
        } else {
            throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "手机号码不存在，请重新输入,或者先注册一个账号。"));
        }
        return ticketId;
    }

    @Override
    public String userLogin(String phoneNum, String password, String resource) throws Exception {
        String ticketId = "";
        // 验证手机号码是否存在
        boolean isPhoneNumExist = userService.isPhoneNumExist(null, phoneNum);

        if (isPhoneNumExist) {
            boolean isLoginSuccess = false;
            if (StringUtils.isNotBlank(resource)) {
                isLoginSuccess = userService.checkPassword4GF(null, phoneNum, password);
            } else {
                isLoginSuccess = userService.isPasswordCorrectByPhoneNum(null, phoneNum, password);
            }

            if (isLoginSuccess) {
                ticketId = LoginStatusUtils.putUserToCacheByUserPhoneNum(phoneNum, ticketId);
            } else {// 验证用户名，密码时发生异常，请稍后再试
                throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "用户名或密码不正确,请重新输入。"));
            }
        } else {
            throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "手机号码不存在，请重新输入,或者先注册一个账号。"));
        }
        return ticketId;
    }


    @Override
    public String userLoginForClient(String phoneNum, String password, String clientMessage) throws Exception {
        String ticketId = "";
        // 验证手机号码是否存在
        boolean isPhoneNumExist = userService.isPhoneNumExist(null, phoneNum);
        if (isPhoneNumExist) {
            boolean isLoginSuccess = userService.isPasswordCorrectByPhoneNum(null, phoneNum, password);
            if (isLoginSuccess) {
                ticketId = userService.getTicketIdByAccount(phoneNum, password, clientMessage);//单点服务器产生ticketId
                // 用户信息取得
                RPCSysUser sysUser = userService.getUserInfoByPhoneNum(ticketId, phoneNum);
                UserExtended userExtend = userExtendedService.getUserExtendedByUserId(sysUser.getUserId());
                LoginStatusUtils.putUserToCacheByUserPhoneNum(phoneNum, ticketId);
            } else {// 验证用户名，密码时发生异常，请稍后再试
                throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "用户名或密码不正确,请重新输入。"));
            }
        } else {
            throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "手机号码不存在，请重新输入,或者先注册一个账号。"));
        }
        return ticketId;
    }

    @Override
    public void updateUserExtended(String userId, String openId) {
        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(userId);
        userExtended.setWeXinOpenId(openId);
        userExtendedService.updateUserExtended(userExtended);
    }
}
