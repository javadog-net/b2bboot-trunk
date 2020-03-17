/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 省市区码表Entity
 * @author hdx
 * @version 2019-03-28
 */
public class AreaCode extends DataEntity<AreaCode> {
	
	private static final long serialVersionUID = 1L;
	private String parentId;		// 父编码
	private String cityName;		// 城市名称
	
	public AreaCode() {
		super();
	}

	public AreaCode(String id){
		super(id);
	}

	@ExcelField(title="父编码", align=2, sort=1)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@ExcelField(title="城市名称", align=2, sort=2)
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}