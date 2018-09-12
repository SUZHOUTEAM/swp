package com.mlsc.waste.utils;

/**
 * 所有的表名管理
 * 
 * @author zhugl
 */
public class TableNameConstants {

    /**
     * 行业表：calling
     */
    public static final String TABLE_CALLING = "calling";

    /**
     * 字典类型表：code_type
     */
    public static final String TABLE_CODE_TYPE = "code_type";

    /**
     * 字典信息表：code_value
     */
    public static final String TABLE_CODE_VALUE = "code_value";

    /**
     * 危废（八位码）：waste
     */
    public static final String TABLE_WASTE = "waste";

    /**
     * 危废名称表：waste_name
     */
    public static final String TABLE_WASTE_NAME = "waste_name";

    /**
     * 危废类别表：危废类别
     */
    public static final String TABLE_WASTE_TYPE = "waste_type";

    /**
     * 消息评论表：cooperation_comment
     */
    public static final String TABLE_COOPERATION_COMMENT = "cooperation_comment";

    /**
     * 消息信息表：cooperation_message
     */
    public static final String TABLE_COOPERATION_MESSAGE = "cooperation_message";

    /**
     * 消息-业务关联表：cooperation_message_business
     */
    public static final String TABLE_COOPERATION_MESSAGE_BUSINESS = "cooperation_message_business";

    /**
     * 委托处理计划发布信息：disposition_plan_release
     */
    public static final String TABLE_DISPOSITION_PLAN_RELEASE = "disposition_plan_release";

    /**
     * 委托处理计划发布信息：TABLE_PENDING_DISPOSITION_RELEASE
     */
    public static final String TABLE_PENDING_DISPOSITION_RELEASE = "pending_dispostion_release";

    /**
     * 委托处理计划信息备注表：disposition_plan_remark
     */
    public static final String TABLE_DISPOSITION_PLAN_REMARK = "disposition_plan_remark";

    /**
     * 委托处理计划应答信息表：disposition_plan_response
     */
    public static final String TABLE_DISPOSITION_PLAN_RESPONSE = "disposition_plan_response";

    /**
     * 委托处理计划发布信息_危废信息表：disposition_planitem_release
     */
    public static final String TABLE_DISPOSITION_PLANITEM_RELEASE = "disposition_planitem_release";

    /**
     * 委托处理计划应答信息_危废信息表：disposition_planitem_response
     */
    public static final String TABLE_DISPOSITION_PLANITEM_RESPONSE = "disposition_planitem_response";

    /**
     * 企业危废信息表：enterprise_waste
     */
    public static final String TABLE_ENTERPRISE_WASTE = "enterprise_waste";

    /**
     * 企业危废信息表：ent_waste
     */
    public static final String TABLE_ENT_WASTE = "ent_waste";

    /**
     * 经营许可证-基本信息：operation_licence
     */
    public static final String TABLE_OPERATION_LICENCE = "operation_licence";

    /**
     * 经营许可证-核准经营内容：operation_licence_item
     */
    public static final String TABLE_OPERATION_LICENCE_ITEM = "operation_licence_item";

    /**
     * 经营许可证—核准经营内容详细信息：operation_licence_detail
     */
    public static final String TABLE_OPERATION_LICENCE_DETAIL = "operation_licence_detail";

    /**
     * 历史版本经营许可证-基本信息：version_operation_licence
     */
    public static final String TABLE_VERSION_OPERATION_LICENCE = "version_operation_licence";

    /**
     * 历史版本经营许可证-核准经营内容：version_operation_licence_item
     */
    public static final String TABLE_VERSION_OPERATION_LICENCE_ITEM = "version_operation_licence_item";

    /**
     * 历史版本经营许可证—核准经营内容详细信息：version_operation_licence_detail
     */
    public static final String TABLE_VERSION_OPERATION_LICENCE_DETAIL = "version_operation_licence_detail";

    /**
     * 企业类型表(保存企业拥有哪些企业类型（产废、处置、运输、实验室等）)：waste_enterprise_type
     */
    public static final String TABLE_WASTE_ENTERPRISE_TYPE = "waste_enterprise_type";

    /**
     * 用户/企业关系审核记录表
     */
    public static final String TABLE_USER_ENTERPRISE_APPROVE_RECORD = "user_enterprise_approve_record";

    /**
     * 企业扩展表
     */
    public static final String TABLE_ENTERPRISE_EXTENDED = "enterprise_extended";

    /**
     * 用户扩展表
     */
    public static final String TABLE_USER_EXTENDED = "user_extended";

    /**
     * 企业ID映射（外部平台企业与易废网注册企业ID的映射）
     */
    public static final String TABLE_DATA_SYNCHRONIZE = "data_synchronize";

    /**
     * 人-企业-人的关系表
     */
    public static final String TABLE_COOPERATION_RELATION = "cooperation_relation";

    /**
     * 易废圈通知信息（消息）表
     */
    public static final String TABLE_COOPERATION_NOTICE = "cooperation_notice";

    /**
     * 鉴定能力信息表
     */
    public static final String TABLE_IDENTIFICATION_ABILITY = "identification_ability";

    /**
     * 上传文件表
     */
    public static final String TABLE_UPLOAD_FILE = "upload_file";

    /**
     * 处置能力发布信息
     */
    public static final String TABLE_DISPOSITION_CAPACITY_RELEASE = "disposition_capacity_release";

    /**
     * 处置能力发布信息_可处置信息
     */
    public static final String TABLE_DISPOSITION_CAPACITYITEM_RELEASE = "disposition_capacityitem_release";

    /**
     * 处置能力发布信息_可处置危废信息
     */
    public static final String TABLE_DISPOSITION_CAPACITYDETAIL_RELEASE = "disposition_capacitydetail_release";

    /**
     * 处置能力应答信息
     */
    public static final String TABLE_DISPOSITION_CAPACITY_RESPONSE = "disposition_capacity_response";

    /**
     * 处置能力应答信息_需处置危废信息表
     */
    public static final String TABLE_DISPOSITION_CAPACITYDETAIL_RESPONSE = "disposition_capacitydetail_response";

    /**
     * 购物车表
     */
    public static final String TABLE_SHOPPING_CART = "shopping_cart";

    /**
     * 购物车明细表
     */
    public static final String TABLE_SHOPPING_CART_DETAIL = "shopping_card_detail";

    /**
     * 订单表
     */
    public static final String TABLE_ORDERS = "orders";

    public static final String TABLE_ORDER_DETAIL = "order_detail";

    /**
     * 用户行为记录表
     */
    public static final String TABLE_ACTION_RECORD = "action_record";

    /**
     * 订单评价表
     */
    public static final String TABLE_ORDER_VALUATION = "order_valuation";

    /**
     * 离线消息表
     */
    public static final String TABLE_OFFLINE_MESSAGE = "offline_message";
    // -------------以下是系统平台表的表名定义-----------------------------------
    /**
     * 平台表：企业基表
     */
    public static final String TABLE_SYS_ENTERPRISE_BASE = "sys_enterprise_base";

    /**
     * 平台： 用户表
     */
    public static final String TABLE_SYS_USER = "sys_user";

    /**
     * 平台： sys_user_enterprise_relation
     */
    public static final String TABLE_SYS_USER_ENTERPRISE_RELATION = "sys_user_enterprise_relation";

    /**
     * 平台： sys_coordinate 主要存经纬度
     */
    public static final String TABLE_SYS_COORDINATE = "sys_coordinate";

    /**
     * 平台： sys_org_com 组织机构
     */
    public static final String TABLE_SYS_ORG_COM = "sys_org_com";

    /**
     * 平台： sys_org_com 区域代码
     */
    public static final String TABLE_SYS_CANYON = "sys_canton";

    /**
     * 平台： sys_org_com_canton_relation 组织机构与区域代码的关系表
     */
    public static final String TABLE_SYS_ORG_COM_CANTON_RELATION = "sys_org_com_canton_relation";
    /**
     * 活动关联企业表
     */
    public static final String TABLE_WASTE_ACTIVITY_ENTERPRISE = "waste_activity_enterprise";
    /**
     * 活动表
     */
    public static final String TABLE_WASTE_ACTIVITY = "waste_activity";

    /**
     * app版本
     */
    public static final String APP_CONFIG = "app_config";
}
