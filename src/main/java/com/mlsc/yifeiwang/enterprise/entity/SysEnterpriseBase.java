package com.mlsc.yifeiwang.enterprise.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 企业基本信息
 * </p>
 *
 * @author caoyy
 * @since 2017-11-24
 */
@TableName("sys_enterprise_base")
public class SysEnterpriseBase extends Model<SysEnterpriseBase> {

	private static final long serialVersionUID = 1L;

	/**
	 * 企业标识
	 */
	private String entId;
	/**
	 * 企业名称
	 */
	private String entName;
	/**
	 * 企业简称
	 */
	private String shortName;
	/**
	 * 企业拼音简称
	 */
	private String nameSpelling;
	/**
	 * 企业代码
	 */
	private String entCode;
	/**
	 * legal entities
	 */
	private String legalName;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 电话
	 */
	private String contactsTel;
	/**
	 * 联系人电子邮件
	 */
	private String contactsEMail;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 邮政编码
	 */
	private String zipCode;
	/**
	 * 通讯地址
	 */
	private String entAddress;
	/**
	 * 企业状态
	 */
	private Integer entStatus;
	/**
	 * 行业
	 */
	private String industry;
	/**
	 * 添加人
	 */
	private String CreaterID;
	/**
	 * 添加时间
	 */
	private Date CreateTime;
	/**
	 * 更新人
	 */
	private String UpdaterID;
	/**
	 * 更新时间
	 */
	private Date UpdateTime;
	private String entType;
	private Double posx;
	private Double posy;
	private String summary;
	private String cantonCode;
	private String salesNote;
	private String otherNote;
	private String paymentRule;
	private Integer weight;
	private String responsibleArea;
	private String externalId;
	private String resource;

	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getNameSpelling() {
		return nameSpelling;
	}

	public void setNameSpelling(String nameSpelling) {
		this.nameSpelling = nameSpelling;
	}

	public String getEntCode() {
		return entCode;
	}

	public void setEntCode(String entCode) {
		this.entCode = entCode;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getContactsEMail() {
		return contactsEMail;
	}

	public void setContactsEMail(String contactsEMail) {
		this.contactsEMail = contactsEMail;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEntAddress() {
		return entAddress;
	}

	public void setEntAddress(String entAddress) {
		this.entAddress = entAddress;
	}

	public Integer getEntStatus() {
		return entStatus;
	}

	public void setEntStatus(Integer entStatus) {
		this.entStatus = entStatus;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCreaterID() {
		return CreaterID;
	}

	public void setCreaterID(String CreaterID) {
		this.CreaterID = CreaterID;
	}

	public Date getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Date CreateTime) {
		this.CreateTime = CreateTime;
	}

	public String getUpdaterID() {
		return UpdaterID;
	}

	public void setUpdaterID(String UpdaterID) {
		this.UpdaterID = UpdaterID;
	}

	public Date getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(Date UpdateTime) {
		this.UpdateTime = UpdateTime;
	}

	public String getEntType() {
		return entType;
	}

	public void setEntType(String entType) {
		this.entType = entType;
	}

	public Double getPosx() {
		return posx;
	}

	public void setPosx(Double posx) {
		this.posx = posx;
	}

	public Double getPosy() {
		return posy;
	}

	public void setPosy(Double posy) {
		this.posy = posy;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCantonCode() {
		return cantonCode;
	}

	public void setCantonCode(String cantonCode) {
		this.cantonCode = cantonCode;
	}

	public String getSalesNote() {
		return salesNote;
	}

	public void setSalesNote(String salesNote) {
		this.salesNote = salesNote;
	}

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}

	public String getPaymentRule() {
		return paymentRule;
	}

	public void setPaymentRule(String paymentRule) {
		this.paymentRule = paymentRule;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getResponsibleArea() {
		return responsibleArea;
	}

	public void setResponsibleArea(String responsibleArea) {
		this.responsibleArea = responsibleArea;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Override
	protected Serializable pkVal() {
		return this.entId;
	}

	@Override
	public String toString() {
		return "SysEnterpriseBase{" +
				"entId=" + entId +
				", entName=" + entName +
				", shortName=" + shortName +
				", nameSpelling=" + nameSpelling +
				", entCode=" + entCode +
				", legalName=" + legalName +
				", contacts=" + contacts +
				", contactsTel=" + contactsTel +
				", contactsEMail=" + contactsEMail +
				", fax=" + fax +
				", zipCode=" + zipCode +
				", entAddress=" + entAddress +
				", entStatus=" + entStatus +
				", CreaterID=" + CreaterID +
				", CreateTime=" + CreateTime +
				", UpdaterID=" + UpdaterID +
				", UpdateTime=" + UpdateTime +
				", entType=" + entType +
				", posx=" + posx +
				", posy=" + posy +
				", summary=" + summary +
				", cantonCode=" + cantonCode +
				", salesNote=" + salesNote +
				", otherNote=" + otherNote +
				", paymentRule=" + paymentRule +
				", weight=" + weight +
				", responsibleArea=" + responsibleArea +
				"}";
	}
}
