package com.mlsc.waste.user.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysUser;
import com.mlsc.waste.user.dao.UserDao;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 */
@Repository
public class UserDaoImpl extends EntityDaoSupport<User> implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

    @Override
    public List<RPCSysUser> getUserInfoByEntId(String enterpriseId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("select sys_user.UserId , ");
        sqlWhere.append(" sys_user.PhoneNum, ");
        sqlWhere.append(" sys_user.LoginName, ");
        sqlWhere.append(" sys_user.UserType, ");
        sqlWhere.append(" sys_user.ChineseName, ");
        sqlWhere.append(" sys_user.Gender, ");
        sqlWhere.append(" sys_user.Birthday, ");
        sqlWhere.append(" sys_user.EmailAddress, ");
        sqlWhere.append(" sys_user.UserStatus ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION + " user_ent_relation ");
        sqlWhere.append(" inner join " + TableNameConstants.TABLE_SYS_USER + " sys_user ");
        sqlWhere.append(" on user_ent_relation.userId = sys_user.userId ");
        sqlWhere.append(" left join " + TableNameConstants.TABLE_USER_EXTENDED + " user_extended ");
        sqlWhere.append(" on sys_user.userId = user_extended.sys_user_id ");
        sqlWhere.append(" where user_ent_relation.entId = :entId and user_extended.user_status = :userStatus");
        map.put("entId", enterpriseId);
        map.put("userStatus", UserStatus.PASS.getStatusCode());

        return namedjdbctemp.query(sqlWhere.toString(), map, new BeanPropertyRowMapper<RPCSysUser>(RPCSysUser.class));
    }

    @Override
    public List<User> getUserListByUserId(List<String> userIdList) throws Exception {
        StringBuffer sqlWhere = new StringBuffer("select su.userId, ");
        sqlWhere.append(" su.PhoneNum phoneNo, ");
        sqlWhere.append(" seb.entName enterpriseName, ");
        sqlWhere.append(" seb.entId enterpriseId ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION + " suer ");
        sqlWhere.append(" inner join " + TableNameConstants.TABLE_SYS_USER + " su ");
        sqlWhere.append(" on suer.userId = su.userId ");
        sqlWhere.append(" inner join " + TableNameConstants.TABLE_SYS_ENTERPRISE_BASE + " seb ");
        sqlWhere.append(" on suer.EntId = seb.entId ");
        sqlWhere.append(" where su.UserId in ( :userIds)");
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userIds", userIdList);

        return namedjdbctemp.query(sqlWhere.toString(), parameters, new BeanPropertyRowMapper<User>(User.class));
    }

    public String getStr4SQLINParam(String[] values) {
        String str = "";
        for (int i = 0; i < values.length; i++) {
            str += (i != 0) ? ", " : "";
            str += "'" + values[i] + "'";
        }
        return str;
    }

    @Override
    public RPCSysUser getUserInfoByBusinessReleaseId(String releaseId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("select sysUser.userId , sysUser.PhoneNum");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_USER + " sysUser ");
        sqlWhere.append(" inner join " + TableNameConstants.TABLE_COOPERATION_MESSAGE + " message ");
        sqlWhere.append(" on sysUser.UserId = message.publisher_id ");
        sqlWhere.append(" inner join " + TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS + " business ");
        sqlWhere.append(" on message.id = business.message_id ");
        sqlWhere.append(" where business.business_id = :releaseId");
        map.put("releaseId", releaseId);
        List<RPCSysUser> userList = namedjdbctemp
                .query(sqlWhere.toString(), map, new BeanPropertyRowMapper<RPCSysUser>(RPCSysUser.class));
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public RPCSysUser getUserInfoByOrderId(String orderId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sqlWhere = new StringBuffer("select sysUser.userId , sysUser.PhoneNum");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_USER + " sysUser ");
        sqlWhere.append(" inner join " + TableNameConstants.TABLE_COOPERATION_MESSAGE + " message ");
        sqlWhere.append(" on sysUser.UserId = message.publisher_id ");
        sqlWhere.append(" inner join " + TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS + " business ");
        sqlWhere.append(" on message.id = business.message_id ");
        sqlWhere.append(" inner join " + TableNameConstants.TABLE_ORDERS + " order1 ");
        sqlWhere.append(" on order1.release_id = business.business_id ");
        sqlWhere.append(" where order1.id = :orderId");

        map.put("orderId", orderId);

        List<RPCSysUser> userList = namedjdbctemp
                .query(sqlWhere.toString(), map, new BeanPropertyRowMapper<RPCSysUser>(RPCSysUser.class));
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }


    }

    @Override
    public ResultData<User> pageUser(String whereSql, Map<String, Object> paramMap, PagingParameter paging) throws
            DaoAccessException {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        String sql = getQueryUserSql(whereSql, paramMap);
        return queryDataStore(sql, paramMap, new BeanPropertyRowMapper<User>(User.class)
                , paging);
    }

    private String getQueryUserSql(String whereSql, Map<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT team.userId,team.phoneNo,team.userName,team.userType,team.enterpriseName,team.applyTime,team.cantonCode, ");
        sb.append("team.imToken,team.extendId,team.registerAddr,team.machineAddr,team.registrationCode,team.entType,");
        sb.append("team.enterpriseId,team.userStatus,team.userRole,team.userRoleName,team.userStatusName,team.mailAddress FROM (");
        sb.append("SELECT user.UserId AS userId,user.PhoneNum AS phoneNo,user.ChineseName AS userName,user.EmailAddress as mailAddress,");
        sb.append(" user.UserType AS userType,ent.entName AS enterpriseName,ent.entId AS enterpriseId,ent.cantonCode, ");
        sb.append("userExtend.registration_code AS registrationCode,");
        sb.append("userExtend.user_status AS userStatus,userExtend.role AS userRole,userExtend.update_time AS ");
        sb.append(" applyTime,user.updateTime AS updateTime,userExtend.token AS imToken,userExtend.id AS extendId,");
        sb.append(" ent.entAddress AS registerAddr,ent.entAddress AS machineAddr,ent.entType AS entType, ");
        sb.append("(SELECT value FROM ").append(TableNameConstants.TABLE_CODE_VALUE).append(" AS cv ");
        sb.append(" LEFT JOIN ").append(TableNameConstants.TABLE_CODE_TYPE).append(" AS ct ");
        sb.append(" ON cv.type_id = ct.id WHERE cv.code = userExtend.user_status AND ct.type_code = :statusType )");
        sb.append(" AS userStatusName,");
        sb.append("(SELECT value FROM ").append(TableNameConstants.TABLE_CODE_VALUE).append(" AS cv ");
        sb.append(" LEFT JOIN ").append(TableNameConstants.TABLE_CODE_TYPE).append(" AS ct ");
        sb.append(" ON cv.type_id = ct.id WHERE cv.code = userExtend.role AND ct.type_code = :userRoleType )");
        sb.append(" AS userRoleName FROM ");
        if (paramMap != null && paramMap.get("followId") != null) {
            sb.append("( SELECT user.* FROM ").append(TableNameConstants.TABLE_COOPERATION_RELATION);
            sb.append(" AS coo_rel INNER JOIN ").append(TableNameConstants.TABLE_SYS_USER).append(" AS user ");
            sb.append(" ON coo_rel.follow_id = :followId AND coo_rel.user_id = user.UserId ");
            sb.append(" AND coo_rel.valid = :relValid ");
            sb.append(" INNER JOIN ").append(TableNameConstants.TABLE_CODE_VALUE).append(" AS cv ");
            sb.append(" ON coo_rel.follow_type = cv.id AND cv.code = :followTypeCode ");
            sb.append(" AND coo_rel.valid = :relValid ) AS user ");
            paramMap.put("relValid", Constant.IS_VALID);
        } else {
            sb.append(TableNameConstants.TABLE_SYS_USER).append(" AS user ");
        }
        sb.append(" LEFT JOIN ");
        sb.append(TableNameConstants.TABLE_USER_EXTENDED).append(" AS userExtend ");
        sb.append(" ON user.UserId = userExtend.sys_user_id AND userExtend.valid = :valid LEFT JOIN ");
        sb.append(TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION).append(" AS rel ");
        sb.append(" ON user.UserId = rel.UserId LEFT JOIN ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE);
        sb.append(" AS ent ON rel.EntId = ent.entId AND entStatus = :validStatus ");
        sb.append(" WHERE 1=1 AND user.UserStatus = :validStatus ");
        sb.append(" ) AS team ");
        sb.append(" WHERE 1=1 ");
        if (StringUtils.isNotEmpty(whereSql)) {
            sb.append(whereSql);
        }
        sb.append(" ORDER BY applyTime DESC,updateTime DESC ");
        paramMap.put("valid", Constant.IS_VALID);
        paramMap.put("validStatus", Constant.VALID_STATUS);
        paramMap.put("statusType", CodeTypeConstant.USER_STATUS);
        paramMap.put("userRoleType", CodeTypeConstant.USER_ROLE);
        return sb.toString();
    }

    @Override
    public void updateUserExtendStatusByUserIdList(List<String> userIdList, String status, String roleCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(TableNameConstants.TABLE_USER_EXTENDED);
        sb.append(" SET user_status = :status,role = :role,update_time = :updateTime ");
        sb.append(" WHERE sys_user_id in (:list) ");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", status);
        paramMap.put("list", userIdList);
        paramMap.put("role", roleCode);
        paramMap.put("updateTime", Util.datetimeToString(new Date()));
        namedjdbctemp.update(sb.toString(), paramMap);
    }

    @Override
    public List<User> listUser(String whereSql, Map<String, Object> paramMap) throws DaoAccessException {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        String sql = getQueryUserSql(whereSql, paramMap);
        return namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<User>(User.class));
    }
}
