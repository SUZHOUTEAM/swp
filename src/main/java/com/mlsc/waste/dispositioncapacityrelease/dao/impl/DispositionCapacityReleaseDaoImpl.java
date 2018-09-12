package com.mlsc.waste.dispositioncapacityrelease.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityReleaseDao;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityRelease;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityReleaseVo;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DispositionCapacityReleaseDaoImpl extends EntityDaoSupport<DispositionCapacityRelease> implements DispositionCapacityReleaseDao {

    /**
     * 根据许可证id查询出 处置能力发布信息
     */
    @Override
    public DispositionCapacityRelease getDispositionCapacityReleaseByLicId(String licId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select capacityrelease.id as id, ");
        tempSql.append(" capacityrelease.release_enterprise_id  as releaseEnterpriseId, ");
        tempSql.append(" capacityrelease.capacity_release_code  as capacityReleaseCode, ");
        tempSql.append(" capacityrelease.release_startdate  as releaseStartdate, ");
        tempSql.append(" capacityrelease.release_enddate  as releaseEnddate, ");
        tempSql.append(" capacityrelease.operation_licence_id  as operationLicenceId, ");
        tempSql.append(" capacityrelease.remark  as  remark, ");
        tempSql.append(" capacityrelease.versioncode  as versionCode, ");
        tempSql.append(" capacityrelease.create_by  as createBy, ");
        tempSql.append(" capacityrelease.create_time  as createTime, ");
        tempSql.append(" capacityrelease.edit_by  as editBy, ");
        tempSql.append(" capacityrelease.edit_time  as editTime, ");
        tempSql.append(" capacityrelease.valid  as valid ");
        tempSql.append(" from " + TableNameConstants.TABLE_DISPOSITION_CAPACITY_RELEASE + " capacityrelease ");
        tempSql.append(" where capacityrelease.operation_licence_id = :operation_licence_id ");
        tempSql.append(" and capacityrelease.valid = :valid ");
        map.put("operation_licence_id", licId);
        map.put("valid", Constant.IS_VALID);
        List<DispositionCapacityRelease> list = namedParameterJdbcTemplate.query(tempSql.toString(), map, new BeanPropertyRowMapper<DispositionCapacityRelease>(DispositionCapacityRelease.class));
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<DispositionCapacityReleaseVo> getDispositionCapacityReleaseDetailInfoByReleaseId(String releaseId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();

        StringBuffer tempSql = new StringBuffer(" select");
        tempSql.append(" item.capacity_release_id, ");
        tempSql.append(" item.id as item_id, ");
        tempSql.append(" item.disposition_type as dispositionTypeId,");
        tempSql.append(" dispositionType.value as disposition_type_name,");
        tempSql.append(" dispositionType.code as disposition_code,");
        tempSql.append(" item.quota_quantity,");
        tempSql.append(" type.id as waste_type_code_id, ");
        tempSql.append(" type.code as waste_type_code ");
        tempSql.append(" from  disposition_capacityitem_release item  ");
        tempSql.append(" left join disposition_capacitydetail_release detail on item.id = detail.capacityitem_id ");
        tempSql.append(" left join waste waste  on detail.waste_id = waste.id ");
        tempSql.append(" left join waste_type type on waste.waste_type_id = type.id  ");
        tempSql.append(" left join code_value dispositionType   on item.disposition_type = dispositionType.id ");
        tempSql.append(" where IFNULL(detail.valid,:valid) = :valid and item.capacity_release_id = :releaseId ");
        tempSql.append(" group by item.capacity_release_id,item.id,item.disposition_type,disposition_type_name,item.quota_quantity,waste_type_code_id,waste_type_code ");
        map.put("valid", Constant.IS_VALID);
        map.put("releaseId", releaseId);
        List<DispositionCapacityReleaseVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map, new BeanPropertyRowMapper<DispositionCapacityReleaseVo>(DispositionCapacityReleaseVo.class));

        if (list != null && list.size() == 1) {
            if (StringUtils.isBlank(list.get(0).getCapacityReleaseId())) {
                list = new ArrayList<DispositionCapacityReleaseVo>();
            }
        }

        return list;
    }

    @Override
    public OperationLicence getLicenceNoByReleaseId(String releaseId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer("SELECT licence.licence_no ,licence.start_date,licence.end_date ");
        tempSql.append("FROM ").append(TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS).append(" business");
        tempSql.append(" INNER JOIN ").append(TableNameConstants.TABLE_DISPOSITION_CAPACITY_RELEASE).append(" capacityRelease ON business.business_id = capacityRelease.id");
        tempSql.append(" INNER JOIN ").append(TableNameConstants.TABLE_OPERATION_LICENCE).append(" licence ON capacityRelease.operation_licence_id = licence.id");
        tempSql.append(" WHERE business.message_id = :releaseId");

        map.put("releaseId", releaseId);

        List<OperationLicence> list = namedParameterJdbcTemplate.query(tempSql.toString(), map, new BeanPropertyRowMapper<OperationLicence>(OperationLicence.class));

        if (list != null && list.size() >= 1) {
            return list.get(0);
        }

        return null;

    }

}
