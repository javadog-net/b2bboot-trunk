/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 消息通知Entity
 * @author hdx
 * @version 2020-02-14
 */
public class HpsMessageReminder extends DataEntity<HpsMessageReminder> {
	
	private static final long serialVersionUID = 1L;
	private String code88;		// 经销商编码（8码）
	private String type;		// 类型
	private String content;		// 内容
	private String projectCode;		// 项目编码
	private String brownCode;		// 工程版号
	private Date createTime;		// 创建时间
	private String isRead;		// 是否已读（0未读   1已读）
	
	public HpsMessageReminder() {
		super();
	}

	public HpsMessageReminder(String id){
		super(id);
	}

	@ExcelField(title="经销商编码（8码）", align=2, sort=1)
	public String getCode88() {
		return code88;
	}

	public void setCode88(String code88) {
		this.code88 = code88;
	}
	
	@ExcelField(title="类型", align=2, sort=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="内容", align=2, sort=3)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="项目编码", align=2, sort=4)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@ExcelField(title="工程版号", align=2, sort=5)
	public String getBrownCode() {
		return brownCode;
	}

	public void setBrownCode(String brownCode) {
		this.brownCode = brownCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=6)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@ExcelField(title="是否已读（0未读   1已读）", align=2, sort=7)
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
}