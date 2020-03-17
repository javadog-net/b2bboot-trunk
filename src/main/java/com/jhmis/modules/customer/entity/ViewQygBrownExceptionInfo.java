/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;

import java.util.Date;

import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * hps视图工程版异常报备列表Entity
 * @author hdx
 * @version 2019-05-24
 */
public class ViewQygBrownExceptionInfo extends DataEntity<ViewQygBrownExceptionInfo> {
	
	private static final long serialVersionUID = 1L;
	private String brownCode;		// 工程单号
	private String projectId;		// 漏斗项目id
	private String projectCode;		// 项目漏斗编号
	private String projectName;		// 项目名称
	private String projectAddress;		// 项目地址
	private String productGroupCode;		// 产品组编码
	private String productGroupName;		// 产品组名称
	private String dealerCode;		// 经销商编码
	private String dealerName;		// 经销商名称
	private String deadTime;		// 结束时间（离180天剩余天数，分别为40/30/15/3t天）
	private String lfimg2;		// 提货数量
	private String brownCount;		// 申请数量
	private String quantityUnavailable;		// 异常报备数量
	private String isCheck;		// 是否可验收 0可验收 1不可
	private String verifyRate;		// 验收比率
	private String verifyNum;		// 验收数量
	private Date lastModifiedDate; //最后修改时间
	private String startTime;//开始时间
	private String endTime; //结束时间
	
	public ViewQygBrownExceptionInfo() {
		super();
	}

	public ViewQygBrownExceptionInfo(String id){
		super(id);
	}

	@ExcelField(title="工程单号", align=2, sort=1)
	public String getBrownCode() {
		return brownCode;
	}

	public void setBrownCode(String brownCode) {
		this.brownCode = brownCode;
	}
	
	@ExcelField(title="漏斗项目id", align=2, sort=2)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@ExcelField(title="项目漏斗编号", align=2, sort=3)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=4)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@ExcelField(title="项目地址", align=2, sort=5)
	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}
	
	@ExcelField(title="产品组编码", align=2, sort=6)
	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}
	
	@ExcelField(title="产品组名称", align=2, sort=7)
	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	
	@ExcelField(title="经销商编码", align=2, sort=8)
	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	
	@ExcelField(title="经销商名称", align=2, sort=9)
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	
	@ExcelField(title="结束时间（离180天剩余天数，分别为40/30/15/3t天）", align=2, sort=10)
	public String getDeadTime() {
		return deadTime;
	}

	public void setDeadTime(String deadTime) {
		this.deadTime = deadTime;
	}
	
	@ExcelField(title="提货数量", align=2, sort=11)
	public String getLfimg2() {
		return lfimg2;
	}

	public void setLfimg2(String lfimg2) {
		this.lfimg2 = lfimg2;
	}
	
	@ExcelField(title="申请数量", align=2, sort=12)
	public String getBrownCount() {
		return brownCount;
	}

	public void setBrownCount(String brownCount) {
		this.brownCount = brownCount;
	}
	
	@ExcelField(title="异常报备数量", align=2, sort=13)
	public String getQuantityUnavailable() {
		return quantityUnavailable;
	}

	public void setQuantityUnavailable(String quantityUnavailable) {
		this.quantityUnavailable = quantityUnavailable;
	}
	
	@ExcelField(title="是否可验收 0可验收 1不可", align=2, sort=14)
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@ExcelField(title="验收比率", align=2, sort=15)
	public String getVerifyRate() {
		return verifyRate;
	}

	public void setVerifyRate(String verifyRate) {
		this.verifyRate = verifyRate;
	}
	
	@ExcelField(title="验收数量", align=2, sort=16)
	public String getVerifyNum() {
		return verifyNum;
	}

	public void setVerifyNum(String verifyNum) {
		this.verifyNum = verifyNum;
	}

	@ExcelField(title="最后修改时间", align=2, sort=17)
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
}