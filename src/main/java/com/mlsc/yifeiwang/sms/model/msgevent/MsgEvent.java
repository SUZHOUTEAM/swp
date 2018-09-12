package com.mlsc.yifeiwang.sms.model.msgevent;

import com.mlsc.common.constant.BaseConstant;
import com.mlsc.yifeiwang.sms.entity.SysNotice;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.sms.common.JPushTarget;
import com.mlsc.yifeiwang.sms.common.JPushUtil;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.common.SmsSendType;
import com.mlsc.yifeiwang.sms.model.JPushMsgParameter;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import com.mlsc.yifeiwang.sms.service.ISysNoticeService;
import com.mlsc.yifeiwang.sms.service.SmsService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.yifeiwang.user.service.IUserExtendedService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghj on 2017/7/21.
 */
@Component
@Scope("prototype")
public class MsgEvent implements Serializable {

    private static final long serialVersionUID = -5442479481756392591L;

    protected SmsAction smsAction;

    protected User sendUser;

    protected List<User> receiveUserList;

    protected User receiveUser;

    protected User receiverUserQueryParam;

    protected Map<String, String> placeholderValueMap = new HashMap<>();

    protected String relId;

    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ISysNoticeService sysNoticeService;
    @Autowired
    private IUserExtendedService userExtendedService;

    public MsgEvent() {

    }

    public MsgEvent(SmsAction smsAction) {
        this.smsAction = smsAction;
    }

    public void setReceiveUserList(List<User> receiveUserList) {
        this.receiveUserList = receiveUserList;
    }

    public void setReceiveUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public void setPlaceholderValueMap(Map<String, String> placeholderValueMap) {
        this.placeholderValueMap = placeholderValueMap;
    }

    public SmsAction getSmsAction() {
        return smsAction;
    }

    public void setSmsAction(SmsAction smsAction) {
        this.smsAction = smsAction;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public User getReceiverUserQueryParam() {
        return receiverUserQueryParam;
    }

    public void setReceiverUserQueryParam(User receiverUserQueryParam) {
        this.receiverUserQueryParam = receiverUserQueryParam;
    }

    public List<User> getReceiveUserList() {
        return receiveUserList;
    }

    public User getReceiveUser() {
        return receiveUser;
    }

    public Map<String, String> getPlaceholderValueMap() {
        return placeholderValueMap;
    }

    public String getRelId() {
        return relId;
    }

    public void sendMsg() throws Exception {
        MsgEventResult msgEventResult = obtainMsgEventResult();
        if (msgEventResult != null) {
            if (msgEventResult.getNoticeList() != null && msgEventResult.getNoticeList().size() > 0) {
                sysNoticeService.saveNoticeList(msgEventResult.getNoticeList(), sendUser);
            }
            if (msgEventResult.getSendMsgParameterList() != null
                    && msgEventResult.getSendMsgParameterList().size() > 0) {
                smsService.sendMsgs(msgEventResult.getSendMsgParameterList());
            }
            if (msgEventResult.getJpushMsgParameterList() != null
                    && msgEventResult.getJpushMsgParameterList().size() > 0) {
                JPushUtil.sendMessages(msgEventResult.getJpushMsgParameterList());
            }
        }
    }


    private MsgEventResult obtainMsgEventResult() throws DaoAccessException {
        generateReceiver();
        checkParameter();
        return generateMsgEventResult();
    }

    protected void generateReceiver() throws DaoAccessException {
        if (receiveUser == null && (receiveUserList == null || receiveUserList.size() == 0) && receiverUserQueryParam != null) {
            List<User> list = userService.listUser(receiverUserQueryParam);
            setReceiveUserList(list);
        }
    }


    protected MsgEventResult generateMsgEventResult() {
        MsgEventResult result = new MsgEventResult();
        if (placeholderValueMap == null) {
            placeholderValueMap = new HashMap<>();
        }
        List<SendMsgParameter> sendMsgParameterList = new ArrayList<SendMsgParameter>();
        SendMsgParameter sendMsgParameter = null;
        List<SysNotice> noticeList = new ArrayList<SysNotice>();
        SysNotice sysNotice = null;
        List<JPushMsgParameter> jPushMsgParameterList = new ArrayList<>();
        JPushMsgParameter jPushMsgParameter = null;
        List<User> receiverList = new ArrayList<>();
        if (receiveUser != null) {
            receiverList.add(receiveUser);
        }
        if (receiveUserList != null && receiveUserList.size() > 0) {
            receiverList.addAll(receiveUserList);
        }
        if (receiverList.size() == 0) {
            return null;
        }
        for (User receiverUser : receiverList) {
            SmsSendType[] sendTypes = smsAction.getSendTypes();
            if (placeholderValueMap.get("entName") == null) {
                if (receiverUser.getEnterpriseName() != null) {
                    placeholderValueMap.put("entName", receiverUser.getEnterpriseName());
                }
            }
            String noticeContent = smsAction.getActionDesc(placeholderValueMap);
            for (SmsSendType smsSendType : sendTypes) {
                if (SmsSendType.SYS.equals(smsSendType)) {
                    sysNotice = new SysNotice();
                    sysNotice.setSenderId(sendUser.getUserId());
                    sysNotice.setReceiverId(receiverUser.getUserId());
                    sysNotice.setReceiverType(smsAction.getReceiverTypeCode());
                    sysNotice.setNoticeCategory(smsAction.getNoticeCategory().getCode());
                    sysNotice.setNoticeType(smsAction.getActionCode());
                    sysNotice.setNoticeContent(noticeContent);
                    sysNotice.setHasRead(BaseConstant.NO);
                    sysNotice.setRelId(relId);
                    noticeList.add(sysNotice);
                }
                if (SmsSendType.SMS.equals(smsSendType)) {
                    sendMsgParameter = new SendMsgParameter(receiverUser.getPhoneNo(), smsAction.getSmsTemplateCode());
                    sendMsgParameter.setSmsParam(placeholderValueMap);
//                    if (sendMsgParameter.getSmsAction().getActionCode().equalsIgnoreCase(SmsAction.NEW_PUBLISH.getActionCode())) {
//                        UserExtended userExtended = userExtendedService.getUserExtendedByUserId(receiverUser.getUserId());
//                        sysNotice.setHasSendMsg(BaseConstant.NO);
//                    }
                    sendMsgParameterList.add(sendMsgParameter);
                }
                if (SmsSendType.JPUSH.equals(smsSendType)) {
                    JPushTarget jPushTarget = JPushTarget.ALL;
                    if (CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equals(receiverUser.getEntType())) {
                        jPushTarget = JPushTarget.PRODUCTION;
                    }
                    if (CodeTypeConstant.ENTERPRISE_TYPE_DISPOSITION.equals(receiverUser.getEntType())||CodeTypeConstant.ENTERPRISE_TYPE_DIS_FACILITATOR.equals(receiverUser.getEntType())) {
                        jPushTarget = JPushTarget.DISPOSION;
                    }
                    if (StringUtils.isNotEmpty(receiverUser.getRegistrationCode())) {
                        jPushMsgParameter = new JPushMsgParameter();
                        jPushMsgParameter.setJpushTarget(jPushTarget);
                        jPushMsgParameter.setRegistrationId(receiverUser.getRegistrationCode());
                        jPushMsgParameter.setNotificationTitle(Constant.NOTIFICATION_TITLE);
                        jPushMsgParameter.setMsgTitle(smsAction.getNoticeCategory().getName());
                        jPushMsgParameter.setMsgContent(noticeContent);
                        jPushMsgParameter.setExtraParam("");
                        jPushMsgParameterList.add(jPushMsgParameter);
                    }
                }
            }
        }
        result.setNoticeList(noticeList);
        result.setSendMsgParameterList(sendMsgParameterList);
        result.setJpushMsgParameterList(jPushMsgParameterList);
        return result;
    }

    protected void checkParameter() {

    }


}
