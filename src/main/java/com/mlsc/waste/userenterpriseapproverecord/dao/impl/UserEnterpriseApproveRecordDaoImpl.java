/**
 * 
 */
package com.mlsc.waste.userenterpriseapproverecord.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.userenterpriseapproverecord.dao.UserEnterpriseApproveRecordDao;
import com.mlsc.waste.userenterpriseapproverecord.model.AuditUserRecordVo;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecord;
import com.mlsc.waste.userenterpriseapproverecord.model.UserEnterpriseApproveRecordVo;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 *
 */
@Repository
public class UserEnterpriseApproveRecordDaoImpl extends EntityDaoSupport<UserEnterpriseApproveRecord> implements UserEnterpriseApproveRecordDao {

    @Override
    public List<UserEnterpriseApproveRecordVo> getUserEnterpriseApproveRecordVos(
            String userId, String entId,String[] eventType, String[] eventStatus) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select record.id, ");
        tempSql.append(" record.user_id,");
        tempSql.append(" record.enterprise_id,");
        tempSql.append(" record.event_type,");
        tempSql.append(" record.event_status,");
        tempSql.append(" record.application_by,");
        tempSql.append(" record.application_time,");
        tempSql.append(" record.approved_by,");
        tempSql.append(" record.approved_time,");
        tempSql.append(" record.create_by,");
        tempSql.append(" record.create_time,");
        tempSql.append(" record.edit_by,");
        tempSql.append(" record.edit_time,");
        tempSql.append(" record.valid, ");
        tempSql.append(" eventtype.code as eventTypeCode, ");//事件类型code
        tempSql.append(" eventstatus.code as eventStatusCode, ");//事件状态code
        tempSql.append(" enterprisebase.entName as enterpriseName, ");//企业名称
        tempSql.append(" concat(eventtype.value,':',enterprisebase.entName) as applicationItem, ");//申请事项  
        tempSql.append(" eventstatus.value as applicationStatus, ");// 申请状态
        tempSql.append(" date_format(record.edit_time, '%Y-%m-%d %T') as applicationTime ");// 申请时间
        tempSql.append("from " + TableNameConstants.TABLE_USER_ENTERPRISE_APPROVE_RECORD + " record ");
        tempSql.append("inner join  " + TableNameConstants.TABLE_CODE_VALUE + " eventtype on record.event_type = eventtype.id ");
        if (eventType != null && eventType.length > 0) {
            tempSql.append(" and eventtype.code in (:eventType) ");
            StringBuffer eventtypeparm = new StringBuffer();
            for (String arr : eventType) {
                eventtypeparm.append(arr + ",");
            }
            map.put("eventType", eventtypeparm.deleteCharAt(eventtypeparm.length()-1).toString());
        }
        
        tempSql.append("inner join  " + TableNameConstants.TABLE_CODE_VALUE + " eventstatus on record.event_status = eventstatus.id ");
        if (eventStatus != null && eventStatus.length > 0) {
            tempSql.append(" and eventstatus.code in (:eventStatus) ");
            StringBuffer eventStatusparm = new StringBuffer();
            for (String arr : eventStatus) {
                eventStatusparm.append(arr + ",");
            }
            map.put("eventStatus", eventStatusparm.deleteCharAt(eventStatusparm.length()-1).toString());
        }
        
        tempSql.append("inner join  " + TableNameConstants.TABLE_SYS_ENTERPRISE_BASE + " enterprisebase on record.enterprise_id = enterprisebase.entId ");
        tempSql.append("where IFNULL(record.valid,:valid) = :valid ");
        tempSql.append(" and record.user_id = :userId ");
        if (StringUtils.isNotBlank(entId)) {
            tempSql.append(" and record.enterprise_id = :entId ");
            map.put("entId", entId);
        }
        
        tempSql.append(" order by record.edit_time desc ");
        
        map.put("valid", Constant.IS_VALID);
        map.put("userId", userId);
        
        List<UserEnterpriseApproveRecordVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map,
                new BeanPropertyRowMapper<UserEnterpriseApproveRecordVo>(UserEnterpriseApproveRecordVo.class));
        
        return list;
    }

    @Override
    public List<AuditUserRecordVo> getUserEnterpriseApproveRecords(
            String userEventType, String userEventStatus, String enterpriseId) throws DaoAccessException{
        /**
        SELECT
            record.id as id,
            userbase.ChineseName as chineseName,
            userbase.PhoneNum as phoneNum,
            codeValue1.value as  eventStatus,
            codeValue1.`code` as eventStatusCode,
            codeValue2.value as  eventType,
            codeValue2.`code` as eventTypeCode,
            userbase.UserType as userType,
            userbase.Gender  as gender,
            record.application_time as applicationTime
            FROM user_enterprise_approve_record record
            join sys_user userbase on record.user_id = userbase.UserId 
            join code_type codeType1 on  codeType1.type_code = 'USER_EVENT_STATUS'
            join code_value codeValue1 on codeValue1.type_id = codeType1.id and record.event_status = codeValue1.id and codeValue1.code = 'SUBMIT'
            join code_type codeType2 on  codeType2.type_code = 'USER_EVENT_TYPE'
            join code_value codeValue2 on codeValue2.type_id = codeType2.id and record.event_type = codeValue2.id and codeValue2.code = 'JOIN'
            where record.valid = '1';
         */
        
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select record.id as id, ");
        tempSql.append(" record.enterprise_id as enterpriseId, ");
        tempSql.append(" userbase.ChineseName as chineseName, ");
        tempSql.append(" userbase.PhoneNum as phoneNum, ");
        tempSql.append(" userbase.UserId as userId, ");
        tempSql.append(" codeValue1.value as  eventStatus, ");
        tempSql.append(" codeValue1.code as eventStatusCode, ");
        tempSql.append(" codeValue2.value as  eventType, ");
        tempSql.append(" codeValue2.code as eventTypeCode, ");
        tempSql.append(" userbase.UserType as userType, ");
        tempSql.append(" userbase.Gender  as gender, ");
        tempSql.append(" record.application_time as applicationTime ");
        tempSql.append(" FROM " + TableNameConstants.TABLE_USER_ENTERPRISE_APPROVE_RECORD + " record ");
        tempSql.append(" join " + TableNameConstants.TABLE_SYS_USER + " userbase on record.user_id = userbase.UserId  ");
        tempSql.append(" join " + TableNameConstants.TABLE_CODE_TYPE  + " codeType1 on  codeType1.type_code = '" + CodeTypeConstant.USER_EVENT_STATUS + "' ");
        tempSql.append(" join " + TableNameConstants.TABLE_CODE_VALUE + " codeValue1 on codeValue1.type_id = codeType1.id and record.event_status = codeValue1.id and codeValue1.code = :userEventStatus ");
        tempSql.append(" join " + TableNameConstants.TABLE_CODE_TYPE + " codeType2 on  codeType2.type_code = '" + CodeTypeConstant.USER_EVENT_TYPE + "'");
        tempSql.append(" join " + TableNameConstants.TABLE_CODE_VALUE + " codeValue2 on codeValue2.type_id = codeType2.id and record.event_type = codeValue2.id and codeValue2.code = :userEventType");
        tempSql.append(" where record.valid = :valid and record.enterprise_id = :enterprise_id; ");
        map.put("enterprise_id", enterpriseId);
        map.put("userEventType", userEventType);
        map.put("userEventStatus", userEventStatus);
        map.put("valid", Constant.IS_VALID);
        List<AuditUserRecordVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map,
                new BeanPropertyRowMapper<AuditUserRecordVo>(AuditUserRecordVo.class));
        return list;
    }

    @Override
    public void updateRecordByEnpId(String enterpriseId, String userEventStatus) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" update " + TableNameConstants.TABLE_USER_ENTERPRISE_APPROVE_RECORD +" "
                + "set event_status = :event_status where enterprise_id = :enterprise_id and valid = :valid");
        map.put("enterprise_id", enterpriseId);
        map.put("event_status", userEventStatus);
        map.put("valid", Constant.IS_VALID);
        namedParameterJdbcTemplate.update(tempSql.toString(), map);
    }
}
