package com.mlsc.waste.userenterpriseapproverecord.service.impl;

import com.mlsc.waste.user.model.User;
import com.mlsc.waste.userenterpriseapproverecord.dao.UserEnterpriseApproveRecordDao;
import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecord;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.service.UserEnterpriseApproveRecordService;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhugl
 */
@Service("UserEnterpriseApproveRecordService")
public class UserEnterpriseApproveRecordServiceImpl implements UserEnterpriseApproveRecordService {
    private final static Logger logger = LoggerFactory.getLogger(UserEnterpriseApproveRecordServiceImpl.class);

    @Autowired
    private UserEnterpriseApproveRecordDao recordDao;
    

    @Override
    public String saveUserEnterpriseApproveRecord(UserEnterpriseApproveRecord record, String ticketId) {
        String recordId = Util.uuid32();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            record.setValid(Constant.IS_VALID);
            record.setCreateBy(user.getUserId());
            record.setCreateTime(Util.datetimeToString(new Date()));
            record.setEditBy(user.getUserId());
            record.setEditTime(Util.datetimeToString(new Date()));
            recordDao.save(record, recordId);
        } catch (Exception e) {
            logger.error("用户/企业关系审核记录保存时异常",e);
            throw new RuntimeException(e);
        }
        return recordId;
        
    }
    
    @Override
    public void updateUserEnterpriseApproveRecord(UserEnterpriseApproveRecord record, String ticketId) {
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            record.setEditBy(user.getUserId());
            record.setEditTime(Util.datetimeToString(new Date()));
            recordDao.update(record);
        } catch (Exception e) {
            logger.error("用户/企业关系审核记录更新时异常",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserEnterpriseApproveRecord getUserEnterpriseApproveRecordById(String recordId) {
        UserEnterpriseApproveRecord record = null;
        try {
            record = recordDao.get(recordId);
        } catch (Exception e) {
            logger.error("查询用户/企业关系审核记录时异常",e);
            throw new RuntimeException(e);
        }
        
        return record;
    }

    @Override
    public List<UserEnterpriseApproveRecordVo> getUserEnterpriseApproveRecordVos(
            String userId,String entId, String[] eventType, String[] eventStatus) {
        List<UserEnterpriseApproveRecordVo> recordListCodeVo = null;
        try {
            recordListCodeVo = recordDao.getUserEnterpriseApproveRecordVos(userId,entId,eventType, eventStatus);
        } catch (Exception e) {
            logger.error("查询用户/企业关系审核记录列表时异常",e);
            throw new RuntimeException(e);
        }
        
        return recordListCodeVo;
    }

    @Override
    public List<AuditUserRecordVo> getUserEnterpriseApproveRecords(
            String userEventType, String userEventStatus, String enterpriseId) {
        List<AuditUserRecordVo> recordList = null;
        try {
            recordList = recordDao.getUserEnterpriseApproveRecords(userEventType,userEventStatus,enterpriseId);
        } catch (Exception e) {
            logger.error("查询用户/企业关系审核记录列表时异常",e);
            throw new RuntimeException(e);
        }
        return recordList;
    }

    @Override
    public void updateRecordByEnpId(String enterpriseId, String userEventStatus) {
        try {
            recordDao.updateRecordByEnpId(enterpriseId,userEventStatus);
        } catch (Exception e) {
            logger.error("平台管理员审核新加入企业异常",e);
            throw new RuntimeException(e);
        }    
    }
}
