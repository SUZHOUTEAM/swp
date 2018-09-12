package com.mlsc.yifeiwang.entorder.service;

import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.entorder.entity.EntOrder;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.model.EnterpriseInfo;
import com.mlsc.yifeiwang.entorder.model.OrderModel;
import com.mlsc.yifeiwang.entorder.model.OrderParam;

import java.util.List;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-10-09
 */
public interface IEntOrdersService extends IService<EntOrder> {

    List<OrderModel> listOrder(User user, PagingParameter pagingParameter, OrderParam orderParam) throws Exception;

    List<RPCSysUser> listPersonByEnterId(User user) throws Exception;

    List<SysEnterpriseBase> listReleaseEnt(User user) throws Exception;

    boolean closeOrder(User user, OrderParam orderParam) throws Exception;

    OrderModel countOrderWasteAmount(OrderParam orderParam);

    boolean updateContractStatus(User user, OrderParam orderParam) throws Exception;

    boolean deleteUploadContract(User user, OrderParam orderParam) throws Exception;
}
