package com.mlsc.yifeiwang.operaction.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperationContacts;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
@Service("contactsService")
public interface IWebsiteOperationContactsService extends IService<WebsiteOperationContacts> {
	List<WebsiteOperationContacts> listWebsiteOperationContacts(WebsiteOperationContacts operationContacts);

	Page<WebsiteOperationContacts> pageWebsiteOperationContacts(String operationId,Page<WebsiteOperationContacts> page);

    void saveOrUpdateWebsiteOperationContacts(User user, String operationId, List<SysEnterpriseBase> enterpriseUserList);
}
