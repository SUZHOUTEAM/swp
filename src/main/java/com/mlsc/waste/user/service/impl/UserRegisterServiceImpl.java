package com.mlsc.waste.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.rpc.thrift.api.service.ISysUserService.Iface;
import com.mlsc.rpc.thrift.client.IRPCServiceClient;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import com.mlsc.yifeiwang.im.service.ImService;
import com.mlsc.yifeiwang.sms.service.SmsService;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.user.service.UserExtendedService;
import com.mlsc.waste.user.service.UserRegisterService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static com.mlsc.yifeiwang.sms.common.SmsTemplateCode.SMS_12856051;
import static com.mlsc.yifeiwang.sms.common.SmsTemplateCode.SMS_12856052;
import static com.mlsc.yifeiwang.sms.common.SmsTemplateCode.SMS_12856054;

/**
 * @author zhugl
 */
@Service("UserRegisterService")
public class UserRegisterServiceImpl implements UserRegisterService {
    
    @Autowired
    private UserService userService;
    @Autowired
    private ImService imService;
    @Autowired
    private UserExtendedService userExtendedService;
    @Autowired
    private IRPCServiceClient client;
    @Autowired
    private SmsService smsService;

    @Override
    public boolean isPhoneNumExist(String phoneNum) throws Exception {
        boolean result = userService.isPhoneNumExist(null,phoneNum);
        return result;
    }

    @Override
    public void getIdentifyCode(String phoneNum,String smsType) throws Exception {
        SendMsgParameter sendMsgParameter = null;
        if ("0".equals(smsType)) {
            sendMsgParameter = new SendMsgParameter(phoneNum, SMS_12856054);
        } else if ("1".equals(smsType)) {
            sendMsgParameter = new SendMsgParameter(phoneNum, SMS_12856052);
        }else if("2".equals(smsType)){
            sendMsgParameter = new SendMsgParameter(phoneNum, SMS_12856051);
        }
        sendMsgParameter.getSmsParam().put("product", "易废网");
        sendMsgParameter.getSmsParam().put("code", Util.getSix());
        smsService.getIdentifyCode(sendMsgParameter, null);
    }

    @Override
    public boolean checkIdentifyCode(String phoneNum, String identifyCode) throws Exception {
        return userService.checkIdentifyCode(phoneNum, identifyCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject userRegisterSave(RPCSysUser sysUser,String openId) throws  Exception {
        JSONObject jsonObject = new JSONObject();
        Iface userServiceManager = client.getUserServiceManager();
        String ticketId = Util.uuid32();
        sysUser = userServiceManager.userRegisterSave(sysUser);
        try {
            // 用户扩展表信息保存
            UserExtended userExtend = saveUserExtended(sysUser, ticketId,openId);
//            addUserInfoToLoginStatusUtils(ticketId, sysUser,userExtend.getToken());
            jsonObject.put("ticketId",ticketId);
            jsonObject.put("imToken",userExtend.getToken());

        } catch (Exception e) {
            userServiceManager.removeUser(ticketId, String.valueOf(sysUser.getUserId()));
            throw e;
        }
        return jsonObject;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public JSONObject userRegisterSaveForClient(RPCSysUser sysUser,String clientMessage,String openId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Iface userServiceManager = client.getUserServiceManager();
        String ticketId = null;
        sysUser = userServiceManager.userRegisterSave(sysUser);
        ticketId = userService.getTicketIdByAccount(sysUser.getPhoneNum(),sysUser.getPassword(),clientMessage);//单点服务器产生ticketId
        jsonObject.put("ticketId",ticketId);
        try {
            // 用户扩展表信息保存
            UserExtended userExtend = saveUserExtended(sysUser, ticketId,openId);
            jsonObject.put("imToken",userExtend.getToken());
//            addUserInfoToLoginStatusUtils(ticketId, sysUser,userExtend.getToken());
        } catch (DaoAccessException e) {
            userServiceManager.removeUser(ticketId, String.valueOf(sysUser.getUserId()));
            throw e;
        }
        return jsonObject;
    }

    /**
     * 用户扩展表的记录产生
     * 
     * @return String ticketId;
     */
    @Override
    public UserExtended saveUserExtended (RPCSysUser sysUser, String ticketId,String openId) throws Exception{
        UserExtended record = new UserExtended();
        record.setSysUserId(sysUser.getUserId());
        record.setMobileStatus(Constant.IS_VALID);
        record.setWeXinOpenId(openId);
        record.setAngle(randomAngle());
        record.setToken(imService.genterateIMToken(sysUser.getPhoneNum(),sysUser.getChineseName()));
        userExtendedService.saveUserExtended(record, ticketId);
        return record;
        
    }

    private String randomAngle(){
        Random random = new Random();
        int r = random.nextInt();
        if ((r % 2) == 0) {
            return "A";
        }else{
            return "B";
        }
    }
}
