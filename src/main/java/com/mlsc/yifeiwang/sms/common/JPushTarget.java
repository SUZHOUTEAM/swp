package com.mlsc.yifeiwang.sms.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghj on 2017/7/21.
 */
public enum JPushTarget {

    DISPOSION("DISPOSION","处置端APP","c6621210ba100807de8db7b1","143b05fcc438bf6ff3f027a1"),
    DIS_FACILITATOR("DIS_FACILITATOR","代理处置商端APP","c6621210ba100807de8db7b1","143b05fcc438bf6ff3f027a1"),
    PRODUCTION("PRODUCTION","产废端APP","8319b0892f31f162cd418cfa","e5ce931d25aae74814708a2a"),
    ALL("ALL","所有APP","","");

    private String code;

    private String desc;

    private String masterSecret;

    private String appkey;

    JPushTarget(String code, String desc, String masterSecret, String appKey) {
        this.code = code;
        this.desc = desc;
        this.masterSecret = masterSecret;
        this.appkey = appKey;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static List<JPushTarget> listNeedSendJpushTarget(JPushTarget jPushTarget){
        List<JPushTarget> list = new ArrayList<>();
        if(JPushTarget.ALL.equals(jPushTarget)){
            for(JPushTarget target : JPushTarget.values()){
                if(!JPushTarget.ALL.equals(target)){
                    list.add(target);
                }
            }
        }else{
            list.add(jPushTarget);
        }
        return list;
    }


}
