package com.mlsc.yifeiwang.sms.service;

import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;

/**
 * Created by yinxl on 2017/7/20.
 */
public interface SysMsgServcie {

    void sendMessageAsync(MsgEvent msgEvent) ;

    void sendMessageSync(MsgEvent msgEvent) throws Exception ;

    }
