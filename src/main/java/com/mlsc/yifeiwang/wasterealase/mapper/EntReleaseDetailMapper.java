package com.mlsc.yifeiwang.wasterealase.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseDetail;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseParam;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
public interface EntReleaseDetailMapper extends BaseMapper<EntReleaseDetail> {
    List<EntReleaseDetailModel> listWasteEntReleaseDetail(EntReleaseParam releaseParam);
    List<EntReleaseDetailModel> countDisposabledWasteAmountReleaseDetail(EntReleaseParam releaseParam);
    List<EntReleaseDetailModel> initInquiryList( EntReleaseParam releaseParam);
}