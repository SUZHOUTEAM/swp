package com.mlsc.yifeiwang.entwaste.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.entwaste.entity.EntWaste;
import com.mlsc.yifeiwang.waste.entity.WasteName;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.entwaste.model.EntWasteModel;
import com.mlsc.yifeiwang.entwaste.model.EntWasteParams;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
public interface IEntWasteService extends IService<EntWaste> {

    /**
     * 保存企业危废
     *
     * @param user
     * @param entWasteParams
     * @throws Exception
     */
    String saveEntWaste(User user, EntWasteParams entWasteParams) throws Exception;

    /**
     * 获取企业危废列表带分页
     *
     * @param entWasteParams
     * @return
     */
    List<EntWasteModel> listEntWaste(User user, EntWasteParams entWasteParams, PagingParameter pagingParameter) throws Exception;

    /**
     * 根据EntWasteId获取企业危废详情
     *
     * @param entWasteParams
     * @return
     * @throws Exception
     */

    EntWasteModel getEntWasteDetailById(EntWasteParams entWasteParams) throws Exception;

    /**
     * 企业危废是否存在
     *
     * @param entWasteParams
     * @return
     * @throws Exception
     */
    boolean isExistEnterpriseWaste(EntWasteParams entWasteParams) throws Exception;

    /**
     * 根据许可证获取企业危废列表
     *
     * @param licenceId
     * @param user
     * @return
     * @throws Exception
     */
    List<EntWasteModel> listEntWasteByLicenceId(String licenceId, User user) throws Exception;


    List<EntWaste> listEntWasteByIds(List<String> ids) throws Exception;

    List<EntWasteModel> listEntWasteByEntId(EntWasteParams entWasteParams) throws Exception;

    boolean updateEntWaste(User user, EntWasteModel entWasteModel) throws Exception;

    boolean deleteEntWaste(User user, List<EntWasteModel> entWasteModels) throws Exception;

    String getWasteIdByCode(String code) throws Exception;

    List<WasteName> listWasteName(String wasteId, String wasteName) throws Exception;

    Map<String, Object> getWasteDropdownList(String keyword);

}
