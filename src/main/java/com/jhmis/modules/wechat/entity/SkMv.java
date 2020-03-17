/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商空视频Entity
 * @author Tc
 * @version 2019-05-24
 */
public class SkMv extends DataEntity<SkMv> {
	
	private static final long serialVersionUID = 1L;
	private String skMvUrl;		// 视频url
	private String skMvImage;		// 背景图url
	private String skMvName;		// 视频名称
	private String skMvIntroduction;		// 视频简介
	private Date skMvAddtime;		// 添加时间
	private String skMvSize;		// 视频时长
	private String skMvAdduser;		// 添加人
	private String skMvRemark;		// 备注
	private String skMvCommentCount;
	private String skMvLike;
	private String skMvVisits;
	public String getSkMvCommentCount() {
		return skMvCommentCount;
	}

	public void setSkMvCommentCount(String skMvCommentCount) {
		this.skMvCommentCount = skMvCommentCount;
	}

	public String getSkMvLike() {
		return skMvLike;
	}

	public void setSkMvLike(String skMvLike) {
		this.skMvLike = skMvLike;
	}

	public String getSkMvVisits() {
		return skMvVisits;
	}

	public void setSkMvVisits(String skMvVisits) {
		this.skMvVisits = skMvVisits;
	}

	private SkMvDetails skMvDetails;//视频详情
	public SkMvDetails getSkMvDetails() {
		return skMvDetails;
	}

	public void setSkMvDetails(SkMvDetails skMvDetails) {
		this.skMvDetails = skMvDetails;
	}

	public SkMv() {
		super();
	}

	public SkMv(String id){
		super(id);
	}

	@ExcelField(title="视频url", align=2, sort=1)
	public String getSkMvUrl() {
		return skMvUrl;
	}

	public void setSkMvUrl(String skMvUrl) {
		this.skMvUrl = skMvUrl;
	}
	
	@ExcelField(title="背景图url", align=2, sort=2)
	public String getSkMvImage() {
		return skMvImage;
	}

	public void setSkMvImage(String skMvImage) {
		this.skMvImage = skMvImage;
	}
	
	@ExcelField(title="视频名称", align=2, sort=3)
	public String getSkMvName() {
		return skMvName;
	}

	public void setSkMvName(String skMvName) {
		this.skMvName = skMvName;
	}
	
	@ExcelField(title="视频简介", align=2, sort=4)
	public String getSkMvIntroduction() {
		return skMvIntroduction;
	}

	public void setSkMvIntroduction(String skMvIntroduction) {
		this.skMvIntroduction = skMvIntroduction;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=5)
	public Date getSkMvAddtime() {
		return skMvAddtime;
	}

	public void setSkMvAddtime(Date skMvAddtime) {
		this.skMvAddtime = skMvAddtime;
	}
	
	@ExcelField(title="视频时长", align=2, sort=6)
	public String getSkMvSize() {
		return skMvSize;
	}

	public void setSkMvSize(String skMvSize) {
		this.skMvSize = skMvSize;
	}
	
	@ExcelField(title="添加人", align=2, sort=7)
	public String getSkMvAdduser() {
		return skMvAdduser;
	}

	public void setSkMvAdduser(String skMvAdduser) {
		this.skMvAdduser = skMvAdduser;
	}
	
	@ExcelField(title="备注", align=2, sort=8)
	public String getSkMvRemark() {
		return skMvRemark;
	}

	public void setSkMvRemark(String skMvRemark) {
		this.skMvRemark = skMvRemark;
	}
	
}