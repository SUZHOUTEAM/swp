package com.mlsc.yifeiwang.user.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.epdp.common.exception.DaoAccessException;
import com.mlsc.epdp.common.utils.EncryptUtils;
import com.mlsc.epdp.common.utils.RSAEncryptUtils;
import com.mlsc.yifeiwang.user.model.UserParam;
import com.mlsc.waste.utils.LoginStatusUtils;
import com.mlsc.yifeiwang.user.mapper.UserInfoMapper;
import com.mlsc.yifeiwang.user.model.UserInfo;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.yifeiwang.weixin.common.Constant;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yxl
 * @since 2017-07-12
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    private final static Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Override
    public UserInfo getUserInfoById(String id) {
        List<String> ids = new ArrayList<>();
        ids.add(id);
        List<UserInfo> infos = this.listUserInfo(ids);
        return infos.stream().findFirst().orElse(null);
    }

    @Override
    public List<UserInfo> listUserInfo(List<String> listUserId) {
        return this.baseMapper.listUserInfo(listUserId);
    }

    @Override
    public List<User> listUserInfoByEnterIds(List<String> enterIds) {
        return this.baseMapper.listUserInfoByEnterIds(enterIds);
    }

    @Override
    public List<User> listSysAdminUser(String userType) {
        return this.baseMapper.listSysAdminUser(userType);
    }

    @Override
    public Map<String, Object> getCodeByOpenId(String openId) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            String code = this.baseMapper.getCodeByOpenId(openId);
            if (code == null) {
                code = Constant.NOUSER;
            } else if (Constant.NOENT.equalsIgnoreCase(code) || StringUtils.isEmpty(code)) {
                UserParam userParam = new UserParam();
                userParam.setOpenId(openId);
                String ticketId = autoLogin(userParam);
                User user = LoginStatusUtils.getUserByTicketId(ticketId);
                result.put("ticketId", ticketId);
                result.put("user", user);
            }
            result.put("code", code);
        } catch (Exception e) {
            logger.error("获取代码时异常", e);
            throw e;
        }
        return result;
    }

    private String autoLogin(UserParam userParam) throws DaoAccessException {
        String ticketId = null;
        if (StringUtils.isNotBlank(userParam.getOpenId())) {
            UserInfo userInfo = this.baseMapper.getUserInfoByOpenId(userParam.getOpenId());
            ticketId = LoginStatusUtils.putUserToCacheByUserPhoneNum(userInfo.getPhoneNum(), "");
        } else if (StringUtils.isNotBlank(userParam.getPhoneNum()) && StringUtils.isNotBlank(userParam.getPassWord())) {
            String password = userParam.getPassWord();
            userParam.setPassWord(EncryptUtils.md5Encrypt(RSAEncryptUtils.decrypt(password)));
            boolean correct = this.baseMapper.checkPassword(userParam);
            if (correct) {
                ticketId = LoginStatusUtils.putUserToCacheByUserPhoneNum(userParam.getPhoneNum(), "");
            }
        }

        return ticketId;
    }

    @Override
    public Map<String, Object> getTicketId(String ticketId, UserParam userParam) throws Exception {
        Map<String, Object> result = new HashedMap();
        try {
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            if (user == null) {
                ticketId = autoLogin(userParam);
                if (StringUtils.isNotBlank(ticketId)) {
                    user = LoginStatusUtils.getUserByTicketId(ticketId);
                    result.put("ticketId", ticketId);
                    result.put("user", user);
                }
            } else {
                result.put("ticketId", ticketId);
                result.put("user", user);
            }
        } catch (Exception e) {
            logger.error("获取ticketId异常", e);
            throw e;
        }
        return result;
    }

    @Override
    public UserInfo getUserInfo(UserParam userParam) throws Exception {
        return this.baseMapper.getUserInfo(userParam);
    }

}
