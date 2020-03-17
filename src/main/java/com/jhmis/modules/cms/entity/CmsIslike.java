/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * info信息  是否喜欢 Entity
 * @author tc
 * @version 2019-11-07
 */
public class CmsIslike extends DataEntity<CmsIslike> {
	
	private static final long serialVersionUID = 1L;
	private String infoId;		// Info 的id
	private String infoTitle;		// info 的title
	private String userId;		// purchaser_account  的id
	private String userName;		// purchaser_account  的name
	private String infoIslike;		// 1 喜欢 0不喜欢
	private Date createTime;		// 时间
	
	public CmsIslike() {
		super();
	}

	public CmsIslike(String id){
		super(id);
	}

	@ExcelField(title="Info 的id", align=2, sort=1)
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	
	@ExcelField(title="info 的title", align=2, sort=2)
	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}
	
	@ExcelField(title="purchaser_account  的id", align=2, sort=3)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="purchaser_account  的name", align=2, sort=4)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="1 喜欢 0不喜欢", align=2, sort=5)
	public String getInfoIslike() {
		return infoIslike;
	}

	public void setInfoIslike(String infoIslike) {
		this.infoIslike = infoIslike;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="时间", align=2, sort=6)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}