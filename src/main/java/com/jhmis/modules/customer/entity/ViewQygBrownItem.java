/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 工程版信息详情视图Entity
 * @author hdx
 * @version 2019-05-29
 */
public class ViewQygBrownItem extends DataEntity<ViewQygBrownItem> {
	
	private static final long serialVersionUID = 1L;
	private String brownId;		// brown_id
	private String productId;		// product_id
	private String productCode;		// product_code
	private String productName;		// product_name
	private String orderQuantity;		// order_quantity
	private String price;		// price
	private String actualOrderQuantity;		// actual_order_quantity
	private String izhikou;		// izhikou
	private String ihoufan;		// ihoufan
	private String itaifan;		// itaifan
	private Date lastModifiedDate;		// 最后修改时间
	private String cinvmUnit;		// 产品单位
	private String iinvrCost;		// 供价
	private String avePolicy;		// 参照平均政策-本型号台阶限价
	private String curPolicy;		// 当前参照最大政策-型号基准价
	private String profit;		// 本型号利润审批价-基准价
	private String createdById;		// 创建人ID
	private String lastModifiedById;		// 最后修改人ID
	private String deleted;		// 是否删除
	private String createdBy;		// 创建人
	private Date createdDate;		// 创建时间
	private String lastModifiedBy;		// 最后修改人名称
	private String creProId;		// 创建来源信息
	private String modProId;		// 修改来源信息
	private Date batchDate;		// 批处理时间
	
	public ViewQygBrownItem() {
		super();
	}

	public ViewQygBrownItem(String id){
		super(id);
	}

	@ExcelField(title="brown_id", align=2, sort=1)
	public String getBrownId() {
		return brownId;
	}

	public void setBrownId(String brownId) {
		this.brownId = brownId;
	}
	
	@ExcelField(title="product_id", align=2, sort=2)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@ExcelField(title="product_code", align=2, sort=3)
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@ExcelField(title="product_name", align=2, sort=4)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@ExcelField(title="order_quantity", align=2, sort=5)
	public String getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	
	@ExcelField(title="price", align=2, sort=6)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	@ExcelField(title="actual_order_quantity", align=2, sort=7)
	public String getActualOrderQuantity() {
		return actualOrderQuantity;
	}

	public void setActualOrderQuantity(String actualOrderQuantity) {
		this.actualOrderQuantity = actualOrderQuantity;
	}
	
	@ExcelField(title="izhikou", align=2, sort=8)
	public String getIzhikou() {
		return izhikou;
	}

	public void setIzhikou(String izhikou) {
		this.izhikou = izhikou;
	}
	
	@ExcelField(title="ihoufan", align=2, sort=9)
	public String getIhoufan() {
		return ihoufan;
	}

	public void setIhoufan(String ihoufan) {
		this.ihoufan = ihoufan;
	}
	
	@ExcelField(title="itaifan", align=2, sort=10)
	public String getItaifan() {
		return itaifan;
	}

	public void setItaifan(String itaifan) {
		this.itaifan = itaifan;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后修改时间", align=2, sort=11)
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	@ExcelField(title="产品单位", align=2, sort=12)
	public String getCinvmUnit() {
		return cinvmUnit;
	}

	public void setCinvmUnit(String cinvmUnit) {
		this.cinvmUnit = cinvmUnit;
	}
	
	@ExcelField(title="供价", align=2, sort=13)
	public String getIinvrCost() {
		return iinvrCost;
	}

	public void setIinvrCost(String iinvrCost) {
		this.iinvrCost = iinvrCost;
	}
	
	@ExcelField(title="参照平均政策-本型号台阶限价", align=2, sort=14)
	public String getAvePolicy() {
		return avePolicy;
	}

	public void setAvePolicy(String avePolicy) {
		this.avePolicy = avePolicy;
	}
	
	@ExcelField(title="当前参照最大政策-型号基准价", align=2, sort=15)
	public String getCurPolicy() {
		return curPolicy;
	}

	public void setCurPolicy(String curPolicy) {
		this.curPolicy = curPolicy;
	}
	
	@ExcelField(title="本型号利润审批价-基准价", align=2, sort=16)
	public String getProfit() {
		return profit;
	}

	public void setProfit(String profit) {
		this.profit = profit;
	}
	
	@ExcelField(title="创建人ID", align=2, sort=17)
	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	
	@ExcelField(title="最后修改人ID", align=2, sort=18)
	public String getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(String lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}
	
	@ExcelField(title="是否删除", align=2, sort=19)
	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	@ExcelField(title="创建人", align=2, sort=20)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=21)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@ExcelField(title="最后修改人名称", align=2, sort=22)
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	@ExcelField(title="创建来源信息", align=2, sort=23)
	public String getCreProId() {
		return creProId;
	}

	public void setCreProId(String creProId) {
		this.creProId = creProId;
	}
	
	@ExcelField(title="修改来源信息", align=2, sort=24)
	public String getModProId() {
		return modProId;
	}

	public void setModProId(String modProId) {
		this.modProId = modProId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="批处理时间", align=2, sort=25)
	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}
	
}