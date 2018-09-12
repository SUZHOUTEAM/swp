package com.mlsc.yifeiwang.licence.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.licence.entity.OperationLicenceDetail;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangj
 * @since 2017-08-22
 */
public interface OperationLicenceDetailMapper extends BaseMapper<OperationLicenceDetail> {
    List<EntWasteModel> listWasteInfoByLicenceId(@Param("entId") String endId, @Param("licenceId") String releaseId);

}