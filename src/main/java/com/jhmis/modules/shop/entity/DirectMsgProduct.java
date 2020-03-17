/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 直采需求商品Entity
 * @author hdx
 * @version 2019-04-03
 */
public class DirectMsgProduct extends DataEntity<DirectMsgProduct> {
	
	private static final long serialVersionUID = 1L;
	private String directMsgId;		// 直采需求id
	private String directMsgOrder;		// 直采需求单号
	private String productGroupCode;		// 产品组编码
	private String productGroupName;		// 产品组名称
	private String storeGoodsId;		// 直采商品ID
	private String num;		// 商品数量
	private String price;		// 商品价格
	private String goodsCode;		// 商品编码
	private Date addTime;		// 添加时间
	private String addUser;		// 添加人
	
	public DirectMsgProduct() {
		super();
	}

	public DirectMsgProduct(String id){
		super(id);
	}

	@ExcelField(title="直采需求id", align=2, sort=1)
	public String getDirectMsgId() {
		return directMsgId;
	}

	public void setDirectMsgId(String directMsgId) {
		this.directMsgId = directMsgId;
	}
	
	@ExcelField(title="直采需求单号", align=2, sort=2)
	public String getDirectMsgOrder() {
		return directMsgOrder;
	}

	public void setDirectMsgOrder(String directMsgOrder) {
		this.directMsgOrder = directMsgOrder;
	}
	
	@ExcelField(title="产品组编码", align=2, sort=3)
	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}
	
	@ExcelField(title="产品组名称", align=2, sort=4)
	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	
	@ExcelField(title="直采商品ID", align=2, sort=5)
	public String getStoreGoodsId() {
		return storeGoodsId;
	}

	public void setStoreGoodsId(String storeGoodsId) {
		this.storeGoodsId = storeGoodsId;
	}
	
	@ExcelField(title="商品数量", align=2, sort=6)
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@ExcelField(title="商品价格", align=2, sort=7)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@ExcelField(title="商品编码", align=2, sort=8)
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="添加时间", align=2, sort=9)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="添加人", align=2, sort=10)
	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	
}