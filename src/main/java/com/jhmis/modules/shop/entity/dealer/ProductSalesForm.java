/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.dealer;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 销售样表Entity
 * @author tity
 * @version 2018-07-22
 */
public class ProductSalesForm extends DataEntity<ProductSalesForm> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 物料号
	private String salesOrganization;		// 销售组织
	private String salesChannel;		// 大渠道
	private String salesChannel2;		// 小渠道
	private String marketLevel;		// 市场级别
	private String customerCode;		// 客户号
	private Date soCreateClosure;		// 订单创建截止日期
	private Date activeDateBegin;		// 有效开始日期
	private Date activeDateEnd;		// 有效截止日期
	private Date lastUpd;		// 更新时间
	private String deleteFlag;		// 是否删除
	private String department;		// 产品组
	private String combineType;		// 组合类型
	private String putMethods;		// 自有渠道投放方式
	private String isProdTemplate;		// 是否只消化库存
	
	public ProductSalesForm() {
		super();
	}

	public ProductSalesForm(String id){
		super(id);
	}

	@ExcelField(title="物料号", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="销售组织", align=2, sort=2)
	public String getSalesOrganization() {
		return salesOrganization;
	}

	public void setSalesOrganization(String salesOrganization) {
		this.salesOrganization = salesOrganization;
	}
	
	@ExcelField(title="大渠道", align=2, sort=3)
	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	
	@ExcelField(title="小渠道", align=2, sort=4)
	public String getSalesChannel2() {
		return salesChannel2;
	}

	public void setSalesChannel2(String salesChannel2) {
		this.salesChannel2 = salesChannel2;
	}
	
	@ExcelField(title="市场级别", align=2, sort=5)
	public String getMarketLevel() {
		return marketLevel;
	}

	public void setMarketLevel(String marketLevel) {
		this.marketLevel = marketLevel;
	}
	
	@ExcelField(title="客户号", align=2, sort=6)
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="订单创建截止日期", align=2, sort=7)
	public Date getSoCreateClosure() {
		return soCreateClosure;
	}

	public void setSoCreateClosure(Date soCreateClosure) {
		this.soCreateClosure = soCreateClosure;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="有效开始日期", align=2, sort=8)
	public Date getActiveDateBegin() {
		return activeDateBegin;
	}

	public void setActiveDateBegin(Date activeDateBegin) {
		this.activeDateBegin = activeDateBegin;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="有效截止日期", align=2, sort=9)
	public Date getActiveDateEnd() {
		return activeDateEnd;
	}

	public void setActiveDateEnd(Date activeDateEnd) {
		this.activeDateEnd = activeDateEnd;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=10)
	public Date getLastUpd() {
		return lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
	}
	
	@ExcelField(title="是否删除", dictType="del_flag", align=2, sort=11)
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@ExcelField(title="产品组", align=2, sort=12)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	@ExcelField(title="组合类型", dictType="sale_form_combine_type", align=2, sort=13)
	public String getCombineType() {
		return combineType;
	}

	public void setCombineType(String combineType) {
		this.combineType = combineType;
	}
	
	@ExcelField(title="自有渠道投放方式", dictType="sale_form_put_methods", align=2, sort=14)
	public String getPutMethods() {
		return putMethods;
	}

	public void setPutMethods(String putMethods) {
		this.putMethods = putMethods;
	}
	
	@ExcelField(title="是否只消化库存", dictType="yes_no", align=2, sort=15)
	public String getIsProdTemplate() {
		return isProdTemplate;
	}

	public void setIsProdTemplate(String isProdTemplate) {
		this.isProdTemplate = isProdTemplate;
	}
	
}