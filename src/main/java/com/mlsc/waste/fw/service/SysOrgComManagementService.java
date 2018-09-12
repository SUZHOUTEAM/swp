package com.mlsc.waste.fw.service;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.fw.model.SysOrgCom;

import java.util.List;
import java.util.Map;


/**
 * @author sunjl
 * 
 */
public interface SysOrgComManagementService {

    /**
     * 平台管理员获取所有组织列表件数
     * @author zhugl date 2016-08-04
     */
    int getOrgComCount(String sql, Map<String, Object> paramMap);
    
    /**
     * 平台管理员获取所有组织列表
     * @author zhugl date 2016-08-04
     */
    List<SysOrgCom> getOrgComList(String sql, Map<String, Object> paramMap, PagingParameter paging);
    
    /**
     * 输入机构代码是否重复
     * @author zhugl date 2016-08-04
     */
    String getOrgComName(String orgComId, String orgComCode);
    
    /**
     * 组织机构表,组织机构与区域代码的关系表的保存
     *  @param statusCode 0:正常保存 ；1：行政区域无值;2:所选的区域在sys_canton中无对应数据
     * @author zhugl date 2016-06-06
     */
    String saveOrgComAndCantonRelation(SysOrgCom orgCom, String cantonCode, String ticketId);
    
    /**
     * 组织机构表,组织机构与区域代码的关系表的保存
     *  @param statusCode 0:正常保存 ；1：行政区域无值;2:所选的区域在sys_canton中无对应数据
     * @author zhugl date 2016-06-06
     */
    String updateOrgComAndCantonRelation(SysOrgCom orgCom, String relId, String cantonCode, String ticketId);
    
    /**
     * 根据区域code获取区域名称
     * 
     * @author zhugl date 2016-06-06
     */
    Map<String, String> getCantonValue(String cantonCode, String ticketId);
    
    
    /**
     * 数据同步时，保存环保局信息
     *  @param orgComName 组织机构名称
     *  @param orgComCode 组织机构所在的象征区域代码
     * @author zhugl date 2016-06-06
     */
    String saveOrgComAndCantonRelation(String orgComName, String orgComCode);
}
