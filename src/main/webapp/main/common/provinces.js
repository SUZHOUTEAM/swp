/**
 * Created by yinxl on 2017/12/26.
 */
var provinces=[{"code":"","value":"全国"},{"code":"11","value":"北京市"},{"code":"12","value":"天津市"},{"code":"13","value":"河北省"},{"code":"14","value":"山西省"},{"code":"15","value":"内蒙古"},{"code":"21","value":"辽宁省"},{"code":"22","value":"吉林省"},{"code":"23","value":"黑龙江省"},{"code":"31","value":"上海市"},{"code":"32","value":"江苏省"},{"code":"33","value":"浙江省"},{"code":"34","value":"安徽省"},{"code":"35","value":"福建省"},{"code":"36","value":"江西省"},{"code":"37","value":"山东省"},{"code":"41","value":"河南省"},{"code":"42","value":"湖北省"},{"code":"43","value":"湖南省"},{"code":"44","value":"广东省"},{"code":"45","value":"广西"},{"code":"46","value":"海南省"},{"code":"50","value":"重庆市"},{"code":"51","value":"四川省"},{"code":"52","value":"贵州省"},{"code":"53","value":"云南省"},{"code":"54","value":"西藏"},{"code":"61","value":"陕西省"},{"code":"62","value":"甘肃省"},{"code":"63","value":"青海省"},{"code":"64","value":"宁夏"},{"code":"65","value":"新疆"}];
var provinces_mobile=[{"code":"32","text":"江苏省"},{"code":"13","text":"河北省"},{"code":"","text":"全国","disabled":true},{"code":"11","text":"北京市","disabled":true},{"code":"12","text":"天津市","disabled":true},{"code":"14","text":"山西省","disabled":true},{"code":"15","text":"内蒙古自治区","disabled":true},{"code":"21","text":"辽宁省","disabled":true},{"code":"22","text":"吉林省","disabled":true},{"code":"23","text":"黑龙江省","disabled":true},{"code":"31","text":"上海市","disabled":true},{"code":"33","text":"浙江省","disabled":true},{"code":"34","text":"安徽省","disabled":true},{"code":"35","text":"福建省","disabled":true},{"code":"36","text":"江西省","disabled":true},{"code":"37","text":"山东省","disabled":true},{"code":"41","text":"河南省","disabled":true},{"code":"42","text":"湖北省","disabled":true},{"code":"43","text":"湖南省","disabled":true},{"code":"44","text":"广东省","disabled":true},{"code":"45","text":"广西壮族自治区","disabled":true},{"code":"46","text":"海南省","disabled":true},{"code":"50","text":"重庆市","disabled":true},{"code":"51","text":"四川省","disabled":true},{"code":"52","text":"贵州省","disabled":true},{"code":"53","text":"云南省","disabled":true},{"code":"54","text":"西藏自治区","disabled":true},{"code":"61","text":"陕西省","disabled":true},{"code":"62","text":"甘肃省","disabled":true},{"code":"63","text":"青海省","disabled":true},{"code":"64","text":"宁夏回族自治区","disabled":true},{"code":"65","text":"新疆维吾尔自治区","disabled":true}];
var provinceObj={11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", "": "全国"};
var province= {"":"全国","11":"北京市","12":"天津市","13":"河北省","14":"山西省","15":"内蒙古自治区","21":"辽宁省","22":"吉林省","23":"黑龙江省","31":"上海市","32":"江苏省","33":"浙江省","34":"安徽省","35":"福建省","36":"江西省","37":"山东省","41":"河南省","42":"湖北省","43":"湖南省","44":"广东省","45":"广西壮族自治区","46":"海南省","50":"重庆市","51":"四川省","52":"贵州省","53":"云南省","54":"西藏自治区","61":"陕西省","62":"甘肃省","63":"青海省","64":"宁夏回族自治区","65":"新疆维吾尔自治区"};
var serviceProvince= {110000: "北京市", 120000: "天津市", 130000: "河北省", 140000: "山西省", 150000: "内蒙古自治区", 210000: "辽宁省", 220000: "吉林省", 230000: "黑龙江省", 310000: "上海市", 320000: "江苏省", 330000: "浙江省", 340000: "安徽省", 350000: "福建省", 360000: "江西省", 370000: "山东省", 410000: "河南省", 420000: "湖北省", 430000: "湖南省", 440000: "广东省", 450000: "广西壮族自治区", 460000: "海南省", 500000: "重庆市", 510000: "四川省", 520000: "贵州省", 530000: "云南省", 540000: "西藏自治区", 610000: "陕西省", 620000: "甘肃省", 630000: "青海省", 640000: "宁夏回族自治区", 650000: "新疆维吾尔自治区"};
var provinceCode=["32","13","35","11","12","14","15","21","22","23","31","33","34","36","37","41","42","43","44","45","46","50","51","52","53","54","61","62","63","64","65",""];