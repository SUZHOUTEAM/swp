package com.mlsc.yifeiwang.operaction.service;

import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.operaction.entity.WebsiteOperation;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationModel;
import com.mlsc.yifeiwang.operaction.model.WebsiteOperationParam;
import com.mlsc.waste.user.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-11-20
 */
public interface IWebsiteOperationService extends IService<WebsiteOperation> {
    List<SysEnterpriseBase> listWasteEnterpriseList(String type, PagingParameter pagingParameter, WebsiteOperationParam websiteOperationParam) throws Exception;

    List<SysEnterpriseBase> initOutSouringWasteEnterprise(String entName) throws Exception;

    /**
     * 委外产废企业
     *
     * @param websiteOperationParam
     * @return
     * @throws Exception
     */
    List<SysEnterpriseBase> listOutSouringWasteEnterprise(WebsiteOperationParam websiteOperationParam) throws Exception;

    /**
     * 未做当年管理计划的产废企业
     *
     * @param websiteOperationParam
     * @return
     * @throws Exception
     */
    List<SysEnterpriseBase> listNoPlanWasteEnterprise(WebsiteOperationParam websiteOperationParam) throws Exception;

    List<SysEnterpriseBase> listPlanWasteEnterprise(WebsiteOperationParam websiteOperationParam) throws Exception;

    /**
     * 当年未发生转移的产废企业
     *
     * @param websiteOperationParam
     * @return
     * @throws Exception
     */
    List<SysEnterpriseBase> listNoTransferWasteEnterprise(WebsiteOperationParam websiteOperationParam) throws Exception;
    /**
     * 保存运营信息
     * @param user
     * @param websiteOperationParam
     * @param enterpriseUserList
     * @throws Exception
     */
    String saveOrUpdateWebSiteOperationInfo(User user,WebsiteOperationParam websiteOperationParam, List<SysEnterpriseBase> enterpriseUserList) throws Exception;

    /**
     *
     * @param user
     * @param operationIds
     * @throws Exception
     * @Description 启动运营
     */
    boolean startWebSiteOperationInfo(User user, List<String> operationIds) throws Exception;

    /**
     * 暂停运营
     *
     * @param user
     * @param operationId
     * @throws Exception
     */
    /**
     *
     * @param user
     * @param operationIds
     * @throws Exception
     */
    boolean stopWebSiteOperationInfo(User user,  List<String> operationIds) throws Exception;

    /**
     * 删除运营
     * @param user
     * @param operationIds
     */
    boolean deleteWebSiteOperationInfo(User user, List<String> operationIds) throws Exception;

    boolean updateWebSiteOperationInfo(User user, WebsiteOperationParam websiteOperationParam) throws Exception;

    List<WebsiteOperationModel> listWebSiteOperationInfo(PagingParameter pagingParameter) throws Exception;

    WebsiteOperationModel getWebSiteOperationInfo(String id) throws Exception;

    List<WebsiteOperation> listOperationByStatus(String busiStatus) throws Exception;

    void updateOperationStatus(WebsiteOperation websiteOperation, String busiStatus) throws Exception;

    List<WebsiteOperation> listStartWebSiteOperationInfo() throws Exception;

    List<WebsiteOperation> listStopWebSiteOperationInfo() throws Exception;

    List<EntWasteModel> listEntWasteByEntId(String entId) throws Exception;
}
