package com.mlsc.yifeiwang.sms.model.msgevent;

import com.mlsc.yifeiwang.sms.common.SmsAction;

/**
 * Created by zhanghj on 2017/7/21.
 */
public class CreateEntRefuseMsgEvent extends MsgEvent {

    public CreateEntRefuseMsgEvent() {
        super();
        this.smsAction = SmsAction.USER_CREATE_ENT_REFUSE;
    }

}
