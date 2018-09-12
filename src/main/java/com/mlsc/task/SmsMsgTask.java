package com.mlsc.task;

import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 短信发送
 */
@Component
@Scope("prototype")
public class SmsMsgTask extends BaseThreadTask {

    private Logger logger = Logger.getLogger(SmsMsgTask.class);

    private static final long serialVersionUID = -2538803975887410623L;

    private MsgEvent msgEvent;

    public void setMsgEvent(MsgEvent msgEvent) {
        this.msgEvent = msgEvent;
    }

    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Override
    protected Object execute() {
        try {
            //todo 设计返回结果，返回发送情况（应发送数量，实际发送数量）
            sysMsgServcie.sendMessageSync(msgEvent);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发送通知出错", e);
        }
        return null;
    }

}
