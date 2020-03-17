/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 直采专区mdm主数据源Entity
 * @author hdx
 * @version 2019-03-26
 */
public class MdmCustomersSource extends DataEntity<MdmCustomersSource> {
	
	private static final long serialVersionUID = 1L;
	private String comCode;		// 公司代码
	private String cusCode;		// 客户编码
	private String accountGroup;		// 科目组
	private String marketArea;		// 客户属性 如日日顺(R)、特种电器、成套、自营店(A)
	private String comName;		// 公司名称
	private String cusName;		// 简称
	private String cusAbbName;		// 全称
	private String oldCustCode;		// 老编码
	private String kind;		// 客户属性
	private String kindName;		// 属性名称
	private String address;		// 城市街道/房号
	private String street;		// 街道/门牌号 
	private String post;		// 邮政编码
	private String linkman;		// 联系人
	private String tel;		// 电话
	private String fax;		// 传真
	private String eMail;		// 邮箱
	private String bankL;		// 银行码
	private String bankN;		// 银行名称
	private String bankA;		// 银行账户
	private String orgId;		// 组织机构代码
	private String tax;		// 税码
	private String enrBankroll;		// 注册资金
	private String creditGrade;		// 信用等级
	private String channel;		// 请使用KIND(渠道类别)
	private String areaName;		// 区域代码
	private String sysTrade;		// 所属系统
	private Date importDate;		// 录入日期
	private Date sysDates;		// 记录生成日期
	private String alterBy;		// 最后修改人
	private Date alterDate;		// 修改日期
	private String activeFlag;		// 使用标记
	private String lockFlag;		// 锁定标记
	private String deleteFlag;		// 删除标记
	private String operateStatus;		// 操作状态
	private String memo;		// 备注

	private String customerCategory;// 客户性质大类
	private String industryClass;// 客户性质小类
	private String mdmProvince;//省
	private String mdmCity;//市
	private String mdmArea;//区
	private String wgname;//网格名称
	private String wgcode;//网格编码

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

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

	public MdmCustomersSource() {
		super();
	}

	public MdmCustomersSource(String id){
		super(id);
	}

	@ExcelField(title="公司代码", align=2, sort=1)
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	
	@ExcelField(title="客户编码", align=2, sort=2)
	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	
	@ExcelField(title="科目组", align=2, sort=3)
	public String getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}
	
	@ExcelField(title="客户属性 如日日顺(R)、特种电器、成套、自营店(A)", align=2, sort=4)
	public String getMarketArea() {
		return marketArea;
	}

	public void setMarketArea(String marketArea) {
		this.marketArea = marketArea;
	}
	
	@ExcelField(title="公司名称", align=2, sort=5)
	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}
	
	@ExcelField(title="简称", align=2, sort=6)
	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	
	@ExcelField(title="全称", align=2, sort=7)
	public String getCusAbbName() {
		return cusAbbName;
	}

	public void setCusAbbName(String cusAbbName) {
		this.cusAbbName = cusAbbName;
	}
	
	@ExcelField(title="老编码", align=2, sort=8)
	public String getOldCustCode() {
		return oldCustCode;
	}

	public void setOldCustCode(String oldCustCode) {
		this.oldCustCode = oldCustCode;
	}
	
	@ExcelField(title="客户属性", align=2, sort=9)
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	@ExcelField(title="属性名称", align=2, sort=10)
	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	
	@ExcelField(title="城市街道/房号", align=2, sort=11)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="街道/门牌号 ", align=2, sort=12)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	
	@ExcelField(title="邮政编码", align=2, sort=13)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	@ExcelField(title="联系人", align=2, sort=14)
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	@ExcelField(title="电话", align=2, sort=15)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@ExcelField(title="传真", align=2, sort=16)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	@ExcelField(title="邮箱", align=2, sort=17)
	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	@ExcelField(title="银行码", align=2, sort=18)
	public String getBankL() {
		return bankL;
	}

	public void setBankL(String bankL) {
		this.bankL = bankL;
	}
	
	@ExcelField(title="银行名称", align=2, sort=19)
	public String getBankN() {
		return bankN;
	}

	public void setBankN(String bankN) {
		this.bankN = bankN;
	}
	
	@ExcelField(title="银行账户", align=2, sort=20)
	public String getBankA() {
		return bankA;
	}

	public void setBankA(String bankA) {
		this.bankA = bankA;
	}
	
	@ExcelField(title="组织机构代码", align=2, sort=21)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@ExcelField(title="税码", align=2, sort=22)
	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}
	
	@ExcelField(title="注册资金", align=2, sort=23)
	public String getEnrBankroll() {
		return enrBankroll;
	}

	public void setEnrBankroll(String enrBankroll) {
		this.enrBankroll = enrBankroll;
	}
	
	@ExcelField(title="信用等级", align=2, sort=24)
	public String getCreditGrade() {
		return creditGrade;
	}

	public void setCreditGrade(String creditGrade) {
		this.creditGrade = creditGrade;
	}
	
	@ExcelField(title="请使用KIND(渠道类别)", align=2, sort=25)
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@ExcelField(title="区域代码", align=2, sort=26)
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@ExcelField(title="所属系统", align=2, sort=27)
	public String getSysTrade() {
		return sysTrade;
	}

	public void setSysTrade(String sysTrade) {
		this.sysTrade = sysTrade;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="录入日期", align=2, sort=29)
	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="记录生成日期", align=2, sort=30)
	public Date getSysDates() {
		return sysDates;
	}

	public void setSysDates(Date sysDates) {
		this.sysDates = sysDates;
	}
	
	@ExcelField(title="最后修改人", align=2, sort=31)
	public String getAlterBy() {
		return alterBy;
	}

	public void setAlterBy(String alterBy) {
		this.alterBy = alterBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="修改日期", align=2, sort=32)
	public Date getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}
	
	@ExcelField(title="使用标记", align=2, sort=33)
	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	@ExcelField(title="锁定标记", align=2, sort=34)
	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}
	
	@ExcelField(title="删除标记", align=2, sort=35)
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@ExcelField(title="操作状态", align=2, sort=36)
	public String getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}
	
	@ExcelField(title="备注", align=2, sort=37)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
	
	
	
}