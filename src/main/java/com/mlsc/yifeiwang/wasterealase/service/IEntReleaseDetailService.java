package com.mlsc.yifeiwang.wasterealase.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseDetail;
import com.mlsc.waste.user.model.User;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
public interface IEntReleaseDetailService extends IService<EntReleaseDetail> {
    List<EntReleaseDetailModel> listWasteEntReleaseDetail(EntReleaseParam releaseParam);

    List<EntReleaseDetailModel> countDisposabledWasteAmountReleaseDetail(EntReleaseParam releaseParam) throws Exception;

    EntReleaseModel initInquiryList(User user, EntReleaseParam releaseParam) throws Exception;

    StringBuilder countWasteAmount(List<EntReleaseDetailModel> countAmount);
}
