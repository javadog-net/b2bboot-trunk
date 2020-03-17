/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.shophicback;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * shophicbackEntity
 * @author hdx
 * @version 2019-11-26
 */
public class ShopHicbackpassLog extends DataEntity<ShopHicbackpassLog> {
	
	private static final long serialVersionUID = 1L;
	private String backid;		// 需求id
	private String bindmoney;		// 实际中标金额
	private Date bindtime;		// 实际中标时间
	private String cancelfalg;		// 需求实际状态
	private String cjmoney;		// 回传中标金额
	private String state;		// 回传状态
	private Date zbtime;		// 回传中标时间
	private String memo;		// 备注
	
	public ShopHicbackpassLog() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ShopHicbackpassLog(String id){
		super(id);
	}

	@ExcelField(title="需求id", align=2, sort=1)
	public String getBackid() {
		return backid;
	}

	public void setBackid(String backid) {
		this.backid = backid;
	}
	
	@ExcelField(title="实际中标金额", align=2, sort=2)
	public String getBindmoney() {
		return bindmoney;
	}

	public void setBindmoney(String bindmoney) {
		this.bindmoney = bindmoney;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="实际中标时间", align=2, sort=3)
	public Date getBindtime() {
		return bindtime;
	}

	public void setBindtime(Date bindtime) {
		this.bindtime = bindtime;
	}
	
	@ExcelField(title="需求实际状态", align=2, sort=4)
	public String getCancelfalg() {
		return cancelfalg;
	}

	public void setCancelfalg(String cancelfalg) {
		this.cancelfalg = cancelfalg;
	}
	
	@ExcelField(title="回传中标金额", align=2, sort=5)
	public String getCjmoney() {
		return cjmoney;
	}

	public void setCjmoney(String cjmoney) {
		this.cjmoney = cjmoney;
	}
	
	@ExcelField(title="回传状态", align=2, sort=6)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="回传中标时间", align=2, sort=7)
	public Date getZbtime() {
		return zbtime;
	}

	public void setZbtime(Date zbtime) {
		this.zbtime = zbtime;
	}
	
	@ExcelField(title="备注", align=2, sort=8)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}