/**
 * 
 */
package com.mlsc.waste.enterprise.service;

import com.mlsc.waste.enterprise.model.WasteEnterpriseType;

import java.util.List;

/**
 * @author zhugl
 *
 */
public interface WasteEnterpriseTypeService {
    
    /**
     * 企业类型记录的保存
     * 
     * @author zhugl date 2016-08-03
     */
    String[] saveWasteEnterpriseTypes(List<WasteEnterpriseType> records, String ticketId);
    
    /**
     * 通过企业ID来查询企业拥有的企业类型
     * 
     * @author zhugl date 2016-08-03
     */
    List<WasteEnterpriseType> listWasteEnterpriseTypesByEnterpriseId(String enterpriseId);
    
    /**
     * 删除企业类型
     * @param enterPriseId
     */
    void deleteById(String enterPriseId);

}
