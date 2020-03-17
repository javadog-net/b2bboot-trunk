/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 招投标Entity
 * @author hdx
 * @version 2019-04-11
 */
public class BidCheckRecord extends DataEntity<BidCheckRecord> {
	
	private static final long serialVersionUID = 1L;
	private String bidInfoId;		// 招投标信息id
	private String checkStatus;		// 审核状态(0审核拒绝，1审核通过)
	private String checkUser;		// 审核人
	private Date checkTime;		// 审核时间
	private String checkRemark;		// 审核备注
	private Date subTime;		// 提交时间
	private Date updateTime;		// 更新时间
	
	private String  checkDate;		// 额外审核时间
	public BidCheckRecord() {
		super();
	}

	public BidCheckRecord(String id){
		super(id);
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	@ExcelField(title="招投标信息id", align=2, sort=1)
	public String getBidInfoId() {
		return bidInfoId;
	}

	public void setBidInfoId(String bidInfoId) {
		this.bidInfoId = bidInfoId;
	}
	
	@ExcelField(title="审核状态(0审核拒绝，1审核通过)", align=2, sort=2)
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	@ExcelField(title="审核人", align=2, sort=3)
	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=4)
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	@ExcelField(title="审核备注", align=2, sort=5)
	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="提交时间", align=2, sort=6)
	public Date getSubTime() {
		return subTime;
	}

	public void setSubTime(Date subTime) {
		this.subTime = subTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=7)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}