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
public class GoodsClassProperties extends DataEntity<GoodsClassProperties> {
	
	private static final long serialVersionUID = 1L;
	private String classId;		// 商品品类ID
	private String name;		// 属性名称
	private String value;		// 属性值
	private String isSearch;		//  是否参与搜索  0 否  1是

	private String className; //商品品类名称

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public GoodsClassProperties() {
		super();
	}

	public GoodsClassProperties(String id){
		super(id);
	}

	@ExcelField(title="商品品类ID", align=2, sort=1)
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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
	
	@ExcelField(title=" 是否参与搜索  0 否  1是", dictType="yes_no", align=2, sort=4)
	public String getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}
	
}