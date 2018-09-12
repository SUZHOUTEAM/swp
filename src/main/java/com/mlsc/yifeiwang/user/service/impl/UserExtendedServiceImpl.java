package com.mlsc.yifeiwang.user.service.impl;

import com.mlsc.common.exception.WasteBusinessException;
import com.mlsc.yifeiwang.user.entity.UserExtended;
import com.mlsc.yifeiwang.sms.model.MessageBean;
import com.mlsc.waste.user.model.UserApproveVo;
import com.mlsc.yifeiwang.user.mapper.UserExtendedMapper;
import com.mlsc.yifeiwang.user.model.UserInfo;
import com.mlsc.yifeiwang.user.service.IUserExtendedService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 平台用户表的一个扩展表 服务实现类
 * </p>
 *
 * @author caoyy
 * @since 2018-07-10
 */
@Service
public class UserExtendedServiceImpl extends ServiceImpl<UserExtendedMapper, UserExtended> implements IUserExtendedService {
    private final static Logger logger = LoggerFactory.getLogger(com.mlsc.waste.user.service.impl.UserExtendedServiceImpl.class);

    @Override
    public UserExtended getUserExtendedByUserId(String userId) {
        return this.baseMapper.getUserExtendedByUserId(userId);
    }

    @Override
    public List<UserApproveVo> listUserApproveVo(String entId, String userStatus, String role) {
        return this.baseMapper.listUserApproveVo(entId, userStatus, role);
    }

    @Override
    public UserExtended getAdminUserByEnterpriseId(String entId) {
        return this.baseMapper.getAdminUserByEnterpriseId(entId);
    }


    @Override
    public void saveOrUpdateUserResgistration(String userId, String registrationCode) throws Exception {
        try {
            UserExtended userExtended = getUserExtendedByUserId(userId);
            if (userExtended != null && StringUtils.isNotEmpty(userExtended.getId())) {
                userExtended.setRegistrationCode(registrationCode);
                this.updateById(userExtended);
            } else {
                userExtended = new UserExtended();
                userExtended.setSysUserId(userId);
                userExtended.setRegistrationCode(registrationCode);
                this.insert(userExtended);
            }
        } catch (Exception e) {
            logger.error("设备绑定失败", e);
            throw new WasteBusinessException(new MessageBean(MessageBean.STATUS_INFO, MessageBean.NOTICE_TYPE_NOTIFY, "设备绑定失败"));
        }
    }

    @Override
    public List<UserInfo> listUserExtends() {
        return this.baseMapper.listUserExtends();
    }

    @Override
    public void updateUserExtends(List<UserExtended> list) {
        this.updateBatchById(list);
    }
}
