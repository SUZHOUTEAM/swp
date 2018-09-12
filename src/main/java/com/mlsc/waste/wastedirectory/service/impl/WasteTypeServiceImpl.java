package com.mlsc.waste.wastedirectory.service.impl;

import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastedirectory.dao.WasteTypeDao;
import com.mlsc.waste.wastedirectory.model.WasteType;
import com.mlsc.waste.wastedirectory.service.WasteTypeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 * 危废类别
 */
@Service
public class WasteTypeServiceImpl implements WasteTypeService {   
    
    @Autowired
    private WasteTypeDao wasteTypeDao;
    
    private Logger logger = Logger.getLogger(WasteTypeServiceImpl.class);
    
    /**
     * 危废类别的保存
     * 
     * @author sunjl date 2016-06-06
     * @throws DaoAccessException 
     */
    @Override
    public String saveWasteType(WasteType wasteType,String ticketId) throws DaoAccessException {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        String id = Util.uuid32();
        wasteType.setCode(Constant.PRV_WASTE_TYPE + wasteType.getCode());
        wasteType.setStatus(Constant.ENABLED);
        wasteType.setCreate_by(user.getUserId());
        wasteType.setCreate_time(Util.datetimeToString(new Date()));
        wasteType.setEdit_by(user.getUserId());
        wasteType.setEdit_time(Util.datetimeToString(new Date()));
        wasteTypeDao.save(wasteType, id);
        return id;
    }
    
    /**
     * 危废类别的更新
     * 
     * @author sunjl date 2016-06-06
     * @throws Exception 
     */
    @Override
    public void updateWasteType(WasteType wasteType,String ticketId) throws DaoAccessException {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        WasteType updateWateType = getWasteTypeById(wasteType.getId());
        updateWateType.setCode(Constant.PRV_WASTE_TYPE + wasteType.getCode());
        updateWateType.setDescription(wasteType.getDescription());
        updateWateType.setEdit_by(user.getUserId());
        updateWateType.setEdit_time(Util.datetimeToString(new Date()));
        wasteTypeDao.update(updateWateType);
    }

    /**
     * 危废类别的删除
     * @throws DaoAccessException 
     */
    @Override
    public void removeWasteType(List<String> ids) throws DaoAccessException{
        wasteTypeDao.deletes(ids);
    }
    
    /**
     * 危废类别状态更新
     * @throws DaoAccessException 
     */
    @Override
    public void updateWasteTypeSataus(List<String> ids,String status,String ticketId) throws DaoAccessException{
        List<WasteType> wasteTypeList = null;
        WasteType wasteType = null;
        if (ids != null && ids.size() > 0) {
            wasteTypeList = new ArrayList<WasteType>();
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            for (String id :ids) {
                wasteType = getWasteTypeById(id);
                if (wasteType != null) {
                    wasteType.setStatus(status);
                    wasteType.setEdit_by(user.getUserId());
                    wasteType.setEdit_time(Util.datetimeToString(new Date()));
                    wasteTypeList.add(wasteType);
                }
            }
            wasteTypeDao.updates(wasteTypeList);
        }
    }
    
    /**
     * 根据id获取危废类别
     * @throws DaoAccessException 
     */
    @Override
    public WasteType getWasteTypeById(String id) throws DaoAccessException{
        return wasteTypeDao.get(id);
    }
    
    /**
     * 根据危废code获取危废类别
     * @throws DaoAccessException 
     */
    @Override
    public WasteType getWasteTypeByWasteCode(String wasteCode) throws DaoAccessException{
        WasteType wasteType = new WasteType();
        wasteType.setCode(wasteCode);
        List<WasteType> wasteTypes = wasteTypeDao.query(wasteType);
        if (wasteTypes != null && wasteTypes.size() > 0){
            return wasteTypes.get(0);
        } else {
            return null;
        }
    }
    
    /**
     * 通过危废代码来查询数据的件数
     * 
     * @author sunjl date 2016-06-15
     * @throws DaoAccessException 
     */
    @Override
    public boolean isWasteTypeCodeExistent(String id,String code) throws DaoAccessException{
        return wasteTypeDao.getWasteTypeCountByCode(id, code) > 0;
    }
    
    /**
     * 危废类别一览
     * @throws DaoAccessException 
     * 
     */
    @Override
    public List<WasteType> list(String sql, Map<String, Object> params,PagingParameter paging) throws DaoAccessException{
        return wasteTypeDao.list(sql,params,paging);
    }
    
    /**
     * 危废类别数量
     * @throws DaoAccessException 
     * 
     */
    @Override
    public Integer count(String where, Map<String, Object> params) throws DaoAccessException{
        Integer countwt = 0;
        countwt = wasteTypeDao.count(where,params);
        return countwt;
    }
    
    /**
     * 根据描述获取危废类别
     * 
     */
    @Override
    public List<Map<String, Object>> getWasteTypeIdDescription(Object... args){
        List<Map<String, Object>> wastetype = null;
        try {
            wastetype = wasteTypeDao.search(args);
        } catch (Exception e) {
            logger.error("根据描述获取危废类别！",e);
            throw new RuntimeException(e);
        }
        return wastetype;
    }

    /**
     * 查找所有的危废类别名称
     * @throws DaoAccessException 
     * 
     */
    @Override
    public List<WasteType> getAllWateType() throws DaoAccessException{
        return wasteTypeDao.queryAllWasteType();
    }
}
