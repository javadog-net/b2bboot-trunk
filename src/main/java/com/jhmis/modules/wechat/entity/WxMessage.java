package com.jhmis.modules.wechat.entity;

/**
 * @Author：hdx
 * @Description: 微信聊天信息
 * @Date: Created in 16:49 2018/12/13
 * @Modified By
 */
public class WxMessage {
	private String id;
	private String message;
	private String messageType;
	private String url;
	private String friendId;
	private String userId;
	private String fileName;

	private String shareUrl;// 分享链接
	private String shareSummary;// 分享描述

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

	private String companyName;
	private String userName;

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	private String sendTime;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
