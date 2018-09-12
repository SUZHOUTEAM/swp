package com.mlsc.task;

import com.mlsc.yifeiwang.activity.common.ActivityStatus;
import com.mlsc.yifeiwang.activity.mapper.WasteActivityMapper;
import com.mlsc.yifeiwang.activity.model.ActivityQueryParam;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by user on 2018/1/8.
 */
public class WebActivityInit {
    private Logger logger = Logger.getLogger(WebActivityInit.class);
    @Autowired
    private WasteActivityMapper wasteActivityMapper;
    @Autowired
    private WebActivityQuartzManager webActivityQuartzManager;

    public void execute() {
        logger.info("开始-----服务器重启后---开启活动通知-------");
        try {
            ActivityQueryParam activityQueryParam = new ActivityQueryParam();
            activityQueryParam.setStatus(ActivityStatus.ACTIVE.getValue());
            List<WasteActivityVO> activityList = wasteActivityMapper.listWasteActivity(activityQueryParam);
            for (WasteActivityVO wasteActivityVO : activityList) {
                webActivityQuartzManager.addJob(wasteActivityVO, WebActivityNotifierJob.class);

            }
        } catch (Exception e) {
            logger.error("活动通知启动失败", e);
        }
        logger.info("结束-----服务器重启后---开启活动通知 -------");
    }
}
