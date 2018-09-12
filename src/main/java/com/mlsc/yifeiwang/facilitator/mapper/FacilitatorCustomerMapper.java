package com.mlsc.yifeiwang.facilitator.mapper;

import com.mlsc.yifeiwang.facilitator.entity.FacilitatorCustomer;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.facilitator.model.FacilitatorCustomerModel;
import com.mlsc.yifeiwang.facilitator.model.FacilitatorCustomerParam;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-01-15
 */
public interface FacilitatorCustomerMapper extends BaseMapper<FacilitatorCustomer> {
    List<FacilitatorCustomerModel> listFacilitatorBindEnterprise(FacilitatorCustomerParam customerParam);

    List<FacilitatorCustomerModel> listEntByFacilitatorEnt(FacilitatorCustomerParam customerParam);

    int countEntByFacilitatorEnt(FacilitatorCustomerParam customerParam);

    FacilitatorCustomerModel getCustomerImg(FacilitatorCustomerParam customerParam);
}