/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商空系列数据Entity
 * @author mll
 * @version 2019-08-14
 */
public class ProductSk extends DataEntity<ProductSk> {
	
	private static final long serialVersionUID = 1L;
	private String number;		// number
	private String name;		// name
	private String channelid;		// channelid
	private String producttype;		// 1大机，2小机
	private String levelid;		// levelid
	private String parentid;		// parentid
	private String parentnumber;		// parentnumber
	private String status;		// status
	private String isvalid;		// isvalid
	private String bigandsmall;		// 1多联机，2水机
	
	public ProductSk() {
		super();
	}

	public ProductSk(String id){
		super(id);
	}

	@ExcelField(title="number", align=2, sort=1)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="name", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="channelid", align=2, sort=3)
	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	
	@ExcelField(title="1大机，2小机", align=2, sort=4)
	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	
	@ExcelField(title="levelid", align=2, sort=5)
	public String getLevelid() {
		return levelid;
	}

	public void setLevelid(String levelid) {
		this.levelid = levelid;
	}
	
	@ExcelField(title="parentid", align=2, sort=6)
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	@ExcelField(title="parentnumber", align=2, sort=7)
	public String getParentnumber() {
		return parentnumber;
	}

	public void setParentnumber(String parentnumber) {
		this.parentnumber = parentnumber;
	}
	
	@ExcelField(title="status", align=2, sort=8)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="isvalid", align=2, sort=9)
	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	
	@ExcelField(title="1多联机，2水机", align=2, sort=10)
	public String getBigandsmall() {
		return bigandsmall;
	}

	public void setBigandsmall(String bigandsmall) {
		this.bigandsmall = bigandsmall;
	}
	
}