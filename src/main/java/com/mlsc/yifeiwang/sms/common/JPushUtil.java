package com.mlsc.yifeiwang.sms.common;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.mlsc.common.exception.BusinessException;
import com.mlsc.common.util.StringUtils;
import com.mlsc.yifeiwang.sms.model.JPushMsgParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JPushUtil {

    private static Map<String,JPushClient> clientMap = new HashMap<String,JPushClient>();
    private final static Logger logger = LoggerFactory.getLogger(JPushUtil.class);

    public static void main(String[] args) throws BusinessException {
        JPushMsgParameter jPushMsgParameter = new JPushMsgParameter();
        jPushMsgParameter.setMsgTitle("易废网");
        jPushMsgParameter.setNotificationTitle("测试测试");
        jPushMsgParameter.setMsgTitle("标题");
        jPushMsgParameter.setExtraParam("ORDER");
        jPushMsgParameter.setMsgContent("测试开始了");
        jPushMsgParameter.setJpushTarget(JPushTarget.DISPOSION);
        jPushMsgParameter.setRegistrationId("1a0018970a907ec0bcf");
        List<JPushMsgParameter> list = new ArrayList<>();
        list.add(jPushMsgParameter);
        sendMessages(list);
    }

    public static void sendMessages(List<JPushMsgParameter> parameterList){
        if(parameterList!=null&&parameterList.size()>0){
            for(JPushMsgParameter jPushMsgParameter : parameterList){
                if(jPushMsgParameter!=null&& StringUtils.isNotNullOrEmpty(jPushMsgParameter.getRegistrationId())){
                    try {
                        JPushTarget jPushTarget = jPushMsgParameter.getJpushTarget();
                        List<JPushTarget> list = JPushTarget.listNeedSendJpushTarget(jPushTarget);
                        for(JPushTarget target: list){
                            JPushClient client = clientMap.get(target.getCode());
                            if(client == null){
                                client = new JPushClient(target.getMasterSecret(),target.getAppkey());
                                clientMap.put(target.getCode(),client);
                            }
                            PushPayload pushPayload = buildPushPayload(jPushMsgParameter.getRegistrationId(),jPushMsgParameter.getNotificationTitle(),
                                    jPushMsgParameter.getMsgTitle(),jPushMsgParameter.getMsgContent(),jPushMsgParameter.getExtraParam());
                            PushResult pushResult=client.sendPush(pushPayload);
                            if(!pushResult.isResultOK()){
                                logger.error("推送信息失败");
                            }
                        }
                    } catch (Exception e) {
                        logger.error("推送信息出错",e);
                    }
                }
            }

        }

    }
    
    private static PushPayload buildPushPayload(String registrationId,String notification_title, String msg_title, String msg_content, String extrasparam) {

        return PushPayload.newBuilder()
                //指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.registrationId(registrationId))
                //jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                .setNotification(Notification.newBuilder()
                        //指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(msg_title+":"+msg_content)
                                .setTitle(notification_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("androidNotification extras key",extrasparam)

                                .build())
                        //指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(msg_title+":"+msg_content)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key",extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                //取消此注释，消息推送时ios将无法在锁屏情况接收
                                // .setContentAvailable(true)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(true)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
                        .build())
                .build();
    }
}
