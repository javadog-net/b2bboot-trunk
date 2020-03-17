/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 访问记录Entity
 * @author lydia
 * @version 2019-10-14
 */
public class Visit extends DataEntity<Visit> {
	
	private static final long serialVersionUID = 1L;
	private String categoryId;		// category_id
	private String infoId;		// info_id
	private String url;		// url
	private String ip;		// ip
	
	public Visit() {
		super();
	}

	public Visit(String id){
		super(id);
	}

	@ExcelField(title="category_id", align=2, sort=1)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@ExcelField(title="info_id", align=2, sort=2)
	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	
	@ExcelField(title="url", align=2, sort=3)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@ExcelField(title="ip", align=2, sort=5)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}