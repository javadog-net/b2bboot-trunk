/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 中奖表Entity
 * @author tc
 * @version 2019-02-27
 */
public class CmsWinPrize extends DataEntity<CmsWinPrize> {
	
	private static final long serialVersionUID = 1L;
	private String actvId;		// 活动id
	private String meetingId;		// 会议id
	private String prizeId;		// 奖项id
	private String userId;		// 中奖人id
	private Date addTime;		// 中奖时间
	private String remark;		// 中奖备注
	private String wpStatus;		// 中奖状态
	//关联查询用到的字段
	private String prizeType;
	private String prizeName;
	private String userName;
	private String userPhone;
	private String companyName;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	//关联查询用到的对象
	private CmsSignupDraw cmsSignupDraw;
	private CmsPrize cmsPrize;
	
	public CmsSignupDraw getCmsSignupDraw() {
		return cmsSignupDraw;
	}

	public void setCmsSignupDraw(CmsSignupDraw cmsSignupDraw) {
		this.cmsSignupDraw = cmsSignupDraw;
	}

	public CmsPrize getCmsPrize() {
		return cmsPrize;
	}

	public void setCmsPrize(CmsPrize cmsPrize) {
		this.cmsPrize = cmsPrize;
	}

	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public CmsWinPrize() {
		super();
	}

	public CmsWinPrize(String id){
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
	
	@ExcelField(title="奖项id", align=2, sort=3)
	public String getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(String prizeId) {
		this.prizeId = prizeId;
	}
	
	@ExcelField(title="中奖人id", align=2, sort=4)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="中奖时间", align=2, sort=5)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="中奖备注", align=2, sort=6)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="中奖状态", align=2, sort=7)
	public String getWpStatus() {
		return wpStatus;
	}

	public void setWpStatus(String wpStatus) {
		this.wpStatus = wpStatus;
	}
	
}