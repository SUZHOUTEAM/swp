package com.mlsc.yifeiwang.sms.model.msgevent;

import com.mlsc.epdp.common.exception.ParamDataException;
import com.mlsc.yifeiwang.sms.common.SmsAction;

/**
 * Created by zhanghj on 2017/7/21.
 */
public class NewInquiryMsgEvent extends MsgEvent {

    public NewInquiryMsgEvent() {
        super();
        this.smsAction = SmsAction.ENT_NEW_INQUIRY;
    }

    @Override
    protected void checkParameter(){
        super.checkParameter();
        if(relId==null){
            throw new ParamDataException("发送通知参数错误:relId为空");
        }
    }
}
