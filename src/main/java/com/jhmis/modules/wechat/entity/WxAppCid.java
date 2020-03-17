/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 设备cidEntity
 * @author tc
 * @version 2019-05-07
 */
public class WxAppCid extends DataEntity<WxAppCid> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String userPhone;		// 用户手机号
	private String cId;		// 用户设备唯一码
	private String phoneType;   //用户手机类型
	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public WxAppCid() {
		super();
	}

	public WxAppCid(String id){
		super(id);
	}

	@ExcelField(title="用户id", fieldType=String.class, value="", align=2, sort=1)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="用户手机号", align=2, sort=2)
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	@ExcelField(title="用户设备唯一码", align=2, sort=3)
	public String getCId() {
		return cId;
	}

	public void setCId(String cId) {
		this.cId = cId;
	}
	
}