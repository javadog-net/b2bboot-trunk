/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 采购商登录统计Entity
 * @author a
 * @version 2019-11-29
 */
public class PurchaserLogintime extends DataEntity<PurchaserLogintime> {
	
	private static final long serialVersionUID = 1L;
	private String accountId;		// purchaser_account表的id
	private Date loginTime;		// 登录时间
	private String purchaserId;		// 采购商id
	private Integer num;		// 第几次登录
	
	public PurchaserLogintime() {
		super();
	}

	public PurchaserLogintime(String id){
		super(id);
	}

	@ExcelField(title="purchaser_account表的id", align=2, sort=1)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="登录时间", align=2, sort=2)
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	@ExcelField(title="采购商id", align=2, sort=3)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="第几次登录", align=2, sort=4)
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
}