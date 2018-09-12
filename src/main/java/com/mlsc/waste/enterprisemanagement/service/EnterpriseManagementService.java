package com.mlsc.waste.enterprisemanagement.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.solr.model.EnterpriseIndex;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;

import java.util.List;
import java.util.Map;


/**
 * @author sunjl
 * 
 */
public interface EnterpriseManagementService {

    /**
     * 平台管理员获取所有企业列表个数
     * @author zhugl date 2016-08-04
     */
    int getAllEnterpriseBaseCount(String sql, Map<String, Object> paramMap);
    
    /**
     * 平台管理员获取所有企业列表
     * @author zhugl date 2016-08-04
     */
    List<RPCSysEnterpriseBaseVo> getAllEnterpriseBaseList(String sql, Map<String, Object> paramMap, PagingParameter paging);
    
    /**
     * 平台管理员审核，更新相关列表
     * @throws DaoAccessException
     */
    List<String> updateRecords(String ticketId ,String[] enterpriseIds,String eventStatus);


    List<String> updateEnterRefuse(String[] enterpriseIds, String eventStatus,String ticketId);

	List<EnterpriseIndex> enterpriseIndexList(Map<String, Object> paramMap, PagingParameter paging);

	void deleteEnterpriseIndex(String[] enterpriseIds);

}