/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 订单日志Entity
 * @author tity
 * @version 2018-07-22
 */
public class OrderLog extends DataEntity<OrderLog> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单id
	private String logMsg;		// 文字描述
	private Date logTime;		// 处理时间
	private String logRole;		// 操作角色
	private String logUser;		// 操作人
	private String logOrderstate;		// 订单状态
	
	public OrderLog() {
		super();
	}

	public OrderLog(String id){
		super(id);
	}

	@ExcelField(title="订单id", align=2, sort=1)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@ExcelField(title="文字描述", align=2, sort=2)
	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="处理时间不能为空")
	@ExcelField(title="处理时间", align=2, sort=3)
	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	
	@ExcelField(title="操作角色", align=2, sort=4)
	public String getLogRole() {
		return logRole;
	}

	public void setLogRole(String logRole) {
		this.logRole = logRole;
	}
	
	@ExcelField(title="操作人", align=2, sort=5)
	public String getLogUser() {
		return logUser;
	}

	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}
	
	@ExcelField(title="订单状态", align=2, sort=6)
	public String getLogOrderstate() {
		return logOrderstate;
	}

	public void setLogOrderstate(String logOrderstate) {
		this.logOrderstate = logOrderstate;
	}
	
}