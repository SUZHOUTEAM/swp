package com.mlsc.waste.wastecircle.dao;

import com.mlsc.epdp.common.base.db.dao.EntityDao;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.model.CoopMsg;
import com.mlsc.waste.wastecircle.model.CoopMsgVo;
import com.mlsc.waste.wastecircle.model.MessageBodyVo;
import com.mlsc.waste.wastecircle.model.SearchCondition;

import java.util.List;

/**
 * 发布信息DAO
 */
public interface CoopMsgDao extends EntityDao<CoopMsg> {
    /**
     * 保存发布信息
     *
     * @param coopMsg
     * @return
     * @throws DaoAccessException
     */
    String saveCoopMsg(CoopMsg coopMsg) throws DaoAccessException;

    /**
     * 根据企业id获取发布信息
     *
     * @param coopMsg
     * @return
     * @throws DaoAccessException
     */
    int getCoopMsgCountByEnterId(CoopMsg coopMsg) throws DaoAccessException;

    /**
     * 获取处置企业发布处置资源列表个数
     *
     * @param pagingParameter
     * @param enterpriseBaseVo
     * @param entType
     * @param user
     * @param searchCondition
     * @param cantonCode
     * @return
     */
    int getCoopMsgCountForProductionUser(PagingParameter pagingParameter, RPCSysEnterpriseBaseVo enterpriseBaseVo,
                                         String entType, User user, String searchCondition, String cantonCode);

    /**
     * 获取处置企业发布处置资源列表
     *
     * @param pagingParameter
     * @param enterpriseBaseVo
     * @param entType
     * @param user
     * @param searchCondition
     * @param cantonCode
     * @param sortByCondition
     * @return
     */
    List<CoopMsgVo> getCoopMsgsForProductionUser(PagingParameter pagingParameter, RPCSysEnterpriseBaseVo enterpriseBaseVo,
                                                 String entType, User user, String searchCondition, String cantonCode, String sortByCondition);


}

