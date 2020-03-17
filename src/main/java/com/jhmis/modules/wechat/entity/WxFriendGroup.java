/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 分组表Entity
 * @author lvyangzhuo
 * @version 2018-11-22
 */
public class WxFriendGroup extends DataEntity<WxFriendGroup> {
	
	private static final long serialVersionUID = 1L;
	private String groupName;		// group_name
	private String purchaserId;		// purchaser_id
	private Date addTime;		// add_time
	private String isDefault;		// 1ΪĬ
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String remark3;		// remark3
	
	public WxFriendGroup() {
		super();
	}

	public WxFriendGroup(String id){
		super(id);
	}

	@ExcelField(title="group_name", align=2, sort=1)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@ExcelField(title="purchaser_id", align=2, sort=2)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="add_time", align=2, sort=3)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="1ΪĬ", align=2, sort=4)
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	@ExcelField(title="remark1", align=2, sort=5)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="remark2", align=2, sort=6)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@ExcelField(title="remark3", align=2, sort=7)
	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
}