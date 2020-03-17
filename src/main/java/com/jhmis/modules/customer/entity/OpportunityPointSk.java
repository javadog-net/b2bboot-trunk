/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商空机会点Entity
 * @author mll
 * @version 2019-08-14
 */
public class OpportunityPointSk extends DataEntity<OpportunityPointSk> {
	
	private static final long serialVersionUID = 1L;
	private String industrytypename;		// industrytypename
	private String isvalid;		// isvalid
	private String parentid;		// parentid
	
	public OpportunityPointSk() {
		super();
	}

	public OpportunityPointSk(String id){
		super(id);
	}

	@ExcelField(title="industrytypename", align=2, sort=1)
	public String getIndustrytypename() {
		return industrytypename;
	}

	public void setIndustrytypename(String industrytypename) {
		this.industrytypename = industrytypename;
	}
	
	@ExcelField(title="isvalid", align=2, sort=2)
	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	
	@ExcelField(title="parentid", align=2, sort=3)
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
}