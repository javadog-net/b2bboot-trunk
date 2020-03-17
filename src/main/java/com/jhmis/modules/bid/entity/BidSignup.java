/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 招投标Entity
 * 
 * @author hdx
 * @version 2019-04-11
 */
public class BidSignup extends DataEntity<BidSignup> {

	private static final long serialVersionUID = 1L;
	private String companyName; // 公司名称

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}

	private String projectName; // 项目名称
	private String bidInfoId; // 项目id
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}

	private String signupConcat; // 报名联系人
	private String purchaserId; //采购商ID
	private String signupConcatJob; // 联系人职务
	private String signupConcatMobile; // 联系人电话
	private String signupConcatEmail; // 联系人邮箱
	private String purchaserAccountId; // 联系人邮箱



	private String message; // 留言

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	private Date addTime; // 报名时间
	private Date updateTime; // 更新时间
	// 额外 时间
	private String addDate; // 联系人邮箱

	public BidSignup() {
		super();
	}

	public BidSignup(String id) {
		super(id);
	}

	@ExcelField(title = "公司名称", align = 2, sort = 1)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@ExcelField(title = "直采需求id", align = 2, sort = 2)
	public String getBidInfoId() {
		return bidInfoId;
	}

	public void setBidInfoId(String bidInfoId) {
		this.bidInfoId = bidInfoId;
	}

	@ExcelField(title = "报名联系人", align = 2, sort = 3)
	public String getSignupConcat() {
		return signupConcat;
	}

	public void setSignupConcat(String signupConcat) {
		System.out.println("setSignupConcat"+signupConcat);
		this.signupConcat = signupConcat;
	}

	@ExcelField(title = "联系人职务", align = 2, sort = 4)
	public String getSignupConcatJob() {
		

		return signupConcatJob;
	}

	public void setSignupConcatJob(String signupConcatJob) {
		System.out.println("signupConcatJob"+signupConcatJob);
		this.signupConcatJob = signupConcatJob;
	}

	@ExcelField(title = "联系人电话", align = 2, sort = 5)
	public String getSignupConcatMobile() {
		return signupConcatMobile;
	}

	public void setSignupConcatMobile(String signupConcatMobile) {
		this.signupConcatMobile = signupConcatMobile;
	}

	@ExcelField(title = "留言", align = 2, sort = 7)
	public String getMessage() {
		return message;
	}

	public String getSignupConcatEmail() {
		return signupConcatEmail;
	}

	public void setSignupConcatEmail(String signupConcatEmail) {
		this.signupConcatEmail = signupConcatEmail;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "报名时间", align = 2, sort = 9)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title = "更新时间", align = 2, sort = 9)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}