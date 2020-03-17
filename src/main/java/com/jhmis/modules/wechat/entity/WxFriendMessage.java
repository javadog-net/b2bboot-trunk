/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 聊天信息表Entity
 * @author lvyangzhuo
 * @version 2018-11-22
 */
public class WxFriendMessage extends DataEntity<WxFriendMessage> {
	
	private static final long serialVersionUID = 1L;
	private String fromUserId;		// from_user_id
	private String toUserId;		// to_user_id
	private String content;		// content
	private Date sendTime;		// send_time
	private String isRead;		// is_read
	private String isDel;		// is_del
	private String isBack;		// is_back
	private String img;		// messageType
	private String friendId;		// friend_id
	private String nickName;		// nick_name
	private String avatarUrl;		// avatarUrl
	private String type;    //消息类型
	private String fileName; //文件名字
	private String shareUrl;//分享链接
	private String shareSummary;//分享内容的概要

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

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public WxFriendMessage() {
		super();
	}

	public WxFriendMessage(String id){
		super(id);
	}

	@ExcelField(title="from_user_id", align=2, sort=1)
	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	@ExcelField(title="to_user_id", align=2, sort=2)
	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	
	@ExcelField(title="content", align=2, sort=3)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="send_time", align=2, sort=4)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@ExcelField(title="is_read", align=2, sort=5)
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	@ExcelField(title="is_del", align=2, sort=6)
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	@ExcelField(title="is_back", align=2, sort=7)
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	@ExcelField(title="type", align=2, sort=8)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="fileName", align=2, sort=9)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}