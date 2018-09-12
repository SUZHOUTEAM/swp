package com.mlsc.yifeiwang.entinquiry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mlsc.yifeiwang.entinquiry.entity.EntInquiryDetail;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailParam;
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
public interface EntInquiryDetailMapper extends BaseMapper<EntInquiryDetail> {
    List<EntInquiryDetailModel> listWasteInquiryDetail(EntInquiryDetailParam detailParam);

    List<EntInquiryDetailModel>  listNoneWasteInquiryDetail(@Param("releaseId") String releaseId);

    void updateWasteInquiryDetail(EntInquiryDetail detailParam);

    List<EntInquiryDetail> listWasteInquiryDetailByInquiryId(EntInquiryDetailParam detailParam);

    List<EntInquiryDetailModel> countInquiryTotalAmount(@Param("detailParamList") List<EntInquiryDetailModel> detailParamList);
}