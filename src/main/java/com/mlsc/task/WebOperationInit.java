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
public class WebOperationInit {
    private Logger logger = Logger.getLogger(WebOperationInit.class);
    @Autowired
    private IWebsiteOperationService operationService;
    @Autowired
    private WebOperactionQuartzManager webOperactionQuartzManager;

    public void execute() {
        logger.info("-----开始服务器重启后开启运营任务-------");
        try {
            List<WebsiteOperation> list = operationService.listOperationByStatus(WebsiteOperationStatus.RUNNING.getCode());
            for (WebsiteOperation operation : list) {
                webOperactionQuartzManager.addJob(operation, WebOperactionJob.class);
            }
        } catch (Exception e) {
            logger.error("运营任务启动失败", e);
        }
        logger.info("-----结束服务器重启后开启运营任务 -------");
    }
}
