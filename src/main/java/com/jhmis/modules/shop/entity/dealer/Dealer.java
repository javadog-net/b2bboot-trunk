/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.dealer;

import org.hibernate.validator.constraints.Email;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 供应商管理Entity
 * @author tity
 * @version 2018-07-29
 */
public class Dealer extends DataEntity<Dealer> {
	
	private static final long serialVersionUID = 1L;
	private String companyCode;		// 经销商编码
	private String companyName;		// 经销商名称
	private String companyNum;		// 公司人数
	private String contacts;		// 公司联系人
	private String tel;		// 公司联系人电话
	private String mobile;		// 手机
	private String email;		// 邮箱
	private String zipCode;		// 邮编
	private String provinceId;		// 省
	private String cityId;		// 市
	private String districtId;		// 区
	private String areaInfo;		// 所在地区
	private String detailAddress;		// 详细地址
	private String channelName;		// 渠道名称(区分88码用户性质，如客单报备,直采，抢派单流程)
	private String taxCode;		// 税码
	private String kjtAccount;		// 快捷通账号
	private String electronicUrl;		// 营业执照电子版
	private String idCardUrl;		// 身份证电子版
	private String legalPersonName;		// 法人姓名
	private String legalPersonIdCard;		// 法人身份号
	private String companyTel;		// 公司电话
	private String electronicLicense;		// 开户许可证电子版
	private String logoUrl;		// 企业logo地址
	private String gmId;		// 所属工贸
	private String isClosed;		// 是否关闭 
	private String isStore;		// 是否开店
	private String isSelf;		// 是否自营
	private String undertakeArea;		// 承接区域
	private String auditState;		// 审核状态
	private Date auditTime;		// 审核时间 
	private String auditor;		// 审核人
	private String auditDesc;		// 审核意见
	private String underProductIstotal;		// 承接品类是否是所有0否 1是(一期）
	private String instruct;		// 行业类别(一期）
	private String gmName;		// 工贸名称(二期新增)
	private String score;		// 经销商积分(一期）underProductIstotal
	private String allowPop;		// 是否允许经销商开店（0 否 1 是）
	private String allowDispatch;	// 是否允许抢派单（0 否 1 是）
	private String allowDemand;		// 是否允许需求报备（0 否 1 是）
	private String dealerType;		// 经销商类型(暂时保留)
	private String underProduct;		// 承接品类
	private String acgPassword;//acg传入密码
	private String customerCategory;// 客户性质大类
	private String industryClass;// 客户性质小类
	private String mdmProvince;//省
	private String mdmCity;//市
	private String mdmArea;//区
	private String wgname;//网格名称
	private String wgcode;//网格编码

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public String getIndustryClass() {
		return industryClass;
	}

	public void setIndustryClass(String industryClass) {
		this.industryClass = industryClass;
	}

	public String getAcgPassword() {
		return acgPassword;
	}

	public void setAcgPassword(String acgPassword) {
		this.acgPassword = acgPassword;
	}

	public Dealer() {
		super();
	}

	public Dealer(String id){
		super(id);
	}

	@ExcelField(title="经销商编码", align=2, sort=1)
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	@ExcelField(title="经销商名称", align=2, sort=2)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="公司人数", align=2, sort=3)
	public String getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}
	
	@ExcelField(title="公司联系人", align=2, sort=4)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@ExcelField(title="公司联系人电话", align=2, sort=5)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@ExcelField(title="手机", align=2, sort=6)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="邮箱", align=2, sort=7)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="邮编", align=2, sort=8)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@ExcelField(title="省", align=2, sort=9)
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	@ExcelField(title="市", align=2, sort=10)
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@ExcelField(title="区", align=2, sort=11)
	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	
	@ExcelField(title="所在地区", align=2, sort=12)
	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}
	
	@ExcelField(title="详细地址", align=2, sort=13)
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	@ExcelField(title="渠道名称(区分88码用户性质，如客单报备,直采，抢派单流程)", align=2, sort=14)
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	@ExcelField(title="税码", align=2, sort=15)
	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	
	@ExcelField(title="快捷通账号", align=2, sort=16)
	public String getKjtAccount() {
		return kjtAccount;
	}

	public void setKjtAccount(String kjtAccount) {
		this.kjtAccount = kjtAccount;
	}
	
	@ExcelField(title="营业执照电子版", align=2, sort=17)
	public String getElectronicUrl() {
		return electronicUrl;
	}

	public void setElectronicUrl(String electronicUrl) {
		this.electronicUrl = electronicUrl;
	}
	
	@ExcelField(title="身份证电子版", align=2, sort=18)
	public String getIdCardUrl() {
		return idCardUrl;
	}

	public void setIdCardUrl(String idCardUrl) {
		this.idCardUrl = idCardUrl;
	}
	
	@ExcelField(title="法人姓名", align=2, sort=19)
	public String getLegalPersonName() {
		return legalPersonName;
	}

	public void setLegalPersonName(String legalPersonName) {
		this.legalPersonName = legalPersonName;
	}
	
	@ExcelField(title="法人身份号", align=2, sort=20)
	public String getLegalPersonIdCard() {
		return legalPersonIdCard;
	}

	public void setLegalPersonIdCard(String legalPersonIdCard) {
		this.legalPersonIdCard = legalPersonIdCard;
	}
	
	@ExcelField(title="公司电话", align=2, sort=21)
	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	
	@ExcelField(title="开户许可证电子版", align=2, sort=22)
	public String getElectronicLicense() {
		return electronicLicense;
	}

	public void setElectronicLicense(String electronicLicense) {
		this.electronicLicense = electronicLicense;
	}
	
	@ExcelField(title="企业logo地址", align=2, sort=23)
	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
	@ExcelField(title="所属工贸", align=2, sort=24)
	public String getGmId() {
		return gmId;
	}

	public void setGmId(String gmId) {
		this.gmId = gmId;
	}
	
	@ExcelField(title="是否关闭 ", dictType="", align=2, sort=25)
	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
	
	@ExcelField(title="是否开店", dictType="", align=2, sort=26)
	public String getIsStore() {
		return isStore;
	}

	public void setIsStore(String isStore) {
		this.isStore = isStore;
	}
	
	@ExcelField(title="是否自营", dictType="", align=2, sort=27)
	public String getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(String isSelf) {
		this.isSelf = isSelf;
	}
	
	@ExcelField(title="承接区域", dictType="", align=2, sort=28)
	public String getUndertakeArea() {
		return undertakeArea;
	}

	public void setUndertakeArea(String undertakeArea) {
		this.undertakeArea = undertakeArea;
	}
	
	@ExcelField(title="审核状态", align=2, sort=34)
	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间 ", align=2, sort=35)
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@ExcelField(title="审核人", align=2, sort=36)
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@ExcelField(title="审核意见", align=2, sort=37)
	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}
	
	@ExcelField(title="承接品类是否是所有0否 1是(一期）", dictType="", align=2, sort=38)
	public String getUnderProductIstotal() {
		return underProductIstotal;
	}

	public void setUnderProductIstotal(String underProductIstotal) {
		this.underProductIstotal = underProductIstotal;
	}
	
	@ExcelField(title="行业类别(一期）", align=2, sort=39)
	public String getInstruct() {
		return instruct;
	}

	public void setInstruct(String instruct) {
		this.instruct = instruct;
	}
	
	@ExcelField(title="工贸名称(二期新增)", align=2, sort=40)
	public String getGmName() {
		return gmName;
	}

	public void setGmName(String gmName) {
		this.gmName = gmName;
	}
	
	@ExcelField(title="经销商积分(一期）", align=2, sort=41)
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@ExcelField(title="是否允许经销商开店（0 否 1 是）", dictType="", align=2, sort=42)
	public String getAllowPop() {
		return allowPop;
	}

	public void setAllowPop(String allowPop) {
		this.allowPop = allowPop;
	}
	
	@ExcelField(title="是否允许抢派单（0 否 1 是）", dictType="", align=2, sort=43)
	public String getAllowDispatch() {
		return allowDispatch;
	}

	public void setAllowDispatch(String allowDispatch) {
		this.allowDispatch = allowDispatch;
	}
	
	@ExcelField(title="是否允许需求报备（0 否 1 是）", dictType="", align=2, sort=44)
	public String getAllowDemand() {
		return allowDemand;
	}

	public void setAllowDemand(String allowDemand) {
		this.allowDemand = allowDemand;
	}
		
	@ExcelField(title="经销商类型(暂时保留)", align=2, sort=45)
	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}
	
	@ExcelField(title="承接品类", align=2, sort=46)
	public String getUnderProduct() {
		return underProduct;
	}

	public void setUnderProduct(String underProduct) {
		this.underProduct = underProduct;
	}		
	
	public String getMdmProvince() {
		return mdmProvince;
	}

	public void setMdmProvince(String mdmProvince) {
		this.mdmProvince = mdmProvince;
	}

	public String getMdmCity() {
		return mdmCity;
	}

	public void setMdmCity(String mdmCity) {
		this.mdmCity = mdmCity;
	}

	public String getMdmArea() {
		return mdmArea;
	}

	public void setMdmArea(String mdmArea) {
		this.mdmArea = mdmArea;
	}

	public String getWgname() {
		return wgname;
	}

	public void setWgname(String wgname) {
		this.wgname = wgname;
	}

	public String getWgcode() {
		return wgcode;
	}

	public void setWgcode(String wgcode) {
		this.wgcode = wgcode;
	}

	@Override
	public String toString() {
		return "Dealer{" +
				"companyCode='" + companyCode + '\'' +
				", companyName='" + companyName + '\'' +
				", contacts='" + contacts + '\'' +
				", mobile='" + mobile + '\'' +
				", email='" + email + '\'' +
				", acgPassword='" + acgPassword + '\'' +
				", gmName='" + gmName + '\'' +
				'}';
	}
}