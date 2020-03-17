/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商空视频详情Entity
 * @author tc
 * @version 2019-05-23
 */
public class SkMvDetails extends DataEntity<SkMvDetails> {
	
	private static final long serialVersionUID = 1L;
	private String skMvId;		// 视频id
	private String skMvName;		// 视频名称
	private String skMvVisits;		// 浏览量
	private String skMvLike;		// 点赞量
	private String skMvCommentCount;		// 评论量
	
	public SkMvDetails() {
		super();
	}

	public SkMvDetails(String id){
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
	
	@ExcelField(title="浏览量", align=2, sort=3)
	public String getSkMvVisits() {
		return skMvVisits;
	}

	public void setSkMvVisits(String skMvVisits) {
		this.skMvVisits = skMvVisits;
	}
	
	@ExcelField(title="点赞量", align=2, sort=4)
	public String getSkMvLike() {
		return skMvLike;
	}

	public void setSkMvLike(String skMvLike) {
		this.skMvLike = skMvLike;
	}
	
	@ExcelField(title="评论量", align=2, sort=5)
	public String getSkMvCommentCount() {
		return skMvCommentCount;
	}

	public void setSkMvCommentCount(String skMvCommentCount) {
		this.skMvCommentCount = skMvCommentCount;
	}
	
}