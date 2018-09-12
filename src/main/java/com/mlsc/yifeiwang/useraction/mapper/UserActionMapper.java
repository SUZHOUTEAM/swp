package com.mlsc.yifeiwang.useraction.mapper;

import com.mlsc.yifeiwang.useraction.entity.UserAction;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.useraction.model.ActionTypeModel;
import com.mlsc.yifeiwang.useraction.model.UserActionModel;
import com.mlsc.yifeiwang.useraction.model.UserActionParam;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-01-03
 */
public interface UserActionMapper extends BaseMapper<UserAction> {
    List<UserActionModel> listUserAction(UserActionParam userActionParam);

    int countUserAction(UserActionParam userActionParam);

    List<ActionTypeModel> listActionType(UserActionParam userActionParam);

    List<UserActionModel> listUserActionDetailByIp(UserActionParam userActionParam);

    int countUserActionDetailByIp(UserActionParam userActionParam);

    List<UserActionModel> listUserActionDetailByTicketId(UserActionParam userActionParam);

    int countUserActionDetailByTicketId(UserActionParam userActionParam);

}