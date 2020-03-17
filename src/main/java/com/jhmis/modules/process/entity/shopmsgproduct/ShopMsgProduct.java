/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.shopmsgproduct;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 需求产品子表Entity
 * @author hdx
 * @version 2019-09-23
 */
public class ShopMsgProduct extends DataEntity<ShopMsgProduct> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// 需求ID
	private String productGroupName;		// 产品组名称(客户语言)
	private String productGroupCode;		// 产品组(对应hps产品组)
	private String estimatedQuantity;		// 预计数量
	private String purchaseBudget;		// 采购预算
	private String productDescp;		// 产品组备注
	private String beWisdom;		// 是否智慧
	
	public ShopMsgProduct() {
		super();
	}

	public ShopMsgProduct(String id){
		super(id);
	}

	@ExcelField(title="需求ID", align=2, sort=1)
	public String getMsgId() {
		return msgId;
	}

	public ShopMsgProduct setMsgId(String msgId) {
		this.msgId = msgId;
		return this;
	}
	
	@ExcelField(title="产品组名称(客户语言)", align=2, sort=2)
	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	
	@ExcelField(title="产品组(对应hps产品组)", align=2, sort=3)
	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}
	
	@ExcelField(title="预计数量", align=2, sort=4)
	public String getEstimatedQuantity() {
		return estimatedQuantity;
	}

	public void setEstimatedQuantity(String estimatedQuantity) {
		this.estimatedQuantity = estimatedQuantity;
	}
	
	@ExcelField(title="采购预算", align=2, sort=5)
	public String getPurchaseBudget() {
		return purchaseBudget;
	}

	public void setPurchaseBudget(String purchaseBudget) {
		this.purchaseBudget = purchaseBudget;
	}
	
	@ExcelField(title="产品组备注", align=2, sort=6)
	public String getProductDescp() {
		return productDescp;
	}

	public void setProductDescp(String productDescp) {
		this.productDescp = productDescp;
	}
	
	@ExcelField(title="是否智慧", align=2, sort=7)
	public String getBeWisdom() {
		return beWisdom;
	}

	public void setBeWisdom(String beWisdom) {
		this.beWisdom = beWisdom;
	}
	
}