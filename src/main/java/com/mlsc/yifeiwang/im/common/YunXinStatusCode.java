package com.mlsc.yifeiwang.im.common;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by yinxl on 2017/7/4.
 * {"desc":"checksum","code":414}//{"desc":"appkey","code":414}//{"desc":"already register","code":414}
 * {"desc":"CurTime is expired","code":414}
 */
public enum YunXinStatusCode {


    SUCCESS("200", ConstantStatus.SUCCESS), ALREADY_REGISTER_ERROR("414",ConstantStatus.ALREADY_REGISTER),
    CHECKSUM_ERROR("414", ConstantStatus.CHECKSUM), APPKEY_ERROR("414", ConstantStatus.APPKEY),
    CURTIME_EXPIRED_ERROR("414", ConstantStatus.CURTIME_EXPIRED);



    private String code;
    private String desc;

    YunXinStatusCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static YunXinStatusCode getStatusCode(JSONObject jsonObject) {
        if(jsonObject != null){
            String retCode = jsonObject.getString("code");
            String retDesc = jsonObject.getString("desc");
            if (retCode.equalsIgnoreCase(SUCCESS.getCode())) {
                return SUCCESS;
            }
            if (retCode.equalsIgnoreCase("414") && null != retDesc) {
                switch (retDesc.toLowerCase()) {
                    case ConstantStatus.ALREADY_REGISTER:
                        return ALREADY_REGISTER_ERROR;
                    case ConstantStatus.CHECKSUM:
                        return CHECKSUM_ERROR;
                    case ConstantStatus.APPKEY:
                        return APPKEY_ERROR;
                    case ConstantStatus.CURTIME_EXPIRED:
                        return CURTIME_EXPIRED_ERROR;
                }
            }
        }
        return null;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    static class ConstantStatus{
       public static final String ALREADY_REGISTER = "already register";
       public static final String SUCCESS = "success";
       public static final String CHECKSUM = "checksum";
       public static final String APPKEY = "appkey";
       public static final String CURTIME_EXPIRED = "curtime is expired";
    }
}
