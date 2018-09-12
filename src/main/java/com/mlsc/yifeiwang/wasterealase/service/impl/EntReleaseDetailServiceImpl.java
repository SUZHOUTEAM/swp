package com.mlsc.yifeiwang.wasterealase.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseDetail;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.wasterealase.mapper.EntReleaseDetailMapper;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseModel;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseParam;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseDetailService;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
@Service
public class EntReleaseDetailServiceImpl extends ServiceImpl<EntReleaseDetailMapper, EntReleaseDetail> implements IEntReleaseDetailService {
    private final static Logger logger = LoggerFactory.getLogger(EntReleaseDetailServiceImpl.class);

    @Autowired
    private LicenceService licenceService;

    @Autowired
    private IEntReleaseService releaseService;

    @Override
    public List<EntReleaseDetailModel> listWasteEntReleaseDetail(EntReleaseParam releaseParam){
        return this.baseMapper.listWasteEntReleaseDetail(releaseParam);
    }

    @Override
    public List<EntReleaseDetailModel> countDisposabledWasteAmountReleaseDetail(EntReleaseParam releaseParam) throws Exception {
        List<EntReleaseDetailModel> releaseDetails = null;
        try{
            releaseDetails  = this.baseMapper.countDisposabledWasteAmountReleaseDetail(releaseParam);
        }catch(Exception e){
            logger.error("获取可处置危废总量时异常",e);
            throw e;
        }
        return releaseDetails;
    }


    @Override
    public EntReleaseModel initInquiryList(User user,EntReleaseParam releaseParam) throws Exception {

        EntReleaseModel entReleaseModel = new EntReleaseModel();
        try{
            entReleaseModel = releaseService.getEntReleaseById(releaseParam);
            releaseParam.setCurrentEntId(user.getEnterpriseId());
            OperationLicence operationLicence = licenceService.getValidLicIdByEnterpriseId(user.getEnterpriseId());
            if(operationLicence!=null ){
                releaseParam.setLicenceId(operationLicence.getId());
            }
            List<EntReleaseDetailModel> releaseDetails  = this.baseMapper.initInquiryList(releaseParam);
            List<EntReleaseDetailModel> countAmount = countDisposabledWasteAmountReleaseDetail(releaseParam);
            StringBuilder totalAmount = countWasteAmount(countAmount);
            entReleaseModel.setTotalWasteAmountDesc(totalAmount.toString());
            entReleaseModel.setReleaseWasteDetails(releaseDetails);
        }catch(Exception e){
            logger.error("初始化处置企业询价列表时异常",e);
            throw e;
        }
        return entReleaseModel;
    }

    @Override
    public StringBuilder countWasteAmount(List<EntReleaseDetailModel> countAmount) {
        StringBuilder toltalAmount = new StringBuilder();
        if(countAmount!=null && countAmount.size()>0){
            Double totalAmountT = 0d;
            for (EntReleaseDetailModel releaseDetail : countAmount) {
                if (Constant.UNIT_T.equals(releaseDetail.getUnitValue()) || Constant.UNIT_KG.equals(releaseDetail.getUnitValue()) || Constant.UNIT_G.equals(releaseDetail.getUnitValue())) {
                    totalAmountT = totalAmountT + Util.changeUnitoT(releaseDetail.getWasteAmount(), releaseDetail.getUnitValue());
                } else {
                    toltalAmount.append(releaseDetail.getWasteAmount()).append(releaseDetail.getUnitValue()).append(",");
                }
            }
            if (totalAmountT != 0) {
                toltalAmount.append(totalAmountT).append(Constant.UNIT_T);
            } else {
                if (toltalAmount.length() > 0) {
                    toltalAmount.subSequence(0, toltalAmount.length() - 1);
                }
            }
        }

        return toltalAmount;
    }
}
