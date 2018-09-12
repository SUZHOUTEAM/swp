package com.mlsc.yifeiwang.sms.model.msgevent;

import com.mlsc.yifeiwang.sms.common.SmsAction;

/**
 * Created by zhanghj on 2017/7/21.
 */
public class CreateEntPassMsgEvent extends MsgEvent {

    public CreateEntPassMsgEvent() {
        super();
        this.smsAction = SmsAction.USER_CREATE_ENT_PASS;
    }

}
