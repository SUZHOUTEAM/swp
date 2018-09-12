package com.mlsc.yifeiwang.activity.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mlsc.yifeiwang.activity.common.ActivityContactsStatus;
import com.mlsc.yifeiwang.activity.common.ActivityStatus;
import com.mlsc.yifeiwang.activity.entity.WasteActivityContacts;
import com.mlsc.yifeiwang.sms.common.SmsTemplateCode;
import com.mlsc.yifeiwang.sms.model.SendMsgParameter;
import com.mlsc.yifeiwang.sms.service.SmsService;
import com.mlsc.yifeiwang.activity.mapper.WasteActivityContactsMapper;
import com.mlsc.yifeiwang.activity.mapper.WasteActivityMapper;
import com.mlsc.yifeiwang.activity.model.ActivityContactsQueryParam;
import com.mlsc.yifeiwang.activity.model.ActivityQueryParam;
import com.mlsc.yifeiwang.activity.model.WasteActivityVO;
import com.mlsc.yifeiwang.activity.service.IWasteActivityContactsService;
import com.mlsc.waste.fw.mapper.SysCantonMapper;
import com.mlsc.waste.fw.model.SysCanton;
import com.mlsc.waste.fw.model.SysCantonQueryParam;
import com.mlsc.waste.utils.CodeTypeConstant;
import com.mlsc.waste.utils.Util;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOperationContactsService;
import com.mlsc.yifeiwang.operaction.service.IWebsiteOpertionDisposalEnterpriseService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yxl
 * @since 2017-08-01
 */
@Service
@Component("wasteActivityContactsService")
public class WasteActivityContactsServiceImpl extends ServiceImpl<WasteActivityContactsMapper, WasteActivityContacts> implements IWasteActivityContactsService {
    private Logger logger = Logger.getLogger(WasteActivityContactsServiceImpl.class);

    @Autowired
    private WasteActivityMapper wasteActivityMapper;

    @Autowired
    private SysCantonMapper sysCantonMapper;

    @Autowired
    private SmsService smsService;

    @Autowired
    private WasteActivityContactsMapper wasteActivityContactsMapper;

    @Autowired
    private IWebsiteOperationContactsService contactsService;

    @Autowired
    private IWebsiteOpertionDisposalEnterpriseService disposalEnterpriseService;

    @Override
    public void generateWasteActivityContactsByActivity(String activityId) {
        ActivityQueryParam queryParam = new ActivityQueryParam();
        queryParam.setActivityId(activityId);
        List<WasteActivityVO> list = wasteActivityMapper.listWasteActivity(queryParam);
        if (list != null && list.size() > 0) {
            WasteActivityVO wasteActivityVO = list.get(0);
            String cantonCode = wasteActivityVO.getCantonCode();
            wasteActivityVO.setCantonCode(Util.calculateCantonCode(cantonCode));
            List<WasteActivityContacts> wasteActivityContactsList = listWasteActivityContacts(wasteActivityVO);
            if (wasteActivityContactsList != null && wasteActivityContactsList.size() > 0) {
                insertBatch(wasteActivityContactsList);
            }
        }
    }

    @Override
    public int generateWasteActivityCoverEntCount(String activityId) throws Exception {
        ActivityQueryParam queryParam = new ActivityQueryParam();
        int coverEntCount = 0;
        try {

            queryParam.setActivityId(activityId);
            List<WasteActivityVO> list = wasteActivityMapper.listWasteActivity(queryParam);
            if (list != null && list.size() > 0) {
                WasteActivityVO wasteActivityVO = list.get(0);
                String cantonCode = wasteActivityVO.getCantonCode();
                wasteActivityVO.setCantonCode(Util.calculateCantonCode(cantonCode));
                ActivityContactsQueryParam activityContactsQueryParam = prepareActivityContacts( wasteActivityVO);
                coverEntCount = this.baseMapper.countNotifyEnt(activityContactsQueryParam);
                coverEntCount = coverEntCount + this.baseMapper.countConstactsUserFromEnterContacts(activityContactsQueryParam);
            }
        } catch (Exception e) {
            logger.error("产生企业覆盖数时异常", e);
            throw e;
        }
        return coverEntCount;
    }

    private List<String> calculateCantonCode(String cantonCode) {
        List<String> cantonCodeList = new ArrayList<>();
        if (StringUtils.isNotEmpty(cantonCode)) {
            SysCantonQueryParam param = new SysCantonQueryParam();
            if (cantonCode.indexOf(",") != -1) {
                String[] cantonCodeArray = cantonCode.split(",");
                List<String> pCodeList = Arrays.asList(cantonCodeArray);
                cantonCodeList.addAll(pCodeList);
                param.setParentCantonCodeList(pCodeList);
            } else {
                cantonCodeList.add(cantonCode);
                param.setParentCantonCode(cantonCode);
            }
            List<SysCanton> children = sysCantonMapper.listSysCanton(param);
            List<String> parentCodeList = null;
            while (children != null && children.size() > 0) {
                parentCodeList = new ArrayList<>();
                for (SysCanton sysCanton : children) {
                    parentCodeList.add(sysCanton.getCantonCode());
                    cantonCodeList.add(sysCanton.getCantonCode());
                }
                param = new SysCantonQueryParam();
                param.setParentCantonCodeList(parentCodeList);
                children = sysCantonMapper.listSysCanton(param);
            }
        }
        return cantonCodeList;
    }


    private List<WasteActivityContacts> listWasteActivityContacts(WasteActivityVO wasteActivityVO) {
        List<WasteActivityContacts> contactsList = new ArrayList<WasteActivityContacts>();
        ActivityContactsQueryParam activityContactsQueryParam = prepareActivityContacts( wasteActivityVO);
        contactsList = wasteActivityContactsMapper.obtainActivityContactList(activityContactsQueryParam);

        List<WasteActivityContacts> otherEnterContactlist = getConstactsUserFromEnterContacts(activityContactsQueryParam);
        if (otherEnterContactlist != null && otherEnterContactlist.size() > 0) {
            contactsList.addAll(otherEnterContactlist);
        }
        return contactsList;
    }

    private ActivityContactsQueryParam prepareActivityContacts( WasteActivityVO wasteActivityVO) {
        ActivityContactsQueryParam activityContactsQueryParam = new ActivityContactsQueryParam();
        activityContactsQueryParam.setEntId(wasteActivityVO.getEntId());
        activityContactsQueryParam.setEntTypeCode(CodeTypeConstant.ENTERPRISE_TYPE_PRODUCTION);
        activityContactsQueryParam.setActivityId(wasteActivityVO.getActivityId());
        activityContactsQueryParam.setCantonCode(wasteActivityVO.getCantonCode());
        activityContactsQueryParam.setStatus(ActivityContactsStatus.NOT_SENDED.getValue());
        activityContactsQueryParam.setIndustry(wasteActivityVO.getIndustry());
        return activityContactsQueryParam;
    }

    private List<WasteActivityContacts> getConstactsUserFromEnterContacts(ActivityContactsQueryParam activityContactsQueryParam) {
        return this.baseMapper.getConstactsUserFromEnterContacts(activityContactsQueryParam);
    }

    @Override
    public void notifyAllActivityContacts(String activityId) throws Exception {
        ActivityQueryParam activityQueryParam = new ActivityQueryParam();
        activityQueryParam.setStatus(ActivityStatus.ACTIVE.getValue());
        if (StringUtils.isNotBlank(activityId)) {
            activityQueryParam.setActivityId(activityId);
        }

        List<WasteActivityVO> activityList = wasteActivityMapper.listWasteActivity(activityQueryParam);
        if (activityList != null && activityList.size() > 0) {
            for (WasteActivityVO wasteActivityVO : activityList) {
                EntityWrapper<WasteActivityContacts> entityWrapper = new EntityWrapper<>();
                List<WasteActivityContacts> contactsList = this.baseMapper.getUserPhoneByActivityId(wasteActivityVO.getActivityId());
                if (StringUtils.isNotBlank(wasteActivityVO.getSmsTemplate()) && contactsList != null && contactsList.size() > 0) {
                    int index = 0;
                    int sendSize = 0;
                    StringBuilder contactsPhoneBuilder = new StringBuilder();
                    List<WasteActivityContacts> sendContactsList = new ArrayList<>();
                    Date now = new Date();
                    logger.debug(wasteActivityVO.getActivityId() + "-----开始发活动短信-----总共条数:" + contactsList.size() + "-----------");
                    while (index < contactsList.size()) {
                        WasteActivityContacts contacts = contactsList.get(index);
                        String phoneNum = contacts.getUserPhone();
                        if (StringUtils.isNotEmpty(phoneNum)) {
                            contactsPhoneBuilder.append(",");
                            contactsPhoneBuilder.append(phoneNum);
                            contacts.setStatus(ActivityContactsStatus.SENDED.getValue());
                            contacts.setUpdateTime(now);
                            sendContactsList.add(contactsList.get(index));
                            sendSize++;
                        }
                        try {
                            if ((sendSize != 0 && sendSize % SmsTemplateCode.ONCE_RECEIVER_NUM == 0)
                                    || index == contactsList
                                    .size() - 1) {
                                SendMsgParameter parameter = new SendMsgParameter();
                                parameter.setSmsTemplateCode(wasteActivityVO.getSmsTemplate());
                                parameter.setPhoneNum(contactsPhoneBuilder.toString().substring(1));
                                smsService.sendMsg(parameter);
                                logger.debug("-----开始发活动短信-----已发条数:" + sendContactsList.size() + "-----------");
                                logger.debug("-----发送活动手机号码为：" + contactsPhoneBuilder.toString().substring(1));
                                sendContactsList = new ArrayList<>();
                                contactsPhoneBuilder = new StringBuilder();
                            }
                        } catch (Exception e) {
                            logger.error("发送活动短信失败：", e);
                        }
                        index++;
                    }
                    logger.debug("-----结束发活动短信-----------");
                }
            }
        }
    }


}
