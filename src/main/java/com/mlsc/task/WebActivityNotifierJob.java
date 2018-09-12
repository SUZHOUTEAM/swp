package com.mlsc.task;

import com.mlsc.yifeiwang.activity.service.IWasteActivityContactsService;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by user on 2018/1/9.
 */
@Component
public class WebActivityNotifierJob implements Job {
    private Logger logger = Logger.getLogger(WebActivityNotifierJob.class);
    @Autowired
    private IWasteActivityContactsService wasteActivityContactsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
            String activityId = map.getString("activityId");
            logger.info("job start------------" + activityId);
            wasteActivityContactsService.notifyAllActivityContacts(activityId);

        } catch (Exception e) {
            logger.error("通知活动用户异常", e);
        }
    }
}
