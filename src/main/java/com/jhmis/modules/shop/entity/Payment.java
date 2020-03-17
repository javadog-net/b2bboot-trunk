/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 支付方式Entity
 * @author tity
 * @version 2018-07-22
 */
public class Payment extends DataEntity<Payment> {
	
	private static final long serialVersionUID = 1L;
	private String payCode;		// 支付代码名称
	private String payName;		// 支付名称
	private String payConfig;		// 支付接口配置信息
	private String paystate;		// 接口状态
	
	public Payment() {
		super();
	}

	public Payment(String id){
		super(id);
	}

	@ExcelField(title="支付代码名称", align=2, sort=1)
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	
	@ExcelField(title="支付名称", align=2, sort=2)
	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}
	
	@ExcelField(title="支付接口配置信息", align=2, sort=3)
	public String getPayConfig() {
		return payConfig;
	}

	public void setPayConfig(String payConfig) {
		this.payConfig = payConfig;
	}
	
	@ExcelField(title="接口状态", dictType="enable", align=2, sort=4)
	public String getPaystate() {
		return paystate;
	}

	public void setPaystate(String paystate) {
		this.paystate = paystate;
	}
	
}