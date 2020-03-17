/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 直采购物车Entity
 * @author hdx
 * @version 2019-03-27
 */
public class DirectCart extends DataEntity<DirectCart> {
	
	private static final long serialVersionUID = 1L;
	private String dealerAccountId;		// 供应商登录账号
	private String goodsCode;		// 商品编码
	private String goodsName;		// 商品名称
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
	private String mainPicUrl;		// 产品主图
	private Integer chooseNum;		// 商品数量
	private String storeGoodsId;		// 店铺商品ID
	private String productGroupCode;		// 产品组编码
	private String productGroupName;		// 产品组名称
	private List<String> idList; //购物车的IDList
	private String price; //产品价格

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public DirectCart() {
		super();
	}

	public DirectCart(String id){
		super(id);
	}

	@ExcelField(title="供应商登录账号", align=2, sort=1)
	public String getDealerAccountId() {
		return dealerAccountId;
	}

	public void setDealerAccountId(String dealerAccountId) {
		this.dealerAccountId = dealerAccountId;
	}
	
	@ExcelField(title="商品编码", align=2, sort=2)
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@ExcelField(title="商品名称", align=2, sort=3)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@ExcelField(title="店铺ID", align=2, sort=4)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="店铺名称", align=2, sort=5)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@ExcelField(title="产品主图", align=2, sort=6)
	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}
	
	@ExcelField(title="商品数量", align=2, sort=7)
	public Integer getChooseNum() {
		return chooseNum;
	}

	public void setChooseNum(Integer chooseNum) {
		this.chooseNum = chooseNum;
	}
	
	@ExcelField(title="店铺商品ID", align=2, sort=12)
	public String getStoreGoodsId() {
		return storeGoodsId;
	}

	public void setStoreGoodsId(String storeGoodsId) {
		this.storeGoodsId = storeGoodsId;
	}
	
	@ExcelField(title="产品组编码", align=2, sort=13)
	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}
	
	@ExcelField(title="产品组名称", align=2, sort=14)
	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	
}