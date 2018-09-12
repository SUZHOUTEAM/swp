package com.mlsc.yifeiwang.appmanagement.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.appmanagement.entity.AppConfig;
import com.mlsc.yifeiwang.appmanagement.mapper.AppConfigMapper;
import com.mlsc.yifeiwang.appmanagement.service.IAppConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxl
 * @since 2017-07-28
 */
@Service("appConfigService")
public class AppConfigServiceImpl extends ServiceImpl<AppConfigMapper, AppConfig> implements IAppConfigService {

    @Override
    public AppConfig getLastUpdateConfig() {
        EntityWrapper<AppConfig> ew = new EntityWrapper<>();
        ew.eq("1" ,"1");
        ew.orderBy("update_time", false);
        return selectOne(ew);
    }
}
