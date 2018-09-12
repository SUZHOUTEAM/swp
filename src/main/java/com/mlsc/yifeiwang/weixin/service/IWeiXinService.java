package com.mlsc.yifeiwang.weixin.service;

import com.mlsc.yifeiwang.weixin.model.WeiXinModel;
import com.mlsc.yifeiwang.weixin.model.WeiXinParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/8/10.
 */
public interface IWeiXinService {
    List<Object> getAccessToken(String code) throws IOException;

    WeiXinModel weiXinPay(WeiXinParam weiXinParam) throws Exception;

    String weiXinNotify(Map<String, String> params) throws Exception;
}
