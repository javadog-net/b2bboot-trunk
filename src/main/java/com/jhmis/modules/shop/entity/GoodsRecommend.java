/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商品推荐Entity
 * @author tity
 * @version 2018-07-22
 */
public class GoodsRecommend extends DataEntity<GoodsRecommend> {
	
	private static final long serialVersionUID = 1L;
	private String goodsCode;		// 商品编码
	private String storeId;		// 店铺ID
	private String sort;		// 排序
	
	public GoodsRecommend() {
		super();
	}

	public GoodsRecommend(String id){
		super(id);
	}

	@ExcelField(title="商品编码", align=2, sort=1)
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@ExcelField(title="店铺ID", align=2, sort=2)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="排序", align=2, sort=7)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}