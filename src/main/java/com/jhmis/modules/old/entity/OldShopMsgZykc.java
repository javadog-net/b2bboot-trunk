/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * oldEntity
 * @author old
 * @version 2019-11-29
 */
public class OldShopMsgZykc extends DataEntity<OldShopMsgZykc> {
	
	private static final long serialVersionUID = 1L;
	private String msgid;		// 需求id
	private Date addtime;		// addtime
	private String companyname;		// 提需求公司名称
	private String cancletype;		// 废弃类型 0 未废弃 1 用户废弃   2 经销商废弃 
	private String cancelreson;		// 废弃原因
	private Date cancletime;		// 废弃时间
	private String custcode;		// 经销商编码
	private String tradecount;		// 交易金额
	private String ischeck;		// 平台审核是否通过 0 是 1否
	private String checker;		// 平台审核人
	private Date checktime;		// 平台审核时间
	private String pimageurl;		// pimageurl
	private String isclosed;		// 1 关闭
	private String canceldesc;		// canceldesc
	
	public OldShopMsgZykc() {
		super();
	}

	public OldShopMsgZykc(String id){
		super(id);
	}

	@ExcelField(title="需求id", align=2, sort=1)
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="addtime", align=2, sort=2)
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@ExcelField(title="提需求公司名称", align=2, sort=3)
	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	@ExcelField(title="废弃类型 0 未废弃 1 用户废弃   2 经销商废弃 ", align=2, sort=4)
	public String getCancletype() {
		return cancletype;
	}

	public void setCancletype(String cancletype) {
		this.cancletype = cancletype;
	}
	
	@ExcelField(title="废弃原因", align=2, sort=5)
	public String getCancelreson() {
		return cancelreson;
	}

	public void setCancelreson(String cancelreson) {
		this.cancelreson = cancelreson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="废弃时间", align=2, sort=6)
	public Date getCancletime() {
		return cancletime;
	}

	public void setCancletime(Date cancletime) {
		this.cancletime = cancletime;
	}
	
	@ExcelField(title="经销商编码", align=2, sort=7)
	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	
	@ExcelField(title="交易金额", align=2, sort=8)
	public String getTradecount() {
		return tradecount;
	}

	public void setTradecount(String tradecount) {
		this.tradecount = tradecount;
	}
	
	@ExcelField(title="平台审核是否通过 0 是 1否", align=2, sort=9)
	public String getIscheck() {
		return ischeck;
	}

	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	
	@ExcelField(title="平台审核人", align=2, sort=10)
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="平台审核时间", align=2, sort=11)
	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}
	
	@ExcelField(title="pimageurl", align=2, sort=12)
	public String getPimageurl() {
		return pimageurl;
	}

	public void setPimageurl(String pimageurl) {
		this.pimageurl = pimageurl;
	}
	
	@ExcelField(title="1 关闭", align=2, sort=13)
	public String getIsclosed() {
		return isclosed;
	}

	public void setIsclosed(String isclosed) {
		this.isclosed = isclosed;
	}
	
	@ExcelField(title="canceldesc", align=2, sort=14)
	public String getCanceldesc() {
		return canceldesc;
	}

	public void setCanceldesc(String canceldesc) {
		this.canceldesc = canceldesc;
	}
	
}