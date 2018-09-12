package com.mlsc.task;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by user on 2017/11/6.
 */
@Component
public class WebActivityNotifierSingleJob {
    private Logger logger = Logger.getLogger(WebActivityNotifierSingleJob.class);
    public void execute(){
        logger.info("-------------开始发送短信----------------");
        try{
            WebActivityNotifierThread webActivityNotifierThread = TaskUtils.getTask(WebActivityNotifierThread.class);
            TaskUtils.executeTask(webActivityNotifierThread);
        }catch(Exception e){
            logger.error("发送短信失败",e);
        }
        logger.info("-------------短信发送结束----------------");
}
}
