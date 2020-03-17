/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 行程Entity
 * @author hdx
 * @version 2019-02-14
 */
public class WxTravel extends DataEntity<WxTravel> {
	
	private static final long serialVersionUID = 1L;
	private String meetingId;		// 会议id
	private String purchaserAccountId;		// 用户id
	private String meetingSignupId;		// 报名表id
	private String concat;		// 联系人
	private String mobile;		// 手机号
	private String certificateType;		// 证件类型(0是身份证 1是护照)
	private String certificateNo;		// 证件号码
	private String startVehicle;		// (交通工具)0是火车 1是飞机
	private Date startTime;		// 出发时间
	private String startStand;		// 出发站
	private String startStandEnd;		// 出发到达站
	private String startVehicleNo;		// 航班号/车次号
	private String startReceive;		// 是否接站 (0是 1否)
	private String startTicket;		// 出票状态(0出票中 1已出票)
	private String returnVehicle;		// 返程交通工具(0是火车 1是飞机)
	private Date returnTime;		// 返程时间
	private String returnStand;		// 返程站
	private String returnStandEnd;		// 返程到达站
	private String returnVehicleNo;		// 航班号/车次号
	private String returnTicket;		// 出票状态(0出票中 1已出票)
	private Date addTime;		// 添加时间
	private String isDel;		// 是否删除
	private Date delTime;		// 删除时间
	
	public WxTravel() {
		super();
	}

	public WxTravel(String id){
		super(id);
	}

	@ExcelField(title="会议id", align=2, sort=1)
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	
	@ExcelField(title="用户id", align=2, sort=2)
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}
	
	@ExcelField(title="报名表id", align=2, sort=3)
	public String getMeetingSignupId() {
		return meetingSignupId;
	}

	public void setMeetingSignupId(String meetingSignupId) {
		this.meetingSignupId = meetingSignupId;
	}
	
	@ExcelField(title="联系人", align=2, sort=4)
	public String getConcat() {
		return concat;
	}

	public void setConcat(String concat) {
		this.concat = concat;
	}
	
	@ExcelField(title="手机号", align=2, sort=5)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@ExcelField(title="证件类型(0是身份证 1是护照)", dictType="", align=2, sort=6)
	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	
	@ExcelField(title="证件号码", align=2, sort=7)
	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	@ExcelField(title="(交通工具)0是火车 1是飞机", align=2, sort=8)
	public String getStartVehicle() {
		return startVehicle;
	}

	public void setStartVehicle(String startVehicle) {
		this.startVehicle = startVehicle;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="出发时间", align=2, sort=9)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@ExcelField(title="出发站", align=2, sort=10)
	public String getStartStand() {
		return startStand;
	}

	public void setStartStand(String startStand) {
		this.startStand = startStand;
	}
	
	@ExcelField(title="出发到达站", align=2, sort=11)
	public String getStartStandEnd() {
		return startStandEnd;
	}

	public void setStartStandEnd(String startStandEnd) {
		this.startStandEnd = startStandEnd;
	}
	
	@ExcelField(title="航班号/车次号", align=2, sort=12)
	public String getStartVehicleNo() {
		return startVehicleNo;
	}

	public void setStartVehicleNo(String startVehicleNo) {
		this.startVehicleNo = startVehicleNo;
	}
	
	@ExcelField(title="是否接站 (0是 1否)", dictType="", align=2, sort=13)
	public String getStartReceive() {
		return startReceive;
	}

	public void setStartReceive(String startReceive) {
		this.startReceive = startReceive;
	}
	
	@ExcelField(title="出票状态(0出票中 1已出票)", dictType="", align=2, sort=14)
	public String getStartTicket() {
		return startTicket;
	}

	public void setStartTicket(String startTicket) {
		this.startTicket = startTicket;
	}
	
	@ExcelField(title="返程交通工具(0是火车 1是飞机)", dictType="", align=2, sort=15)
	public String getReturnVehicle() {
		return returnVehicle;
	}

	public void setReturnVehicle(String returnVehicle) {
		this.returnVehicle = returnVehicle;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="返程时间", align=2, sort=16)
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	@ExcelField(title="返程站", align=2, sort=17)
	public String getReturnStand() {
		return returnStand;
	}

	public void setReturnStand(String returnStand) {
		this.returnStand = returnStand;
	}
	
	@ExcelField(title="返程到达站", align=2, sort=18)
	public String getReturnStandEnd() {
		return returnStandEnd;
	}

	public void setReturnStandEnd(String returnStandEnd) {
		this.returnStandEnd = returnStandEnd;
	}
	
	@ExcelField(title="航班号/车次号", align=2, sort=19)
	public String getReturnVehicleNo() {
		return returnVehicleNo;
	}

	public void setReturnVehicleNo(String returnVehicleNo) {
		this.returnVehicleNo = returnVehicleNo;
	}
	
	@ExcelField(title="出票状态(0出票中 1已出票)", dictType="", align=2, sort=20)
	public String getReturnTicket() {
		return returnTicket;
	}

	public void setReturnTicket(String returnTicket) {
		this.returnTicket = returnTicket;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=24)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="是否删除", align=2, sort=25)
	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="删除时间", align=2, sort=26)
	public Date getDelTime() {
		return delTime;
	}

	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	
}