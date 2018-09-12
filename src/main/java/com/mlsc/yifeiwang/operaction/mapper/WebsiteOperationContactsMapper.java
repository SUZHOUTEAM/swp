package com.mlsc.yifeiwang.operaction.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperationContacts;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
public interface WebsiteOperationContactsMapper extends BaseMapper<WebsiteOperationContacts> {
    List<WebsiteOperationContacts> listWebsiteOperationContacts(WebsiteOperationContacts websiteOperationContacts);

    List<WebsiteOperationContacts>  listUserConstactsFromEntContact(List<String> enterIds);
}