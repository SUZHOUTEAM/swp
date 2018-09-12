package com.mlsc.yifeiwang.activity.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.activity.entity.WasteActivity;
import com.mlsc.yifeiwang.activity.model.ActivityQueryParam;
import com.mlsc.yifeiwang.activity.model.EnterpriseWasteVo;
import com.mlsc.yifeiwang.activity.model.WasteActivityDetailVO;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yxl
 * @since 2017-07-31
 */
public interface WasteActivityMapper extends BaseMapper<WasteActivity> {

    List<WasteActivityVO> listWasteActivity(ActivityQueryParam param);

    List<WasteActivityDetailVO> getActivityDetailById(ActivityQueryParam param);

    List<WasteActivityVO> listAcitiveByShoppingCardId(ActivityQueryParam activityVo);

    List<WasteActivityVO> listAcitiveByOrderId(ActivityQueryParam activityVo);

    List<WasteActivityDetailVO> listCapacityreleaseByActivityId(ActivityQueryParam param);


    List<EnterpriseWasteVo> listEnterpriseWasteByWasteId(ActivityQueryParam param);

    Integer getEnterpriseWasteBuyCount(ActivityQueryParam param);

    Integer isInActivityCanton(ActivityQueryParam param);

    WasteActivityDetailVO getActivityCantonCodeById(ActivityQueryParam param);

    List<WasteActivityVO> listWasteActivityByEntId(ActivityQueryParam param);

    int countWasteActivityByEntId(ActivityQueryParam param);

    List<WasteActivityVO> listOrderActiviyName(@Param("entId") String entId);


    List<WasteActivityVO> listOrderActiviyNameByApplyEntId(@Param("entId") String entId);

    List<WasteActivityVO> listActiviyNameByInquiryEntId(@Param("entId") String entId);


    List<WasteActivityVO> listActiviyNameByApplyEntId(@Param("entId") String enterpriseId);

    List<WasteActivityVO> listHomePageWasteActivity(ActivityQueryParam param);

    int countHomePageWasteActivity(ActivityQueryParam param);

    List<String> listStopWasteActivity();

    List<WasteActivityVO> listStartWasteActivity();

    int countLiveWasteActivity(ActivityQueryParam activityVo);

    List<WasteActivityVO> listLiveWasteActivity(ActivityQueryParam activityVo);
}