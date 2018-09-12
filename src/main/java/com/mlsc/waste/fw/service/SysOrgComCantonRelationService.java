package com.mlsc.waste.fw.service;

import com.mlsc.waste.fw.model.SysOrgComCantonRelation;

import java.util.List;



/**
 * @author sunjl
 * 
 */
public interface SysOrgComCantonRelationService {
    
    /**
     * 组织机构表的保存
     * 
     * @author zhugl date 2016-06-06
     */
    void saveOrgComCantonRelation(SysOrgComCantonRelation orgComCantonRelation, String ticketId);
    
    /**
     * 组织机构表的更新
     * 
     * @author zhugl date 2016-06-06
     */
    void updateOrgComCantonRelation(SysOrgComCantonRelation orgComCantonRelation, String ticketId);
    
    /**
     * 根据组织机构ID获取区域代码
     * @author zhugl date 2016-08-04
     */
    SysOrgComCantonRelation getSysOrgComCantonRelation(String comId);
    
    /**
     * 根据ID获取区域代码
     * @author zhugl date 2016-08-04
     */
    SysOrgComCantonRelation getSysOrgComCantonRelationByRelId(String relId);
    
    
    /**
     * 根据组织机构代码获取区域代码
     * @author zhugl date 2016-08-04
     */
    List<SysOrgComCantonRelation> getSysOrgComCantonRelationByCantonCode(String cantonCode);
    
}
