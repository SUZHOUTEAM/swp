package com.mlsc.task;

/**
 * Created by user on 2017/11/20.
 */

import com.mlsc.yifeiwang.operaction.entity.WebsiteOperation;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebOperactionQuartzManager {
    private Logger logger = Logger.getLogger(WebOperactionQuartzManager.class);

    @Autowired
    private Scheduler sched;

    /**
     * @param websiteOperation
     * @param jobClass
     * @Description: 添加一个定时任务
     */
    public void addJob(WebsiteOperation websiteOperation, Class<? extends Job> jobClass) {
        try {
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(websiteOperation.getId(), Scheduler.DEFAULT_GROUP).build();
            jobDetail.getJobDataMap().put("operationId", websiteOperation.getId());
            jobDetail.getJobDataMap().put("smsTemplate", websiteOperation.getSmsTemplate());

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(websiteOperation.getId(), Scheduler.DEFAULT_GROUP)//触发器key
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(websiteOperation.getCronJob()))
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

    /**
     * @param websiteOperation
     * @Description: 修改一个任务的触发时间
     */
    public void modifyJobTime(WebsiteOperation websiteOperation, String cron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(websiteOperation.getId(), websiteOperation.getId());
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                triggerBuilder.withIdentity(websiteOperation.getId(), Scheduler.DEFAULT_GROUP);
                triggerBuilder.startNow();
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                trigger = (CronTrigger) triggerBuilder.build();
                sched.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param websiteOperation
     * @Description: 移除一个任务
     */
    public void removeJob(WebsiteOperation websiteOperation) {
        try {
            logger.info("移除任务，运营id是：" + websiteOperation.getId());
            TriggerKey triggerKey = TriggerKey.triggerKey(websiteOperation.getId(), websiteOperation.getId());
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(websiteOperation.getId(), Scheduler.DEFAULT_GROUP));// 删除任务
        } catch (Exception e) {
            logger.error("移除任务时失败", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     */
    public void startJobs() {
        try {
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public void shutdownJobs() {
        try {
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}