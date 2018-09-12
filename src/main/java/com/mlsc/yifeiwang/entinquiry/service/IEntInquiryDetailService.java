package com.mlsc.yifeiwang.entinquiry.service;

import com.baomidou.mybatisplus.service.IService;
import com.mlsc.yifeiwang.entinquiry.entity.EntInquiryDetail;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailParam;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
public interface IEntInquiryDetailService extends IService<EntInquiryDetail> {
    List<EntInquiryDetailModel> listWasteInquiryDetail(EntInquiryDetailParam detailParm) throws Exception;

    List<EntInquiryDetail> listWasteInquiryDetailByInquiryId(EntInquiryDetailParam detailParm) throws Exception;

    List<EntInquiryDetailModel> listNoneWasteInquiryDetail(String releaseId) throws Exception;

    void updateWasteInquiryDetail(EntInquiryDetail detailParm) throws Exception;

    List<EntInquiryDetailModel> countInquiryTotalAmount(List<EntInquiryDetailModel> detailParmList) throws Exception;
}
