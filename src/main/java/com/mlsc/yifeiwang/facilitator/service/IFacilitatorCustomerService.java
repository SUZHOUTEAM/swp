package com.mlsc.yifeiwang.facilitator.service;

import com.mlsc.yifeiwang.facilitator.entity.FacilitatorCustomer;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.facilitator.model.FacilitatorCustomerModel;
import com.mlsc.yifeiwang.facilitator.model.FacilitatorCustomerParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-01-15
 */
public interface IFacilitatorCustomerService extends IService<FacilitatorCustomer> {

    boolean saveFacilitatorCustomer(User user, FacilitatorCustomer facilitatorCutomer) throws Exception;

    boolean deleteFacilitatorCustomer(User user, List<String> ids) throws Exception;

    List<FacilitatorCustomerModel> listBindEnterprise(FacilitatorCustomerParam facilitatorCustomerParam) throws Exception;

    List<FacilitatorCustomerModel> listFacilitatorCustomer(FacilitatorCustomerParam facilitatorCustomerParam, PagingParameter pagingParameter) throws Exception;

    String saveProductionEnt(String ticketId, String entType, RPCSysEnterpriseBase enterpriseBase) throws Exception;

    void checkAreaCoverage(String productEntCantonCode, String enterpriseId, List<String> infoList) throws Exception;

    FacilitatorCustomerModel getCustomerImg(FacilitatorCustomerParam facilitatorCustomerParam) throws Exception;
}
