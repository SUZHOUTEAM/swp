package com.mlsc.task;

/**
 * Created by user on 2017/11/20.
 */

import com.mlsc.yifeiwang.activity.common.ActivityStatus;
import com.mlsc.yifeiwang.activity.entity.WasteActivityContacts;
import com.mlsc.yifeiwang.sms.common.SmsTemplateCode;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import com.mlsc.yifeiwang.sms.service.SmsService;
import com.mlsc.yifeiwang.activity.model.ActivityQueryParam;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperationContacts;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationContactsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class WebOperactionJob implements Job {
    private Logger logger = Logger.getLogger(WebOperactionJob.class);

    private String operationId;
    @Autowired
    private IWebsiteOperationContactsService contactsService;
    @Autowired
    private SmsService smsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
            String operationId = map.getString("operationId");
            String smsTemplate = map.getString("smsTemplate");
            logger.info("job start------------" + operationId);
            WebsiteOperationContacts operationContacts = new WebsiteOperationContacts();
            operationContacts.setOperationId(operationId);
            List<WebsiteOperationContacts> listWebsiteOperationContacts = contactsService.listWebsiteOperationContacts(operationContacts);
            if (listWebsiteOperationContacts != null && listWebsiteOperationContacts.size() > 0) {
                notifyAllWebsiteOperationContacts(listWebsiteOperationContacts,smsTemplate);
            }

        } catch (Exception e) {
            logger.error("营销短信通知异常", e);
        }


    }

    public void notifyAllWebsiteOperationContacts(List<WebsiteOperationContacts> listWebsiteOperationContacts,String smsTemplate) throws Exception {
        ActivityQueryParam activityQueryParam = new ActivityQueryParam();
        activityQueryParam.setStatus(ActivityStatus.ACTIVE.getValue());
        {
            int index = 0;
            int sendSize = 0;
            StringBuilder contactsPhoneBuilder = new StringBuilder();
            List<WasteActivityContacts> sendContactsList = new ArrayList<>();
            Date now = new Date();
            logger.debug("-----开始发送营销短信-----总共条数:" + listWebsiteOperationContacts.size() + "-----------");
            while (index < listWebsiteOperationContacts.size()&&StringUtils.isNotEmpty(smsTemplate)) {
                WebsiteOperationContacts contacts = listWebsiteOperationContacts.get(index);
                String phoneNum = contacts.getContactsTel();
                if (StringUtils.isNotEmpty(phoneNum)) {
                    contactsPhoneBuilder.append(",");
                    contactsPhoneBuilder.append(phoneNum);
                    sendSize++;
                }
                try {
                    if ((sendSize != 0 && sendSize % SmsTemplateCode.ONCE_RECEIVER_NUM == 0)
                            || index == listWebsiteOperationContacts
                            .size() - 1) {
                        SendMsgParameter parameter = new SendMsgParameter();
                        parameter.setSmsTemplateCode(smsTemplate);
                        parameter.setPhoneNum(contactsPhoneBuilder.toString().substring(1));
                        smsService.sendMsg(parameter);
                        logger.debug("-----发送营销手机号码为：" + contactsPhoneBuilder.toString().substring(1));
                        contactsPhoneBuilder = new StringBuilder();
                    }
                } catch (Exception e) {
                    logger.error("发送营销短信失败：", e);
                }
                index++;
            }
            logger.debug("-----结束发送营销短信-----------");
        }
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}