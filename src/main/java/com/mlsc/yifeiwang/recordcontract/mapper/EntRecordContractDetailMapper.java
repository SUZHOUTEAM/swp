package com.mlsc.yifeiwang.recordcontract.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContractDetail;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractDetailModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-03-01
 */
public interface EntRecordContractDetailMapper extends BaseMapper<EntRecordContractDetail> {
    List<EntRecordContractDetailModel> listContractDetailByRecordId(@Param("recordId") String recordId);
}