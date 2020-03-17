/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商品属性Entity
 * @author tity
 * @version 2018-07-22
 */
public class GoodsProperties extends DataEntity<GoodsProperties> {
	
	private static final long serialVersionUID = 1L;
	private String goodsCode;		// 商品编码
	private String tag;		// 属性标签
	private String name;		// 属性名称
	private String value;		// 属性值
	
	public GoodsProperties() {
		super();
	}

	public GoodsProperties(String id){
		super(id);
	}

	@ExcelField(title="商品编码", align=2, sort=0)
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@ExcelField(title="属性标签", align=2, sort=1)
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@ExcelField(title="属性名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="属性值", align=2, sort=3)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}