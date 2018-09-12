package com.mlsc.yifeiwang.enterprise.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.enterprise.entity.EnterpriseConfiguration;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.enterprise.mapper.EnterpriseConfigurationMapper;
import com.mlsc.yifeiwang.enterprise.mapper.EnterpriseConfigurationModel;
import com.mlsc.yifeiwang.enterprise.mapper.EnterpriseConfigurationParam;
import com.mlsc.yifeiwang.enterprise.service.IEnterpriseConfigurationService;
import com.mlsc.yifeiwang.licence.service.IOperationLicenceDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-12-25
 */
@Service
public class EnterpriseConfigurationServiceImpl extends ServiceImpl<EnterpriseConfigurationMapper, EnterpriseConfiguration> implements IEnterpriseConfigurationService {

    private final static Logger logger = LoggerFactory.getLogger(EntCustomerServiceImpl.class);

    @Autowired
    private IOperationLicenceDetailService licenceDetailService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveEnterpriseConfiguration(User user, EnterpriseConfiguration enterpriseConfiguration) throws Exception {
        try {
            if (enterpriseConfiguration != null) {
                if (enterpriseConfiguration.getPage() != 0 && enterpriseConfiguration.getIndex() != 0) {
                    enterpriseConfiguration.setWeight(Util.getWeight(enterpriseConfiguration.getPage(), enterpriseConfiguration.getIndex()));
                    Date date = new Date();
                    enterpriseConfiguration.setCreateBy(user.getUserId());
                    enterpriseConfiguration.setCreateTime(date);
                    enterpriseConfiguration.setEditBy(user.getUserId());
                    enterpriseConfiguration.setEditTime(date);
                    this.baseMapper.insert(enterpriseConfiguration);
                }
            }

        } catch (Exception e) {
            logger.error("保存企业配置时异常", e);
            throw e;
        }
        return true;
    }

    @Override
    public boolean validateEnterpriseConfiguration(EnterpriseConfiguration enterpriseConfiguration, List<String> errorList) {
        boolean validate = true;
        if (enterpriseConfiguration == null) {
            validate = false;
            errorList.add("首页配置信息不能为空");
        } else {
            if (StringUtils.isBlank(enterpriseConfiguration.getEntId())) {
                validate = false;
                errorList.add("企业不能为空");
            }
            if (StringUtils.isBlank(enterpriseConfiguration.getSection())) {
                validate = false;
                errorList.add("栏目不能为空");
            }
            if (enterpriseConfiguration.getStartDate() == null) {
                validate = false;
                errorList.add("开始日期不能为空");
            }
            if (enterpriseConfiguration.getEndDate() == null) {
                validate = false;
                errorList.add("结束日期不能为空");
            }
        }
        return validate;

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateEnterpriseConfiguration(User user, EnterpriseConfiguration enterpriseConfiguration) throws Exception {
        try {
            if (enterpriseConfiguration != null && StringUtils.isNotBlank(enterpriseConfiguration.getId())) {
                EnterpriseConfiguration updatedData = this.selectById(enterpriseConfiguration.getId());
                if (updatedData != null) {
                    if (StringUtils.isNotEmpty(enterpriseConfiguration.getCantonCode())) {
                        updatedData.setCantonCode(enterpriseConfiguration.getCantonCode());
                    }
                    if (StringUtils.isNotEmpty(enterpriseConfiguration.getEntId())) {
                        updatedData.setEntId(enterpriseConfiguration.getEntId());
                    }
                    if (StringUtils.isNotEmpty(enterpriseConfiguration.getSection())) {
                        updatedData.setSection(enterpriseConfiguration.getSection());
                    }
                    if (StringUtils.isNotEmpty(enterpriseConfiguration.getWasteType())) {
                        updatedData.setWasteType(enterpriseConfiguration.getWasteType());
                    }
                    if (enterpriseConfiguration.getPage() != 0) {
                        updatedData.setPage(enterpriseConfiguration.getPage());
                    }
                    if (enterpriseConfiguration.getIndex() != 0) {
                        updatedData.setIndex(enterpriseConfiguration.getIndex());
                    }

                    if (enterpriseConfiguration.getPage() != 0 && enterpriseConfiguration.getIndex() != 0) {
                        enterpriseConfiguration.setWeight(Util.getWeight(enterpriseConfiguration.getPage(), enterpriseConfiguration.getIndex()));
                    }
                    if (enterpriseConfiguration.getStartDate() != null) {
                        updatedData.setStartDate(enterpriseConfiguration.getStartDate());
                    }
                    if (enterpriseConfiguration.getEndDate() != null) {
                        updatedData.setEndDate(enterpriseConfiguration.getEndDate());
                    }
                    Date date = new Date();
                    updatedData.setEditBy(user.getUserId());
                    updatedData.setEditTime(date);
                    int count = this.baseMapper.updateAllColumnById(updatedData);
                    if (count != 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("更新企业配置时异常", e);
            throw e;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteEnterpriseConfiguration(User user, List<String> ids) throws Exception {
        try {
            for (String id : ids) {
                EnterpriseConfiguration updatedData = this.selectById(id);
                if (updatedData != null) {
                    updatedData.setDeleteFlag(1);
                    Date date = new Date();
                    updatedData.setEditBy(user.getUserId());
                    updatedData.setEditTime(date);
                    this.baseMapper.updateAllColumnById(updatedData);
                }
            }
        } catch (Exception e) {
            logger.error("删除企业配置时异常", e);
            throw e;
        }
        return true;
    }

    @Override
    public List<EnterpriseConfigurationModel> listEnterpriseInfoBySection(User user, EnterpriseConfigurationParam configurationParam) throws Exception {
        List<EnterpriseConfigurationModel> enterpriseModelList = new ArrayList<EnterpriseConfigurationModel>();
        try {
            if (configurationParam != null && StringUtils.isNotBlank(configurationParam.getSection())) {
                int queryCount = countEnterpriseInfoBySection(configurationParam);
                if (queryCount > 0) {
                    enterpriseModelList = this.baseMapper.listEnterpriseInfoBySection(configurationParam);
                }
            }

        } catch (Exception e) {
            logger.error("获取首页企业列表时异常", e);
            throw e;
        }
        return enterpriseModelList;
    }

    private int countEnterpriseInfoBySection(EnterpriseConfigurationParam configurationParam) {
        return this.baseMapper.countEnterpriseInfoBySection(configurationParam);
    }


    @Override
    public List<EnterpriseConfigurationModel> listEnterpriseInfo(User user, EnterpriseConfigurationParam configurationParam, PagingParameter pagingParameter) throws Exception {
        List<EnterpriseConfigurationModel> enterpriseModelList = new ArrayList<EnterpriseConfigurationModel>();
        try {
            Util.initPagingParameter(pagingParameter);
            configurationParam.setStartRowIndex(pagingParameter.getStart());
            configurationParam.setRows(pagingParameter.getPageSize());
            int queryCount = countEnterpriseInfo(configurationParam);
            if (queryCount > 0) {
                pagingParameter.setTotalRecord(queryCount);
                enterpriseModelList = this.baseMapper.listEnterpriseInfo(configurationParam);
            }
        } catch (Exception e) {
            logger.error("管理员获取首页企业列表时异常", e);
            throw e;
        }

        return enterpriseModelList;
    }

    @Override
    public EnterpriseConfigurationModel getEnterpriseConfig(EnterpriseConfigurationParam configurationParam) throws Exception {
        try {
            List<EnterpriseConfigurationModel> modelList = this.baseMapper.listEnterpriseInfo(configurationParam);
            if (modelList != null && modelList.size() > 0) {
                return modelList.get(0);
            }
        } catch (Exception e) {
            logger.error("获取企业配置时异常", e);
            throw e;
        }
        return null;
    }


    private int countEnterpriseInfo(EnterpriseConfigurationParam configurationParam) {
        return this.baseMapper.countEnterpriseInfo(configurationParam);
    }


    @Override
    public int getMaxPageBySection(EnterpriseConfigurationParam configurationParam) throws Exception {
        try {
            String maxPageStr = this.baseMapper.getMaxPageBySection(configurationParam);
            if (StringUtils.isNotEmpty(maxPageStr)) {
                return Integer.valueOf(maxPageStr);
            }
            return 0;
        } catch (Exception e) {
            logger.error("获取最大版面值", e);
            throw e;
        }
    }

    @Override
    public List<EnterpriseConfigurationModel> listEnterpriseInfoByCantonCode(EnterpriseConfigurationParam configurationParam, PagingParameter pagingParameter) {
        List<EnterpriseConfigurationModel> enterpriseModelList = new ArrayList<EnterpriseConfigurationModel>();
        try {
            Util.initPagingParameter(pagingParameter);
            configurationParam.setStartRowIndex(pagingParameter.getStart());
            configurationParam.setRows(pagingParameter.getPageSize());
            int queryCount = this.baseMapper.countEnterpriseInfoBySection(configurationParam);
            if (queryCount > 0) {
                pagingParameter.setTotalRecord(queryCount);
                enterpriseModelList = this.baseMapper.listEnterpriseInfoByCantonCode(configurationParam);
            }
        } catch (Exception e) {
            logger.error("获取首页企业列表时异常", e);
            throw e;
        }
        return enterpriseModelList;
    }

    @Override
    public List<EnterpriseConfigurationModel> listEnterpriseInfoByWeight(EnterpriseConfigurationParam configurationParam) throws Exception {
        List<EnterpriseConfigurationModel> enterpriseModelList;
        try {
            enterpriseModelList = this.baseMapper.listEnterpriseInfoByWeight(configurationParam);
        } catch (Exception e) {
            logger.error("获取合作伙伴列表时异常", e);
            throw e;
        }

        return enterpriseModelList;
    }


}
