/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * app版本Entity
 * @author abc
 * @version 2019-05-10
 */
public class AppVersion extends DataEntity<AppVersion> {
	
	private static final long serialVersionUID = 1L;
	private String version;		// 版本号
	private String address;		// 下载地址
	private String type;		// 系统
	private Date time;		// 版本生成时间
	private String content;		// 更新内容
	private Long totalSize;		// 文件大小
	private String update;		// 是否更新
	private Date updateTime;		// 更新时间
	private String mustUpdate;		// 是否强制更新
	private String fromVersion; //传过版本号
	
	public AppVersion() {
		super();
	}

	public AppVersion(String id){
		super(id);
	}

	@ExcelField(title="版本号", align=2, sort=1)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@ExcelField(title="下载地址", align=2, sort=2)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="系统", align=2, sort=3)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="版本生成时间不能为空")
	@ExcelField(title="版本生成时间", align=2, sort=4)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@ExcelField(title="更新内容", align=2, sort=5)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="文件大小", align=2, sort=6)
	public Long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	
	@ExcelField(title="是否更新", dictType="", align=2, sort=7)
	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=8)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@ExcelField(title="是否强制更新", dictType="", align=2, sort=9)
	public String getMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(String mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

	public String getFromVersion() {
		return fromVersion;
	}

	public void setFromVersion(String fromVersion) {
		this.fromVersion = fromVersion;
	}
	
}