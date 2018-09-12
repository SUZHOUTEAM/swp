package com.mlsc.yifeiwang.recordcontract.service;

import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContractDetail;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractDetailModel;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-03-01
 */
public interface IEntRecordContractDetailService extends IService<EntRecordContractDetail> {
    List<EntRecordContractDetailModel> listContractDetailByRecordId(String recordId) throws Exception;

}
