package com.mlsc.common.util;



/**
 * 支付宝支付配置文件
 */
public class AlipayConfig {
    // 1.商户appid
    public static String APPID = "2018050760154004";

    // 2.私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDH46PmZd281NTWeD93C7cF4IGLwoKCO5WNx3kNcGFTeELbE2ddnOtO9hb9QOtvqlSuYWGrLiylK/kz4a8QoK18SP9NKQcHgpNP1XTjdqHjaaKCJr5j1w3Zozgjc3M1N7L2ot3wuK9/jCohSokYxg04aY/g183E1BSZycgrgI3s9v+lJdnhVrBZIqEFMOktmCmR6g4s8XKxGOjWHhL4xbeRUuLOVBTQZYVqhjPSvt/AI6ZscLhuDI28t606rYSYcIvlJnY9K2wDMbai9Oze8qFlIPqRu3J5ewzuHIMqWGKn7j1KiQBVUNHXYmGPsVAm/ai6APJ1zWayWcYI+sCquyXBAgMBAAECggEAHpnR8KatoIfwoXi9NotblJG/I1c+xZM7nP3poU4QCEhD6+f3jhREzczMzi6PHIQ1/JA59IvYF904hGHLfD8s7/b45qYeRl8OEE28XEpAFGGKmGQ4B27X1vau1HaLL518T1e1pHGRpHQfywLvJBTF11oJxcEj5jsVgEms1EQU9i7ArSPB9rPmpOh6lTcKs4mnOu7JmcjO+3D9fYyqSDV4fY/CcKJ/BmiZ4VLahBBcjbOuX52B5RAFozy+diHK1jTpR5Z7AItQrwQjwfEMESuRLbeAcPa76UhSbteZ5Vj4pV+7DxUzi0YiFz91TDiV9cIOLyuxm6j3UVonHJLwqku5wQKBgQDu0wcvxb+33n6zu5/bnR24GkOH1dHo1G0Xoug9cTFFGRGSbEaFkgkBELLCtIeyUOH7A8Qm9uE+OCBK8gK7+UQfElAdYBPQWC5Z/J+VpnhSs4kILmBiwBX6rBXJy1pQeA99yb/e9kfbZSMAxXr+stiAKHsXH2EA/FMd+86/mKhTaQKBgQDWQ8gWNNqUIVuAI3spnfEL7NGOagukZF1orG6PXUfu1TpzQMgw1mtpLbC+lgp1Qov+wu0vzVAJFnSWixqRtjiB1ftbAZ9sQE+Bv2UVb3K4Zf+WhPSeYfI7Ypvr8dbaeimZsYOihSNpyyFvI1D6Qed2dmCO7yv++e/icEYgUklsmQKBgQDIOdJ/odn/bKFrTHVUqR/+dr6BRqmq+1XPCDyPLrQxCzhpOy/Q0kOMShSlGrfdruQXD+xHApqLEIE6dDYIpI4RQsg6KI/UD+okdaDR1XRxWaYcXs1eXU7QD7Bgs4Wvi0jpm9iqNG7AKMW04twgZ48Nu2KfWyZ3rhR9QdzvUyxkaQKBgQCiyQZ4EJnMFvfMh6hn+u4KsFaZi2ni8Lmi7/Zjh2xCQM/ShnJR4GXkl+kDMz0oz3FLEc9gFO1sqhnT0kVSVQ3jXq5D+xyjAs0k90OWIIEZJa0kL0kmF6YQhh+OB8PDYzK96Qbob5xzIPf61usL5wwyjUdY8M8NPdiJweEVZ08PaQKBgDK2VnFOF7t8DCAJma6c90oqTvEjklFMdbxszPmxQcreLyhZvVtL5eaJqKl0YJjKMCMuh8ASElJItm4u8y50AKFo9dn2RTSsCtVdAQB4xNWs6y9/VTB7TuLqxuWQE7SVmK91Oj56WDfWXD8keZU5O0tu6Od3UqPhXNbSLfjdOu7W";

    // 3.支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiJe8nGlGlK60ZDGG6SoKgY8VdWWyfDCLbBuysDxjbnMAYxTLFRYDdUQ/4bCyrt1sh0FiMyDijQpWz+Rocti40ywoOvznjpfeUqH5OY4ZkNKev1j4EpHRBwvhk3skQQOCzJYfoVLhDR7RSncuABhaqS6170yN9GHyG/LUco9nw80eDC5bL63fl0RclsZefhV4EC7pE9B3BEctuQ+/5nHxDPqFcADrv/pzh27OnfAsOb1isWH3C6G0mNRgBNeRqHig6OxNh+fwxSNaqpN2sKFkm9vdIcNu3TAwsErtkNJn6cyBFh5MvIBZ1Muint58D+LvJgbH6O4+3XFsPmCMeijTAQIDAQAB";

    // 4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://www.yifeiwang.com/swp/entRecharge/notifyUrl";

    // 5.页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://www.yifeiwang.com/swp/common/paySuccess.jsp";

    // 5.页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url_mobile = "http://www.yifeiwang.com/swp/common/paySuccess_mobile.jsp";

    // 6.请求网关地址
    public static String URL = "https://openapi.alipay.com/gateway.do";

    // 7.编码
    public static String CHARSET = "UTF-8";

    // 8.返回格式
    public static String FORMAT = "json";

    // 9.加密类型
    public static String SIGNTYPE = "RSA2";


    public static String sellerId = "seller_id";

}
