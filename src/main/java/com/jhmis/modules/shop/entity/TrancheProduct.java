/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 一期商品数据Entity
 * @author hdx
 * @version 2018-07-29
 */
public class TrancheProduct extends DataEntity<TrancheProduct> {
	
	private static final long serialVersionUID = 1L;
	private String productCode;		// 产品编码
	private String productIscheck;		// 是否在产品中心存在0存在1不存在
	private String remark;		// 备注
	
	public TrancheProduct() {
		super();
	}

	public TrancheProduct(String id){
		super(id);
	}

	@ExcelField(title="产品编码", align=2, sort=0)
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@ExcelField(title="是否在产品中心存在0存在1不存在", align=2, sort=1)
	public String getProductIscheck() {
		return productIscheck;
	}

	public void setProductIscheck(String productIscheck) {
		this.productIscheck = productIscheck;
	}
	
	@ExcelField(title="备注", align=2, sort=2)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}