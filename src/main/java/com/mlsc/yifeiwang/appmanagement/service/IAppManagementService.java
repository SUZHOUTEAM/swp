package com.mlsc.yifeiwang.appmanagement.service;

import com.mlsc.yifeiwang.appmanagement.entity.AppManagement;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.appmanagement.model.AppManagementParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caoyy
 * @since 2017-12-11
 */
public interface IAppManagementService extends IService<AppManagement> {

    AppManagement saveAppManagement(User user, AppManagement appManagement) throws Exception;

    AppManagement getLatestVersion(AppManagement appManagement) throws Exception;

    List<AppManagement> listAppManagement(PagingParameter paging, AppManagementParam managementParam) throws Exception;

    boolean releaseAppManagementStatus(User user, List<String> ids) throws Exception;

    boolean deleteAppMangement(User user,List<String> ids) throws Exception;

    boolean validateAppManagement(AppManagement appManagement, List<String> errorList);

    Boolean updateAppManagement(User user, AppManagement appManagement) throws Exception;
}
