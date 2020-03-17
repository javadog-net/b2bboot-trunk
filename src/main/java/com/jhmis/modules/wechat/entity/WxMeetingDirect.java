/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 会议直播表Entity
 * @author lvyangzhuo
 * @version 2018-11-26
 */
public class WxMeetingDirect extends DataEntity<WxMeetingDirect> {
	
	private static final long serialVersionUID = 1L;
	private String meetingId;		// 会议id
	private String userId;		// 企业购会员id
	private Date sendTime;		// 直播内容发送时间
	private String content;		// 内容
	private String imgUrl;		// 图片
	private Date createTime;		// 创建时间
	private String createUser;		// 创建人
	
	public WxMeetingDirect() {
		super();
	}

	public WxMeetingDirect(String id){
		super(id);
	}

	@ExcelField(title="会议id", align=2, sort=1)
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	@ExcelField(title="企业购会员id", align=2, sort=2)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="直播内容发送时间", align=2, sort=3)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@ExcelField(title="内容", align=2, sort=4)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="图片", align=2, sort=5)
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=7)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ExcelField(title="创建人", align=2, sort=8)
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
}