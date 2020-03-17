/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 招投标Entity
 * 
 * @author hdx
 * @version 2019-04-11
 */
public class BidInfo extends DataEntity<BidInfo> {

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	private static final long serialVersionUID = 1L;
	private String industry; // 行业id
	private String projectName; // 项目名称
	private String companyName; // 公司名称
	private String projectIntro; // 项目介绍(富文本)
	private String projectContent; // 项目内容(富文本)
	private String projectBudget; // 项目预算
	private String projectLogo; // 项目logo
	private String provinceId; // 省份id
	private String cityId; // 城市id
	private String bidConcat; // 联系人
	public int getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}

	private String bidConcatPhone; // 联系人手机号
	private String bidConcatEmail; // 联系人邮箱
	private String bidConcatAddress; // 联系人地址
	private String applicationConditions; // 报名单位资格要求(富文本)
	private int visitNum; // 报名单位资格要求(富文本)

	private Date addTime; // 创建时间
	
	@ExcelField(title = "所属地区(必填)", align = 2, sort = 7)
	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}

	private String addUser; // 创建人
	private String status; // 状态值(0待审核,1审核通过,2审核拒绝)
	private String isValid; // 是否有效(0否,1是)难题
	private String isDel; // 是否逻辑删除(0否,1是)
	private String source; // 1客服新建 2客服导入 3用户新建
	private String areaInfo; // 省市区汉子
	public String getSevenDay() {
		return sevenDay;
	}

	public void setSevenDay(String sevenDay) {
		this.sevenDay = sevenDay;
	}

	private Date updateTime; // 更新时间
	//条件筛选
	private String timeType; // 时间筛选
	private String sevenDay; // 三天
	private String threeDay; //七天
	private String month; // 一个月
	private String threeMonth; // 三个月
	private String sixMonth; // 半年
	private String year; // 一年
	

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getThreeDay() {
		return threeDay;
	}

	public void setThreeDay(String threeDay) {
		this.threeDay = threeDay;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getThreeMonth() {
		return threeMonth;
	}

	public void setThreeMonth(String threeMonth) {
		this.threeMonth = threeMonth;
	}

	public String getSixMonth() {
		return sixMonth;
	}

	public void setSixMonth(String sixMonth) {
		this.sixMonth = sixMonth;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public BidInfo() {
		super();
	}
	public BidInfo(String id) {
		super(id);
	}

	@ExcelField(title = "所属行业(必填)", align = 2, sort = 3)
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@ExcelField(title = "项目名称(必填)", align = 2, sort = 1)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@ExcelField(title = "公司名称(必填)", align = 2, sort = 2)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@ExcelField(title = "项目介绍(必填)", align = 2, sort = 4)
	public String getProjectIntro() {
		return projectIntro;
	}

	public void setProjectIntro(String projectIntro) {
		this.projectIntro = projectIntro;
	}

	@ExcelField(title = "项目内容(必填)", align = 2, sort = 5)
	@NotNull(message="项目内容不能为空")
	public String getProjectContent() {
		return projectContent;
	}

	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}

	@ExcelField(title = "项目预算(必填)", align = 2, sort = 6)
	@NotNull(message="项目预算不能为空")
	public String getProjectBudget() {
		return projectBudget;
	}

	public void setProjectBudget(String projectBudget) {
		this.projectBudget = projectBudget;
	}

	public String getProjectLogo() {
		return projectLogo;
	}

	public void setProjectLogo(String projectLogo) {
		this.projectLogo = projectLogo;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@ExcelField(title = "联系人(必填)", align = 2, sort = 8)
	public String getBidConcat() {
		return bidConcat;
	}

	public void setBidConcat(String bidConcat) {
		this.bidConcat = bidConcat;
	}

	@ExcelField(title = "联系人手机号(必填)", align = 2, sort = 9)
	public String getBidConcatPhone() {
		return bidConcatPhone;
	}

	public void setBidConcatPhone(String bidConcatPhone) {
		this.bidConcatPhone = bidConcatPhone;
	}

	@ExcelField(title = "联系人邮箱(必填)", align = 2, sort = 10)
	public String getBidConcatEmail() {
		return bidConcatEmail;
	}

	public void setBidConcatEmail(String bidConcatEmail) {
		this.bidConcatEmail = bidConcatEmail;
	}

	@ExcelField(title = "联系人地址(必填)", align = 2, sort = 11)
	public String getBidConcatAddress() {
		return bidConcatAddress;
	}

	public void setBidConcatAddress(String bidConcatAddress) {
		this.bidConcatAddress = bidConcatAddress;
	}

	@ExcelField(title = "报名单位资格要求(必填)", align = 2, sort = 12)
	public String getApplicationConditions() {
		return applicationConditions;
	}

	public void setApplicationConditions(String applicationConditions) {
		this.applicationConditions = applicationConditions;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	
	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}