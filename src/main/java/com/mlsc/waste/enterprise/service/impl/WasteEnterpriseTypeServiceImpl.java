package com.mlsc.waste.enterprise.service.impl;

import com.mlsc.waste.enterprise.dao.EnterpriseTypeDao;
import com.mlsc.waste.enterprise.model.WasteEnterpriseType;
import com.mlsc.waste.enterprise.service.WasteEnterpriseTypeService;
import com.mlsc.waste.user.model.User;
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
@Service("WasteEnterpriseTypeService")
public class WasteEnterpriseTypeServiceImpl implements WasteEnterpriseTypeService {

    @Autowired
    private EnterpriseTypeDao enterpriseTypeDao;

    private final static Logger logger = LoggerFactory.getLogger(WasteEnterpriseTypeServiceImpl.class);

    /**
     * 保存企业类型
     */
    @Override
    public String[] saveWasteEnterpriseTypes(List<WasteEnterpriseType> records,String ticketId) {
        String[] idlist = new String[records.size()];
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            int index = 0;
            for (WasteEnterpriseType record : records) {
                record.setValid(Constant.IS_VALID);
                record.setCreateBy(user.getUserId());
                record.setCreateTime(Util.datetimeToString(new Date()));
                record.setEditBy(user.getUserId());
                record.setEditTime(Util.datetimeToString(new Date()));
                idlist[index++] = Util.uuid32();
            }
            if (records.size() > 0) {
                enterpriseTypeDao.saves(records, idlist);
            }
        } catch (Exception e) {
            logger.error("企业类型记录保存异常",e);
            throw new RuntimeException(e);
        }
        return idlist;
    }

    /**
     * 根据企业id获取企业类型
     * @throws Exception 
     */
    @Override
    public List<WasteEnterpriseType> listWasteEnterpriseTypesByEnterpriseId(String enterpriseId){
        List<WasteEnterpriseType> recordlist = null;
        WasteEnterpriseType record = new WasteEnterpriseType();
        record.setEnterpriseId(enterpriseId);
        try {
            recordlist = enterpriseTypeDao.query(record);
        } catch (Exception e) {
            logger.error("根据企业id获取企业类型时异常",e);
            throw new RuntimeException(e);
        }
        return recordlist;
    }


    /**
     * 删除企业类型
     */
    @Override
    public void deleteById(String enterPriseId) {
        try {
            enterpriseTypeDao.delete(enterPriseId);
        } catch (Exception e) {
            logger.error("企业类型删除时异常",e);
            throw new RuntimeException(e);
        }
    }

}