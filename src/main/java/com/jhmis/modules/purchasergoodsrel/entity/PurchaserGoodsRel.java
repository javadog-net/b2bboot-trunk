/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.purchasergoodsrel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;

import java.util.List;

/**
 * 采购商可采商品Entity
 * @author wangbn
 * @version 2019-07-24
 */
public class PurchaserGoodsRel extends DataEntity<PurchaserGoodsRel> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserId;		// 采购商ID
	private String goodsSku;		// 商品sku
	private Double exclusivePrice;		// 专享价
	private String opeDate;		// 操作时间
	
	private String state;   //0待设置专享价格 1在售


	private List<PurchaserGoodsRel> purchaserGoodsRelList;  //查询时使用
	
	public PurchaserGoodsRel() {
		super();
	}

	public PurchaserGoodsRel(String id){
		super(id);
	}

	@ExcelField(title="采购商ID", align=2, sort=1)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="采购商ID", align=2, sort=2)
	public String getGoodsSku() {
		return goodsSku;
	}

	public void setGoodsSku(String goodsSku) {
		this.goodsSku = goodsSku;
	}
	
	@ExcelField(title="专享价", align=2, sort=3)
	public Double getExclusivePrice() {
		return exclusivePrice;
	}

	// getter setter
	public List<PurchaserGoodsRel> getPurchaserGoodsRelList() {
		return purchaserGoodsRelList;
	}

	public void setPurchaserGoodsRelList(List<PurchaserGoodsRel> purchaserGoodsRelList) {
		this.purchaserGoodsRelList = purchaserGoodsRelList;
	}


	public void setExclusivePrice(Double exclusivePrice) {
		this.exclusivePrice = exclusivePrice;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="操作时间", align=2, sort=4)
	public String getOpeDate() {
		return opeDate;
	}

	public void setOpeDate(String opeDate) {
		this.opeDate = opeDate;
	}
	
}