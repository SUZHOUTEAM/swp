package com.mlsc.waste.utils;

import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.epdp.common.exception.ParamDataException;
import com.mlsc.epdp.common.utils.EncryptUtils;
import com.mlsc.epdp.common.utils.RSAEncryptUtils;
import com.mlsc.rpc.thrift.api.dto.ReObject;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserCacheService;
import com.mlsc.waste.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户信息的工具类
 *
 * @author dinghq
 */
@Component
public class LoginStatusUtils {

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private UserService userService;

    private static LoginStatusUtils loginStatusUtils;

    @Autowired
    private com.mlsc.epdp.user.service.ISysUserService rpcSysUserService;

    @PostConstruct
    public void init() {
        loginStatusUtils = this;
    }

    private static int USER_INFO_KEEP_DATE = 30 * 60;//用户登录信息保持的时间  以秒为单位


    public static String putUserToCacheByUserPhoneNum(String phoneNum, String ticketId) throws DaoAccessException {
        String result = ticketId;
        User param = new User();
        param.setPhoneNo(phoneNum);
        List<User> userList = loginStatusUtils.userService.listUser(param);
        if (userList != null && userList.size() > 0) {
            User user = userList.get(0);
            if (StringUtils.isEmpty(result)) {
                result = getAndPutTicketByUserPhone(user.getPhoneNo());
            }
            putUserToCache(user, result);
        }
        return result;
    }


    private static void putUserToCache(User user, String ticketId, String startTime, String endTime) throws ParseException {
        if (user != null && StringUtils.isNotBlank(ticketId)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            Date nowDate = new Date();
            if (df.parse(startTime).compareTo(nowDate) <= 0) {
                user.setUserInfoValidStartDate(df.parse(startTime));
            } else {
                user.setUserInfoValidStartDate(nowDate);
            }
            user.setUserInfoValidEndDate(df.parse(endTime));
            loginStatusUtils.userCacheService.putUserByTicket(ticketId, user);
        }
    }

    private static void putUserToCache(User user, String ticketId) {
        if (user != null && StringUtils.isNotBlank(ticketId)) {
            Date nowDate = new Date();
            user.setUserInfoValidStartDate(nowDate);
            user.setUserInfoValidEndDate(new Date(nowDate.getTime() + USER_INFO_KEEP_DATE * 1000));
            loginStatusUtils.userCacheService.putUserByTicket(ticketId, user);
            loginStatusUtils.userCacheService.putTicketByUserPhoneNum(user.getPhoneNo(), ticketId);
        }
    }

    public static List<User> refreshUserListToCacheByUserParam(User param) throws DaoAccessException {
        List<User> userList = loginStatusUtils.userService.listUser(param);
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                String ticketId = getTicketByUserPhoneNum(user.getPhoneNo());
                if (StringUtils.isNotEmpty(ticketId)) {
                    putUserToCache(user, ticketId);
                }
            }
        }
        return userList;
    }

    public static User refreshUserToCacheByUserParam(User param) throws DaoAccessException {
        List<User> userList = loginStatusUtils.userService.listUser(param);
        User user = null;
        if (userList != null && userList.size() > 0) {
            user = userList.get(0);
            String ticketId = getTicketByUserPhoneNum(user.getPhoneNo());
            if (StringUtils.isNotEmpty(ticketId)) {
                putUserToCache(user, ticketId);
            }
        }
        return user;
    }

    public static User refreshUserToCacheByUserParam(User param, String startTime, String endTime)
            throws DaoAccessException, ParseException {
        List<User> userList = loginStatusUtils.userService.listUser(param);
        User user = null;
        if (userList != null && userList.size() > 0) {
            user = userList.get(0);
            String ticketId = getTicketByUserPhoneNum(user.getPhoneNo());
            if (StringUtils.isNotEmpty(ticketId)) {
                putUserToCache(user, ticketId, startTime, endTime);
            }
        }
        return user;
    }


    public static String getTicketByUserPhoneNum(String phoneNum) {
        return loginStatusUtils.userCacheService.getTicketByUserPhoneNum(phoneNum);
    }

    private static String getAndPutTicketByUserPhone(String userPhone) {
        return loginStatusUtils.userCacheService.getAndPutTicketByUserPhoneNum(userPhone);
    }


    public static User getUserByTicketId(String ticketId) {
        User returnUserInfo = null;
        if (StringUtils.isNotBlank(ticketId)) {
            User userInfo = loginStatusUtils.userCacheService.getUserByTicket(ticketId);
            if (userInfo != null) {
                Date nowDate = new Date();
                if (userInfo.getUserInfoValidStartDate().compareTo(nowDate) <= 0 && userInfo.getUserInfoValidEndDate().compareTo(nowDate) >= 0) {
                    putUserToCache(userInfo, ticketId);
                    returnUserInfo = userInfo;
                } else {//无效
                    clearUserByTicketId(ticketId);
                }
            }
        }
        return returnUserInfo;
    }


    public static void clearUserByTicketId(String ticketId) {
        if (StringUtils.isNotBlank(ticketId)) {
            User userInfo = loginStatusUtils.userCacheService.getUserByTicket(ticketId);
            if (userInfo != null) {
                loginStatusUtils.userCacheService.removeUserByTicket(ticketId);
                loginStatusUtils.userCacheService.removeTicketByPhoneNum(userInfo.getPhoneNo());
            }
        }
    }

    public static void clearNoValidUserInfo() {
//        for(Map.Entry<String, User> entry: USER_MAP.entrySet()){
//            User userInfo = entry.getValue();
//            if (userInfo != null) {
//                Date nowDate = new Date();
//                if (userInfo.getUserInfoValidStartDate().compareTo(nowDate) <= 0 && userInfo.getUserInfoValidEndDate().compareTo(nowDate) >= 0) {
//                } else {
//                    clearUserByTicketId(entry.getKey());
//                }
//            }
//        }
    }
}
