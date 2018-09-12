package com.mlsc.yifeiwang.sms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.sms.entity.CooperationNotice;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.sms.mapper.CooperationNoticeMapper;
import com.mlsc.yifeiwang.sms.model.CooperationNoticeVo;
import com.mlsc.yifeiwang.sms.model.NoticeQueryParam;
import com.mlsc.yifeiwang.sms.model.NoticeRouteEnum;
import com.mlsc.yifeiwang.sms.service.ICooperationNoticeService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.LoginStatusUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 通知信息 服务实现类
 * </p>
 *
 * @author yxl
 * @since 2017-07-27
 */
@Service
public class CooperationNoticeServiceImpl extends ServiceImpl<CooperationNoticeMapper, CooperationNotice> implements ICooperationNoticeService {

    @Override
    public void updateCooperationNoticeIsRead(String cooperationNoticeId, String isRead, String ticketId) {
        User user = LoginStatusUtils.getUserByTicketId(ticketId);
        CooperationNotice cooperationNotice = selectById(cooperationNoticeId);
        cooperationNotice.setIsRead(isRead);
        cooperationNotice.setEditBy(user.getUserId());
        cooperationNotice.setEditTime(new Date());
        updateById(cooperationNotice);
    }

    @Override
    public void saveCooperationNotices(List<CooperationNotice> cooperationNotices, User user) {
        if (cooperationNotices != null && cooperationNotices.size() > 0) {
            for (CooperationNotice notice : cooperationNotices) {
                notice.setValid(Constant.IS_VALID);
                notice.setEditBy(user.getUserId());
                notice.setEditTime(new Date());
                notice.setCreateBy(user.getUserId());
                notice.setCreateTime(notice.getEditTime());
            }
            insertOrUpdateBatch(cooperationNotices);
        }
    }

    @Override
    public CooperationNotice getCooperationNoticeById(String cooperationNoticeId) {
        return selectById(cooperationNoticeId);
    }

    @Override
    public List<CooperationNoticeVo> getMySenderNotices(String senderId, String isRead, PagingParameter paging, String ticketId) {
        Integer start = null, rows = null;
        if (paging != null) {
            start = paging.getStart();
            rows = paging.getLimit() > 0 ? paging.getLimit() : null;
        }
        NoticeQueryParam param = new NoticeQueryParam(senderId,null,isRead,start,rows);
        List<CooperationNoticeVo> resultlist = this.baseMapper.listMySenderNotices(param);

        resetSummaryAndContent(resultlist);

        return resultlist;
    }
    @Override
    public int getMySenderNoticeCount(String senderId, String isRead) {
        NoticeQueryParam param = new NoticeQueryParam(senderId,null,isRead);
        return this.baseMapper.countMySenderNotices(param);
    }

    @Override
    public List<CooperationNoticeVo> getMyReceiverNotices(String receiverId, String isRead, PagingParameter paging, String ticketId) {
        Integer start = null, rows = null;
        if (paging != null) {
            start = paging.getStart();
            rows = paging.getLimit() > 0 ? paging.getLimit() : null;
        }
        NoticeQueryParam param = new NoticeQueryParam(null,receiverId,isRead,start,rows);
        List<CooperationNoticeVo> resultlist = this.baseMapper.listMyReceiverNotices(param);

        resetSummaryAndContent(resultlist);

        return resultlist;
    }

    @Override
    public int getMyReceiverNoticeCount(String receiverId, String isRead) {
        NoticeQueryParam param = new NoticeQueryParam(null, receiverId, isRead);

        return this.baseMapper.countMyReceiverNotices(param);
    }

    private void resetSummaryAndContent(List<CooperationNoticeVo> resultlist) {
        for (CooperationNoticeVo noticeVo : resultlist){
            String content = noticeVo.getNoticeContent();
            if (content.lastIndexOf(";") >= 0) {
                String summary = content.substring(content.lastIndexOf(";") + 1, content.length());
                noticeVo.setNoticeContentSummary(summary);
                noticeVo.setNoticeContent(content.substring(0, content.lastIndexOf(";")));
            }
        }
    }

    @Override
    public List<CooperationNoticeVo> searchMyReceiverNotices(String receiverId, String startDate, String endDate, String infotype, String isRead, PagingParameter paging) {
        Integer start = null, rows = null;
        if (paging != null) {
            start = paging.getStart();
            rows = paging.getLimit() > 0 ? paging.getLimit() : null;
        }
        NoticeQueryParam param = new NoticeQueryParam(null, receiverId, isRead, start, rows, infotype, startDate, endDate);
        List<CooperationNoticeVo> resultlist = this.baseMapper.listMyReceiverNotices(param);
        Iterator<CooperationNoticeVo> itr = resultlist.iterator();
        while (itr.hasNext()) {
            CooperationNoticeVo noticeVo = itr.next();
            noticeVo.setRouteCode(NoticeRouteEnum.rountCode(noticeVo.getNoticeContent()));
        }
        return resultlist;
    }

    @Override
    public int searchMyReceiverNoticesTotalcount(String receiverId, String startDate, String endDate, String infotype, String isRead) {
        NoticeQueryParam param = new NoticeQueryParam(null, receiverId, isRead, infotype, startDate, endDate);

        return this.baseMapper.countMyReceiverNotices(param);
    }
}
