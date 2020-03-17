/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;

import java.util.Date;

/**
 * 客单评论Entity
 * @author tc
 * @version 2020-01-21
 */
public class CustomerComment extends DataEntity<CustomerComment> {
	
	private static final long serialVersionUID = 1L;
	private String commentUser;		// 评论人
	private String commentMobile;		// 联系人手机号
	private String commentType;		// 类型 1.系统使用 2.项目漏斗 3.产品订单 4.物流 5.安装及售后 6.培训 7.其他  多个用 , 分割
	private String commentContent;		// 评论内容
	private String commentDegreeScore;		// 流程度评分
	private String commentBeautifulScore;		// 美观度评分
	private String commentHumanityScore;		// 人性化评分
	private Date commentDate;		// 评论时间
	
	public CustomerComment() {
		super();
	}

	public CustomerComment(String id){
		super(id);
	}

	@ExcelField(title="评论人", align=2, sort=1)
	public String getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(String commentUser) {
		this.commentUser = commentUser;
	}
	
	@ExcelField(title="联系人手机号", align=2, sort=2)
	public String getCommentMobile() {
		return commentMobile;
	}

	public void setCommentMobile(String commentMobile) {
		this.commentMobile = commentMobile;
	}
	
	@ExcelField(title="类型 1.系统使用 2.项目漏斗 3.产品订单 4.物流 5.安装及售后 6.培训 7.其他  多个用 , 分割", align=2, sort=3)
	public String getCommentType() {
		return commentType;
	}

	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	
	@ExcelField(title="评论内容", align=2, sort=4)
	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	
	@ExcelField(title="流程度评分", align=2, sort=5)
	public String getCommentDegreeScore() {
		return commentDegreeScore;
	}

	public void setCommentDegreeScore(String commentDegreeScore) {
		this.commentDegreeScore = commentDegreeScore;
	}
	
	@ExcelField(title="美观度评分", align=2, sort=6)
	public String getCommentBeautifulScore() {
		return commentBeautifulScore;
	}

	public void setCommentBeautifulScore(String commentBeautifulScore) {
		this.commentBeautifulScore = commentBeautifulScore;
	}
	
	@ExcelField(title="人性化评分", align=2, sort=7)
	public String getCommentHumanityScore() {
		return commentHumanityScore;
	}

	public void setCommentHumanityScore(String commentHumanityScore) {
		this.commentHumanityScore = commentHumanityScore;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="评论时间", align=2, sort=8)
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	
}