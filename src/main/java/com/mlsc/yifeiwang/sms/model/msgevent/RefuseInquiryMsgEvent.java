package com.mlsc.yifeiwang.sms.model.msgevent;

import com.mlsc.epdp.common.exception.ParamDataException;
import com.mlsc.yifeiwang.sms.common.SmsAction;

/**
 * Created by zhanghj on 2017/7/21.
 */
public class RefuseInquiryMsgEvent extends MsgEvent {

    public RefuseInquiryMsgEvent() {
        super();
        this.smsAction = SmsAction.ENT_REFUSE_INQUIRY;
    }

    @Override
    protected void checkParameter(){
        super.checkParameter();
        if(relId==null){
            throw new ParamDataException("发送通知参数错误:rel为空");
        }
    }
}
