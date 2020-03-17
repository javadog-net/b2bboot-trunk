/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 奖项表操作Entity
 * @author tc
 * @version 2019-02-27
 */
public class CmsPrize extends DataEntity<CmsPrize> {
	
	private static final long serialVersionUID = 1L;
	private String actvId;		// 活动id
	private String meetingId;		// 会议id
	private String prizeName;		// 奖项名称
	private String prizeType;//奖项类型
	public String getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(String prizeType) {
		this.prizeType = prizeType;
	}

	private String prizeNum;		// 奖项人数
	private String prizeTab;		// 抽奖标识（0：有效。1：无效）
	private Date addTime;		// 添加时间
	
	public CmsPrize() {
		super();
	}

	public CmsPrize(String id){
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
	
	@ExcelField(title="奖项名称", align=2, sort=3)
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	
	@ExcelField(title="奖项人数", align=2, sort=4)
	public String getPrizeNum() {
		return prizeNum;
	}

	public void setPrizeNum(String prizeNum) {
		this.prizeNum = prizeNum;
	}
	
	@ExcelField(title="抽奖标识（0：有效。1：无效）", align=2, sort=5)
	public String getPrizeTab() {
		return prizeTab;
	}

	public void setPrizeTab(String prizeTab) {
		this.prizeTab = prizeTab;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=6)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
}