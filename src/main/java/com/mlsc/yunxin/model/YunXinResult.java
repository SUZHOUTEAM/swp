package com.mlsc.yunxin.model;

/**
 * Created by yinxl on 2017/7/12.
 */
public class YunXinResult {
    private String code;
    private String clientType;
    private String clientIp;
    private String accid;
    private String eventType;
    private String convType;
    private String resendFlag;
    private String fromAccount;
    private String to;

    public String getConvType() {
        return convType;
    }

    public void setConvType(String convType) {
        this.convType = convType;
    }

    public String getResendFlag() {
        return resendFlag;
    }

    public void setResendFlag(String resendFlag) {
        this.resendFlag = resendFlag;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }


    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public String toString() {
        return "YunXinResult{" +
                "code='" + code + '\'' +
                ", clientType='" + clientType + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", accid='" + accid + '\'' +
                ", eventType='" + eventType + '\'' +
                ", convType='" + convType + '\'' +
                ", resendFlag='" + resendFlag + '\'' +
                ", fromAccount='" + fromAccount + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
