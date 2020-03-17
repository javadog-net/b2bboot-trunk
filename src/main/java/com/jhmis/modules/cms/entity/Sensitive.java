/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 敏感词管理Entity
 * @author lydia
 * @version 2019-11-02
 */
public class Sensitive extends DataEntity<Sensitive> {
	
	private static final long serialVersionUID = 1L;
	private String sensitiveword;		// 敏感词
	private String replaceto;		// 替换为
	
	public Sensitive() {
		super();
	}

	public Sensitive(String id){
		super(id);
	}

	@ExcelField(title="敏感词", align=2, sort=1)
	public String getSensitiveword() {
		return sensitiveword;
	}

	public void setSensitiveword(String sensitiveword) {
		this.sensitiveword = sensitiveword;
	}
	
	@ExcelField(title="替换为", align=2, sort=2)
	public String getReplaceto() {
		return replaceto;
	}

	public void setReplaceto(String replaceto) {
		this.replaceto = replaceto;
	}
	
}