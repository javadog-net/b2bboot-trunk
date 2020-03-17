/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 会议报名表Entity
 * 
 * @author lvyangzhuo
 * @version 2018-11-26
 */
public class WxMeetingSignup extends DataEntity<WxMeetingSignup> {

	public static final String AUDIT_YES = "1";

	public static final String AUDIT_NO = "2";

	public static final String SIGNSTATUS_CHECK = "0";
	public static final String SIGNSTATUS_PASS = "1";
	public static final String SIGNSTATUS_REFUSE = "2";
	public static final String SIGNSTATUS_ATTEND = "3";
	public static final String SIGNSTATUS_PASSANDSCHEDULE = "4";
	private static final long serialVersionUID = 1L;
	private String meetingId; // 会议id
	private String userId; // 企业购会员id
	private String signStatus; // 0-待审核; 1-审核通过; 2-审核拒绝 -3已签到;4-审核通过待填写行程
	private String attendStatus; // 0-待参加; 1-已签到;3-已过期
	private Date signTime; // 报名时间
	private String examinePerson; // 审核人
	private Date examineTime; // 审核时间
	private Date attendTime; // 参会扫码时间
	// 用户名称
	private String realName;// 用户名
	private String mobile;// 手机号
	private String companyName;// 公司名称
	private String departName;// 部门名称
	private String address;// 地址
	private String email;// 邮箱
	private String carNum;// 车次
	private String tableNum;// 桌号
	private String seatNum;// 座号
	private WxTravel wxTravel;
    private String cityId;
    private String provinceId;

    private String orgid;
    private String orgname;

    private String id;
    @ExcelField(title = "id", align = 2, sort = 9)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	@ExcelField(title = "车辆分组", align = 2, sort = 8)
	public String getCarNum() {
		return carNum;
	}

	public WxTravel getWxTravel() {
		return wxTravel;
	}

	public void setWxTravel(WxTravel wxTravel) {
		this.wxTravel = wxTravel;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	@ExcelField(title = "晚宴座次 ", align = 2, sort = 7)
	public String getTableNum() {
		return tableNum;
	}

	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}
	@ExcelField(title = "会议座次", align = 2, sort = 6)
	public String getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String industryName;// 行业

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	private String signWay;// 报名来源：0 小程序，1 app

	public String getSignWay() {
		return signWay;
	}

	public void setSignWay(String signWay) {
		this.signWay = signWay;
	}
	@ExcelField(title = "手机号 ", align = 2, sort = 4)
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@ExcelField(title = "公司名称", align = 2, sort = 5)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	private String name;// 会议名称
	private String site;// 地点
	private Date starttime;// 开始时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	@ExcelField(title = "用户名", align = 2, sort = 3)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public WxMeetingSignup() {
		super();
	}

	public WxMeetingSignup(String id) {
		super(id);
	}

	@ExcelField(title = "会议id", align = 2, sort = 1)
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	@ExcelField(title = "企业购会员id", fieldType = String.class, align = 2, sort = 2)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	//@ExcelField(title = "0-待审核; 1-审核通过; 2-审核拒绝 ", dictType = "", align = 2, sort = 3)
	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	//@ExcelField(title = "0-待参加; 1-已签到;3-已过期", dictType = "", align = 2, sort = 4)
	public String getAttendStatus() {
		return attendStatus;
	}

	public void setAttendStatus(String attendStatus) {
		this.attendStatus = attendStatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@ExcelField(title = "报名时间", align = 2, sort = 5)
	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	//@ExcelField(title = "审核人", align = 2, sort = 6)
	public String getExaminePerson() {
		return examinePerson;
	}

	public void setExaminePerson(String examinePerson) {
		this.examinePerson = examinePerson;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@ExcelField(title = "审核时间", align = 2, sort = 7)
	public Date getExamineTime() {
		return examineTime;
	}

	public void setExamineTime(Date examineTime) {
		this.examineTime = examineTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@ExcelField(title = "参会扫码时间", align = 2, sort = 8)
	public Date getAttendTime() {
		return attendTime;
	}

	public void setAttendTime(Date attendTime) {
		this.attendTime = attendTime;
	}

}