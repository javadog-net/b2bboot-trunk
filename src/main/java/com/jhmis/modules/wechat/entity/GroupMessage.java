/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 群组聊天信息Entity
 * @author lvyangzhuo
 * @version 2018-11-22
 */
public class GroupMessage extends DataEntity<GroupMessage> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// user_id
	private String groupId;		// group_id
	private String content;		// content
	private String isDel;		// is_del
	private String isRead;		// is_read
	private Date sendTime;		// send_time
	private String isBack;		// is_back
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String remark3;		// remark3
	
	public GroupMessage() {
		super();
	}

	public GroupMessage(String id){
		super(id);
	}

	@ExcelField(title="user_id", fieldType=String.class, value="", align=2, sort=1)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="group_id", align=2, sort=2)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@ExcelField(title="content", align=2, sort=3)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="is_del", align=2, sort=4)
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	@ExcelField(title="is_read", align=2, sort=5)
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="send_time", align=2, sort=6)
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@ExcelField(title="is_back", align=2, sort=7)
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	
	@ExcelField(title="remark1", align=2, sort=8)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="remark2", align=2, sort=9)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@ExcelField(title="remark3", align=2, sort=10)
	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
}