/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 会议表Entity
 * @author lvyangzhuo
 * @version 2018-11-23
 */
public class WxMeeting extends DataEntity<WxMeeting> {
	
	private static final long serialVersionUID = 1L;
	//默认状态为未开始报名状态
	public static final String MEETING_DEFAULT_STATUS = "1";
	private String createId; //创建会议的用户ID
	private String name;		// 会议名称
	private String site;		// 会议地点
	private Date startTime;		// 会议开始时间
	private Date endTime;		// 会议结束时间
	private String conferenceLength;// 会议时长
	private String generalize;		// 会议概括
	private String introduce;		// 会议介绍
	private String guide;		// 会务负责人
	private String map;		// 会议地图
	private Date closingUp;		// 会议截止报名时间
	private Integer personSignNum;		// 报名人数
	private Integer personAttendNum;		// 参会人数
	private String originator;		// 发起人
	private String img;		// 封面图
    private String detailImgs;		// 会议详情图片
	private String status;		// 会议状态 1_未开始报名，2_开始报名，3_会议已开始,4_会议已结束
	private String address;		// 会议地址
	private String isDel;		// 是否有效
	private String label;      // 0 是待开会议，1是已开会议
	private Date aimTime;     // 开过与未开过会议时间标识
	private String bannerImgs; //会议banner图
	private Date addTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getDetailImgs() {
        return detailImgs;
    }

    public void setDetailImgs(String detailImgs) {
        this.detailImgs = detailImgs;
    }
    
    
    public Date getAimTime() {
		return aimTime;
	}

	public void setAimTime(Date aimTime) {
		this.aimTime = aimTime;
	}

	private List<WxMeetingSchedule> wxMeetingScheduleList = Lists.newArrayList();		// 子表列表

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public WxMeeting() {
		super();
	}

	public WxMeeting(String id){
		super(id);
	}

	@ExcelField(title="会议名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="会议地点", align=2, sort=2)
	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="会议开始时间", align=2, sort=3)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="会议结束时间", align=2, sort=4)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="会议时长", align=2, sort=5)
	public String getConferenceLength() {
		return conferenceLength;
	}

	public void setConferenceLength(String conferenceLength) {
		this.conferenceLength = conferenceLength;
	}
	
	@ExcelField(title="会议概括", align=2, sort=6)
	public String getGeneralize() {
		return generalize;
	}

	public void setGeneralize(String generalize) {
		this.generalize = generalize;
	}
	
	@ExcelField(title="会议介绍", align=2, sort=7)
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	@ExcelField(title="会议指南", align=2, sort=8)
	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}
	
	@ExcelField(title="会议地图", align=2, sort=9)
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="会议截止报名时间", align=2, sort=10)
	public Date getClosingUp() {
		return closingUp;
	}

	public void setClosingUp(Date closingUp) {
		this.closingUp = closingUp;
	}

	public Integer getPersonSignNum() {
		return personSignNum;
	}

	public void setPersonSignNum(Integer personSignNum) {
		this.personSignNum = personSignNum;
	}

	public Integer getPersonAttendNum() {
		return personAttendNum;
	}

	public void setPersonAttendNum(Integer personAttendNum) {
		this.personAttendNum = personAttendNum;
	}

	@ExcelField(title="发起人", align=2, sort=13)
	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}
	
	@ExcelField(title="封面图", align=2, sort=14)
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@ExcelField(title="会议状态", align=2, sort=15)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="会议地址", align=2, sort=16)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="是否有效", align=2, sort=17)
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	public List<WxMeetingSchedule> getWxMeetingScheduleList() {
		return wxMeetingScheduleList;
	}

	public void setWxMeetingScheduleList(List<WxMeetingSchedule> wxMeetingScheduleList) {
		this.wxMeetingScheduleList = wxMeetingScheduleList;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getBannerImgs() {
		return bannerImgs;
	}

	public void setBannerImgs(String bannerImgs) {
		this.bannerImgs = bannerImgs;
	}
}