/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * MDM四方关系Entity
 * @author hdx
 * @version 2019-03-27
 */
public class MdmCustomersPartner extends DataEntity<MdmCustomersPartner> {
	
	private static final long serialVersionUID = 1L;
	private String customerNumber;		// 售达方编码
	private String customerName1;		// 售达方名称
	private String custPartnerType;		// 合作伙伴类型(SH为送达方 PY付款方 SP售达方 BP开票方) 
	private String custPartnerSubjectId;		// 合作伙伴编码
	private String subCustomerName1;		// 合作伙伴名称
	private String salesGroup;		// 销售组织
	private String partnerRowId;		// 伙伴关系ID(主键)
	private Date lastUpd;		// 最后更新日期MDM
	private String customerFlag;		// 客户标记
	private String custDeleteFlag;		// 客户删除标记
	private String salesDeleteFlag;		// 销售组织删除标记
	private String partnerDeleteFlag;		// 合作伙伴关系的冻结标记
	private String subCustomerDeleteFlag;		// 合作伙伴自身的冻结标记
	
	public MdmCustomersPartner() {
		super();
	}

	public MdmCustomersPartner(String id){
		super(id);
	}

	@ExcelField(title="售达方编码", align=2, sort=1)
	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	@ExcelField(title="售达方名称", align=2, sort=2)
	public String getCustomerName1() {
		return customerName1;
	}

	public void setCustomerName1(String customerName1) {
		this.customerName1 = customerName1;
	}
	
	@ExcelField(title="合作伙伴类型(SH为送达方 PY付款方 SP售达方 BP开票方) ", align=2, sort=3)
	public String getCustPartnerType() {
		return custPartnerType;
	}

	public void setCustPartnerType(String custPartnerType) {
		this.custPartnerType = custPartnerType;
	}
	
	@ExcelField(title="合作伙伴编码", align=2, sort=4)
	public String getCustPartnerSubjectId() {
		return custPartnerSubjectId;
	}

	public void setCustPartnerSubjectId(String custPartnerSubjectId) {
		this.custPartnerSubjectId = custPartnerSubjectId;
	}
	
	@ExcelField(title="合作伙伴名称", align=2, sort=5)
	public String getSubCustomerName1() {
		return subCustomerName1;
	}

	public void setSubCustomerName1(String subCustomerName1) {
		this.subCustomerName1 = subCustomerName1;
	}
	
	@ExcelField(title="销售组织", align=2, sort=6)
	public String getSalesGroup() {
		return salesGroup;
	}

	public void setSalesGroup(String salesGroup) {
		this.salesGroup = salesGroup;
	}
	
	@ExcelField(title="伙伴关系ID(主键)", align=2, sort=7)
	public String getPartnerRowId() {
		return partnerRowId;
	}

	public void setPartnerRowId(String partnerRowId) {
		this.partnerRowId = partnerRowId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后更新日期MDM", align=2, sort=8)
	public Date getLastUpd() {
		return lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}
	
	@ExcelField(title="客户标记", align=2, sort=9)
	public String getCustomerFlag() {
		return customerFlag;
	}

	public void setCustomerFlag(String customerFlag) {
		this.customerFlag = customerFlag;
	}
	
	@ExcelField(title="客户删除标记", align=2, sort=10)
	public String getCustDeleteFlag() {
		return custDeleteFlag;
	}

	public void setCustDeleteFlag(String custDeleteFlag) {
		this.custDeleteFlag = custDeleteFlag;
	}
	
	@ExcelField(title="销售组织删除标记", align=2, sort=11)
	public String getSalesDeleteFlag() {
		return salesDeleteFlag;
	}

	public void setSalesDeleteFlag(String salesDeleteFlag) {
		this.salesDeleteFlag = salesDeleteFlag;
	}
	
	@ExcelField(title="合作伙伴关系的冻结标记", align=2, sort=12)
	public String getPartnerDeleteFlag() {
		return partnerDeleteFlag;
	}

	public void setPartnerDeleteFlag(String partnerDeleteFlag) {
		this.partnerDeleteFlag = partnerDeleteFlag;
	}
	
	@ExcelField(title="合作伙伴自身的冻结标记", align=2, sort=13)
	public String getSubCustomerDeleteFlag() {
		return subCustomerDeleteFlag;
	}

	public void setSubCustomerDeleteFlag(String subCustomerDeleteFlag) {
		this.subCustomerDeleteFlag = subCustomerDeleteFlag;
	}
	
}