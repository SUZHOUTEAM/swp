/**
 *
 */
package com.mlsc.waste.licence.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.licence.dao.LicenceDao;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceVo;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hou
 */
@Repository
public class LicenceDaoImpl extends EntityDaoSupport<OperationLicence>
        implements LicenceDao {
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

    @Override
    public List<OperationLicenceVo> list(String where,
                                         Map<String, Object> paramMap, PagingParameter paging,
                                         String enterpriseId) throws DaoAccessException {

        StringBuffer sql = listSql(where, paramMap, enterpriseId);
        return namedParameterJdbcTemplate.query(sql.toString() + " limit " + paging.getStart() + " , "
                + paging.getLimit(), paramMap, new BeanPropertyRowMapper<OperationLicenceVo>(OperationLicenceVo.class));
    }

    /**
     * 数据总数
     */
    @Override
    public Integer count(String where, Map<String, Object> paramMap, String enterpriseId) throws DaoAccessException {
        StringBuffer tempSql = new StringBuffer();
        tempSql.append(" select count(1) from ( ");
        tempSql.append(" " + listSql(where, paramMap, enterpriseId).toString() + " ");
        tempSql.append(" ) tem");
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), paramMap, Integer.class);
    }

    @Override
    public String getAuditStatusByLicenceId(String licenceId) throws DaoAccessException {
        Map<String, String> paraMap = new HashMap<>();
        StringBuffer tempSql = new StringBuffer();
        tempSql.append(" select cv.code from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " cv on ol.audit_status = cv.id ");
        tempSql.append(" where ol.id = :licenceId ");

        paraMap.put("licenceId", licenceId);
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), paraMap, String.class);
    }

    /**
     * 获取企业有效许可证Id
     */
    @Override
    public List<OperationLicence> getValidLicIdByEnterpriseId(String enterpriseId)
            throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer();
        tempSql.append(" SELECT ol.id,");
        tempSql.append(" ol.enterprise_id,");
        tempSql.append(" ol.enterprise_name,");
        tempSql.append(" ol.licence_no,");
        tempSql.append(" ol.licence_org as licence_org,");
        tempSql.append(" ol.licence_date,");
        tempSql.append(" ol.initiallic_date,");
        tempSql.append(" ol.corporate,");
        tempSql.append(" ol.register_addr,");
        tempSql.append(" ol.machine_addr,");
        tempSql.append(" ol.start_date,");
        tempSql.append(" ol.end_date,");
        tempSql.append(" ol.licence_status,");
        tempSql.append(" ol.operation_mode,");
        tempSql.append(" ol.application_time,");
        tempSql.append(" ol.audit_status,");
        tempSql.append(" ol.approved_by,");
        tempSql.append(" ol.create_by,");
        tempSql.append(" ol.create_time,");
        tempSql.append(" ol.edit_by,");
        tempSql.append(" ol.edit_time,");
        tempSql.append(" ol.valid ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol  ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " t1 on ol.audit_status = t1.id and t1.code= :pass");
        tempSql.append(" WHERE IFNULL(ol.valid, :valid) = :valid");
        tempSql.append(" AND ol.start_date <= SYSDATE() AND ol.end_date >= SYSDATE() ");
        tempSql.append(" AND ol.enterprise_id =:enterpriseId");
        tempSql.append(" order by  ol.edit_time desc");

        map.put("enterpriseId", enterpriseId);
        map.put("valid", Constant.IS_VALID);
        map.put("pass", CodeTypeConstant.LIC_AUDIT_PASS);

        List<OperationLicence> list = namedParameterJdbcTemplate.query(tempSql.toString(), map,
                new BeanPropertyRowMapper<OperationLicence>(OperationLicence.class));

        // 没有查到数据时，queryForList方法默认返回一个数据对象，此时list.size() = 1,但是值全部为NULL
        if (list != null && list.size() == 1) {
            if (StringUtils.isBlank(list.get(0).getId())) {
                list = new ArrayList<OperationLicence>();
            }
        }
        return list;
    }

    @Override
    public void deleteLicenceByLicenceIds(List<String> licenceIds, Map<String, Object> paramMap) throws DaoAccessException {
        StringBuffer tempSql = new StringBuffer("update " + TableNameConstants.TABLE_OPERATION_LICENCE);
        tempSql.append(" set valid = :valid, ");
        tempSql.append(" edit_by = :editBy, ");
        tempSql.append(" edit_time = :editTime ");
        tempSql.append(" where id in (:licenceIds) ");
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }

    private StringBuffer listSql(String where, Map<String, Object> paramMap, String enterpriseId) {
        StringBuffer tempSql = new StringBuffer();
        String licenceStatus = paramMap.get("licenceStatus") == null ? "" : paramMap.get("licenceStatus").toString().replaceAll("%", "");
        if (StringUtils.isBlank(licenceStatus)) {
            // 查未生效和过期的许可证
            tempSql.append(" select * from ( ");
            tempSql = invalidSql(tempSql, where);
            tempSql.append(" UNION ");
            tempSql = overdSql(tempSql, where);
            tempSql.append(" ) tem1 order by tem1.edit_time desc ");
        } else if (CodeTypeConstant.LIC_VALID_INVALID.equals(licenceStatus)) {
            // 只查未生效
            tempSql = invalidSql(tempSql, where);
            tempSql.append(" order by ol.edit_time desc ");
        } else if (CodeTypeConstant.LIC_VALID_OVERDUE.equals(licenceStatus)) {
            // 只查过期的
            tempSql = overdSql(tempSql, where);
            tempSql.append(" order by ol.edit_time desc ");
        }

        paramMap.put("valid", Constant.IS_VALID);
        paramMap.put("create", CodeTypeConstant.LIC_AUDIT_CREATE);
        paramMap.put("submit", CodeTypeConstant.LIC_AUDIT_SUBMIT);
        paramMap.put("termination", CodeTypeConstant.LIC_AUDIT_TERMINATION);
        paramMap.put("pass", CodeTypeConstant.LIC_AUDIT_PASS);
        paramMap.put("refused", CodeTypeConstant.LIC_AUDIT_REFUSED);
        paramMap.put("enterpriseId", enterpriseId);

        return tempSql;
    }

    private StringBuffer invalidSql(StringBuffer tempSql, String where) {
        // 只查未生效
        sqlAppend(tempSql);
        tempSql.append(" '未生效' as licenceStatus ");
        whereAppend(tempSql);
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " codevaue2 on ol.audit_status = codevaue2.id and codevaue2.code in (:create,:submit,:refused,:termination)");
        tempSql.append(" where IFNULL(ol.valid, :valid) = :valid and ol.enterprise_id =:enterpriseId ");
        tempSql = whereSQL(tempSql, where);
        return tempSql;
    }

    private StringBuffer overdSql(StringBuffer tempSql, String where) {
        // 只查过期的
        sqlAppend(tempSql);
        tempSql.append(" '过期' as licenceStatus ");
        whereAppend(tempSql);
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " codevaue2 on ol.audit_status = codevaue2.id and codevaue2.code =:pass ");
        tempSql.append(" where IFNULL(ol.valid, :valid) = :valid ");
        tempSql.append(" and (ol.start_date > SYSDATE() or ol.end_date < SYSDATE() ) and ol.enterprise_id =:enterpriseId ");
        tempSql = whereSQL(tempSql, where);
        return tempSql;
    }

    private StringBuffer sqlAppend(StringBuffer sbuffer) {
        sbuffer.append(" SELECT ol.id,");
        sbuffer.append(" ol.enterprise_id,");
        sbuffer.append(" ol.enterprise_name,");
        sbuffer.append(" ol.licence_no ,");
        sbuffer.append(" ol.licence_org ,");
        sbuffer.append(" org_com.comName as comName ,");
        sbuffer.append(" date_format(ol.licence_date, '%Y-%m-%d') as licence_date, ");
        sbuffer.append(" date_format(ol.initiallic_date,'%Y-%m-%d') as initiallic_date,");
        sbuffer.append(" ol.corporate,");
        sbuffer.append(" ol.register_addr,");
        sbuffer.append(" ol.machine_addr,");
        sbuffer.append(" ol.start_date,");
        sbuffer.append(" ol.end_date,");
        sbuffer.append(" ol.licence_status,");
        sbuffer.append(" ol.operation_mode,");
        sbuffer.append(" ol.application_time,");
        sbuffer.append(" ol.audit_status,");
        sbuffer.append(" ol.approved_by,");
        sbuffer.append(" ol.create_by,");
        sbuffer.append(" ol.create_time,");
        sbuffer.append(" ol.edit_by,");
        sbuffer.append(" ol.edit_time,");
        sbuffer.append(" ol.valid, ");
        sbuffer.append(" codevaue1.value as operationMode, ");
        sbuffer.append(" codevaue2.code as auditStatusCode, ");
        sbuffer.append(" codevaue2.value as auditStatus, ");
        sbuffer.append(" concat(date_format(ol.start_date, '%Y-%m-%d'),' 至 ',date_format(ol.end_date, '%Y-%m-%d')) AS validityPeriod, ");
        return sbuffer;
    }

    private StringBuffer whereAppend(StringBuffer sbuffer) {
        sbuffer.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol ");
        sbuffer.append(" left join " + TableNameConstants.TABLE_SYS_ORG_COM + " org_com on ol.licence_org = org_com.comId ");
        sbuffer.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " codevaue1 on ol.operation_mode = codevaue1.id ");
        return sbuffer;
    }

    private StringBuffer whereSQL(StringBuffer sbuffer, String where) {
        sbuffer.append(" " + where);
        String replaceString1 = "and licenceStatus like :licenceStatus";
        String replaceString2 = "enterpriseName like :enterpriseName";
        String replaceString3 = "auditStatus like :auditStatus";
        String sql = sbuffer.toString().replace(replaceString1, " ").replace(replaceString2, "ol.enterprise_name like :enterpriseName ")
                .replace(replaceString3, "ol.audit_status like :auditStatus ");
        return new StringBuffer(sql);
    }

    @Override
    public OperationLicenceVo getlicenceVoById(String licenceId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer();
        tempSql.append(" SELECT ol.id,");
        tempSql.append(" ol.enterprise_id,");
        tempSql.append(" ol.enterprise_name,");
        tempSql.append(" ol.licence_no,");
        tempSql.append(" ol.licence_org,");
        tempSql.append(" date_format(ol.licence_date, '%Y-%m-%d') as licence_date,");
        tempSql.append(" date_format(ol.initiallic_date, '%Y-%m-%d') as initiallic_date,");
        tempSql.append(" ol.corporate,");
        tempSql.append(" ol.register_addr,");
        tempSql.append(" ol.machine_addr,");
        tempSql.append(" date_format(ol.start_date, '%Y-%m-%d') as start_date,");
        tempSql.append(" date_format(ol.end_date, '%Y-%m-%d') as end_date,");
        tempSql.append(" ol.licence_status,");
        tempSql.append(" ol.operation_mode,");
        tempSql.append(" ol.application_time,");
        tempSql.append(" ol.audit_status,");
        tempSql.append(" ol.approved_by,");
        tempSql.append(" ol.create_by,");
        tempSql.append(" ol.create_time,");
        tempSql.append(" ol.edit_by,");
        tempSql.append(" ol.edit_time,");
        tempSql.append(" ol.valid ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol  ");
        tempSql.append(" WHERE IFNULL(ol.valid, :valid) = :valid ");
        tempSql.append(" AND ol.id =:licenceId");

        map.put("licenceId", licenceId);
        map.put("valid", Constant.IS_VALID);

        List<OperationLicenceVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map,
                new BeanPropertyRowMapper<OperationLicenceVo>(OperationLicenceVo.class));

        // 没有查到数据时，queryForList方法默认返回一个数据对象，此时list.size() = 1,但是值全部为NULL
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 许可证审核状态的更新
     *
     * @param
     * @throws DaoAccessException
     */
    @Override
    public void updateAuditStatus(List<String> licenceIds, Map<String, Object> paramMap) throws DaoAccessException {
        StringBuffer tempSql = new StringBuffer("update " + TableNameConstants.TABLE_OPERATION_LICENCE);
        tempSql.append(" set audit_status =:auditStatus, ");
        tempSql.append(" approved_by =:approvedBy, ");
        tempSql.append(" edit_by = :editBy, ");
        tempSql.append(" edit_time = :editTime ");
        tempSql.append(" where id in (:licenceIds) ");
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }

    @Override
    public int queryLicenceNoIsExist(String licenceNo, String licenceId) throws DaoAccessException {
        Map<String, String> paramMap = new HashMap<String, String>();
        StringBuffer tempSql = new StringBuffer(" select count(1) from " + TableNameConstants.TABLE_OPERATION_LICENCE);
        tempSql.append(" where licence_no = :licenceNo and valid=:valid ");
        if (StringUtils.isNotBlank(licenceId)) {
            tempSql.append(" and id <> :licenceId ");
            paramMap.put("licenceId", licenceId);
        }
        paramMap.put("licenceNo", licenceNo);
        paramMap.put("valid", Constant.IS_VALID);
        int count = namedParameterJdbcTemplate.queryForObject(tempSql.toString(), paramMap, Integer.class);
        return count;
    }

    @Override
    public Integer isValidityPeriodRepeat(String entId, String licenceId, String startDate, String endDate) throws DaoAccessException {
        Map<String, String> paramMap = new HashMap<String, String>();
        StringBuffer tempSql = new StringBuffer(" select count(1) from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol ");
        tempSql.append(" INNER JOIN " + TableNameConstants.TABLE_CODE_VALUE + " licenceStatus ");
        tempSql.append(" on ol.audit_status = licenceStatus.id ");
        
        
        tempSql.append(" where IFNULL(ol.valid, :valid) = :valid and ol.enterprise_id = :entId ");

        if (StringUtils.isNotBlank(licenceId)) {
            tempSql.append(" and ol.id <> :licenceId ");
            paramMap.put("licenceId", licenceId);
        }

        tempSql.append("  and ( ");
        tempSql.append("  (ol.start_date >= :startDate and ol.end_date <= :endDate) or ");
        tempSql.append("  (ol.start_date <= :startDate and ol.end_date >= :endDate) or ");
        tempSql.append("  (ol.start_date >= :startDate and ol.start_date <= :endDate and ol.end_date >= :endDate) or ");
        tempSql.append("  (ol.start_date <= :startDate and ol.end_date >= :startDate and ol.end_date <= :endDate) ");
        tempSql.append(" )  and licenceStatus.code != :licenceStatus");
        paramMap.put("entId", entId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("valid", Constant.IS_VALID);
        paramMap.put("licenceStatus", CodeTypeConstant.LIC_AUDIT_TERMINATION);
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), paramMap, Integer.class);
    }

    /**
     * 查询是否存在无效许可证(AaronWang)
     */
    @Override
    public int getUsedLicId(String enterpriseId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer();
        tempSql.append(" SELECT count(1) ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol  ");
        tempSql.append(" inner join  " + TableNameConstants.TABLE_CODE_VALUE + " auditstatus on ol.audit_status = auditstatus.id ");
        tempSql.append(" WHERE IFNULL(ol.valid, :valid) = :valid and ");
        tempSql.append(" ( ol.start_date >= SYSDATE() or ol.end_date <= SYSDATE() or auditstatus.code in (:creat,:refused,:termination) ) ");
        tempSql.append(" AND ol.enterprise_id = :enterpriseId");
        map.put("enterpriseId", enterpriseId);
        map.put("valid", Constant.IS_VALID);
        map.put("creat", CodeTypeConstant.LIC_AUDIT_CREATE);
        //map.put("submit", CodeTypeConstant.LIC_AUDIT_SUBMIT);正在审核的单独一种情况出来
        map.put("refused", CodeTypeConstant.LIC_AUDIT_REFUSED);
        
        map.put("termination", CodeTypeConstant.LIC_AUDIT_TERMINATION);

        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), map, Integer.class);
    }

    @Override
    public int isHasDetails(String licenceId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select count(licenceDetail.id) ");
        tempSql.append(" from ").append(TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL).append(" licenceDetail ");
        tempSql.append(" where licenceDetail.licence_id = :licence_id and licenceDetail.valid = :valid");
        map.put("licence_id", licenceId);
        map.put("valid", Constant.IS_VALID);
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), map, Integer.class);
    }

    @Override
    public int getProcessableTotalCount(String licenceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select count(licenceDetail.id) ");
        tempSql.append(" from ").append(TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL).append(" licenceDetail ");
        tempSql.append(" LEFT JOIN ").append(TableNameConstants.TABLE_WASTE_NAME).append(" wn on licenceDetail.waste_name_id = wn.id ");
        tempSql.append(" where licenceDetail.licence_id = :licence_id and licenceDetail.valid = :valid and wn.id is not null");
        map.put("licence_id", licenceId);
        map.put("valid", Constant.IS_VALID);
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), map, Integer.class);
    }

    @Override
    public List<OperationLicence> listLiceneByEnterpriseIdAndStatus(String enterpriseId,String status) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer();
        tempSql.append(" SELECT * ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol  ");
        tempSql.append(" inner join  " + TableNameConstants.TABLE_CODE_VALUE + " auditstatus on ol.audit_status = auditstatus.id ");
        tempSql.append(" WHERE IFNULL(ol.valid, :valid) = :valid and ");
        tempSql.append(" auditstatus.code= :submit ");
        tempSql.append(" AND ol.enterprise_id = :enterpriseId");
        //tempSql.append(" and ol.start_date <= SYSDATE() ");
        //tempSql.append(" and ol.end_date >= SYSDATE() ");//在当期有效期之内才可以
        map.put("enterpriseId", enterpriseId);
        map.put("valid", Constant.IS_VALID);
        map.put("submit", status);
        List<OperationLicence> submitLics = namedParameterJdbcTemplate.query(tempSql.toString(), map,
                new BeanPropertyRowMapper<OperationLicence>(OperationLicence.class));
        return submitLics;
    }


    @Override
    public void updateEditTimeByWasteId(String wasteId) throws DaoAccessException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer("update " + TableNameConstants.TABLE_OPERATION_LICENCE);
        tempSql.append(" set  edit_time = SYSDATE()");
        tempSql.append(" where id in ( ");
        tempSql.append(" select licencedetail.licence_id ");
        tempSql.append(" from ").append(TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL).append(" licencedetail ");
        tempSql.append(" JOIN ").append(TableNameConstants.TABLE_WASTE).append(" waste ");
        tempSql.append("  ON waste.id = licencedetail.waste_id ");
        tempSql.append(" where waste.id = :wasteId ");
        tempSql.append(" )");
        paramMap.put("wasteId", wasteId);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }

    @Override
    public void updateEditTimeByWasteNameId(List<String> wasteNameIds) throws DaoAccessException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer("update " + TableNameConstants.TABLE_OPERATION_LICENCE);
        tempSql.append(" set  edit_time = SYSDATE()");
        tempSql.append(" where id in ( ");
        tempSql.append(" select DISTINCT(licencedetail.licence_id) ");
        tempSql.append(" from ").append(TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL).append(" licencedetail ");
        tempSql.append(" JOIN ").append(TableNameConstants.TABLE_WASTE).append(" waste ");
        tempSql.append("  ON waste.id = licencedetail.waste_id ");
        tempSql.append(" JOIN ").append(TableNameConstants.TABLE_WASTE_NAME).append(" wastename ");
        tempSql.append(" ON waste.id = wastename.waste_id ");
        tempSql.append(" where wastename.id in (:wasteNameIds) ");
        tempSql.append(" )");
        paramMap.put("wasteNameIds", wasteNameIds);
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }


    @Override
    public List<OperationLicenceVo> getSubmitList(String where, Map<String, Object> paramMap, PagingParameter paging, String enterpriseId) throws DaoAccessException {
        StringBuffer tempSql = new StringBuffer(" SELECT ol.id,");
        tempSql.append(" ol.enterprise_id,");
        tempSql.append(" ol.enterprise_name,");
        tempSql.append(" ol.licence_no,");
        tempSql.append(" ol.licence_org,");
        tempSql.append(" date_format(ol.licence_date, '%Y-%m-%d') as licence_date,");
        tempSql.append(" date_format(ol.initiallic_date, '%Y-%m-%d') as initiallic_date,");
        tempSql.append(" ol.corporate,");
        tempSql.append(" ol.register_addr,");
        tempSql.append(" ol.machine_addr,");
        tempSql.append(" date_format(ol.start_date, '%Y-%m-%d') as start_date,");
        tempSql.append(" date_format(ol.end_date, '%Y-%m-%d') as end_date,");
        tempSql.append(" ol.licence_status,");
        tempSql.append(" ol.operation_mode,");
        tempSql.append(" ol.application_time,");
        tempSql.append(" ol.audit_status,");
        tempSql.append(" ol.approved_by,");
        tempSql.append(" ol.create_by,");
        tempSql.append(" ol.create_time,");
        tempSql.append(" ol.edit_by,");
        tempSql.append(" ol.edit_time,");
        tempSql.append(" auditstatus.value as auditStatus,");
        tempSql.append(" operationmode.value as operationMode,");
        tempSql.append(" comname.ComName as comName,");
        tempSql.append(" concat(date_format(ol.start_date, '%Y-%m-%d'),' 至 ',date_format(ol.end_date, '%Y-%m-%d')) AS validityPeriod, ");
        tempSql.append(" ol.valid ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol  ");
        tempSql.append(" inner join  " + TableNameConstants.TABLE_CODE_VALUE + " auditstatus on ol.audit_status = auditstatus.id ");
        tempSql.append(" inner join  " + TableNameConstants.TABLE_CODE_VALUE + " operationmode on ol.operation_mode = operationmode.id ");
        tempSql.append(" left join  " + TableNameConstants.TABLE_SYS_ORG_COM + " comname on ol.licence_org = comname.ComID ");
        tempSql.append(" WHERE IFNULL(ol.valid, '1') = '1'  ");
       //  tempSql.append("AND auditstatus.code in ( \'"+ CodeTypeConstant.LIC_AUDIT_SUBMIT +"\') ");
        tempSql.append(" AND ol.enterprise_id = \'" + enterpriseId+"\'");
        //tempSql.append(" ol.start_date <= SYSDATE() ");
        //tempSql.append(" ol.end_date >= SYSDATE() ");//在当期有效期之内才可以
        if (paramMap.get("licence_no") != null) {//许可证编号查询条件
            tempSql.append(" and ol.licence_no like :licence_no ");
        }
        if (paging != null && paging.getLimit() != 0) {
            tempSql.append(" limit " + paging.getStart() + " , " + paging.getLimit());
        }
        List<OperationLicenceVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), paramMap, new BeanPropertyRowMapper<OperationLicenceVo>(OperationLicenceVo.class));
        return list;
    }
}