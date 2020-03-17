package com.haier.order.dto;

import java.io.Serializable;

public class RetDTO implements Serializable {

	private static final long serialVersionUID = 1301101487214872798L;

	/**
	 * 成功状态 1
	 */
	public static final String SUCCESS = "1";

	/**
	 * Appexception 反给用户看的状态
	 */
	public static final String ERROR4CUSTOMER = "2";

	/**
	 * 系统内部其他异常
	 */
	public static final String ERROR4LOG = "3";
	
	/**
	 * 幂等状态：已经重复调用
	 */
	public static final String IDEMPOTENCY_TYPE = "4";

	/**
	 * 状态
	 */
	private String retStatus = "1";
	/**
	 * 对应字段key的值 比如：orgId
	 */
	private String retMessageKey = "";
	/**
	 * 消息
	 */
	private String retMessage = "";
	/**
	 * 返回数据
	 */
	private String retData = "";
	public String getRetStatus() {
		return retStatus;
	}
	public void setRetStatus(String retStatus) {
		this.retStatus = retStatus;
	}
	public String getRetMessageKey() {
		return retMessageKey;
	}
	public void setRetMessageKey(String retMessageKey) {
		this.retMessageKey = retMessageKey;
	}
	public String getRetMessage() {
		return retMessage;
	}
	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}
	public String getRetData() {
		return retData;
	}
	public void setRetData(String retData) {
		this.retData = retData;
	}
	@Override
	public String toString() {
		return "RetDTO [retStatus=" + retStatus + ", retMessageKey=" + retMessageKey + ", retMessage=" + retMessage
				+ ", retData=" + retData + "]";
	}


}

