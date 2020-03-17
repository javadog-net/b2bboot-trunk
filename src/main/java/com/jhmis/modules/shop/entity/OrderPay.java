/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

import java.util.List;

/**
 * 订单支付Entity
 * @author tity
 * @version 2018-07-22
 */
public class OrderPay extends DataEntity<OrderPay> {
	
	private static final long serialVersionUID = 1L;
	private String purchaserAccountId;		// 采购商账号ID
	private String paySn;		// 支付单号
	private int apiPayState;		// 1 支付成功  0  未支付
	private List<Orders> ordersList;//订单列表
	private String purchaserAddressId;
	private String purchaserInvoiceId;
	private String isApplyInvoice;
	private String returnUrl;
	private String  isPayState; // 是否显示支付标注
	//只用于查询
	private Integer orderState;
	private String createDateStart;
	private String createDateEnd;
	private String orderSn;		// 订单编号
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
	private String dealerId;		// 供应商ID
	private String purchaserId;
	private String evaluationState;		// 评价状态 0未评价，1已评价，2已过期未评价
	private String evaluationAgainState;		// 追加评价状态 0未评价，1已评价，2已过期未评价
	private String isInvoiceFinished;		// 0、未开  1 已开
	private String orderLock;		// 订单锁定类型:1不用锁定,2需要锁定,默认为1

	private double totalAmount;

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public OrderPay() {
		super();
	}

	public OrderPay(String id){
		super(id);
	}

	public String getIsPayState() {
		return isPayState;
	}

	public void setIsPayState(String isPayState) {
		this.isPayState = isPayState;
	}

	public String getEvaluationState() {
		return evaluationState;
	}

	public void setEvaluationState(String evaluationState) {
		this.evaluationState = evaluationState;
	}

	public String getEvaluationAgainState() {
		return evaluationAgainState;
	}

	public void setEvaluationAgainState(String evaluationAgainState) {
		this.evaluationAgainState = evaluationAgainState;
	}

	public String getIsInvoiceFinished() {
		return isInvoiceFinished;
	}

	public void setIsInvoiceFinished(String isInvoiceFinished) {
		this.isInvoiceFinished = isInvoiceFinished;
	}

	public String getOrderLock() {
		return orderLock;
	}

	public void setOrderLock(String orderLock) {
		this.orderLock = orderLock;
	}

	@ExcelField(title="采购商账号ID", align=2, sort=1)
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}
	
	@ExcelField(title="支付单号", align=2, sort=2)
	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}
	
	@ExcelField(title="1 支付成功  0  未支付", align=2, sort=3)
	public int getApiPayState() {
		return apiPayState;
	}

	public void setApiPayState(int apiPayState) {
		this.apiPayState = apiPayState;
	}

	public List<Orders> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<Orders> ordersList) {
		this.ordersList = ordersList;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getPurchaserAddressId() {
		return purchaserAddressId;
	}

	public void setPurchaserAddressId(String purchaserAddressId) {
		this.purchaserAddressId = purchaserAddressId;
	}

	public String getPurchaserInvoiceId() {
		return purchaserInvoiceId;
	}

	public void setPurchaserInvoiceId(String purchaserInvoiceId) {
		this.purchaserInvoiceId = purchaserInvoiceId;
	}

	public String getIsApplyInvoice() {
		return isApplyInvoice;
	}

	public void setIsApplyInvoice(String isApplyInvoice) {
		this.isApplyInvoice = isApplyInvoice;
	}
}