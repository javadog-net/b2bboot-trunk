/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 客单需求Entity
 * @author hdx
 * @version 2019-04-25
 */
public class CustomerMsg extends DataEntity<CustomerMsg> {
	
	private static final long serialVersionUID = 1L;
	//漏斗项目详情
	private CustomerProjectInfo customerProjectInfo;
	//漏斗项目产品组
	private List<CustomerProjectProduct> listCustomerProjectProduct;
	//漏斗项目产品组详情
	private List<CustomerProjectProductDetail> listCustomerProjectProductDetail;
	public static final String CUSTOMER_ORDER = "CUSTOMER_ORDER";
	public static final String DIRECTIVD_ORDER = "DIRECTIVD_ORDER";
	private String msgId;		// 客单需求订单号  201901416xxxxx(为了保证一期既存项目与改版后id重复)
	private String projectName;		// 项目名称
	private String firstCompanyName;		// 甲方公司名称
	private String firstCompanyOrgCode;		// 统一社会信用代码
	private String addressProvince;		// 项目地址（省）
	private String addressProvinceName;		// 地址（省份）
	private String addressCity;		// 项目地址（城市）
	private String addressCityName;		// 地址(城市)
	private String addressCounty;		// 项目地址（区/县）
	private String addressCountyName;		// 地址(地区)
	private String addressDetail;		// 详细地址
    private String center; //中心
	private String centerName; //中心
	private String projectCreaterCode;		// 项目录入人编码
	private String projectCreaterName;		// 项目录入姓名
	private String projectSource;		// 项目来源(GN-PRJ-08-用户留言；GN-PRJ-？？-经销商报备；GN-PRJ-11-奥维；GN-PRJ-13-生态会;GN-PRJ-14-其他第三方)
	private String projectManagerCode;		// 项目经理编码
	private String projectManagerName;		// 项目经理名称
	private String domainModel;		// 产业模式（客单/直单）
	private String domainType;		// 产业类型(大客户、商空、冰箱等)
	private Date addTime;		// 添加时间
	private String productGroup;		// 产品组（多个以逗号隔开）
	private String contractorCode;		// 客户编码
	private String contractorName;		// 承接商名称
	private String status;		// 1是 成功进入漏斗  0是 没有进入漏斗
	private String errorMsg;		// 失败原因
	private String isDel;		// 是否删除(0 否 1 是)
	private String remark;		// 备注
	private String projectId;		// 漏斗项目回传id
	private String userId;		// 提报人
	private String dataSource;		// 数据来源（工程平台自创建和企业购传入）
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date estimatedTimeBid;		// 预计投标日期
	private String firstContactName;		// 甲方联系人姓名
	private String position;		// 职位
	private String phone;		// phone
	private String industryHomeCategory;		// 项目所属行业门类(20漏斗基础数据)
	private String industryCategory;		// 行业大类(96漏斗基础数据)
	private String industryHomeCategoryName;		// 项目所属行业门类(20漏斗基础数据)
	private String industryCategoryName;
	private String nodename; //项目节点
	private String loginLevel;//登录级别
	private String operateName;//审批人
	private String operateStatus;//审批状态
	private String projectstatus;//项目状态
	private String allTime;
	private String startTime;
	private String endTime;
	private String chancePoint;//机会点
	private String userGroup;//用户群
	private String lockUser;//锁定用户
	private String customerDemandAnalysis;//客户需求分析
	private Boolean beSendWithOne;//送装一体
	private String firstBidDocument;//甲方招标文件
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date estimatedTimeSigning;//预计签约日期
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date estimatedTimeDelivery;//实际首次下单日期
	private Boolean beWinBid;//是否招标
	private String contractAttachement;//合同扫描件
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date winBidTime;//预计开标时间
	private Boolean afterSalesBidding;//需售后招标
	private Boolean needDirectDeliverySite;//需直发工地
	private List<CustomerQuotes> quotes;//报价
	private String productSeriesOneCode;//产品序列第一编码
	private String productSeriesOneName;//产品序列第一名称
	private String productSeriesTwoCode;//产品序列第二编码
	private String productSeriesTwoName;//产品序列第二名称
	private String brandInfoCode;//产品品牌编码
	private String brandInfoName;//产品品牌名称
	private String bigOrSmall;//大机或小机(1大机，2小机)
	private String industryOrChannel;//行业或渠道（行业1，渠道2）
	private String smallBill;//是否为小单（小单1，大单0）
	private String businessCode;//商机编码
    private String businessSource;//商机来源

    public String getBusinessSource() {
        return businessSource;
    }

    public void setBusinessSource(String businessSource) {
        this.businessSource = businessSource;
    }

    public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getProjectstatus() {
		return projectstatus;
	}

	public void setProjectstatus(String projectstatus) {
		this.projectstatus = projectstatus;
	}

	public String getAllTime() {
		return allTime;
	}

	public void setAllTime(String allTime) {
		this.allTime = allTime;
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

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	

	public String getLoginLevel() {
		return loginLevel;
	}

	public void setLoginLevel(String loginLevel) {
		this.loginLevel = loginLevel;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}

	public String getIndustryHomeCategoryName() {
		return industryHomeCategoryName;
	}

	public void setIndustryHomeCategoryName(String industryHomeCategoryName) {
		this.industryHomeCategoryName = industryHomeCategoryName;
	}

	public String getIndustryCategoryName() {
		return industryCategoryName;
	}

	public void setIndustryCategoryName(String industryCategoryName) {
		this.industryCategoryName = industryCategoryName;
	}

	// 行业大类(96漏斗基础数据)
    List<CustomerMsgProduct> listCustomerMsgProduct;

    public List<CustomerMsgProduct> getListCustomerMsgProduct() {
        return listCustomerMsgProduct;
    }

    public void setListCustomerMsgProduct(List<CustomerMsgProduct> listCustomerMsgProduct) {
        this.listCustomerMsgProduct = listCustomerMsgProduct;
    }

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public CustomerProjectInfo getCustomerProjectInfo() {
		return customerProjectInfo;
	}

	public void setCustomerProjectInfo(CustomerProjectInfo customerProjectInfo) {
		this.customerProjectInfo = customerProjectInfo;
	}

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public List<CustomerProjectProduct> getListCustomerProjectProduct() {
		return listCustomerProjectProduct;
	}

	public void setListCustomerProjectProduct(List<CustomerProjectProduct> listCustomerProjectProduct) {
		this.listCustomerProjectProduct = listCustomerProjectProduct;
	}

	public List<CustomerProjectProductDetail> getListCustomerProjectProductDetail() {
		return listCustomerProjectProductDetail;
	}

	public void setListCustomerProjectProductDetail(List<CustomerProjectProductDetail> listCustomerProjectProductDetail) {
		this.listCustomerProjectProductDetail = listCustomerProjectProductDetail;
	}

	public CustomerMsg() {
		super();
	}

	public CustomerMsg(String id){
		super(id);
	}

	@ExcelField(title="客单需求订单号  201901416xxxxx(为了保证一期既存项目与改版后id重复)", align=2, sort=1)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@ExcelField(title="项目名称", align=2, sort=2)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@ExcelField(title="甲方公司名称", align=2, sort=3)
	public String getFirstCompanyName() {
		return firstCompanyName;
	}

	public void setFirstCompanyName(String firstCompanyName) {
		this.firstCompanyName = firstCompanyName;
	}
	
	@ExcelField(title="统一社会信用代码", align=2, sort=4)
	public String getFirstCompanyOrgCode() {
		return firstCompanyOrgCode;
	}

	public void setFirstCompanyOrgCode(String firstCompanyOrgCode) {
		this.firstCompanyOrgCode = firstCompanyOrgCode;
	}
	
	@ExcelField(title="项目地址（省）", align=2, sort=5)
	public String getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}
	
	@ExcelField(title="地址（省份）", align=2, sort=6)
	public String getAddressProvinceName() {
		return addressProvinceName;
	}

	public void setAddressProvinceName(String addressProvinceName) {
		this.addressProvinceName = addressProvinceName;
	}
	
	@ExcelField(title="项目地址（城市）", align=2, sort=7)
	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	
	@ExcelField(title="地址(城市)", align=2, sort=8)
	public String getAddressCityName() {
		return addressCityName;
	}

	public void setAddressCityName(String addressCityName) {
		this.addressCityName = addressCityName;
	}
	
	@ExcelField(title="项目地址（区/县）", align=2, sort=9)
	public String getAddressCounty() {
		return addressCounty;
	}

	public void setAddressCounty(String addressCounty) {
		this.addressCounty = addressCounty;
	}
	
	@ExcelField(title="地址(地区)", align=2, sort=10)
	public String getAddressCountyName() {
		return addressCountyName;
	}

	public void setAddressCountyName(String addressCountyName) {
		this.addressCountyName = addressCountyName;
	}
	
	@ExcelField(title="详细地址", align=2, sort=11)
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	@ExcelField(title="项目录入人编码", align=2, sort=12)
	public String getProjectCreaterCode() {
		return projectCreaterCode;
	}

	public void setProjectCreaterCode(String projectCreaterCode) {
		this.projectCreaterCode = projectCreaterCode;
	}
	
	@ExcelField(title="项目录入姓名", align=2, sort=13)
	public String getProjectCreaterName() {
		return projectCreaterName;
	}

	public void setProjectCreaterName(String projectCreaterName) {
		this.projectCreaterName = projectCreaterName;
	}
	
	@ExcelField(title="项目来源(GN-PRJ-08-用户留言；GN-PRJ-？？-经销商报备；GN-PRJ-11-奥维；GN-PRJ-13-生态会;GN-PRJ-14-其他第三方)", align=2, sort=14)
	public String getProjectSource() {
		return projectSource;
	}

	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}
	
	@ExcelField(title="项目经理编码", align=2, sort=15)
	public String getProjectManagerCode() {
		return projectManagerCode;
	}

	public void setProjectManagerCode(String projectManagerCode) {
		this.projectManagerCode = projectManagerCode;
	}
	
	@ExcelField(title="项目经理名称", align=2, sort=16)
	public String getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}
	
	@ExcelField(title="产业模式（客单/直单）", align=2, sort=17)
	public String getDomainModel() {
		return domainModel;
	}

	public void setDomainModel(String domainModel) {
		this.domainModel = domainModel;
	}
	
	@ExcelField(title="产业类型(大客户、商空、冰箱等)", align=2, sort=18)
	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=19)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="产品组（多个以逗号隔开）", align=2, sort=20)
	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	
	@ExcelField(title="客户编码", align=2, sort=21)
	public String getContractorCode() {
		return contractorCode;
	}

	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}
	
	@ExcelField(title="承接商名称", align=2, sort=22)
	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	
	@ExcelField(title="1是 成功进入漏斗  0是 没有进入漏斗", dictType="", align=2, sort=23)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="失败原因", align=2, sort=24)
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	@ExcelField(title="是否删除(0 否 1 是)", dictType="", align=2, sort=25)
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	@ExcelField(title="备注", align=2, sort=26)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="漏斗项目回传id", align=2, sort=27)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@ExcelField(title="提报人", align=2, sort=28)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="数据来源（工程平台自创建和企业购传入）", align=2, sort=29)
	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="预计投标日期", align=2, sort=30)
	public Date getEstimatedTimeBid() {
		return estimatedTimeBid;
	}

	public void setEstimatedTimeBid(Date estimatedTimeBid) {
		this.estimatedTimeBid = estimatedTimeBid;
	}
	
	@ExcelField(title="甲方联系人姓名", align=2, sort=31)
	public String getFirstContactName() {
		return firstContactName;
	}

	public void setFirstContactName(String firstContactName) {
		this.firstContactName = firstContactName;
	}
	
	@ExcelField(title="职位", align=2, sort=32)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@ExcelField(title="phone", align=2, sort=33)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@ExcelField(title="项目所属行业门类(20漏斗基础数据)", align=2, sort=34)
	public String getIndustryHomeCategory() {
		return industryHomeCategory;
	}

	public void setIndustryHomeCategory(String industryHomeCategory) {
		this.industryHomeCategory = industryHomeCategory;
	}
	
	@ExcelField(title="行业大类(96漏斗基础数据)", align=2, sort=35)
	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getChancePoint() {
		return chancePoint;
	}

	public void setChancePoint(String chancePoint) {
		this.chancePoint = chancePoint;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getLockUser() {
		return lockUser;
	}

	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}

	public String getCustomerDemandAnalysis() {
		return customerDemandAnalysis;
	}

	public void setCustomerDemandAnalysis(String customerDemandAnalysis) {
		this.customerDemandAnalysis = customerDemandAnalysis;
	}

	public Boolean getBeSendWithOne() {
		return beSendWithOne;
	}

	public void setBeSendWithOne(Boolean beSendWithOne) {
		this.beSendWithOne = beSendWithOne;
	}

	public String getFirstBidDocument() {
		return firstBidDocument;
	}

	public void setFirstBidDocument(String firstBidDocument) {
		this.firstBidDocument = firstBidDocument;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEstimatedTimeSigning(){
		return estimatedTimeSigning;
	}

	public void setEstimatedTimeSigning(Date estimatedTimeSigning) {
		this.estimatedTimeSigning = estimatedTimeSigning;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEstimatedTimeDelivery(){
		return estimatedTimeDelivery;		
	}

	public void setEstimatedTimeDelivery(Date estimatedTimeDelivery) {
		this.estimatedTimeDelivery = estimatedTimeDelivery;
	}

	public String getContractAttachement() {
		return contractAttachement;
	}

	public void setContractAttachement(String contractAttachement) {
		this.contractAttachement = contractAttachement;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getWinBidTime(){
		return winBidTime;
	}

	public void setWinBidTime(Date winBidTime) {
		this.winBidTime = winBidTime;
	}

	public List<CustomerQuotes> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<CustomerQuotes> quotes) {
		this.quotes = quotes;
	}

	public Boolean getBeWinBid() {
		return beWinBid;
	}

	public void setBeWinBid(Boolean beWinBid) {
		this.beWinBid = beWinBid;
	}

	public String getProductSeriesOneCode() {
		return productSeriesOneCode;
	}

	public void setProductSeriesOneCode(String productSeriesOneCode) {
		this.productSeriesOneCode = productSeriesOneCode;
	}

	public String getProductSeriesOneName() {
		return productSeriesOneName;
	}

	public void setProductSeriesOneName(String productSeriesOneName) {
		this.productSeriesOneName = productSeriesOneName;
	}

	public String getProductSeriesTwoCode() {
		return productSeriesTwoCode;
	}

	public void setProductSeriesTwoCode(String productSeriesTwoCode) {
		this.productSeriesTwoCode = productSeriesTwoCode;
	}

	public String getProductSeriesTwoName() {
		return productSeriesTwoName;
	}

	public void setProductSeriesTwoName(String productSeriesTwoName) {
		this.productSeriesTwoName = productSeriesTwoName;
	}

	public String getBrandInfoCode() {
		return brandInfoCode;
	}

	public void setBrandInfoCode(String brandInfoCode) {
		this.brandInfoCode = brandInfoCode;
	}

	public String getBrandInfoName() {
		return brandInfoName;
	}

	public void setBrandInfoName(String brandInfoName) {
		this.brandInfoName = brandInfoName;
	}

	public String getBigOrSmall() {
		return bigOrSmall;
	}

	public void setBigOrSmall(String bigOrSmall) {
		this.bigOrSmall = bigOrSmall;
	}

	public String getIndustryOrChannel() {
		return industryOrChannel;
	}

	public void setIndustryOrChannel(String industryOrChannel) {
		this.industryOrChannel = industryOrChannel;
	}

	public Boolean getAfterSalesBidding() {
		return afterSalesBidding;
	}

	public void setAfterSalesBidding(Boolean afterSalesBidding) {
		this.afterSalesBidding = afterSalesBidding;
	}

	public Boolean getNeedDirectDeliverySite() {
		return needDirectDeliverySite;
	}

	public void setNeedDirectDeliverySite(Boolean needDirectDeliverySite) {
		this.needDirectDeliverySite = needDirectDeliverySite;
	}

	public String getSmallBill() {
		return smallBill;
	}

	public void setSmallBill(String smallBill) {
		this.smallBill = smallBill;
	}


	
	
	
}