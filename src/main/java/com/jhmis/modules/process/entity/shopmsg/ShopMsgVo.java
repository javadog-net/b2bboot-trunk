/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.shopmsg;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;

import java.util.Date;
import java.util.List;

/**
 * 发布需求相关Entity
 * @author hdx
 * @version 2019-09-03
 */
public class ShopMsgVo {
	private String id;
	private String companyName;		// 公司
	private String provinceId;		// 省份编码
	private String provinceName;		// 省份名称
	private String cityId;		// 城市编码
	private String cityName;		// 城市名称
	private String districId;		// 地区编码
	private String districName;		// 区域名称
	private String addressDetail;		// 详细地址(hps必备）
	private String connectName;		// 联系人
	private String mobile;		// 联系方式
	private String reserveMobile;		// 400中的预留手机号
	private String email;		// 邮箱
	private String depart;		// 部门
	private String money;		// 预算（400 客服界面上有该选项）
	private String quotaInfo;		// 报价信息（400客服界面用到）
	private int count;//产品数量
	private String descp;		// 需求描述
	private String proGroupCode;		// 产品组冗余字段
	private String proGroup;		// 产品组名称
	private String channel;  // 渠道来源

	public String getProGroup() {
		return proGroup;
	}
	public void setProGroup(String proGroup) {
		this.proGroup = proGroup;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProGroupCode() {
		return proGroupCode;
	}

	public void setProGroupCode(String proGroupCode) {
		this.proGroupCode = proGroupCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistricId() {
		return districId;
	}

	public void setDistricId(String districId) {
		this.districId = districId;
	}

	public String getDistricName() {
		return districName;
	}

	public void setDistricName(String districName) {
		this.districName = districName;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReserveMobile() {
		return reserveMobile;
	}

	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getQuotaInfo() {
		return quotaInfo;
	}

	public void setQuotaInfo(String quotaInfo) {
		this.quotaInfo = quotaInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}
}