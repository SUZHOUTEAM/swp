package com.mlsc.waste.wastecircle.model;

import com.mlsc.epdp.common.annotation.Column;

/**
 * Created by Administrator on 2017/7/5 0005.
 */
public class OfflineMessageVo extends OfflineMessage {

 /*    <th>留言人</th>
    <th>留言的企业</th>
    <th>被留言的企业</th>
    <th>留言内容</th>
    <th>留言时间</th>
    <th>状态</th>*/

    @Column("fromUserName")
    private String fromUserName;//留言的人

    @Column("fromPhoneNum")
    private String fromPhoneNum;//留言人电话

    @Column("fromEntName")
    private String fromEntName;//留言的企业

    @Column("toEntName")
    private String toEntName;//被留言的企业


    @Column("flag")
    private String flag;//被留言的企业

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromPhoneNum() {
        return fromPhoneNum;
    }

    public void setFromPhoneNum(String fromPhoneNum) {
        this.fromPhoneNum = fromPhoneNum;
    }

    public String getFromEntName() {
        return fromEntName;
    }

    public void setFromEntName(String fromEntName) {
        this.fromEntName = fromEntName;
    }

    public String getToEntName() {
        return toEntName;
    }

    public void setToEntName(String toEntName) {
        this.toEntName = toEntName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
