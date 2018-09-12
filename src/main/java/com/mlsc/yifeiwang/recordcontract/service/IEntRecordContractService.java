package com.mlsc.yifeiwang.recordcontract.service;

import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContract;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractModel;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-03-01
 */
public interface IEntRecordContractService extends IService<EntRecordContract> {

    String saveRecordContract(User user, EntRecordContractParam recordContractParam) throws Exception;

    boolean updateRecordContract(User user, EntRecordContractParam recordContractParam) throws Exception;

    boolean approveRecordContract(User user, EntRecordContractParam recordContractParam) throws Exception;

    boolean rejectRecordContract(User user, EntRecordContractParam recordContractParam) throws Exception;

    EntRecordContractModel getRecordContractById(User user, EntRecordContractParam recordContractParam) throws Exception;

    List<EntRecordContractModel> listRecordContractList(User user, PagingParameter pagingParameter, EntRecordContractParam recordContractParam) throws Exception;

}
