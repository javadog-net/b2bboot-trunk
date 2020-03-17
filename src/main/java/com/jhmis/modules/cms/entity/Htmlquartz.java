/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;

import java.util.Date;

/**
 * 静态化调度Entity
 * @author lydia
 * @version 2019-12-13
 */
public class Htmlquartz extends DataEntity<Htmlquartz> {
	
	private static final long serialVersionUID = 1L;
	private String categoryId;		// category_id
	private String type;		// type
	private Integer exetimeHour;		// exetime_hour
	private Integer exetimeMin;		// exetime_min
	private String intervalType;		// 间隔单位
	private Integer intervalHour;		// interval_hour
	private Integer intervalMin;		// interval_min
	private Date lastexeTime;		// lastexe_time
	private Date nextexeTime;		// nextexe_time
	private String acquisitionid;		// acquisitionid
	private String objtype;		// objtype
	private String nextextTimeFlag;  // 1  查询没有下次任务的调度  2  查询有下次调度的任务
	
	public Htmlquartz() {
		super();
	}

	public Htmlquartz(String id){
		super(id);
	}

	@ExcelField(title="category_id", align=2, sort=1)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="type", align=2, sort=2)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getExetimeHour() {
		return exetimeHour;
	}

	public void setExetimeHour(Integer exetimeHour) {
		this.exetimeHour = exetimeHour;
	}

	public Integer getExetimeMin() {
		return exetimeMin;
	}

	public void setExetimeMin(Integer exetimeMin) {
		this.exetimeMin = exetimeMin;
	}

	@ExcelField(title="interval_type", align=2, sort=5)
	public String getIntervalType() {
		return intervalType;
	}

	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}

	public Integer getIntervalHour() {
		return intervalHour;
	}

	public void setIntervalHour(Integer intervalHour) {
		this.intervalHour = intervalHour;
	}

	public Integer getIntervalMin() {
		return intervalMin;
	}

	public void setIntervalMin(Integer intervalMin) {
		this.intervalMin = intervalMin;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="lastexe_time", align=2, sort=8)
	public Date getLastexeTime() {
		return lastexeTime;
	}

	public void setLastexeTime(Date lastexeTime) {
		this.lastexeTime = lastexeTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="nextexe_time", align=2, sort=9)
	public Date getNextexeTime() {
		return nextexeTime;
	}

	public void setNextexeTime(Date nextexeTime) {
		this.nextexeTime = nextexeTime;
	}
	
	@ExcelField(title="acquisitionid", align=2, sort=10)
	public String getAcquisitionid() {
		return acquisitionid;
	}

	public void setAcquisitionid(String acquisitionid) {
		this.acquisitionid = acquisitionid;
	}
	
	@ExcelField(title="objtype", align=2, sort=11)
	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	public String getNextextTimeFlag() {
		return nextextTimeFlag;
	}

	public void setNextextTimeFlag(String nextextTimeFlag) {
		this.nextextTimeFlag = nextextTimeFlag;
	}
}