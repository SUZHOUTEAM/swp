package com.mlsc.yifeiwang.sms.service.impl;

import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.ISysNoticeService;
import com.mlsc.yifeiwang.sms.service.SmsService;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.task.SmsMsgTask;
import com.mlsc.task.TaskUtils;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.service.UserService;
import com.mlsc.waste.wastecircle.service.CoopMsgBusService;
import com.mlsc.waste.wastecircle.service.CooperationRelationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yinxl on 2017/7/20.
 */
@Service
public class SysMsgServcieImpl implements SysMsgServcie {

    private Logger logger = Logger.getLogger(SysMsgServcie.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;
    @Autowired
    private CoopMsgBusService coopMsgBusService;
    @Autowired
    private ISysNoticeService sysNoticeService;
    @Autowired
    private CooperationRelationService followService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private LicenceService licenceService;

    @Override
    public void sendMessageAsync(MsgEvent msgEvent) {
        SmsMsgTask smsMsgTask = TaskUtils.getTask(SmsMsgTask.class);
        smsMsgTask.setMsgEvent(msgEvent);
        TaskUtils.executeTask(smsMsgTask);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendMessageSync(MsgEvent msgEvent) throws Exception {
        MsgEvent springMsgEvent = applicationContext.getBean(msgEvent.getClass());
        copyProperty(msgEvent, springMsgEvent);
        springMsgEvent.sendMsg();
    }

    private void copyProperty(MsgEvent source, MsgEvent target) {
        if (source != null && target != null) {
            target.setSmsAction(source.getSmsAction());
            target.setPlaceholderValueMap(source.getPlaceholderValueMap());
            target.setSendUser(source.getSendUser());
            target.setRelId(source.getRelId());
            target.setReceiveUser(source.getReceiveUser());
            target.setReceiveUserList(source.getReceiveUserList());
            target.setReceiverUserQueryParam(source.getReceiverUserQueryParam());
        }
    }
}