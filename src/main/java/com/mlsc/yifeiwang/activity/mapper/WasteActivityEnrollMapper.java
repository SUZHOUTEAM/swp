package com.mlsc.yifeiwang.activity.mapper;

import com.mlsc.yifeiwang.activity.entity.WasteActivityEnroll;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.activity.model.WasteActivityEnrollModel;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-02-05
 */
public interface WasteActivityEnrollMapper extends BaseMapper<WasteActivityEnroll> {

    int countActivityEnroll(WasteActivityEnrollModel wasteActivityEnrollModel);

    List<WasteActivityEnrollModel> listActivityEnroll(WasteActivityEnrollModel wasteActivityEnrollModel);

    int countRegistrationEnrollActivity(WasteActivityEnrollModel wasteActivityEnrollModel);

    List<WasteActivityEnrollModel> registrationEnrollActivity(WasteActivityEnrollModel wasteActivityEnrollModel);
}