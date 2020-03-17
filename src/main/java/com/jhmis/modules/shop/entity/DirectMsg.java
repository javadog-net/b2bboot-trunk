/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 直采需求Entity
 * @author hdx
 * @version 2019-04-03
 */
public class DirectMsg extends DataEntity<DirectMsg> {

	public static final String DIRECT_MSG_STATUS_WAIT = "0";
	public static final String DIRECT_MSG_STATUS_SUCCESS = "1";
	public static final String DIRECT_MSG_STATUS_REFUSE = "2";

	private static final long serialVersionUID = 1L;
	private String msgOrder;		// 直采需求单号
	private String projectname;		// 项目名称
	private String firstCompanyName;		// 甲方公司名称
	private String firstContactName;		// 甲方联系人姓名
	private String phone;		// 甲方联系人手机号
	private String projectCreaterCode;		// 项目录入人编码
	private String projectCreaterName;		// 项目录入人名称
	private String projectManagerCode;		// 项目经理编码
	private String projectManagerName;		// 项目经理名称
	private String shPartner;		// 送达方
	private String pyPartner;		// 付款方
	private String bpPartner;		// 开票方
	private String status;		// 状态值(0待审核,1审核通过,2审核拒绝)
	private String addressProvince;		// 项目地址（省）
	private String addressCity;		// 项目地址（市）
	private String addressCounty;		// 项目地址（区/县）
	private String addUser;		// 提报人
	private Date addTime;		// 添加时间
	private String errorMsg;		// 失败信息
	private Date errorTime;		// 失败时间
	List<DirectMsgProduct> directMsgProductList; //需求产品相关信息

	public List<DirectMsgProduct> getDirectMsgProductList() {
		return directMsgProductList;
	}

	public void setDirectMsgProductList(List<DirectMsgProduct> directMsgProductList) {
		this.directMsgProductList = directMsgProductList;
	}

	public DirectMsg() {
		super();
	}

	public DirectMsg(String id){
		super(id);
	}

	public String getMsgOrder() {
		return msgOrder;
	}

	public void setMsgOrder(String msgOrder) {
		this.msgOrder = msgOrder;
	}

	@ExcelField(title="项目名称", align=2, sort=2)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	@ExcelField(title="甲方公司名称", align=2, sort=3)
	public String getFirstCompanyName() {
		return firstCompanyName;
	}

	public void setFirstCompanyName(String firstCompanyName) {
		this.firstCompanyName = firstCompanyName;
	}

	@ExcelField(title="甲方联系人姓名", align=2, sort=4)
	public String getFirstContactName() {
		return firstContactName;
	}

	public void setFirstContactName(String firstContactName) {
		this.firstContactName = firstContactName;
	}

	@ExcelField(title="甲方联系人手机号", align=2, sort=5)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@ExcelField(title="项目录入人编码", align=2, sort=6)
	public String getProjectCreaterCode() {
		return projectCreaterCode;
	}

	public void setProjectCreaterCode(String projectCreaterCode) {
		this.projectCreaterCode = projectCreaterCode;
	}

	@ExcelField(title="项目录入人名称", align=2, sort=7)
	public String getProjectCreaterName() {
		return projectCreaterName;
	}

	public void setProjectCreaterName(String projectCreaterName) {
		this.projectCreaterName = projectCreaterName;
	}

	@ExcelField(title="项目经理编码", align=2, sort=8)
	public String getProjectManagerCode() {
		return projectManagerCode;
	}

	public void setProjectManagerCode(String projectManagerCode) {
		this.projectManagerCode = projectManagerCode;
	}

	@ExcelField(title="项目经理名称", align=2, sort=9)
	public String getProjectManagerName() {
		return projectManagerName;
	}

	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}

	@ExcelField(title="送达方", align=2, sort=10)
	public String getShPartner() {
		return shPartner;
	}

	public void setShPartner(String shPartner) {
		this.shPartner = shPartner;
	}

	@ExcelField(title="付款方", align=2, sort=11)
	public String getPyPartner() {
		return pyPartner;
	}

	public void setPyPartner(String pyPartner) {
		this.pyPartner = pyPartner;
	}

	@ExcelField(title="开票方", align=2, sort=12)
	public String getBpPartner() {
		return bpPartner;
	}

	public void setBpPartner(String bpPartner) {
		this.bpPartner = bpPartner;
	}

	@ExcelField(title="状态值(0待审核,1审核通过,2审核拒绝)", align=2, sort=13)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ExcelField(title="项目地址（省）", align=2, sort=14)
	public String getAddressProvince() {
		return addressProvince;
	}

	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}

	@ExcelField(title="项目地址（市）", align=2, sort=15)
	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	@ExcelField(title="项目地址（区/县）", align=2, sort=16)
	public String getAddressCounty() {
		return addressCounty;
	}

	public void setAddressCounty(String addressCounty) {
		this.addressCounty = addressCounty;
	}

	@ExcelField(title="提报人", align=2, sort=17)
	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=18)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@ExcelField(title="失败信息", align=2, sort=19)
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="失败时间", align=2, sort=20)
	public Date getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Date errorTime) {
		this.errorTime = errorTime;
	}

}