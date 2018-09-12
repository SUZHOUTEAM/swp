package com.mlsc.waste.dispositioncapacityrelease.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.dispositioncapacityrelease.dao.DispositionCapacityDetailReleaseDao;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityDetailRelease;
import com.mlsc.waste.dispositioncapacityrelease.model.DispositionCapacityDetailReleaseVo;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.TableNameConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DispositionCapacityDetailReleaseDaoImpl extends EntityDaoSupport<DispositionCapacityDetailRelease> implements DispositionCapacityDetailReleaseDao {

    @Override
    public List<DispositionCapacityDetailReleaseVo> getEightCodeListByIds(String entId, String messageId, String dispCode, String wasteTypeId, String wasteCode, PagingParameter paging) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select DISTINCT detail_release.id as detailReleaseId, ");
        tempSql.append(" detail_release.waste_id,");
        tempSql.append(" w.code as eightCode,");
        tempSql.append(" detail_release.capacityitem_id as capacityitemId,");
        tempSql.append(" dispostionType.code as disType,");
        tempSql.append(" dispostionType.value as distValue,");
        tempSql.append(" w.waste_type_id,");
        tempSql.append(" wasteName.name as wasteName,");

        tempSql.append(" ifnull(ent_waste.valid, 0) as is_dis ");
        tempSql.append(" from " + TableNameConstants.TABLE_DISPOSITION_CAPACITYDETAIL_RELEASE + " detail_release ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_WASTE + " w on w.id = detail_release.waste_id ");
        if (StringUtils.isNotBlank(wasteCode)) {
            tempSql.append(" and w.code like :wasteCode ");
        }
        tempSql.append(" left join ").append(TableNameConstants.TABLE_WASTE_NAME).append(" wasteName ");
        tempSql.append(" on w.id = wasteName.waste_id ");

        tempSql.append(" left join " + TableNameConstants.TABLE_ENTERPRISE_WASTE + " ent_waste on ent_waste.waste_id = detail_release.waste_id and ent_waste.valid = :valid ");
        tempSql.append(" and ent_waste.enterprise_id = :entId ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_DISPOSITION_CAPACITYITEM_RELEASE + " item_release on detail_release.capacityitem_id = item_release.id ");
        tempSql.append(" and item_release.valid = :valid ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " dispostionType on item_release.disposition_type = dispostionType.id ");
        if (StringUtils.isNotBlank(dispCode)) {
            tempSql.append(" and dispostionType.code = :dispCode ");
        }
        tempSql.append(" where detail_release.capacity_release_id ");
        tempSql.append(" in (select business_id from " + TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS + " coopmsg where coopmsg.message_id = :messageId and coopmsg.business_type_code = :businessTypeCode) ");
        if (StringUtils.isNotBlank(wasteTypeId)) {
            tempSql.append(" and w.waste_type_id = :wasteTypeId ");
        }
        tempSql.append(" and detail_release.valid = :valid ");
        tempSql.append("AND ent_waste.valid = :valid");

        tempSql.append(" order by is_dis desc,right(w.code, 2),substring(w.code, 5, 3) ");
        if (paging.getLimit() != 0) {
            tempSql.append(" limit " + paging.getStart() + " , " + paging.getLimit());
        }

        map.put("valid", Constant.IS_VALID);
        map.put("entId", entId);
        map.put("messageId", messageId);
        map.put("dispCode", dispCode);
        map.put("wasteTypeId", wasteTypeId);
        map.put("wasteCode", "%" + wasteCode + "%");
        map.put("businessTypeCode", "dispositionCapacityRelease");
        List<DispositionCapacityDetailReleaseVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map, new BeanPropertyRowMapper<DispositionCapacityDetailReleaseVo>(DispositionCapacityDetailReleaseVo.class));
        return list;
    }


    @Override
    public List<DispositionCapacityDetailReleaseVo> getEightCodeListByIds(String entId, String releaseId) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select DISTINCT detail_release.id as detailReleaseId, ");
        tempSql.append(" detail_release.waste_id,");
        tempSql.append(" w.code as eightCode,");
        tempSql.append(" detail_release.capacityitem_id as capacityitemId,");
        tempSql.append(" dispostionType.code as disType,");
        tempSql.append(" dispostionType.value as distValue,");
        tempSql.append(" dispostionType.id as dispositionId,");
        tempSql.append("  concat(dispostionType.code,' ',dispostionType.value) as dispositionName , ");
        tempSql.append(" w.waste_type_id,");
        tempSql.append(" detail_release.waste_name_id ,");
        tempSql.append(" ifnull(wasteName.name,'-') as wasteName,");
        tempSql.append(" ifnull(ent_waste.valid, 0) as is_dis, ");
        tempSql.append(" detail_release.capacity_release_id ");
        tempSql.append(" from " + TableNameConstants.TABLE_DISPOSITION_CAPACITYDETAIL_RELEASE + " detail_release ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_WASTE + " w on w.id = detail_release.waste_id ");
        tempSql.append(" left join ").append(TableNameConstants.TABLE_WASTE_NAME).append(" wasteName ");
        tempSql.append(" on detail_release.waste_name_id = wasteName.id ");
        tempSql.append(" left join ent_waste on ent_waste.wasteId = detail_release.waste_id and ent_waste.deleteFlag = '0' ");
        tempSql.append(" and ent_waste.entId = :entId ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_DISPOSITION_CAPACITYITEM_RELEASE + " item_release on detail_release.capacityitem_id = item_release.id ");
        tempSql.append(" and item_release.valid = :valid ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " dispostionType on item_release.disposition_type = dispostionType.id ");

        tempSql.append(" where detail_release.capacity_release_id = :releaseId");
        tempSql.append(" and detail_release.valid = :valid ");
        tempSql.append(" AND ent_waste.valid = :valid");
        tempSql.append(" order by w.code ");

        map.put("valid", Constant.IS_VALID);
        map.put("entId", entId);
        map.put("releaseId", releaseId);

        map.put("businessTypeCode", "dispositionCapacityRelease");
        List<DispositionCapacityDetailReleaseVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map, new BeanPropertyRowMapper<DispositionCapacityDetailReleaseVo>(DispositionCapacityDetailReleaseVo.class));
        return list;
    }

    @Override
    public DispositionCapacityDetailRelease getDispositioncapacityDetailReleaseByIds(String capacityReleaseId, String capacityItemReleaseId, String wasteId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer(" select detail_release.id, ");
        tempSql.append(" detail_release.capacity_release_id, ");
        tempSql.append(" detail_release.capacityitem_id, ");
        tempSql.append(" detail_release.waste_id, ");
        tempSql.append(" detail_release.used_quantity ");
        tempSql.append(" from " + TableNameConstants.TABLE_DISPOSITION_CAPACITYDETAIL_RELEASE + " detail_release ");
        tempSql.append(" where detail_release.valid =:valid ");
        tempSql.append(" and detail_release.capacity_release_id =:capacityReleaseId ");
        tempSql.append(" and detail_release.capacityitem_id =:capacityItemReleaseId ");
        tempSql.append(" and detail_release.waste_id =:wasteId ");
        map.put("capacityReleaseId", capacityReleaseId);
        map.put("capacityItemReleaseId", capacityItemReleaseId);
        map.put("wasteId", wasteId);
        map.put("valid", Constant.IS_VALID);
        List<DispositionCapacityDetailRelease> query = namedParameterJdbcTemplate.query(tempSql.toString(), map, new BeanPropertyRowMapper<DispositionCapacityDetailRelease>(DispositionCapacityDetailRelease.class));
        DispositionCapacityDetailRelease capacityDetailRelease = null;
        if (query != null && query.size() != 0) {
            capacityDetailRelease = query.get(0);
        }
        return capacityDetailRelease;
    }
}
