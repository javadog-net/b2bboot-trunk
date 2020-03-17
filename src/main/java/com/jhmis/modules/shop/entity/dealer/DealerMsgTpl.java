/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity.dealer;


import com.jhmis.core.persistence.DataEntity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 供应商消息模板Entity
 * @author tity
 * @version 2018-08-20
 */
public class DealerMsgTpl extends DataEntity<DealerMsgTpl> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 模板编码
	private String name;		// 模板名称
	private String type;		// 消息类型
	private String messageSwitch;		// 站内信开关
	private String messageContent;		// 站内信内容
	private String smsSwitch;		// 短信开关
	private String smsContent;		// 短信内容
	private String mailSwitch;		// 邮件开关
	private String mailSubject;		// 邮件标题
	private String mailContent;		// 邮件内容
	private Date creatTime;//创建时间
	public DealerMsgTpl() {
		super();
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public DealerMsgTpl(String id){
		super(id);
	}

	@ExcelField(title="模板编码", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="模板名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="消息类型", dictType="message_type", align=2, sort=3)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="站内信开关", dictType="yes_no", align=2, sort=4)
	public String getMessageSwitch() {
		return messageSwitch;
	}

	public void setMessageSwitch(String messageSwitch) {
		this.messageSwitch = messageSwitch;
	}
	
	@ExcelField(title="站内信内容", align=2, sort=5)
	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	@ExcelField(title="短信开关", dictType="yes_no", align=2, sort=6)
	public String getSmsSwitch() {
		return smsSwitch;
	}

	public void setSmsSwitch(String smsSwitch) {
		this.smsSwitch = smsSwitch;
	}
	
	@ExcelField(title="短信内容", align=2, sort=7)
	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	
	@ExcelField(title="邮件开关", dictType="yes_no", align=2, sort=8)
	public String getMailSwitch() {
		return mailSwitch;
	}

	public void setMailSwitch(String mailSwitch) {
		this.mailSwitch = mailSwitch;
	}
	
	@ExcelField(title="邮件标题", align=2, sort=9)
	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	
	@ExcelField(title="邮件内容", align=2, sort=10)
	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
	
}