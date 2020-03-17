/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 分类推荐表Entity
 * @author hdx
 * @version 2018-08-02
 */
public class RecommendCat extends DataEntity<RecommendCat> {
	
	private static final long serialVersionUID = 1L;
	private String categoryId;		// 产品大类id
	private String categoryName;		// 产品大类名称
	private String goodsId;		// 产品id
	private String goodsCode;		// 产品编码
	private String storeId;		// 店铺id
	private String remark;		// remark
	private String sort;		// 排序
	private String storeName;   //店铺名称
	private String goodsName;   //商品名称

	private String marketPrice;   //市场价
	private String price;   //商品名称
	private String mainPicUrl;   //商品名称

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public RecommendCat() {
		super();
	}

	public RecommendCat(String id){
		super(id);
	}

	@ExcelField(title="产品大类id", align=2, sort=1)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="产品大类名称", align=2, sort=2)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@ExcelField(title="产品id", align=2, sort=3)
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@ExcelField(title="产品编码", align=2, sort=4)
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@ExcelField(title="店铺id", align=2, sort=5)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="remark", align=2, sort=6)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="排序", align=2, sort=7)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}