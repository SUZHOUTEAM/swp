package com.mlsc.waste.wastedirectory.service.impl;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.solr.SimpleSolr;
import com.mlsc.yifeiwang.codedirectory.service.ICodeTypeService;
import com.mlsc.waste.licence.dao.LicenceDao;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.dao.WasteDao;
import com.mlsc.waste.wastedirectory.model.Waste;
import com.mlsc.waste.wastedirectory.model.WasteName;
import com.mlsc.waste.wastedirectory.model.WasteTypeVo;
import com.mlsc.waste.wastedirectory.service.WasteNameService;
import com.mlsc.waste.wastedirectory.service.WasteService;
import com.mlsc.waste.wastedirectory.service.WasteTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sunjl 危废（八位码）
 */
@Service
public class WasteServiceImpl implements WasteService {

    @Autowired
    private WasteDao wasteDao;

    @Autowired
    private WasteTypeService wasteTypeService;

    @Autowired
    private WasteNameService wasteNameService;

    @Autowired
    private ICodeTypeService codeTypeService;

    @Autowired
    private LicenceDao licenceDao;

    private final static Logger logger = LoggerFactory.getLogger(WasteServiceImpl.class);

    /**
     * 危废类名录的删除
     *
     * @author sunjl date 2016-06-07
     */
    public void delete(String[] ids) {
        try {
            for (String id : ids) {
                wasteDao.delete(id);
            }
        } catch (DaoAccessException e) {
            logger.error("危废名录删除失败！", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSataus(List<String> ids, String status, String ticketId) throws DaoAccessException {
        List<Waste> wasteList = new ArrayList<Waste>();
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        Waste waste = null;
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                waste = getWasteById(id);
                if (waste != null) {
                    waste.setStatus(status);
                    waste.setEdit_by(user.getUserId());
                    waste.setEdit_time(Util.datetimeToString(new Date()));
                    wasteList.add(waste);
                }
            }
            wasteDao.updates(wasteList);
        }
    }

    @Override
    public Waste getWasteById(String id) {
        try {
            return wasteDao.get(id);
        } catch (DaoAccessException e) {
            logger.error("危废名录根据Id查询失败！", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Waste> list(String sql, Map<String, Object> params, PagingParameter paging) throws DaoAccessException {
        List<Waste> wastelist = null;
        wastelist = wasteDao.list(sql, params, paging);
        return wastelist;
    }

    /**
     * 通过危废代码来查询数据的件数
     *
     * @author sunjl date 2016-06-15
     */
    @Override
    public Waste getWasteByCode(String code) throws DaoAccessException {
        return wasteDao.queryByCode(code);
    }

    @Override
    public Integer count(String where, Map<String, Object> params) throws DaoAccessException {
        int size = 0;
        size = wasteDao.getCount(where, params);
        return size;
    }

    @Override
    public List<Waste> getWastesByWasteTypeId(String wasteTypeId) {
        List<Waste> list = null;
        try {
            list = wasteDao.queryByWasteTypeId(wasteTypeId);
        } catch (Exception e) {
            logger.error("危废名录根据typeid查询失败！", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getWasteTypeList(Object... args) {
        List<Map<String, Object>> wasteTypeList = null;
        wasteTypeList = wasteTypeService.getWasteTypeIdDescription(args);
        return wasteTypeList;
    }


    @Override
    public List<WasteName> getWasteNamesByWasteId(String wasteId) {
        List<WasteName> wasteNameList = null;
        try {
            wasteNameList = wasteNameService.getWasteNamesByWasteId(wasteId);
        } catch (Exception e) {
            logger.error("危废名录根据wasteid查询失败！", e);
            throw new RuntimeException(e);
        }
        return wasteNameList;
    }


    private void updateWasteEditTimeByWasteNameId(List<String> ids) {
        try {
            wasteDao.updateWasteEditTimeByWasteNameId(ids);
        } catch (DaoAccessException e) {
            logger.error("更新危废更新时间失败", e);
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<CodeValue> getDropDownListByTypeCode(String typeCode) {
        List<CodeValue> dropDownList = null;
        try {
            dropDownList = codeTypeService.getCodeValuesTypeCode(CodeTypeConstant.WASTE_FEATURES);
        } catch (Exception e) {
            logger.error("数据字典获取失败！", e);
            throw new RuntimeException(e);
        }
        return dropDownList;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void removeWasteByIds(List<String> wasteIds) throws Exception {
        StringBuffer idsStrBuf = new StringBuffer();
        SimpleSolr simpleSolr = new SimpleSolr("WASTEINFO");
        for (String wasteId : wasteIds) {
            wasteDao.delete(wasteId);
            simpleSolr.deleteById(wasteId);
            wasteNameService.removeWasteNamesByWasteId(wasteId);
            idsStrBuf.append(wasteId).append(",");
        }
    }


    @Override
    public List<Waste> getCodeWasteDropDownList(String keyword) {
        List<Waste> wasteList = null;
        try {
            wasteList = wasteDao.getCodeWasteDropDownList(keyword);
        } catch (DaoAccessException e) {
            logger.error("根据code模糊查询失败", e);
            throw new RuntimeException(e);
        }
        return wasteList;
    }

    @Override
    public List<Waste> getWasteNameDropDownList(String keyword) {
        List<Waste> wasteList = null;
        try {
            wasteList = wasteDao.getWasteNameDropDownList(keyword);
        } catch (DaoAccessException e) {
            logger.error("根据危废名称模糊查询失败", e);
            throw new RuntimeException(e);
        }
        return wasteList;
    }

    @Override
    public List<WasteTypeVo> getAllWasteCode() {
        List<Waste> wasteList = null;
        List<WasteTypeVo> wasteTypeList = new ArrayList<WasteTypeVo>();
        Map<String, WasteTypeVo> wateTypeMap = new HashMap<String, WasteTypeVo>();
        try {
            wasteList = wasteDao.getAllWasteCode();
            for (Waste waste : wasteList) {
                if (wateTypeMap.get(waste.getWaste_type_id()) == null) {
                    WasteTypeVo wasteType = new WasteTypeVo();
                    wasteType.setId(waste.getWaste_type_id());
                    wasteType.setCode(waste.getWasteTypeCode());
                    wasteType.setDescription(waste.getWasteTypeDesc());
                    List<Waste> wasteInfoList = new ArrayList<Waste>();
                    Waste wasteInfo = new Waste();
                    wasteInfo.setId(waste.getId());
                    wasteInfo.setCode(waste.getCode());
                    wasteInfo.setDescription(waste.getDescription());
                    wasteInfoList.add(wasteInfo);
                    wasteType.setWasteList(wasteInfoList);
                    wateTypeMap.put(waste.getWaste_type_id(), wasteType);
                } else {
                    WasteTypeVo wasteType = wateTypeMap.get(waste.getWaste_type_id());
                    Waste wasteInfo = new Waste();
                    wasteInfo.setId(waste.getId());
                    wasteInfo.setCode(waste.getCode());
                    wasteInfo.setDescription(waste.getDescription());
                    wasteType.getWasteList().add(wasteInfo);
                }
            }
            Set<String> keyList = wateTypeMap.keySet();
            Iterator<String> it = keyList.iterator();
            while (it.hasNext()) {
                String key = it.next();
                wasteTypeList.add(wateTypeMap.get(key));
            }
            Collections.sort(wasteTypeList, new WasteTypeVo());

        } catch (Exception e) {
            logger.error("获取所有8位码列表时异常", e);
            throw new RuntimeException(e);
        }
        return wasteTypeList;
    }


}
