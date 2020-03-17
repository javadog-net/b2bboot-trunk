/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 微信会员信息表Entity
 * @author lvyangzhuo
 * @version 2018-11-22
 */
public class WxMember extends DataEntity<WxMember> {
	
	private static final long serialVersionUID = 1L;
	private String avatarurl;		// avatarurl
	private String country;		// country
	private String gender;		// gender
	private String language;		// language
	private String nickname;		// nickname
	private String province;		// province
	private String city;		// city
	private String openid;		// openid
	private Date loginLastTime;		// login_last_time
	private String loginTimes;		// login_times
	private String userId;		// user_id
	private String userMobile;		// user_mobile

	public WxMember() {
		super();
	}

	public WxMember(String id){
		super(id);
	}

	@ExcelField(title="avatarurl", align=2, sort=1)
	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}
	
	@ExcelField(title="country", align=2, sort=2)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@ExcelField(title="gender", align=2, sort=3)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@ExcelField(title="language", align=2, sort=4)
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	@ExcelField(title="nickname", align=2, sort=5)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@ExcelField(title="province", align=2, sort=6)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@ExcelField(title="city", align=2, sort=7)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@ExcelField(title="openid", align=2, sort=8)
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="login_last_time", align=2, sort=9)
	public Date getLoginLastTime() {
		return loginLastTime;
	}

	public void setLoginLastTime(Date loginLastTime) {
		this.loginLastTime = loginLastTime;
	}
	
	@ExcelField(title="login_times", align=2, sort=10)
	public String getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(String loginTimes) {
		this.loginTimes = loginTimes;
	}
	
	@ExcelField(title="user_id", fieldType=String.class, value="", align=2, sort=11)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="user_mobile", align=2, sort=12)
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	

}