/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 商品价格分组Entity
 * @author tity
 * @version 2018-07-22
 */
public class StorePriceGroup extends DataEntity<StorePriceGroup> {
	
	private static final long serialVersionUID = 1L;
	private String storeId;		// 店铺ID
	private String code;		// 产品编码
	private Double price;		// 价格
	private String createByStr;
	private String updateByStr;

	public String getCreateByStr() {
		return createByStr;
	}

	public void setCreateByStr(String createByStr) {
		this.createByStr = createByStr;
	}

	public String getUpdateByStr() {
		return updateByStr;
	}

	public void setUpdateByStr(String updateByStr) {
		this.updateByStr = updateByStr;
	}

	public StorePriceGroup() {
		super();
	}

	public StorePriceGroup(String id){
		super(id);
	}

	@ExcelField(title="店铺ID", align=2, sort=1)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="产品编码", align=2, sort=2)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="价格不能为空")
	@ExcelField(title="价格", align=2, sort=3)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	private List<StoreGoodsPrice> storeGoodsPriceList;

	public List<StoreGoodsPrice> getStoreGoodsPriceList() {
		return storeGoodsPriceList;
	}

	public void setStoreGoodsPriceList(List<StoreGoodsPrice> storeGoodsPriceList) {
		this.storeGoodsPriceList = storeGoodsPriceList;
	}
}