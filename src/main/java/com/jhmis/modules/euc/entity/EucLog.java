/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * euc_log日志Entity
 * @author hdx
 * @version 2019-11-13
 */
public class EucLog extends DataEntity<EucLog> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// 需求id
	private String businessCode;		// 商机编码
	private String businessName;		// 商机名称
	private Date addTime;		// 创建时间
	private String status;		// 状态
	private String content;		// 内容描述
	
	public EucLog() {
		super();
	}

	public EucLog(String id){
		super(id);
	}

	@ExcelField(title="需求id", align=2, sort=1)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@ExcelField(title="商机编码", align=2, sort=2)
	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	
	@ExcelField(title="商机名称", align=2, sort=3)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=4)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="状态", align=2, sort=5)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="内容描述", align=2, sort=6)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}