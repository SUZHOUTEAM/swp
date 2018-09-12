package com.mlsc.yifeiwang.appmanagement.mapper;

import com.mlsc.yifeiwang.appmanagement.entity.AppManagement;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.appmanagement.model.AppManagementParam;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-12-11
 */
public interface AppManagementMapper extends BaseMapper<AppManagement> {
    List<AppManagement> listAppManagement(AppManagementParam appManagementParam);

    int countAppManagement(AppManagementParam appManagementParam);
}