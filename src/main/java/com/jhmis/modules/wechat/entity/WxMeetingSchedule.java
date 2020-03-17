/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 会议日程表Entity
 * @author lvyangzhuo
 * @version 2018-11-23
 */
public class WxMeetingSchedule extends DataEntity<WxMeetingSchedule> {
	
	private static final long serialVersionUID = 1L;
	private String meetingId;		// 会议id
	private Date startTime;		// 活动开始时间
	private Date endTime;		// 活动结束时间
	private String content;		// 活动内容
	private Date conferenceDay;	// 会议日期
	private String type;		// 活动内容类型
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String myType;		// 自定义标签的内容
	
	public WxMeetingSchedule() {
		super();
	}

	public WxMeetingSchedule(String id){
		super(id);
	}
	
	@ExcelField(title="会议id", align=2, sort=1)
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="活动开始时间", align=2, sort=2)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="活动结束时间", align=2, sort=3)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="活动内容", align=2, sort=4)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="会议日期", align=2, sort=5)
	public Date getConferenceDay() {
		return conferenceDay;
	}

	public void setConferenceDay(Date conferenceDay) {
		this.conferenceDay = conferenceDay;
	}
	
	@ExcelField(title="内容类型", dictType="", align=2, sort=6)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="remark1", align=2, sort=7)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="remark2", align=2, sort=8)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@ExcelField(title="myType", align=2, sort=9)
	public String getMyType() {
		return myType;
	}

	public void setMyType(String myType) {
		this.myType = myType;
	}
	
}