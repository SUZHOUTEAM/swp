package com.mlsc.waste.wastedirectory.service.impl;

import com.mlsc.waste.licence.dao.LicenceDao;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.dao.WasteNameDao;
import com.mlsc.waste.wastedirectory.model.WasteName;
import com.mlsc.waste.wastedirectory.service.WasteNameService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author sunjl
 */
@Service
public class WasteNameServiceImpl implements WasteNameService {
    @Autowired
    private WasteNameDao wasteNameDao;
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;
    @Autowired
    private LicenceDao licenceDao;

    private Logger logger = Logger.getLogger(WasteNameServiceImpl.class);

    @Override
    public String saveWasteName(WasteName wasteName, String ticketId) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        wasteName.setStatus(Constant.ENABLED);
        setWasteNameInfoByUser(wasteName, user);
        String idString = Util.uuid32();
        try {
            wasteNameDao.save(wasteName, idString);
        } catch (Exception e) {
            logger.error("危废名称保存异常", e);
            throw new RuntimeException(e);
        }
        return idString;
    }

    @Override
    public List<WasteName> getWasteNamesByWasteId(String waste_id) {
        List<WasteName> wasteNames = null;
        try {
            wasteNames = wasteNameDao.queryByWasteId(waste_id);
        } catch (Exception e) {
            logger.error("危废名称list查询异常", e);
            throw new RuntimeException(e);
        }

        return wasteNames;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removeWasteNamesByIds(List<String> ids) {
        try {
            wasteNameDao.deletes(ids);
        } catch (Exception e) {
            logger.error("危废名称删除异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public WasteName getWasteNameById(String id) {
        WasteName wasteName = null;
        try {
            wasteName = wasteNameDao.get(id);
        } catch (Exception e) {
            logger.error("危废名称查询异常", e);
            throw new RuntimeException(e);
        }

        return wasteName;
    }

    @Override
    public List<WasteName> getWasteNamesByNameAndWasteid(String wasteid, String name, String model) {
        List<WasteName> wasteNames = null;
        try {
            wasteNames = wasteNameDao.queryByNameAndWasteid(wasteid, name, model);
        } catch (Exception e) {
            logger.error("危废名称list查询异常", e);
            throw new RuntimeException(e);
        }
        return wasteNames;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveWasteNames(List<WasteName> wasteNames, String ticketId) {
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            String[] wasteNameidlist = new String[wasteNames.size()];
            int index = 0;
            if (wasteNames.size() > 0) {
                for (WasteName wasteName : wasteNames) {
                    setWasteNameInfoByUser(wasteName, user);
                    wasteName.setStatus(Constant.ENABLED);
                    wasteNameidlist[index++] = Util.uuid32();
                }
            }
            wasteNameDao.saves(wasteNames, wasteNameidlist);
        } catch (Exception e) {
            logger.error("危废名称信息保存异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateWasteNames(List<WasteName> wasteNames, String ticketId) {
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            if (wasteNames.size() > 0) {
                for (WasteName wasteName : wasteNames) {
                    setWasteNameInfoByUser(wasteName, user);
                }
            }
            wasteNameDao.updates(wasteNames);
        } catch (Exception e) {
            logger.error("危废名称信息更新异常", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeWasteNamesByWasteId(String wasteId) {
        try {
            wasteNameDao.deleteByWasteId(wasteId);
            licenceDao.updateEditTimeByWasteId(wasteId);
        } catch (Exception e) {
            logger.error("通过八位码来危废名称信息时异常", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public String saveOrUpdateWasteName(String wasteId, String wasteName, String ticketId) throws Exception {
        String wastNameId = null;

        WasteName queryWasteName = new WasteName();
        queryWasteName.setWaste_id(wasteId);
        queryWasteName.setName(wasteName);
        queryWasteName.setStatus(Constant.ENABLED);
        List<WasteName> lsitList = wasteNameDao.query(queryWasteName);

        if (lsitList != null && lsitList.size() > 0) {
            wastNameId = lsitList.get(0).getId();
        } else {
            wastNameId = saveWasteName(queryWasteName, ticketId);
        }

        return wastNameId;
    }

    // 抽取的方法//////////////////////////////////
    private WasteName setWasteNameInfoByUser(WasteName wasteName, User user) {
        wasteName.setCreate_by(user.getUserId());
        wasteName.setCreate_time(Util.datetimeToString(new Date()));
        wasteName.setEdit_by(user.getUserId());
        wasteName.setEdit_time(Util.datetimeToString(new Date()));
        return wasteName;
    }

}
