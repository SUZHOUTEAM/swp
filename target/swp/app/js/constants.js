/*!
 * 
 * 新固废前台定义的常数
 */

// ---------------密码手机号码正则验证的常量START---------------------------
var passwordReg1 = /(^.{6,18}$)/;// 长度在6到18位之间
var passwordMsg1 = "密码长度在6到18位之间。";
var passwordReg2 = /(^[0-9]+$)/;// 不能全是数字
var passwordMsg2 = "密码不能全是数字。";
var passwordReg3 = /(^[A-Za-z]+$)/;// 不能全是字母
var passwordMsg3 = "密码不能全是字母。";
var passwordReg4 = /^[A-Za-z0-9!@#$%*]+$/;// 允许含有的字符
var passwordMsg4 = "不允许出现“!@#$%*”以外的特殊字符";

var phoneNumReg1= /^1/;// 只允许数字
var phoneNumMsg1 = "手机号码只能以数字1开头";
var phoneNumReg2= /^1[0-9]{0,10}$/;// 只允许数字
var phoneNumMsg2 = "手机号码只能是数字";
var phoneNumReg3= /^1[0-9]{10}$/;//
var phoneNumMsg3 = "手机号码必须是11位数字";
/**
 * 图片上传相关参数
 */
var APPKEY='5da5441f62e48aedc7a3853ffc75c2db';
var PRODID='gf';
var UPLOAD_ACTION='/upload';
var VIEW_ACTION='/view';
var REMOVE_ACTION='/remove';
var IMGURL='/service/sys/file/upload';
var isRealUrl=true;//window.location.href.indexOf("121.40.113.7") > -1||window.location.href.indexOf("yifeiwang.com") > -1;
var HOME_PRE = isRealUrl?"http://yifeiwang.com/":"http://192.168.252.130:18888/";// 首页数据服务器
var IMG_DETAIN =HOME_PRE+"mlsc-webupload";// 图片服务器地址
var IMG_VIEW_URL = IMG_DETAIN+IMGURL+VIEW_ACTION+"?appKey="+APPKEY+"&prodID="+PRODID;// 图片查看服务器地址
// var IMGVIEWURL=isRealUrl?'http://yifeiwang.com:4000/webupload/':(IMG_VIEW_URL+'&fileID=');
var IMGVIEWURL=IMG_VIEW_URL+'&fileID=';
var IMG_PREV=IMG_DETAIN+IMGURL;
var FILETYPE=['image/jpeg','image/png','image/gif'];
var TYPE_ERROR_MSG='只支持jpg,png,gif图片格式上传';
var statusClass={'接受报价':'ACCEPT','已成交':'ACCEPT','待确认':'SUBMIT','已谢绝':'REFUSED'};//询价单的状态
var recordStatusClass={'APPROVED':'审核通过','SUBMIT':'待审核','REFUSED':'审核退回'};//询价单的状态
var orderStatusClass={'进行中':'SUBMIT','已完成':'ACCEPT'};//询价单的状态
var COMMENT={'5':'好评','3':'中评','1':'差评'};
var ACTIONS={'add':'新增','edit':'编辑','view':'查看'};
var ACTIVITYSTATUS=[{code:'DRAFT',value:'未开始'},{code:'SUBMIT',value:'进行中'},{code:'OVER',value:'已结束'}];
var INQUIRYSTATUS={'SUBMIT':'已询价','REFUSED':'已谢绝','DONE':'已成交','RELEASED':'待处理'};
var SIGNTYPE={'SAMPLING':'取样','SAMPLE':'样品','TAGING':'自由','CONTRACT':'合同'};
var TAGSTATUS={'TAGGING':'自由标记','SAMPLING':'取样中','SAMPLED':'已取样','DELIVERED_LAB':'已送实验室','QUALIFIED':'合格','UNQUALIFIED':'不合格','DRAFTCONTRACT':'合同已拟定','SIGNCONTRACT':'合同已签定','DELIVERYCONTRACT':'合同已寄出','TERMINATE':'终止','PRICE':'报价'};
var priorityList={'URGENT':'紧急','HIGH':'高','GENERAL':'一般','NONE':'暂不处理'};
var facilitatorEnt=isRealUrl?{'entId':'2548143641135104','entName':'易废网江苏自营店'}:{'entId':'2527190461532160','entName':'易废网测试服务网点'};
var packageList=[{code:'GOLDEN',value:'公爵套餐',price:20000},{code:'SLIVER',value:'伯爵套餐',price:10000},{code:'CUPRUM',value:'候爵套餐',price:6000}];
//---------------密码手机号码正则验证的常量END---------------------------
//---------------验证码间隔时间的常量START---------------------------
var second_constants = 120;// 120秒倒计时
var timer;
//---------------验证码间隔时间的常量END---------------------------

//---------------弹出确认框需要的参数---------------------------
var confirm_shade = 0;
var confirm_offset = '200px';
var confirm_skin = 'confirm-class';
//---------------金额格式化---------------------------
var numReg = new RegExp(/(,)/g);//金额计算时，去掉【,】
var numReg_format = new RegExp(/(\.000)/g);//金额格式化时，去掉【.000】
var regParseNum = new RegExp(/(?=(?!\b)(\d{3})+$)/g); // 金额用千分位表示
var regNumValidate = new RegExp(/^(([1-9]\d{0,3}|0)(,)?)+(\.\\d{0,3})?$/g); // 金额，单价合法性输入的验证
//金额千分位表示
function parseNum(num){
    var result = "";
    if (num !== "") {
        var numArrar = String(num).split("\.");
        if (numArrar.length == 2) {
            result = String(numArrar[0]).replace(regParseNum, ',') + "." + numArrar[1];
        } else {
            result = String(numArrar[0]).replace(regParseNum, ',');
        }
    }
    return result;
}
//八位码的输入限制
function wasteInputController($selector){
    $selector.keypress(function(event){
        var eventObj = event || e;
        var keyCode = eventObj.keyCode || eventObj.which;
        var $input = $(event.target);
        if (parseInt(keyCode) < 48 || parseInt(keyCode) > 57) {
            return false;
        } else {
            var wasteCode = $input.val();
            if (wasteCode.length == 3 || wasteCode.length == 7) {
                wasteCode = wasteCode + "-";
                $input.val(wasteCode);
            }
        }
    });
}
//根据企业类型显示产废信息和许可内容
function badgeRenderer($enterpriseTypeSelector){
    $enterpriseTypeSelector.each(function() {
        if ($(this).hasClass("PRODUCTION")) {
            $(this).addClass("production-badge");
        } else if ($(this).hasClass("DISPOSITION")) {
            $(this).addClass("disposition-badge");
        } else if ($(this).hasClass("RECYCLING")) {
            $(this).addClass("recycling-badge");
        } else if ($(this).hasClass("IDENTIFICATION")) {
            $(this).addClass("identification-badge");
        } else if ($(this).hasClass("TRANSPORTATION")) {
            $(this).addClass("bg-transportation");
        }
    });
}
//---------------图片类型代码常量START------------------------
var file_type_enterpriseLogo = "a";//企业logo
var file_type_enterpriseBusinessLicense = "b";//企业营业执照 
var file_type_userLogo = "c";//用户头像
var file_type_Licence = "d";//许可证
var file_type_wasteLaboratoryReport = "e";//危废化验单
var file_type_waste = "f";//危废图片
//---------------图片类型代码常量END---------------------------

