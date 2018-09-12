package com.mlsc.waste.dispositioncapacityrelease.service.impl;

import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityDetailReleaseDao;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityDetailRelease;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityDetailReleaseService;
import com.mlsc.waste.licence.model.OperationLicenceDetail;
import com.mlsc.waste.licence.service.LicenceDetailService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("DispositionCapacityDetailReleaseService")
public class DispositionCapacityDetailReleaseServiceImpl implements DispositionCapacityDetailReleaseService {

    private final static Logger logger = LoggerFactory.getLogger(DispositionCapacityDetailReleaseServiceImpl.class);

    @Autowired
    private DispositionCapacityDetailReleaseDao detailReleaseDao;

    @Autowired
    private LicenceDetailService licenceDetailService;

    @Autowired
    private ICodeValueService codeValueService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveDispositionCapacityDetails(String ticketId, String licenceId, String licenceItemId, String capacityReleaseId, String capacityItemReleaseId) throws Exception {
        if (StringUtils.isBlank(licenceId) || StringUtils.isBlank(licenceItemId) || StringUtils.isBlank(capacityReleaseId) || StringUtils.isBlank(capacityItemReleaseId)) {
            return;
        }
        String releaseStatusId = codeValueService.getCodeValueByCode(CodeTypeConstant.DISCAPACITY_RTYPE, CodeTypeConstant.DISCAPACITY_RTYPE_RELEASE).getId();
        List<OperationLicenceDetail> licenceDetailList = licenceDetailService.getlicenceDetails(licenceId, licenceItemId);
        List<String> capacityDetailuuids = new ArrayList<String>();
        DispositionCapacityDetailRelease capacityDetail = null;
        List<DispositionCapacityDetailRelease> capacityDetails = new ArrayList<DispositionCapacityDetailRelease>();
        for (OperationLicenceDetail licenceDetail : licenceDetailList) {
            capacityDetail = getDispositionCapacityDetailRelease(ticketId, licenceDetail, capacityReleaseId, capacityItemReleaseId);
            capacityDetail.setReleaseStatus(releaseStatusId);

            capacityDetailuuids.add(Util.uuid32());
            capacityDetails.add(capacityDetail);
        }
        detailReleaseDao.saves(capacityDetails, capacityDetailuuids.toArray());
    }

    private DispositionCapacityDetailRelease getDispositionCapacityDetailRelease(String ticketId, OperationLicenceDetail licenceDetail, String capacityReleaseId, String capacityItemReleaseId) throws Exception {
        DispositionCapacityDetailRelease capacityDetail = new DispositionCapacityDetailRelease();
        User userByTicketId = LoginStatusUtils.getUserByTicketId(ticketId);

        capacityDetail.setCapacityReleaseId(capacityReleaseId);
        capacityDetail.setCapacityitemId(capacityItemReleaseId);
        capacityDetail.setWasteId(licenceDetail.getWaste_id());
        if (StringUtils.isNotBlank(licenceDetail.getWaste_name_id())) {
            capacityDetail.setWasteNameId(licenceDetail.getWaste_name_id());
        }
        capacityDetail.setUsedQuantity("0");
        capacityDetail.setCreateBy(userByTicketId.getUserId());
        capacityDetail.setCreateTime(Util.datetimeToString(new Date()));
        capacityDetail.setEditBy(userByTicketId.getUserId());
        capacityDetail.setEditTime(Util.datetimeToString(new Date()));
        capacityDetail.setValid(Constant.IS_VALID);
        return capacityDetail;
    }



    @Override
    public DispositionCapacityDetailRelease getDispositionCapacityDetailReleaseById(String detailReleaseId) {
        DispositionCapacityDetailRelease capacityDetailRelease = null;
        try {
            capacityDetailRelease = detailReleaseDao.get(detailReleaseId);
        } catch (Exception e) {
            logger.error("根据id查询处置能力信息表数据时异常", e);
            throw new RuntimeException(e);
        }
        return capacityDetailRelease;
    }


}
