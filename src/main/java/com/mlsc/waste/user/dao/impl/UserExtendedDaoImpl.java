/**
 *
 */
package com.mlsc.waste.user.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.epdp.common.utils.StringUtils;
import com.mlsc.waste.user.dao.UserExtendedDao;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.waste.user.model.UserExtended;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.utils.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhugl
 */
@Repository
public class UserExtendedDaoImpl extends EntityDaoSupport<UserExtended> implements UserExtendedDao {
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

    @Override
    public UserExtended getUserExtendedInfoByUserId(UserExtended userExtended) throws DaoAccessException {
        List<UserExtended> resultList = query(userExtended);
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;
    }

    @Override
    public void updateWastecycleInit(String userId) throws DaoAccessException {
        Map<String, String> map = new HashMap<String, String>();
        String sql = "update " + TableNameConstants.TABLE_USER_EXTENDED + " set is_wastecycle_init=:isWastecycleInit" + " where sys_user_id =:sysUserId";
        map.put("isWastecycleInit", Constant.IS_VALID);
        map.put("sysUserId", userId);
        namedjdbctemp.update(sql, map);
    }

    @Override
    public List<UserApproveVo> listUserApproVo(String whereSql, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        StringBuffer sb = new StringBuffer("select extend.id,extend.sys_user_id ,");
        sb.append("sysuser.PhoneNum, ");
        sb.append("sysuser.ChineseName, ");
        sb.append("extend.user_status as userStatusCode, ");
        sb.append("extend.role as role, ");
        sb.append("(SELECT value FROM ").append(TableNameConstants.TABLE_CODE_VALUE);
        sb.append(" AS cv LEFT JOIN ").append(TableNameConstants.TABLE_CODE_TYPE);
        sb.append(" AS ct ON cv.type_id = ct.id WHERE cv.code = extend.role AND ct.type_code = :roleTypeCode ");
        sb.append(" )AS roleName,");
        sb.append("(SELECT value FROM ").append(TableNameConstants.TABLE_CODE_VALUE);
        sb.append(" AS cv LEFT JOIN ").append(TableNameConstants.TABLE_CODE_TYPE);
        sb.append(" AS ct ON cv.type_id = ct.id WHERE cv.code = extend.user_status AND ct.type_code = :statusTypeCode");
        sb.append(" )AS userStatus ");
        sb.append(" from " + TableNameConstants.TABLE_SYS_USER + " sysuser ");
        sb.append(" left join " + TableNameConstants.TABLE_USER_EXTENDED + " extend ");
        sb.append(" on sysuser.UserId = extend.sys_user_id ");
        sb.append(" left join " + TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION + " relation ");
        sb.append(" on extend.sys_user_id = relation.UserId ");
        sb.append(" where 1=1 ");
        if (StringUtils.isNotNullOrEmpty(whereSql)) {
            sb.append(whereSql);
        }
        paramMap.put("roleTypeCode", CodeTypeConstant.USER_ROLE);
        paramMap.put("statusTypeCode", CodeTypeConstant.USER_STATUS);
        List<UserApproveVo> queryList = namedjdbctemp.query(sb.toString(), paramMap, new BeanPropertyRowMapper<UserApproveVo>(UserApproveVo.class));

        return queryList;
    }

    @Override
    public UserExtended getAdminUserByEnterpriseId(String enterpriseId) {
        Map<String, Object> map = new HashMap<String, Object>();

        StringBuffer sqlWhere = new StringBuffer("select extend.*  ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION + " relation ");
        sqlWhere.append(" left join " + TableNameConstants.TABLE_USER_EXTENDED + " extend ");
        sqlWhere.append(" on relation.UserId = extend.sys_user_id ");
        sqlWhere.append(" where extend.role = :role and relation.EntId = :enterpriseId");

        map.put("enterpriseId", enterpriseId);
        map.put("role", UserRole.ADMIN.getRoleCode());

        List<UserExtended> queryList = namedjdbctemp.query(sqlWhere.toString(), map, new BeanPropertyRowMapper<UserExtended>(UserExtended.class));

        if (queryList != null && queryList.size() > 0) {
            return queryList.get(0);
        } else {
            return null;
        }


    }

    @Override
    public UserExtended getAdminUserByEnterpriseId(String enterpriseId, String userId) {
        Map<String, Object> map = new HashMap<String, Object>();

        StringBuffer sqlWhere = new StringBuffer("select extend.*  ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION + " relation ");
        sqlWhere.append(" left join " + TableNameConstants.TABLE_USER_EXTENDED + " extend ");
        sqlWhere.append(" on relation.UserId = extend.sys_user_id ");
        sqlWhere.append(" where extend.role = :role and relation.EntId = :enterpriseId");

        map.put("enterpriseId", enterpriseId);
        map.put("role", UserRole.ADMIN.getRoleCode());
        if (StringUtils.isNotNullOrEmpty(userId)) {
            sqlWhere.append(" and relation.UserId  = :userId");
            map.put("userId", userId);
        }

        List<UserExtended> queryList = namedjdbctemp.query(sqlWhere.toString(), map, new BeanPropertyRowMapper<UserExtended>(UserExtended.class));

        if (queryList != null && queryList.size() > 0) {
            return queryList.get(0);
        } else {
            return null;
        }


    }

    @Override
    public List<UserExtended> listUserExtendedByEnterId(String enterId) {
        Map<String, Object> map = new HashMap<String, Object>();

        StringBuffer sqlWhere = new StringBuffer("select extend.*  ");
        sqlWhere.append(" from " + TableNameConstants.TABLE_SYS_USER_ENTERPRISE_RELATION + " relation ");
        sqlWhere.append(" left join " + TableNameConstants.TABLE_USER_EXTENDED + " extend ");
        sqlWhere.append(" on relation.UserId = extend.sys_user_id ");
        sqlWhere.append(" where  relation.EntId = :enterpriseId");
        map.put("enterpriseId", enterId);
        List<UserExtended> queryList = namedjdbctemp.query(sqlWhere.toString(), map, new BeanPropertyRowMapper<UserExtended>(UserExtended.class));
        return queryList;
    }

}
