package com.mlsc.yifeiwang.licence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.entity.DispositionCapacitydetailRelease;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangj
 * @since 2017-08-23
 */
public interface DispositionCapacitydetailReleaseMapper extends BaseMapper<DispositionCapacitydetailRelease> {
    List<EntWasteModel> listWasteInfoByReleaseDetailId(@Param("entId")  String  endId, @Param("releaseId") String releaseId);
}