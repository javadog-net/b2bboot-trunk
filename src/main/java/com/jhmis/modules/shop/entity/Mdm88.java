/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 88码处理Entity
 * @author mll
 * @version 2019-09-16
 */
public class Mdm88 extends DataEntity<Mdm88> {
	
	private static final long serialVersionUID = 1L;
	private String code88;		// 88码
	
	public Mdm88() {
		super();
	}

	public Mdm88(String id){
		super(id);
	}

	@ExcelField(title="88码", align=2, sort=0)
	public String getCode88() {
		return code88;
	}

	public void setCode88(String code88) {
		this.code88 = code88;
	}
	
}