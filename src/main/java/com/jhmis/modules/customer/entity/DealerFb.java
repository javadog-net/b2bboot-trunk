/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 非标客户Entity
 * @author hdx
 * @version 2019-05-25
 */
public class DealerFb extends DataEntity<DealerFb> {
	
	private static final long serialVersionUID = 1L;
	private String company;		// 公司名称
	private String mobile;		// 手机号
	private String contact;		// 联系人
	private Date addTime;		// 添加时间
	private String isCheck;		// 是否审核通过(0待审核，1审核通过，2审核拒绝)
	private String relation;		// 关联标记（用于后期关联真实客户）
	
	public DealerFb() {
		super();
	}

	public DealerFb(String id){
		super(id);
	}

	@ExcelField(title="公司名称", align=2, sort=1)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@ExcelField(title="手机号", align=2, sort=2)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="联系人", align=2, sort=3)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=4)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="是否审核通过(0待审核，1审核通过，2审核拒绝)", align=2, sort=5)
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@ExcelField(title="关联标记（用于后期关联真实客户）", align=2, sort=6)
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
	
}