/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 评论管理Entity
 * @author lydia
 * @version 2019-09-16
 */
public class Comment extends DataEntity<Comment> {
	
	private static final long serialVersionUID = 1L;
	private String categoryId;		// 栏目id
	private String contentId;		// 内容id
	private String parentId ;    //上级评论ID
	private Comment parent;		// 上级评论对象
	private String replyTo;		// 回复谁
	private Integer level;		// 层级（下一层默认+1）
	private String title;		// 内容标题（文章标题）
	private String isAnonymous;		// 是否匿名评论（0否1是）
	private String content;		// 评论内容
	private String purchaserId;		// 会员ID
	private String userName;		// 评论账号
	private String ip;		// 评论人ip
	private String auditUserId;		// 审核人
	private Date auditDate;		// 审核时间
	private String status;		// 评论状态{0:未审核,-1:未通过审核,1:通过审核}
	private Integer opposes;		// 反对数
	private Integer likes;		// 点赞数

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	private String avatar;// 用户的头像
	private List<Comment> comments;
	
	public Comment() {
		super();
	}

	public Comment(String id){
		super(id);
	}

	@ExcelField(title="栏目id", align=2, sort=1)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="内容id", align=2, sort=2)
	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	
	@JsonBackReference
	@ExcelField(title="上级评论ID", fieldType=Comment.class, value="parent.name", align=2, sort=3)
	public Comment getParent() {
		return parent;
	}

	public void setParent(Comment parent) {
		this.parent = parent;
	}
	
	@ExcelField(title="回复谁", align=2, sort=4)
	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	
	@ExcelField(title="层级（下一层默认+1）", align=2, sort=5)
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@ExcelField(title="内容标题（文章标题）", align=2, sort=6)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="是否匿名评论（0否1是）", dictType="yes_no", align=2, sort=7)
	public String getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(String isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	
	@ExcelField(title="评论内容", align=2, sort=8)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="会员ID", align=2, sort=9)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="评论账号", align=2, sort=10)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="评论人ip", align=2, sort=11)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@ExcelField(title="审核人", align=2, sort=13)
	public String getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(String auditUserId) {
		this.auditUserId = auditUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=14)
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	@ExcelField(title="评论状态{0:未审核,-1:未通过审核,1:通过审核}", align=2, sort=15)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="反对数", align=2, sort=17)
	public Integer getOpposes() {
		return opposes;
	}

	public void setOpposes(Integer opposes) {
		this.opposes = opposes;
	}
	
	@ExcelField(title="点赞数", align=2, sort=18)
	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}