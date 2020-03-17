/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 订单商品Entity
 * @author tity
 * @version 2018-07-22
 */
public class OrderGoods extends DataEntity<OrderGoods> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单id
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
	private String goodsCode;		// 商品编码
	private String goodsName;		// 商品名称
	private String goodsType;		// 1默认2组合套装
	private Double price;		// 商品价格
	private Integer num;		// 商品数量
	private String mainPicUrl;		// 商品主图url
	private Double payPrice;		// 商品实际成交价
	private Double supplyPrice;		// 客供价
	private String purchaserId;		// 采购商ID
	private String categoryId;		// 商品二级目录
	private String evaluateStatus;    //评价表示0是默认未评价，1是初次评价，2是在此评价完成
	private String isExchange;//是否已申请换货
	private String storeGoodsId; //店铺商品ID

	public String getStoreGoodsId() {
		return storeGoodsId;
	}

	public void setStoreGoodsId(String storeGoodsId) {
		this.storeGoodsId = storeGoodsId;
	}

	public String getEvaluateStatus() {
		return evaluateStatus;
	}

	public void setEvaluateStatus(String evaluateStatus) {
		this.evaluateStatus = evaluateStatus;
	}
	
	public OrderGoods() {
		super();
	}

	public OrderGoods(String id){
		super(id);
	}

	@ExcelField(title="订单id", align=2, sort=1)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@ExcelField(title="店铺ID", align=2, sort=2)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="店铺名称", align=2, sort=3)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@ExcelField(title="商品编码", align=2, sort=4)
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@ExcelField(title="商品名称", align=2, sort=5)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@ExcelField(title="1默认2组合套装", dictType="order_goods_type", align=2, sort=6)
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	
	@NotNull(message="商品价格不能为空")
	@ExcelField(title="商品价格", align=2, sort=7)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@NotNull(message="商品数量不能为空")
	@ExcelField(title="商品数量", align=2, sort=8)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	@ExcelField(title="商品主图url", align=2, sort=9)
	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}
	
	@NotNull(message="商品实际成交价不能为空")
	@ExcelField(title="商品实际成交价", align=2, sort=10)
	public Double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
	
	@ExcelField(title="客供价", align=2, sort=11)
	public Double getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(Double supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	
	@ExcelField(title="采购商ID", align=2, sort=12)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="商品二级目录", align=2, sort=13)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getIsExchange() {
		return isExchange;
	}

	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}
}