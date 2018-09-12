package com.mlsc.waste.utils;


public class Constant {

    //平台表的数据有效状态
    public static final int VALID_STATUS = 0;

    public static final int INVALID_STATUS = 1;

    public static final int DELETED = 1;
    /**
     * 停用状态
     */
    public static final String DISABLE = "0";
    /**
     * 启用状态
     */
    public static final String ENABLED = "1";

    /**
     * 系统统一有效标记
     */
    public static final String IS_VALID = "1";

    /**
     * 系统统一无效标记
     */
    public static final String IS_NOT_VALID = "0";

    /**
     * checkbox被选中
     */
    public static final String CHECKED_ON = "on";

    /**
     * checkbox未选中
     */
    public static final String CHECKED_OFF = "off";

    /**
     * 字典信息是否含有编码：有
     */
    public static final String SPLICE_ON = "1";

    /**
     * 字典信息是否含有编码：无
     */
    public static final String SPLICE_OFF = "0";

    /**
     * 是否判断：是(是否包含运费,是否在许可证范围内,是否有毒性,已读等判断true的情况)
     */
    public static final String IS_YSE = "1";

    /**
     * 是否判断：否(是否包含运费,是否在许可证范围内是否有毒性,未读等判断false的情况)
     */
    public static final String IS_NO = "0";

    /**
     * 用户通知信息状态：success
     */
    public static final String STATUS_SUCCESS = "success";

    /**
     * 用户通知信息状态：info
     */
    public static final String STATUS_INFO = "info";

    /**
     * 用户通知信息状态：danger
     */
    public static final String STATUS_DANGER = "danger";

    /**
     * 系统错误信息
     */
    public static final String SYS_MSG = "系统错误，请联系系统管理员协助解决！";

    /**
     * 500页面地址
     */
    public static final String URL_500 = "500";

    /**
     * 二位码的前缀
     */
    public static final String PRV_WASTE_TYPE = "HW";


    public static final String DISPOSITION_PLAN_RELEASE = "dispositionPlanRelease";

    public static final String DISPOSITION_CAPACITY_RELEASE = "dispositionCapacityRelease";

    public static final String NOTIFICATION_TITLE = "易废网";

    /**
     * 图片服务器地址
     */
    //public static final String IMG_SERVICE_URL = "http://192.168.252.130:8080/mlsc-webupload/service/sys/file/upload/view?appKey=5da5441f62e48aedc7a3853ffc75c2db&prodID=gf";//本地环境

    public static final String IMG_SERVICE_URL = "http://121.40.113.7/mlsc-webupload/service/sys/file/upload/view?appKey=5da5441f62e48aedc7a3853ffc75c2db&prodID=gf";// 正式发布环境   发布的时候这个变量需要切换


    /**
     * 平台管理员用户类型：暂定
     */
    public static final int SYS_USER_TYPE = 99;

    /**
     * 在表sys_org_com中环保局的comtype暂定为2 TODO
     */
    public static final int COM_TYPE_EPA = 2;


    /**
     * 从老固废同步数据时，默认的操作人员账号
     */
    public static final String SYS_ADMIN = "100000000000000";
    public static final String SYNCHRONIZED_DATA_TYPE_EE = "EE";// 易废网的企业ID与易废网以外的企业ID之间的映射
    public static final String SYNCHRONIZED_DATA_TYPE_UE = "UE";// 易废网的企用户ID与易废网以外的企业ID之间的映射


    public static final String ENTERPRISE_TYPE_PRODCITION = "Production";

    public static final String ENTERPRISE_TYPE_DISPOSITION = "Disposition";

    public static final String ENTERPRISE_TYPE_DIS_FACILITATOR = "DIS_FACILITATOR";

    public static final String ENTERPRISE_TYPE_IDENTIFICATION = "Identification";

    public static final String Lucene_Enterprise = "EnterpirseInfo";

    public static final String USER_TOKEN = "USER_TOKEN";

    public static final String Lucene_Enterprise_Waste = "EnterpirseInfoWaste";


    //---------------图片类型代码常量START------------------------
    public static final String FILE_TYPE_ENTERPRISE_LOGO = "a";//企业logo
    public static final String FILE_TYPE_ENTERPRISE_BUSINESS_LICENCE = "b";//企业营业执照 
    public static final String FILE_TYPE_ENTERPRISE_USER_LOGO = "c";//用户头像
    public static final String FILE_TYPE_ENTERPRISE_LICENCE = "d";//许可证
    public static final String FILE_TYPE_WASTE_LABORATORY_REPORT = "e";//危废化验单
    public static final String FILE_TYPE_WASTE = "f";//危废图片
    //---------------图片类型代码常量END---------------------------


    public static final double DISTANCE_INFINITY = 99999999;

    /*网易云信*/
    public static final String IM_APP_ENV_APPKEY = "appkey";
    public static final String IM_APP_ENV_SECRET = "secret";

    // 处置企业购买产废的经营协议信息同步队列名称
    public static final String ORDER_BUYWASTE_INFO_MSG = "order_buyWaste_info_msg";

    public static final String WE_PAY = "1";
    public static final String OTHER_PAY = "0";

    public static int WEIGHTMAX = 1000;


    public static final String UNIT_T = "吨";
    public static final String UNIT_KG = "千克";
    public static final String UNIT_G = "克";

    public static final String EntCode = "YFWENTCODE";
    public static final int FREE_BITCION = 128;

    public static final float half = 0.5f;
    public static final int baseFee = 5000;

    public static final String WEIXIN_BROWSER = "0";

    public static final String NOT_WEIXIN_BROWSER = "1";

    public static final String FAIL = "FAIL";

    public static final String SYSTEM = "SYSTEM";


    public static final String ADMIN_ACCT_NO = "";

    public static final String Default_DIS_ENTNAME = "易废网处置旗舰店";

    public static final String SYS_INQUIRY_REMARK = "系统报价，仅作参考";

    public static final Double MAX_AMOUNT = 3d;

    public static final String COMMA = ",";

    public static final String KEYWORD = "1";

    public static final String NONE_KEYWORD = "0";

    public static final String CARBON_KEYWORD = "活性炭,活性碳";

    public static final int DEFAULT_PAGESIZE = 10;


    public static final String TRUE = "true";

    public static final String FALSE = "false";


}
