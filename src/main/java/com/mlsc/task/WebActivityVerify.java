package com.mlsc.task;

import com.mlsc.yifeiwang.activity.common.ActivityStatus;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import com.mlsc.yifeiwang.activity.service.IWasteActivityService;
import com.mlsc.waste.user.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by user on 2018/3/29.
 */
@Component
public class WebActivityVerify {
    private Logger logger = Logger.getLogger(WebActivityVerify.class);
    @Autowired
    private IWasteActivityService wasteActivityService;

    @Autowired
    private WebActivityQuartzManager webActivityQuartzManager;

    public void execute() {
        logger.info("开始----在线活动监听--------");
        try {
            List<String> stopIdList = wasteActivityService.listStopWasteActivity();
            if (stopIdList != null && stopIdList.size() > 0) {
                logger.info("----在线活动监听--删除任务------"+ StringUtils.join(stopIdList,","));

                User user = new User();
                user.setUserId("100000000000000");
                wasteActivityService.stopActivity(stopIdList,  user,ActivityStatus.PAUSE.getValue());
                wasteActivityService.reomoveJob(stopIdList, ActivityStatus.PAUSE.getValue());
            }

            List<WasteActivityVO> startWasteActivity = wasteActivityService.listStartWasteActivity();
            if (startWasteActivity != null && startWasteActivity.size() > 0) {

                for (WasteActivityVO activity : startWasteActivity) {
                    logger.info("----在线活动监听--添加任务------"+activity.getActivityId());
                    webActivityQuartzManager.addJob(activity, WebActivityNotifierJob.class);
                }
            }



        } catch (Exception e) {
            logger.error("在线活动监听时异常", e);
        }
        logger.info("结束----在线活动监听--------");
    }
}
