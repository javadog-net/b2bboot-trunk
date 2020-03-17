package com.jhmis.modules.wechat.entity;

public class EnterpriseDetails {
	private String status;//状态
	private String message;//返回信息
	private String name; //名称
	private String address; //地址
	private String domains;	//行业
	private String onelevelid;//所属一级行业id
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDomains() {
		return domains;
	}
	public void setDomains(String domains) {
		this.domains = domains;
	}
	public String getOnelevelid() {
		return onelevelid;
	}
	public void setOnelevelid(String onlevelid) {
		this.onelevelid = onlevelid;
	}
	
	
}
