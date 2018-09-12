package com.mlsc.yifeiwang.recordcontract.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContract;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractModel;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractParam;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-03-01
 */
public interface EntRecordContractMapper extends BaseMapper<EntRecordContract> {
    List<EntRecordContractModel> listRecordContractList(EntRecordContractParam recordContractParam);

    Integer countRecordContractList(EntRecordContractParam recordContractParam);
}
