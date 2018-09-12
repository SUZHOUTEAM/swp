package com.mlsc.yifeiwang.sms.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhanghj
 * @since 2017-08-10
 */
@TableName("sys_notice")
public class SysNotice extends Model<SysNotice> {

    private static final long serialVersionUID = 1L;

	private String id;
	@TableField("notice_type")
	private String noticeType;
	@TableField("notice_category")
	private String noticeCategory;
	@TableField("rel_id")
	private String relId;
	@TableField("sender_id")
	private String senderId;
	@TableField("receiver_id")
	private String receiverId;
	@TableField("receiver_type")
	private String receiverType;
	@TableField("notice_title")
	private String noticeTitle;
	@TableField("notice_content")
	private String noticeContent;
	@TableField("has_send_msg")
	private String hasSendMsg;
	@TableField("has_read")
	private String hasRead;
	@TableField("create_by")
	private String createBy;
	@TableField("create_time")
	private Date createTime;
	@TableField("edit_by")
	private String editBy;
	@TableField("edit_time")
	private Date editTime;
	private String valid;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeCategory() {
		return noticeCategory;
	}

	public void setNoticeCategory(String noticeCategory) {
		this.noticeCategory = noticeCategory;
	}

	public String getRelId() {
		return relId;
	}

	public void setRelId(String relId) {
		this.relId = relId;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getHasSendMsg() {
		return hasSendMsg;
	}

	public void setHasSendMsg(String hasSendMsg) {
		this.hasSendMsg = hasSendMsg;
	}

	public String getHasRead() {
		return hasRead;
	}

	public void setHasRead(String hasRead) {
		this.hasRead = hasRead;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEditBy() {
		return editBy;
	}

	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "SysNotice{" +
			"id=" + id +
			", noticeType=" + noticeType +
			", noticeCategory=" + noticeCategory +
			", relId=" + relId +
			", senderId=" + senderId +
			", receiverId=" + receiverId +
			", receiverType=" + receiverType +
			", noticeTitle=" + noticeTitle +
			", noticeContent=" + noticeContent +
			", hasSendMsg=" + hasSendMsg +
			", hasRead=" + hasRead +
			", createBy=" + createBy +
			", createTime=" + createTime +
			", editBy=" + editBy +
			", editTime=" + editTime +
			", valid=" + valid +
			"}";
	}
}
