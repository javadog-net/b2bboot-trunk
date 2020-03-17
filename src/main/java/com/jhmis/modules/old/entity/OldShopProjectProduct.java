/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 1Entity
 * @author 1
 * @version 2019-12-06
 */
public class OldShopProjectProduct extends DataEntity<OldShopProjectProduct> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// projectcode
	private String name;		// name
	private String count;		// count
	private String money;		// money
	private String address;		// address
	private String code;		// code
	private String msgid;		// 迭代标识(msgid存在为直单漏斗更新后数据，目的为更新视图所用)
	
	public OldShopProjectProduct() {
		super();
	}

	public OldShopProjectProduct(String id){
		super(id);
	}

	@ExcelField(title="projectcode", align=2, sort=0)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="name", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="count", align=2, sort=2)
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	@ExcelField(title="money", align=2, sort=3)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@ExcelField(title="address", align=2, sort=4)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="code", align=2, sort=5)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="迭代标识(msgid存在为直单漏斗更新后数据，目的为更新视图所用)", align=2, sort=6)
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
}