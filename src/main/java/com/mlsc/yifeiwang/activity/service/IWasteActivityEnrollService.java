package com.mlsc.yifeiwang.activity.service;

import com.mlsc.yifeiwang.activity.entity.WasteActivityEnroll;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.activity.model.WasteActivityEnrollModel;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-02-05
 */
public interface IWasteActivityEnrollService extends IService<WasteActivityEnroll> {

    List<String> saveWasteActivityEnroll(User user, WasteActivityEnroll wasteActivityEnroll)  throws Exception;

    boolean auditApprove(User user, List<String> ids) throws Exception;

    boolean auditReject(User user, List<String> ids) throws Exception;

    boolean deleteEntroll(User user, List<String> ids) throws Exception;

    WasteActivityEnroll checkEntrollStatus(WasteActivityEnroll wasteActivityEnroll) throws Exception;

    List<WasteActivityEnrollModel> listActivityEnroll(WasteActivityEnrollModel wasteActivityEnrollModel, PagingParameter pagingParameter) throws Exception;

    List<WasteActivityEnrollModel> registrationEnrollActivity(WasteActivityEnrollModel wasteActivityEnrollModel, PagingParameter pagingParameter) throws Exception;

    boolean crowdFundingSucceed( User user,WasteActivityEnrollModel wasteActivityEnrollModel) throws Exception;
}
