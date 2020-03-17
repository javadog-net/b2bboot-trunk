/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.haier.link.upper.dto.ProductInfo;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 品牌管理Entity
 * @author tity
 * @version 2018-07-22
 */
public class Brand extends DataEntity<Brand> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 品牌名称
	/*private String imgUrl;//品牌图片
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}*/

	public Brand() {
		super();
	}

	public Brand(String id){
		super(id);
	}

	@ExcelField(title="品牌名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Store store;
	private StoreGoods storeGoods;
	private ProductInfo productInfo;
	private List<GoodsEvaluate> evaluateList;
	private Goods goods;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public StoreGoods getStoreGoods() {
		return storeGoods;
	}

	public void setStoreGoods(StoreGoods storeGoods) {
		this.storeGoods = storeGoods;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public List<GoodsEvaluate> getEvaluateList() {
		return evaluateList;
	}

	public void setEvaluateList(List<GoodsEvaluate> evaluateList) {
		this.evaluateList = evaluateList;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}
}