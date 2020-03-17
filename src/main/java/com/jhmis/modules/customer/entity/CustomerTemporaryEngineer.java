/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 临时工程商Entity
 * @author hdx
 * @version 2019-05-28
 */
public class CustomerTemporaryEngineer extends DataEntity<CustomerTemporaryEngineer> {
	
	private static final long serialVersionUID = 1L;
	private String cusName;		// 工程商名称
	private String tax;		// 税务单号
	private String cusHeadPerson;		// 工程商负责人
	private String cusNumber;		// 工程商单号(FB+手机号)
	private String cusPass;		// 工程商密码(手机号后六位)
	private String wgProviceId;		// 网格省
	private String wgProviceName;		// 网格省份id
	private String wgCityId;		// 网格市区
	private String wgCityName;		// 网格城市名称
	private String orgName;		// 所属工贸
	private String orgId;		// 所属工贸id
	private String responsibleBusiness;		// 负责业务（0小机，1大机）
	private String cusTel;		// 固话
	private String cusMobile;		// 手机号
	private String wgClassify;		// 网格分类(0专业客户 1普通客户)
	private String addressDetails;		// 详细地址
	private Date addTime;		// 添加时间
	private String status;		// 状态(0未审核,1已审核,2审核拒绝)
	private String refuseReason;		// 拒绝原因
	private Date checkTime;		// 审核时间
	private String areaId;		// 区id
	private String areaName;		// 区名称
	
	public CustomerTemporaryEngineer() {
		super();
	}

	public CustomerTemporaryEngineer(String id){
		super(id);
	}

	@ExcelField(title="工程商名称", align=2, sort=1)
	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	
	@ExcelField(title="税务单号", align=2, sort=2)
	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}
	
	@ExcelField(title="工程商负责人", align=2, sort=3)
	public String getCusHeadPerson() {
		return cusHeadPerson;
	}

	public void setCusHeadPerson(String cusHeadPerson) {
		this.cusHeadPerson = cusHeadPerson;
	}
	
	@ExcelField(title="工程商单号(FB+手机号)", align=2, sort=4)
	public String getCusNumber() {
		return cusNumber;
	}

	public void setCusNumber(String cusNumber) {
		this.cusNumber = cusNumber;
	}
	
	@ExcelField(title="工程商密码(手机号后六位)", align=2, sort=5)
	public String getCusPass() {
		return cusPass;
	}

	public void setCusPass(String cusPass) {
		this.cusPass = cusPass;
	}
	
	@ExcelField(title="网格省", align=2, sort=6)
	public String getWgProviceId() {
		return wgProviceId;
	}

	public void setWgProviceId(String wgProviceId) {
		this.wgProviceId = wgProviceId;
	}
	
	@ExcelField(title="网格省份id", align=2, sort=7)
	public String getWgProviceName() {
		return wgProviceName;
	}

	public void setWgProviceName(String wgProviceName) {
		this.wgProviceName = wgProviceName;
	}
	
	@ExcelField(title="网格市区", align=2, sort=8)
	public String getWgCityId() {
		return wgCityId;
	}

	public void setWgCityId(String wgCityId) {
		this.wgCityId = wgCityId;
	}
	
	@ExcelField(title="网格城市名称", align=2, sort=9)
	public String getWgCityName() {
		return wgCityName;
	}

	public void setWgCityName(String wgCityName) {
		this.wgCityName = wgCityName;
	}
	
	@ExcelField(title="所属工贸", align=2, sort=10)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@ExcelField(title="所属工贸id", align=2, sort=11)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@ExcelField(title="负责业务（0小机，1大机）", align=2, sort=12)
	public String getResponsibleBusiness() {
		return responsibleBusiness;
	}

	public void setResponsibleBusiness(String responsibleBusiness) {
		this.responsibleBusiness = responsibleBusiness;
	}
	
	@ExcelField(title="固话", align=2, sort=13)
	public String getCusTel() {
		return cusTel;
	}

	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}
	
	@ExcelField(title="手机号", align=2, sort=14)
	public String getCusMobile() {
		return cusMobile;
	}

	public void setCusMobile(String cusMobile) {
		this.cusMobile = cusMobile;
	}
	
	@ExcelField(title="网格分类(0专业客户 1普通客户)", align=2, sort=15)
	public String getWgClassify() {
		return wgClassify;
	}

	public void setWgClassify(String wgClassify) {
		this.wgClassify = wgClassify;
	}
	
	@ExcelField(title="详细地址", align=2, sort=16)
	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=17)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="状态(0未审核,1已审核,2审核拒绝)", align=2, sort=18)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="拒绝原因", align=2, sort=19)
	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=20)
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	@ExcelField(title="区id", align=2, sort=21)
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	@ExcelField(title="区名称", align=2, sort=22)
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
}