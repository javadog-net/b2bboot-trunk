/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 会议附件上传Entity
 * @author tc
 * @version 2019-03-20
 */
public class WxMeetingFile extends DataEntity<WxMeetingFile> {
	
	private static final long serialVersionUID = 1L;
	private String meetingId;		// 会议id
	private String textUrl;		// 文件内容的url
	private Date addTime;		// 添加时间
	private String addUser;		// 添加人
	private String remark;		// 备注
	private String type;      //文件类型 word-1，excel-2，图片-3
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public WxMeetingFile() {
		super();
	}

	public WxMeetingFile(String id){
		super(id);
	}

	@ExcelField(title="会议id", align=2, sort=1)
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	@ExcelField(title="文件内容的url", align=2, sort=2)
	public String getTextUrl() {
		return textUrl;
	}

	public void setTextUrl(String textUrl) {
		this.textUrl = textUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=3)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="添加人", align=2, sort=4)
	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	
	@ExcelField(title="备注", align=2, sort=5)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}