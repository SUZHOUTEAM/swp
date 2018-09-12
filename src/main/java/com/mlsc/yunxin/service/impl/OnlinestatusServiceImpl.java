package com.mlsc.yunxin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yunxin.entity.EnableYunXinAccount;
import com.mlsc.yunxin.entity.Onlinestatus;
import com.mlsc.yunxin.mapper.OnlinestatusMapper;
import com.mlsc.yunxin.model.UserInfo;
import com.mlsc.yunxin.service.IOnlinestatusService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yxl
 * @since 2017-07-12
 */
@Service
public class OnlinestatusServiceImpl extends ServiceImpl<OnlinestatusMapper, Onlinestatus> implements IOnlinestatusService {

    @Override
    public UserInfo getUserInfoByAccid(String accid) {
        return baseMapper.getUserInfoByAccid(accid);
    }

    @Override
    public List<UserInfo> listUserInfo() {
        return baseMapper.listUserInfo();
    }

    @Override
    public EnableYunXinAccount getEnableYunXinAccount() {
       return this.baseMapper.getEnableYunXinAccount();
    }
}
