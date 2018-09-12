package com.mlsc.waste.wastecircle.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.yifeiwang.sms.model.ReleaseStatus;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.PropertyUtil;
import com.mlsc.waste.utils.TableNameConstants;
import com.mlsc.waste.utils.Util;
import com.mlsc.waste.wastecircle.dao.CoopMsgDao;
import com.mlsc.waste.wastecircle.model.CoopMsg;
import com.mlsc.waste.wastecircle.model.CoopMsgVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CoopMsgDaoImpl extends EntityDaoSupport<CoopMsg> implements CoopMsgDao {

    @Override
    public String saveCoopMsg(CoopMsg coopMsg) throws DaoAccessException {
        String msgId = Util.uuid32();
        save(coopMsg, msgId);
        return msgId;
    }


    @Override
    public int getCoopMsgCountByEnterId(CoopMsg coopMsg) throws DaoAccessException {
        StringBuffer sql = new StringBuffer("SELECT COUNT(1) ");
        sql.append("from ").append(TableNameConstants.TABLE_COOPERATION_MESSAGE);
        sql.append(" where valid=:valid and enterprise_Id =:enterprise_Id order by edit_time desc");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("valid", coopMsg.getValid());
        map.put("enterprise_Id", coopMsg.getEnterId());
        int count = namedParameterJdbcTemplate.queryForObject(sql.toString(), map, Integer.class);

        return count;
    }


    @Override
    public List<CoopMsgVo> getCoopMsgsForProductionUser(PagingParameter pagingParameter,
            RPCSysEnterpriseBaseVo enterpriseBaseVo,
            String entType, User user, String searchCondition, String cantonCode, String sortByCondition) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer(" select * from (");
        sql.append(" select distinct msg.id as id, ");
        sql.append(" msg.publisher_id as publisherId, ");
        sql.append(" msg.enterprise_Id as enterId, ");
        sql.append(" msg.message_detail as  messageDetail, ");
        sql.append(" msg.create_by as createBy, ");
        sql.append(" msg.create_time as  createTime, ");
        sql.append(" msg.edit_by as editBy, ");
        sql.append(" msg.edit_time as editTime, ");
        sql.append(" msg.valid as valid, ");
        sql.append(" upload_file.business_code as businessCode, ");
        sql.append(" upload_file.file_id as fileId, ");

        sql.append("(SELECT count(1) from operation_licence_detail ");
        sql.append(" where operation_licence_detail.licence_id = any(SELECT ol.id from operation_licence ol ");
        sql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE
                + " t1 on ol.audit_status = t1.id and t1.code= :pass");
        sql.append(" WHERE IFNULL(ol.valid, :valid) = :valid");
        sql.append(" AND ol.start_date <= SYSDATE() AND ol.end_date >= SYSDATE() ");
        sql.append(" AND ol.enterprise_id =msg.enterprise_Id)");
        sql.append(" and operation_licence_detail.valid = :valid)AS processableTotalCount,");
        map.put("valid", Constant.IS_VALID);
        map.put("pass", CodeTypeConstant.LIC_AUDIT_PASS);

        sql.append(" (SELECT count(a.wastecount) FROM (");
        sql.append(" SELECT count(detail_release.id) AS wastecount,coopmsg.message_id msgId ");
        sql.append(" FROM " + TableNameConstants.TABLE_DISPOSITION_CAPACITYDETAIL_RELEASE + " detail_release ");
        sql.append(" inner join " + TableNameConstants.TABLE_WASTE + " w ON w.id = detail_release.waste_id ");
        sql.append(" inner join " + TableNameConstants.TABLE_DISPOSITION_CAPACITYITEM_RELEASE
                + " item_release ON detail_release.capacityitem_id = item_release.id ");
        sql.append(" inner join " + TableNameConstants.TABLE_ENTERPRISE_WASTE
                + " ent_waste ON ent_waste.waste_id = detail_release.waste_id ");
        sql.append(" left join " + TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS
                + " coopmsg ON detail_release.capacity_release_id = coopmsg.business_id ");
        sql.append(" WHERE detail_release.valid = :valid ");
        sql.append(" AND ent_waste.valid = :valid ");
        sql.append(" AND item_release.valid = :valid AND coopmsg.business_type_code = 'dispositionCapacityRelease' ");
        sql.append(" AND ent_waste.enterprise_id = :myentId ");
        sql.append(
                " GROUP BY detail_release.waste_name_id,detail_release.waste_id,coopmsg.message_id)a where a"
                        + ".msgId=msg.id) as processableCount, ");

        sql.append(" sys_enterprise_base.entName as entName, ");
        sql.append(" cooperation_message_business.business_id as enterBusId, ");
        sql.append(" cooperation_message_business.business_type_code as busCode, ");
        sql.append(" sys_enterprise_base.CantonCode as CantonCode , ");
        if (enterpriseBaseVo != null) {
            sql.append(" fn_get_distance(:myposx,:myposy,sys_enterprise_base.posx,sys_enterprise_base.posy) as distance ");
            map.put("myposx", enterpriseBaseVo.getPosx());
            map.put("myposy", enterpriseBaseVo.getPosy());
        } else {
            sql.append(" :infinity as distance ");
        }
        map.put("infinity", Constant.DISTANCE_INFINITY);
        sql.append(" from ").append(TableNameConstants.TABLE_COOPERATION_MESSAGE).append(" msg ");
        sql.append(" join ").append(TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS);
        sql.append(" on msg.id = cooperation_message_business.message_id");
        // 产费企业
        if (CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equals(entType)) {
            sql.append(" and cooperation_message_business.business_type_code = 'dispositionCapacityRelease'");
            sql.append(" inner join ").append(TableNameConstants.TABLE_DISPOSITION_CAPACITY_RELEASE)
                    .append(" caprelease ");
            sql.append(
                    " ON caprelease.id = cooperation_message_business.business_id    AND sysdate() <= caprelease.release_enddate "
                    		+ " and caprelease.release_status = :releaseStatus "  );
            map.put("releaseStatus", ReleaseStatus.NORMAL.getStatusCode());

        }
        sql.append(" and cooperation_message_business.valid = '1' ");
        sql.append(" left join ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE);
        sql.append(" on sys_enterprise_base.entStatus = '0' and msg.enterprise_Id = sys_enterprise_base.entId ");

        sql.append(" left join ").append(TableNameConstants.TABLE_UPLOAD_FILE);
        sql.append(" on upload_file.reference_id = msg.enterprise_Id and  upload_file.file_type='a' ");

        // 产费企业
        if (CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equals(entType)) {
            sql.append(" join (");
            sql.append(" select distinct det.capacity_release_id as bus_id,");
            sql.append(" det.waste_id,waste.code,waste_name.name ");
            sql.append(" from ").append(TableNameConstants.TABLE_DISPOSITION_CAPACITYDETAIL_RELEASE).append(" AS det");
            sql.append(" INNER JOIN ").append(TableNameConstants.TABLE_DISPOSITION_CAPACITY_RELEASE);
            sql.append(" AS cap ON det.capacity_release_id = cap.id  AND cap.valid = :valid " );
            sql.append(" INNER JOIN ").append(TableNameConstants.TABLE_OPERATION_LICENCE);
            sql.append(" AS lic ON cap.operation_licence_id = lic.id   AND lic.start_date <= now() ");
            sql.append(" AND lic.end_date >= now() AND lic.valid = :valid ");
//            sql.append(" INNER JOIN ").append(TableNameConstants.TABLE_CODE_VALUE);
//            sql.append(" AS lic_status ON lic.audit_status = lic_status.id AND lic_status.code = :pass ");
            sql.append(" join ").append(TableNameConstants.TABLE_WASTE);
            sql.append(" on waste.id = det.waste_id ");
            sql.append(" join ").append(TableNameConstants.TABLE_ENTERPRISE_WASTE);
            sql.append(" on enterprise_waste.enterprise_id =:myentId ");
            sql.append(" and enterprise_waste.valid = '1' ");
            sql.append(" and det.waste_id = enterprise_waste.waste_id ");
            sql.append(" LEFT join waste_name on det.waste_name_id = waste_name.id ");
            sql.append(" where det.valid = 1 ");
            sql.append(" ) as bus_info ");
            sql.append(" on bus_info.bus_id = cooperation_message_business.business_id ");
            map.put("myentId", user.getEnterpriseId());
        }

        // 检索框是否为空
        if (StringUtils.isNotBlank(searchCondition)) {
            sql.append(
                    " AND (bus_info.CODE like :searchCondition or sys_enterprise_base.entName like :searchCondition "
                            + "or bus_info.name like :searchCondition) ");
            map.put("searchCondition", "%" + searchCondition + "%");
        }

        sql.append(" where  msg.valid = '1' ");
        sql.append(" and sys_enterprise_base.posx between :longitudeLower AND :longitudeUpper ");
        sql.append(" and sys_enterprise_base.posy between :latitudeLower AND :latitudeUpper ");
        map.put("longitudeUpper", Double.parseDouble(PropertyUtil.getValue("longitudeUpper")));
        map.put("longitudeLower", Double.parseDouble(PropertyUtil.getValue("longitudeLower")));
        map.put("latitudeUpper", Double.parseDouble(PropertyUtil.getValue("latitudeUpper")));
        map.put("latitudeLower", Double.parseDouble(PropertyUtil.getValue("latitudeLower")));
        // 区域是否为空
        if (StringUtils.isNotBlank(cantonCode)) {
            sql.append(" and CantonCode like :cantonCode ");
            map.put("cantonCode", cantonCode + "%");
        }

        sql.append(" ) msg");

        if (StringUtils.isNotBlank(sortByCondition)) {
            if (sortByCondition.equals("sortByDistance")) {
                sql.append(" order by msg.distance ");
            } else if (sortByCondition.equals("sortByEditTime")) {
                sql.append(" order by msg.editTime desc ");
            } else if (sortByCondition.equals("sortByAllProcessWaste")) {
                sql.append(" order by msg.processableTotalCount desc ");
            } else if (sortByCondition.equals("sortByProcessableWaste")) {
                sql.append(" order by msg.processableCount desc ");
            }
        } else {
            sql.append(" order by msg.editTime desc");
        }
        sql.append(" limit " + pagingParameter.getStart() + "," + +pagingParameter.getLimit());
        List<CoopMsgVo> msgList = namedParameterJdbcTemplate
                .query(sql.toString(), map, new BeanPropertyRowMapper<CoopMsgVo>(CoopMsgVo.class));
        return msgList;
    }

    @Override
    public int getCoopMsgCountForProductionUser(PagingParameter pagingParameter,
            RPCSysEnterpriseBaseVo enterpriseBaseVo,
            String entType, User user, String searchCondition, String cantonCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer(" select count(*) from (");
        sql.append(" select distinct msg.id as id, ");
        sql.append(" msg.publisher_id as publisherId, ");
        sql.append(" msg.enterprise_Id as enterId, ");
        sql.append(" msg.message_detail as  messageDetail, ");
        sql.append(" msg.create_by as createBy, ");
        sql.append(" msg.create_time as  createTime, ");
        sql.append(" msg.edit_by as editBy, ");
        sql.append(" msg.edit_time as editTime, ");
        sql.append(" msg.valid as valid, ");
        sql.append(" sys_enterprise_base.entName as entName, ");
        sql.append(" cooperation_message_business.business_id as enterBusId, ");
        sql.append(" cooperation_message_business.business_type_code as busCode, ");
        sql.append(" sys_enterprise_base.CantonCode as CantonCode , ");
        if (enterpriseBaseVo != null) {
            sql.append(" fn_get_distance(:myposx,:myposy,sys_enterprise_base.posx,sys_enterprise_base.posy) as distance ");
            map.put("myposx", enterpriseBaseVo.getPosx());
            map.put("myposy", enterpriseBaseVo.getPosy());
        } else {
            sql.append(" :infinity as distance ");
        }
        map.put("infinity", Constant.DISTANCE_INFINITY);
        sql.append(" from ").append(TableNameConstants.TABLE_COOPERATION_MESSAGE).append(" msg ");
        sql.append(" join ").append(TableNameConstants.TABLE_COOPERATION_MESSAGE_BUSINESS);
        sql.append(" on msg.id = cooperation_message_business.message_id");
        // 产费企业
        if (CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equals(entType)) {
            sql.append(" and cooperation_message_business.business_type_code = 'dispositionCapacityRelease'");
            sql.append(" inner join ").append(TableNameConstants.TABLE_DISPOSITION_CAPACITY_RELEASE).append(" caprelease ");
            sql.append(" ON caprelease.id = cooperation_message_business.business_id    AND sysdate() <= caprelease.release_enddate ");
            sql.append(" and caprelease.release_status <> :releaseStatus ");
            map.put("releaseStatus", ReleaseStatus.TERMINATION.getStatusCode());
        }
        sql.append(" and cooperation_message_business.valid = '1' ");
        sql.append(" left join ").append(TableNameConstants.TABLE_SYS_ENTERPRISE_BASE);
        sql.append(" on sys_enterprise_base.entStatus = '0' and msg.enterprise_Id = sys_enterprise_base.entId ");

        // 产费企业
        if (CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION.equals(entType)) {
            sql.append(" join (");
            sql.append(" select distinct disposition_capacitydetail_release.capacity_release_id as bus_id,");
            sql.append(" disposition_capacitydetail_release.waste_id,waste.code,waste_name.name ");
            sql.append(" from ").append(TableNameConstants.TABLE_DISPOSITION_CAPACITYDETAIL_RELEASE);
            sql.append(" join ").append(TableNameConstants.TABLE_WASTE);
            sql.append(" on waste.id = disposition_capacitydetail_release.waste_id ");
            sql.append(" join ").append(TableNameConstants.TABLE_ENTERPRISE_WASTE);
            sql.append(" on enterprise_waste.enterprise_id =:myentId ");
            sql.append(" and enterprise_waste.valid = '1' ");
            sql.append(" and disposition_capacitydetail_release.waste_id = enterprise_waste.waste_id ");
            sql.append(" LEFT join waste_name on disposition_capacitydetail_release.waste_name_id = waste_name.id ");
            sql.append(" where disposition_capacitydetail_release.valid = 1 ");
            sql.append(" ) as bus_info ");
            sql.append(" on bus_info.bus_id = cooperation_message_business.business_id ");
            map.put("myentId", user.getEnterpriseId());
        }

        // 检索框是否为空
        if (StringUtils.isNotBlank(searchCondition)) {
            sql.append(
                    " AND (bus_info.CODE like :searchCondition or sys_enterprise_base.entName like :searchCondition "
                            + "or bus_info.name like :searchCondition) ");
            map.put("searchCondition", "%" + searchCondition + "%");
        }

        sql.append(" where 1= 1 and msg.valid = '1' ");
        sql.append(" and sys_enterprise_base.posx between :longitudeLower AND :longitudeUpper ");
        sql.append(" and sys_enterprise_base.posy between :latitudeLower AND :latitudeUpper ");
        map.put("longitudeUpper", Double.parseDouble(PropertyUtil.getValue("longitudeUpper")));
        map.put("longitudeLower", Double.parseDouble(PropertyUtil.getValue("longitudeLower")));
        map.put("latitudeUpper", Double.parseDouble(PropertyUtil.getValue("latitudeUpper")));
        map.put("latitudeLower", Double.parseDouble(PropertyUtil.getValue("latitudeLower")));
        // 区域是否为空
        if (StringUtils.isNotBlank(cantonCode)) {
            sql.append(" and CantonCode like :cantonCode ");
            map.put("cantonCode", cantonCode + "%");
        }
        sql.append(" ) msg");
        int count = namedParameterJdbcTemplate.queryForObject(sql.toString(), map, Integer.class);
        return count;
    }



}
