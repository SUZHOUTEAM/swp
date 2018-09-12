package com.mlsc.task;

import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationContactsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhanghj on 2017/8/9.
 */
@Component
@Scope("prototype")
public class WebOperactionNoticeTask extends BaseThreadTask {

    private Logger logger = Logger.getLogger(WebOperactionNoticeTask.class);

    @Autowired
    private IWebsiteOperationContactsService contactsService;

    private static final long serialVersionUID = -2300802729148803084L;

    private User user;

    private List<SysEnterpriseBase> enterpriseUserList;

    private String operactionId;


    @Override
    protected Object execute() {
        try {
            contactsService.saveOrUpdateWebsiteOperationContacts(user, operactionId, enterpriseUserList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("将获得通知产废企业联系人出错", e);
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SysEnterpriseBase> getEnterpriseUserList() {
        return enterpriseUserList;
    }

    public void setEnterpriseUserList(List<SysEnterpriseBase> enterpriseUserList) {
        this.enterpriseUserList = enterpriseUserList;
    }

    public String getOperactionId() {
        return operactionId;
    }

    public void setOperactionId(String operactionId) {
        this.operactionId = operactionId;
    }
}
