package com.mlsc.yifeiwang.enterprise.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.enterprise.entity.EnterpriseConfiguration;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2017-12-25
 */
public interface EnterpriseConfigurationMapper extends BaseMapper<EnterpriseConfiguration> {

    List<EnterpriseConfigurationModel> listEnterpriseInfoBySection(EnterpriseConfigurationParam configurationParam);

    int countEnterpriseInfoBySection(EnterpriseConfigurationParam configurationParam);

    List<EnterpriseConfigurationModel> listEnterpriseInfo(EnterpriseConfigurationParam configurationParam);

    int countEnterpriseInfo(EnterpriseConfigurationParam configurationParam);

    String getMaxPageBySection(EnterpriseConfigurationParam configurationParam);

//    int countEnterpriseInfoByCantonCode(EnterpriseConfigurationParam configurationParam);

    List<EnterpriseConfigurationModel> listEnterpriseInfoByCantonCode(EnterpriseConfigurationParam configurationParam);

    List<EnterpriseConfigurationModel> listEnterpriseInfoByWeight(EnterpriseConfigurationParam configurationParam);
}