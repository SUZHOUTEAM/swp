package com.mlsc.yifeiwang.activity.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.activity.entity.WasteActivity;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.activity.model.ActivityQueryParam;
import com.mlsc.yifeiwang.activity.model.EnterpriseWasteVo;
import com.mlsc.yifeiwang.activity.model.WasteActivityDetailVO;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import com.mlsc.waste.user.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yxl
 * @since 2017-07-31
 */
public interface IWasteActivityService extends IService<WasteActivity> {
    List<WasteActivityVO> listWasteActivityByEntId(ActivityQueryParam param, PagingParameter pagingParameter) throws Exception;

    List<WasteActivityVO> listWasteActivity(ActivityQueryParam param);

    Page<WasteActivity> listWasteActivity(Page<WasteActivity> page);

    void updateActivity(List<String> ids, WasteActivity wa, User user) throws Exception;

    void reomoveJob(List<String> ids, String status) throws Exception;

    List<WasteActivityVO> listActiviyNameByApplyEntId(String enterpriseId) throws Exception;

    List<WasteActivityVO> listOrderActiviyNameByApplyEntId(String entId) throws Exception;

    List<WasteActivityDetailVO> getActivityDetailById(ActivityQueryParam param);

    WasteActivity save(String ticketId, WasteActivityVO wasteActivityVO) throws Exception;

    List<WasteActivityVO> getCurrentActivity(String cantonCode);

    WasteActivityVO getWasteActivityVoById(ActivityQueryParam param);

    List<WasteActivityVO> listAcitiveByShoppingCardId(ActivityQueryParam activityVo);

    List<WasteActivityVO> listAcitiveByOrderId(ActivityQueryParam activityVo);

    List<WasteActivityDetailVO> listCapacityreleaseByActivityId(User user, ActivityQueryParam param);

    List<EnterpriseWasteVo> listEnterpriseWasteByWasteId(ActivityQueryParam param);

    Integer getEnterpriseWasteBuyCount(ActivityQueryParam param);

    boolean deleteLogicBatchIds(List<String> strings);

    WasteActivityDetailVO getActivityCantonCodeById(ActivityQueryParam param);

    Integer isInActivityCanton(String cantonCodes, User user);

    boolean publishByBatchIds(String ticketId, List<String> strings) throws Exception;

    List<String> getCantonCodeList(String cantonCode);

    Integer getWasteCountByWasteCode(List<WasteActivityDetailVO> activityDetailListByEnt);

    List<WasteActivityVO> listOrderActiviyName(String entId) throws Exception;

    List<WasteActivityVO> listActiviyNameByInquiryEntId(String enterpriseId) throws Exception;

    List<WasteActivityVO> listHomePageWasteActivity(ActivityQueryParam param, PagingParameter pagingParameter) throws Exception;

    List<String> listStopWasteActivity();

    List<WasteActivityVO> listStartWasteActivity();


    void updateActivityCoverEntCount(User user, String id) throws Exception;

    void stopActivity(List<String> ids, User user, String status) throws Exception;

    List<WasteActivityVO> listLiveWasteActivity(ActivityQueryParam activityVo);
}

