package com.jhmis.modules.wechat.entity;

public class WxMeetingScheduleCopy {
	
	private String time;		// 活动时间
	private String content;		// 活动内容
	private String type;		// 活动类型
	public WxMeetingScheduleCopy(String time, String content, String type) {
		super();
		this.time = time;
		this.content = content;
		this.type = type;
	}
	public WxMeetingScheduleCopy() {
		super();
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
