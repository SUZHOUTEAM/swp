package com.mlsc.yifeiwang.entinquiry.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.enterprise.common.EnterpriseType;
import com.mlsc.yifeiwang.entinquiry.common.InquiryPriorityEnum;
import com.mlsc.yifeiwang.entinquiry.common.InquiryStatusEnum;
import com.mlsc.yifeiwang.entinquiry.common.InquiryType;
import com.mlsc.yifeiwang.entorder.common.OrderContractStatus;
import com.mlsc.yifeiwang.entorder.common.OrderStatusEnum;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.user.model.UserInfo;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Constant;
import com.mlsc.waste.utils.UserStatus;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.discusstag.model.DiscussTagParam;
import com.mlsc.yifeiwang.discusstag.service.IDiscussTagService;
import com.mlsc.yifeiwang.enterprise.entity.SysEnterpriseBase;
import com.mlsc.yifeiwang.enterprise.service.ISysEnterpriseBaseService;
import com.mlsc.yifeiwang.entinquiry.entity.EntInquiry;
import com.mlsc.yifeiwang.entinquiry.entity.EntInquiryDetail;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryDetailParam;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryModel;
import com.mlsc.yifeiwang.entinquiry.model.EntInquiryParam;
import com.mlsc.yifeiwang.entinquiry.mapper.EntInquiryMapper;
import com.mlsc.yifeiwang.entinquiry.service.IEntInquiryDetailService;
import com.mlsc.yifeiwang.entinquiry.service.IEntInquiryService;
import com.mlsc.yifeiwang.entorder.entity.EntOrder;
import com.mlsc.yifeiwang.entorder.entity.EntOrderDetail;
import com.mlsc.yifeiwang.entorder.service.IEntOrderDetailService;
import com.mlsc.yifeiwang.entorder.service.IEntOrdersService;
import com.mlsc.yifeiwang.wasterealase.common.ReleaseStatus;
import com.mlsc.yifeiwang.wasterealase.entity.EntRelease;
import com.mlsc.yifeiwang.wasterealase.model.EntReleaseDetailModel;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseDetailService;
import com.mlsc.yifeiwang.wasterealase.service.IEntReleaseService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Caoyy
 * @since 2017-09-12
 */
@Service
public class EntInquiryServiceImpl extends ServiceImpl<EntInquiryMapper, EntInquiry> implements IEntInquiryService {
    private final static Logger logger = LoggerFactory.getLogger(EntInquiryServiceImpl.class);
    @Autowired
    private IEntInquiryDetailService inquiryDetailService;

    @Autowired
    private IEntOrderDetailService entOrderDetailService;

    @Autowired
    private SysMsgServcie sysMsgService;

    @Autowired
    private IEntReleaseService entReleaseService;

    @Autowired
    private IDiscussTagService discussTagService;

    @Autowired
    private IEntOrdersService entOrdersService;

    @Autowired
    private ISysEnterpriseBaseService sysEnterpriseBaseService;

    @Autowired
    private IEntReleaseDetailService entReleaseDetailService;

    @Autowired
    private IUserInfoService userInfoService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean saveEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception {
        try {
            EntInquiry entInquiry = new EntInquiry();
            boolean insertResult = insertEntInquiry(user, inquiryParam, entInquiry);
            if (insertResult) {
                saveEntDetailInquiry(inquiryParam, entInquiry, user);
                informReleaseEntContacts(entInquiry, user, inquiryParam);
                updateReleaseStatus(inquiryParam, user);
            }

        } catch (Exception e) {
            logger.error("询价时异常", e);
            throw e;
        }
        return true;
    }

    private void informReleaseEntContacts(EntInquiry entInquiry, User user, EntInquiryParam inquiryParam) {
        MsgEvent msgEvent = new MsgEvent(SmsAction.ENT_NEW_INQUIRY);
        msgEvent.setRelId(entInquiry.getId());
        msgEvent.setSendUser(user);
        User queryParam = new User();

        if (StringUtils.isNotEmpty(inquiryParam.getFacilitatorEntId())) {
            queryParam.setEnterpriseId(inquiryParam.getFacilitatorEntId());
        } else {
            queryParam.setEnterpriseId(inquiryParam.getReleaseEntId());
        }

        if (EnterpriseType.FACILITATOR.getCode().equalsIgnoreCase(user.getEntType())) {
            queryParam.setUserStatus(UserStatus.PASS.getStatusCode());

            Map<String, String> placeholderMap = new HashMap<>();
            placeholderMap.put("userName", user.getUserName().substring(0, 1).concat("工"));
            placeholderMap.put("phoneNo", user.getPhoneNo());
            msgEvent.setPlaceholderValueMap(placeholderMap);
            msgEvent.setReceiverUserQueryParam(queryParam);
            sysMsgService.sendMessageAsync(msgEvent);
        } else {
            queryParam.setUserStatus(UserStatus.PASS.getStatusCode());
            msgEvent.setReceiverUserQueryParam(queryParam);
            msgEvent.setReceiverUserQueryParam(queryParam);
            sysMsgService.sendMessageAsync(msgEvent);
        }

    }

    private void updateReleaseStatus(EntInquiryParam inquiryParam, User user) {
        if (StringUtils.isNotBlank(inquiryParam.getReleaseId()) && StringUtils.isNotBlank(inquiryParam.getActivityId())) {
            String releaseId = inquiryParam.getReleaseId();
            EntRelease entRelease = entReleaseService.selectById(releaseId);
            entRelease.setBusiStatus(ReleaseStatus.SUBMIT.getCode());
            Date date = new Date();
            entRelease.setEditTime(date);
            entRelease.setEditBy(user.getUserId());
            entReleaseService.updateAllColumnById(entRelease);
        }
    }

    @Override
    public boolean insertEntInquiry(User user, EntInquiryParam inquiryParam, EntInquiry entInquiry) {
        EntRelease entRelease = entReleaseService.selectById(inquiryParam.getReleaseId());
        entInquiry.setEntId(user.getEnterpriseId());
        entInquiry.setReleaseEntId(inquiryParam.getReleaseEntId());
        entInquiry.setReleaseId(inquiryParam.getReleaseId());
        entInquiry.setQuotedType(inquiryParam.getQuotedType());
        entInquiry.setTotalAmount(inquiryParam.getTotalAmount());
        entInquiry.setTotalPrice(inquiryParam.getTotalPrice());
        entInquiry.setActivityId(inquiryParam.getActivityId());
        entInquiry.setInquiryType(inquiryParam.getInquiryType());
        entInquiry.setRemark(inquiryParam.getRemark());
        entInquiry.setFacilitatorEntId(entRelease.getFacilitatorEntId());
        entInquiry.setDisEntId(inquiryParam.getDisEntId());
        Date date = new Date();
        entInquiry.setBusiStatus(InquiryStatusEnum.SUBMIT.getCode());
        entInquiry.setCreateBy(user.getUserId());
        entInquiry.setCreateTime(date);
        entInquiry.setEditBy(user.getUserId());
        entInquiry.setEditTime(date);
        return this.insert(entInquiry);

    }

    @Override
    public void saveEntDetailInquiry(EntInquiryParam inquiryParam, EntInquiry entInquiry, User user) {
        List<EntInquiryDetailParam> inquiryDetail = inquiryParam.getInquiryDetail();
        if (inquiryDetail != null && inquiryDetail.size() > 0) {
            Date date = new Date();
            List<EntInquiryDetail> saveList = new ArrayList<>(inquiryDetail.size());
            for (EntInquiryDetailParam item : inquiryDetail) {
                EntInquiryDetail entInquiryDetail = new EntInquiryDetail();
                entInquiryDetail.setInquiryId(entInquiry.getId());
                entInquiryDetail.setReleaseDetailId(item.getReleaseDetailId());
                entInquiryDetail.setPrice(item.getPrice());
                entInquiryDetail.setPriceId(item.getPriceId());
                entInquiryDetail.setRemark(item.getRemark());
                entInquiryDetail.setTotalPrice(item.getTotalPrice());
                entInquiryDetail.setBusiStatus(InquiryStatusEnum.SUBMIT.getCode());
                entInquiryDetail.setDispositionType(item.getDispositionType());
                entInquiryDetail.setCreateBy(user.getUserId());
                entInquiryDetail.setCreateTime(date);
                entInquiryDetail.setEditBy(user.getUserId());
                entInquiryDetail.setEditTime(date);
                saveList.add(entInquiryDetail);
            }
            inquiryDetailService.insertBatch(saveList);
        }
    }

    @Override
    public List<EntInquiryModel> listWasteInquiry(User user, PagingParameter pagingParameter, EntInquiryParam inquiryParam) throws Exception {
        List<EntInquiryModel> inquiryList = null;
        try {
            Util.initPagingParameter(pagingParameter);
            inquiryParam.setStartRowIndex(pagingParameter.getStart());
            inquiryParam.setRows(pagingParameter.getLimit());
            if (user != null) { //处置企业-我的询价
                inquiryParam.setInquiryEntId(user.getEnterpriseId());
            }
            int count = this.baseMapper.countWasteInquiry(inquiryParam);
            if (count > 0) {
                inquiryList = this.baseMapper.listWasteInquiry(inquiryParam);
                pagingParameter.setTotalRecord(count);
                Iterator<EntInquiryModel> iter = inquiryList.iterator();
                while (iter.hasNext()) {
                    EntInquiryModel inquiryModel = iter.next();
                    inquiryModel.setBusiStatus(InquiryStatusEnum.getNameByCode(inquiryModel.getBusiStatus()));
                    inquiryModel.setPriority(InquiryPriorityEnum.getNameByCode(inquiryModel.getPriority()));
                    listWasteInquiryDetail(inquiryModel);
                    if (user != null) {
                        getTagInfo(inquiryModel, user.getEnterpriseId());
                        if (user.getEntType().equals(CodeTypeConstant.ENTERPRISE_TYPE_FACILITATOR)) {
                            listReleaseEntContactsForFacilitator(inquiryModel);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取处置企业询价时异常", e);
            throw e;
        }
        return inquiryList;
    }

    private void listReleaseEntContactsForFacilitator(EntInquiryModel inquiryModel) {
        if (StringUtils.isNotEmpty(inquiryModel.getReleaseUserId())) {
            UserInfo userInfo = userInfoService.getUserInfoById(inquiryModel.getReleaseUserId());
            if (userInfo != null && StringUtils.isNotEmpty(userInfo.getPhoneNum())) {
                inquiryModel.setReleaseEntContacts(userInfo.getPhoneNum().trim());
            }
        }
    }

    private void listWasteInquiryDetail(EntInquiryModel inquiryModel) throws Exception {
        try {
            EntInquiryDetailParam detailParm = new EntInquiryDetailParam();
            detailParm.setInquiryId(inquiryModel.getInquiryId());
            List<EntInquiryDetailModel> detailList = inquiryDetailService.listWasteInquiryDetail(detailParm);
            if (detailList != null && detailList.size() > 0) {
                inquiryModel.setInquiryDetail(detailList);
                inquiryModel.setInquiryWasteCount(detailList.size());
            }
        } catch (Exception e) {
            logger.error("获取询价列表时异常", e);
            throw e;
        }

    }

    private void getTagInfo(EntInquiryModel inquiryModel, String enterpriseId) throws Exception {
        try {
            DiscussTagParam tagInfo = discussTagService.countTagInfo(inquiryModel.getReleaseId(), enterpriseId);
            if (tagInfo != null) {
                inquiryModel.setTagInfo(tagInfo);
            }
        } catch (Exception e) {
            logger.error("获取标签信息时异常", e);
            throw e;
        }

    }

    @Override
    public EntInquiryModel countTotalInquiryInfo(User user, EntInquiryParam inquiryParam) throws Exception {
        EntInquiryModel totalInquiryModel = new EntInquiryModel();
        if (inquiryParam != null) {
            if (user != null) { //处置企业-我的询价
                inquiryParam.setInquiryEntId(user.getEnterpriseId());
            }
            List<EntReleaseDetailModel> detailModels = this.baseMapper.listInquiryAmount(inquiryParam);
            EntInquiryDetailModel totalPrice = this.baseMapper.inquiryTotalPrice(inquiryParam);
            StringBuilder totalAmount = entReleaseDetailService.countWasteAmount(detailModels);
            if (detailModels != null && detailModels.size() > 0) {
                totalInquiryModel.setTotalAmount(totalAmount.toString());
                if (totalPrice != null) {
                    totalInquiryModel.setTotalPrice(totalPrice.getWasteTotalPrice());
                }

            }
        }

        return totalInquiryModel;
    }

    @Override
    public List<EntInquiry> listEntInquiryByReleaseId(String releaseId) throws Exception {
        return this.baseMapper.listEntInquiryByReleaseId(releaseId);
    }

    @Override
    public int countWasteInquiry(EntInquiryParam inquiryParam) throws Exception {
        int count;
        try {
            count = this.baseMapper.countWasteInquiry(inquiryParam);
        } catch (Exception e) {
            logger.error("处置企业询价个数时异常", e);
            throw e;
        }
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean rejectEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception {
        try {
            if (inquiryParam != null && StringUtils.isNotBlank(inquiryParam.getInquiryId())) {
                updateEntInquiry(user, inquiryParam, InquiryStatusEnum.REFUSED.getCode());
                updateEntInquiryDetail(user, inquiryParam, InquiryStatusEnum.REFUSED.getCode());
                sendRejectMsg(user, inquiryParam);
                return true;
            }
        } catch (Exception e) {
            logger.error("产废企业拒绝询价时异常", e);
            throw e;
        }
        return false;
    }

    private void sendRejectMsg(User user, EntInquiryParam inquiryParam) {
        if (StringUtils.isNotEmpty(inquiryParam.getInquiryEntId())) {
            MsgEvent msgEvent = new MsgEvent(SmsAction.ENT_REFUSE_INQUIRY);
            msgEvent.setRelId(inquiryParam.getInquiryId());
            msgEvent.setSendUser(user);
            User queryParam = new User();
            queryParam.setEnterpriseId(inquiryParam.getInquiryEntId());
            queryParam.setUserStatus(UserStatus.PASS.getStatusCode());
            msgEvent.setReceiverUserQueryParam(queryParam);
            Map<String, String> placeholderMap = new HashMap<>();
            placeholderMap.put("entName", user.getEnterpriseName());
            msgEvent.setPlaceholderValueMap(placeholderMap);
            sysMsgService.sendMessageAsync(msgEvent);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean confirmEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception {
        try {
            if (inquiryParam != null && StringUtils.isNotBlank(inquiryParam.getInquiryId())) {
                updateEntInquiry(user, inquiryParam, InquiryStatusEnum.ACCEPT.getCode());
                updateEntInquiryDetail(user, inquiryParam, InquiryStatusEnum.ACCEPT.getCode());
                String orderId = insertOrder(user, inquiryParam);
                if (StringUtils.isNotBlank(orderId)) {
                    insertOrderDetail(user, inquiryParam, orderId);
                    updateRelease(user, inquiryParam);
                    sendConfirmMsg(user, inquiryParam);
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("产废企业确认询价时异常", e);
            throw e;
        }
        return false;
    }

    private void sendConfirmMsg(User user, EntInquiryParam inquiryParam) {
        MsgEvent msgEvent = new MsgEvent(SmsAction.ENT_CONFIRM_INQUIRY);
        msgEvent.setRelId(inquiryParam.getInquiryId());
        msgEvent.setSendUser(user);
        User queryParam = new User();
        if (StringUtils.isNotEmpty(inquiryParam.getInquiryEntId())) {
            queryParam.setEnterpriseId(inquiryParam.getInquiryEntId());
            queryParam.setUserStatus(UserStatus.PASS.getStatusCode());
            msgEvent.setReceiverUserQueryParam(queryParam);
            Map<String, String> placeholderMap = new HashMap<>();
            placeholderMap.put("entName", user.getEnterpriseName());
            msgEvent.setPlaceholderValueMap(placeholderMap);
            sysMsgService.sendMessageAsync(msgEvent);
        }
    }


    private void updateEntInquiry(User user, EntInquiryParam inquiryParam, String busiStatus) throws Exception {
        EntInquiry entInquiry = new EntInquiry();
        entInquiry.setId(inquiryParam.getInquiryId());
        entInquiry.setEditBy(user.getUserId());
        entInquiry.setBusiStatus(busiStatus);
        Date date = new Date();
        entInquiry.setEditTime(date);
        this.updateById(entInquiry);
    }

    private void updateEntInquiryDetail(User user, EntInquiryParam inquiryParam, String busiStatus) throws Exception {
        EntInquiryDetail detailParm = new EntInquiryDetail();
        Date date = new Date();
        detailParm.setBusiStatus(busiStatus);
        detailParm.setInquiryId(inquiryParam.getInquiryId());
        detailParm.setEditBy(user.getUserId());
        detailParm.setEditTime(date);
        inquiryDetailService.updateWasteInquiryDetail(detailParm);

    }

    private String insertOrder(User user, EntInquiryParam inquiryParam) throws Exception {
        EntInquiry entInquiry = this.selectById(inquiryParam.getInquiryId());
        EntOrder entOrder = new EntOrder();
        Date date = new Date();
        BeanUtils.copyProperties(entOrder, entInquiry);
        entOrder.setId("");
        entOrder.setInquiryEntId(entInquiry.getEntId());
        entOrder.setInquiryId(inquiryParam.getInquiryId());
        entOrder.setOrderCode(Util.getOrdersCode());
        entOrder.setConfirmedTime(date);
        entOrder.setDisEntId(inquiryParam.getDisEntId());
        entOrder.setBusiStatus(OrderStatusEnum.ONGOING.getCode());
        entOrder.setContractStatus(OrderContractStatus.WAITINGUPLOAD.getCode());
        entOrder.setCreateBy(user.getUserId());
        entOrder.setCreateTime(date);
        entOrder.setEditBy(user.getUserId());
        entOrder.setEditTime(date);
        entOrder.setActivityId(inquiryParam.getActivityId());
        entOrdersService.insert(entOrder);
        return entOrder.getId();
    }

    private void insertOrderDetail(User user, EntInquiryParam inquiryParam, String orderId) throws Exception {
        Date date = new Date();
        EntInquiryDetailParam detailParm = new EntInquiryDetailParam();
        detailParm.setInquiryId(inquiryParam.getInquiryId());

        List<EntInquiryDetail> entInquiryDetailList = inquiryDetailService.listWasteInquiryDetailByInquiryId(detailParm);
        List<EntOrderDetail> entOrderDetailList = new ArrayList<EntOrderDetail>();
        for (EntInquiryDetail entInquiryDetail : entInquiryDetailList) {
            EntOrderDetail orderDetail = new EntOrderDetail();
            BeanUtils.copyProperties(orderDetail, entInquiryDetail);
            orderDetail.setId("");
            orderDetail.setOrderId(orderId);
            orderDetail.setInquiryDetailId(entInquiryDetail.getId());
            orderDetail.setBusiStatus(OrderStatusEnum.ONGOING.getCode());
            orderDetail.setCreateBy(user.getUserId());
            orderDetail.setCreateTime(date);
            orderDetail.setEditBy(user.getUserId());
            orderDetail.setEditTime(date);
            entOrderDetailList.add(orderDetail);
        }

        if (entOrderDetailList.size() > 0) {
            entOrderDetailService.insertBatch(entOrderDetailList);
        }
    }

    private void updateRelease(User user, EntInquiryParam inquiryParam) {
        EntRelease entRelease = entReleaseService.selectById(inquiryParam.getReleaseId());
        entRelease.setBusiStatus(ReleaseStatus.DONE.getCode());
        Date date = new Date();
        entRelease.setEditTime(date);
        entRelease.setEditBy(user.getUserId());
        entReleaseService.updateAllColumnById(entRelease);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception {
        try {
            if (inquiryParam != null && StringUtils.isNotBlank(inquiryParam.getInquiryId())) {
                EntInquiry entInquiry = this.baseMapper.selectById(inquiryParam.getInquiryId());
                if (!entInquiry.getBusiStatus().equalsIgnoreCase(InquiryStatusEnum.ACCEPT.getCode())) {
                    Date date = new Date();
                    entInquiry.setDeleteFlag(Constant.DELETED);
                    entInquiry.setEditTime(date);
                    entInquiry.setEditBy(user.getUserId());
                    this.baseMapper.updateById(entInquiry);
                }
                if (StringUtils.isNotEmpty(entInquiry.getActivityId())) { //如果是通过我的活动出来询价，删除我的询价后可再次询价
                    updateActivityInquiryStatus(user, entInquiry);
                }
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            logger.error("删除我的询价", e);
            throw e;
        }

    }

    private void updateActivityInquiryStatus(User user, EntInquiry entInquiry) {
        EntityWrapper<EntOrder> ew = new EntityWrapper<>();
        ew.eq("entId", entInquiry.getEntId());
        ew.eq("releaseId", entInquiry.getReleaseId());
        int count = entOrdersService.selectCount(ew);
        if (count == 0) {
            Date date = new Date();
            String releaseId = entInquiry.getReleaseId();
            EntRelease entRelease = entReleaseService.selectById(releaseId);
            entRelease.setBusiStatus(ReleaseStatus.RELEASED.getCode());
            entInquiry.setEditTime(date);
            entInquiry.setEditBy(user.getUserId());
            entReleaseService.updateById(entRelease);
        }
    }

    @Override
    public int countUntreatedInquiry(User user) throws Exception {
        int count = 0;
        try {
            String entId = user.getEnterpriseId();
            if (StringUtils.isNotEmpty(entId)) {
                count = this.baseMapper.countUntreatedInquiry(entId);
            }

        } catch (Exception e) {
            logger.error("统计未处理询价时异常", e);
            throw e;
        }
        return count;
    }

    @Override
    public boolean updateResponsibleEntInquiry(User user, EntInquiryParam inquiryParam) throws Exception {
        try {
            if (inquiryParam != null && StringUtils.isNotBlank(inquiryParam.getInquiryId())) {
                EntInquiry entInquiry = this.baseMapper.selectById(inquiryParam.getInquiryId());
                if (entInquiry != null) {
                    Date date = new Date();
                    entInquiry.setResponsiblePerson(inquiryParam.getResponsibleUserId());
                    entInquiry.setEditTime(date);
                    entInquiry.setEditBy(user.getUserId());

                    return this.updateAllColumnById(entInquiry);
                }
            }
        } catch (Exception e) {
            logger.error("处置企业管理员更新负责人", e);
            throw e;
        }
        return false;
    }

    @Override
    public boolean updateEntInquiryPriority(User user, EntInquiryParam inquiryParam) throws Exception {
        if (inquiryParam == null || StringUtils.isBlank(inquiryParam.getInquiryId())) {
            return false;
        }
        try {
            EntInquiry entInquiry = new EntInquiry();
            entInquiry.setId(inquiryParam.getInquiryId());
            Date date = new Date();
            entInquiry.setPriority(inquiryParam.getPriority());
            entInquiry.setEditTime(date);
            entInquiry.setEditBy(user.getUserId());
            return this.updateById(entInquiry);
        } catch (Exception e) {
            logger.error("处置企业管理员更新询价权限时异常", e);
            throw e;
        }


    }

    @Override
    public EntInquiryModel getReferencePrice(String releaseId) throws Exception {
        PagingParameter pagingParameter = new PagingParameter(0, 1);
        EntInquiryParam inquiryParam = new EntInquiryParam();

        SysEnterpriseBase sysEnterpriseBase = sysEnterpriseBaseService.getDefaultDispositionEnt();
        inquiryParam.setReleaseId(releaseId);
        inquiryParam.setInquiryType(InquiryType.REFERENCE_PRICE.getCode());
        inquiryParam.setLoginEntId(sysEnterpriseBase.getEntId());
        try {
            List<EntInquiryModel> list = listWasteInquiry(null, pagingParameter, inquiryParam);
            if (list != null && list.size() > 0) {
                Optional<EntInquiryModel> inquiryModel = list.stream().findFirst();
                return inquiryModel.orElse(null);
            }
        } catch (Exception e) {
            logger.error("生成系统报价时异常", e);
            throw e;
        }
        return null;

    }

    @Override
    public List listReleaseEntNameByEntId(String enterpriseId) throws Exception {
        return this.baseMapper.listReleaseEntNameByEntId(enterpriseId);
    }


}
