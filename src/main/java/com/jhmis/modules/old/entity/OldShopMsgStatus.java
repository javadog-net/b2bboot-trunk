/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * oldEntity
 * @author old
 * @version 2019-11-29
 */
public class OldShopMsgStatus extends DataEntity<OldShopMsgStatus> {
	
	private static final long serialVersionUID = 1L;
	private String statuscontent;		// statuscontent
	private String statusname;		// statusname
	private Date createtime;		// createtime
	private String inf;		// inf
	private String operator;		// operator
	private String content;		// content
	private String qutoesid;		// qutoesid
	private String bigprojectid;		// 大机projectID
	private String smallprojectid;		// 小机projectID
	private String overtimeflag;		// 1 超时
	
	public OldShopMsgStatus() {
		super();
	}

	public OldShopMsgStatus(String id){
		super(id);
	}

	@ExcelField(title="statuscontent", align=2, sort=1)
	public String getStatuscontent() {
		return statuscontent;
	}

	public void setStatuscontent(String statuscontent) {
		this.statuscontent = statuscontent;
	}
	
	@ExcelField(title="statusname", align=2, sort=2)
	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="createtime", align=2, sort=3)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@ExcelField(title="inf", align=2, sort=4)
	public String getInf() {
		return inf;
	}

	public void setInf(String inf) {
		this.inf = inf;
	}
	
	@ExcelField(title="operator", align=2, sort=5)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@ExcelField(title="content", align=2, sort=6)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="qutoesid", align=2, sort=7)
	public String getQutoesid() {
		return qutoesid;
	}

	public void setQutoesid(String qutoesid) {
		this.qutoesid = qutoesid;
	}
	
	@ExcelField(title="大机projectID", align=2, sort=8)
	public String getBigprojectid() {
		return bigprojectid;
	}

	public void setBigprojectid(String bigprojectid) {
		this.bigprojectid = bigprojectid;
	}
	
	@ExcelField(title="小机projectID", align=2, sort=9)
	public String getSmallprojectid() {
		return smallprojectid;
	}

	public void setSmallprojectid(String smallprojectid) {
		this.smallprojectid = smallprojectid;
	}
	
	@ExcelField(title="1 超时", align=2, sort=10)
	public String getOvertimeflag() {
		return overtimeflag;
	}

	public void setOvertimeflag(String overtimeflag) {
		this.overtimeflag = overtimeflag;
	}
	
}