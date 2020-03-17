package com.jhmis.modules.wechat.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WxPending {
	private static final long serialVersionUID = 1L;
	private String id;
	private String userId;
	private String type;//待处理事项类型。0-会议审核    1-会议提醒    2-好友提醒
	private String typeId;//若type=0或1,typeId为会议meetingId;若type=2,typeId为好友id
	private String status;//状态。0-未查看    1-已查看
	private String friendName;//XX申请添加好友
	private String signStatus;//会议申请状态。1-审核通过; 2-审核未通过。若type=2,则存放好友头像url
	private String meetingName;//会议名称
	private String meetingSite;//会议地点
	private Date meetingStartTime;//会议开始时间
	private Date time;//待处理事项产生时间
	public WxPending() {
		super();
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public String getMeetingName() {
		return meetingName;
	}
	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	public String getMeetingSite() {
		return meetingSite;
	}
	public void setMeetingSite(String meetingSite) {
		this.meetingSite = meetingSite;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMeetingStartTime() {
		return meetingStartTime;
	}
	public void setMeetingStartTime(Date meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	
	
}
