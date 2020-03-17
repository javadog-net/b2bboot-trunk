/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 客单漏斗项目产品Entity
 * @author hdx
 * @version 2019-04-16
 */
public class CustomerProjectProduct extends DataEntity<CustomerProjectProduct> {
	
	private static final long serialVersionUID = 1L;
	private String projectcode;		// 项目编码
	private String name;		// 产品组名称
	private String count;		// 产品组数量
	private String money;		// 金额
	private String address;		// 安装地址
	private String code;		// 产品组编码
	
	public CustomerProjectProduct() {
		super();
	}

	public CustomerProjectProduct(String id){
		super(id);
	}

	@ExcelField(title="项目编码", align=2, sort=0)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="产品组名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="产品组数量", align=2, sort=2)
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	@ExcelField(title="金额", align=2, sort=3)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@ExcelField(title="安装地址", align=2, sort=4)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="产品组编码", align=2, sort=5)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}