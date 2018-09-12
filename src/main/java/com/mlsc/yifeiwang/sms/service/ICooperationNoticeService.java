package com.mlsc.yifeiwang.sms.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.sms.entity.CooperationNotice;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.sms.model.CooperationNoticeVo;
import com.mlsc.waste.user.model.User;

import java.util.List;

/**
 * <p>
 * 通知信息 服务类
 * </p>
 *
 * @author yxl
 * @since 2017-07-27
 */
public interface ICooperationNoticeService extends IService<CooperationNotice> {
    /**
     * 单条通知记录的已读未读更新
     *
     * @param cooperationNoticeId 通知的id
     * @param isRead 需要更新成的状态  已读（Constant.IS_YSE）未读（Constant.IS_NO）
     * @author zhugl date 2016-08-25
     */
    void updateCooperationNoticeIsRead(String cooperationNoticeId, String isRead, String ticketId);

    /**
     * 易废圈通知信息（消息）表的批量保存
     *
     * @author zhugl date date 2016-08-25
     */
    void saveCooperationNotices(List<CooperationNotice> cooperationNotices, User user);

    /**
     * 通过ID查询易废圈通知信息（消息）表
     *
     * @author zhugl date date 2016-08-25
     */
    CooperationNotice getCooperationNoticeById(String cooperationNoticeId);

    /** 查询我发布的所有的通知
     *
     *  @param senderId 发送通知者
     *  @param isRead 是否已读？ 已读（Constant.IS_YSE）未读（Constant.IS_NO） null时不作为检索条件
     * @author zhugl date date 2016-08-25
     */
    List<CooperationNoticeVo> getMySenderNotices(String senderId,String isRead, PagingParameter paging, String ticketId);


    /** 查询我收到的通知
     *
     * @param receiverId 通知接受者
     * @param isRead 是否已读？ 已读（Constant.IS_YSE）未读（Constant.IS_NO） null时不作为检索条件
     * @author zhugl date date 2016-08-25
     * @param ticketId
     */
    List<CooperationNoticeVo> getMyReceiverNotices(String receiverId,String isRead, PagingParameter paging, String ticketId);

    /** 查询我收到的未读通知的件数
     *
     * @param receiverId 通知接受者
     * @param isRead 是否已读？ 已读（Constant.IS_YSE）未读（Constant.IS_NO） null时不作为检索条件
     * @author zhugl date date 2016-08-25
     */
    int getMyReceiverNoticeCount(String receiverId, String isRead);

    /** 查询我我发出通知的件数
     *
     * @param senderId 通知接受者
     * @param isRead 是否已读？ 已读（Constant.IS_YSE）未读（Constant.IS_NO） null时不作为检索条件
     * @author zhugl date date 2016-08-25
     */
    int getMySenderNoticeCount(String senderId, String isRead);


    /**
     * 根据条件筛选消息
     * @param receiverId 接收者的ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param infotype  通知类型
     * @param isRead    是否只显示未读
     * @param pagingParameter  分页
     * @return
     */
    List<CooperationNoticeVo> searchMyReceiverNotices(String receiverId,
            String startDate, String endDate, String infotype, String isRead,
            PagingParameter pagingParameter);

    /**
     * 查询筛选出的消息的总条数
     * @param receiverId
     * @param startDate
     * @param endDate
     * @param infotype
     * @param isRead
     * @return
     */
    int searchMyReceiverNoticesTotalcount(String receiverId, String startDate,
            String endDate, String infotype, String isRead);

}
