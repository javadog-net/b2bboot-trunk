/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 工贸信息Entity
 * @author tity
 * @version 2018-07-22
 */
public class GmInfo extends DataEntity<GmInfo> {
	
	private static final long serialVersionUID = 1L;
	private String gmCode;		// 工贸简称
	private String branchCode;		// 工贸编码
	private String gmName;		// 工贸名称
	private String gmDepart;		// 大区
	private String gmtab;//用户是否是该工贸标识
	public String getGmtab() {
		return gmtab;
	}

	public void setGmtab(String gmtab) {
		this.gmtab = gmtab;
	}

	public GmInfo() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public GmInfo(String id){
		super(id);
	}

	@ExcelField(title="工贸简称", align=2, sort=1)
	public String getGmCode() {
		return gmCode;
	}

	public void setGmCode(String gmCode) {
		this.gmCode = gmCode;
	}
	
	@ExcelField(title="工贸编码", align=2, sort=2)
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	@ExcelField(title="工贸名称", align=2, sort=3)
	public String getGmName() {
		return gmName;
	}

	public void setGmName(String gmName) {
		this.gmName = gmName;
	}
	
	@ExcelField(title="大区", align=2, sort=4)
	public String getGmDepart() {
		return gmDepart;
	}

	public void setGmDepart(String gmDepart) {
		this.gmDepart = gmDepart;
	}
	
}