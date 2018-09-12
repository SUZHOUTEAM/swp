package com.mlsc.yifeiwang.entinquiry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.entinquiry.entity.EntInquiry;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryParam;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
public interface EntInquiryMapper extends BaseMapper<EntInquiry> {
    List<EntInquiryModel> listWasteInquiry(EntInquiryParam inquiryParam);

    int countWasteInquiry(EntInquiryParam inquiryParam);

    List<EntReleaseDetailModel> listInquiryAmount(EntInquiryParam inquiryParam);

    EntInquiryDetailModel inquiryTotalPrice(EntInquiryParam inquiryParam);

    List<EntInquiry> listEntInquiryByReleaseId(@Param("releaseId") String releaseId);

    int countUntreatedInquiry(@Param("entId") String entId);

    List<EntInquiryModel> listReleaseEntNameByEntId(@Param("entId") String enterpriseId);
}