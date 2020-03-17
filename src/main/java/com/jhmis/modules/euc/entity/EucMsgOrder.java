/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * euc订单Entity
 * @author hdx
 * @version 2019-12-25
 */
public class EucMsgOrder extends DataEntity<EucMsgOrder> {
	
	private static final long serialVersionUID = 1L;
	private String eucId;		// euc需求id
	private Date createTime;		// 生成时间
	private String contractorCode;		// 经销商编码
	private String contractorName;		// 经销商名称
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
	private String businessChannel;		// 承接商大渠道
	private String competitors;		// 竞争对手公司
	private String isSendHps;		// 是否成功传输hps 0否 1是
	private String projectCode;		// 项目编码(hps 回传)
	private String ifTopCustomer;   //是否top
	private String customerMsgId;	//客单报备信息id
	//是否是承接方式前废弃
    private String noUndertake;
	//经销商放弃自主录入
	private String abandonWrite;

	//新增字段
	private String authorityStatus; //授权状态 (DEFAULT待授权， AUTHORIZED已授权， UNAUTHORIZED未获得授权[7天未处理])
	private String authorityRemark; //授权备注
	private Date authorityTime; //授权时间
	private String authorityRecord; //授权记录 (1一次授权，2申诉授权)
	private String appealStatus; //APPEAL_AUDITING申诉待审核，APPEAL_SUCCESS申诉通过， APPEAL_FAILED申诉未通过
	private String appealContent; //申诉内容
	private String appealFiles; //申诉文件
	private Date appealTime; //申诉时间
	private String appealRemark; //申诉备注(可做申诉失败原因)

	public String getAuthorityStatus() {
		return authorityStatus;
	}

	public void setAuthorityStatus(String authorityStatus) {
		this.authorityStatus = authorityStatus;
	}

	public String getAuthorityRemark() {
		return authorityRemark;
	}

	public void setAuthorityRemark(String authorityRemark) {
		this.authorityRemark = authorityRemark;
	}

	public Date getAuthorityTime() {
		return authorityTime;
	}

	public void setAuthorityTime(Date authorityTime) {
		this.authorityTime = authorityTime;
	}

	public String getAuthorityRecord() {
		return authorityRecord;
	}

	public void setAuthorityRecord(String authorityRecord) {
		this.authorityRecord = authorityRecord;
	}

	public String getAppealStatus() {
		return appealStatus;
	}

	public void setAppealStatus(String appealStatus) {
		this.appealStatus = appealStatus;
	}

	public String getAppealContent() {
		return appealContent;
	}

	public void setAppealContent(String appealContent) {
		this.appealContent = appealContent;
	}

	public String getAppealFiles() {
		return appealFiles;
	}

	public void setAppealFiles(String appealFiles) {
		this.appealFiles = appealFiles;
	}

	public Date getAppealTime() {
		return appealTime;
	}

	public void setAppealTime(Date appealTime) {
		this.appealTime = appealTime;
	}

	public String getAppealRemark() {
		return appealRemark;
	}

	public void setAppealRemark(String appealRemark) {
		this.appealRemark = appealRemark;
	}

	public String getAbandonWrite() {
		return abandonWrite;
	}

	public void setAbandonWrite(String abandonWrite) {
		this.abandonWrite = abandonWrite;
	}

	public String getAbandonRemark() {
		return abandonRemark;
	}

	public void setAbandonRemark(String abandonRemark) {
		this.abandonRemark = abandonRemark;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getNoUndertake() {
		return noUndertake;
	}

	public void setNoUndertake(String noUndertake) {
		this.noUndertake = noUndertake;
	}

	public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessSource() {
		return businessSource;
	}

	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCompanyNameA() {
		return companyNameA;
	}

	public void setCompanyNameA(String companyNameA) {
		this.companyNameA = companyNameA;
	}

	public String getContactNameA() {
		return contactNameA;
	}

	public void setContactNameA(String contactNameA) {
		this.contactNameA = contactNameA;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getBusinessOpportunityAddress() {
		return businessOpportunityAddress;
	}

	public void setBusinessOpportunityAddress(String businessOpportunityAddress) {
		this.businessOpportunityAddress = businessOpportunityAddress;
	}

	public EucMsgOrder() {
		super();
	}

	public EucMsgOrder(String id){
		super(id);
	}

	@ExcelField(title="euc需求id", align=2, sort=1)
	public String getEucId() {
		return eucId;
	}

	public void setEucId(String eucId) {
		this.eucId = eucId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="生成时间", align=2, sort=2)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ExcelField(title="经销商编码", align=2, sort=3)
	public String getContractorCode() {
		return contractorCode;
	}

	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}
	
	@ExcelField(title="经销商名称", align=2, sort=4)
	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	
	@ExcelField(title="承接方式0工程1零售", align=2, sort=5)
	public String getUndertake() {
		return undertake;
	}

	public void setUndertake(String undertake) {
		this.undertake = undertake;
	}
	
	@ExcelField(title="交易金额", align=2, sort=6)
	public String getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(String tradeCount) {
		this.tradeCount = tradeCount;
	}
	
	@ExcelField(title="订单类型(0派单 1抢单 )", align=2, sort=7)
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@ExcelField(title="中心工贸", align=2, sort=8)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@ExcelField(title="平台审核是否通过0 是 1否", align=2, sort=9)
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@ExcelField(title="平台审核人", align=2, sort=10)
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@ExcelField(title="是否中标 0否 1是", align=2, sort=11)
	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="平台审核时间", align=2, sort=12)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@ExcelField(title="上传凭证", align=2, sort=13)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=14)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@ExcelField(title="是否放弃(0否 1是 )", align=2, sort=15)
	public String getIsAbandon() {
		return isAbandon;
	}

	public void setIsAbandon(String isAbandon) {
		this.isAbandon = isAbandon;
	}
	
	@ExcelField(title="放弃类型 0-承接前放弃 1-工程单放弃 2零售单放弃", align=2, sort=16)
	public String getAbandonType() {
		return abandonType;
	}

	public void setAbandonType(String abandonType) {
		this.abandonType = abandonType;
	}
	
	@ExcelField(title="放弃原因", align=2, sort=17)
	public String getAbandonReason() {
		return abandonReason;
	}

	public void setAbandonReason(String abandonReason) {
		this.abandonReason = abandonReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="放弃时间", align=2, sort=18)
	public Date getAbandonTime() {
		return abandonTime;
	}

	public void setAbandonTime(Date abandonTime) {
		this.abandonTime = abandonTime;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getTopCustomerAbbreviation() {
		return topCustomerAbbreviation;
	}

	public void setTopCustomerAbbreviation(String topCustomerAbbreviation) {
		this.topCustomerAbbreviation = topCustomerAbbreviation;
	}

	public String getContactMobileA() {
		return contactMobileA;
	}

	public void setContactMobileA(String contactMobileA) {
		this.contactMobileA = contactMobileA;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getHaierTheater() {
		return haierTheater;
	}

	public void setHaierTheater(String haierTheater) {
		this.haierTheater = haierTheater;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getSmallMesh() {
		return smallMesh;
	}

	public void setSmallMesh(String smallMesh) {
		this.smallMesh = smallMesh;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public Date getLastProcessingTime() {
		return lastProcessingTime;
	}

	public void setLastProcessingTime(Date lastProcessingTime) {
		this.lastProcessingTime = lastProcessingTime;
	}

	public String getProjectManagerCode() {
		return projectManagerCode;
	}

	public void setProjectManagerCode(String projectManagerCode) {
		this.projectManagerCode = projectManagerCode;
	}

	public String getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}

	public String getBusinessChannel() {
		return businessChannel;
	}

	public void setBusinessChannel(String businessChannel) {
		this.businessChannel = businessChannel;
	}

	public String getCompetitors() {
		return competitors;
	}

	public void setCompetitors(String competitors) {
		this.competitors = competitors;
	}

	public String getIsSendHps() {
		return isSendHps;
	}

	public void setIsSendHps(String isSendHps) {
		this.isSendHps = isSendHps;
	}

	public String getIfTopCustomer() {
		return ifTopCustomer;
	}

	public void setIfTopCustomer(String ifTopCustomer) {
		this.ifTopCustomer = ifTopCustomer;
	}

	public String getCustomerMsgId() {
		return customerMsgId;
	}

	public void setCustomerMsgId(String customerMsgId) {
		this.customerMsgId = customerMsgId;
	}
}