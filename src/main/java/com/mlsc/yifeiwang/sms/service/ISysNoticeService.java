package com.mlsc.yifeiwang.sms.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.sms.entity.SysNotice;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.epdp.common.base.entity.ResultData;
import com.mlsc.yifeiwang.sms.model.SysNoticeQueryParam;
import com.mlsc.yifeiwang.sms.model.SysNoticeVo;
import com.mlsc.waste.user.model.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhanghj
 * @since 2017-08-10
 */
public interface ISysNoticeService extends IService<SysNotice> {

    void readNotice(String noticeId,User user);

    void readAllUnreadNotice(User user);

    void saveNoticeList(List<SysNotice> noticeList,User user);

    List<SysNoticeVo> listSysNotice(SysNoticeQueryParam queryParam);

    ResultData<SysNoticeVo> pageSysNotice(SysNoticeQueryParam queryParam,PagingParameter pagingParameter);

    int getUnreadNoticeCount(String receiverId);

    List<JSONObject> listNoticeCategory(String receiverId,String entType);

}
