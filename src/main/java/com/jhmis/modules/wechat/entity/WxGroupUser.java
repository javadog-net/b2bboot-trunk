/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 群组成员Entity
 * @author lvyangzhuo
 * @version 2018-11-22
 */
public class WxGroupUser extends DataEntity<WxGroupUser> {
	
	private static final long serialVersionUID = 1L;
	private String groupId;		// group_id
	private String userId;		// user_id
	private Date joinTime;		// join_time
	private String isAdmin;		// is_admin
	public static final String ISADMIN_YES = "0";
	public static final String ISADMIN_NO = "1";
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String remark3;		// remark3
	private String groupName;		// group_name
	private Date buildTime;		// build_time
	private String description;		// description
	private String status;		// status
	private String source;		// source
	private String sourceStatus;		// sourceStatus
	private String img;		// img
	private String name;		// name
	private Date sendTime;   //sendTime
	private String realName; //realName
	private String content;
	private String isRead;
	private String isStopSpeak;//禁言
	
	
    public String getIsStopSpeak() {
		return isStopSpeak;
	}

	public void setIsStopSpeak(String isStopSpeak) {
		this.isStopSpeak = isStopSpeak;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceStatus() {
		return sourceStatus;
	}

	public void setSourceStatus(String sourceStatus) {
		this.sourceStatus = sourceStatus;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public WxGroupUser() {
		super();
	}

	public WxGroupUser(String id){
		super(id);
	}

	@ExcelField(title="group_id", align=2, sort=0)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@ExcelField(title="user_id", fieldType=String.class, value="", align=2, sort=1)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="join_time", align=2, sort=2)
	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	
	@ExcelField(title="is_admin", align=2, sort=3)
	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	@ExcelField(title="remark1", align=2, sort=4)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="remark2", align=2, sort=5)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@ExcelField(title="remark3", align=2, sort=6)
	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
}