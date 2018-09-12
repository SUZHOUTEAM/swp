package com.mlsc.yifeiwang.enterprise.service;

import com.mlsc.yifeiwang.enterprise.entity.EnterpriseConfiguration;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.enterprise.mapper.EnterpriseConfigurationModel;
import com.mlsc.yifeiwang.enterprise.mapper.EnterpriseConfigurationParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-12-25
 */
public interface IEnterpriseConfigurationService extends IService<EnterpriseConfiguration> {

    boolean saveEnterpriseConfiguration(User user, EnterpriseConfiguration enterpriseConfiguration) throws Exception;

    boolean validateEnterpriseConfiguration(EnterpriseConfiguration enterpriseConfiguration, List<String> errorList);

    boolean updateEnterpriseConfiguration(User user, EnterpriseConfiguration enterpriseConfiguration) throws Exception;

    boolean deleteEnterpriseConfiguration(User user, List<String> ids) throws Exception;

    List<EnterpriseConfigurationModel> listEnterpriseInfoBySection(User user, EnterpriseConfigurationParam configurationParam) throws Exception;

    List<EnterpriseConfigurationModel> listEnterpriseInfo(User user, EnterpriseConfigurationParam configurationParam, PagingParameter pagingParameter) throws Exception;

    EnterpriseConfigurationModel getEnterpriseConfig(EnterpriseConfigurationParam configurationParam) throws Exception;

    int getMaxPageBySection(EnterpriseConfigurationParam configurationParam) throws Exception;

    List<EnterpriseConfigurationModel> listEnterpriseInfoByCantonCode(EnterpriseConfigurationParam configurationParam, PagingParameter pagingParameter);

    List<EnterpriseConfigurationModel> listEnterpriseInfoByWeight(EnterpriseConfigurationParam configurationParam) throws Exception;
}
