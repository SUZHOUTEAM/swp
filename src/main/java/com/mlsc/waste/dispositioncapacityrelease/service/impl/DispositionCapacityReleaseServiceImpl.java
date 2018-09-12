package com.mlsc.waste.dispositioncapacityrelease.service.impl;

import com.mlsc.yifeiwang.sms.model.ReleaseStatus;
import com.mlsc.yifeiwang.codedirectory.service.ICodeValueService;
import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityReleaseDao;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityRelease;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityItemReleaseService;
import com.mlsc.waste.dispositioncapacityrelease.service.DispositionCapacityReleaseService;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("DispositionCapacityReleaseService")
public class DispositionCapacityReleaseServiceImpl implements DispositionCapacityReleaseService {

    private final static Logger logger = LoggerFactory.getLogger(DispositionCapacityReleaseServiceImpl.class);


    @Autowired
    private DispositionCapacityReleaseDao dispositionCapacityReleaseDao;

    @Autowired
    private LicenceService licenceService;


    @Autowired
    private DispositionCapacityItemReleaseService itemReleaseService;

    @Autowired
    private ICodeValueService codevalueService;



    /**
     * 根据许可证id批量增加处置能力信息
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveDispositionCapacityByLicIds(String ticketId, List<String> licenceIds) throws Exception {
        if (licenceIds == null || licenceIds.isEmpty()) {
            return;
        }
        DispositionCapacityRelease capacityRelease = null;
        for (String licenceId : licenceIds) {
            capacityRelease = getDispositionCapacityRelease(ticketId, licenceId);
            String capacityReleaseId = Util.uuid32();
            dispositionCapacityReleaseDao.save(capacityRelease, capacityReleaseId);
            itemReleaseService.saveDispositionCapacityItemByLicId(ticketId, licenceId, capacityReleaseId);
        }
    }

    private DispositionCapacityRelease getDispositionCapacityRelease(String ticketId, String licenceId) throws Exception {
        DispositionCapacityRelease capacityRelease = new DispositionCapacityRelease();
        OperationLicence licence = licenceService.getLicneceById(licenceId);
        User userByTicketId = LoginStatusUtils.getUserByTicketId(ticketId);
        capacityRelease.setReleaseEnterpriseId(licence.getEnterprise_id());// 企业ID
        capacityRelease.setCapacityReleaseCode(Util.getRreleaseCode());// 编号存时间戳
        capacityRelease.setReleaseStartdate(licence.getStart_date());//
        capacityRelease.setReleaseEnddate(licence.getEnd_date());
        capacityRelease.setOperationLicenceId(licenceId);
        capacityRelease.setRemark(null);
        capacityRelease.setVersionCode(Util.datetimeToString(new Date()));
        capacityRelease.setCreateBy(userByTicketId.getUserId());
        capacityRelease.setCreateTime(Util.datetimeToString(new Date()));
        capacityRelease.setEditBy(userByTicketId.getUserId());
        capacityRelease.setEditTime(Util.datetimeToString(new Date()));
        capacityRelease.setValid(Constant.IS_VALID);
        capacityRelease.setReleaseStatus(ReleaseStatus.NORMAL.getStatusCode());
        return capacityRelease;
    }

    /**
     * 根据许可证id查询出 处置能力信息表 数据
     */
    @Override
    public DispositionCapacityRelease getDispositionCapacityReleaseByLicId(String licId) {
        DispositionCapacityRelease capacityRelease = null;
        try {
            capacityRelease = dispositionCapacityReleaseDao.getDispositionCapacityReleaseByLicId(licId);
        } catch (Exception e) {
            logger.error("根据许可证id查询处置能力信息表数据失败", e);
            throw new RuntimeException(e);
        }
        return capacityRelease;
    }

    @Override
    public DispositionCapacityRelease getDispositionCapacityReleaseById(String capacityReleaseId) {
        DispositionCapacityRelease capacityRelease = null;
        try {
            capacityRelease = dispositionCapacityReleaseDao.get(capacityReleaseId);
        } catch (Exception e) {
            logger.error("根据id查询处置能力信息表数据时异常", e);
            throw new RuntimeException(e);
        }
        return capacityRelease;
    }

    @Override
    public void updateDispositionCapacityRelease(DispositionCapacityRelease release, User user) {
        try {
            release.setEditBy(user.getUserId());
            release.setEditTime(Util.datetimeToString(new Date()));
            dispositionCapacityReleaseDao.update(release);
        } catch (Exception e) {
            logger.error("更新处置能力发布时出错", e);
            throw new RuntimeException(e);
        }

    }
}
