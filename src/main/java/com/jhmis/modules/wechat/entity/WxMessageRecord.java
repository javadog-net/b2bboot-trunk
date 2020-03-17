/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 短信履历Entity
 * @author hdx
 * @version 2019-02-15
 */
public class WxMessageRecord extends DataEntity<WxMessageRecord> {
	
	private static final long serialVersionUID = 1L;
	private String mobile;		// 手机号
	private String content;		// 内容描述
	private String result;		// 短信发送结果集
	private Date addTime;		// 添加时间
	private String addPerson;		// 添加人
	
	public WxMessageRecord() {
		super();
	}

	public WxMessageRecord(String id){
		super(id);
	}

	@ExcelField(title="手机号", align=2, sort=1)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="内容描述", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="短信发送结果集", align=2, sort=3)
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=4)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="添加人", align=2, sort=5)
	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}
	
}