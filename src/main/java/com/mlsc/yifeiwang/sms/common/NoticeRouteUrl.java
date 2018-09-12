package com.mlsc.yifeiwang.sms.common;

import com.mlsc.common.constant.BaseConstant;

/**
 * Created by zhanghj on 2017/8/15.
 */
public enum NoticeRouteUrl {

    MY_PUBLISH("/wastecircle/publishList.htm","BaseScreen,cart"),MY_ORDER("/entOrders/list.htm","UserOrderList"),
    MY_INQUIRY
            ("/entInquiry/list.htm",""),RESOURCE_POOL("/wastecircle/init.htm","BaseScreen,home"),
    ACTIVITY_INFO("","");

    private String webUrl;

    private String appUrl;

    public String getUrlByClientType(String clientType){
        if(BaseConstant.APP_CLIENT.equals(clientType)){
            return appUrl;
        }else{
            return webUrl;
        }
    }

    NoticeRouteUrl(String webUrl, String appUrl){
        this.webUrl = webUrl;
        this.appUrl = appUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
