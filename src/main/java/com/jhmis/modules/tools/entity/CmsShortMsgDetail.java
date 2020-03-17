/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 短信履历Entity
 * @author tc
 * @version 2019-09-04
 */
public class CmsShortMsgDetail extends DataEntity<CmsShortMsgDetail> {
	
	private static final long serialVersionUID = 1L;
	private String shortmsgid;		// 短信id
	private String mobilephone;		// 手机号
	private String result;		// 结果
	private Date sendtime;		// 发送时间
	private String sender;		// 发送人
	
	public CmsShortMsgDetail() {
		super();
	}

	public CmsShortMsgDetail(String id){
		super(id);
	}

	@ExcelField(title="短信id", align=2, sort=1)
	public String getShortmsgid() {
		return shortmsgid;
	}

	public void setShortmsgid(String shortmsgid) {
		this.shortmsgid = shortmsgid;
	}
	
	@ExcelField(title="手机号", align=2, sort=2)
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	
	@ExcelField(title="结果", align=2, sort=3)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发送时间", align=2, sort=4)
	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	@ExcelField(title="发送人", align=2, sort=5)
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
}