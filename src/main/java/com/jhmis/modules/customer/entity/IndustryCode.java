/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 行业类别Entity
 * @author hdx
 * @version 2019-04-27
 */
public class IndustryCode extends DataEntity<IndustryCode> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 编码
	private String name;		// 名称
	private String parentCode;		// 上级编码
	private String level;		// 等级
	private String typeDesc;		// 类别描述
	private Integer orderNumber; //排序
	
	public IndustryCode() {
		super();
	}

	public IndustryCode(String id){
		super(id);
	}

	@ExcelField(title="编码", align=2, sort=0)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="上级编码", align=2, sort=2)
	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	@ExcelField(title="等级", align=2, sort=3)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@ExcelField(title="类别描述", align=2, sort=4)
	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}


	
}