package com.mlsc.task;

import com.mlsc.yifeiwang.activity.entity.WasteActivity;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.activity.service.IWasteActivityContactsService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.user.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghj on 2017/8/9.
 */
@Component
@Scope("prototype")
public class WebActivityNoticeTask extends BaseThreadTask {

    private Logger logger = Logger.getLogger(WebActivityNoticeTask.class);

    private static final long serialVersionUID = -2300802729148803084L;

    private User sendUser;

    private List<WasteActivity> activityList;

    @Autowired
    private UserService userService;

    public void setActivity(List<WasteActivity> activity) {
        this.activityList = activity;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Autowired
    private IWasteActivityContactsService wasteActivityContactsService;


    @Override
    protected Object execute() {
        try {
            MsgEvent msgEvent = new MsgEvent(SmsAction.ACTIVITY_NEW_PUBLISH);
            List<User> receiverList = userService.listUser(new User());
            msgEvent.setSendUser(sendUser);
            msgEvent.setReceiveUserList(receiverList);
            Map<String, String> placeholderValueMap = new HashMap<String, String>();
            for (WasteActivity activity: activityList) {
                placeholderValueMap.put("activityName", activity.getActivityName());
                msgEvent.setPlaceholderValueMap(placeholderValueMap);
                msgEvent.setRelId(activity.getId());
                sysMsgServcie.sendMessageSync(msgEvent);
                wasteActivityContactsService.generateWasteActivityContactsByActivity(activity.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("生成活动通知企业用户及通知相关用户时出错", e);
        }
        return null;
    }
}
