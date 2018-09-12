package com.mlsc.waste.licence.service;

import com.mlsc.waste.licence.model.OperationLicenceDetail;

import java.util.List;
import java.util.Map;

public interface LicenceDetailService {

    /**
     * 根据licence_id和中间表的id删除detail表中的数据
     * @param licenceId
     * @param itemId
     * @throws Exception 
     * @ 
     */
    void removeDetails(String licenceId, String itemId) throws Exception;
    
    void removeDetail(String detailId) throws Exception;
    
    void updateDetailWasteNameId(String detailId,String wasteNameId, String ticketId) throws Exception;
    

    
    /**
     * 保存许可证datails
     * 
     * @param lienceDetails
     * @throws Exception 
     * @ 
     */
    void saveLicenceDetail(List<OperationLicenceDetail> lienceDetails, String ticketId) throws Exception;
    

    /**
     * 判断许可证明细项是否存在
     * @param licenceId
     * @param licenceItemId
     * @param wasteTypeId
     * @param wasteId
     * @param wasteNameId
     * @return
     * @throws Exception
     */
    boolean isHasLicenceDetail(String licenceId, String licenceItemId, String wasteTypeId, String wasteId,String wasteNameId) throws Exception;
    

    /**
     * 根据许可证ID来删除datails(vaild=0)
     * @param licenceIds
     * @param ticketId
     * @throws Exception
     */
    void deleteLicenceDetailByLicenceIds(List<String> licenceIds, String ticketId) throws Exception;
    
    /**
     * 根据licenceId和licenceItemId关联查询出licenceDetail表中的数据
     * @param licenceId
     * @param licenceItemId
     * @return
     */
    List<OperationLicenceDetail> getlicenceDetails(String licenceId, String licenceItemId);
    
    /**
     * 根据licenceId关联查询出licenceDetail表中的数据
     * @param licenceId
     * @return
     */
    List<OperationLicenceDetail> getlicenceDetails(String licenceId);
    
    /**
     * 获取当前危废是否可接收标志
     * 根据当前企业的有效许可证ID，剩余量，二位码，八位码来判断当前危废是否可接收（在表operation_licence_detail）
     * 1:在许可证范围内，0:不在许可证范围内
     * @author zhugl date 2016-07-13
     */
    int checkLicenceStatus(String licenceId, String dispositionType , String planQuantity,String wasteTypeId, String wasteId) ;
    
    /**
     * 获取当前危废的可处置的处置方式
     * 根据当前企业的有效许可证ID，剩余量，二位码，八位码来判断当前危废的可处置的处置方式（在表operation_licence_detail）
     */
    List<Map<String, Object>> listDispositionType(String licenceId, String planQuantity,String wasteTypeId, String wasteId);
    
    /**
     * 根据8位码2位码获取许可证可处置量
     * @param licenceId
     * @param wasteTypeId
     * @param wasteId
     * @return
     */
    String getProcessQuantityByWasteIdAndWasteTypeId(String licenceId,String dispositionType,String wasteTypeId, String wasteId);
    /**
     * 查看许可证是否有效
     * @param licenceId
     * @return
     */
	String getLicenceDetailStatus(String licenceId); 
    
}
