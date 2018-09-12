package com.mlsc.yifeiwang.actionrecord.modal;

/**
 * Created by yinxl on 2017/7/7.
 */
public class ActionVO {
    private String keyWord;
    private String enterpriseId;
    private String cip;
    private String cid;
    private String cname;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "ActionVO{" +
                "keyWord='" + keyWord + '\'' +
                ", enterpriseId='" + enterpriseId + '\'' +
                ", cip='" + cip + '\'' +
                ", cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }
}
