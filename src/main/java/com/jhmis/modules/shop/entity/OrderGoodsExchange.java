/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;

/**
 * Entity
 * @author cuihj
 * @version 2018-08-20
 */
public class OrderGoodsExchange extends DataEntity<OrderGoodsExchange> {
	
	private static final long serialVersionUID = 1L;
	private String orderGoodsId;		// 订单商品ID
	private String exchangeNum;		// 换货数量
	private String applyer;		// 申请人
	private Date applyTime;		// 申请时间
	private String auditState;		// 审核状态 1 审核通过 2 拒绝
	private Date auditTime;		// 审核时间
	private String auditor;		// 审核人
	private String auditDesc;		// 审核意见

	private String goodName;// 换货产品名称
	private String exchangeReason; //换货原因
	private String remarks;//备注

	private String createDateStart;//申请单开始时间
	private String createDateEnd;//申请单结束

	private String auditTimeStart;//审核开始时间
	private String auditTimeEnd;//审核结束时间



	private String orderSn ; // 订单号
	private String storeId ;
	private String  storeName; // 商铺名称
	private String  dealerId;
	private String kjtAccount ; //快捷通账号
	private String paySn ;       //支付单号
	private double goodsAmount; // 商品总价
	private Double orderAmount;		// 订单总价格
	private Date apiPayDate;		// 在线支付动作时间,只要向第三方支付平台提交就会更新
	private String tradeNo;		// 外部交易订单号
	private String addressInfo;		// 采购商地址信息
	private PurchaserAddress purchaseraddressInfo;
	private String shippingInfo;		// 运单信息
	private String goodsCode;		// 商品编码
	private String oldGoodsName;		// 商品名称
	private Double price;		// 商品价格
	private Integer num;		// 商品数量
	private String mainPicUrl;		// 商品主图url
	private Double payPrice;		// 商品实际成交价
	private String purchaserId;//采购商ID
	private String purchaserLoginName; //采购商登录账号
	private String purchaserRealName; //采购商真实姓名
	private String purchaserMobile; //采购商电话
	private String dealerLoginName;// 供应商登录账号
	private String dealerRealName ;// 供应商真实姓名
	private String dealerMobile;//供应商电话

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

	public String getAuditTimeStart() {
		return auditTimeStart;
	}

	public void setAuditTimeStart(String auditTimeStart) {
		this.auditTimeStart = auditTimeStart;
	}

	public String getAuditTimeEnd() {
		return auditTimeEnd;
	}

	public void setAuditTimeEnd(String auditTimeEnd) {
		this.auditTimeEnd = auditTimeEnd;
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

	public String getKjtAccount() {
		return kjtAccount;
	}

	public void setKjtAccount(String kjtAccount) {
		this.kjtAccount = kjtAccount;
	}

	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	public double getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(double goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Date getApiPayDate() {
		return apiPayDate;
	}

	public void setApiPayDate(Date apiPayDate) {
		this.apiPayDate = apiPayDate;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	public PurchaserAddress getPurchaseraddressInfo() {
		return purchaseraddressInfo;
	}

	public void setPurchaseraddressInfo(PurchaserAddress purchaseraddressInfo) {
		this.purchaseraddressInfo = purchaseraddressInfo;
	}

	public String getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(String shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getOldGoodsName() {
		return oldGoodsName;
	}

	public void setOldGoodsName(String oldGoodsName) {
		this.oldGoodsName = oldGoodsName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public Double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}

	public String getPurchaserLoginName() {
		return purchaserLoginName;
	}

	public void setPurchaserLoginName(String purchaserLoginName) {
		this.purchaserLoginName = purchaserLoginName;
	}

	public String getPurchaserRealName() {
		return purchaserRealName;
	}

	public void setPurchaserRealName(String purchaserRealName) {
		this.purchaserRealName = purchaserRealName;
	}

	public String getPurchaserMobile() {
		return purchaserMobile;
	}

	public void setPurchaserMobile(String purchaserMobile) {
		this.purchaserMobile = purchaserMobile;
	}

	public String getDealerLoginName() {
		return dealerLoginName;
	}

	public void setDealerLoginName(String dealerLoginName) {
		this.dealerLoginName = dealerLoginName;
	}

	public String getDealerRealName() {
		return dealerRealName;
	}

	public void setDealerRealName(String dealerRealName) {
		this.dealerRealName = dealerRealName;
	}

	public String getDealerMobile() {
		return dealerMobile;
	}

	public void setDealerMobile(String dealerMobile) {
		this.dealerMobile = dealerMobile;
	}

	public OrderGoodsExchange() {
		super();
	}

	public OrderGoodsExchange(String id){
		super(id);
	}

	@ExcelField(title="订单商品ID", align=2, sort=1)
	public String getOrderGoodsId() {
		return orderGoodsId;
	}

	public void setOrderGoodsId(String orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}
	
	@ExcelField(title="换货数量", align=2, sort=2)
	public String getExchangeNum() {
		return exchangeNum;
	}

	public void setExchangeNum(String exchangeNum) {
		this.exchangeNum = exchangeNum;
	}
	
	@ExcelField(title="申请人", align=2, sort=3)
	public String getApplyer() {
		return applyer;
	}

	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="申请时间不能为空")
	@ExcelField(title="申请时间", align=2, sort=4)
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	@ExcelField(title="审核状态 1 审核通过 2 拒绝", align=2, sort=5)
	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间", align=2, sort=6)
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@ExcelField(title="审核人", align=2, sort=7)
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@ExcelField(title="审核意见", align=2, sort=8)
	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}
	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getExchangeReason() {
		return exchangeReason;
	}

	public void setExchangeReason(String exchangeReason) {
		this.exchangeReason = exchangeReason;
	}

	@Override
	public String getRemarks() {
		return remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
}