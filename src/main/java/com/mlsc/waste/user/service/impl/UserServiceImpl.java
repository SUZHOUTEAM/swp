package com.mlsc.waste.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.cache.CacheUtil;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.epdp.common.exception.ParamDataException;
import com.mlsc.epdp.common.utils.RSAEncryptUtils;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.api.dto.ReObject;
import com.mlsc.rpc.thrift.api.service.ISysUserService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.user.mapper.UserInfoMapper;
import com.mlsc.yifeiwang.im.service.ImService;
import com.mlsc.sso.utils.HttpRequestUtils;
import com.mlsc.waste.user.dao.SystemUserAuthDao;
import com.mlsc.waste.user.dao.SystemUserDao;
import com.mlsc.waste.user.dao.UserDao;
import com.mlsc.waste.user.dao.UserExtendedDao;
import com.mlsc.waste.user.model.SysUser;
import com.mlsc.waste.user.model.SysUserAuth;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.PropertyUtil;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private IRPCServiceClient client;

    @Autowired
    private UserExtendedDao userExtendDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserExtendedService userExtendedService;

    @Autowired
    private SystemUserDao systemUserDao;

    @Autowired
    private SystemUserAuthDao systemUserAuthDao;

    @Autowired
    private ImService imService;

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    private com.mlsc.epdp.user.service.ISysUserService rpcSysUserService;


    @Override
    public Boolean isPhoneNumExist(String ticketId, String phoneNum) throws Exception {
        boolean checkStatus = false;
        Iface userServiceManager = client.getUserServiceManager();
        ReObject object = userServiceManager.checkPhoneNum(ticketId, phoneNum);
        if (object != null) {
            checkStatus = object.isSuccess();
        }
        return checkStatus;
    }


    @Override
    public boolean checkIdentifyCode(String phoneNum, String identifyCode) throws Exception {
        logger.info("手机号：" + phoneNum + "验证码：" + identifyCode);
        String code = CacheUtil.get(phoneNum).toString();
        logger.info("code:" + code);
        if (StringUtils.isBlank(code)) {
            throw new Exception("验证码过期了");
        } else {
            if (!identifyCode.equals(code)) {
                throw new Exception("验证码错误");
            }
        }

        return true;
    }

    @Override
    public Boolean updateUserName(User user) throws Exception {
        try {
            if (StringUtils.isNotBlank(user.getUserName())) {
                user.setUpdateTime(new Date());
                user.setUpdaterID(user.getUserId());
                userInfoMapper.updateUserInfo(user);
                UserExtended userExtended = userExtendedService.getUserExtendedByUserId(user.getUserId());
                if (StringUtils.isNotBlank(userExtended.getToken())) {
                    imService.updateUserInfo(user.getPhoneNo(), user.getUserName());
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("更改用户名时异常", e);
            throw e;
        }
    }

    @Override
    public String updateUserPassword(String ticketId, String userId, String password) throws Exception {
        boolean updateStatus = false;
        Iface userServiceManager = client.getUserServiceManager();
        updateStatus = userServiceManager.updateUserPassword(ticketId, userId, password).isSuccess();
        String status;
        if (updateStatus) {
            status = Constant.IS_VALID;
        } else {
            status = Constant.IS_NOT_VALID;
        }
        return status;
    }

    @Override
    public Boolean updateUserPasswordByPhoneNum(String ticketId,
                                                String phoneNum, String password) throws Exception {
        boolean updateStatus = false;
        Iface userServiceManager = client.getUserServiceManager();
        updateStatus = userServiceManager.updateUserPasswordByPhoneNum(ticketId, phoneNum, password).isSuccess();
        return updateStatus;
    }

    @Override
    public Boolean isPasswordCorrect(String ticketId, String userId, String password) throws Exception {
        boolean checkStatus = false;
        Iface userServiceManager = client.getUserServiceManager();
        checkStatus = userServiceManager.checkUserPassword(ticketId, userId, password).isSuccess();
        return checkStatus;
    }

    @Override
    public Boolean isPasswordCorrectByPhoneNum(String ticketId,
                                               String phoneNum, String password) throws Exception {
        boolean checkStatus = false;
        Iface userServiceManager = client.getUserServiceManager();
        checkStatus = userServiceManager.checkUserPasswordByPhoneNum(ticketId, phoneNum, password).isSuccess();
        return checkStatus;
    }


    @Override
    public Boolean checkPassword4GF(String ticketId,
                                    String phoneNum, String password) throws Exception {
        boolean checkStatus = false;
        checkStatus = checkUserPasswordByPhoneNum(ticketId, phoneNum, password).isSuccess();
        return checkStatus;
    }


    private ReObject checkUserPasswordByPhoneNum(String ticket, String phoneNum, String password) {
        ReObject reObj = new ReObject();
        reObj.setTicket(ticket);
        try {
            try {
                password = RSAEncryptUtils.decrypt(password);
            } catch (Exception e) {
                throw new ParamDataException("密码格式有误,调用服务前请对密码进行RSA加密！");
            }
            reObj.setSuccess(rpcSysUserService.checkUserPasswordByPhoneNum(ticket, phoneNum, password));
        } catch (ParamDataException e) {
            reObj.setSuccess(false);
            reObj.setMsg(e.getMessage());
        } catch (DaoAccessException e) {
            e.printStackTrace();
            reObj.setSuccess(false);
            reObj.setMsg("数据库操作异常.");
        }
        return reObj;
    }

    @Override
    public String isMailAddressExist(String ticketId, String mailAddress) throws Exception {
        boolean checkStatus = false;
        Iface userServiceManager = client.getUserServiceManager();
        checkStatus = userServiceManager.checkMailAddressExist(ticketId, mailAddress).isSuccess();
        String status;
        if (checkStatus) {
            status = Constant.IS_NOT_VALID;
        } else {
            status = Constant.IS_VALID;
        }

        return status;
    }

    @Override
    public String updateUserMailAddress(String ticketId, String userId, String mailAddress)
            throws Exception {
        boolean updateStatus = false;
        Iface userServiceManager = client.getUserServiceManager();
        updateStatus = userServiceManager.updateUserMailAddress(ticketId, userId, mailAddress).isSuccess();
        String status;
        if (updateStatus) {
            UserExtended userExtended = userExtendedService.getUserExtendedByUserId(userId);
            if (StringUtils.isNotEmpty(userExtended.getId())) {
                userExtended.setEmailStatus(Constant.IS_VALID);
                userExtendedService.updateUserExtended(userExtended);
            } else {
                userExtended.setEmailStatus(Constant.IS_VALID);
                userExtendedService.saveUserExtended(userExtended, ticketId);
            }
            status = Constant.IS_VALID;
        } else {
            status = Constant.IS_NOT_VALID;
        }
        return status;
    }

    @Override
    public void updateUserPhone(String ticketId, String userId, String phoneNo) throws Exception {
        User currentUser = LoginStatusUtils.getUserByTicketId(ticketId);
        updateSystemUser(currentUser, userId, phoneNo);
        updateSystemAuth(currentUser, userId, phoneNo);
        updateUserExtend(ticketId, userId);
    }

    private void updateSystemUser(User currentUser, String userId, String phoneNo) throws Exception {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setPhoneNum(phoneNo);
        sysUser.setLoginName(phoneNo);
        sysUser.setUpdaterID(currentUser.getUserId());
        sysUser.setUpdateTime(Util.datetimeToString(new Date()));
        systemUserDao.updateChange(sysUser);
    }

    private void updateSystemAuth(User currentUser, String userId, String phoneNo) throws Exception {
        SysUserAuth sysUserAuth = new SysUserAuth();
        sysUserAuth.setUserId(userId);
        sysUserAuth.setPhoneNum(phoneNo);
        sysUserAuth.setLoginName(phoneNo);
        sysUserAuth.setUpdaterID(currentUser.getUserId());
        sysUserAuth.setUpdateTime(Util.datetimeToString(new Date()));
        systemUserAuthDao.updateChange(sysUserAuth);
    }

    private void updateUserExtend(String ticketId, String userId) throws Exception {
        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(userId);
        if (StringUtils.isNotEmpty(userExtended.getId())) {
            userExtended.setMobileStatus(Constant.IS_VALID);
            userExtendedService.updateUserExtended(userExtended);
        } else {
            userExtended.setMobileStatus(Constant.IS_VALID);
            userExtendedService.saveUserExtended(userExtended, ticketId);
        }
    }

    @Override
    public Boolean updateUserIMToken(String ticketId, UserExtended userExtended) throws DaoAccessException {
        boolean updateStatus = false;
        try {
            userExtendDao.update(userExtended);
            updateStatus = true;
        } catch (DaoAccessException e) {
            throw e;
        }
        return updateStatus;
    }

    @Override
    public RPCSysUser getUserInfoByUserId(String ticketId, String userId) throws Exception {
        Iface userServiceManager = client.getUserServiceManager();
        RPCSysUser sysUser = userServiceManager.queryUserInfoByUserId(ticketId, userId);
        return sysUser;
    }

    @Override
    public List<User> getUserListByUserId(String ticketId, List<String> userIdList) throws Exception {
        return userDao.getUserListByUserId(userIdList);
    }

    @Override
    public RPCSysUser getUserInfoByPhoneNum(String ticketId, String phoneNum) throws Exception {
        Iface userServiceManager = client.getUserServiceManager();
        RPCSysUser sysUser = userServiceManager.queryUserInfoByPhoneNum(ticketId, phoneNum);
        return sysUser;
    }

    @Override
    public RPCSysUser userRegisterSave(RPCSysUser sysUser) throws Exception {
        Iface userServiceManager = client.getUserServiceManager();
        sysUser = userServiceManager.userRegisterSave(sysUser);
        return sysUser;
    }

    @Override
    public User getUserInfo(User user, RPCSysUser sysUser) {
        user.setUserName(sysUser.getChineseName());
        user.setMailAddress(sysUser.getEmailAddress());
        user.setPhoneNo(sysUser.getPhoneNum());
        user.setPassword(sysUser.getPassword());
        return user;
    }

    @Override
    public UserExtended getUserExtendedInfoByUserId(String userId) throws DaoAccessException {
        UserExtended userExtended = new UserExtended();
        userExtended.setSysUserId(userId);
        userExtended.setValid(Constant.IS_VALID);
        return userExtendDao.getUserExtendedInfoByUserId(userExtended);
    }

    @Override
    public void getMailStatusAndMobileStatus(User user, UserExtended userExtended) {
        user.setScurityDesc(new ArrayList<String>());
        int score = 0;
        if (userExtended.getMobileStatus() != null && userExtended.getMobileStatus().equals(Constant.IS_VALID)) {
            score = 33;
        } else {
            user.getScurityDesc().add("绑定手机，提供安全保障");
        }
        if (userExtended.getEmailStatus() != null && userExtended.getEmailStatus().equals(Constant.IS_VALID)) {
            score = score + 33;
        } else {
            user.getScurityDesc().add("绑定密保邮箱，提供安全保障");
        }
        score = score + checkPassword(user.getPassword(), user);
        user.setScurityScore(score);
    }

    private int checkPassword(String passwordStr, User user) {
        String str1 = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";   //由字母、数字组成，不超过18位
        if (passwordStr.matches(str1)) {
            user.getScurityDesc().add("密码由字母、数字组成强度不够");
            return 20;
        } else {
            return 34;
        }
    }

    @Override
    public List<RPCSysUser> getUserInfoByEntId(String enterpriseId) throws DaoAccessException {
        return userDao.getUserInfoByEntId(enterpriseId);
    }


    @Override
    public String getTicketIdByAccount(String phoneNum, String password, String clientMessage) {
        String ticketId = "";
        Map<String, String> params = new HashMap<String, String>();
        params.put("loginAccount", phoneNum);
        params.put("password", password);
        params.put("clientMessage", clientMessage);
        try {
            String url = PropertyUtil.getValue("ssoDemain") + "userLogin/getTicketIdByloginAccountAndPassword";
            String jsonStr = HttpRequestUtils.httpPost(url, params).toString();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            ticketId = jsonObject.getString("data");
        } catch (Exception e) {
            e.printStackTrace();
            ticketId = "";
        }
        return ticketId;
    }

    @Override
    public RPCSysUser getUserInfoByBusinessReleaseId(String releaseId) throws DaoAccessException {
        return userDao.getUserInfoByBusinessReleaseId(releaseId);
    }

    @Override
    public RPCSysUser getUserInfoByOrderId(String releaseId) {
        RPCSysUser sysUser = null;
        try {
            sysUser = userDao.getUserInfoByOrderId(releaseId);
        } catch (Exception e) {
            logger.error("根据订单获取用户信息时出错", e);
            throw new RuntimeException(e);
        }
        return sysUser;
    }

    @Override
    public List<User> listUser(User param) throws DaoAccessException {
        String whereSql = "";
        Map<String, Object> paramMap = new HashMap<>();
        if (param != null) {
            if (StringUtils.isNotEmpty(param.getUserId())) {
                whereSql += " AND userId = :userId ";
                paramMap.put("userId", param.getUserId());
            }
            if (StringUtils.isNotEmpty(param.getPhoneNo())) {
                whereSql += " AND phoneNo = :phoneNo ";
                paramMap.put("phoneNo", param.getPhoneNo());
            }
            if (StringUtils.isNotEmpty(param.getExtendId())) {
                whereSql += " AND extendId = :extendId ";
                paramMap.put("extendId", param.getExtendId());
            }
            if (param.getUserIdList() != null && param.getUserIdList().size() > 0) {
                whereSql += " AND userId in (:userIdList) ";
                paramMap.put("userIdList", param.getUserIdList());
            }
            if (StringUtils.isNotEmpty(param.getEnterpriseId())) {
                whereSql += " AND enterpriseId = :entId ";
                paramMap.put("entId", param.getEnterpriseId());
            }
            if (StringUtils.isNotEmpty(param.getUserStatus())) {
                whereSql += " AND userStatus = :userStatus ";
                paramMap.put("userStatus", param.getUserStatus());
            }
            if (StringUtils.isNotEmpty(param.getUserIds())) {
                String[] array = param.getUserIds().split(",");
                List<String> userIdList = new ArrayList<>();
                for (String userId : array) {
                    userIdList.add(userId);
                }
                whereSql += " AND userId in (:userIdList) ";
                paramMap.put("userIdList", userIdList);
            }
        }

        List<User> list = userDao.listUser(whereSql, paramMap);
        return list;
    }

    @Override
    public List<User> listValidUserByEnterpriseId(String enterpriseId) throws DaoAccessException {
        User param = new User();
        param.setEnterpriseId(enterpriseId);
        param.setUserStatus(UserStatus.PASS.getStatusCode());
        return listUser(param);
    }

    @Override
    public User getUserByUserId(String userId) throws DaoAccessException {
        User param = new User();
        param.setUserId(userId);
        List<User> userList = listUser(param);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }


}
