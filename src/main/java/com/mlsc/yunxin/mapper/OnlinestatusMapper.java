package com.mlsc.yunxin.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yunxin.entity.EnableYunXinAccount;
import com.mlsc.yunxin.entity.Onlinestatus;
import com.mlsc.yunxin.model.UserInfo;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yxl
 * @since 2017-07-12
 */
public interface OnlinestatusMapper extends BaseMapper<Onlinestatus> {

   UserInfo getUserInfoByAccid(String accid);
   List<UserInfo> listUserInfo();
   EnableYunXinAccount getEnableYunXinAccount();
}