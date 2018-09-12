/**
 * 
 */
package com.mlsc.waste.wastedirectory.service;

import com.mlsc.waste.wastedirectory.model.WasteName;

import java.util.List;

/**
 * @author sunjl
 *
 */
public interface WasteNameService {
    
    /**
     * 危废名称的新增
     * 
     * @author sunjl date 2016-06-07
     */
    String saveWasteName(WasteName wasteName,String ticketId);
     
     /**
      * 危废名称的查找
      * @param id
      * @return
      */
    List<WasteName> getWasteNamesByWasteId(String wasteId);
     

     /**
      * 危废名称的删除
      * @param ids
      */
    void removeWasteNamesByIds(List<String> ids);
    
    /**
     * 危废名称的删除(通过wasteid删除)
     * @param ids
     */
    void removeWasteNamesByWasteId(String wasteId);
     
     /**
      * 危废名称的查找
      * @param id
      * @return
      */
    WasteName getWasteNameById(String id);
     
    /**
     * 根据八位码，危废名称模糊查询
     * @param wasteid
     * @param name
     * @return
     */
    List<WasteName> getWasteNamesByNameAndWasteid(String wasteid,String name,String model);
    
    /**
     * 危废名称的新增
     * 
     * @author zhugl date 2016-08-01
     */
    void saveWasteNames(List<WasteName> wasteNames, String ticketId);
    
    /**
     * 危废名称的更新
     * 
     * @author zhugl date 2016-08-01
     */
    void updateWasteNames(List<WasteName> wasteNames, String ticketId);
    
    /**
     * 根据八位码和危废名称查询
     * 
     * @author zhugl date 2016-08-01
     */

    String saveOrUpdateWasteName(String wasteId,String wasteName,String ticketId) throws Exception;
    

}
