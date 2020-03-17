/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * aEntity
 * @author abc
 * @version 2019-06-25
 */
public class AppIndustry extends DataEntity<AppIndustry> {
	
	private static final long serialVersionUID = 1L;
	private String industry;		// 行业
	private String onelevel;		// 一级行业分类
	private String onelevelid;		// 一级行业分类id
	
	public AppIndustry() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public AppIndustry(String id){
		super(id);
	}

	@ExcelField(title="行业", align=2, sort=1)
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	@ExcelField(title="一级行业分类", align=2, sort=2)
	public String getOnelevel() {
		return onelevel;
	}

	public void setOnelevel(String onelevel) {
		this.onelevel = onelevel;
	}
	
	@ExcelField(title="一级行业分类id", align=2, sort=3)
	public String getOnelevelid() {
		return onelevelid;
	}

	public void setOnelevelid(String onelevelid) {
		this.onelevelid = onelevelid;
	}
	
}