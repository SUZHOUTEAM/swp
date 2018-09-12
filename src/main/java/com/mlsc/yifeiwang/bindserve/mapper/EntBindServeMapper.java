package com.mlsc.yifeiwang.bindserve.mapper;

import com.mlsc.yifeiwang.bindserve.entity.EntBindServe;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeModel;
import com.mlsc.yifeiwang.bindserve.model.EntBindServeParam;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-04-26
 */
public interface EntBindServeMapper extends BaseMapper<EntBindServe> {

    int countBindServe(EntBindServeParam entBindServeParam);

    List<EntBindServeModel> listBindServe(EntBindServeParam entBindServeParam);
}