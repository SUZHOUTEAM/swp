package com.mlsc.yifeiwang.user.mapper;

import com.mlsc.yifeiwang.user.entity.UserExtended;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.yifeiwang.user.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 平台用户表的一个扩展表 Mapper 接口
 * </p>
 *
 * @author caoyy
 * @since 2018-07-10
 */
public interface UserExtendedMapper extends BaseMapper<UserExtended> {

    UserExtended getUserExtendedByUserId(@Param("sysUserId") String userId);

    List<UserApproveVo> listUserApproveVo(@Param("entId") String entId, @Param("userStatus") String userStatus, @Param("role") String role);

    UserExtended getAdminUserByEnterpriseId(@Param("entId") String entId);

    List<UserInfo> listUserExtends();
}