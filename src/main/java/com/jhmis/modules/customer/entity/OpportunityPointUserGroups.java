/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 机会点，用户群Entity
 * @author mll
 * @version 2019-07-19
 */
public class OpportunityPointUserGroups extends DataEntity<OpportunityPointUserGroups> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 编码
	private String name;		// 名称
	private String fatherCode;		// 父编码
	private String type;		// 类型(1-机会点，2-用户群)
	private String typeName;		// 类型名称
	
	public OpportunityPointUserGroups() {
		super();
	}

	public OpportunityPointUserGroups(String id){
		super(id);
	}

	@ExcelField(title="编码", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="父编码", align=2, sort=3)
	public String getFatherCode() {
		return fatherCode;
	}

	public void setFatherCode(String fatherCode) {
		this.fatherCode = fatherCode;
	}
	
	@ExcelField(title="类型(1-机会点，2-用户群)", align=2, sort=4)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="类型名称", align=2, sort=5)
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}