/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 邮件履历Entity
 * @author tc
 * @version 2019-09-04
 */
public class CmsEmailDetail extends DataEntity<CmsEmailDetail> {
	
	private static final long serialVersionUID = 1L;
	private String mailinfoid;		// 邮件信息ID
	private String email;		// 收件人
	private Date sendtime;		// 发送时间
	private String sender;		// 发送人
	private String result;		// 发送结果 
	
	public CmsEmailDetail() {
		super();
	}

	public CmsEmailDetail(String id){
		super(id);
	}

	@ExcelField(title="邮件信息ID", align=2, sort=1)
	public String getMailinfoid() {
		return mailinfoid;
	}

	public void setMailinfoid(String mailinfoid) {
		this.mailinfoid = mailinfoid;
	}
	
	@ExcelField(title="收件人", align=2, sort=2)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发送时间", align=2, sort=3)
	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	@ExcelField(title="发送人", align=2, sort=4)
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@ExcelField(title="发送结果 ", align=2, sort=5)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}