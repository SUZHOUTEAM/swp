package com.mlsc.yunxin.service;


import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yunxin.entity.EnableYunXinAccount;
import com.mlsc.yunxin.entity.Onlinestatus;
import com.mlsc.yunxin.model.UserInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yxl
 * @since 2017-07-12
 */
public interface IOnlinestatusService extends IService<Onlinestatus> {
    UserInfo getUserInfoByAccid(String accid);
    List<UserInfo> listUserInfo();
    EnableYunXinAccount getEnableYunXinAccount();
}
