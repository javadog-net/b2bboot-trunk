/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 评论Entity
 * @author tc
 * @version 2019-05-28
 */
public class SkMvComment extends DataEntity<SkMvComment> {
	
	private static final long serialVersionUID = 1L;
	private String skMvId;		// 视频id
	private String skMvName;		// 视频名称
	private String userId;		// 评论用户的id
	private String image;//评论人头像
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private String userName;		// 评论人名
	private String skMvComment;		// 评论
	private Date skMvCommentTime;		// 评论时间
	private String skMvIspass;		// 评论是否通过 0未通过 1通过
	private String skMvIspassExaminer;		// 评论审核人
	
	public SkMvComment() {
		super();
	}

	public SkMvComment(String id){
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
	
	@ExcelField(title="评论用户的id", align=2, sort=3)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="评论人名", align=2, sort=4)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="评论", align=2, sort=5)
	public String getSkMvComment() {
		return skMvComment;
	}

	public void setSkMvComment(String skMvComment) {
		this.skMvComment = skMvComment;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="评论时间", align=2, sort=6)
	public Date getSkMvCommentTime() {
		return skMvCommentTime;
	}

	public void setSkMvCommentTime(Date skMvCommentTime) {
		this.skMvCommentTime = skMvCommentTime;
	}
	
	@ExcelField(title="评论是否通过 0未通过 1通过", align=2, sort=7)
	public String getSkMvIspass() {
		return skMvIspass;
	}

	public void setSkMvIspass(String skMvIspass) {
		this.skMvIspass = skMvIspass;
	}
	
	@ExcelField(title="评论审核人", align=2, sort=8)
	public String getSkMvIspassExaminer() {
		return skMvIspassExaminer;
	}

	public void setSkMvIspassExaminer(String skMvIspassExaminer) {
		this.skMvIspassExaminer = skMvIspassExaminer;
	}
	
}