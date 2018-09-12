package com.mlsc.task;

import com.mlsc.yifeiwang.operaction.common.WebsiteOperationStatus;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperation;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by user on 2017/11/29.
 */
@Component
public class WebOperationVerify {
    private Logger logger = Logger.getLogger(WebOperationVerify.class);

    @Autowired
    private IWebsiteOperationService operationService;

    @Autowired
    private WebOperactionQuartzManager webOperactionQuartzManager;

    public void execute() {
        logger.info("开始----运营监听--------");
        try {
            List<WebsiteOperation> stopList = operationService.listStopWebSiteOperationInfo();
            if (stopList != null && stopList.size() > 0) {
                for (WebsiteOperation operation : stopList) {
                    logger.info("----运营监听--删除任务------"+ operation.getId());
                    operationService.updateOperationStatus(operation, WebsiteOperationStatus.STOP.getCode());
                    webOperactionQuartzManager.removeJob(operation);
                }
            }


            List<WebsiteOperation> startList = operationService.listStartWebSiteOperationInfo();
            if (startList != null && startList.size() > 0) {
                for (WebsiteOperation operation : startList) {
                    logger.info("----运营监听--添加任务------"+operation.getId());
                    operationService.updateOperationStatus(operation, WebsiteOperationStatus.RUNNING.getCode());
                    webOperactionQuartzManager.addJob(operation, WebOperactionJob.class);
                }
            }


        } catch (Exception e) {
            logger.error("运营任务启动失败", e);
        }
        logger.info("结束----运营监听--------");
    }
}
