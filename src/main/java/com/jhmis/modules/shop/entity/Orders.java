/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;

/**
 * 订单管理Entity
 * @author tity
 * @version 2018-07-24
 */
public class Orders extends DataEntity<Orders> {
	
	private static final long serialVersionUID = 1L;
	private String orderSn;		// 订单编号
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
	private String dealerId;		// 供应商ID
	private String kjtAccount;		// 快捷通账号
	private String paySn;		// 支付单号
	private String purchaserId;		// 采购商ID
	private String purchaserAccountId;		// 采购商账号ID
	private String purchaserPhone;		// 采购商手机
	private String paymentCode;		// 支付方式
	private Date paymentDate;		// 付款（支付）时间
	private Double goodsAmount;		// 商品总价格
	private Double orderAmount;		// 订单总价格
	private String evaluationState;		// 评价状态 0未评价，1已评价，2已过期未评价
	private String evaluationAgainState;		// 追加评价状态 0未评价，1已评价，2已过期未评价
	private Integer orderState;		// 订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
	private String refundState;		// 退款状态:0是无退款,1是部分退款,2是全部退款
	private Double refundAmount;		// 退款金额
	private String orderFrom;		// 订单来源   0 平台 1 小程序
	private Date apiPayDate;		// 在线支付动作时间,只要向第三方支付平台提交就会更新
	private String tradeNo;		// 外部交易订单号
	private String addressInfo;		// 采购商地址信息
	private PurchaserAddress purchaseraddressInfo;
	private String shippingInfo;		// 运单信息
	private String expresseName;		// 快递名称
	private Date sendOutTime ;      //发货时间
	private String sendOuter;       //发货人
	private String invoiceInfo;		// 发票信息
	private PurchaserInvoice purchaserInvoice;
	private String isApplyInvoice;		// 申请开发票的状态 0 未申请   1已申请   
	private String isInvoiceFinished;		// 0、未开  1 已开
	private String orderLock;		// 订单锁定类型:1不用锁定,2需要锁定,默认为1
	private Date deliveryTime;		// 确认收货时间
	private String confirmReceiver;  //确认收货人
	private String dealerName;		// 供应商名称
	private String purchaserName;		// 采购商名称
	private String purchaserLoginName;		// 采购商登录账号
	private Date cancelTime; //订单取消时间
	private String canceler ;//订单取消人
	private int interval ;// 订单定时取消时间间隔
	private int apiPayState;//支付状态
	private Date royaltyDate;// 分账时间
	private int royaltyState;//分账是否成功
	private double kjtFee;//快捷通手续费
	private  double invoiceAmount;//发票金额

    private OrderPay orderPay;

    //下单时间查询
	private String createDateStart;
	private String createDateEnd;

	private List<OrderGoods> ordersGoodsList;  //查询时使用

	private String productName; //商品名称
	private String productNo; //商品编号
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getConfirmReceiver() {
		return confirmReceiver;
	}

	public void setConfirmReceiver(String confirmReceiver) {
		this.confirmReceiver = confirmReceiver;
	}

	public Orders() {
		super();
	}
	public Orders(String id){
		super(id);
	}

	@ExcelField(title="订单编号", align=2, sort=1)
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@ExcelField(title="店铺ID", align=2, sort=2)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="店铺名称", align=2, sort=3)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@ExcelField(title="供应商ID", align=2, sort=4)
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	
	@ExcelField(title="快捷通账号", align=2, sort=5)
	public String getKjtAccount() {
		return kjtAccount;
	}

	public void setKjtAccount(String kjtAccount) {
		this.kjtAccount = kjtAccount;
	}
	
	@ExcelField(title="支付单号", align=2, sort=6)
	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}
	
	@ExcelField(title="采购商ID", align=2, sort=7)
	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}
	
	@ExcelField(title="采购商账号ID", align=2, sort=8)
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}
	
	@ExcelField(title="采购商手机", align=2, sort=9)
	public String getPurchaserPhone() {
		return purchaserPhone;
	}

	public void setPurchaserPhone(String purchaserPhone) {
		this.purchaserPhone = purchaserPhone;
	}
	
	@ExcelField(title="支付方式", dictType="payment_kind", align=2, sort=11)
	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="付款（支付）时间", align=2, sort=12)
	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	@NotNull(message="商品总价格不能为空")
	@ExcelField(title="商品总价格", align=2, sort=13)
	public Double getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(Double goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	
	@NotNull(message="订单总价格不能为空")
	@ExcelField(title="订单总价格", align=2, sort=14)
	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	@ExcelField(title="评价状态 0未评价，1已评价，2已过期未评价", dictType="evaluation_state", align=2, sort=15)
	public String getEvaluationState() {
		return evaluationState;
	}

	public void setEvaluationState(String evaluationState) {
		this.evaluationState = evaluationState;
	}
	
	@ExcelField(title="追加评价状态 0未评价，1已评价，2已过期未评价", dictType="evaluation_state", align=2, sort=16)
	public String getEvaluationAgainState() {
		return evaluationAgainState;
	}

	public void setEvaluationAgainState(String evaluationAgainState) {
		this.evaluationAgainState = evaluationAgainState;
	}
	
	@ExcelField(title="订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;", dictType="order_state", align=2, sort=17)
	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	
	@ExcelField(title="退款状态:0是无退款,1是部分退款,2是全部退款", dictType="order_refund_state", align=2, sort=18)
	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}
	
	@ExcelField(title="退款金额", align=2, sort=19)
	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	
	@ExcelField(title="订单来源   0 平台 1 小程序", align=2, sort=21)
	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="在线支付动作时间,只要向第三方支付平台提交就会更新", align=2, sort=22)
	public Date getApiPayDate() {
		return apiPayDate;
	}

	public void setApiPayDate(Date apiPayDate) {
		this.apiPayDate = apiPayDate;
	}
	
	@ExcelField(title="外部交易订单号", align=2, sort=23)
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@ExcelField(title="运单号", align=2, sort=25)
	public String getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(String shippingInfo) {
		this.shippingInfo = shippingInfo;
	}
	
	@ExcelField(title="快递名称", align=2, sort=26)
	public String getExpresseName() {
		return expresseName;
	}

	public void setExpresseName(String expresseName) {
		this.expresseName = expresseName;
	}
	@ExcelField(title="申请开发票的状态 0 未申请   1已申请   ", align=2, sort=28)
	public String getIsApplyInvoice() {
		return isApplyInvoice;
	}

	public void setIsApplyInvoice(String isApplyInvoice) {
		this.isApplyInvoice = isApplyInvoice;
	}
	
	@ExcelField(title="0、未开  1 已开", dictType="yes_no", align=2, sort=29)
	public String getIsInvoiceFinished() {
		return isInvoiceFinished;
	}

	public void setIsInvoiceFinished(String isInvoiceFinished) {
		this.isInvoiceFinished = isInvoiceFinished;
	}
	
	@ExcelField(title="订单锁定类型:1不用锁定,2需要锁定,默认为1", dictType="order_lock", align=2, sort=30)
	public String getOrderLock() {
		return orderLock;
	}

	public void setOrderLock(String orderLock) {
		this.orderLock = orderLock;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="确认收货时间", align=2, sort=31)
	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	@ExcelField(title="供应商名称", align=2, sort=32)
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	
	@ExcelField(title="采购商名称", align=2, sort=33)
	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}
	
	@ExcelField(title="采购商登录账号", align=2, sort=34)
	public String getPurchaserLoginName() {
		return purchaserLoginName;
	}

	public void setPurchaserLoginName(String purchaserLoginName) {
		this.purchaserLoginName = purchaserLoginName;
	}
	@ExcelField(title="订单取消时间", align=2, sort=35)
	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	public List<OrderGoods> getOrdersGoodsList() {
		return ordersGoodsList;
	}

	public void setOrdersGoodsList(List<OrderGoods> ordersGoodsList) {
		this.ordersGoodsList = ordersGoodsList;
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

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	public String getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;

	}

	public PurchaserAddress getPurchaseraddressInfo() {
		return purchaseraddressInfo;
	}

	public void setPurchaseraddressInfo(PurchaserAddress purchaseraddressInfo) {
		this.purchaseraddressInfo = purchaseraddressInfo;
	}

	public PurchaserInvoice getPurchaserInvoice() {
		return purchaserInvoice;
	}

	public void setPurchaserInvoice(PurchaserInvoice purchaserInvoice) {
		this.purchaserInvoice = purchaserInvoice;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public Date getSendOutTime() {
		return sendOutTime;
	}

	public void setSendOutTime(Date sendOutTime) {
		this.sendOutTime = sendOutTime;
	}

	public String getSendOuter() {
		return sendOuter;
	}

	public void setSendOuter(String sendOuter) {
		this.sendOuter = sendOuter;
	}

	public String getCanceler() {
		return canceler;
	}

	public void setCanceler(String canceler) {
		this.canceler = canceler;
	}

	public int getApiPayState() {
		return apiPayState;
	}

	public void setApiPayState(int apiPayState) {
		this.apiPayState = apiPayState;
	}

	public Date getRoyaltyDate() {
		return royaltyDate;
	}

	public void setRoyaltyDate(Date royaltyDate) {
		this.royaltyDate = royaltyDate;
	}

	public int getRoyaltyState() {
		return royaltyState;
	}

	public void setRoyaltyState(int royaltyState) {
		this.royaltyState = royaltyState;
	}
    public OrderPay getOrderPay() {
        return orderPay;
    }

    public void setOrderPay(OrderPay orderPay) {
        this.orderPay = orderPay;
    }

	public double getKjtFee() {
		return kjtFee;
	}

	public void setKjtFee(double kjtFee) {
		this.kjtFee = kjtFee;
	}

	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
}