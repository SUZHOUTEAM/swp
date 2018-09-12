package com.mlsc.waste.fw.service;

import com.mlsc.waste.fw.model.SysCanton;

import java.util.List;
import java.util.Map;



/**
 * @author sunjl
 * 
 */
public interface SysCantonService {
    
    /**
     * 根据区域代码获取组织机构
     * @author zhugl date 2016-08-04
     */
    SysCanton querySysCantonByCantonCode(String ticketId, String cantonCode);
    
    /**
     * 根据父行政区代码获取行政区
     * @author zhugl date 2016-08-04
     */
    List<SysCanton> querySysCantonListByParentCantonCode(String parentCantonCode);
    
    /**
     * city-picker数据源获取
     * @author zhugl date 2016-08-04
     */
    Map<String, Object> getProvinceDistricts(String ticketId);
    
    /**
     * city-picker数据源获取
     * @author zhugl date 2016-08-04
     */
    Map<String, Object> getCityDistricts(String parentCantonCode, String ticketId);

	SysCanton queryCantonNameByEnterpriseId(String enterId);

    SysCanton queryCantonNameByCantonCode(String cantonCode);
}
