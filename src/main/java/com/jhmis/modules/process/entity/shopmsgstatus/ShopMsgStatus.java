/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.shopmsgstatus;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 需求状态履历表Entity
 * @author hdx
 * @version 2019-09-23
 */
public class ShopMsgStatus extends DataEntity<ShopMsgStatus> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// 需求ID
	private String statusNode;		// 流程大节点
	private String statusName;		// 流程状态
	private String operator;		// 操作人
	private String content;		// 具体内容
	private String operatorType;		// 操作人类型(0客户，1客服，2总监，3.经销商）
	
	public ShopMsgStatus() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public ShopMsgStatus(String id){
		super(id);
	}

	@ExcelField(title="需求ID", align=2, sort=1)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@ExcelField(title="流程大节点", align=2, sort=2)
	public String getStatusNode() {
		return statusNode;
	}

	public void setStatusNode(String statusNode) {
		this.statusNode = statusNode;
	}
	
	@ExcelField(title="流程状态", align=2, sort=3)
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	@ExcelField(title="操作人", align=2, sort=5)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@ExcelField(title="具体内容", align=2, sort=6)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="操作人类型(0客户，1客服，2总监，3.经销商）", align=2, sort=7)
	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	
}