/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商品扩展资料Entity
 * @author tity
 * @version 2018-07-22
 */
public class GoodsExt extends DataEntity<GoodsExt> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// code
	private String categoryPid;		// 产品一级目录
	private String categoryId;		// 产品二级目录
	private String state;		// 是否可用  0  可用  1 禁用
	private Double marketPrice;		// 市场价
	
	public GoodsExt() {
		super();
	}

	public GoodsExt(String id){
		super(id);
	}

	@ExcelField(title="code", align=2, sort=0)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="产品一级目录", align=2, sort=1)
	public String getCategoryPid() {
		return categoryPid;
	}

	public void setCategoryPid(String categoryPid) {
		this.categoryPid = categoryPid;
	}
	
	@ExcelField(title="产品二级目录", align=2, sort=2)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="是否可用  0  可用  1 禁用", align=2, sort=3)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@ExcelField(title="市场价", align=2, sort=4)
	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	
}