package com.mlsc.waste.enterprise.service;

import com.mlsc.yifeiwang.codedirectory.entity.CodeValue;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.solr.model.EnterpriseIndex;
import com.mlsc.waste.enterprise.model.*;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.wastecircle.model.EnterpriseVo;

import java.util.List;
import java.util.Map;

/**
 * @author sunjl
 */
public interface EnterpriseService {

    void informSysAdminUser(String enterpriseId, User user) throws Exception;

    /**
     * 保存企业基本信息
     *
     * @param ticketId
     * @param enterprise
     * @return
     */
    RPCSysEnterpriseBase saveEnterpriseInfo(String ticketId, RPCSysEnterpriseBase enterprise);

    /**
     * 更新企业信息
     *
     * @param ticketId
     * @param enterprise
     * @return
     */
    RPCSysEnterpriseBase updateEnterpriseBase(String ticketId, RPCSysEnterpriseBase enterprise,String latitudeAndLongitude) throws Exception;

    void updateEnterpriseNewField(RPCSysEnterpriseBase sysEnterpriseBase) throws DaoAccessException;


    /**
     * 保存企业类型
     *
     * @param ticketId
     * @param enterpriseId
     * @param enterpriseTypeMap
     */
    void saveEnterpriseType(String ticketId, String enterpriseId, Map<String, String> enterpriseTypeMap, String responsibleArea);

    /**
     * 保存企业相关信息
     *
     * @param ticketId
     * @param enterpriseBase
     */
    String saveEnterpriseInformation(String ticketId, RPCSysEnterpriseBase enterpriseBase) throws Exception;


    /**
     * 获取企业类型
     *
     * @param typecode
     * @return
     * @throws DaoAccessException
     */
    List<CodeValue> getDropDownListByTypeCode(String typecode);


    /**
     * 通过企业ID判断企业是否存在以及企业类型是否存在
     *
     * @param ticketId
     * @return
     */
    boolean isEnterpriseVaild(String ticketId) throws Exception;

    /**
     * 关注或取消关注
     *
     * @param enterId
     * @param action   1:关注；0：取消关注
     * @param ticketId
     * @return
     */
    void saveOrRemoveFollow(String enterId, String action, String ticketId);

    /**
     * 关注一批企业
     *
     * @param ticketId
     * @param entIds
     * @throws DaoAccessException
     */
    int saveFollowAndEnterWasteCircle(String ticketId, List<String> entIds);

    /**
     * 获取二位码列表
     *
     * @param keyword
     * @return
     */
    Map<String, Object> getCodeWastesDropDownList(String keyword);


    /**
     * @param entType
     * @param keywords
     * @param paging
     * @return
     */
    List<EnterpriseIndex> listEnterpriseSuggest(String cantonCode, String entType, String keywords, PagingParameter paging);


    /**
     * 获取危废名称列表
     *
     * @param keyword
     * @return
     */
    Map<String, Object> getWasteNameDropDownList(String keyword);

    /**
     * 根据企业id获取企业信息
     *
     * @param ticketId
     * @param enterpriseId
     * @return
     */
    RPCSysEnterpriseBase getEnterpriseInfoById(String ticketId, String enterpriseId);

    String getCanonCode(String ticketId, String district, String city);

    String getShortName(String entName);

    /**
     * 根据企业id获取所属省
     *
     * @param enterpriseId
     * @return
     */
    String getCantonNameByEnterpriseId(String enterpriseId);


    RPCSysEnterpriseBaseVo getPosxVsPosyByEntId(String enterpriseId);


    boolean checkEnterpriseCodeExist(String entId, String entCode);

    List<EnterpriseVo> getEnterpriseVosByName(String enterpriseName, String enterpriseType);

    String getDistrictByEnterpriseId(String enterpriseId) throws Exception;

    List<EnterpriseVo> getCZEnterpriseVosByName(String enterpriseName);
}

