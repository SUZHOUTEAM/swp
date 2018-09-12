package com.mlsc.yifeiwang.sms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.sms.entity.SysNotice;
import com.mlsc.yifeiwang.sms.model.SysNoticeQueryParam;
import com.mlsc.yifeiwang.sms.model.SysNoticeVo;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zhanghj
 * @since 2017-08-10
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    List<SysNoticeVo> listSysNotice(SysNoticeQueryParam noticeQueryParam);

    int countSysNotice(SysNoticeQueryParam noticeQueryParam);

}