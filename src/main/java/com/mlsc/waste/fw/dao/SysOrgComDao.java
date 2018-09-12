package com.mlsc.waste.fw.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysOrgCom;
import com.mlsc.waste.fw.model.SysOrgCom;

import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 */
public interface SysOrgComDao extends EntityDao<SysOrgCom> {

    /**
     * 平台管理员获取所有企业列表
     *
     * @param sql
     * @param paramMap
     * @param paging
     * @return
     * @throws DaoAccessException
     */
    List<SysOrgCom> getOrgComList(String sql, Map<String, Object> paramMap, PagingParameter paging) throws DaoAccessException;

    /**
     * 根据区域代码获取组织机构
     *
     * @param ticketId
     * @param comType
     * @param cantonCode
     * @return
     * @throws DaoAccessException
     */
    List<RPCSysOrgCom> querySysOrgComListByCantonID(String ticketId, int comType, String cantonCode) throws DaoAccessException;

    /**
     * 根据区域代码获取组织机构
     *
     * @param ticketId
     * @param comType
     * @param cantonCode
     * @return
     * @throws DaoAccessException
     */
    List<RPCSysOrgCom> querySysOrgComListByParentCantonID(String ticketId, int comType, String cantonCode) throws DaoAccessException;


}
