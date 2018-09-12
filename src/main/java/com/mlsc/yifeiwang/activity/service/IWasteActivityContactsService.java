package com.mlsc.yifeiwang.activity.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.activity.entity.WasteActivityContacts;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxl
 * @since 2017-08-01
 */

public interface IWasteActivityContactsService extends IService<WasteActivityContacts> {

    void generateWasteActivityContactsByActivity(String activityId);

    int generateWasteActivityCoverEntCount(String activityId) throws Exception;

    void notifyAllActivityContacts(String activityId) throws Exception;

}
