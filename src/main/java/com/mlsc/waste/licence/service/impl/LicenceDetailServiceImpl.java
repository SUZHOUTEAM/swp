package com.mlsc.waste.licence.service.impl;

import com.mlsc.waste.licence.dao.LicenceDetailDao;
import com.mlsc.waste.licence.model.OperationLicenceDetail;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("LicenceDetailService")
public class LicenceDetailServiceImpl implements LicenceDetailService{

    private final static Logger logger = LoggerFactory.getLogger(LicenceDetailServiceImpl.class);

    @Autowired
    private LicenceDetailDao licenceDetailDao;
    
    @Override
    public void removeDetails(String licence_id, String itemId) throws Exception {
        licenceDetailDao.deleteDetails(licence_id, itemId);
    }
    @Override
    public void removeDetail(String detailId) throws Exception {
        licenceDetailDao.delete(detailId);
    }
    
    @Override
    public void updateDetailWasteNameId(String detailId,String wasteNameId,String ticketId) throws Exception {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        OperationLicenceDetail updateDetail = licenceDetailDao.get(detailId);
        updateDetail.setWaste_name_id(wasteNameId);
        updateDetail.setEdit_by(user.getUserId());
        updateDetail.setEdit_time(Util.datetimeToString(new Date()));
        licenceDetailDao.update(updateDetail);
    }

    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveLicenceDetail(List<OperationLicenceDetail> lienceDetails, String ticketId) throws Exception {
        if (lienceDetails == null) {
            return ;
        }
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String[] idlist = new String[lienceDetails.size()];
        int index = 0;
        for (OperationLicenceDetail detail:lienceDetails) {
            detail.setValid(Constant.IS_VALID);
            detail.setCreate_by(user.getUserId());
            detail.setCreate_time(Util.datetimeToString(new Date()));
            detail.setEdit_by(user.getUserId());
            detail.setEdit_time(Util.datetimeToString(new Date()));
            idlist[index++] = Util.uuid32();
        }
        if (lienceDetails.size() > 0) {
            licenceDetailDao.saves(lienceDetails, idlist);
        }
    }
    
    @Override
    public boolean isHasLicenceDetail(String licenceId, String licenceItemId, String wasteTypeId, String wasteId,String wasteNameId) throws Exception {
       int count = licenceDetailDao.isHasLicenceDetail(licenceId, licenceItemId, wasteTypeId, wasteId, wasteNameId);
       return count > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteLicenceDetailByLicenceIds(List<String> licenceIds, String ticketId) throws Exception {
        if (licenceIds == null || licenceIds.isEmpty()) {
            return ;
        }
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("valid", Constant.IS_NOT_VALID);
        paraMap.put("licenceIds", licenceIds);
        paraMap.put("editBy", user.getUserId());
        paraMap.put("editTime", Util.datetimeToString(new Date()));
        licenceDetailDao.deleteLicenceDetailByLicenceIds(licenceIds, paraMap);
    }

    @Override
    public List<OperationLicenceDetail> getlicenceDetails(String licenceId, String licenceItemId) {
        if (StringUtils.isBlank(licenceId) || StringUtils.isBlank(licenceItemId)) {
            return null;
        }
        List<OperationLicenceDetail> licenceDetails = null;
        try {
            OperationLicenceDetail detail = new OperationLicenceDetail();
            detail.setLicence_id(licenceId);
            detail.setOperation_item_id(licenceItemId);
            detail.setValid(Constant.IS_VALID);
            licenceDetails = licenceDetailDao.query(detail);
        } catch (Exception e) {
            logger.error("根据licenceId和dispositionType查询licenceDetails时异常",e);
            throw new RuntimeException(e);
        }
        return licenceDetails;
    }
    
    @Override
    public List<OperationLicenceDetail> getlicenceDetails(String licenceId) {
        if (StringUtils.isBlank(licenceId)) {
            return null;
        }
        List<OperationLicenceDetail> licenceDetails = null;
        try {
            OperationLicenceDetail detail = new OperationLicenceDetail();
            detail.setLicence_id(licenceId);
            detail.setValid(Constant.IS_VALID);
            licenceDetails = licenceDetailDao.query(detail);
        } catch (Exception e) {
            logger.error("根据licenceId查询licenceDetails时异常",e);
            throw new RuntimeException(e);
        }
        return licenceDetails;
    }
    
    @Override
    public int checkLicenceStatus(String licenceId, String dispositionType,String planQuantity,String wasteTypeId, String wasteId) {
        int checkStatus = 0 ;
        planQuantity = planQuantity.replaceAll(",", "");

        try{
            checkStatus = licenceDetailDao.checkLicenceStatus(licenceId,dispositionType, wasteTypeId, wasteId);
        }catch(EmptyResultDataAccessException e){
            checkStatus = 0;
            logger.error("许可证失效",e);
        }catch(Exception e){
            logger.error("判断许可证是否处置危废时异常",e);
            throw new RuntimeException(e);
        }
        return checkStatus;
    }
    
    @Override
    public List<Map<String, Object>> listDispositionType(String licenceId, String planQuantity,String wasteTypeId, String wasteId) {
        List<Map<String, Object>> result = null;
        try{
            result = licenceDetailDao.listDispositionType(licenceId, StringUtils.isBlank(planQuantity)?"":planQuantity.replaceAll(",", ""), wasteTypeId, wasteId);
        }catch(EmptyResultDataAccessException e){
            result = null;
        }catch(Exception e){
            logger.error("判断许可证是否处置危废时异常",e);
            throw new RuntimeException(e);
        }
        return result;
    }
    
    @Override
    public String getProcessQuantityByWasteIdAndWasteTypeId(String licenceId, String dispositionType,String wasteTypeId, String wasteId) {
        String processQuantity = "";

        try{
            processQuantity = licenceDetailDao.getProcessQuantityByWasteIdAndWasteTypeId(licenceId,dispositionType,  wasteTypeId, wasteId);
        }catch(Exception e){
            logger.error("根据8位码2位码获取许可证可处置量时异常",e);
            throw new RuntimeException(e);
        }
        return processQuantity;
    }
    
    @Override
    public String getLicenceDetailStatus(String licenceId) {
        String processQuantity = "";

        try{
            processQuantity = licenceDetailDao.getLicenceDetailStatus(licenceId);
        }catch(Exception e){
            logger.error("获取许可证状态时异常",e);
            throw new RuntimeException(e);
        }
        return processQuantity;
    }
}
