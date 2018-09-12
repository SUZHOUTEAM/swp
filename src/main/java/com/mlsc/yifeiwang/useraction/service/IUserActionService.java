package com.mlsc.yifeiwang.useraction.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.useraction.model.ActionTypeModel;
import com.mlsc.yifeiwang.useraction.model.UserActionParam;
import com.mlsc.yifeiwang.useraction.entity.UserAction;
import com.mlsc.yifeiwang.useraction.model.UserActionModel;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-01-03
 */
public interface IUserActionService extends IService<UserAction> {

    boolean saveUserAction(String ticketId, User user, UserAction userAction) throws Exception;

    List<UserActionModel> listUserAction(UserActionParam userActionParam, PagingParameter pagingParameter) throws Exception;

    List<ActionTypeModel> listActionType(UserActionParam userActionParam) throws Exception;

    List<UserActionModel> listUserActionDetailByIp(UserActionParam userActionParam, PagingParameter pagingParameter) throws Exception;

    List<UserActionModel> listUserActionDetailByTicketId(UserActionParam userActionParam, PagingParameter pagingParameter) throws Exception;
}
