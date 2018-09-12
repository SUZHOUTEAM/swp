package com.mlsc.yifeiwang.user.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.user.model.UserParam;
import com.mlsc.yifeiwang.user.model.UserInfo;
import com.mlsc.waste.user.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yxl
 * @since 2017-07-12
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<UserInfo> listUserInfo(List<String> listUserId);

    List<User> listUserInfoByEnterIds(List<String> enterIds);

    List<User> listSysAdminUser(@Param("userType") String userType);

    void updateUserInfo(User user);

    String getCodeByOpenId(@Param("openId") String openId);

    UserInfo getUserInfoByOpenId(@Param("openId") String openId);

    Boolean checkPassword(UserParam userParam);

    UserInfo getUserInfo(UserParam userParam);
}