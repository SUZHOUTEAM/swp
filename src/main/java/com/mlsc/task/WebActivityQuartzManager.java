package com.mlsc.task;

import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by user on 2018/1/8.
 */
@Service
public class WebActivityQuartzManager {
    private Logger logger = Logger.getLogger(WebActivityQuartzManager.class);

    @Autowired
    private Scheduler sched;

    public void addJob(WasteActivityVO wasteActivityVO, Class<? extends Job> jobClass) {
        try {
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(wasteActivityVO.getActivityId(), Scheduler.DEFAULT_GROUP).build();
            jobDetail.getJobDataMap().put("activityId",wasteActivityVO.getActivityId());

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(wasteActivityVO.getActivityId(), Scheduler.DEFAULT_GROUP)//触发器key
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(wasteActivityVO.getCronJob()))
                    .startNow().build();
            sched.scheduleJob(jobDetail, trigger);
            // 调度容器设置JobDetail和Trigger
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void removeJob(WasteActivityVO wasteActivityVO) {
        try {
            logger.info("---开始----删除活动定时短信 activityId:"+wasteActivityVO.getActivityId());
            TriggerKey triggerKey = TriggerKey.triggerKey(wasteActivityVO.getActivityId(), wasteActivityVO.getActivityId());

            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(wasteActivityVO.getActivityId(), Scheduler.DEFAULT_GROUP));// 删除任务
            logger.info("---结束---删除活动定时短信 activityId:"+wasteActivityVO.getActivityId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
