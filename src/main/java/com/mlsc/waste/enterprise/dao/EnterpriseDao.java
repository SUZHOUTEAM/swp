package com.mlsc.waste.enterprise.dao;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.waste.enterprise.model.CityCompany;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.wastecircle.model.EnterpriseVo;

import java.util.List;

/**
 * @author sunjl
 */
public interface EnterpriseDao {
    /**
     * 根据企业id获取企业所属行政区划
     *
     * @param enterpriseId
     * @return
     * @throws DaoAccessException
     */
    RPCSysEnterpriseBaseVo getCantonNameByEnterpriseId(String enterpriseId) throws DaoAccessException;

    /**
     * 根据企业id获取企业所属行政区划
     *
     * @param enterpriseId
     * @return
     * @throws DaoAccessException
     */
    RPCSysEnterpriseBaseVo getDistrictByEnterpriseId(String enterpriseId) throws DaoAccessException;


    // 移动端加入企业列表

    List<EnterpriseVo> getEnterpriseVosByName(String enterpriseName, String enterpriseType);

    List<EnterpriseVo> getCZEnterpriseVosByName(String enterpriseName);
}
