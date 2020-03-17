/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 退款管理Entity
 * @author tity
 * @version 2018-07-22
 */
public class RefundReturn extends DataEntity<RefundReturn> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单ID
	private String orderSn;		// 订单编号
	private String refundSn;		// 申请编号
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
	private String purchaserId;		// 采购商ID
	private String purchaserName;		// 采购商名称
	private String code;		// 产品编码
	private String orderGoodsId;		// 订单商品ID
	private String goodsName;		// 商品名称
	private Integer goodsNum;		// 商品数量
	private Double refundAmount;		// 退款金额
	private String mainPicUrl;		// 商品主图地址
	private String orderGoodsType;		// 订单商品类型:1默认2抢购商品3组合套装
	private String refundType;		// 申请类型:1 退款,2 退货,默认为1
	private String refundState;		// 申请状态:1 处理中,2 待管理员处理,3 已完成,默认为1
	private String purchaserDesc;		// 采购商申请原因
	private String returnType;		// 退货类型:1不用退货,2需要退货,默认为1
	private String orderLock;		// 订单锁定类型:1不用锁定,2需要锁定,默认为1
	private String shipingCode;		// 物流单号
	private Date addTime;		// 申请时间
	private String picInfo;		// 图片
	private String reasonInfo;		// 原因内容
	private Date auditTime;		// 审核时间
	private String auditor;		// 审核人
	private String auditState;		// 审核状态:1待审核,2同意,3不同意,默认为1
	private String auditDesc;		// 审核备注
	private Date refundTime;		// 退款时间
	private String reasonId;		// 原因ID:0为其它
	private String refundDesc;		// 退款备注
	
	public RefundReturn() {
		super();
	}

	public RefundReturn(String id){
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
	
	@ExcelField(title="申请编号", align=2, sort=3)
	public String getRefundSn() {
		return refundSn;
	}

	public void setRefundSn(String refundSn) {
		this.refundSn = refundSn;
	}
	
	@ExcelField(title="店铺ID", align=2, sort=4)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="店铺名称", align=2, sort=5)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@ExcelField(title="采购商ID", align=2, sort=6)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="采购商名称", align=2, sort=7)
	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	@ExcelField(title="产品编码", align=2, sort=8)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="订单商品ID", align=2, sort=9)
	public String getOrderGoodsId() {
		return orderGoodsId;
	}

	public void setOrderGoodsId(String orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}
	
	@ExcelField(title="商品名称", align=2, sort=10)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@NotNull(message="商品数量不能为空")
	@ExcelField(title="商品数量", align=2, sort=11)
	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	
	@ExcelField(title="退款金额", align=2, sort=12)
	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	@ExcelField(title="商品主图地址", align=2, sort=13)
	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}
	
	@ExcelField(title="订单商品类型:1默认2抢购商品3组合套装", dictType="order_goods_type", align=2, sort=14)
	public String getOrderGoodsType() {
		return orderGoodsType;
	}

	public void setOrderGoodsType(String orderGoodsType) {
		this.orderGoodsType = orderGoodsType;
	}
	
	@ExcelField(title="申请类型:1 退款,2 退货,默认为1", dictType="refund_type", align=2, sort=15)
	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	
	@ExcelField(title="申请状态:1 处理中,2 待管理员处理,3 已完成,默认为1", dictType="refund_process_state", align=2, sort=16)
	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	
	@ExcelField(title="采购商申请原因", align=2, sort=17)
	public String getPurchaserDesc() {
		return purchaserDesc;
	}

	public void setPurchaserDesc(String purchaserDesc) {
		this.purchaserDesc = purchaserDesc;
	}
	
	@ExcelField(title="退货类型:1不用退货,2需要退货,默认为1", dictType="order_return_type", align=2, sort=18)
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	@ExcelField(title="订单锁定类型:1不用锁定,2需要锁定,默认为1", dictType="order_lock", align=2, sort=19)
	public String getOrderLock() {
		return orderLock;
	}

	public void setOrderLock(String orderLock) {
		this.orderLock = orderLock;
	}
	
	@ExcelField(title="物流单号", align=2, sort=20)
	public String getShipingCode() {
		return shipingCode;
	}

	public void setShipingCode(String shipingCode) {
		this.shipingCode = shipingCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="申请时间", align=2, sort=21)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="图片", align=2, sort=22)
	public String getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(String picInfo) {
		this.picInfo = picInfo;
	}
	
	@ExcelField(title="原因内容", align=2, sort=23)
	public String getReasonInfo() {
		return reasonInfo;
	}

	public void setReasonInfo(String reasonInfo) {
		this.reasonInfo = reasonInfo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=24)
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@ExcelField(title="审核人", align=2, sort=25)
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@ExcelField(title="审核状态:1待审核,2同意,3不同意,默认为1", dictType="audit_state", align=2, sort=26)
	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	
	@ExcelField(title="审核备注", align=2, sort=27)
	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="退款时间", align=2, sort=28)
	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	
	@ExcelField(title="原因ID:0为其它", align=2, sort=29)
	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	
	@ExcelField(title="退款备注", align=2, sort=30)
	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}
	
}