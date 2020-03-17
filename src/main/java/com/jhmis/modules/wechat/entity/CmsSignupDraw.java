/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 参与报名抽奖Entity
 * @author tc
 * @version 2019-02-27
 */
public class CmsSignupDraw extends DataEntity<CmsSignupDraw> {
	
	private static final long serialVersionUID = 1L;
	private String actvId;		// 活动id
	private String meetingId;		// 会议id
	private String userId;		// 参与会员id
	private String userName;		// 参与会员姓名
	private String userPhone;		// 参与会员手机号
	private String companyName;		// 参与会员公司名称
	private String remark;		// 备注
	private Date addTime;		// 参与时间
	private String avatar;//用户头像
	private String prizeIdTab;//指定用户中奖
	
	public String getPrizeIdTab() {
		return prizeIdTab;
	}

	public void setPrizeIdTab(String prizeIdTab) {
		this.prizeIdTab = prizeIdTab;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public CmsSignupDraw() {
		super();
	}

	public CmsSignupDraw(String id){
		super(id);
	}

	@ExcelField(title="活动id", align=2, sort=1)
	public String getActvId() {
		return actvId;
	}

	public void setActvId(String actvId) {
		this.actvId = actvId;
	}
	
	@ExcelField(title="会议id", align=2, sort=2)
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	@ExcelField(title="参与会员id", align=2, sort=3)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="参与会员姓名", align=2, sort=4)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="参与会员手机号", align=2, sort=5)
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	@ExcelField(title="参与会员公司名称", align=2, sort=6)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="备注", align=2, sort=7)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="参与时间", align=2, sort=8)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
}