/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.dispatcher;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 抢派单操作日志Entity
 * @author tc
 * @version 2019-09-19
 */
public class CmsOperlogs extends DataEntity<CmsOperlogs> {
	
	private static final long serialVersionUID = 1L;
	private String loginname;		// loginname
	private Date opertime;		// opertime
	private String content;		// content
	private String ip;		// ip
	
	public CmsOperlogs() {
		super();
	}

	public CmsOperlogs(String id){
		super(id);
	}

	@ExcelField(title="loginname", align=2, sort=1)
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="opertime", align=2, sort=2)
	public Date getOpertime() {
		return opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}
	
	@ExcelField(title="content", align=2, sort=3)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="ip", align=2, sort=4)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}