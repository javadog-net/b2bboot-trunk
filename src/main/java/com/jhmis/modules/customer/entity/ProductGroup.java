/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 产品组编码表Entity
 * @author hdx
 * @version 2019-04-17
 */
public class ProductGroup extends DataEntity<ProductGroup> {
	
	private static final long serialVersionUID = 1L;
	private String productGroupCode;		// 产品组编码
	private String productGroupName;		// 产品组名称
	private String industrialCoding;		// 产业编码
	private String industrialName;		// 产品组名称
	private String industrialAccount;		// 工贸账套(所在股份)
	private String transferSystem;		// 传送系统
	
	public ProductGroup() {
		super();
	}

	public ProductGroup(String id){
		super(id);
	}

	@ExcelField(title="产品组编码", align=2, sort=0)
	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}
	
	@ExcelField(title="产品组名称", align=2, sort=1)
	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	
	@ExcelField(title="产业编码", align=2, sort=2)
	public String getIndustrialCoding() {
		return industrialCoding;
	}

	public void setIndustrialCoding(String industrialCoding) {
		this.industrialCoding = industrialCoding;
	}
	
	@ExcelField(title="产品组名称", align=2, sort=3)
	public String getIndustrialName() {
		return industrialName;
	}

	public void setIndustrialName(String industrialName) {
		this.industrialName = industrialName;
	}
	
	@ExcelField(title="工贸账套(所在股份)", align=2, sort=4)
	public String getIndustrialAccount() {
		return industrialAccount;
	}

	public void setIndustrialAccount(String industrialAccount) {
		this.industrialAccount = industrialAccount;
	}
	
	@ExcelField(title="传送系统", align=2, sort=5)
	public String getTransferSystem() {
		return transferSystem;
	}

	public void setTransferSystem(String transferSystem) {
		this.transferSystem = transferSystem;
	}
	
}