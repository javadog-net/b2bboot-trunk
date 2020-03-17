/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;

/**
 * 活动表操作Entity
 * @author tc
 * @version 2019-02-27
 */
public class CmsActivity extends DataEntity<CmsActivity> {
	
	private static final long serialVersionUID = 1L;
	private String meetingId;		// 会议id
	private String meetingName;		// 会议名称
	private String actvName;		// 活动名称
	private String actvBanner;		// 活动banner
	private String creatUser;		// 创建人
	private Date creatTime;		// 创建时间（添加时间）
	private Date startTime;		// 活动开始时间
	private String actvTab;		// 活动是否有效（0：是。1：否）
	private String remark;		// 活动备注
	private List<CmsPrize> listcmsPrize;   //奖项集合


	public List<CmsPrize> getListcmsPrize() {
		return listcmsPrize;
	}

	public void setListcmsPrize(List<CmsPrize> listcmsPrize) {
		this.listcmsPrize = listcmsPrize;
	}

	public CmsActivity() {
		super();
	}

	public CmsActivity(String id){
		super(id);
	}

	@ExcelField(title="会议id", align=2, sort=1)
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	@ExcelField(title="会议名称", align=2, sort=2)
	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}
	
	@ExcelField(title="活动名称", align=2, sort=3)
	public String getActvName() {
		return actvName;
	}

	public void setActvName(String actvName) {
		this.actvName = actvName;
	}
	
	@ExcelField(title="活动banner", align=2, sort=4)
	public String getActvBanner() {
		return actvBanner;
	}

	public void setActvBanner(String actvBanner) {
		this.actvBanner = actvBanner;
	}
	
	@ExcelField(title="创建人", align=2, sort=5)
	public String getCreatUser() {
		return creatUser;
	}

	public void setCreatUser(String creatUser) {
		this.creatUser = creatUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间（添加时间）", align=2, sort=6)
	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="活动开始时间", align=2, sort=7)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@ExcelField(title="活动是否有效（0：是。1：否）", align=2, sort=8)
	public String getActvTab() {
		return actvTab;
	}

	public void setActvTab(String actvTab) {
		this.actvTab = actvTab;
	}
	
	@ExcelField(title="活动备注", align=2, sort=9)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}