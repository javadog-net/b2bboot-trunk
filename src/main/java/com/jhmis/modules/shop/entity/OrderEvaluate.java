/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 店铺评分Entity
 * @author tity
 * @version 2018-07-22
 */
public class OrderEvaluate extends DataEntity<OrderEvaluate> {

	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单ID
	private String orderSn;		// 订单编号
	private String storeId;		// 店铺编号
	private String storeName;		// 店铺名称
	private String purchaserId;		// 采购商ID
	private String purchaserAccountId;		// 采购商账号ID
	private Integer scoreProductQuality;		// 产品质量评分
	private Integer scoreDemandResponse;		// 需求响应评分
	private Integer scoreDeliveryCredit;		// 物流配送评分
	private Integer scoreSupplySpeed;		// 供货速度评分
	private Integer scoreCustomerService;		// 售后服务评分
	private String isanonymous;		// 0 否  1 是匿名评价
	private Date addtime;		// 评价时间
	private Date beginAddtime;		// 开始 评价时间
	private Date endAddtime;		// 结束 评价时间
	private List<GoodsEvaluate> goodsEvaluateList;  //多个产品订单

	public List<GoodsEvaluate> getGoodsEvaluateList() {
		return goodsEvaluateList;
	}

	public void setGoodsEvaluateList(List<GoodsEvaluate> goodsEvaluateList) {
		this.goodsEvaluateList = goodsEvaluateList;
	}

	public OrderEvaluate() {
		super();
	}

	public OrderEvaluate(String id){
		super(id);
	}

	@ExcelField(title="订单ID", align=2, sort=1)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@ExcelField(title="订单编号", align=2, sort=2)
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@ExcelField(title="店铺编号", align=2, sort=3)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="店铺名称", align=2, sort=4)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@ExcelField(title="采购商ID", align=2, sort=5)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="采购商账号ID", align=2, sort=6)
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}
	
	@NotNull(message="产品质量评分不能为空")
	@ExcelField(title="产品质量评分", align=2, sort=7)
	public Integer getScoreProductQuality() {
		return scoreProductQuality;
	}

	public void setScoreProductQuality(Integer scoreProductQuality) {
		this.scoreProductQuality = scoreProductQuality;
	}
	
	@NotNull(message="需求响应评分不能为空")
	@ExcelField(title="需求响应评分", align=2, sort=8)
	public Integer getScoreDemandResponse() {
		return scoreDemandResponse;
	}

	public void setScoreDemandResponse(Integer scoreDemandResponse) {
		this.scoreDemandResponse = scoreDemandResponse;
	}
	
	@NotNull(message="物流配送评分不能为空")
	@ExcelField(title="物流配送评分", align=2, sort=9)
	public Integer getScoreDeliveryCredit() {
		return scoreDeliveryCredit;
	}

	public void setScoreDeliveryCredit(Integer scoreDeliveryCredit) {
		this.scoreDeliveryCredit = scoreDeliveryCredit;
	}
	
	@NotNull(message="供货速度评分不能为空")
	@ExcelField(title="供货速度评分", align=2, sort=10)
	public Integer getScoreSupplySpeed() {
		return scoreSupplySpeed;
	}

	public void setScoreSupplySpeed(Integer scoreSupplySpeed) {
		this.scoreSupplySpeed = scoreSupplySpeed;
	}
	
	@NotNull(message="售后服务评分不能为空")
	@ExcelField(title="售后服务评分", align=2, sort=11)
	public Integer getScoreCustomerService() {
		return scoreCustomerService;
	}

	public void setScoreCustomerService(Integer scoreCustomerService) {
		this.scoreCustomerService = scoreCustomerService;
	}
	
	@ExcelField(title="0 否  1 是匿名评价", dictType="yes_no", align=2, sort=12)
	public String getIsanonymous() {
		return isanonymous;
	}

	public void setIsanonymous(String isanonymous) {
		this.isanonymous = isanonymous;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="评价时间不能为空")
	@ExcelField(title="评价时间", align=2, sort=13)
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getBeginAddtime() {
		return beginAddtime;
	}

	public void setBeginAddtime(Date beginAddtime) {
		this.beginAddtime = beginAddtime;
	}
	
	public Date getEndAddtime() {
		return endAddtime;
	}

	public void setEndAddtime(Date endAddtime) {
		this.endAddtime = endAddtime;
	}
		
}