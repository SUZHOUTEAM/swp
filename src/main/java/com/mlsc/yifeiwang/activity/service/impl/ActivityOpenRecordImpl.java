package com.mlsc.yifeiwang.activity.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.activity.entity.ActivityOpenRecord;
import com.mlsc.yifeiwang.activity.service.IActivityOpenRecordService;
import com.mlsc.yifeiwang.activity.mapper.ActivityOpenRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wudang
 * @since 2017-12-11
 */
@Service
public class ActivityOpenRecordImpl extends ServiceImpl<ActivityOpenRecordMapper, ActivityOpenRecord> implements IActivityOpenRecordService {
    private final static Logger logger = LoggerFactory.getLogger(ActivityOpenRecordImpl.class);

    @Override
    public boolean saveActivityOpenRecord(String activityId) {
        ActivityOpenRecord activityOpenRecord = new ActivityOpenRecord();
        activityOpenRecord.setActivityId(activityId);
        Date currentDate = new Date();
        activityOpenRecord.setCreateTime(currentDate);
        return this.insert(activityOpenRecord);
    }

}
