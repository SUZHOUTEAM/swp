package com.mlsc.yifeiwang.wasterealase.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.common.util.RSAEncryptUtils;
import com.mlsc.rpc.thrift.api.dto.RPCSysEnterpriseBase;
import com.mlsc.waste.enterprise.service.EnterpriseService;
import com.mlsc.waste.user.service.UserLoginService;
import com.mlsc.yifeiwang.facilitator.service.IFacilitatorCustomerService;
import com.mlsc.yifeiwang.wasterealase.common.QuotedTypeEnum;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.entinquiry.common.InquiryStatusEnum;
import com.mlsc.yifeiwang.entinquiry.common.InquiryType;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.common.SmsTemplateCode;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SmsService;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.yifeiwang.activity.model.ActivityQueryParam;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import com.mlsc.yifeiwang.activity.service.IWasteActivityService;
import com.mlsc.waste.enterprise.model.RPCSysEnterpriseBaseVo;
import com.mlsc.waste.fw.model.SysCanton;
import com.mlsc.waste.fw.service.SysCantonService;
import com.mlsc.waste.licence.model.OperationLicence;
import com.mlsc.waste.licence.service.LicenceService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.*;
import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;
import com.mlsc.yifeiwang.discusstag.service.IDiscussTagService;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import com.mlsc.yifeiwang.entinquiry.entity.EntInquiry;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailParam;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryParam;
import com.mlsc.yifeiwang.entinquiry.service.IEntInquiryService;
import com.mlsc.yifeiwang.entwaste.model.EntWasteParams;
import com.mlsc.yifeiwang.entwaste.service.IEntWasteService;
import com.mlsc.yifeiwang.wasterealase.common.ReleaseStatus;
import com.mlsc.yifeiwang.wasterealase.common.ReleaseType;
import com.mlsc.yifeiwang.wasterealase.mapper.EntReleaseMapper;
import com.mlsc.yifeiwang.wasterealase.entity.EntRelease;
import com.mlsc.yifeiwang.wasterealase.entity.EntReleaseDetail;
import com.mlsc.yifeiwang.wasterealase.model.*;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseDetailService;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseService;
import com.mlsc.yifeiwang.wasterealase.service.IWasteReferencePriceDetailService;
import com.mlsc.yifeiwang.wasterealase.service.IWasteReferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
@Service
public class EntReleaseServiceImpl extends ServiceImpl<EntReleaseMapper, EntRelease> implements IEntReleaseService {
    private final static Logger logger = LoggerFactory.getLogger(EntReleaseServiceImpl.class);
    @Autowired
    private IEntReleaseDetailService entReleaseDetailService;
    @Autowired
    private LicenceService licenceService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Autowired
    private IEntInquiryService entInquiryService;

    @Autowired
    private IWasteActivityService wasteActivityService;

    @Autowired
    private ISysEnterpriseBaseService enterpriseBaseService;

    @Autowired
    private IDiscussTagService discussTagService;

    @Autowired
    private IEntWasteService entWasteService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SysCantonService sysCantonService;

    @Autowired
    private IWasteReferenceService referenceService;

    @Autowired
    private IWasteReferencePriceDetailService priceDetailService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private IFacilitatorCustomerService facilitatorCustomerService;


    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public EntReleaseModel saveEntRelease(User user, EntReleaseParam entReleaseParam) throws Exception {
        try {

            EntRelease entRelease = saveEntReleaseInfo(user, entReleaseParam);
            saveEntReleaseDetail(user, entRelease.getId(), entReleaseParam.getReleaseDetail());
            entReleaseParam.setReleaseId(entRelease.getId());
            EntReleaseModel entReleaseModel = new EntReleaseModel();
            if (!user.getEntType().equalsIgnoreCase(CodeTypeConstant.ENTERPRISE_TYPE_FACILITATOR)) {
                generateSystemInquiry(entRelease, entReleaseParam.getReleaseDetail());
                informSysAdmin(user);
                entReleaseModel.setInformEntCount(informEnterpriseByReleaseId(user, entReleaseParam));
            }
            entReleaseModel.setReleaseId(entRelease.getId());
            return entReleaseModel;
        } catch (Exception e) {
            logger.error("发布企业危废时异常", e);
            throw e;
        }

    }


    private void generateSystemInquiry(EntRelease entRelease, List<EntReleaseDetailParam> entReleaseDetails) {
        try {
            if (entRelease != null && entReleaseDetails != null && entReleaseDetails.size() > 0) {
                SysEnterpriseBase sysEnterpriseBase = enterpriseBaseService.getDefaultDispositionEnt();
                EntInquiryParam inquiryParam = new EntInquiryParam();
                inquiryParam.setInquiryEntId(sysEnterpriseBase.getEntId());
                inquiryParam.setReleaseEntId(entRelease.getEntId());
                inquiryParam.setReleaseId(entRelease.getId());
                inquiryParam.setInquiryType(InquiryType.REFERENCE_PRICE.getCode());
                inquiryParam.setQuotedType(QuotedTypeEnum.SINGLE_QUOTE.getCode());
                inquiryParam.setRemark(Constant.SYS_INQUIRY_REMARK);

                List<WasteReferencePriceModel> defaultDispositionTypes = listDefaultDispositionType(entRelease.getEntId(), entReleaseDetails);
                List<EntInquiryDetailParam> inquiryDetails = ListAmountPerEntWaste(defaultDispositionTypes, entReleaseDetails);

                if (inquiryDetails.size() > 0) {
                    Double totalPrice = getTotalPrice(inquiryDetails);
                    inquiryParam.setTotalPrice(totalPrice);
                    inquiryParam.setTotalAmount(countWasteAmount(inquiryDetails));
                    inquiryParam.getInquiryDetail().addAll(inquiryDetails);
                    saveEntInquiry(inquiryParam);
                }
            }
        } catch (Exception e) {
            logger.error("系统报价时异常", e);
        }
    }

    private List<EntInquiryDetailParam> ListAmountPerEntWaste(List<WasteReferencePriceModel> defaultDispositionTypes, List<EntReleaseDetailParam> entReleaseDetails) {
        List<EntInquiryDetailParam> inquiryDetails = new ArrayList<>();
        defaultDispositionTypes.forEach(defaultDisposition -> {
            WasteReferencePriceModel priceDetail = getPriceByDefaultDisposition(defaultDisposition);
            EntReleaseDetailParam releaseDetail = entReleaseDetails.stream().filter(entReleaseDetail -> entReleaseDetail.getEntWasteId().equalsIgnoreCase(defaultDisposition.getEntWasteId())).findFirst().orElse(null);
            if (releaseDetail != null && priceDetail != null) {
                EntInquiryDetailParam inquiryDetail = new EntInquiryDetailParam();
                inquiryDetail.setPriceId(priceDetail.getPriceId());
                inquiryDetail.setReleaseDetailId(releaseDetail.getId());
                inquiryDetail.setWasteName(releaseDetail.getWasteName());
                inquiryDetail.setAmount(releaseDetail.getReleaseAmount());
                inquiryDetail.setRemark(defaultDisposition.getRemark());
                inquiryDetail.setDispositionType(defaultDisposition.getDefaultDispositionType());
                inquiryDetail.setUnitCode(releaseDetail.getUnitCode());
                inquiryDetail.setAmountT(Util.changeUnitoT(inquiryDetail.getAmount(), inquiryDetail.getUnitCode()));
                inquiryDetails.add(inquiryDetail);
            }
        });
        return inquiryDetails;
    }

    private String countWasteAmount(List<EntInquiryDetailParam> countAmount) {
        countAmount.forEach(item -> {
            if (CodeModel.UNIT_TYPE.KG.getCode().equals(item.getUnitCode())) {
                item.setUnitCode(CodeModel.UNIT_TYPE.T.getCode());
            } else if (CodeModel.UNIT_TYPE.G.getCode().equals(item.getUnitCode())) {
                item.setUnitCode(CodeModel.UNIT_TYPE.T.getCode());
            }
        });

        StringBuilder TotalAmountStr = new StringBuilder();
        Map<String, List<EntInquiryDetailParam>> groupInquiry = countAmount.stream().collect(Collectors.groupingBy(EntInquiryDetailParam::getUnitCode));
        groupInquiry.forEach((key, inquiryList) -> {
            Double totalAmount;
            if (CodeModel.UNIT_TYPE.T.getCode().equalsIgnoreCase(key)) {
                totalAmount = inquiryList.stream().mapToDouble(EntInquiryDetailParam::getAmountT).sum();
            } else {
                totalAmount = inquiryList.stream().mapToDouble(EntInquiryDetailParam::getAmount).sum();
            }

            String unitValue = CodeModel.UNIT_TYPE.getLabel(key);
            TotalAmountStr.append(totalAmount.toString()).append(unitValue);
        });


        return TotalAmountStr.toString();
    }

    private void saveEntInquiry(EntInquiryParam inquiryParam) throws Exception {
        User user = new User();
        user.setUserId(Constant.SYS_ADMIN);
        user.setEnterpriseId(inquiryParam.getInquiryEntId());
        EntInquiry entInquiry = new EntInquiry();
        boolean insertResult = entInquiryService.insertEntInquiry(user, inquiryParam, entInquiry);
        if (insertResult) {
            entInquiryService.saveEntDetailInquiry(inquiryParam, entInquiry, user);
        }
    }

    private Double getTotalPrice(List<EntInquiryDetailParam> inquiryDetails) {
        mergeR15AndR10Waste(inquiryDetails);
        Map<String, List<EntInquiryDetailParam>> groupInquiry = inquiryDetails.stream().collect(Collectors.groupingBy(EntInquiryDetailParam::getPriceId));
        List<Double> totalPrices = new ArrayList<>();
        groupInquiry.forEach((priceId, inquiryDetailParams) -> {
            Double amount = inquiryDetailParams.stream().collect(Collectors.summingDouble(EntInquiryDetailParam::getAmountT));
            amount = calculateAmount(amount);
            Optional<EntInquiryDetailParam> detailParam = inquiryDetails.stream().filter(inquiryDetailParam -> inquiryDetailParam.getPriceId().equals(priceId)).findFirst();
            if (detailParam.isPresent()) {
                WasteReferencePriceParam priceParam = new WasteReferencePriceParam();
                priceParam.setPriceId(priceId);
                priceParam.setAmount(amount);
                WasteReferencePriceModel priceModel = priceDetailService.getPriceByParentAndAmount(priceParam);
                Double totalPrice = priceModel.getPrice() * amount;
                totalPrices.add(totalPrice);

                List<EntInquiryDetailParam> inquiryDetailList = inquiryDetails.stream().filter(inquiryDetailParam -> inquiryDetailParam.getPriceId().equals(priceId)).collect(Collectors.toList());
                inquiryDetailList.forEach(inquiryDetail -> {
                    inquiryDetail.setPrice(priceModel.getPrice());
                    inquiryDetail.setTotalPrice(totalPrice);
                });

            }
        });

        return totalPrices.stream().mapToDouble(item -> item).sum();
    }

    private void mergeR15AndR10Waste(List<EntInquiryDetailParam> inquiryDetails) {
        EntInquiryDetailParam d10InquiryDetail = inquiryDetails.stream().filter(inquiryDetail -> CodeTypeConstant.DISPOSE_TYPE_D10.equalsIgnoreCase(inquiryDetail.getDispositionType())).findAny().orElse(null);
        List<String> keywords = Stream.of(Constant.CARBON_KEYWORD.split(Constant.COMMA)).collect(Collectors.toList());
        List<EntInquiryDetailParam> r15InquiryDetails = inquiryDetails.stream().filter(inquiryDetail -> inquiryDetail.getDispositionType().equalsIgnoreCase(CodeTypeConstant.DISPOSE_TYPE_R15))
                .filter(inquiryDetail -> keywords.stream().filter(keyword -> inquiryDetail.getWasteName().indexOf(keyword) != -1).collect(Collectors.toList()).size() > 0).collect(Collectors.toList());
        Double r15Amount = r15InquiryDetails.stream().collect(Collectors.summingDouble(EntInquiryDetailParam::getAmountT));

        if (r15Amount < Constant.MAX_AMOUNT && d10InquiryDetail != null) {
            r15InquiryDetails.forEach(r15inquiryDetail -> {
                inquiryDetails.stream().filter(inquiryDetail -> inquiryDetail.getReleaseDetailId().equalsIgnoreCase(r15inquiryDetail.getReleaseDetailId())).forEach(subInquiryDetail -> {
                    subInquiryDetail.setPriceId(d10InquiryDetail.getPriceId());
                    subInquiryDetail.setDispositionType(d10InquiryDetail.getDispositionType());
                    subInquiryDetail.setRemark(d10InquiryDetail.getRemark());
                });
            });
        }
    }


    private Double calculateAmount(Double amount) {
        int amountInt = amount.intValue();
        if (Constant.MAX_AMOUNT > amount) {
            if (amountInt != amount)
                amount = Double.valueOf(amountInt + 1);
        }
        return amount;
    }

    private List<WasteReferencePriceModel> listDefaultDispositionType(String releaseEntId, List<EntReleaseDetailParam> entReleaseDetails) throws Exception {

        RPCSysEnterpriseBaseVo enterpriseBase = enterpriseBaseService.getEnterpriseByEntId(releaseEntId);

        List<WasteReferencePriceModel> priceModels = referenceService.listDefDisposition(entReleaseDetails, enterpriseBase.getCantonCode());
        return priceModels;
    }


    private WasteReferencePriceModel getPriceByDefaultDisposition(WasteReferencePriceModel defaultDisposition) {
        WasteReferencePriceParam priceParam = new WasteReferencePriceParam();
        priceParam.setId(defaultDisposition.getId());
        priceParam.setDispositionType(defaultDisposition.getDefaultDispositionType());
        priceParam.setAmount(defaultDisposition.getAmount());
        return priceDetailService.getPriceByDefaultDispositionType(priceParam);
    }

    private EntRelease saveEntReleaseInfo(User user, EntReleaseParam entReleaseParam) {
        entReleaseParam.setEntId(user.getEnterpriseId());
        if (StringUtils.isNotEmpty(entReleaseParam.getActivityId())) {
            entReleaseParam.setReleaseType(ReleaseType.DISPOSITION_CAPACITY_RELEASE.getCode());
        } else {
            entReleaseParam.setReleaseType(ReleaseType.DISPOSITION_PLAN_RELEASE.getCode());
        }

        entReleaseParam.setBusiStatus(ReleaseStatus.RELEASED.getCode());
        Date date = new Date();
        entReleaseParam.setCreateBy(user.getUserId());
        entReleaseParam.setCreateTime(date);
        entReleaseParam.setEditBy(user.getUserId());
        entReleaseParam.setEditTime(date);

        EntRelease entRelease = entReleaseParam;
        if (CodeTypeConstant.ENTERPRISE_TYPE_FACILITATOR.equalsIgnoreCase(user.getEntType())) {
            entRelease.setFacilitatorEntId(user.getEnterpriseId());
            entRelease.setEntId(entReleaseParam.getEntReleaseId());
        }
        entRelease.setId(Util.uuid32());
        this.baseMapper.insert(entRelease);
        return entRelease;
    }


    @Override
    public int informEnterpriseByReleaseId(User user, EntReleaseParam entReleaseParam) throws Exception {
        List<InformEnterpriseModel> enterpriseList = null;
        try {
            String ENV = PropertyUtil.getValue("ENV");

            RPCSysEnterpriseBaseVo enterpriseBase = enterpriseBaseService.getEnterpriseByEntId(user.getEnterpriseId());
            entReleaseParam.setCantonCode(enterpriseBase.getCantonCode());
            entReleaseParam.setCurrentEntId(user.getEnterpriseId());
            if (StringUtils.isEmpty(entReleaseParam.getActivityId())) {
                enterpriseList = listInformEnterprise(entReleaseParam);
                if (ENV.equalsIgnoreCase("PROD")) {
                    sendMsg(user, entReleaseParam, enterpriseList);
                }
            } else {
                informActivityEnterprise(user, entReleaseParam);
            }


        } catch (Exception e) {
            logger.error("发布企业危废后通知处置企业时出错", e);
            throw e;
        }
        return enterpriseList.size();

    }

    private void informActivityEnterprise(User user, EntReleaseParam entReleaseParam) throws Exception {
        try {
            ActivityQueryParam queryParam = new ActivityQueryParam();
            queryParam.setActivityId(entReleaseParam.getActivityId());
            WasteActivityVO wasteActivityVO = wasteActivityService.getWasteActivityVoById(queryParam);

            MsgEvent msgEvent = new MsgEvent(SmsAction.APPLY_ACTIVITY);
            msgEvent.setSendUser(user);
            Map<String, String> placeholderValueMap = new HashMap<String, String>();
            placeholderValueMap.put("activityName", wasteActivityVO.getActivityName());
            placeholderValueMap.put("entName", user.getEnterpriseName());
            msgEvent.setRelId(entReleaseParam.getReleaseId());
            msgEvent.setPlaceholderValueMap(placeholderValueMap);

            List<String> enterIdList = new ArrayList<String>();
            enterIdList.add(wasteActivityVO.getEntId());
            List<User> receiveUserList = userInfoService.listUserInfoByEnterIds(enterIdList);
            if (receiveUserList != null && receiveUserList.size() > 0) {
                msgEvent.setSendUser(user);
                msgEvent.setReceiveUserList(receiveUserList);
                sysMsgServcie.sendMessageSync(msgEvent);
            }
        } catch (Exception e) {
            logger.error("产废企业用户申请参加处置企业活动，通知处置企业用户", e);
            throw e;
        }

    }

    private void informSysAdmin(User user) throws Exception {
        try {
            List<User> receiveUserList = userInfoService.listSysAdminUser(null);
            if (receiveUserList != null && receiveUserList.size() > 0) {
                MsgEvent msgEvent = new MsgEvent(SmsAction.NEW_PUBLISH_SYSADMIN);
                msgEvent.setSendUser(user);
                Map<String, String> placeholderValueMap = new HashMap<String, String>();
                placeholderValueMap.put("entName", user.getEnterpriseName());
                placeholderValueMap.put("userName", user.getUserName());
                placeholderValueMap.put("phoneNo", user.getPhoneNo());
                msgEvent.setPlaceholderValueMap(placeholderValueMap);
                msgEvent.setReceiveUserList(receiveUserList);
                sysMsgServcie.sendMessageSync(msgEvent);
            }
        } catch (Exception e) {
            logger.error("产废企业用户发布危废时通知企业管理员", e);
            throw e;
        }
    }

    private void sendMsg(User user, EntReleaseParam entReleaseParam, List<InformEnterpriseModel> enterpriseList) {
        if (enterpriseList != null && enterpriseList.size() > 0) {
            List<String> enterIdList = enterpriseList.stream().map(InformEnterpriseModel::getEntId).distinct().collect(Collectors.toList());
            List<User> receiveUserList = userInfoService.listUserInfoByEnterIds(enterIdList);
            try {
                if (receiveUserList != null && receiveUserList.size() > 0) {
                    MsgEvent msgEvent = new MsgEvent(SmsAction.NEW_PUBLISH);
                    msgEvent.setSendUser(user);
                    msgEvent.setReceiveUserList(receiveUserList);
                    msgEvent.setRelId(entReleaseParam.getReleaseId());
                    sysMsgServcie.sendMessageSync(msgEvent);
                }
            } catch (Exception e) {
                logger.error("发送短信通知时异常", e);
            }

        }
    }

    private List<InformEnterpriseModel> listInformEnterprise(EntReleaseParam entReleaseParam) {
//        entReleaseParam.setRows(10);
        List<InformEnterpriseModel> enterpriseList = this.baseMapper.informEnterpriseByReleaseId(entReleaseParam);
        List<InformEnterpriseModel> disFacEntList = this.baseMapper.listDisFacilitatorEnt(entReleaseParam);
        enterpriseList.addAll(disFacEntList);
//        if (enterpriseList == null || (enterpriseList != null && enterpriseList.size() < 10)) {
//            if (enterpriseList == null) {
//                entReleaseParam.setRows(10);
//                entReleaseParam.setOutsideCantonCode(entReleaseParam.getCantonCode());
//            } else {
//                entReleaseParam.setRows(10 - enterpriseList.size());
//                entReleaseParam.setOutsideCantonCode(entReleaseParam.getCantonCode());
//            }
//            enterpriseList.addAll(this.baseMapper.informEnterpriseByReleaseId(entReleaseParam));
//        }
        return enterpriseList;
    }

    private List<EntReleaseDetail> saveEntReleaseDetail(User user, String releaseId, List<EntReleaseDetailParam> releaseDetail) {
        Date date = new Date();
        List<EntReleaseDetail> entReleaseDetails = new ArrayList<EntReleaseDetail>();
        Iterator<EntReleaseDetailParam> iter = releaseDetail.iterator();
        while (iter.hasNext()) {
            EntReleaseDetail entReleaseDetail = iter.next();
            entReleaseDetail.setId(Util.uuid32());
            entReleaseDetail.setReleaseId(releaseId);
            entReleaseDetail.setBusiStatus(ReleaseStatus.RELEASED.getCode());
            entReleaseDetail.setCreateBy(user.getUserId());
            entReleaseDetail.setCreateTime(date);
            entReleaseDetail.setEditBy(user.getUserId());
            entReleaseDetail.setEditTime(date);
            entReleaseDetails.add(entReleaseDetail);
        }
        entReleaseDetailService.insertBatch(entReleaseDetails);

        return entReleaseDetails;
    }


    @Override
    public List<EntReleaseModel> listWasteEntRelease(User user, EntReleaseParam releaseParams, PagingParameter pagingParameter) throws Exception {
        List<EntReleaseModel> releaseList = null;
        try {
            releaseParams.setCurrentEntId(user.getEnterpriseId());
            releaseParams.setCurrentUserId(user.getUserId());
            SysEnterpriseBase enterpriseBase = enterpriseBaseService.getEnterpriseInfoById(user.getEnterpriseId());
            releaseParams.setCurrentEntType(user.getEntType());
            prepareReleaseParams(releaseParams);
            Util.initPagingParameter(pagingParameter);
            releaseParams.setStartRowIndex(pagingParameter.getStart());
            releaseParams.setRows(pagingParameter.getLimit());
            List<String> cantonList = releaseParams.getCantonCodeList();
            if ((cantonList == null || cantonList.size() == 0) && StringUtils.isNotEmpty(releaseParams.getCantonCode())) {
                cantonList = new ArrayList<String>();
                cantonList.add(releaseParams.getCantonCode());
                releaseParams.setCantonCodeList(cantonList);
            }
            int queryCount = countEntRelease(releaseParams);
            if (queryCount > 0) {
                pagingParameter.setTotalRecord(queryCount);
                releaseList = this.baseMapper.listWasteEntRelease(releaseParams);
                prepareReleaseDetail(releaseList, releaseParams, user);
            }

        } catch (Exception e) {
            logger.error("获取资源池时异常", e);
            throw e;
        }
        return releaseList;
    }


    @Override
    public List<EntReleaseModel> listWasteEntReleaseByEnterId(User user, EntReleaseParam releaseParams, PagingParameter pagingParameter) throws Exception {
        List<EntReleaseModel> releaseList = null;
        try {
            releaseParams.setCurrentEntId(user.getEnterpriseId());
            releaseParams.setCurrentEntType(user.getEntType());
            Util.initPagingParameter(pagingParameter);
            releaseParams.setStartRowIndex(pagingParameter.getStart());
            releaseParams.setRows(pagingParameter.getLimit());
            int count = this.baseMapper.countWasteEntReleaseByEnterId(releaseParams);
            if (count > 0) {
                pagingParameter.setTotalRecord(count);
                releaseList = this.baseMapper.listWasteEntReleaseByEnterId(releaseParams);
                Iterator<EntReleaseModel> iter = releaseList.iterator();
                while (iter.hasNext()) {
                    EntReleaseModel releaseModel = iter.next();
                    releaseParams.setReleaseId(releaseModel.getReleaseId());
                    List<EntReleaseDetailModel> detailList = entReleaseDetailService.listWasteEntReleaseDetail(releaseParams);
                    releaseModel.setReleaseWasteDetails(detailList);
                    EntInquiryParam inquiryParam = new EntInquiryParam();
                    inquiryParam.setReleaseId(releaseModel.getReleaseId());
                    releaseModel.setTotalWasteCount(String.valueOf(detailList.size()));
                    releaseModel.setReleaseStatusValue(ReleaseStatus.getNameByCode(releaseModel.getReleaseStatus()));
                    releaseModel.setInquiryEntCount(entInquiryService.countWasteInquiry(inquiryParam));
                    DiscussTagParam tagInfo = discussTagService.countTagInfo(releaseModel.getReleaseId(), user.getEnterpriseId());
                    if (tagInfo != null) {
                        releaseModel.setTagInfo(tagInfo);
                    }
                }
            }

        } catch (Exception e) {
            logger.error("获取产废企业发布企业危废时异常", e);
            throw e;
        }
        return releaseList;
    }

    @Override
    public List<EntReleaseModel> listWasteEntReleaseByActivityId(EntReleaseParam releaseParams, PagingParameter pagingParameter) throws Exception {
        List<EntReleaseModel> releaseList = null;
        try {
            Util.initPagingParameter(pagingParameter);
            releaseParams.setStartRowIndex(pagingParameter.getStart());
            releaseParams.setRows(pagingParameter.getLimit());
            int count = this.baseMapper.countWasteEntReleaseByActivityId(releaseParams);
            if (count > 0) {
                pagingParameter.setTotalRecord(count);
                releaseList = this.baseMapper.listWasteEntReleaseByActivityId(releaseParams);
                Iterator<EntReleaseModel> iter = releaseList.iterator();
                while (iter.hasNext()) {
                    EntReleaseModel releaseModel = iter.next();
                    releaseParams.setReleaseId(releaseModel.getReleaseId());
                    List<EntReleaseDetailModel> detailList = entReleaseDetailService.listWasteEntReleaseDetail(releaseParams);
                    releaseModel.setReleaseWasteDetails(detailList);
                    releaseModel.setTotalWasteCount(String.valueOf(detailList.size()));
                    releaseModel.setReleaseStatusValue(ReleaseStatus.getNameByCode(releaseModel.getReleaseStatus()));
                    DiscussTagParam tagInfo = discussTagService.countTagInfo(releaseModel.getReleaseId(), releaseParams.getCurrentEntId());
                    if (tagInfo != null) {
                        releaseModel.setTagInfo(tagInfo);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("根据活动列出参加企业申请参加危废列表时异常", e);
            throw e;
        }
        return releaseList;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteWasteEntReleaseByReleaseId(User user, EntReleaseParam releaseParam) throws Exception {
        try {
            EntRelease entRelase = this.baseMapper.selectById(releaseParam.getReleaseId());
            if (entRelase != null && !entRelase.getBusiStatus().equals(ReleaseStatus.DONE.getCode())) {
                boolean deleteResult = deleteWasteEntRelease(user, releaseParam);
                if (deleteResult) {
                    updateInquiryStatus(user, releaseParam.getReleaseId());
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("更新产废企业发布危废时异常", e);
            throw e;
        }
        return false;
    }

    private boolean deleteWasteEntRelease(User user, EntReleaseParam releaseParam) {
        EntRelease entRelase = new EntRelease();
        entRelase.setId(releaseParam.getReleaseId());
        entRelase.setDeleteFlag(1);
        entRelase.setEditBy(user.getUserId());
        Date date = new Date();
        entRelase.setEditTime(date);
        return this.updateById(entRelase);
    }

    private void updateInquiryStatus(User user, String releaseId) throws Exception {
        List<EntInquiry> list = entInquiryService.listEntInquiryByReleaseId(releaseId);
        if (list != null && list.size() > 0) {
            Date date = new Date();
            for (EntInquiry entInquiry : list) {
                entInquiry.setBusiStatus(InquiryStatusEnum.CANCEL.getCode());
                entInquiry.setEditBy(user.getUserId());
                entInquiry.setEditTime(date);
                entInquiryService.updateById(entInquiry);
            }
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean refusedWasteEntApply(EntReleaseParam releaseParam, User user) throws Exception {
        try {
            EntRelease entRelase = new EntRelease();
            Date date = new Date();
            entRelase.setId(releaseParam.getReleaseId());
            entRelase.setBusiStatus(ReleaseStatus.REFUSED.getCode());
            entRelase.setEditBy(user.getUserId());
            entRelase.setEditTime(date);
            return this.updateById(entRelase);
        } catch (Exception e) {
            logger.error("处置企业拒绝产废企业申请参加活动时异常", e);
            throw e;
        }
    }

    private void prepareReleaseParams(EntReleaseParam releaseParams) {

        OperationLicence operationLicence = licenceService.getValidLicIdByEnterpriseId(releaseParams.getCurrentEntId());
        if (operationLicence != null) {
            releaseParams.setLicenceId(operationLicence.getId());
        }
        List<String> amountIntervalStrList = releaseParams.getAmountIntervalStr();
        if (amountIntervalStrList != null && amountIntervalStrList.size() > 0) {
            List<AmountInterval> amountIntervalList = new ArrayList<>();
            for (String amountInterval : amountIntervalStrList) {
                if (amountInterval.split(",").length == 2) {
                    String[] amountStr = amountInterval.split(",");
                    AmountInterval amountInterval1 = new AmountInterval();
                    amountInterval1.setStartAmount(Double.valueOf(amountStr[0]));
                    amountInterval1.setEndAmount(Double.valueOf(amountStr[1]));
                    amountIntervalList.add(amountInterval1);
                } else {
                    releaseParams.setAmountInterval(Double.valueOf(amountInterval));
                }
            }
            releaseParams.setAmountIntervalList(amountIntervalList);
        }
        if (releaseParams.getCantonCodeList() != null && releaseParams.getCantonCodeList().size() > 0) {
            List<String> cantonList = releaseParams.getCantonCodeList();
            List<String> changedList = new ArrayList<String>();
            for (String cantonCode : cantonList) {
                String canton = Util.calculateCantonCode(cantonCode);
                if (StringUtils.isNotEmpty(canton)) {
                    changedList.add(canton);
                }
            }
            releaseParams.setCantonCodeList(changedList);
        }


    }

    @Override
    public int countEntRelease(EntReleaseParam releaseParams) {
        return this.baseMapper.countEntRelease(releaseParams);
    }

    @Override
    public EntReleaseModel getEntReleaseById(EntReleaseParam entReleaseParam) throws Exception {
        EntReleaseModel entRelease = this.baseMapper.getEntReleaseById(entReleaseParam);
        return entRelease;
    }

    @Override
    public List<ReleaseStatusModel> countActivityStatusByActivityId(String activityId) throws Exception {
        List<ReleaseStatusModel> releaseStatusModels = null;
        try {
            releaseStatusModels = this.baseMapper.countActivityStatusByActivityId(activityId);
        } catch (Exception e) {
            logger.error("统计活动状态时异常", e);
            throw e;

        }
        return releaseStatusModels;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean endWasteEntReleaseByReleaseId(User user, EntReleaseParam releaseParam) throws Exception {
        try {
            if (StringUtils.isEmpty(releaseParam.getActivityId()) && StringUtils.isNotEmpty(releaseParam.getReleaseId())) {
                EntRelease entRelease = new EntRelease();
                entRelease.setId(releaseParam.getReleaseId());
                entRelease.setBusiStatus(ReleaseStatus.END.getCode());
                entRelease.setEditBy(user.getUserId());
                Date date = new Date();
                entRelease.setEditTime(date);
                return this.updateById(entRelease);
            }
        } catch (Exception e) {
            logger.error("结束委托时异常", e);
            throw e;
        }
        return false;
    }

    private void prepareReleaseDetail(List<EntReleaseModel> releaseList, EntReleaseParam releaseParams, User user) throws Exception {
        Iterator<EntReleaseModel> iter = releaseList.iterator();
        while (iter.hasNext()) {
            EntReleaseModel releaseModel = iter.next();
            releaseParams.setReleaseId(releaseModel.getReleaseId());
            releaseModel.setReleaseStatusValue(ReleaseStatus.getNameByCode(releaseModel.getReleaseStatus()));

            if (StringUtils.isNotEmpty(releaseModel.getDisposalWasteCount()) || user == null) {
                StringBuilder totalAmount = entReleaseDetailService.countWasteAmount(entReleaseDetailService.countDisposabledWasteAmountReleaseDetail(releaseParams));
                releaseModel.setDisposalWasteAmount(totalAmount.toString());
            }
            if (user != null) {
                DiscussTagParam tagInfo = discussTagService.countTagInfo(releaseModel.getReleaseId(), user.getEnterpriseId());
                if (tagInfo != null) {
                    releaseModel.setTagInfo(tagInfo);
                }
                if (StringUtils.isNotEmpty(releaseModel.getReleaseEntId())) {
                    SysCanton sysCanton = sysCantonService.queryCantonNameByEnterpriseId(releaseModel.getReleaseEntId());
                    if (sysCanton != null) {
                        releaseModel.setCantonCodeName(sysCanton.getCantonName());
                    }
                }
            }

            List<EntReleaseDetailModel> detailList = entReleaseDetailService.listWasteEntReleaseDetail(releaseParams);
            if (user == null) {
                releaseModel.setDisposalWasteCount(String.valueOf(detailList.size()));
            }
            releaseModel.setReleaseWasteDetails(detailList);
        }
    }


    @Override
    public EntReleaseModel generateReleasePoster(String userId) throws Exception {
        EntReleaseModel entReleaseModel;
        try {
            entReleaseModel = this.baseMapper.getReleasePosterInfo(userId);
        } catch (Exception e) {
            logger.error("生成发布海报时异常", e);
            throw e;
        }
        return entReleaseModel;
    }

    @Override
    public List<EntReleaseModel> listWasteEntRelease4FacilitatorEnt(EntReleaseParam releaseParams, PagingParameter pagingParameter) throws Exception {
        List<EntReleaseModel> releaseList = null;
        try {
            SysEnterpriseBase enterpriseBase = enterpriseBaseService.getEnterpriseInfoById(releaseParams.getCurrentEntId());
            if (enterpriseBase != null && StringUtils.isNotEmpty(enterpriseBase.getResponsibleArea()) && enterpriseBase.getResponsibleArea().length() > 2) {
                String cantonCode = Util.calculateCantonCode(enterpriseBase.getResponsibleArea());
                releaseParams.setCantonCode(cantonCode);
            }

            prepareReleaseParams(releaseParams);
            Util.initPagingParameter(pagingParameter);
            releaseParams.setStartRowIndex(pagingParameter.getStart());
            releaseParams.setRows(pagingParameter.getLimit());

            int queryCount = this.baseMapper.countWasteEntRelease4FacilitatorEnt(releaseParams);
            if (queryCount > 0) {
                pagingParameter.setTotalRecord(queryCount);
                releaseList = this.baseMapper.listWasteEntRelease4FacilitatorEnt(releaseParams);
                prepareReleaseDetail4FacilitatorEnt(releaseList, releaseParams);
            }
        } catch (Exception e) {
            logger.error("获取资源池时异常", e);
            throw e;
        }
        return releaseList;
    }

    @Override
    public void saveEntWaste(User user, EntReleaseParam entReleaseParams) throws Exception {
        try {
            List<EntReleaseDetailParam> releaseDetails = entReleaseParams.getReleaseDetail();
            for (EntReleaseDetailParam entReleaseDetail : releaseDetails) {
                if (StringUtils.isEmpty(entReleaseDetail.getEntWasteId())) {
                    EntWasteParams entWasteParams = new EntWasteParams();
                    entWasteParams.setEntId(user.getEnterpriseId());
                    entWasteParams.setWasteName(entReleaseDetail.getWasteName());
                    entWasteParams.setWasteCode(entReleaseDetail.getWasteCode());
                    entWasteParams.setUnitCode(entReleaseDetail.getUnitCode());
                    entWasteParams.setWasteId(entReleaseDetail.getWasteId());
                    entWasteService.saveEntWaste(user, entWasteParams);
                    entReleaseDetail.setEntWasteId(entWasteParams.getEntWasteId());
                }
            }
        } catch (Exception e) {
            logger.error("发布保存危废时异常", e);
            throw e;
        }

    }

    public void saveEntWaste4UpStream(EntReleaseParam entReleaseParams) throws Exception {
        try {
            String ticketId = generateTempTicket();
            String entId = saveProductionEnt(ticketId, entReleaseParams);
            User user = LoginStatusUtils.getUserByTicketId(ticketId);
            List<EntReleaseDetailParam> releaseDetails = entReleaseParams.getReleaseDetail();
            for (EntReleaseDetailParam entReleaseDetail : releaseDetails) {
                if (StringUtils.isEmpty(entReleaseDetail.getEntWasteId())) {
                    EntWasteParams entWasteParams = new EntWasteParams();
                    entWasteParams.setEntId(entId);
                    entWasteParams.setWasteName(entReleaseDetail.getWasteName());
                    entWasteParams.setWasteCode(entReleaseDetail.getWasteCode());
                    entWasteParams.setUnitCode(entReleaseDetail.getUnitCode());
                    entWasteParams.setWasteId(entReleaseDetail.getWasteId());
                    entWasteService.saveEntWaste(user, entWasteParams);
                    entReleaseDetail.setEntWasteId(entWasteParams.getEntWasteId());
                }
            }
        } catch (Exception e) {
            logger.error("发布保存危废时异常", e);
            throw e;
        }
    }

    private String saveProductionEnt(String ticketId, EntReleaseParam entReleaseParams) throws Exception {
        String entId = null;
        SysEnterpriseBase enterpriseBase = new SysEnterpriseBase();
        enterpriseBase.setEntType(Constant.ENTERPRISE_TYPE_PRODCITION);
        enterpriseBase.setEntName(entReleaseParams.getReleaseEntName());
        enterpriseBase = enterpriseBaseService.getEnterpriseInfo(enterpriseBase);
        if (enterpriseBase == null) {
            RPCSysEnterpriseBase sysEnterpriseBase = new RPCSysEnterpriseBase();
            sysEnterpriseBase.setEntName(enterpriseBase.getEntName());
            String shortName = enterpriseService.getShortName(enterpriseBase.getEntName());
            sysEnterpriseBase.setShortName(shortName);
            entId = facilitatorCustomerService.saveProductionEnt(ticketId, Constant.ENTERPRISE_TYPE_PRODCITION, sysEnterpriseBase);
        } else {
            entId = enterpriseBase.getEntId();
        }
        return entId;
    }

    String generateTempTicket() throws Exception {
        String ticketId = userLoginService.userLogin("100000000000001", RSAEncryptUtils.encrypt("test123"));
        return ticketId;
    }

    private void prepareReleaseDetail4FacilitatorEnt(List<EntReleaseModel> releaseList, EntReleaseParam releaseParams) throws Exception {
        Iterator<EntReleaseModel> iter = releaseList.iterator();
        while (iter.hasNext()) {
            EntReleaseModel releaseModel = iter.next();
            releaseParams.setReleaseId(releaseModel.getReleaseId());
            releaseModel.setReleaseStatusValue(ReleaseStatus.getNameByCode(releaseModel.getReleaseStatus()));
            List<EntReleaseDetailModel> countAmount = entReleaseDetailService.countDisposabledWasteAmountReleaseDetail(releaseParams);
            StringBuilder toltalAmount = entReleaseDetailService.countWasteAmount(countAmount);
            releaseModel.setDisposalWasteAmount(toltalAmount.toString());

            DiscussTagParam tagInfo = discussTagService.countTagInfo(releaseModel.getReleaseId(), releaseParams.getCurrentEntId());
            if (tagInfo != null) {
                releaseModel.setTagInfo(tagInfo);
            }

            List<EntReleaseDetailModel> detailList = entReleaseDetailService.listWasteEntReleaseDetail(releaseParams);
            releaseModel.setDisposalWasteCount(String.valueOf(detailList.size()));
            releaseModel.setReleaseWasteDetails(detailList);
        }
    }


    @Override
    public void sendHelpMsg(User user) throws Exception {
        try {
            Map<String, String> placeholderValueMap = new HashMap<String, String>();
            SendMsgParameter parameter = new SendMsgParameter();
            placeholderValueMap.put("entName", user.getEnterpriseName());
            placeholderValueMap.put("userName", user.getUserName());
            placeholderValueMap.put("phoneNo", user.getPhoneNo());
            parameter.setSmsParam(placeholderValueMap);
            parameter.setSmsTemplateCode(SmsTemplateCode.SMS_130914371);
            parameter.setPhoneNum("18662428826");//周正伟电话
            smsService.sendMsg(parameter);
        } catch (Exception e) {
            logger.error("发送请求手机时异常", e);
        }

    }

}
