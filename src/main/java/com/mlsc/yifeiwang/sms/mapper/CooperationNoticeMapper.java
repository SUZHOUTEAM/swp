package com.mlsc.yifeiwang.sms.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.sms.entity.CooperationNotice;
import com.mlsc.yifeiwang.sms.model.CooperationNoticeVo;
import com.mlsc.yifeiwang.sms.model.NoticeQueryParam;

import java.util.List;

/**
 * <p>
 * 通知信息 Mapper 接口
 * </p>
 *
 * @author yxl
 * @since 2017-07-27
 */
public interface CooperationNoticeMapper extends BaseMapper<CooperationNotice> {
    List<CooperationNoticeVo> listMySenderNotices(NoticeQueryParam queryParam);
    Integer countMySenderNotices(NoticeQueryParam queryParam);
    List<CooperationNoticeVo> listMyReceiverNotices(NoticeQueryParam queryParam);
    Integer countMyReceiverNotices(NoticeQueryParam queryParam);
}