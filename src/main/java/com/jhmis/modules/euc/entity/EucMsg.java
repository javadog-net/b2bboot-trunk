/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * euc系统相关需求Entity
 * @author hdx
 * @version 2019-11-08
 */
public class EucMsg extends DataEntity<EucMsg> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// 需求id
	private String businessCode;		// 商机编码
	private String businessName;		// 商机名称
	private String businessSource;		// 商机来源
	private String businessType;		// 商机类型
	private String topCustomerAbbreviation;		// top客户简称
	private String companyNameA;		// 甲方公司名称
	private String contactNameA;		// 甲方联系姓名
	private String contactMobileA;		// 甲方联系电话
	private String industryId;		// 行业id
	private String industry;		// 行业名称
	private String state;		// 状态
	private String haierTheater;		// 海尔战区
	private String center;		// 中心
    private String centerName;		// 中心名称
	private String smallMesh;		// 网格小微
	private String grid;		// 网格
	private String province;		// 省份
	private String provinceId;		// 省份id
	private String city;		// 城市	
	private String cityId;		// 城市id 
	private String district;		// 区县
	private String districtId;		// 区县_id
	private String businessOpportunityAddress;		// 商机地址
	private Date lastProcessingTime;		// 最近处理时间
	private Date entryDate;		// 录入日期
	private String startEntryDate;   //开始时间
	private String endEntryDate;	//结束时间

	public String getStartEntryDate() {
		return startEntryDate;
	}

	public void setStartEntryDate(String startEntryDate) {
		this.startEntryDate = startEntryDate;
	}

	public String getEndEntryDate() {
		return endEntryDate;
	}

	public void setEndEntryDate(String endEntryDate) {
		this.endEntryDate = endEntryDate;
	}

	private String projectManagerCode;		// 海尔接口人编码
	private String projectManagerName;		// 海尔接口人姓名
	private String contractorCode;		// 承接商编码
	private String contractorName;		// 承接商名称
	private String businessChannel;		// 承接商大渠道
	private String competitors;		// 竞争对手公司
	private String isSendHps;		// 是否成功传输hps 0否 1是
	private String projectCode;		// 项目编码(hps 回传)
    private String ifTopCustomer;   //是否top
	private String customerMsgId;	//客单报备信息id
    private String isAll; //是否获取中心下所有数据
	private String undertake;		// 承接方式0工程1零售
	private String tradeCount;		// 交易金额
	private String orderType;		// 订单类型(0派单 1抢单 )
	private String orgId;		// 中心工贸
	private String isCheck;		// 平台审核是否通过0 是 1否
	private String checker;		// 平台审核人
	private String isBind;		// 是否中标 0否 1是
	private Date checkDate;		// 平台审核时间
	private String imageUrl;		// 上传凭证
	private Date updateTime;		// 更新时间
	private String isAbandon;		// 是否放弃(0否 1是 )

	private String abandonType;		// 放弃类型 0-承接前放弃 1-工程单放弃 2零售单放弃
	private String abandonReason;		// 放弃原因
	private Date abandonTime;		// 放弃时间
	private String abandonRemark;   //经销商放弃备注原因

	private String orderId;



	//新增字段
	private String smallMeshCode;  //网格小微编码
	private String gridCode; //网格编码
	private String screenStstus; //筛选状态(0网格，1中心，2全国)
	private String isValid;  //是否有效（是否在抢单池中可见） 0否 1是
	private Integer shareCount; //共享数量

	public String getSmallMeshCode() {
		return smallMeshCode;
	}

	public void setSmallMeshCode(String smallMeshCode) {
		this.smallMeshCode = smallMeshCode;
	}

	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}

	public String getScreenStstus() {
		return screenStstus;
	}

	public void setScreenStstus(String screenStstus) {
		this.screenStstus = screenStstus;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(String tradeCount) {
		this.tradeCount = tradeCount;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getImageUrl() {
		if(StringUtils.isEmpty(imageUrl)){
			imageUrl = "";
		}
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsAbandon() {
		return isAbandon;
	}

	public void setIsAbandon(String isAbandon) {
		this.isAbandon = isAbandon;
	}

	public String getAbandonType() {
		return abandonType;
	}

	public void setAbandonType(String abandonType) {
		this.abandonType = abandonType;
	}

	public String getAbandonReason() {
		return abandonReason;
	}

	public void setAbandonReason(String abandonReason) {
		this.abandonReason = abandonReason;
	}

	public Date getAbandonTime() {
		return abandonTime;
	}

	public void setAbandonTime(Date abandonTime) {
		this.abandonTime = abandonTime;
	}

	public String getAbandonRemark() {
		return abandonRemark;
	}

	public void setAbandonRemark(String abandonRemark) {
		this.abandonRemark = abandonRemark;
	}

	public String getUndertake() {
		if(StringUtils.isEmpty(undertake)){
			undertake = "";
		}
		return undertake;
	}

	public void setUndertake(String undertake) {
		this.undertake = undertake;
	}

	public String getIsAll() {
        return isAll;
    }

    public void setIsAll(String isAll) {
        this.isAll = isAll;
    }

    public String getCustomerMsgId() {
		return customerMsgId;
	}

	public void setCustomerMsgId(String customerMsgId) {
		this.customerMsgId = customerMsgId;
	}

	public String getIfTopCustomer() {
        return ifTopCustomer;
    }

    public void setIfTopCustomer(String ifTopCustomer) {
        this.ifTopCustomer = ifTopCustomer;
    }

    public EucMsg() {
		super();
	}

	public EucMsg(String id){
		super(id);
	}

	@ExcelField(title="需求id", align=2, sort=1)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@ExcelField(title="商机编码", align=2, sort=2)
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	
	@ExcelField(title="商机名称", align=2, sort=3)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	@ExcelField(title="商机来源", align=2, sort=4)
	public String getBusinessSource() {
		return businessSource;
	}

	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}
	
	@ExcelField(title="商机类型", align=2, sort=5)
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@ExcelField(title="top客户简称", align=2, sort=6)
	public String getTopCustomerAbbreviation() {
		return topCustomerAbbreviation;
	}

	public void setTopCustomerAbbreviation(String topCustomerAbbreviation) {
		this.topCustomerAbbreviation = topCustomerAbbreviation;
	}
	
	@ExcelField(title="甲方公司名称", align=2, sort=7)
	public String getCompanyNameA() {
		return companyNameA;
	}

	public void setCompanyNameA(String companyNameA) {
		this.companyNameA = companyNameA;
	}
	
	@ExcelField(title="甲方联系姓名", align=2, sort=8)
	public String getContactNameA() {
		return contactNameA;
	}

	public void setContactNameA(String contactNameA) {
		this.contactNameA = contactNameA;
	}
	
	@ExcelField(title="甲方联系电话", align=2, sort=9)
	public String getContactMobileA() {
		return contactMobileA;
	}

	public void setContactMobileA(String contactMobileA) {
		this.contactMobileA = contactMobileA;
	}
	
	@ExcelField(title="行业", align=2, sort=10)
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@ExcelField(title="状态", align=2, sort=11)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@ExcelField(title="海尔战区", align=2, sort=12)
	public String getHaierTheater() {
		return haierTheater;
	}

	public void setHaierTheater(String haierTheater) {
		this.haierTheater = haierTheater;
	}
	
	@ExcelField(title="中心", align=2, sort=13)
	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}
	
	@ExcelField(title="网格小微", align=2, sort=14)
	public String getSmallMesh() {
		return smallMesh;
	}

	public void setSmallMesh(String smallMesh) {
		this.smallMesh = smallMesh;
	}
	
	@ExcelField(title="网格", align=2, sort=15)
	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}
	
	@ExcelField(title="省份", align=2, sort=16)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@ExcelField(title="省份id", align=2, sort=17)
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	@ExcelField(title="城市	", align=2, sort=18)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@ExcelField(title="城市id ", align=2, sort=19)
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@ExcelField(title="区县", align=2, sort=20)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	@ExcelField(title="区县_id", align=2, sort=21)
	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	
	@ExcelField(title="商机地址", align=2, sort=22)
	public String getBusinessOpportunityAddress() {
		return businessOpportunityAddress;
	}

	public void setBusinessOpportunityAddress(String businessOpportunityAddress) {
		this.businessOpportunityAddress = businessOpportunityAddress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最近处理时间", align=2, sort=23)
	public Date getLastProcessingTime() {
		return lastProcessingTime;
	}

	public void setLastProcessingTime(Date lastProcessingTime) {
		this.lastProcessingTime = lastProcessingTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="录入日期", align=2, sort=24)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	@ExcelField(title="海尔接口人编码", align=2, sort=25)
	public String getProjectManagerCode() {
		return projectManagerCode;
	}

	public void setProjectManagerCode(String projectManagerCode) {
		this.projectManagerCode = projectManagerCode;
	}
	
	@ExcelField(title="海尔接口人姓名", align=2, sort=26)
	public String getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}
	
	@ExcelField(title="承接商编码", align=2, sort=27)
	public String getContractorCode() {
		return contractorCode;
	}

	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}
	
	@ExcelField(title="承接商名称", align=2, sort=28)
	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	
	@ExcelField(title="承接商大渠道", align=2, sort=29)
	public String getBusinessChannel() {
		return businessChannel;
	}

	public void setBusinessChannel(String businessChannel) {
		this.businessChannel = businessChannel;
	}
	
	@ExcelField(title="竞争对手公司", align=2, sort=30)
	public String getCompetitors() {
		return competitors;
	}

	public void setCompetitors(String competitors) {
		this.competitors = competitors;
	}
	
	@ExcelField(title="是否成功传输hps 0否 1是", align=2, sort=33)
	public String getIsSendHps() {
		return isSendHps;
	}

	public void setIsSendHps(String isSendHps) {
		this.isSendHps = isSendHps;
	}
	
	@ExcelField(title="项目编码(hps 回传)", align=2, sort=34)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }
}