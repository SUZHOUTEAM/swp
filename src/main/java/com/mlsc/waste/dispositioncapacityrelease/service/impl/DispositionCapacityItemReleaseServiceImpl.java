package com.mlsc.waste.dispositioncapacityrelease.service.impl;

import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityItemReleaseDao;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityItemRelease;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityDetailReleaseService;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityItemReleaseService;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityReleaseService;
import com.mlsc.waste.licence.model.OperationLicenceItem;
import com.mlsc.waste.licence.service.LicenceItemService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("DispositionCapacityItemReleaseService")
public class DispositionCapacityItemReleaseServiceImpl implements DispositionCapacityItemReleaseService {

    private final static Logger logger = LoggerFactory.getLogger(DispositionCapacityItemReleaseServiceImpl.class);

    @Autowired
    private DispositionCapacityItemReleaseDao capacityItemReleaseDao;

    @Autowired
    private DispositionCapacityReleaseService capacityReleaseService;

    @Autowired
    private DispositionCapacityDetailReleaseService capacityDetailReleaseService;

    @Autowired
    private LicenceItemService licenceItemService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveDispositionCapacityItemByLicId(String ticketId, String licenceId, String capacityReleaseId) throws Exception {
        if (StringUtils.isBlank(licenceId) || StringUtils.isBlank(capacityReleaseId)) {
            return;
        }
        capacityReleaseId = capacityReleaseService.getDispositionCapacityReleaseByLicId(licenceId).getId();
        List<OperationLicenceItem> licenceItems = licenceItemService.getOperationLicenceItems(licenceId);
        for (OperationLicenceItem licenceItem : licenceItems) {
            DispositionCapacityItemRelease capacityItem = getDispositionCapacityItemRelease(ticketId, licenceItem, capacityReleaseId);
            String capacityItemReleaseId = Util.uuid32();
            capacityItemReleaseDao.save(capacityItem, capacityItemReleaseId);
            capacityDetailReleaseService.saveDispositionCapacityDetails(ticketId, licenceId, licenceItem.getId(), capacityReleaseId, capacityItemReleaseId);
        }
    }

    private DispositionCapacityItemRelease getDispositionCapacityItemRelease(String ticketId, OperationLicenceItem licenceItem, String capacityReleaseId) throws Exception {
        DispositionCapacityItemRelease capacityItem = new DispositionCapacityItemRelease();
        User userByTicketId = LoginStatusUtils.getUserByTicketId(ticketId);
        capacityItem.setCapacityReleaseId(capacityReleaseId);
        capacityItem.setDispositionType(licenceItem.getDisposition_type());
        capacityItem.setQuotaQuantity(licenceItem.getApproved_quantity());
        capacityItem.setCreateBy(userByTicketId.getUserId());
        capacityItem.setCreateTime(Util.datetimeToString(new Date()));
        capacityItem.setEditBy(userByTicketId.getUserId());
        capacityItem.setEditTime(Util.datetimeToString(new Date()));
        capacityItem.setValid(Constant.IS_VALID);
        return capacityItem;
    }




    @Override
    public DispositionCapacityItemRelease getDispositionCapacityItemReleaseById(String capacityItemReleaseId) {
        DispositionCapacityItemRelease capacityItemRelease = null;
        try {
            capacityItemRelease = capacityItemReleaseDao.get(capacityItemReleaseId);
        } catch (Exception e) {
            logger.error("根据id查询处置能力信息表数据时异常", e);
            throw new RuntimeException(e);
        }
        return capacityItemRelease;
    }


}
