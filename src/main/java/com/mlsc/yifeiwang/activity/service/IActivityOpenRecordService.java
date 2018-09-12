package com.mlsc.yifeiwang.activity.service;


import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.activity.entity.ActivityOpenRecord;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wudang
 * @since 2017-12-11
 */
public interface IActivityOpenRecordService extends IService<ActivityOpenRecord> {

    boolean saveActivityOpenRecord(String activityId);

}
