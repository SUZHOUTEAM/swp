package com.mlsc.waste.licence.service.impl;

import com.mlsc.waste.licence.dao.LicenceItemDao;
import com.mlsc.waste.licence.model.OperationLicenceItem;
import com.mlsc.waste.licence.service.LicenceItemService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("LicenceItemService")
public class LicenceItemServiceImpl implements LicenceItemService{
    
    @Autowired
    private LicenceItemDao licenceItemDao;
    
    @Override
    public OperationLicenceItem getOperationLicenceItemByItemId(String itemId) throws Exception{
        return licenceItemDao.get(itemId);
    }
    
    @Override
    public void removeItemById(String itemId) throws Exception  {
        licenceItemDao.delete(itemId);
    }

    @Override
    public String saveLicenceItem(OperationLicenceItem operationLicenceItem, String ticketId) throws Exception {
        String itemid = null;
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        operationLicenceItem.setValid(Constant.IS_VALID);
        operationLicenceItem.setCreate_by(user.getUserId());
        operationLicenceItem.setCreate_time(Util.datetimeToString(new Date()));
        operationLicenceItem.setEdit_by(user.getUserId());
        operationLicenceItem.setEdit_time(Util.datetimeToString(new Date()));
        itemid = Util.uuid32();
        licenceItemDao.save(operationLicenceItem, itemid);
        return itemid;
    }
    
    @Override
    public String isHasDispositionItem(String licenceId, String dispositionTypeId) throws Exception {
        OperationLicenceItem query = new OperationLicenceItem();
        
        query.setLicence_id(licenceId);
        query.setDisposition_type(dispositionTypeId);
        query.setValid(Constant.IS_VALID);
        
        List<OperationLicenceItem> queryList = licenceItemDao.query(query);
        if (queryList == null || queryList.size() == 0) {
            return null;
        } else {
            return queryList.get(0).getId();
        }
    }
    
    @Override
    public void updateLicenceItem(OperationLicenceItem item, String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        
        OperationLicenceItem updateItem = getOperationLicenceItemByItemId(item.getId());
        updateItem.setDisposition_type(item.getDisposition_type());
        updateItem.setApproved_quantity(item.getApproved_quantity());
        updateItem.setEdit_by(user.getUserId());
        updateItem.setEdit_time(Util.datetimeToString(new Date()));
        licenceItemDao.update(updateItem);
    }

    @Override
    public void deleteLicenceItem(List<String> licenceIds, String ticketId) throws Exception {
        if (licenceIds == null || licenceIds.isEmpty()) {
            return ;
        }
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("valid", Constant.IS_NOT_VALID);
        paraMap.put("licenceIds", licenceIds);
        paraMap.put("editBy", user.getUserId());
        paraMap.put("editTime", Util.datetimeToString(new Date()));
        licenceItemDao.deleteLicenceItemByLicenceIds(licenceIds, paraMap);
    }

    @Override
    public List<OperationLicenceItem> getOperationLicenceItems(String licenceId) throws Exception{
        OperationLicenceItem item = new OperationLicenceItem();
        item.setLicence_id(licenceId);
        item.setValid(Constant.IS_VALID);
        return licenceItemDao.query(item);
    }
}
