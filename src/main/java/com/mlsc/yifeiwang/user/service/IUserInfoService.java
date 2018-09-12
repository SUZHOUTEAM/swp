package com.mlsc.yifeiwang.user.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.user.model.UserParam;
import com.mlsc.yifeiwang.user.model.UserInfo;
import com.mlsc.waste.user.model.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yxl
 * @since 2017-07-12
 */
public interface IUserInfoService extends IService<UserInfo> {

    UserInfo getUserInfoById(String id);

    List<UserInfo> listUserInfo(List<String> listUserId);

    List<User> listUserInfoByEnterIds(List<String> enterIds);

    List<User> listSysAdminUser(String userType);

    Map<String, Object> getCodeByOpenId(String openId) throws Exception;

    Map<String, Object> getTicketId(String ticketId, UserParam userParam) throws Exception;

    UserInfo getUserInfo(UserParam userParam) throws Exception;

}
