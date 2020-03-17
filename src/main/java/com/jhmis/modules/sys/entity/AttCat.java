/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import com.google.common.collect.Lists;

import com.jhmis.core.persistence.TreeEntity;

/**
 * 附件管理Entity
 * @author tity
 * @version 2018-07-06
 */
public class AttCat extends TreeEntity<AttCat> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String userType;		// (1,后台用户，2供应商，3前台用户)
	
	private List<Attachment> attachmentList = Lists.newArrayList();		// 子表列表
	
	public AttCat() {
		super();
	}

	public AttCat(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public  AttCat getParent() {
			return parent;
	}
	
	@Override
	public void setParent(AttCat parent) {
		this.parent = parent;
		
	}
	
	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}