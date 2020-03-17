/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 群聊信息Entity
 * 
 * @author hdx
 * @version 2018-12-16
 */
public class WxGroupMessage extends DataEntity<WxGroupMessage> {

	private static final long serialVersionUID = 1L;
	private String userId; // 企业购会员id
	private String groupId; // 群组id
	private String content; // 内容
	private String isDel; // 是否删除0 否 1是
	private String isRead; // 是否已读 0 未读 1 已读
	private Date sendTime; // 发送时间
	private String isBack; // 是否撤回 0否 1是
	private String img; // img
	private String type; // 消息类型
	private String shareUrl;
	private String shareSummary;

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getShareSummary() {
		return shareSummary;
	}

	public void setShareSummary(String shareSummary) {
		this.shareSummary = shareSummary;
	}

	private String fileName; // 文件名字
	private String userName;
	private String purchaserId;
	private String companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public WxGroupMessage() {
		super();
	}

	public WxGroupMessage(String id) {
		super(id);
	}

	@ExcelField(title = "企业购会员id", fieldType = String.class, value = "", align = 2, sort = 1)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@ExcelField(title = "群组id", align = 2, sort = 2)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@ExcelField(title = "内容", align = 2, sort = 3)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ExcelField(title = "是否删除0 否  1是", align = 2, sort = 4)
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@ExcelField(title = "是否已读 0 未读 1 已读", align = 2, sort = 5)
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "发送时间", align = 2, sort = 6)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@ExcelField(title = "是否撤回 0否 1是", align = 2, sort = 7)
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	@ExcelField(title = "消息类型  0文字 1图片 2文件", align = 2, sort = 8)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ExcelField(title = "文件名称", align = 2, sort = 9)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}