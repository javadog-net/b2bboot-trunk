/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;

import java.util.Date;

import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 工程信息查询Entity
 * @author a
 * @version 2019-12-17
 */
public class CustomerDemand extends DataEntity<CustomerDemand> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// msg_id
	private String loginLevel;		// login_level
	private String nodeName;		// node_name
	private String operateStatus;		// operate_status
	private String operater;		// operater
	private String projectCode;		// project_code
	private String projectCreaterName;		// project_creater_name
	private String projectName;		// project_name
	private String projectState;
	private String createTime; 
	private String smallBill;		// small_bill
	
	public CustomerDemand() {
		super();
	}

	public CustomerDemand(String id){
		super(id);
	}

	@ExcelField(title="msg_id", align=2, sort=1)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@ExcelField(title="login_level", align=2, sort=2)
	public String getLoginLevel() {
		return loginLevel;
	}

	public void setLoginLevel(String loginLevel) {
		this.loginLevel = loginLevel;
	}
	
	@ExcelField(title="node_name", align=2, sort=3)
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	@ExcelField(title="operate_status", align=2, sort=4)
	public String getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}
	
	@ExcelField(title="operater", align=2, sort=5)
	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}
	
	@ExcelField(title="project_code", align=2, sort=6)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@ExcelField(title="project_creater_name", align=2, sort=7)
	public String getProjectCreaterName() {
		return projectCreaterName;
	}

	public void setProjectCreaterName(String projectCreaterName) {
		this.projectCreaterName = projectCreaterName;
	}
	
	@ExcelField(title="project_name", align=2, sort=8)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@ExcelField(title="small_bill", align=2, sort=9)
	public String getSmallBill() {
		return smallBill;
	}

	public void setSmallBill(String smallBill) {
		this.smallBill = smallBill;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProjectState() {
		return projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	
	
	
	
}