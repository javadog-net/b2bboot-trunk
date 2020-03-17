/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 网格码表Entity
 * @author hdx
 * @version 2020-02-25
 */
public class TGridArea extends DataEntity<TGridArea> {
	
	private static final long serialVersionUID = 1L;
	private String gmcode;		// 工贸编码，例：12901
	private String gmname;		// 工贸名称，例：合肥小微
	private String wgxw;		// 网格小微，例：绩溪旌德歙县网格小微
	private String wgxwcode;		// 网格小微代码，例：02040500B702
	private String wgcode;		// 网格代码，例：CMI46102
	private String wgname;		// 网格名称，例：安徽省宣城市绩溪县
	private String threexzq;		// 3级行政地区，例：341824000000
	private String xzqcode;		// 行政地区代码，例：340000000000/341800000000/341824000000
	private String xzqname;		// 行政地区名称，例：安徽省/宣城市/绩溪县
	private String province;		// 省，例：安徽省
	private String provincecode;		// 省代码，例：340000000000
	private String city;		// 市，例：宣城市
	private String citycode;		// 市代码，例：341800000000
	private String district;		// 区县，例：绩溪县
	private String districtcode;		// 区县代码，例：341824000000
	private Date pushedTime;		// 推送时间
	
	public TGridArea() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public TGridArea(String id){
		super(id);
	}

	@ExcelField(title="工贸编码，例：12901", align=2, sort=1)
	public String getGmcode() {
		return gmcode;
	}

	public void setGmcode(String gmcode) {
		this.gmcode = gmcode;
	}
	
	@ExcelField(title="工贸名称，例：合肥小微", align=2, sort=2)
	public String getGmname() {
		return gmname;
	}

	public void setGmname(String gmname) {
		this.gmname = gmname;
	}
	
	@ExcelField(title="网格小微，例：绩溪旌德歙县网格小微", align=2, sort=3)
	public String getWgxw() {
		return wgxw;
	}

	public void setWgxw(String wgxw) {
		this.wgxw = wgxw;
	}
	
	@ExcelField(title="网格小微代码，例：02040500B702", align=2, sort=4)
	public String getWgxwcode() {
		return wgxwcode;
	}

	public void setWgxwcode(String wgxwcode) {
		this.wgxwcode = wgxwcode;
	}
	
	@ExcelField(title="网格代码，例：CMI46102", align=2, sort=5)
	public String getWgcode() {
		return wgcode;
	}

	public void setWgcode(String wgcode) {
		this.wgcode = wgcode;
	}
	
	@ExcelField(title="网格名称，例：安徽省宣城市绩溪县", align=2, sort=6)
	public String getWgname() {
		return wgname;
	}

	public void setWgname(String wgname) {
		this.wgname = wgname;
	}
	
	@ExcelField(title="3级行政地区，例：341824000000", align=2, sort=7)
	public String getThreexzq() {
		return threexzq;
	}

	public void setThreexzq(String threexzq) {
		this.threexzq = threexzq;
	}
	
	@ExcelField(title="行政地区代码，例：340000000000/341800000000/341824000000", align=2, sort=8)
	public String getXzqcode() {
		return xzqcode;
	}

	public void setXzqcode(String xzqcode) {
		this.xzqcode = xzqcode;
	}
	
	@ExcelField(title="行政地区名称，例：安徽省/宣城市/绩溪县", align=2, sort=9)
	public String getXzqname() {
		return xzqname;
	}

	public void setXzqname(String xzqname) {
		this.xzqname = xzqname;
	}
	
	@ExcelField(title="省，例：安徽省", align=2, sort=10)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@ExcelField(title="省代码，例：340000000000", align=2, sort=11)
	public String getProvincecode() {
		return provincecode;
	}

	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}
	
	@ExcelField(title="市，例：宣城市", align=2, sort=12)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@ExcelField(title="市代码，例：341800000000", align=2, sort=13)
	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	
	@ExcelField(title="区县，例：绩溪县", align=2, sort=14)
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	@ExcelField(title="区县代码，例：341824000000", align=2, sort=15)
	public String getDistrictcode() {
		return districtcode;
	}

	public void setDistrictcode(String districtcode) {
		this.districtcode = districtcode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="推送时间", align=2, sort=16)
	public Date getPushedTime() {
		return pushedTime;
	}

	public void setPushedTime(Date pushedTime) {
		this.pushedTime = pushedTime;
	}
	
}