/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 产业大小单Entity
 * @author mll
 * @version 2019-07-31
 */
public class IndustryProducts extends DataEntity<IndustryProducts> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 编码
	private String name;		// 名称
	private String sort;		// 排序
	private String describe;		// 描述
	private String industrialCode;		// 产业编码
	private String industrialName;		// 产业名称
	private String intustryAccount;		// 工贸账套（所在股份）
	private String transferSystem;		// 传送系统
	private String money;		// 金额，大于该金额，则判断为大单
	private String num;		// 数量，大于该数量，则判断为大单
	
	public IndustryProducts() {
		super();
	}

	public IndustryProducts(String id){
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
	
	@ExcelField(title="排序", align=2, sort=3)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@ExcelField(title="描述", align=2, sort=4)
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	@ExcelField(title="产业编码", align=2, sort=5)
	public String getIndustrialCode() {
		return industrialCode;
	}

	public void setIndustrialCode(String industrialCode) {
		this.industrialCode = industrialCode;
	}
	
	@ExcelField(title="产业名称", align=2, sort=6)
	public String getIndustrialName() {
		return industrialName;
	}

	public void setIndustrialName(String industrialName) {
		this.industrialName = industrialName;
	}
	
	@ExcelField(title="工贸账套（所在股份）", align=2, sort=7)
	public String getIntustryAccount() {
		return intustryAccount;
	}

	public void setIntustryAccount(String intustryAccount) {
		this.intustryAccount = intustryAccount;
	}
	
	@ExcelField(title="传送系统", align=2, sort=8)
	public String getTransferSystem() {
		return transferSystem;
	}

	public void setTransferSystem(String transferSystem) {
		this.transferSystem = transferSystem;
	}
	
	@ExcelField(title="金额，大于该金额，则判断为大单", align=2, sort=9)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@ExcelField(title="数量，大于该数量，则判断为大单", align=2, sort=10)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
}