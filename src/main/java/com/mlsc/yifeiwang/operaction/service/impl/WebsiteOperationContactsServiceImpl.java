package com.mlsc.yifeiwang.operaction.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.operaction.mapper.WebsiteOperationContactsMapper;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperationContacts;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationContactsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
@Service
public class WebsiteOperationContactsServiceImpl extends ServiceImpl<WebsiteOperationContactsMapper, WebsiteOperationContacts> implements IWebsiteOperationContactsService {

    @Override
    public List<WebsiteOperationContacts> listWebsiteOperationContacts(WebsiteOperationContacts operationContacts) {
        return this.baseMapper.listWebsiteOperationContacts(operationContacts);
    }

    @Override
    public Page<WebsiteOperationContacts> pageWebsiteOperationContacts(String operationId, Page<WebsiteOperationContacts> page) {
        EntityWrapper<WebsiteOperationContacts> ew = new EntityWrapper<WebsiteOperationContacts>();
        ew.setSqlSelect("entName,contactsName,contactsTel");
        if (StringUtils.isNotEmpty(operationId)) {
            ew.eq("operationId", operationId);
        }
        return selectPage(page, ew);
    }

    @Override
    public void saveOrUpdateWebsiteOperationContacts(User user, String operationId, List<SysEnterpriseBase> enterpriseUserList) {
        List<WebsiteOperationContacts> contactsList = new ArrayList<WebsiteOperationContacts>();
        List<String> enterIds = new ArrayList<String>();
        for (SysEnterpriseBase enterUser : enterpriseUserList) {
            WebsiteOperationContacts contacts = new WebsiteOperationContacts();
            contacts.setEntName(enterUser.getEntName());
            contacts.setEntId(enterUser.getEntId());
            contacts.setContactsName(enterUser.getContacts());
            contacts.setContactsTel(enterUser.getContactsTel());
            contacts.setOperationId(operationId);
            Date date = new Date();
            contacts.setCreateBy(user.getUserId());
            contacts.setCreateTime(date);
            contacts.setEditBy(user.getUserId());
            contacts.setEditTime(date);
            contactsList.add(contacts);
            enterIds.add(enterUser.getEntId());
        }

        List<WebsiteOperationContacts> entContactList = this.baseMapper.listUserConstactsFromEntContact(enterIds);
        if (entContactList != null && contactsList.size() > 0) {
            contactsList.addAll(entContactList);
        }
        EntityWrapper<WebsiteOperationContacts> ew = new EntityWrapper<WebsiteOperationContacts>();
        ew.setSqlSelect("id");
        if (cn.jiguang.common.utils.StringUtils.isNotEmpty(operationId)) {
            ew.eq("operationId", operationId);
        }
        List<WebsiteOperationContacts> list = this.selectList(ew);
        if (list != null && list.size() > 0) {
            this.delete(ew);
        }
        this.insertBatch(contactsList);
    }
}
