package com.mlsc.yifeiwang.appmanagement.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.appmanagement.entity.AppConfig;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxl
 * @since 2017-07-28
 */
public interface IAppConfigService extends IService<AppConfig> {
    AppConfig getLastUpdateConfig();
}
