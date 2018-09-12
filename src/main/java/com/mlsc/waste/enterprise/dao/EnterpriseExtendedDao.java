/**
 * 
 */
package com.mlsc.waste.enterprise.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.enterprise.model.EnterpriseExtended;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;

import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 *
 */
public interface EnterpriseExtendedDao extends EntityDao<EnterpriseExtended> {

    /**
     * 平台管理员管理员审核，根据企业ID和事件状态去更新数据
     * @param enterpriseId
     * @param userEventStatus
     */
    void updateByEnpId(String enterpriseId, String userEventStatus,String valid);

    List<RPCSysEnterpriseBaseVo> getAllEnterpriseStatusList(String sql,Map<String, Object> paramMap, PagingParameter paging,String codevalueId);

    int getAllEnterpriseStatusCount(String sqlWhere,Map<String, Object> paramMap,String codevalueId);

    
}
