/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.purchaser;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 发票信息Entity
 * @author tity
 * @version 2018-08-14
 */
public class PurchaserInvoice extends DataEntity<PurchaserInvoice> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserId;		// 采购商
	private String kind;		// 发票类型  发票类型(1普通发票，2增值税发票，3电子发票)
	private String companyName;		// 单位名称
	private String taxCode;		// 纳税人识别号
	private String regAddr;		// 注册地址
	private String regPhone;		// 注册电话
	private String regBname;		// 开户银行
	private String regBaccount;		// 银行账户
	private String recName;		// 收票人姓名
	private String recMobphone;		// 收票人手机号
	private String recProvinceId;		// 省
	private String recCityId;		// 市
	private String recDistrictId;		// 区
	private String recAreaInfo;		// 收票人所在地区
	private String recDetailAddr;		// 送票详细地址
	private String isDefault;		// 是否默认
	
	public PurchaserInvoice() {
		super();
	}

	public PurchaserInvoice(String id){
		super(id);
	}

	@ExcelField(title="采购商", align=2, sort=1)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="发票类型", dictType="invoice_kind", align=2, sort=2)
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	@ExcelField(title="单位名称", align=2, sort=3)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="纳税人识别号", align=2, sort=4)
	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	
	@ExcelField(title="注册地址", align=2, sort=5)
	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}
	
	@ExcelField(title="注册电话", align=2, sort=6)
	public String getRegPhone() {
		return regPhone;
	}

	public void setRegPhone(String regPhone) {
		this.regPhone = regPhone;
	}
	
	@ExcelField(title="开户银行", align=2, sort=7)
	public String getRegBname() {
		return regBname;
	}

	public void setRegBname(String regBname) {
		this.regBname = regBname;
	}
	
	@ExcelField(title="银行账户", align=2, sort=8)
	public String getRegBaccount() {
		return regBaccount;
	}

	public void setRegBaccount(String regBaccount) {
		this.regBaccount = regBaccount;
	}
	
	@ExcelField(title="收票人姓名", align=2, sort=9)
	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}
	
	@ExcelField(title="收票人手机号", align=2, sort=10)
	public String getRecMobphone() {
		return recMobphone;
	}

	public void setRecMobphone(String recMobphone) {
		this.recMobphone = recMobphone;
	}
	
	@ExcelField(title="省", align=2, sort=11)
	public String getRecProvinceId() {
		return recProvinceId;
	}

	public void setRecProvinceId(String recProvinceId) {
		this.recProvinceId = recProvinceId;
	}
	
	@ExcelField(title="市", align=2, sort=12)
	public String getRecCityId() {
		return recCityId;
	}

	public void setRecCityId(String recCityId) {
		this.recCityId = recCityId;
	}
	
	@ExcelField(title="区", align=2, sort=13)
	public String getRecDistrictId() {
		return recDistrictId;
	}

	public void setRecDistrictId(String recDistrictId) {
		this.recDistrictId = recDistrictId;
	}
	
	@ExcelField(title="收票人所在地区", align=2, sort=14)
	public String getRecAreaInfo() {
		return recAreaInfo;
	}

	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}
	
	@ExcelField(title="送票详细地址", align=2, sort=15)
	public String getRecDetailAddr() {
		return recDetailAddr;
	}

	public void setRecDetailAddr(String recDetailAddr) {
		this.recDetailAddr = recDetailAddr;
	}
	
	@ExcelField(title="是否默认", dictType="yes_no", align=2, sort=16)
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
}