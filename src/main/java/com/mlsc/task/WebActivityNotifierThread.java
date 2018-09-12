package com.mlsc.task;

import com.mlsc.yifeiwang.activity.service.IWasteActivityContactsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by user on 2017/11/6.
 */
@Component
@Scope("prototype")
public class WebActivityNotifierThread extends BaseThreadTask {
    private Logger logger = Logger.getLogger(WebActivityNotifierThread.class);

    @Autowired
    private IWasteActivityContactsService wasteActivityContactsService;

    protected Object execute() {
        try {
            wasteActivityContactsService.notifyAllActivityContacts(null);
        } catch (Exception e) {
            logger.error("将获得通知产废企业联系人出错", e);
        }
        return null;
    }
}
