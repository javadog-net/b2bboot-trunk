/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 签到表Entity
 * @author tc
 * @version 2018-11-22
 */
public class WxSignIn extends DataEntity<WxSignIn> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// user_id
	private String continueDay;		// continue_day
	private Date createTime;		// create_time
	private String count;
	private String signInDay;//签到日
	private String signInYear;// 签到年
	private String signInMonth;//签到月
	private String userIdTab;//用户签到标识
	public String getUserIdTab() {
		return userIdTab;
	}
	public void setUserIdTab(String userIdTab) {
		this.userIdTab = userIdTab;
	}
	
	public String getSignInDay() {
		return signInDay;
	}
	public void setSignInDay(String signInDay) {
		this.signInDay = signInDay;
	}
	public String getSignInYear() {
		return signInYear;
	}
	public void setSignInYear(String signInYear) {
		this.signInYear = signInYear;
	}
	public String getSignInMonth() {
		return signInMonth;
	}
	public void setSignInMonth(String signInMonth) {
		this.signInMonth = signInMonth;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}

	public WxSignIn() {
		super();
	}

	public WxSignIn(String id){
		super(id);
	}

	@ExcelField(title="user_id", fieldType=String.class, value="", align=2, sort=1)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="continue_day", align=2, sort=2)
	public String getContinueDay() {
		return continueDay;
	}

	public void setContinueDay(String continueDay) {
		this.continueDay = continueDay;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="create_time", align=2, sort=3)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}