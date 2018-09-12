/**
 * 
 */
package com.mlsc.waste.licence.dao.impl;

import com.mlsc.epdp.common.base.db.dao.EntityDaoSupport;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.licence.dao.LicenceApprovedDao;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.model.OperationLicenceDetailVo;
import com.mlsc.waste.licence.model.OperationLicenceItemVo;
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
 * @author sunjl
 * 
 */
@Repository
public class LicenceApprovedDaoImpl extends EntityDaoSupport<OperationLicence>
        implements LicenceApprovedDao {
    @Autowired
    private NamedParameterJdbcTemplate namedjdbctemp;
    

    /**
     * 查询审核许可证列表
     * @param where
     * @param paramMap
     * @param paging
     * @return
     * @throws DaoAccessException
     */
    @Override
    public List<OperationLicenceVo> list(String where, Map<String, Object> paramMap, PagingParameter paging) throws DaoAccessException {
        StringBuffer tempSql = new StringBuffer(" select * from ( ");
        tempSql.append(" select ol.id,");
        tempSql.append(" ol.enterprise_name,");
        tempSql.append(" ol.licence_no,");
        tempSql.append(" cv.value as auditStatus,");
        tempSql.append(" cv1.VALUE as operationMode,");
        tempSql.append(" sys_org_com.comName as licence_org,");
        tempSql.append(" DATE_FORMAT(ol.application_time,'%Y-%m-%d') as application_time, ");
        tempSql.append(" concat(DATE_FORMAT(ol.start_date,'%Y-%m-%d'),' 至 ',DATE_FORMAT(ol.end_date,'%Y-%m-%d')) AS validityPeriod ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " cv on ol.audit_status = cv.id ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " cv1 on ol.operation_mode = cv1.id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_SYS_ORG_COM + " sys_org_com on ol.licence_org = sys_org_com.comId ");
        tempSql.append(" where ol.valid = :isvalid and cv.code in (:submit,:pass,:refused,:termination) and cv1.code in (:collection,:includeall) ");
        tempSql.append( where + " order by ol.edit_time desc ) tmp ");
        
        paramMap.put("submit", CodeTypeConstant.LIC_AUDIT_SUBMIT);
        paramMap.put("isvalid", Constant.IS_VALID);
        paramMap.put("create", CodeTypeConstant.LIC_AUDIT_CREATE);
        paramMap.put("pass", CodeTypeConstant.LIC_AUDIT_PASS);
        paramMap.put("refused", CodeTypeConstant.LIC_AUDIT_REFUSED);
        paramMap.put("collection", CodeTypeConstant.LIC_MODE_COLLECTION);
        paramMap.put("includeall", CodeTypeConstant.LIC_MODE_INCLUDEALL);
        paramMap.put("termination", CodeTypeConstant.LIC_AUDIT_TERMINATION);
        
        tempSql = new StringBuffer(tempSql.toString().replaceAll("enterpriseName like :enterpriseName", " enterprise_name like :enterpriseName "));
        tempSql = new StringBuffer(tempSql.toString().replaceAll("auditStatus like :auditStatus", " audit_status like :auditStatus "));
        tempSql = new StringBuffer(tempSql.toString().replaceAll("operationMode like :operationMode", " operation_mode like :operationMode "));
        return namedParameterJdbcTemplate.query(tempSql.toString() + " limit "
                + paging.getStart() + " , " + paging.getLimit(), paramMap,
                new BeanPropertyRowMapper<OperationLicenceVo>(
                        OperationLicenceVo.class));
    }
    
    /**
     * 查询审核许可证记录数
     * @param where 查询条件 
     * @param paramMap 查询条件参数
     * @return
     * @throws DaoAccessException
     */
    @Override
    public Integer count(String where, Map<String, Object> paramMap) throws DaoAccessException{
        
        StringBuffer tempSql = new StringBuffer(" select count(1) ");
        tempSql.append(" from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " cv on ol.audit_status = cv.id ");
        tempSql.append(" inner join " + TableNameConstants.TABLE_CODE_VALUE + " cv1 on ol.operation_mode = cv1.id ");
        tempSql.append(" left join " + TableNameConstants.TABLE_SYS_ORG_COM + " sys_org_com on ol.licence_org = sys_org_com.comId ");
        tempSql.append(" where ol.valid = :isvalid and cv.code in (:submit,:pass,:refused,:termination) and cv1.code in (:collection,:includeall) ");
        tempSql.append( where + " ");
        
        paramMap.put("submit", CodeTypeConstant.LIC_AUDIT_SUBMIT);
        paramMap.put("isvalid", Constant.IS_VALID);
        paramMap.put("create", CodeTypeConstant.LIC_AUDIT_CREATE);
        paramMap.put("pass", CodeTypeConstant.LIC_AUDIT_PASS);
        paramMap.put("refused", CodeTypeConstant.LIC_AUDIT_REFUSED);
        paramMap.put("collection", CodeTypeConstant.LIC_MODE_COLLECTION);
        paramMap.put("includeall", CodeTypeConstant.LIC_MODE_INCLUDEALL);
        paramMap.put("termination", CodeTypeConstant.LIC_AUDIT_TERMINATION);
        
        tempSql = new StringBuffer(tempSql.toString().replaceAll("enterpriseName like :enterpriseName", " enterprise_name like :enterpriseName "));
        tempSql = new StringBuffer(tempSql.toString().replaceAll("auditStatus like :auditStatus", " audit_status like :auditStatus "));
        tempSql = new StringBuffer(tempSql.toString().replaceAll("operationMode like :operationMode", " operation_mode like :operationMode "));
        
        return namedParameterJdbcTemplate.queryForObject(tempSql.toString(), paramMap, Integer.class);
    }

    /**
     * 根据id获取许可证信息
     * @param id
     * @return
     * @throws DaoAccessException
     */
    @Override
    public OperationLicenceVo queryById(String id) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer("select ol.id, ");
        tempSql.append(" ol.enterprise_id,");
        tempSql.append(" ol.licence_no,");
        tempSql.append(" orgcome.ComName as licence_org,");
        tempSql.append(" DATE_FORMAT(ol.licence_date,'%Y-%m-%d') licence_date , ");
        tempSql.append(" DATE_FORMAT(ol.initiallic_date,'%Y-%m-%d') initiallic_date,");
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
        tempSql.append(" ol.valid,");
        tempSql.append(" ol.enterprise_name as enterpriseName,");
        tempSql.append("concat(DATE_FORMAT(ol.start_date,'%Y-%m-%d'),' 至 ',DATE_FORMAT(ol.end_date,'%Y-%m-%d')) as validityPeriod,");
        tempSql.append("case when t1.code = :lic_valid_valid && t2.code = :pass && (sysdate() between start_date and end_date ) then '有效' else '无效' end as licenceStatus, ");
        tempSql.append("t2.value as auditStatus, ");
        tempSql.append("t2.code as auditStatusCode, ");
        tempSql.append("t3.value as operationMode ");
        tempSql.append("from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol ");
        tempSql.append("left join " + TableNameConstants.TABLE_CODE_VALUE + " t1 on ol.licence_status = t1.id ");
        tempSql.append("inner join " + TableNameConstants.TABLE_CODE_VALUE + " t2 on ol.audit_status = t2.id ");
        tempSql.append("inner join " + TableNameConstants.TABLE_CODE_VALUE + " t3 on ol.operation_mode = t3.id ");
        tempSql.append("left join ").append(TableNameConstants.TABLE_SYS_ORG_COM).append(" orgcome ");
        tempSql.append(" on orgcome.ComID = ol.licence_org  ");
        tempSql.append("where IFNULL(ol.valid,:valid) = :valid and ol.id = :id ");
        
        map.put("valid", Constant.IS_VALID);
        map.put("lic_valid_valid", CodeTypeConstant.LIC_VALID_VALID);
        map.put("pass", CodeTypeConstant.LIC_AUDIT_PASS);
        map.put("id", id);
        
        List<OperationLicenceVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map,
                new BeanPropertyRowMapper<OperationLicenceVo>(OperationLicenceVo.class));
        
        // 没有查到数据时，queryForList方法默认返回一个数据对象，此时list.size() = 1,但是值全部为NULL
        if (list != null && list.size() == 1) {
            if (StringUtils.isBlank(list.get(0).getId())) {
                list = new ArrayList<OperationLicenceVo>();
            }
        }
        
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据许可证id获取许可证处置量
     * @param licenceId
     * @return
     * @throws DaoAccessException
     */
    @Override
    public List<OperationLicenceItemVo> queryItemByLicenceId(String licenceId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer("select distinct ");
        tempSql.append("item.licence_id as licenceId,");
        tempSql.append("item.id as itemId,");
        tempSql.append("item.remark as itemRemark,");
        tempSql.append("format(item.approved_quantity,3) as approved_quantity,");
        tempSql.append("format(item.excuted_quantity,3) as excuted_quantity, ");
        tempSql.append("format(item.approved_quantity - item.excuted_quantity,3) as surplus_quantity, ");
        tempSql.append("100 - (format(item.excuted_quantity/item.approved_quantity,6)) * 100 as surplus_percentage, ");
        tempSql.append("t1.id as disposition_type_id, ");
        tempSql.append("concat(t1.code,' - ',t1.value) as  dispositionType ");
        tempSql.append("from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol ");
        tempSql.append("inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " item on item.licence_id = ol.id ");
        tempSql.append("and IFNULL(item.valid,:valid) = :valid ");
        tempSql.append("inner join " + TableNameConstants.TABLE_CODE_VALUE + " t1 on item.disposition_type = t1.id ");
        tempSql.append("where IFNULL(ol.valid,:valid) = :valid and ol.id = :licenceId ");
        tempSql.append(" order by t1.code ");
        
        map.put("valid", Constant.IS_VALID);
        map.put("licenceId", licenceId);
        
        List<OperationLicenceItemVo> list = queryForList(tempSql.toString(), map,
            new BeanPropertyRowMapper<OperationLicenceItemVo>(OperationLicenceItemVo.class));
        // 没有查到数据时，queryForList方法默认返回一个数据对象，此时list.size() = 1,但是值全部为NULL
        if (list != null && list.size() == 1) {
            if (StringUtils.isBlank(list.get(0).getItemId())) {
                list = new ArrayList<OperationLicenceItemVo>();
            }
        }
        
        return list;
    }

    /**
     * 根据许可证id获取二位码列表
     * @param licenceId
     * @return
     * @throws DaoAccessException
     */
    @Override
    public List<OperationLicenceDetailVo> queryWasteTypeByLicenceId(String licenceId)
        throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer("select distinct ");
        tempSql.append("detail.licence_id as licenceId,");
        tempSql.append("detail.operation_item_id as itemId,");
        tempSql.append("detail.waste_type as wasteTypeId,");
        tempSql.append("wt.code as wasteTypeCode,");
        tempSql.append("wt.description as  wasteTypeValue ");
        tempSql.append("from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol ");
        tempSql.append("inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " item on item.licence_id = ol.id ");
        tempSql.append("and IFNULL(item.valid,:valid) = :valid ");
        tempSql.append("inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " detail on detail.licence_id = ol.id ");
        tempSql.append("and item.id = detail.operation_item_id ");
        tempSql.append("and IFNULL(detail.valid,:valid) = :valid ");
        tempSql.append("inner join " + TableNameConstants.TABLE_WASTE_TYPE + " wt on detail.waste_type = wt.id ");
        tempSql.append("and IFNULL(wt.status,:status) = :status ");
        tempSql.append("where IFNULL(ol.valid,:valid) = :valid and ol.id = :id ");
        tempSql.append(" order by wt.code ");
        
        map.put("valid", Constant.IS_VALID);
        map.put("status", Constant.ENABLED);
        map.put("id", licenceId);
        
        List<OperationLicenceDetailVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map,
            new BeanPropertyRowMapper<OperationLicenceDetailVo>(OperationLicenceDetailVo.class));
        // 没有查到数据时，queryForList方法默认返回一个数据对象，此时list.size() = 1,但是值全部为NULL
        if (list != null && list.size() == 1) {
            if (StringUtils.isBlank(list.get(0).getItemId())) {
                list = new ArrayList<OperationLicenceDetailVo>();
            }
        }
        return list;
    }

    /**
     * 根据许可证id获取八位码列表
     * @param licenceId
     * @param licenceItemId
     * @return
     * @throws DaoAccessException
     */
    @Override
    public List<OperationLicenceDetailVo> queryWasteCodeByLicenceId(String licenceId,String licenceItemId) throws DaoAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer tempSql = new StringBuffer("select distinct ");
        tempSql.append("detail.licence_id as licenceId,");
        tempSql.append("detail.operation_item_id as itemId,");
        tempSql.append("detail.id as detailId,");
        tempSql.append("detail.waste_type as wasteTypeId,");
        tempSql.append("detail.waste_id as wasteId,");
        tempSql.append("detail.waste_name_id as wasteNameId,");
        tempSql.append("item.approved_quantity as approvedQuantity,");
        tempSql.append("item.excuted_quantity as excutedQuantity,");
        tempSql.append("wt.code as wasteTypeCode,");
        tempSql.append("wt.description as  wasteTypeValue, ");
        tempSql.append("w.code as wasteCode, ");
        tempSql.append("ifnull(wn.name,'') as wasteName ");
        tempSql.append("from " + TableNameConstants.TABLE_OPERATION_LICENCE + " ol ");
        tempSql.append("inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_ITEM + " item on item.licence_id = ol.id and ifnull(item.valid,:valid) = :valid ");
        tempSql.append("inner join " + TableNameConstants.TABLE_OPERATION_LICENCE_DETAIL + " detail on detail.licence_id = ol.id ");
        tempSql.append("and item.id = detail.operation_item_id and ifnull(detail.valid,:valid) = :valid ");
        tempSql.append("inner join " + TableNameConstants.TABLE_WASTE_TYPE + " wt on detail.waste_type = wt.id ");
        tempSql.append("inner join " + TableNameConstants.TABLE_WASTE + " w on detail.waste_id = w.id ");
        tempSql.append("left join " + TableNameConstants.TABLE_WASTE_NAME + " wn on detail.waste_name_id = wn.id ");
        tempSql.append("where ifnull(ol.valid,:valid) = :valid and ol.id = :licenceId ");
        if (StringUtils.isNotBlank(licenceItemId)) {
            tempSql.append(" and item.id = :licenceItemId ");
            map.put("licenceItemId", licenceItemId);
        }
        tempSql.append(" order by right(w.code, 2),left(w.code, 3),substring(w.code, 5, 3) ");
        
        map.put("valid", Constant.IS_VALID);
        map.put("licenceId", licenceId);
        
        List<OperationLicenceDetailVo> list = namedParameterJdbcTemplate.query(tempSql.toString(), map,
            new BeanPropertyRowMapper<OperationLicenceDetailVo>(OperationLicenceDetailVo.class));
        // 没有查到数据时，queryForList方法默认返回一个数据对象，此时list.size() = 1,但是值全部为NULL
        if (list != null && list.size() == 1) {
            if (StringUtils.isBlank(list.get(0).getItemId())) {
                list = new ArrayList<OperationLicenceDetailVo>();
            }
        }
        return list;
    }

}