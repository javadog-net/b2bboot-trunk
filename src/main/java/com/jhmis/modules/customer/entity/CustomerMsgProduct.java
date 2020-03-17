/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 客单产品关联表Entity
 * @author hdx
 * @version 2019-04-16
 */
public class CustomerMsgProduct extends DataEntity<CustomerMsgProduct> {
	
	private static final long serialVersionUID = 1L;
	private String customerMsgId;		// 客单需求订单号
	private String productGroup;		// 产品组
	private String productGroupName;		// 产品组名称

	private String brand;		// 品牌
	private String beWisdom;		// 是否智慧
	private String estimatedQuantity;		// 预计数量
	private String purchaseBudget;		// 采购预算（元）

	public CustomerMsgProduct() {
		super();
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public CustomerMsgProduct(String id){
		super(id);
	}

	@ExcelField(title="客单需求订单号", align=2, sort=1)
	public String getCustomerMsgId() {
		return customerMsgId;
	}

	public void setCustomerMsgId(String customerMsgId) {
		this.customerMsgId = customerMsgId;
	}
	
	@ExcelField(title="产品组", align=2, sort=2)
	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	
	@ExcelField(title="品牌", align=2, sort=3)
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@ExcelField(title="是否智慧", align=2, sort=4)
	public String getBeWisdom() {
		return beWisdom;
	}

	public void setBeWisdom(String beWisdom) {
		this.beWisdom = beWisdom;
	}
	
	@ExcelField(title="预计数量", align=2, sort=5)
	public String getEstimatedQuantity() {
		return estimatedQuantity;
	}

	public void setEstimatedQuantity(String estimatedQuantity) {
		this.estimatedQuantity = estimatedQuantity;
	}
	
	@ExcelField(title="采购预算（元）", align=2, sort=6)
	public String getPurchaseBudget() {
		return purchaseBudget;
	}

	public void setPurchaseBudget(String purchaseBudget) {
		this.purchaseBudget = purchaseBudget;
	}
	
}