package com.mlsc.waste.licence.service;

import com.mlsc.waste.licence.model.OperationLicenceItem;

import java.util.List;

public interface LicenceItemService {
    
    OperationLicenceItem getOperationLicenceItemByItemId(String itemId) throws Exception;

    void removeItemById(String itemId)throws Exception;

    String saveLicenceItem(OperationLicenceItem operationLicenceItem, String ticketId) throws Exception;
    
    String isHasDispositionItem(String licenceId, String dispositionTypeId) throws Exception;
    
    void updateLicenceItem(OperationLicenceItem operationLicenceItem, String ticketId) throws Exception;
    
    List<OperationLicenceItem> getOperationLicenceItems(String licenceId) throws Exception;
    
    /**
     * 根据许可证ID来删除operationLicenceItem(vaild=0) zhugl
     * 
     * @param operationLicenceDetail
     * @throws Exception 
     * @ 
     */
    void deleteLicenceItem(List<String> licenceIds, String ticketId) throws Exception;
    
}
