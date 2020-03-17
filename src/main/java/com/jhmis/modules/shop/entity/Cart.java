/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 购物车Entity
 * @author tity
 * @version 2018-07-22
 */
public class Cart extends DataEntity<Cart> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserAccountId;		// 采购商登录账号
	private String goodsCode;		// 商品编码
	private String goodsName;		// 商品名称
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
	private String mainPicUrl;		// 产品主图
	private Integer chooseNum;		// 商品数量
	private String storeGoodsId; //店铺商品ID
	private List<StoreGoods> storeGoodsList;  // 店铺商品信息

	private List<String> idList; //购物车的IDList

	public String getStoreGoodsId() {
		return storeGoodsId;
	}

	public void setStoreGoodsId(String storeGoodsId) {
		this.storeGoodsId = storeGoodsId;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public Cart() {
		super();
	}

	public Cart(String id){
		super(id);
	}

	@ExcelField(title="采购商登录账号", align=2, sort=1)
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
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
	
	@NotNull(message="商品数量不能为空")
	@ExcelField(title="商品数量", align=2, sort=7)
	public Integer getChooseNum() {
		return chooseNum;
	}

	public void setChooseNum(Integer chooseNum) {
		this.chooseNum = chooseNum;
	}

	public List<StoreGoods> getStoreGoodsList() {
		return storeGoodsList;
	}

	public void setStoreGoodsList(List<StoreGoods> storeGoodsList) {
		this.storeGoodsList = storeGoodsList;
	}
}