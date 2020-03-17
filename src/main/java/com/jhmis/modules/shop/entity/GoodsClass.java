/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商品类型管理Entity
 * @author tity
 * @version 2018-07-22
 */
public class GoodsClass extends DataEntity<GoodsClass> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 品类名称
	private String iconUrl;		// 图标路径
	
	public GoodsClass() {
		super();
	}

	public GoodsClass(String id){
		super(id);
	}

	@ExcelField(title="品类名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="图标路径", align=2, sort=2)
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
}