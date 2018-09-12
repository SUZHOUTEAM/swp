package com.mlsc.yifeiwang.appmanagement.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.appmanagement.common.AppStatus;
import com.mlsc.yifeiwang.appmanagement.entity.AppManagement;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.appmanagement.service.IAppManagementService;
import com.mlsc.yifeiwang.appmanagement.mapper.AppManagementMapper;
import com.mlsc.yifeiwang.appmanagement.model.AppManagementParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2017-12-11
 */
@Service
public class AppManagementServiceImpl extends ServiceImpl<AppManagementMapper, AppManagement> implements IAppManagementService {
    private final static Logger logger = LoggerFactory.getLogger(AppManagementServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AppManagement saveAppManagement(User user, AppManagement appManagement) throws Exception {
        try {
            Date currentDate = new Date();
            appManagement.setBusiStatus(AppStatus.SUBMIT.getCode());
            appManagement.setCreateBy(user.getUserId());
            appManagement.setCreateTime(currentDate);
            appManagement.setEditBy(user.getUserId());
            appManagement.setEditTime(currentDate);
            this.insert(appManagement);
        } catch (Exception e) {
            logger.error("保存手机App版本时异常", e);
            throw e;
        }
        return appManagement;
    }

    @Override
    public boolean validateAppManagement(AppManagement appManagement, List<String> errorList) {
        boolean validate = true;
        if (appManagement == null) {
            validate = false;
            errorList.add("App信息管理不能为空");
        } else {
            if (StringUtils.isBlank(appManagement.getAppType())) {
                validate = false;
                errorList.add("app类型不能为空");
            }
            if (StringUtils.isBlank(appManagement.getEntType())) {
                validate = false;
                errorList.add("企业类型不能为空");
            }
//            if (StringUtils.isBlank(appManagement.getFileName())) {
//                validate = false;
//                errorList.add("安装包不能为空");
//            }
            if (StringUtils.isBlank(appManagement.getDescription())) {
                validate = false;
                errorList.add("更新日志不能为空");
            }
            if (StringUtils.isBlank(appManagement.getVersionCode())) {
                validate = false;
                errorList.add("版本号不能为空");
            }
        }
        return validate;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean updateAppManagement(User user, AppManagement appManagement) throws Exception {
        try {
            AppManagement checkExsit = this.selectById(appManagement.getId());
            Date currentDate = new Date();
            if (checkExsit != null) {
                if (StringUtils.isNotEmpty(appManagement.getAppType())) {
                    checkExsit.setAppType(appManagement.getAppType());
                }
                if (StringUtils.isNotEmpty(appManagement.getEntType())) {
                    checkExsit.setEntType(appManagement.getEntType());
                }
                if (StringUtils.isNotEmpty(appManagement.getVersionCode())) {
                    checkExsit.setVersionCode(appManagement.getVersionCode());
                }
                if (StringUtils.isNotEmpty(appManagement.getDescription())) {
                    checkExsit.setDescription(appManagement.getDescription());
                }
                if (StringUtils.isNotEmpty(appManagement.getFileId())) {
                    checkExsit.setFileId(appManagement.getFileId());
                }
                if (StringUtils.isNotEmpty(appManagement.getFileName())) {
                    checkExsit.setFileName(appManagement.getFileName());
                }
                checkExsit.setEditBy(user.getUserId());
                checkExsit.setEditTime(currentDate);
                return this.updateAllColumnById(checkExsit);
            }

        } catch (Exception e) {
            logger.error("更新手机App版本时异常", e);
            throw e;
        }
        return false;
    }

    @Override
    public AppManagement getLatestVersion(AppManagement appManagement) throws Exception {
        if (StringUtils.isNotBlank(appManagement.getAppType()) && StringUtils.isNotBlank(appManagement.getEntType())) {
            EntityWrapper<AppManagement> appWrapper = new EntityWrapper<AppManagement>();
            appWrapper.setSqlSelect("fileId,versionCode,description");
            appWrapper.eq("appType", appManagement.getAppType());
            appWrapper.eq("entType", appManagement.getEntType());
            appWrapper.eq("busiStatus", AppStatus.RELEASE.getCode());
            appWrapper.eq("deleteFlag", 0);
            appWrapper.orderBy("editTime desc");
            List<AppManagement> appList = this.selectList(appWrapper);
            if (appList != null && appList.size() > 0) {
                return appList.get(0);
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    @Override
    public List<AppManagement> listAppManagement(PagingParameter paging, AppManagementParam managementParam) throws Exception {
        List<AppManagement> appManagementList = null;
        Util.initPagingParameter(paging);
        managementParam.setStartRowIndex(paging.getStart());
        managementParam.setRows(paging.getLimit());
        try {
            int count = this.baseMapper.countAppManagement(managementParam);
            if (count > 0) {
                paging.setTotalRecord(count);
                appManagementList = this.baseMapper.listAppManagement(managementParam);
            }
        } catch (Exception e) {
            logger.error("获取app管理列表时异常", e);
            throw e;
        }

        return appManagementList;


    }

    @Override
    public boolean releaseAppManagementStatus(User user, List<String> ids) throws Exception {
        boolean updateResult = true;
        try {
            for (String id : ids) {
                AppManagement appManagement = this.selectById(id);
                Date currentDate = new Date();
                appManagement.setBusiStatus(AppStatus.RELEASE.getCode());
                appManagement.setEditBy(user.getUserId());
                appManagement.setEditTime(currentDate);
                updateResult = updateResult && this.updateById(appManagement);
            }
        } catch (Exception e) {
            logger.error("更新手机APP状态时异常", e);
            throw e;
        }
        return updateResult;
    }

    @Override
    public boolean deleteAppMangement(User user, List<String> ids) throws Exception {
        boolean deleteResult = true;
        try {
            if (ids != null && ids.size() > 0) {
                for (String id : ids) {
                    AppManagement appManagement = this.selectById(id);
                    Date currentDate = new Date();
                    appManagement.setDeleteFlag(Constant.DELETED);
                    appManagement.setEditBy(user.getUserId());
                    appManagement.setEditTime(currentDate);
                    deleteResult = deleteResult && this.updateById(appManagement);
                }
            }
        } catch (Exception e) {
            logger.error("更新手机APP状态时异常", e);
            throw e;
        }
        return deleteResult;
    }
}
