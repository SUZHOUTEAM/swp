package com.mlsc.waste.licence.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.mobileservice.model.DispositionCapacityDetailReleaseVo;
import com.mlsc.waste.licence.dao.LicenceDetailDao;
import com.mlsc.waste.licence.model.OperationLicenceDetail;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LicenceDetailDaoImpl extends EntityDaoSupport<OperationLicenceDetail> implements LicenceDetailDao {
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;

    @Override
    public void deleteDetails(String licence_id, String itemId) throws DaoAccessException {
        String sql = "delete from " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " where  licence_id =? and operation_item_id =?";
        jdbcTemplate.update(sql, licence_id, itemId);
    }

    @Override
    public int checkLicenceStatus(String licenceId, String dispositionType, String wasteTypeId, String wasteId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();

        StringBuffer tempSql = new StringBuffer(" select count(distinct item.id) ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " detail ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " item on detail.operation_item_id = item.id ");
        tempSql.append(" where ifnull(item.valid,:valid) = :valid and item.licence_id = :licenceId ");
        tempSql.append(" and ifnull(detail.valid,:valid) = :valid and detail.licence_id = :licenceId and detail.waste_type = :wasteTypeId and detail.waste_id = :wasteId ");
        if (StringUtils.isNotBlank(dispositionType)) {
            tempSql.append(" and item.disposition_type = :dispositionType ");
            map.put("dispositionType", dispositionType);
        }
        map.put("valid", Constant.IS_VALID);
        map.put("licenceId", licenceId);
        map.put("wasteTypeId", wasteTypeId);
        map.put("wasteId", wasteId);
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), map, Integer.class);
    }

    @Override
    public List<Map<String, Object>> listDispositionType(String licenceId, String planQuantity, String wasteTypeId, String wasteId) throws DaoAccessException {
        Map<String, Object> paraMap = new HashMap<String, Object>();

        StringBuffer tempSql = new StringBuffer(" select distinct item.id as itemId,");
        tempSql.append(" ct.id as dispositionTypeId, ");
        tempSql.append(" CONCAT(ct.code,'-',ct.value) as dispositionTypeLabel ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " detail ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " item on detail.operation_item_id = item.id ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " ct on item.disposition_type = ct.id ");
        tempSql.append(" where ifnull(item.valid,:valid) = :valid and item.licence_id = :licenceId ");
        tempSql.append(" and ifnull(detail.valid,:valid) = :valid and detail.licence_id = :licenceId and detail.waste_id = :wasteId ");
        if (StringUtils.isNotBlank(wasteTypeId)) {
            tempSql.append(" and detail.waste_type = :wasteTypeId ");
            paraMap.put("wasteTypeId", wasteTypeId);
        }
        tempSql.append(" and detail.waste_id = :wasteId ");

        paraMap.put("valid", Constant.IS_VALID);
        paraMap.put("licenceId", licenceId);
        paraMap.put("planQuantity", planQuantity);
        paraMap.put("wasteId", wasteId);

        List<Map<String, Object>> searchList = search(tempSql.toString(), paraMap);

        return searchList;
    }

    @Override
    public String getProcessQuantityByWasteIdAndWasteTypeId(String licenceId, String dispositionType, String wasteTypeId, String wasteId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();

        StringBuffer tempSql = new StringBuffer(" select sum(item.approved_quantity - item.excuted_quantity)");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " detail ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " item on detail.operation_item_id = item.id ");
        tempSql.append(" where ifnull(item.valid,:valid) = :valid and item.licence_id = :licenceId ");
        tempSql.append(" and ifnull(detail.valid,:valid) = :valid and detail.licence_id = :licenceId and detail.waste_type = :wasteTypeId and detail.waste_id = :wasteId and item.disposition_type = :dispositionType");

        map.put("valid", Constant.IS_VALID);
        map.put("licenceId", licenceId);
        map.put("wasteTypeId", wasteTypeId);
        map.put("wasteId", wasteId);
        map.put("dispositionType", dispositionType);

        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), map, String.class);
    }

    @Override
    public String getLicenceDetailStatus(String licenceId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();

        StringBuffer tempSql = new StringBuffer(" select count(1)");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE + " licence ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " detail on licence.id = detail.licence_id");
        tempSql.append(" inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " item on detail.operation_item_id = item.id ");
        tempSql.append(" where licence.end_date >=sysdate() and licence.id = :licenceId  and  ifnull(licence.valid,:valid) = :valid and ifnull(detail.valid,:valid) = :valid and ifnull(item.valid,:valid) = :valid ");

        map.put("valid", Constant.IS_VALID);
        map.put("licenceId", licenceId);

        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), map, String.class);
    }

    @Override
    public void deleteLicenceDetailByLicenceIds(List<String> licenceIds, Map<String, Object> paramMap) throws DaoAccessException {
        StringBuffer tempSql = new StringBuffer("update " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL);
        tempSql.append(" set valid = :valid, ");
        tempSql.append(" edit_by = :editBy, ");
        tempSql.append(" edit_time = :editTime ");
        tempSql.append(" where licence_id in (:licenceIds) ");
        namedParameterJdbcTemplate.update(tempSql.toString(), paramMap);
    }

    @Override
    public int isHasLicenceDetail(String licenceId, String licenceItemId, String wasteTypeId, String wasteId, String wasteNameId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select count(1) ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " detail ");
        tempSql.append(" where ifnull(detail.valid,:valid) = :valid and detail.licence_id = :licenceId and detail.operation_item_id = :licenceItemId and detail.waste_type = :wasteTypeId and detail.waste_id = :wasteId ");
        tempSql.append(" and ifnull(detail.waste_name_id,'') = :wasteNameId ");

        map.put("valid", Constant.IS_VALID);
        map.put("licenceId", licenceId);
        map.put("licenceItemId", licenceItemId);
        map.put("wasteTypeId", wasteTypeId);
        map.put("wasteId", wasteId);
        map.put("wasteNameId", wasteNameId);
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), map, Integer.class);
    }

    @Override
    public List<DispositionCapacityDetailReleaseVo> getDispositionCapacityDetailReleaseCanList(String licenceId, String entId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select DISTINCT operation_licence_detail.waste_id, ");
        tempSql.append(" operation_licence_detail.waste_name_id,");
        tempSql.append(" operation_licence_item.disposition_type as disposition_type_id ,");
        tempSql.append(" waste_name. NAME AS waste_name ,");
        tempSql.append(" operation_licence_item.disposition_type,");
        tempSql.append(" dispName.value as dispositionTypeValue ,");
        tempSql.append(" dispName.code as dispositionTypeCode,");
        tempSql.append(" waste.code as wasteCode,");
        tempSql.append(" waste_type.description as waste_type_description,");
        tempSql.append(" waste_type.code as waste_type_code,");
        tempSql.append("if (operation_licence_detail.waste_id IS NOT NULL,1,0) as flag ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " operation_licence_detail ");
        tempSql.append(" left join " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " operation_licence_item ");
        tempSql.append(" on operation_licence_detail.operation_item_id = operation_licence_item.id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_CODE_VALUE + " dispName ");
        tempSql.append(" on operation_licence_item.disposition_type = dispName.id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_WASTE + " waste ");
        tempSql.append(" on operation_licence_detail.waste_id = waste.id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_WASTE_NAME + " waste_name ");
        tempSql.append(" on waste_name.waste_id = operation_licence_detail.waste_id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_WASTE_TYPE + " waste_type ");
        tempSql.append(" on waste.waste_type_id = waste_type.id ");
        tempSql.append(" where operation_licence_detail.licence_id = :licenceId ");
        tempSql.append(" and operation_licence_detail.waste_id in (");
        tempSql.append(" select enterprise_waste.waste_id from " + TableNameConstants.TABLE_ENTERPRISE_WASTE);
        tempSql.append(" where  enterprise_waste.enterprise_id = :entId and enterprise_waste.valid ='1') group by operation_licence_detail.waste_id");
        map.put("licenceId", licenceId);
        map.put("entId", entId);
        List<DispositionCapacityDetailReleaseVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map, new BeanPropertyRowMapper<DispositionCapacityDetailReleaseVo>(DispositionCapacityDetailReleaseVo.class));
        return list;
    }

    @Override
    public List<DispositionCapacityDetailReleaseVo> getDispositionCapacityDetailReleaseNoList(String licenceId, String entId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select DISTINCT operation_licence_detail.waste_id, ");
        tempSql.append("operation_licence_detail.waste_name_id,");
        tempSql.append("operation_licence_item.disposition_type as disposition_type_id ,");
        tempSql.append("waste_name. NAME AS waste_name ,");
        tempSql.append("operation_licence_item.disposition_type,");
        tempSql.append("dispName.value as dispositionTypeValue ,");
        tempSql.append("dispName.code as dispositionTypeCode,");
        tempSql.append("waste.code as wasteCode,");
        tempSql.append("waste_type.description as waste_type_description,");
        tempSql.append("waste_type.code as waste_type_code,");
        tempSql.append("if (operation_licence_detail.waste_id IS NOT NULL,0,1) as flag ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " operation_licence_detail ");
        tempSql.append(" left join " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " operation_licence_item ");
        tempSql.append(" on operation_licence_detail.operation_item_id = operation_licence_item.id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_CODE_VALUE + " dispName ");
        tempSql.append(" on operation_licence_item.disposition_type = dispName.id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_WASTE + " waste ");
        tempSql.append(" on operation_licence_detail.waste_id = waste.id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_WASTE_NAME + " waste_name ");
        tempSql.append(" on waste_name.waste_id = operation_licence_detail.waste_id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_WASTE_TYPE + " waste_type ");
        tempSql.append(" on waste.waste_type_id = waste_type.id ");
        tempSql.append(" where operation_licence_detail.licence_id = :licenceId ");
        tempSql.append(" and operation_licence_detail.waste_id  not in (");
        tempSql.append(" select enterprise_waste.waste_id from " + TableNameConstants.TABLE_ENTERPRISE_WASTE);
        tempSql.append(" where  enterprise_waste.enterprise_id = :entId and enterprise_waste.valid ='1') group by operation_licence_detail.waste_id");
        map.put("licenceId", licenceId);
        map.put("entId", entId);
        List<DispositionCapacityDetailReleaseVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map, new BeanPropertyRowMapper<DispositionCapacityDetailReleaseVo>(DispositionCapacityDetailReleaseVo.class));
        return list;
    }
}
