/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 群组表Entity
 * @author lvyangzhuo
 * @version 2018-11-22
 */
public class WxGroup extends DataEntity<WxGroup> {
	
	private static final long serialVersionUID = 1L;


	private String groupName;		// group_name
	private String userId;		// user_id
	private Date buildTime;		// build_time
	private String description;		// description
	private String status;		// status
	public static final String STATUS_YES = "0";
	public static final String STATUS_NO = "1";
	private String source;		// status
	private String sourceStatus;		// status

	public String getSourceStatus() {
		return sourceStatus;
	}

	public void setSourceStatus(String sourceStatus) {
		this.sourceStatus = sourceStatus;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	public WxGroup() {
		super();
	}

	public WxGroup(String id){
		super(id);
	}

	@ExcelField(title="group_name", align=2, sort=1)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@ExcelField(title="user_id", fieldType=String.class, value="", align=2, sort=2)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="build_time", align=2, sort=3)
	public Date getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}
	
	@ExcelField(title="description", align=2, sort=4)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title="status", align=2, sort=5)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}