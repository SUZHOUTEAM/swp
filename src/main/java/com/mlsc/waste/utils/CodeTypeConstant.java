package com.mlsc.waste.utils;


public class CodeTypeConstant {
    
    /**
     * 产废类型:PRODUCT_TYPE
     */
    public static final String PRODUCT_TYPE = "PRODUCT_TYPE";
    
    /**
     * 计量单位:UNIT_TYPE
     */
    public static final String UNIT_TYPE = "UNIT_TYPE";
    
    /**
     * 计量单位:吨
     */
    public static final String UNIT_TYPE_T = "T";
    
    /**
     * 计量单位:只
     */
    public static final String UNIT_TYPE_C = "C";
    
    /**
     * 计量单位：立方
     */
    public static final String UNIT_TYPE_M3 = "立方";
    
    /**
     * 处置方式:DISPOSE_TYPE
     */
    public static final String DISPOSE_TYPE = "DISPOSE_TYPE";
    
    /**
     * 处置方式:水泥窑共处置
     */
    public static final String DISPOSE_TYPE_C1 = "C1";
    
    /**
     * 处置方式:生产建筑材料
     */
    public static final String DISPOSE_TYPE_C2 = "C2";
    
    /**
     * 处置方式:清洗（包装容器）
     */
    public static final String DISPOSE_TYPE_C3 = "C3";
    
    /**
     * 处置方式:填埋
     */
    public static final String DISPOSE_TYPE_D1 = "D1";
    
    /**
     * 处置方式:焚烧
     */
    public static final String DISPOSE_TYPE_D10 = "D10";
    
    /**
     * 处置方式:其他
     */
    public static final String DISPOSE_TYPE_D16 = "D16";
    
    /**
     * 处置方式:埋或焚烧前的预处理
     */
    public static final String DISPOSE_TYPE_D9 = "D9";
    
    /**
     * 处置方式:干法解毒
     */
    public static final String DISPOSE_TYPE_G21 = "G21";
    
    /**
     * 处置方式:湿法解毒
     */
    public static final String DISPOSE_TYPE_G22 = "G22";
    
    /**
     * 处置方式:烧结炼铁
     */
    public static final String DISPOSE_TYPE_G23 = "G23";
    
    /**
     * 处置方式:生产水泥
     */
    public static final String DISPOSE_TYPE_G24 = "G24";
    
    /**
     * 处置方式:其他
     */
    public static final String DISPOSE_TYPE_G29 = "G29";
    
    /**
     * 处置方式:作为燃料（直接燃烧除外）或以其他方式产生能量
     */
    public static final String DISPOSE_TYPE_R1 = "R1";
    
    /**
     * 处置方式:其他
     */
    public static final String DISPOSE_TYPE_R15 = "R15";
    
    /**
     * 处置方式:溶剂回收/再生（如蒸馏、萃取等）
     */
    public static final String DISPOSE_TYPE_R2 = "R2";
    
    /**
     * 处置方式:再循环/再利用不是用作溶剂的有机物
     */
    public static final String DISPOSE_TYPE_R3 = "R3";
    
    /**
     * 处置方式:再循环/再利用金属和金属化合物
     */
    public static final String DISPOSE_TYPE_R4 = "R4";
    
    /**
     * 处置方式:再循环/再利用其他无机物
     */
    public static final String DISPOSE_TYPE_R5 = "R5";
    
    /**
     * 处置方式:再生酸或碱
     */
    public static final String DISPOSE_TYPE_R6 = "R6";
    
    /**
     * 处置方式:回收污染减除剂的组分
     */
    public static final String DISPOSE_TYPE_R7 = "R7";
    
    /**
     * 处置方式:回收催化剂组分
     */
    public static final String DISPOSE_TYPE_R8 = "R8";
    
    /**
     * 处置方式:废油再提炼或其他废油的再利用
     */
    public static final String DISPOSE_TYPE_R9 = "R9";
    
    /**
     * 处置方式:医疗废物焚烧
     */
    public static final String DISPOSE_TYPE_Y10 = "Y10";
    
    /**
     * 处置方式:医疗废物高温蒸汽处理
     */
    public static final String DISPOSE_TYPE_Y11 = "Y11";
    
    /**
     * 处置方式:医疗废物化学消毒处理
     */
    public static final String DISPOSE_TYPE_Y12 = "Y12";
    
    /**
     * 处置方式:医疗废物微波消毒处理
     */
    public static final String DISPOSE_TYPE_Y13 = "Y13";
    
    /**
     * 处置方式:医疗废物其他处置方式
     */
    public static final String DISPOSE_TYPE_Y16 = "Y16";
    
    /**
     * 危废产生源
     */
    public static final String WASTE_SOURCE = "WASTE_SOURCE";
    /**
     * 危废产生源:生产工艺过程产生
     */
    public static final String WASTE_SOURCE_G1 = "G1";
    /**
     * 危废产生源:事故（如泄漏）产生。包括溢出的污染物及清洁被污染设备过程中产生的废物等
     */
    public static final String WASTE_SOURCE_G2 = "G2";
    /**
     * 危废产生源:设备检修、清库等过程产生
     */
    public static final String WASTE_SOURCE_G3 = "G3";
    /**
     * 危废产生源:其他
     */
    public static final String WASTE_SOURCE_G4 = "G4";
    
    /**
     * 许可证审核状态
     */
    public static final String LIC_AUDIT = "LIC_AUDIT";
    
    /**
     * 许可证审核状态:企业创建
     */
    public static final String LIC_AUDIT_CREATE = "CREATE";
    
    /**
     * 许可证审核状态:待审核
     */
    public static final String LIC_AUDIT_SUBMIT = "SUBMIT";
    
    /**
     * 许可证审核状态:审核通过
     */
    public static final String LIC_AUDIT_PASS = "PASS";
    
    /**
     * 许可证审核状态:审核退回
     */
    public static final String LIC_AUDIT_REFUSED = "REFUSED";
    
    /**
     * 终止
     */
    public static final String LIC_AUDIT_TERMINATION = "TERMINATION";
    
    /**
     * 许可证许可方式：包含  排除 全部
     */
    public static final String LIC_PERMIT = "LIC_CALC_TYPE";
    
    /**
     * 许可证许可方式：全部
     */
    public static final String LIC_PERMIT_CALC_ALL = "CALC_ALL";
    
    /**
     * 许可证许可方式：包含
     */
    public static final String LIC_PERMIT_CALC_INCLUDE = "CALC_INCLUDE";
    
    /**
     * 许可证许可方式：排除
     */
    public static final String LIC_PERMIT_CALC_EXCEPT = "CALC_EXCEPT";
    
    /**
     * 核准经营方式：危险废物收集经营 危险废物收集、贮存、处置综合经营
     */
    public static final String LIC_MODE = "LIC_MODE";
    
    /**
    * 核准经营方式：危险废物收集经营
    */
    public static final String LIC_MODE_COLLECTION = "COLLECTION";
    
    /**
    * 核准经营方式：危险废物收集、贮存、处置综合经营
    */
    public static final String LIC_MODE_INCLUDEALL = "INCLUDEALL";
    
    /**
     * 许可证有效状态
     */
    public static final String LIC_VALID = "LIC_VALID";
    
    /**
     * 许可证有效状态:有效
     */
    public static final String LIC_VALID_VALID = "VALID";
    
    /**
     * 许可证有效状态:未生效
     */
    public static final String LIC_VALID_INVALID = "INVALID";
    
    /**
     * 许可证有效状态:过期
     */
    public static final String LIC_VALID_OVERDUE = "OVERDUE";
    
    /**
     * 委托处理计划-报价方式（QUOTE_TYPE）
     */
    public static final String QUOTE_TYPE = "QUOTE_TYPE";
    
    /**
     * 委托处理计划-报价方式（整体报价）
     */
    public static final String QUOTE_TYPE_TOTAL_QUOTE = "TOTAL_QUOTE";
    
    /**
     * 委托处理计划-报价方式（明细报价）
     */
    public static final String QUOTE_TYPE_DETAIL_QUOTE = "DETAIL_QUOTE";
    
    /**
     * 委托处理计划-发布信息状态（DISPLAN_RTYPE）
     */
    public static final String DISPLAN_RTYPE = "DISPLAN_RTYPE";
    
    /**
     * 委托处理计划-发布信息状态（已发布）
     */
    public static final String DISPLAN_RTYPE_RELEASE = "RELEASE";
    
    /**
     * 委托处理计划-发布信息状态（已确认）
     */
    public static final String DISPLAN_RTYPE_ACCEPT = "ACCEPT";
    
    /**
     * 委托处理计划-应答信息状态（DISPLAN_ATYPE）
     */
    public static final String DISPLAN_ATYPE = "DISPLAN_ATYPE";
    
    /**
     * 委托处理计划-应答信息状态（未提交）
     */
    public static final String DISPLAN_ATYPE_UNSUBMIT = "UNSUBMIT";
    
    /**
     * 委托处理计划-应答信息状态（已提交）
     */
    public static final String DISPLAN_ATYPE_SUBMIT = "SUBMIT";
    
    /**
     * 委托处理计划-应答信息状态（已确认）
     */
    public static final String DISPLAN_ATYPE_ACCEPT = "ACCEPT";
    
    /**
     * 委托处理计划-应答信息状态（已关闭）
     */
    public static final String DISPLAN_ATYPE_COLSED = "COLSED";
    
    /**
     * 委托处理计划-应答信息状态（已谢绝）
     */
    public static final String DISPLAN_ATYPE_REFUSED = "REFUSED";
    
    /**
     * 委托处理计划-应答信息状态（已被其他处置企业确认）
     */
    public static final String DISPLAN_ATYPE_PROCESSED = "PROCESSED";
    
    /**
     * 危废特性
     */
    public static final String WASTE_FEATURES = "WASTE_FEATURES";
    /**
     * 危废特性:毒性
     */
    public static final String WASTE_FEATURES_T = "T";
    /**
     * 危废特性:腐蚀性
     */
    public static final String WASTE_FEATURES_C = "C";
    /**
     * 危废特性:易燃性
     */
    public static final String WASTE_FEATURES_I = "I";
    /**
     * 危废特性:反应性
     */
    public static final String WASTE_FEATURES_R = "R";
    /**
     * 危废特性:感染性
     */
    public static final String WASTE_FEATURES_In = "In";
    
    /**
     * 危险废物种类:WASTE_FLAG
     */
    public static final String WASTE_FLAG = "WASTE_FLAG";
    /**
     * 危险废物种类:爆炸性
     */
    public static final String WASTE_FLAG_Explosive = "Explosive";
    /**
     * 危险废物种类:有毒
     */
    public static final String WASTE_FLAG_Toxic = "Toxic";
    /**
     * 危险废物种类:易燃
     */
    public static final String WASTE_FLAG_Flammable = "Flammable";
    /**
     * 危险废物种类:有害
     */
    public static final String WASTE_FLAG_Harmful = "Harmful";
    /**
     * 危险废物种类:助燃
     */
    public static final String WASTE_FLAG_Oxidizing = "Oxidizing";
    /**
     * 危险废物种类:腐蚀性
     */
    public static final String WASTE_FLAG_Corrosive = "Corrosive";
    /**
     * 危险废物种类:刺激性
     */
    public static final String WASTE_FLAG_Irritant = "Irritant";
    /**
     * 危险废物种类:石棉
     */
    public static final String WASTE_FLAG_Asbestos = "Asbestos";
    
    /**
     * 废物形态:WASTE_PATTERN
     */
    public static final String WASTE_PATTERN = "WASTE_FLAG";
    /**
     * 废物形态:固态
     */
    public static final String WASTE_PATTERN_S = "S";
    /**
     * 废物形态:半固体
     */
    public static final String WASTE_PATTERN_SS = "SS";
    /**
     * 废物形态:液态
     */
    public static final String WASTE_PATTERN_L = "L";
    /**
     * 废物形态:气态
     */
    public static final String WASTE_PATTERN_G = "G";
    
    /**
     * 用户/企业事件类型:USER_EVENT_TYPE
     */
    public static final String USER_EVENT_TYPE = "USER_EVENT_TYPE";
    /**
     * 用户/企业事件类型:创建企业
     */
    public static final String USER_EVENT_TYPE_CREATE = "CREATE";
    /**
     * 用户/企业事件类型:更新企业
     */
    public static final String USER_EVENT_TYPE_UPDATE = "UPDATE";
    /**
     * 用户/企业事件类型:加入企业
     */
    public static final String USER_EVENT_TYPE_JOIN = "JOIN";
    /**
     * 用户/企业事件类型:退出企业
     */
    public static final String USER_EVENT_TYPE_QUIT = "QUIT";
    
    /**
     * 用户/企业事件状态:USER_EVENT_STATUS
     */
    public static final String USER_EVENT_STATUS = "USER_EVENT_STATUS";
    /**
     * 用户/企业事件类型:申请已提交
     */
    public static final String USER_EVENT_STATUS_SUBMIT = "SUBMIT";
    /**
     * 用户/企业事件类型:申请通过
     */
    public static final String USER_EVENT_STATUS_PASS = "PASS";
    /**
     * 用户/企业事件类型:申请未通过
     */
    public static final String USER_EVENT_STATUS_REFUSED = "REFUSED";
    /**
     * 用户/企业事件类型:申请已撤回
     */
    public static final String USER_EVENT_STATUS_REVERSED = "REVERSED";
    
    /**
     * 企业类型:ENTERPRISE_TYPE
     */
    public static final String ENTERPRISE_TYPE = "ENTERPRISE_TYPE";
    /**
     * 企业类型:产废企业
     */
    public static final String ENTERPRISE_TYPE_PRODUCTION = "PRODUCTION";
    /**
     * 企业类型:处置企业
     */
    public static final String ENTERPRISE_TYPE_DISPOSITION = "DISPOSITION";
    /**
     * 企业类型:综合利用企业
     */
    public static final String ENTERPRISE_TYPE_RECYCLING = "RECYCLING";
    /**
     * 企业类型:鉴定机构
     */
    public static final String ENTERPRISE_TYPE_IDENTIFICATION = "IDENTIFICATION";
    /**
     * 企业类型:运输企业
     */
    public static final String ENTERPRISE_TYPE_TRANSPORTATION = "TRANSPORTATION";
    /**
     * 企业类型:服务商
     */
    public static final String ENTERPRISE_TYPE_FACILITATOR = "FACILITATOR";

    /**
     * 企业类型:服务商
     */
    public static final String ENTERPRISE_TYPE_DIS_FACILITATOR = "DIS_FACILITATOR";
    /**
     * 易废圈-被关注者类型
     */
    public static final String FOLLOW_TYPE = "FOLLOW_TYPE";
    /**
     * 易废圈-被关注者类型:个人用户
     */
    public static final String FOLLOW_TYPE_USER = "SINGLE";
    /**
     * 易废圈-被关注者类型:企业用户
     */
    public static final String FOLLOW_TYPE_ORGANIZED = "ORGANIZED";
    
    /**
     * 易废圈-被通知者类型
     */
    public static final String RECEIVER_TYPE = "RECEIVER_TYPE";
    /**
     * 易废圈-被通知者类型:个人用户
     */
    public static final String RECEIVER_TYPE_SINGLE = "SINGLE";
    /**
     * 易废圈-被通知者类型:企业用户
     */
    public static final String RECEIVER_TYPE_ORGANIZED = "ORGANIZED";
    
    /**
     * 易废圈-通知类型:NOTICE_TYPE
     */
    public static final String NOTICE_CATEGORY= "NOTICE_CATEGORY";
//    /**
//     * 易废圈-通知类型 - 购买动态
//     */
//    public static final String NOTICE_TYPE_PURCHASE_STATUS = "PURCHASE_STATUS";
//    /**
//     * 易废圈-通知类型 - 新的订单
//     */
//    public static final String NOTICE_TYPE_NEW_ORDER = "NEW_ORDER";
//    /**
//     * 易废圈-通知类型 - 新的资源单
//     */
//    public static final String NOTICE_TYPE_NEW_RESOURCELIST = "NEW_RESOURCELIST";
//    /**
//     * 通知类型:系统消息
//     */
//    public static final String NOTICE_TYPE_SYS_TYPE = "SYS_TYPE";
    
    /**
     * 处置能力-发布信息状态:DISCAPACITY_RTYPE
     */
    public static final String DISCAPACITY_RTYPE = "DISCAPACITY_RTYPE";
    /**
     * 处置能力-发布信息状态:已发布
     */
    public static final String DISCAPACITY_RTYPE_RELEASE = "RELEASE";
    /**
     * 处置能力-发布信息状态:已中止
     */
    public static final String DISCAPACITY_RTYPE_CANCEL = "CANCEL";
    
    /**
     * 处置能力-应答信息状态:DISCAPACITY_ATYPE
     */
    public static final String DISCAPACITY_ATYPE = "DISCAPACITY_ATYPE";
    /**
     * 处置能力-应答信息状态:已提交
     */
    public static final String DISCAPACITY_ATYPE_SUBMIT = "SUBMIT";
    /**
     * 处置能力-应答信息状态:已确认
     */
    public static final String DISCAPACITY_ATYPE_ACCEPT = "ACCEPT";
    /**
     * 处置能力-应答信息状态:已中止
     */
    public static final String DISCAPACITY_ATYPE_COLSED = "COLSED";
    
    /**
     * 处置能力-应答信息状态:已谢绝
     */
    public static final String DISCAPACITY_ATYPE_REFUSED = "REFUSED";
    
    /**
     * 待发布危废处理状态:PENDING_DISPOSTION
     */
    public static final String PENDING_DISPOSTION = "PENDING_DISPOSTION";
    
    /**
     * 待发布危废处理状态:PENDING_DISPOSTION_SUBMIT
     */
    public static final String PENDING_DISPOSTION_SUBMIT = "SUBMIT";
    
    /**
     * 待发布危废处理状态:PENDING_DISPOSTION_ACCEPT
     */
    public static final String PENDING_DISPOSTION_ACCEPT = "ACCEPT";
    
    public static final String USER_STATUS = "USER_STATUS";

    public static final String USER_STATUS_PASS = "PASS";
    
    public static final String USER_STATUS_REJECT = "REJECT";
    
    public static final String USER_ROLE_ADMIN = "ADMIN";
    
    public static final String USER_ROLE_REGULAR = "REGULAR";
    
    public static final String USER_ROLE = "USER_ROLE";

    public static final String COUNTRY_CANTONCODE = "86";
    
}
