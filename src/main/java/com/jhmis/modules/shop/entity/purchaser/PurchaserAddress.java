/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.purchaser;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 采购商地址管理Entity
 * @author tity
 * @version 2018-07-22
 */
public class PurchaserAddress extends DataEntity<PurchaserAddress> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserId;		// 供应商ID
	private String trueName;		// 会员姓名
	private String provinceId;		// 地区ID
	private String cityId;		// 市级ID
	private String districtId;		// 区ID
	private String areaInfo;		// 省市区汉字
	private String detailAddress;		// 详细地址
	private String telPhone;		// 座机电话
	private String mobilePhone;		// 手机电话
	private String isDefault;		// 0 否  1  是默认地址   
	private String zipCode;		// 邮编
	private String companyName;//公司名称

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public PurchaserAddress() {
		super();
	}

	public PurchaserAddress(String id){
		super(id);
	}

	@ExcelField(title="供应商ID", align=2, sort=1)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="会员姓名", align=2, sort=2)
	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	
	@ExcelField(title="地区ID", align=2, sort=3)
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	@ExcelField(title="市级ID", align=2, sort=4)
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@ExcelField(title="区ID", align=2, sort=5)
	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	
	@ExcelField(title="省市区汉字", align=2, sort=6)
	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}
	
	@ExcelField(title="详细地址", align=2, sort=7)
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	
	@ExcelField(title="座机电话", align=2, sort=8)
	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	
	@ExcelField(title="手机电话", align=2, sort=9)
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@ExcelField(title="0 否  1  是默认地址   ", dictType="yes_no", align=2, sort=10)
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	@ExcelField(title="邮编", align=2, sort=11)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}