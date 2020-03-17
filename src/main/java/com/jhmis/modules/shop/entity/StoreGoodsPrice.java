/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;

/**
 * 店铺商品价格Entity
 * @author tity
 * @version 2018-07-22
 */
public class StoreGoodsPrice extends DataEntity<StoreGoodsPrice> {
	
	private static final long serialVersionUID = 1L;
	private String storePriceGroupId;		// 店铺价格分组ID
	private String purchaserId;		// 采购商ID
	private Purchaser purchaser;
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

	public Purchaser getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(Purchaser purchaser) {
		this.purchaser = purchaser;
	}

	public StoreGoodsPrice() {
		super();
	}

	public StoreGoodsPrice(String id){
		super(id);
	}

	@ExcelField(title="店铺价格分组ID", align=2, sort=1)
	public String getStorePriceGroupId() {
		return storePriceGroupId;
	}

	public void setStorePriceGroupId(String storePriceGroupId) {
		this.storePriceGroupId = storePriceGroupId;
	}
	
	@ExcelField(title="采购商ID", align=2, sort=2)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
}