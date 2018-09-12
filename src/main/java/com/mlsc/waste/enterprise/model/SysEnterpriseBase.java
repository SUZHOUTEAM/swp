package com.mlsc.waste.enterprise.model;

import com.mlsc.epdp.common.annotation.Column;
import com.mlsc.epdp.common.annotation.Id;
import com.mlsc.epdp.common.annotation.Table;
import com.mlsc.epdp.common.base.entity.Entity;

/**
 * Created by zhanghj on 2017/7/13.
 */

@Table(SysEnterpriseBase.TABLE)
public class SysEnterpriseBase extends Entity {

    private static final long serialVersionUID = -1581749224452039840L;

    public static final String TABLE = "sys_enterprise_base";
    public static final String COL_ENTID = "ENTID";
    public static final String COL_ENTNAME = "ENTNAME";
    public static final String COL_SHORTNAME = "SHORTNAME";
    public static final String COL_NAMESPELLING = "NAMESPELLING";
    public static final String COL_ENTCODE = "ENTCODE";
    public static final String COL_LEGALNAME = "LEGALNAME";
    public static final String COL_CONTACTS = "CONTACTS";
    public static final String COL_CONTACTSTEL = "CONTACTSTEL";
    public static final String COL_CONTACTSEMAIL = "CONTACTSEMAIL";
    public static final String COL_FAX = "FAX";
    public static final String COL_ENTADDRESS = "ENTADDRESS";
    public static final String COL_ZIPCODE = "ZIPCODE";
    public static final String COL_ENTSTATUS = "ENTSTATUS";
    public static final String COL_CREATERID = "CREATERID";
    public static final String COL_CREATETIME = "CREATETIME";
    public static final String COL_UPDATERID = "UPDATERID";
    public static final String COL_UPDATETIME = "UPDATETIME";
    public static final String COL_ENTTYPE = "ENTTYPE";
    public static final String COL_POSX = "POSX";
    public static final String COL_POSY = "POSY";
    public static final String COL_CANTONCODE = "CANTONCODE";
    public static final String COL_RESPONSIBLEAREA = "RESPONSIBLEAREA";

    @Id
    @Column(COL_ENTID)
    private String entId;
    @Column(COL_ENTNAME)
    private String entName;
    @Column(COL_SHORTNAME)
    private String shortName;
    @Column(COL_NAMESPELLING)
    private String nameSpelling;
    @Column(COL_ENTCODE)
    private String entCode;
    @Column(COL_CONTACTS)
    private String contacts;
    @Column(COL_CONTACTSTEL)
    private String contactsTel;
    @Column(COL_CONTACTSEMAIL)
    private String contactsEMail;
    @Column(COL_FAX)
    private String fax;
    @Column(COL_ENTADDRESS)
    private String entAddress;
    @Column(COL_ZIPCODE)
    private String zipCode;
    @Column(COL_LEGALNAME)
    private String legalName;
    @Column(COL_ENTSTATUS)
    private Integer entStatus;
    @Column(COL_CREATERID)
    private String createrID;
    @Column(COL_CREATETIME)
    private String createTime;
    @Column(COL_UPDATERID)
    private String updaterID;
    @Column(COL_UPDATETIME)
    private String updateTime;
    @Column(COL_ENTTYPE)
    private String entType;
    @Column(COL_POSX)
    private Double posx;
    @Column(COL_POSY)
    private Double posy;
    @Column(COL_CANTONCODE)
    private String cantonCode;
    @Column(COL_RESPONSIBLEAREA)
    private String responsibleArea;

    public String getEntId() {
        return this.entId;
    }

    public void setEntId(String entId) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.entId = entId;
    }

    public String getEntName() {
        return this.entName;
    }

    public void setEntName(String entName) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.entName = entName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.shortName = shortName;
    }

    public String getNameSpelling() {
        return this.nameSpelling;
    }

    public void setNameSpelling(String nameSpelling) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.nameSpelling = nameSpelling;
    }

    public String getEntCode() {
        return this.entCode;
    }

    public void setEntCode(String entCode) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.entCode = entCode;
    }

    public String getContacts() {
        return this.contacts;
    }

    public void setContacts(String contacts) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.contacts = contacts;
    }

    public String getContactsTel() {
        return this.contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.contactsTel = contactsTel;
    }

    public String getContactsEMail() {
        return this.contactsEMail;
    }

    public void setContactsEMail(String contactsEMail) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.contactsEMail = contactsEMail;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.fax = fax;
    }

    public String getEntAddress() {
        return this.entAddress;
    }

    public void setEntAddress(String entAddress) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.entAddress = entAddress;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.zipCode = zipCode;
    }

    public String getLegalName() {
        return this.legalName;
    }

    public void setLegalName(String legalName) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.legalName = legalName;
    }

    public Integer getEntStatus() {
        return this.entStatus;
    }

    public void setEntStatus(Integer entStatus) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.entStatus = entStatus;
    }

    public String getCreaterID() {
        return this.createrID;
    }

    public void setCreaterID(String createrID) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.createrID = createrID;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.createTime = createTime;
    }

    public String getUpdaterID() {
        return this.updaterID;
    }

    public void setUpdaterID(String updaterID) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.updaterID = updaterID;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.updateTime = updateTime;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.entType = entType;
    }

    public Double getPosx() {
        return posx;
    }

    public void setPosx(Double posx) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.posx = posx;
    }

    public Double getPosy() {
        return posy;
    }

    public void setPosy(Double posy) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.posy = posy;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.cantonCode = cantonCode;
    }

    public String getResponsibleArea() {
        return responsibleArea;
    }

    public void setResponsibleArea(String responsibleArea) {
        this.changeValue(Thread.currentThread().getStackTrace()[1].getMethodName());
        this.responsibleArea = responsibleArea;
    }
}
