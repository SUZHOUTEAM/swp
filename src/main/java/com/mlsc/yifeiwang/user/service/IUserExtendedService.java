package com.mlsc.yifeiwang.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.user.entity.UserExtended;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.yifeiwang.user.model.UserInfo;

import java.util.List;

/**
 * <p>
 * 平台用户表的一个扩展表 服务类
 * </p>
 *
 * @author caoyy
 * @since 2018-07-10
 */
public interface IUserExtendedService extends IService<UserExtended> {

    UserExtended getUserExtendedByUserId(String userId);

    List<UserApproveVo> listUserApproveVo(String entId, String userStatus, String role);

    UserExtended getAdminUserByEnterpriseId(String entId);

    void saveOrUpdateUserResgistration(String userId, String registrationCode) throws Exception;

    List<UserInfo> listUserExtends();

    void updateUserExtends(List<UserExtended> list);
}
