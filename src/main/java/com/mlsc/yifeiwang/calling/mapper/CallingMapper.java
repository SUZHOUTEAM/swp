package com.mlsc.yifeiwang.calling.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.calling.entity.Calling;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.calling.model.CallingListPojo;
import com.mlsc.yifeiwang.calling.model.CallingVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yxl
 * @since 2017-07-26
 */
public interface CallingMapper extends BaseMapper<Calling> {
    Integer countCalling(Map<String, Object> params);

    List<CallingListPojo> listCalling(Map<String, Object> params);

    List<CallingVo> listThirdLevelCalling();

    List<CallingListPojo> listCallingByPage(PagingParameter paging);

    Integer totalCalling();
}