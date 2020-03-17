/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 行业信息管理Entity
 * @author tity
 * @version 2018-07-22
 */
public class Industry extends DataEntity<Industry> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 行业编码
	private String name;		// 行业名称
	
	public Industry() {
		super();
	}

	public Industry(String id){
		super(id);
	}

	@ExcelField(title="行业编码", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="行业名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}