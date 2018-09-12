package com.mlsc.waste.wastecircle.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.dao.CoopMsgBusDao;
import com.mlsc.waste.wastecircle.model.CoopMsgBus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CoopMsgBusDaoImpl extends EntityDaoSupport<CoopMsgBus> implements CoopMsgBusDao {

    @Override
    public String saveCoopMsgBus(CoopMsgBus coopMsgBus) throws DaoAccessException {
        String id =  Util.uuid32();
        save(coopMsgBus,id);
        return id;
    }

    @Override
    public CoopMsgBus queryCoopMsgBusByMsgId(CoopMsgBus coopMsgBus) throws DaoAccessException {
        StringBuffer sql = new StringBuffer("SELECT ID, MESSAGE_ID AS MSGID ,BUSINESS_TYPE_CODE AS BUSTYPECODE , ");
        sql.append("BUSINESS_ID AS BUSID " );
        sql.append("FROM ").append(TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS) ;
        sql.append(" WHERE MESSAGE_ID=:MESSAGE_ID AND VALID=:VALID");
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("MESSAGE_ID", coopMsgBus.getMsgId());
        map.put("VALID", coopMsgBus.getValid());
        
        List<CoopMsgBus> resultList = namedParameterJdbcTemplate.query(sql.toString(), map, new BeanPropertyRowMapper<CoopMsgBus>(CoopMsgBus.class));
        
        if(resultList!=null && resultList.size() >0){
            return resultList.get(0);
        }else{
            return null; 
        }
    }

    @Override
    public void updateCoopMsgBus(CoopMsgBus coopMsgBus)    throws DaoAccessException {

    }

    @Override
    public String getCoopMsgBusByMsgId(String msgId) throws DaoAccessException {
        StringBuffer sql = new StringBuffer(" select id, message_id, business_type_code, business_id as BusId, create_by ,create_time , edit_by ,edit_time ");
        sql.append(" from ").append(TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS).append(" business ");
        sql.append(" where business.message_id = :message_id and valid = :valid");
        Map<String, String> map = new HashMap<String, String>();
        map.put("message_id", msgId);
        map.put("valid", "1");
        CoopMsgBus coopMsgBus = namedParameterJdbcTemplate.queryForObject(sql.toString(), map, new BeanPropertyRowMapper<CoopMsgBus>(CoopMsgBus.class));
        if(coopMsgBus != null){
            return coopMsgBus.getBusId();
        }
        return null;
    }

    @Override
    public void deleteMsgBusByMsgId(String msgId) throws DaoAccessException {
        StringBuffer sql = new StringBuffer(" delete from ");
        sql.append(TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS);
        sql.append(" where message_id = :message_id  and valid = :valid");
        Map<String, String> map = new HashMap<String, String>();
        map.put("message_id", msgId);
        map.put("valid", "1");
        namedParameterJdbcTemplate.update(sql.toString(), map);
    }
    
    @Override
    public String getMessageIdByBusId(String busId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer();
        tempSql.append(" select message_id");
        tempSql.append(" from " + TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS );
        tempSql.append(" where business_id = :business_id and ifnull(valid, :valid) = :valid ");
        map.put("valid", Constant.IS_VALID);
        map.put("business_id", busId);
        
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), map, String.class);
    }

}
