package com.mlsc.waste.myenterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mlsc.common.exception.BusinessException;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.sso.utils.HttpRequestUtils;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.enterprise.service.EnterpriseExtendedService;
import com.mlsc.waste.fileupload.model.Uploadfile;
import com.mlsc.waste.fileupload.service.UploadfileService;
import com.mlsc.waste.management.dao.SysUserEnterpriseRelationDao;
import com.mlsc.waste.myenterprise.service.MyEnterpriseService;
import com.mlsc.waste.user.dao.UserExtendedDao;
import com.mlsc.waste.user.model.SysUserEnterpriseRelation;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecord;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.service.UserEnterpriseApproveRecordService;
import com.mlsc.waste.utils.*;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhugl
 */
@Service("MyEnterpriseService")
public class MyEnterpriseServiceImpl implements MyEnterpriseService {
    private final static Logger logger = LoggerFactory.getLogger(MyEnterpriseServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserEnterpriseApproveRecordService recordService;

    @Autowired
    private EnterpriseExtendedService enterpriseExtendedService;

    @Autowired
    private ICodeValueService codeValueService;

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private UserExtendedService userExtendedService;

    @Autowired
    private PlatformSupporter platformSupporter; // 拿到平台表的client

    @Autowired
    private IRPCServiceClient client;

    @Autowired
    private UserExtendedDao userExtendedDao;

    @Autowired
    private SysUserEnterpriseRelationDao userEnterpriseRelationDao;

    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Autowired
    private IUserInfoService userInfoService;


    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;

    @Override
    public boolean isBindedEnterprise(String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        RPCSysEnterpriseBase enterpriseBase = platformSupporter.getOrgComServiceManager()
                .queryEnterpriseInfo(ticketId, user.getUserId());
        return !(enterpriseBase == null || StringUtils.isBlank(enterpriseBase.getEntId()));
    }

    @Override
    public List<UserEnterpriseApproveRecordVo> getUserEnterpriseApproveRecordVos(String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        return recordService.getUserEnterpriseApproveRecordVos(user.getUserId(), null, null, null);
    }



    /**
     * 获取企业图像
     */
    @Override
    public String getEnterImgSrc(String enterpriseId) {
        String enterImgSrc = null;
        try {
            Uploadfile uploadFile = uploadfileService.getFileByBusinessCode("a" + enterpriseId);
            if (uploadFile != null && StringUtils.isNotBlank(uploadFile.getBusinessCode()) && StringUtils
                    .isNotBlank(uploadFile.getFileId())) {
                enterImgSrc = Constant.IMG_SERVICE_URL + "&businessCode=" + uploadFile.getBusinessCode() + "&fileID="
                        + uploadFile.getFileId();
            }
        } catch (Exception e) {
            logger.error("获取企业图像时异常", e);
            throw new RuntimeException(e);
        }
        return enterImgSrc;
    }

    /**
     * 加入企业
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void joinEnterprise(String ticketId, String invitationCode, String enterpriseId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);

        try {
            SysEnterpriseBase sysEnterpriseBase = enterpriseBaseService.getEnterpriseInfoById(enterpriseId);
            updateUserExtended(user,sysEnterpriseBase);
            insertUserEnterpriseRelation(user,enterpriseId);

            List<User> sysAdminUserList = userInfoService.listSysAdminUser(UserRole.ADMIN.getRoleCode());
            informSysAdminUser(sysAdminUserList, user, sysEnterpriseBase);
        } catch (Exception e) {
            logger.error("加入企业时异常", e);
            throw e;
        }


    }

    private void insertUserEnterpriseRelation(User user, String enterpriseId) throws Exception {
        try{
            SysUserEnterpriseRelation userEnterpriseRelation = userEnterpriseRelationDao
                    .getSysUserEnterpriseRelationByUserId(user.getUserId());
            if (userEnterpriseRelation != null) {
                throw new BusinessException("你已经绑定了企业，若需要重新绑定请先退出当前绑定企业");
            }
            userEnterpriseRelation = new SysUserEnterpriseRelation();
            userEnterpriseRelation.setUserId(user.getUserId());
            userEnterpriseRelation.setEntId(enterpriseId);
            userEnterpriseRelationDao.save(userEnterpriseRelation, Util.uuid32());
        }catch(Exception e){
            logger.error("保存用户企业关系时异常", e);
            throw e;
        }

    }

    private void updateUserExtended(User user, SysEnterpriseBase sysEnterpriseBase) throws Exception {
        try{
            UserExtended userExtend = userExtendedService.getUserExtendedByUserId(user.getUserId());
            if (sysEnterpriseBase.getEntType().equalsIgnoreCase(CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION)) {
                userExtend.setUserStatus(UserStatus.PASS.getStatusCode());
            } else {
                userExtend.setUserStatus(UserStatus.SUBMIT.getStatusCode());
            }
            userExtend.setRole(UserRole.REGULAR.getRoleCode());
            userExtend.setUpdateTime(Util.datetimeToString(new Date()));
            userExtendedService.updateUserExtended(userExtend);
        }catch(Exception e){
            logger.error("保存用户扩展时异常", e);
            throw e;
        }


    }

    private void informSysAdminUser(List<User> sysAdminUserList, User user, SysEnterpriseBase sysEnterpriseBase) throws Exception {
        if (sysAdminUserList != null && sysAdminUserList.size() > 0) {
            User sysAdmin = new User();
            sysAdmin.setUserId(Constant.SYS_ADMIN);
            MsgEvent msgEvent = new MsgEvent(SmsAction.NEW_USER);
            msgEvent.setSendUser(sysAdmin);
            Map<String, String> placeholderValueMap = new HashMap<String, String>();
            placeholderValueMap.put("entName", sysEnterpriseBase.getEntName());
            placeholderValueMap.put("userName", user.getUserName());
            placeholderValueMap.put("phoneNo", user.getPhoneNo());
            msgEvent.setPlaceholderValueMap(placeholderValueMap);
            msgEvent.setSendUser(sysAdmin);
            msgEvent.setReceiveUserList(sysAdminUserList);
            sysMsgServcie.sendMessageSync(msgEvent);
        }

    }



    /**
     * 退出企业后，更新审核记录表记录
     */
    private UserEnterpriseApproveRecord updateUserEnterpriseApproveRecord(String ticketId, String useId,
                                                                          String recordid, String eventStatusId) {
        UserEnterpriseApproveRecord approveRecord = null;
        try {
            approveRecord = recordService.getUserEnterpriseApproveRecordById(recordid);
            approveRecord.setEventStatus(eventStatusId);
            approveRecord.setApprovedBy(useId);
            approveRecord.setApprovedTime(Util.datetimeToString(new Date()));
            recordService.updateUserEnterpriseApproveRecord(approveRecord, ticketId);
        } catch (Exception e) {
            logger.error("用户/企业关系审核记录更新时异常", e);
            throw new RuntimeException(e);
        }

        return approveRecord;
    }


    /**
     * 查询用户企业申请记录
     */
    @Override
    public List<AuditUserRecordVo> getUserEnterpriseApproveRecords(String userEventType, String userEventStatus,
                                                                   String enterpriseId) {
        List<AuditUserRecordVo> recordList = null;
        try {
            recordList = recordService.getUserEnterpriseApproveRecords(userEventType, userEventStatus, enterpriseId);
        } catch (Exception e) {
            logger.error("查询申请记录失败", e);
            throw new RuntimeException(e);
        }
        return recordList;
    }




    /**
     * 获取平台企业基本信息
     */
    @Override
    public RPCSysEnterpriseBaseVo getSysEnterpriseBasesByEntId(String ticketId, String enterpriseId) {
        RPCSysEnterpriseBaseVo enterpriseBaseVo = null;
        try {
            enterpriseBaseVo = enterpriseBaseService.getEnterpriseByEntId(enterpriseId);
            if (enterpriseBaseVo != null) {
                enterpriseBaseVo.setEnterpriseStatusLabel(getEventStatus(enterpriseBaseVo.getEnterpriseStatus(),
                        enterpriseBaseVo.getEnterpriseStatusLabel()));
            }
        } catch (Exception e) {
            logger.error("获取平台企业基本信息失败", e);
            throw new RuntimeException(e);
        }

        return enterpriseBaseVo;
    }

    /**
     * 对企业进行标注是否通过平台验证
     */
    private String getEventStatus(String eventStatusCode, String eventStatusLabel) {
        String statusString = null;
        if (StringUtils.isNotEmpty(eventStatusCode)) {
            switch (eventStatusCode) {
                case CodeTypeConstant.USER_EVENT_STATUS_SUBMIT:
                    statusString = eventStatusLabel + "（待验证）";
                    break;
                case CodeTypeConstant.USER_EVENT_STATUS_PASS:
                    statusString = eventStatusLabel + "（已验证）";
                    break;
                case CodeTypeConstant.USER_EVENT_STATUS_REFUSED:
                    statusString = eventStatusLabel + "（未通过验证）";
                    break;
                case CodeTypeConstant.USER_EVENT_STATUS_REVERSED:
                    statusString = "申请已撤销";
                    break;
                default:
                    break;
            }
        } else {
            statusString = "申请通过（未验证）";
        }
        return statusString;
    }

    /**
     * 查询指定企业里面所有的人员信息
     */
    @Override
    public List<RPCSysUser> getUserInfoByEntId(String enterpriseId) {
        List<RPCSysUser> resultList = null;
        try {
            resultList = userService.getUserInfoByEntId(enterpriseId);
        } catch (Exception e) {
            logger.error("查询指定企业里面所有的的人员信息时异常", e);
            throw new RuntimeException(e);
        }

        return resultList;
    }




    @Override
    public void updateUserRoleAndStatus(String ticketId, UserExtended param, String entId) throws Exception {
        UserExtended userExtend;
        if (StringUtils.isNotEmpty(param.getSysUserId())) {
            userExtend = userExtendedService.getUserExtendedByUserId(param.getSysUserId());
        } else {
            userExtend = userExtendedService.getUserExtendedById(param.getId());
        }
        String userStatus = param.getUserStatus();
        String userRole = param.getRole();
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        MsgEvent msgEvent = null;
        if (StringUtils.isNotEmpty(userStatus)) {
            if (UserStatus.REJECT.getStatusCode().equals(userStatus)) {
                removeEnterpriseUserRelation(ticketId, userExtend, entId);
                msgEvent = new MsgEvent(SmsAction.USER_JOIN_ENT_REFUSE);
            } else if (UserStatus.QUIT.getStatusCode().equals(userStatus)) {
                removeEnterpriseUserRelation(ticketId, userExtend, entId);
                msgEvent = new MsgEvent(SmsAction.USER_QUIT_ENT);
            } else if (UserStatus.PASS.getStatusCode().equals(userStatus)) {
                msgEvent = new MsgEvent(SmsAction.USER_JOIN_ENT_PASS_REGULAR);
            }
            userExtend.setUserStatus(userStatus);
        }else if (StringUtils.isNotEmpty(userRole)) {
            if (UserRole.ADMIN.getRoleCode().equals(userRole)) {
                msgEvent = new MsgEvent(SmsAction.USER_BECOME_ADMIN);
            } else {
                checkSingleEnterpriseAdmin(userExtend, entId);
                msgEvent = new MsgEvent(SmsAction.USER_REMOVE_ADMIN);
            }
            userExtend.setRole(userRole);
        }

        User receiveUser = userService.getUserByUserId(userExtend.getSysUserId());
        userExtend.setUpdateTime(Util.datetimeToString(new Date()));
        userExtendedService.updateUserExtended(userExtend);

        msgEvent.setSendUser(user);
        msgEvent.setReceiveUser(receiveUser);
        sysMsgServcie.sendMessageAsync(msgEvent);

    }


    @Override
    public void removeEnterpriseUserRelation(String ticketId, UserExtended extended, String entId)
            throws Exception {
        checkSingleEnterpriseAdmin(extended, entId);
        if (StringUtils.isNotBlank(entId)) {
            client.getOrgComServiceManager().removeUserEnterpriseRelation(ticketId, extended.getSysUserId(), entId);
        }

    }

    @Override
    public void checkTickedId(String ticketId, String clientMessage, User user) throws Exception {
        if (user == null && PropertyUtil.getValue("ssoOpen").equals("1")) {
            Map<String, String> map = getAccountAndPasswordByTicketId(ticketId, clientMessage);
            String phoneNum = map.get("loginAccount");
            String password = map.get("password");
            String startTime = map.get("startTime");
            String endTime = map.get("endTime");
            boolean isPhoneNumExist = userService.isPhoneNumExist(null, phoneNum);//判断手机号码是否存在
            if (isPhoneNumExist) {
                boolean isLoginSuccess = userService.isPasswordCorrectByPhoneNum(null, phoneNum, password);//判断手机号码和密码是否全部正确
                if (isLoginSuccess) {
                    User userParam = new User();
                    userParam.setPhoneNo(phoneNum);
                    user = LoginStatusUtils.refreshUserToCacheByUserParam(userParam, startTime, endTime);
                }
            }
        } else {
            if (PropertyUtil.getValue("ssoOpen").equals("1")) {
                Boolean flag = checkClientMessage(ticketId, clientMessage);
                if (!flag) {//客户信息无效
                    user = null;
                }
            }
        }
    }

    @Override
    public List<RPCSysUser> listEnterpriseUser(String enterpriseId, String facilitatorEntId) {
        List<RPCSysUser> prcSysUserList = null;
        if (StringUtils.isEmpty(facilitatorEntId)) {
            prcSysUserList = getUserInfoByEntId(enterpriseId);
        } else {
            prcSysUserList = getUserInfoByEntId(facilitatorEntId);
        }
        return prcSysUserList;
    }

    public Map<String, String> getAccountAndPasswordByTicketId(String ticketId, String clientMessage) {
        Map<String, String> result = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();
        params.put("ticketId", ticketId);
        params.put("clientMessage", clientMessage);
        try {
            String ssoRootDirectory = PropertyUtil.getValue("ssoDemain");
            String jsonStr = HttpRequestUtils.httpPost(ssoRootDirectory + "userLogin/getLoginAccountAndPwdByTicketId", params).toString();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            JSONObject data = JSON.parseObject(jsonObject.getString("data"));
            if (data != null) {
                result.put("loginAccount", data.getString("loginAccount"));
                result.put("password", data.getString("password"));
                result.put("startTime", data.getString("startTime"));
                result.put("endTime", data.getString("endTime"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public Boolean checkClientMessage(String ticketId, String clientMessage) {
        Boolean result = false;
        Map<String, String> params = new HashMap<String, String>();
        params.put("ticketId", ticketId);
        params.put("clientMessage", clientMessage);
        try {
            String ssoDemain = PropertyUtil.getValue("ssoDemain");
            String jsonStr = HttpRequestUtils.httpPost(ssoDemain + "userLogin/checkClientMessage", params).toString();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if (jsonObject.getBooleanValue("data") == true) {//ip匹配
                result = true;
            }
        } catch (Exception e) {
            logger.error("checkClientMessage时异常",e);
        }
        return result;
    }


    private void checkSingleEnterpriseAdmin(UserExtended extended, String entId) throws BusinessException {
        if (UserRole.ADMIN.getRoleCode().equals(extended.getRole()) && UserStatus.PASS.getStatusCode().equals(extended.getUserStatus
                ())) {
            String whereSql = " AND extend.role = :role AND extend.user_status = :userStatus AND relation.EntId = "
                    + ":enterpriseId  ";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("role", UserRole.ADMIN.getRoleCode());
            map.put("userStatus", UserStatus.PASS.getStatusCode());
            map.put("enterpriseId", entId);
            List<UserApproveVo> list = userExtendedDao.listUserApproVo(whereSql, map);
            if (list == null || list.size() <= 1) {
                throw new BusinessException("无法退出企业，请保证企业中至少要有一名管理员");
            }
        }
    }


}