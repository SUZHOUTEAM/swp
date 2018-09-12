package com.mlsc.waste.wastecircle.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.wastecircle.dao.OfflineMessageDao;
import com.mlsc.waste.wastecircle.model.OfflineMessage;
import com.mlsc.waste.wastecircle.model.OfflineMessageVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OfflineMessageDaoImpl extends EntityDaoSupport<OfflineMessage> implements OfflineMessageDao {

    @Override
    public List<OfflineMessageVo> getOfflineMsgList() throws DaoAccessException {
        StringBuffer sql = new StringBuffer(" SELECT froment.entName AS fromEntName, ");
        sql.append("fromuser.ChineseName AS fromUserName,  ");
        sql.append("fromuser.PhoneNum AS fromPhoneNum,  ");
        sql.append("toent.entName AS toEntName,  ");
        sql.append("msg.context,  ");
        sql.append("msg.createTime,  ");
        sql.append("case when msg.status='1' then '未读' when msg.status='0' then '已读' else null end as flag ");
        sql.append(" from ").append(TableNameConstants.TABLE_OFFLINE_MESSAGE).append(" msg ");
        sql.append(" join ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE).append(" froment ");
        sql.append(" ON msg.fromEntId = froment.entId ");
        sql.append(" join ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE).append(" toent ");
        sql.append(" ON msg.toEntId = toent.entId ");
        sql.append(" join ").append(TableNameConstants.TABLE_SYS_USER).append(" fromuser ");
        sql.append(" ON msg.fromUserId = fromuser.UserId ");
        sql.append(" ORDER BY msg.status desc ,msg.createTime desc ");
        List<OfflineMessageVo> offlineMessageVoList = namedParameterJdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<OfflineMessageVo>(OfflineMessageVo.class));
        return offlineMessageVoList;
    }

    @Override
    public List<OfflineMessageVo> getOfflineMsgList(String where, Map<String, Object> paramMap, PagingParameter paging) {
        StringBuffer sql = new StringBuffer(" SELECT froment.entName AS fromEntName, ");
        sql.append("fromuser.ChineseName AS fromUserName,  ");
        sql.append("fromuser.PhoneNum AS fromPhoneNum,  ");
        sql.append("toent.entName AS toEntName,  ");
        sql.append("msg.context,  ");
        sql.append("msg.createTime,  ");
        sql.append("msg.id,  ");
        sql.append("msg.status,  ");
        sql.append("case when msg.status='1' then '未读' when msg.status='0' then '已读' else null end as flag ");
        sql.append(" from ").append(TableNameConstants.TABLE_OFFLINE_MESSAGE).append(" msg ");
        sql.append(" join ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE).append(" froment ");
        sql.append(" ON msg.fromEntId = froment.entId ");
        sql.append(" join ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE).append(" toent ");
        sql.append(" ON msg.toEntId = toent.entId ");
        sql.append(" join ").append(TableNameConstants.TABLE_SYS_USER).append(" fromuser ");
        sql.append(" ON msg.fromUserId = fromuser.UserId ");
        sql.append(" where 1=1 ");
        if (paramMap.get("fromEntName") != null) {
            sql.append(" and froment.entName like :fromEntName ");
        }
        if (paramMap.get("toEntName") != null) {
            sql.append(" and toent.entName  like :toEntName ");
        }
        if (paramMap.get("status") != null && !paramMap.get("status").toString().contains("total")) {
            sql.append(" and msg.status  like :status ");
        }
        sql.append(" ORDER BY msg.status desc ,msg.createTime desc ");
        if (paging != null && paging.getLimit() != 0) {
            sql.append(" limit " + paging.getStart() + " , " + paging.getLimit());
        }

        List<OfflineMessageVo> offlineMessageVoList = namedParameterJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper<OfflineMessageVo>(OfflineMessageVo.class));
        return offlineMessageVoList;
    }

    @Override
    public OfflineMessageVo getOfflineMsgById(String id) throws DaoAccessException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer(" SELECT froment.entName AS fromEntName, ");
        sql.append("fromuser.ChineseName AS fromUserName,  ");
        sql.append("fromuser.PhoneNum AS fromPhoneNum,  ");
        sql.append("toent.entName AS toEntName,  ");
        sql.append("msg.context,  ");
        sql.append("msg.createTime,  ");
        sql.append("msg.id,  ");
        sql.append("msg.status,  ");
        sql.append("msg.*,  ");
        sql.append("case when msg.status='1' then '未读' when msg.status='0' then '已读' else null end as flag ");
        sql.append(" from ").append(TableNameConstants.TABLE_OFFLINE_MESSAGE).append(" msg ");
        sql.append(" join ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE).append(" froment ");
        sql.append(" ON msg.fromEntId = froment.entId ");
        sql.append(" join ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE).append(" toent ");
        sql.append(" ON msg.toEntId = toent.entId ");
        sql.append(" join ").append(TableNameConstants.TABLE_SYS_USER).append(" fromuser ");
        sql.append(" ON msg.fromUserId = fromuser.UserId ");
        sql.append(" where 1=1 and msg.id = :id");
        paramMap.put("id",id);
        List<OfflineMessageVo> offlineMessageVoList = namedParameterJdbcTemplate.query(sql.toString(), paramMap, new BeanPropertyRowMapper<OfflineMessageVo>(OfflineMessageVo.class));
        if(offlineMessageVoList != null){
            return offlineMessageVoList.get(0);
        }
        return  null;
    }
}
