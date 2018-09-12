/**
 * 
 */
package com.mlsc.waste.enterprise.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.enterprise.model.EnterpriseExtended;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;

import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 *
 */
public interface EnterpriseExtendedService {

    /**
     * 用户/企业关系审核记录的保存
     * 
     * @author zhugl date 2016-08-03
     */
    String saveEnterpriseExtended(EnterpriseExtended record, String ticketId);
    
    /**
     * 用户/企业关系审核记录的更新
     * 
     * @author zhugl date 2016-08-03
     */
    void updateEnterpriseExtended(EnterpriseExtended record, String ticketId);
    
    /**
     * 通过平台企业表的ID查询
     * 
     * @author zhugl date 2016-08-03
     */
    EnterpriseExtended getEnterpriseExtendedByEnterpriseId(String enterpriseId);
    
    /**
     * 平台管理员审核企业 通过 或者 退回
     * @param enterpriseId
     * @param userEventStatusId
     */
    void updateByEnpId(String enterpriseId, String userEventStatusId,String valid);
    
    /**
     * 根据条件所有企业信息及状态 
     * @param sql
     * @param paramMap
     * @param paging
     * @return
     */
    List<RPCSysEnterpriseBaseVo> getAllEnterpriseStatusList(String sql,Map<String, Object> paramMap, PagingParameter paging);
   /**
    * 根据条件获取所有企业信息及状态个数 
    * @param sql
    * @param paramMap
    * @return
    */
    int getAllEnterpriseStatusCount(String sql, Map<String, Object> paramMap);

}
