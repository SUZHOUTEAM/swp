package com.mlsc.yifeiwang.recordcontract.service.impl;

import cn.jiguang.common.utils.StringUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.recordcontract.common.RecordContractStatus;
import com.mlsc.epdp.common.base.entity.PagingParameter;
import com.mlsc.yifeiwang.sms.common.SmsAction;
import com.mlsc.yifeiwang.sms.model.msgevent.MsgEvent;
import com.mlsc.yifeiwang.sms.service.SysMsgServcie;
import com.mlsc.yifeiwang.user.service.IUserInfoService;
import com.mlsc.waste.user.model.User;
import com.mlsc.waste.utils.UserRole;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.entorder.entity.EntOrder;
import com.mlsc.yifeiwang.entorder.entity.EntOrderDetail;
import com.mlsc.yifeiwang.recordcontract.mapper.EntRecordContractMapper;
import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContract;
import com.mlsc.yifeiwang.recordcontract.entity.EntRecordContractDetail;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractDetailModel;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractModel;
import com.mlsc.yifeiwang.recordcontract.model.EntRecordContractParam;
import com.mlsc.yifeiwang.entorder.service.IEntOrderDetailService;
import com.mlsc.yifeiwang.entorder.service.IEntOrdersService;
import com.mlsc.yifeiwang.recordcontract.service.IEntRecordContractDetailService;
import com.mlsc.yifeiwang.recordcontract.service.IEntRecordContractService;
import org.apache.commons.beanutils.BeanUtils;
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
 * @author caoyy
 * @since 2018-03-01
 */
@Service
public class EntRecordContractServiceImpl extends ServiceImpl<EntRecordContractMapper, EntRecordContract> implements IEntRecordContractService {
    private final static Logger logger = LoggerFactory.getLogger(EntRecordContractServiceImpl.class);
    @Autowired
    private IEntOrdersService entOrdersService;
    @Autowired
    private IEntOrderDetailService entOrderDetailService;
    @Autowired
    private IEntRecordContractDetailService entRecordContractDetailService;

    @Autowired
    private SysMsgServcie sysMsgServcie;

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String saveRecordContract(User user, EntRecordContractParam recordContractParam) throws Exception {
        try {
            String recordId = saveRecordContractInfo(user, recordContractParam);
            saveRecordContractDetail(user, recordId, recordContractParam);
            informSubmitInfo2SysAdminUser(user);
            return recordId;
        } catch (Exception e) {
            logger.error("保存区域服务商备案时异常", e);
            throw e;
        }
    }

    private String saveRecordContractInfo(User user, EntRecordContractParam recordContractParam) throws Exception {
        EntRecordContract entRecordContract = new EntRecordContract();
        EntOrder entOrder = entOrdersService.selectById(recordContractParam.getOrderId());
        BeanUtils.copyProperties(entRecordContract, entOrder);
        Date date = new Date();
        entRecordContract.setOrderId(entOrder.getId());
        entRecordContract.setTotalPrice(recordContractParam.getTotalPrice());
        entRecordContract.setRemark(recordContractParam.getRemark());
        entRecordContract.setQuotedType(recordContractParam.getQuotedType());
        entRecordContract.setBusiStatus(RecordContractStatus.SUBMIT.getCode());
        entRecordContract.setCreateBy(user.getUserId());
        entRecordContract.setCreateTime(date);
        entRecordContract.setEditBy(user.getUserId());
        entRecordContract.setEditTime(date);
        entRecordContract.setId("");
        this.baseMapper.insert(entRecordContract);
        return entRecordContract.getId();
    }

    private void saveRecordContractDetail(User user, String recordId, EntRecordContractParam entRecordContractParam) throws Exception {
        List<EntRecordContractDetail> recordContractDetails = entRecordContractParam.getEntRecordContractDetailList();
        if (recordContractDetails != null && recordContractDetails.size() > 0) {
            Date date = new Date();
            for (EntRecordContractDetail entRecordContractDetail : recordContractDetails) {
                EntOrderDetail entOrderDetail = entOrderDetailService.selectById(entRecordContractDetail.getOrderDetailId());
                EntRecordContractDetail insertData = new EntRecordContractDetail();
                BeanUtils.copyProperties(insertData, entOrderDetail);

                insertData.setRecordId(recordId);
                insertData.setBusiStatus(RecordContractStatus.SUBMIT.getCode());
                insertData.setOrderDetailId(entOrderDetail.getId());
                insertData.setOrderId(entRecordContractParam.getOrderId());
                insertData.setPrice(entRecordContractDetail.getPrice());
                insertData.setCreateBy(user.getUserId());
                insertData.setCreateTime(date);
                insertData.setEditBy(user.getUserId());
                insertData.setEditTime(date);
                insertData.setId("");
                entRecordContractDetailService.insert(insertData);
            }
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateRecordContract(User user, EntRecordContractParam recordContractParam) throws Exception {
        EntRecordContract entRecordContract = this.baseMapper.selectById(recordContractParam.getId());
        if (entRecordContract != null) {
            if (recordContractParam.getTotalPrice() != 0 || recordContractParam.getQuotedType() != 0) {
                boolean updateResult = updateRecordContractInfo(user, recordContractParam, entRecordContract);
                if(updateResult){
                    updateRecordContractDetailInfo(user, recordContractParam);
                    informSubmitInfo2SysAdminUser(user);
                    return true;
                }
            }
        }
        return false;
    }

    private void updateRecordContractDetailInfo(User user, EntRecordContractParam recordContractParam) {
        List<EntRecordContractDetail> entRecordContractDetailList = recordContractParam.getEntRecordContractDetailList();
        if (entRecordContractDetailList != null && entRecordContractDetailList.size() > 0) {
            for (EntRecordContractDetail contractDetail : entRecordContractDetailList) {
                Date date = new Date();
                EntRecordContractDetail updatedData = entRecordContractDetailService.selectById(contractDetail.getId());
                if (contractDetail.getTotalPrice() != 0) {
                    updatedData.setPrice(contractDetail.getPrice());
                    updatedData.setTotalPrice(contractDetail.getTotalPrice());
                    updatedData.setEditBy(user.getUserId());
                    updatedData.setEditTime(date);
                    updatedData.setBusiStatus(RecordContractStatus.SUBMIT.getCode());
                    entRecordContractDetailService.updateById(updatedData);
                }
            }
        }
    }

    private boolean updateRecordContractInfo(User user, EntRecordContractParam recordContractParam, EntRecordContract entRecordContract) {
        Date date = new Date();
        if (recordContractParam.getTotalPrice() != 0) {
            entRecordContract.setTotalPrice(recordContractParam.getTotalPrice());
        }
        entRecordContract.setTotalPrice(recordContractParam.getTotalPrice());
        entRecordContract.setQuotedType(recordContractParam.getQuotedType());
        if (StringUtils.isNotEmpty(recordContractParam.getRemark())) {
            entRecordContract.setRemark(recordContractParam.getRemark());
        }
        entRecordContract.setBusiStatus(RecordContractStatus.SUBMIT.getCode());
        entRecordContract.setEditBy(user.getUserId());
        entRecordContract.setEditTime(date);
        return this.updateById(entRecordContract);
    }

    private void informSubmitInfo2SysAdminUser(User user) throws Exception {
        List<User> sysAdminUserList = userInfoService.listSysAdminUser(UserRole.ADMIN.getRoleCode());
        if (sysAdminUserList != null && sysAdminUserList.size() > 0) {
            MsgEvent msgEvent = new MsgEvent(SmsAction.RECORDCONTRACT_SUBMIT);
            msgEvent.setSendUser(user);
            Map<String, String> placeholderValueMap = new HashMap<String, String>();
            placeholderValueMap.put("entName", user.getEnterpriseName());
            placeholderValueMap.put("userName", user.getUserName());
            msgEvent.setPlaceholderValueMap(placeholderValueMap);
            msgEvent.setReceiveUserList(sysAdminUserList);
            sysMsgServcie.sendMessageSync(msgEvent);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean approveRecordContract(User user, EntRecordContractParam recordContractParam) throws Exception {
        Date date = new Date();
        EntRecordContract entRecordContract = this.baseMapper.selectById(recordContractParam.getId());
        entRecordContract.setBusiStatus(RecordContractStatus.APPROVED.getCode());
        entRecordContract.setEditBy(user.getUserId());
        entRecordContract.setEditTime(date);
        boolean updateResult = this.updateById(entRecordContract);
        if (updateResult) {
            informApproveInfo2User(user, entRecordContract, SmsAction.RECORDCONTRACT_APPROVE);
        }
        return updateResult;
    }


    @Override
    public boolean rejectRecordContract(User user, EntRecordContractParam recordContractParam) throws Exception {
        Date date = new Date();
        EntRecordContract entRecordContract = this.baseMapper.selectById(recordContractParam.getId());
        entRecordContract.setBusiStatus(RecordContractStatus.REFUSED.getCode());
        entRecordContract.setEditBy(user.getUserId());
        entRecordContract.setEditTime(date);
        boolean rejectResult = this.updateById(entRecordContract);
        if(rejectResult){
            informApproveInfo2User(user, entRecordContract, SmsAction.RECORDCONTRACT_REJECT);
        }
        return rejectResult;
    }

    private void informApproveInfo2User(User user, EntRecordContract entRecordContract, SmsAction recordContractApprove) {
        try {
            String receiveEntId = StringUtils.isNotEmpty(entRecordContract.getFacilitatorEntId()) ? entRecordContract.getFacilitatorEntId() : entRecordContract.getReleaseEntId();
            List<String> receiveEntIds = new ArrayList<String>();
            receiveEntIds.add(receiveEntId);
            List<User> receiveUsers = userInfoService.listUserInfoByEnterIds(receiveEntIds);
            MsgEvent msgEvent = new MsgEvent(recordContractApprove);
            msgEvent.setSendUser(user);
            Map<String, String> placeholderValueMap = new HashMap<String, String>();
            msgEvent.setPlaceholderValueMap(placeholderValueMap);
            msgEvent.setReceiveUserList(receiveUsers);
            sysMsgServcie.sendMessageSync(msgEvent);
        } catch (Exception e) {
            logger.error("系统管理员发送备案审核信息时异常", e);
        }

    }

    @Override
    public EntRecordContractModel getRecordContractById(User user, EntRecordContractParam recordContractParam) throws Exception {
        try {
            List<EntRecordContractModel> contractModelList = this.baseMapper.listRecordContractList(recordContractParam);
            if (contractModelList != null && contractModelList.size() > 0) {
                EntRecordContractModel contractModel = contractModelList.get(0);
                if (contractModel != null) {
                    List<EntRecordContractDetailModel> recordContractDetailModelList = entRecordContractDetailService.listContractDetailByRecordId(contractModel.getId());
                    contractModel.setContractDetailModelList(recordContractDetailModelList);
                }
                return contractModel;
            }
        } catch (Exception e) {
            logger.error("根据id获取备案信息时异常", e);
            throw e;
        }
        return null;
    }

    @Override
    public List<EntRecordContractModel> listRecordContractList(User user, PagingParameter pagingParameter, EntRecordContractParam recordContractParam) throws Exception {
        List<EntRecordContractModel> entRecordContractModels = null;
        try {
            Util.initPagingParameter(pagingParameter);
            recordContractParam.setStartRowIndex(pagingParameter.getStart());
            recordContractParam.setRows(pagingParameter.getLimit());
            int count = this.baseMapper.countRecordContractList(recordContractParam);
            if (count > 0) {
                entRecordContractModels = this.baseMapper.listRecordContractList(recordContractParam);
                pagingParameter.setTotalRecord(count);
                Iterator<EntRecordContractModel> iter = entRecordContractModels.iterator();
                while (iter.hasNext()) {
                    EntRecordContractModel recordContractModel = iter.next();
                    List<EntRecordContractDetailModel> recordContractDetailModelList = entRecordContractDetailService.listContractDetailByRecordId(recordContractModel.getId());
                    recordContractModel.setContractDetailModelList(recordContractDetailModelList);
                }
            }
        } catch (Exception e) {
            logger.error("获取订单列表时异常", e);
            throw e;
        }

        return entRecordContractModels;
    }


}
