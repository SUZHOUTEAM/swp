package com.mlsc.yifeiwang.entinquiry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.entinquiry.entity.EntInquiryDetail;
import com.mlsc.yifeiwang.entinquiry.mapper.EntInquiryDetailMapper;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailParam;
import com.mlsc.yifeiwang.entinquiry.service.IEntInquiryDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
@Service
public class EntInquiryDetailServiceImpl extends ServiceImpl<EntInquiryDetailMapper, EntInquiryDetail> implements IEntInquiryDetailService {
    private final static Logger logger = LoggerFactory.getLogger(EntInquiryDetailServiceImpl.class);


    @Override
    public List<EntInquiryDetailModel> listWasteInquiryDetail(EntInquiryDetailParam detailParma) throws Exception {
        List<EntInquiryDetailModel> inquiryDetailList = null;
        try {
            inquiryDetailList = this.baseMapper.listWasteInquiryDetail(detailParma);
        } catch (Exception e) {
            logger.error("获取询价详情时异常",e);
            throw e;
        }
        return inquiryDetailList;
    }


    @Override
    public List<EntInquiryDetailModel> listNoneWasteInquiryDetail(String releaseId ) throws Exception {
        List<EntInquiryDetailModel> inquiryDetailList = null;
        try {
            inquiryDetailList = this.baseMapper.listNoneWasteInquiryDetail(releaseId);
        } catch (Exception e) {
            logger.error("获取未有系统报价时异常",e);
            throw e;
        }
        return inquiryDetailList;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateWasteInquiryDetail(EntInquiryDetail detailParm) throws Exception {
        try {
            this.baseMapper.updateWasteInquiryDetail(detailParm);
        } catch (Exception e) {
            logger.error("更新询价详情时异常",e);
            throw e;
        }
    }
    @Override
    public List<EntInquiryDetail> listWasteInquiryDetailByInquiryId(EntInquiryDetailParam detailParm) throws Exception {
        List<EntInquiryDetail> inquiryDetailList = null;
        try {
            inquiryDetailList = this.baseMapper.listWasteInquiryDetailByInquiryId(detailParm);
        } catch (Exception e) {
            logger.error("获取询价详情时异常",e);
            throw e;
        }
        return inquiryDetailList;
    }


    @Override
    public List<EntInquiryDetailModel> countInquiryTotalAmount(List<EntInquiryDetailModel> detailParmList) throws Exception {
        List<EntInquiryDetailModel> inquiryDetailList = null;
        try {
            inquiryDetailList = this.baseMapper.countInquiryTotalAmount(detailParmList);
        } catch (Exception e) {
            logger.error("获取询价详情时异常",e);
            throw e;
        }
        return inquiryDetailList;
    }

}
