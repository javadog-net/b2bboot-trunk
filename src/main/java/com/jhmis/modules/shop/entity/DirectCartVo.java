/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;

import java.util.List;

/**
 * 直采购物车Entity
 * @author hdx
 * @version 2019-03-27
 */
public class DirectCartVo extends DataEntity<DirectCartVo> {

	private static final long serialVersionUID = 1L;

	//	产品组编码
	private String productGroupCode;
	//	产品组名称
	private String productGroupName;

	private List<DirectCart> listDirectCart;

	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public List<DirectCart> getListDirectCart() {
		return listDirectCart;
	}

	public void setListDirectCart(List<DirectCart> listDirectCart) {
		this.listDirectCart = listDirectCart;
	}
}