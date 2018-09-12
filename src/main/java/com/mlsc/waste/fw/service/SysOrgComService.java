package com.mlsc.waste.fw.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.waste.fw.model.SysOrgCom;

import java.util.List;
import java.util.Map;


/**
 * @author sunjl
 * 
 */
public interface SysOrgComService {
    
    /**
     * 平台管理员获取所有组织机构列表件数
     * @author zhugl date 2016-08-04
     */
    int getOrgComCount(String sql, Map<String, Object> paramMap);
    
    /**
     * 平台管理员获取所有组织机构列表
     * @author zhugl date 2016-08-04
     */
    List<SysOrgCom> getOrgComList(String sql, Map<String, Object> paramMap, PagingParameter paging);
    
    /**
     * 组织机构表的保存
     * 
     * @author zhugl date 2016-06-06
     */
    void saveOrgCom(SysOrgCom orgCom, String ticketId);
    
    /**
     * 组织机构表的保存
     * 
     * @author zhugl date 2016-06-06
     */
    void updateOrgCom(SysOrgCom orgCom, String ticketId);
    
    /**
     * 根据Id和code去机构名称
     * @author zhugl date 2016-08-04
     */
    String getOrgComName(String orgComId, String orgComCode);
    
    /**
     * 根据ID获取组织机构
     * @author zhugl date 2016-08-04
     */
    SysOrgCom getOrgCom(String comId);
    
    /**
     * 根据区域代码获取组织机构
     * @author zhugl date 2016-08-04
     */
    List<RPCSysOrgCom> querySysOrgComListByCantonID(String ticketId, int comType, String cantonCode);
    
    /**
     * 根据区域代码获取组织机构
     * @author zhugl date 2016-08-04
     */
    List<RPCSysOrgCom> querySysOrgComListByParentCantonID(String ticketId, int comType, String cantonCode);
    
 
}
