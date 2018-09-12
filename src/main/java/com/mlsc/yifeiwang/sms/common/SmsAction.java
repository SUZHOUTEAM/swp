package com.mlsc.yifeiwang.sms.common;


import com.mlsc.waste.utils.CodeTypeConstant;

import java.util.HashMap;
import java.util.Map;

import static com.mlsc.yifeiwang.sms.common.SmsSendType.JPUSH;
import static com.mlsc.yifeiwang.sms.common.SmsSendType.SMS;
import static com.mlsc.yifeiwang.sms.common.SmsSendType.SYS;
import static com.mlsc.yifeiwang.sms.common.SmsTemplateCode.*;


/**
 * 消息总控
 * Created by yinxl on 2017/7/20.
 */
public enum SmsAction {
    ENT_NEW_FAC_INQUIRY("ENT_NEW_FAC_INQUIRY","有处置企业向你发起确认报价，详情请进入“我的询价->查看报价“。或电话联系你的危废专员${userName}，电话为：${phoneNo}", NoticeRouteUrl.MY_PUBLISH, SMS_137671772, new
            SmsSendType[]{SYS,SMS,JPUSH}, NoticeCategory.PURCHASE_STATUS, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    ENT_NEW_INQUIRY("ENT_NEW_INQUIRY","有处置企业向你发起确认报价，详情请进入“我的询价->查看报价“。", NoticeRouteUrl.MY_PUBLISH, SMS_136397017, new
            SmsSendType[]{SYS,SMS,JPUSH}, NoticeCategory.PURCHASE_STATUS, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    ENT_CONFIRM_INQUIRY("ENT_CONFIRM_INQUIRY","${entName}确认了你方报价，详情请查看“我的订单”。", NoticeRouteUrl.MY_ORDER, SMS_142949508, new
            SmsSendType[]{SYS,SMS, JPUSH}, NoticeCategory.NEW_ORDER, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    ENT_REFUSE_INQUIRY("ENT_REFUSE_INQUIRY"," ${entName}拒绝了你方的报价，详情请查看“我的报价”。", NoticeRouteUrl.MY_INQUIRY, SMS_142949286, new
            SmsSendType[]{SYS,SMS, JPUSH}, NoticeCategory.PURCHASE_STATUS,CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    USER_JOIN_ENT_PASS_REGULAR("USER_JOIN_ENT_PASS_REGULAR","管理员已核实了你的信息，你已经成为“${entName}”企业的用户,请重新登录。", null, SMS_115130121, new
            SmsSendType[]{SYS,SMS,JPUSH}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    ENT_NEW_ORDER("ENT_NEW_ORDER","我的发布，生成新的订单", NoticeRouteUrl.MY_ORDER, "", new SmsSendType[]{SYS,JPUSH}, NoticeCategory.NEW_ORDER,
            CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    ENT_NEW_LIC_PASS("ENT_NEW_LIC_PASS","你绑定的企业{entName}新增的许可证被审核通过", null, SMS_39270230, new SmsSendType[]{SYS, SMS,
            JPUSH}, NoticeCategory.SYS_TYPE,CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    ENT_NEW_LIC_REFUSE("ENT_NEW_LIC_REFUSE","你绑定的企业{entName}新增的许可证被审核退回", null, "", new SmsSendType[]{SYS,  JPUSH},
            NoticeCategory.SYS_TYPE,CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    USER_CREATE_ENT_PASS("USER_CREATE_ENT_PASS","你创建企业{entName}的申请被通过，你已被设为企业管理员", null, SMS_76480151, new
            SmsSendType[]{SYS,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    USER_CREATE_ENT_REFUSE("USER_CREATE_ENT_REFUSE","你创建企业{entName}的申请被拒绝", null, "", new SmsSendType[]{SYS},
            NoticeCategory.SYS_TYPE,CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    USER_JOIN_ENT_PASS_ADMIN("USER_JOIN_ENT_PASS_ADMIN","你加入企业{entName}的申请被通过，你已被设为企业管理员", null, SMS_76420151, new
            SmsSendType[]{SYS,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    USER_JOIN_ENT_REFUSE("USER_JOIN_ENT_REFUSE","你加入企业{entName}的申请被拒绝", null, "", new SmsSendType[]{SYS}, NoticeCategory
            .SYS_TYPE,CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    USER_QUIT_ENT("USER_QUIT_ENT","你已退出企业{entName}", null, "", new SmsSendType[]{SYS}, NoticeCategory.SYS_TYPE,
            CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    USER_REMOVE_ADMIN("USER_REMOVE_ADMIN","你在{entName}的企业管理员身份已经被移除", null, "", new SmsSendType[]{}, NoticeCategory
            .SYS_TYPE,
            CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    USER_BECOME_ADMIN("USER_BECOME_ADMIN","你已成为{entName}的企业管理员", null, "", new SmsSendType[]{SYS}, NoticeCategory
            .SYS_TYPE,
            CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    FOLLOW_NEW_PUBLISH("FOLLOW_NEW_PUBLISH","你关注的企业{entName}有新资源单", NoticeRouteUrl.RESOURCE_POOL, SMS_21235045, new
            SmsSendType[]{SYS, JPUSH}, NoticeCategory.NEW_RESOURCELIST, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    ACTIVITY_NEW_PUBLISH("ACTIVITY_NEW_PUBLISH","易废网发布了新的活动{activityName}",null,"",new SmsSendType[]{SYS,JPUSH},
            NoticeCategory.ACTIVITY_STATUS,CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    APPLY_ACTIVITY("APPLY_ACTIVITY","{entName}申请参加{activityName}",null,"",new SmsSendType[]{SYS,JPUSH},
            NoticeCategory.ACTIVITY_STATUS,CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    NEW_PUBLISH("NEW_PUBLISH","易废网有新的危废处置需求，速去报价！", NoticeRouteUrl.RESOURCE_POOL, SMS_142621158, new
            SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.NEW_RESOURCELIST, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    ACCT_TRANSFER("ACCT_TRANSFER","目前暂支持线下转帐，请转帐{amount}元至帐号{accountNo},管理员审核通过后会以短信形式通知报名成功。",null, SMS_126030059, new
                SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    CROWD_FUNDING_SUCCEED("CROWD_FUNDING_SUCCEED","{activityName}成功开课，请按时参加。",null, SMS_125770072, new
                  SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    CROWD_FUNDING_FAILED("CROWD_FUNDING_FAILED","{activityName}取消开课，报名费用将退回原支付方式。",null, SMS_125720088, new
                          SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    NEW_USER("NEW_USER","已有新用户注册，企业是{entName}，联系人是{userName}，联系电话为：{phoneNo}。",null, SMS_125940075, new
                         SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    UPLOADED_CONTRACT("UPLOADED_CONTRACT","您有新的订单，合同已上传",null, SMS_125955104,new
            SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    RECORDCONTRACT_SUBMIT("RECORDCONTRACT_SUBMIT","企业名称为{entName}，用户是{userName}的 合同备案已提交",null, SMS_125780136,new
            SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    RECORDCONTRACT_APPROVE("RECORDCONTRACT_APPROVE","您所提交的备案已审核通过，请登录易废网查看",null, SMS_125930123,new
                          SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    RECORDCONTRACT_REJECT("RECORDCONTRACT_REJECT","您所提交的备案被退回，请登录易废网重新提交",null, SMS_126020096,new
            SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    NEW_PUBLISH_SYSADMIN("ACCT_TRANSFER","企业为{entName}发布危废，用户是{userName},联系电话是{phoneNo}\n",null, SMS_126020118, new
            SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    BIND_SET_SYSTEM("BIND_SET_SYSTEM","公司为{entName}的{userName}购买了{setName},请登录系统后台查看",null, SMS_143865053, new
             SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE),
    BIND_SET("BIND_SET","您已成功购买${setName}服务！稍后将有工作人员联系您确认后续服务事项并签订合同",null, SMS_136161113, new
             SmsSendType[]{SYS, JPUSH,SMS}, NoticeCategory.SYS_TYPE, CodeTypeConstant.RECEIVER_TYPE_SINGLE);

    SmsAction(String actionCode,String actionDesc, NoticeRouteUrl noticeRouteUrl, String smsTemplateCode, SmsSendType[] sendTypes,
            NoticeCategory noticeCategory, String receiverTypeCode) {
        this.actionCode = actionCode;
        this.actionDesc = actionDesc;
        this.noticeRouteUrl = noticeRouteUrl;
        this.smsTemplateCode = smsTemplateCode;
        this.sendTypes = sendTypes;
        this.noticeCategory = noticeCategory;
        this.receiverTypeCode = receiverTypeCode;
    }
    private String actionCode;
    private String actionDesc;
    private NoticeRouteUrl noticeRouteUrl;
    private String smsTemplateCode;
    private SmsSendType[] sendTypes;
    private NoticeCategory noticeCategory;
    private String receiverTypeCode;

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public String getActionDesc(Map<String, String> map) {
        String result = actionDesc;
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                result = result.replaceAll("\\{" + entry.getKey() + "\\}", entry.getValue());
            }
        }
        return result;
    }


    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public NoticeRouteUrl getNoticeRouteUrl() {
        return noticeRouteUrl;
    }

    public void setNoticeRouteUrl(NoticeRouteUrl noticeRouteUrl) {
        this.noticeRouteUrl = noticeRouteUrl;
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode;
    }

    public SmsSendType[] getSendTypes() {
        return sendTypes;
    }

    public void setSendTypes(SmsSendType[] sendTypes) {
        this.sendTypes = sendTypes;
    }

    public NoticeCategory getNoticeCategory() {
        return noticeCategory;
    }

    public void setNoticeCategory(NoticeCategory noticeCategory) {
        this.noticeCategory = noticeCategory;
    }

    public String getReceiverTypeCode() {
        return receiverTypeCode;
    }

    public void setReceiverTypeCode(String receiverTypeCode) {
        this.receiverTypeCode = receiverTypeCode;
    }

    public static SmsAction getSmsActionByActionCode(String code){
        for(SmsAction smsAction : SmsAction.values()){
            if(smsAction.getActionCode().equals(code)){
                return smsAction;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("entName", "易废网旗舰店");
        String rs = SmsAction.ENT_NEW_INQUIRY.getActionDesc(map);
        System.out.println(rs);
    }
}
