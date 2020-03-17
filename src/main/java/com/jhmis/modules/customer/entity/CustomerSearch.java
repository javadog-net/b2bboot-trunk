package com.jhmis.modules.customer.entity;

import java.util.Date;



public class CustomerSearch {

    protected String createBeginDate;
    protected String createEndDate;
    protected String cusCode;
    protected String nodename;
    protected String operateStatus;
    protected String pageNo;
    protected String pageSize;
    protected String projectName;
	public String getCreateBeginDate() {
		return createBeginDate;
	}
	public void setCreateBeginDate(String createBeginDate) {
		this.createBeginDate = createBeginDate;
	}
	public String getCreateEndDate() {
		return createEndDate;
	}
	public void setCreateEndDate(String createEndDate) {
		this.createEndDate = createEndDate;
	}
	public String getCusCode() {
		return cusCode;
	}
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	public String getOperateStatus() {
		return operateStatus;
	}
	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
    
    
}
