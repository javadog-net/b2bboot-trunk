/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 项目履约视图Entity
 * @author h'd'x
 * @version 2020-02-13
 */
public class ViewBusinessOpportunity extends DataEntity<ViewBusinessOpportunity> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目id
	private String projectCode;		// 项目编码
	private String projectName;		// 项目名称
	private String custCode;		// 经销商编码
	private String custName;		// cust_name
	private String centerCode;		// 中心编码
	private String centerName;		// 中心名称
	private String gridCenterName;		// 网格小微
	private String gridName;		// 网格
	private String firstCompanyName;		// 甲方公司名称
	private String firstCompanyOrgCode;		// 甲方统一社会信用代码
	private String brownCode;		// 工程版单号
	private String industryHomeCategoryId;		// 行业id
	private String industryHomeCategoryName;		// 行业名称
	private String projectSource;		// 项目来源
	private String projectManagerName;		// 海尔接口人
	private String projectManagerMobile;		// 海尔接口人电话
	private Date effectTime;		// 工程版生效时间
	private Date dcheckdatedy;		// 协议签订时间
	private String projectstate;		// 项目状态
	private Date lastModifiedDateBrown;		// 更新时间


	private String orderNo;  //订单号
	private String productGroup;		//产品组
	private String productModel;		//产品型号
	private String sapKwmeng;		//订单数量
	private Date createdTime;		//下单时间
	private String sapJudgeDate;		//评审时间
	private String sapJudgeStatus;		//评审状态
	private String sapPtdSendDate;		//计划发运时间

	private String planInDate;		//预计到达时间
	private String sapDn5date;		//二次物流时间 已发货时间（若此值不存在，则取一次物流时间）
	private String sapDn1date;		//一次物流时间
	private String sapCenterRecieveDate;	//达到中心时间
	private String sapReorderDate;		//签收时间
	private String orderTypeNo;		//订单号

    private String sapDn;		//85订单号

	private String orderStatus; //订单状态用户筛选
	//a.已下单：1
	//b.已评审：2
	//c.一次物流:3
	//d.二次物流:4
	//e.已返单: 5

	private String sapSended1;		//一次物流
	private String sapSended5;		//二次物流
	private String sapReorderd;		//已返单
    private String sapJudged;		//已评审

    public String getSapJudged() {
        return sapJudged;
    }

    public void setSapJudged(String sapJudged) {
        this.sapJudged = sapJudged;
    }

    private String sapDn1;   //
	private String sapDn5;   //85单号

	public String getSapDn1() {
		return sapDn1;
	}

	public void setSapDn1(String sapDn1) {
		this.sapDn1 = sapDn1;
	}

	public String getSapDn5() {
		return sapDn5;
	}

	public void setSapDn5(String sapDn5) {
		this.sapDn5 = sapDn5;
	}

	public String getSapSended1() {
		return sapSended1;
	}

	public void setSapSended1(String sapSended1) {
		this.sapSended1 = sapSended1;
	}

	public String getSapSended5() {
		return sapSended5;
	}

	public void setSapSended5(String sapSended5) {
		this.sapSended5 = sapSended5;
	}

	public String getSapReorderd() {
		return sapReorderd;
	}

	public void setSapReorderd(String sapReorderd) {
		this.sapReorderd = sapReorderd;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSapDn() {
		return sapDn;
	}

	public void setSapDn(String sapDn) {
		this.sapDn = sapDn;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public ViewBusinessOpportunity() {
		super();
	}

	public ViewBusinessOpportunity(String id){
		super(id);
	}

	@ExcelField(title="项目id", align=2, sort=0)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@ExcelField(title="项目编码", align=2, sort=1)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@ExcelField(title="项目名称", align=2, sort=2)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@ExcelField(title="经销商编码", align=2, sort=3)
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	@ExcelField(title="cust_name", align=2, sort=4)
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@ExcelField(title="中心编码", align=2, sort=5)
	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}
	
	@ExcelField(title="中心名称", align=2, sort=6)
	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	
	@ExcelField(title="网格小微", align=2, sort=7)
	public String getGridCenterName() {
		return gridCenterName;
	}

	public void setGridCenterName(String gridCenterName) {
		this.gridCenterName = gridCenterName;
	}
	
	@ExcelField(title="网格", align=2, sort=8)
	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
	
	@ExcelField(title="甲方公司名称", align=2, sort=9)
	public String getFirstCompanyName() {
		return firstCompanyName;
	}

	public void setFirstCompanyName(String firstCompanyName) {
		this.firstCompanyName = firstCompanyName;
	}
	
	@ExcelField(title="甲方统一社会信用代码", align=2, sort=10)
	public String getFirstCompanyOrgCode() {
		return firstCompanyOrgCode;
	}

	public void setFirstCompanyOrgCode(String firstCompanyOrgCode) {
		this.firstCompanyOrgCode = firstCompanyOrgCode;
	}
	
	@ExcelField(title="工程版单号", align=2, sort=11)
	public String getBrownCode() {
		return brownCode;
	}

	public void setBrownCode(String brownCode) {
		this.brownCode = brownCode;
	}
	
	@ExcelField(title="行业id", align=2, sort=12)
	public String getIndustryHomeCategoryId() {
		return industryHomeCategoryId;
	}

	public void setIndustryHomeCategoryId(String industryHomeCategoryId) {
		this.industryHomeCategoryId = industryHomeCategoryId;
	}
	
	@ExcelField(title="行业名称", align=2, sort=13)
	public String getIndustryHomeCategoryName() {
		return industryHomeCategoryName;
	}

	public void setIndustryHomeCategoryName(String industryHomeCategoryName) {
		this.industryHomeCategoryName = industryHomeCategoryName;
	}
	
	@ExcelField(title="项目来源", align=2, sort=14)
	public String getProjectSource() {
		return projectSource;
	}

	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}
	
	@ExcelField(title="海尔接口人", align=2, sort=15)
	public String getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}
	
	@ExcelField(title="海尔接口人电话", align=2, sort=16)
	public String getProjectManagerMobile() {
		return projectManagerMobile;
	}

	public void setProjectManagerMobile(String projectManagerMobile) {
		this.projectManagerMobile = projectManagerMobile;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="工程版生效时间", align=2, sort=17)
	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="协议签订时间", align=2, sort=18)
	public Date getDcheckdatedy() {
		return dcheckdatedy;
	}

	public void setDcheckdatedy(Date dcheckdatedy) {
		this.dcheckdatedy = dcheckdatedy;
	}
	
	@ExcelField(title="项目状态", align=2, sort=19)
	public String getProjectstate() {
		return projectstate;
	}

	public void setProjectstate(String projectstate) {
		this.projectstate = projectstate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=20)
	public Date getLastModifiedDateBrown() {
		return lastModifiedDateBrown;
	}

	public void setLastModifiedDateBrown(Date lastModifiedDateBrown) {
		this.lastModifiedDateBrown = lastModifiedDateBrown;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getSapKwmeng() {
		return sapKwmeng;
	}

	public void setSapKwmeng(String sapKwmeng) {
		this.sapKwmeng = sapKwmeng;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getSapJudgeDate() {
		return sapJudgeDate;
	}

	public void setSapJudgeDate(String sapJudgeDate) {
		this.sapJudgeDate = sapJudgeDate;
	}

	public String getSapJudgeStatus() {
		return sapJudgeStatus;
	}

	public void setSapJudgeStatus(String sapJudgeStatus) {
		this.sapJudgeStatus = sapJudgeStatus;
	}

	public String getSapPtdSendDate() {
		return sapPtdSendDate;
	}

	public void setSapPtdSendDate(String sapPtdSendDate) {
		this.sapPtdSendDate = sapPtdSendDate;
	}

	public String getPlanInDate() {
		return planInDate;
	}

	public void setPlanInDate(String planInDate) {
		this.planInDate = planInDate;
	}

	public String getSapDn5date() {
		return sapDn5date;
	}

	public void setSapDn5date(String sapDn5date) {
		this.sapDn5date = sapDn5date;
	}

	public String getSapDn1date() {
		return sapDn1date;
	}

	public void setSapDn1date(String sapDn1date) {
		this.sapDn1date = sapDn1date;
	}

	public String getSapCenterRecieveDate() {
		return sapCenterRecieveDate;
	}

	public void setSapCenterRecieveDate(String sapCenterRecieveDate) {
		this.sapCenterRecieveDate = sapCenterRecieveDate;
	}

	public String getSapReorderDate() {
		return sapReorderDate;
	}

	public void setSapReorderDate(String sapReorderDate) {
		this.sapReorderDate = sapReorderDate;
	}

	public String getOrderTypeNo() {
		return orderTypeNo;
	}

	public void setOrderTypeNo(String orderTypeNo) {
		this.orderTypeNo = orderTypeNo;
	}

	@Override
	public String toString() {
		return "ViewBusinessOpportunity{" +
				"projectId='" + projectId + '\'' +
				", projectCode='" + projectCode + '\'' +
				", projectName='" + projectName + '\'' +
				", custCode='" + custCode + '\'' +
				", custName='" + custName + '\'' +
				", centerCode='" + centerCode + '\'' +
				", centerName='" + centerName + '\'' +
				", gridCenterName='" + gridCenterName + '\'' +
				", gridName='" + gridName + '\'' +
				", firstCompanyName='" + firstCompanyName + '\'' +
				", firstCompanyOrgCode='" + firstCompanyOrgCode + '\'' +
				", brownCode='" + brownCode + '\'' +
				", industryHomeCategoryId='" + industryHomeCategoryId + '\'' +
				", industryHomeCategoryName='" + industryHomeCategoryName + '\'' +
				", projectSource='" + projectSource + '\'' +
				", projectManagerName='" + projectManagerName + '\'' +
				", projectManagerMobile='" + projectManagerMobile + '\'' +
				", effectTime=" + effectTime +
				", dcheckdatedy=" + dcheckdatedy +
				", projectstate='" + projectstate + '\'' +
				", lastModifiedDateBrown=" + lastModifiedDateBrown +
				", orderNo='" + orderNo + '\'' +
				", productGroup='" + productGroup + '\'' +
				", productModel='" + productModel + '\'' +
				", sapKwmeng='" + sapKwmeng + '\'' +
				", createdTime=" + createdTime +
				", sapJudgeDate='" + sapJudgeDate + '\'' +
				", sapJudgeStatus='" + sapJudgeStatus + '\'' +
				", sapPtdSendDate='" + sapPtdSendDate + '\'' +
				", planInDate='" + planInDate + '\'' +
				", sapDn5date='" + sapDn5date + '\'' +
				", sapDn1date='" + sapDn1date + '\'' +
				", sapCenterRecieveDate='" + sapCenterRecieveDate + '\'' +
				", sapReorderDate='" + sapReorderDate + '\'' +
				", orderTypeNo='" + orderTypeNo + '\'' +
				", sapDn='" + sapDn + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				", sapSended1='" + sapSended1 + '\'' +
				", sapSended5='" + sapSended5 + '\'' +
				", sapReorderd='" + sapReorderd + '\'' +
				", sapDn1='" + sapDn1 + '\'' +
				", sapDn5='" + sapDn5 + '\'' +
				'}';
	}
}