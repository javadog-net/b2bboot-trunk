/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商空点赞Entity
 * @author tc
 * @version 2019-05-28
 */
public class SkMvLike extends DataEntity<SkMvLike> {
	
	private static final long serialVersionUID = 1L;
	private String skMvId;		// 视频id
	private String skMvName;		// 视频名称
	private String userId;		// （点赞）用户的id
	private String userName;		// 点赞人名
	private String skMvIslike;		// 0未点赞 1点赞
	private Date skMvLikeTime;		// 点赞时间
	
	public SkMvLike() {
		super();
	}

	public SkMvLike(String id){
		super(id);
	}

	@ExcelField(title="视频id", align=2, sort=1)
	public String getSkMvId() {
		return skMvId;
	}

	public void setSkMvId(String skMvId) {
		this.skMvId = skMvId;
	}
	
	@ExcelField(title="视频名称", align=2, sort=2)
	public String getSkMvName() {
		return skMvName;
	}

	public void setSkMvName(String skMvName) {
		this.skMvName = skMvName;
	}
	
	@ExcelField(title="（点赞）用户的id", align=2, sort=3)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="点赞人名", align=2, sort=4)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="0未点赞 1点赞", align=2, sort=5)
	public String getSkMvIslike() {
		return skMvIslike;
	}

	public void setSkMvIslike(String skMvIslike) {
		this.skMvIslike = skMvIslike;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="点赞时间", align=2, sort=6)
	public Date getSkMvLikeTime() {
		return skMvLikeTime;
	}

	public void setSkMvLikeTime(Date skMvLikeTime) {
		this.skMvLikeTime = skMvLikeTime;
	}
	
}