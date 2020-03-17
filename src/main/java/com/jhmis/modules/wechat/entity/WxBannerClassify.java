/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * banner分类表Entity
 * @author lvyangzhuo
 * @version 2018-11-22
 */
public class WxBannerClassify extends DataEntity<WxBannerClassify> {
	
	private static final long serialVersionUID = 1L;
	private String classifyName;		// classify_name
	private String addPerson;		// add_person
	private String createTime;		// create_time
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String remark3;		// remark3
	
	public WxBannerClassify() {
		super();
	}

	public WxBannerClassify(String id){
		super(id);
	}

	@ExcelField(title="classify_name", align=2, sort=1)
	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	
	@ExcelField(title="add_person", align=2, sort=2)
	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}
	
	@ExcelField(title="create_time", align=2, sort=3)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@ExcelField(title="remark1", align=2, sort=4)
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@ExcelField(title="remark2", align=2, sort=5)
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@ExcelField(title="remark3", align=2, sort=6)
	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	
}