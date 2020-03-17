package com.haier.order.dto;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;
import com.jhmis.modules.shop.entity.purchaser.PurchaserInvoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMainDTO{
	private String uuid;
	private String oper;
	private String opeTime;
	private String createOper;
	private String createOpeTime;
	private int version;
	private String sortName = "opeTime";
	private String sortType = "desc";
	private Map<String, Integer> mapCondition = new HashMap();

	/**
	 * 订单组uuid
	 */
	
	private String orderGroupUuid;
	/**
	 * 订单业务编号
	 */

	private String orderId;
	/**
	 * 订单状态
	 */
	
	private String orderState;
	/**
	 * 订单类型
	 */
	
	private String orderType;
	/**
	 * 发货时间
	 */
	
	private String sendTime;

	/**
	 * 付款时间
	 */
	private String payTime;
	/**
	 * 确认到货时间
	 */
	
	private String receiveTime;
	/**
	 * 商户uuid
	 */
	
	private String storeUuid;
	/**
	 * 会员uuid
	 */
	
	private String customerUuid;
	
	/**
	 * 订单总金额
	 * 售卖价 * 购买数量 + 原始运费
	 * totalPrice和促销，优惠券，改价没有一点关系
	 * 商户在价格管理设置了售卖价，在物流设置了运费，会员勾选了购买数量，那么totalPrice就确定下来了，再不会变了
	 */
	
	private double totalPrice;
	
	/**
	 * 主单优惠
	 * 来源有3个：
	 * 1.店铺促销，自然算是店铺级优惠了
	 * 2.优惠券，算作店铺级优惠
	 * 3.改运费，也算作店铺级优惠
	 */
	
	private double orderFreePrice;
	/**
	 * 总优惠信息
	 * 除了上边的店铺级优惠orderFreePrice
	 * 还有sku级优惠，Σ(order_detail.totalFreePrice)
	 */
	
	private double totalFreePrice;
	/**
	 * 应付金额
	 */
	
	private double payPrice;
	/**
	 * 时间支付运费运费
	 */
	
	private double freight;
	/**
	 * 模板计算运费
	 */
	
	private double tempFreight;
	/**
	 * 退款金额
	 */
	
	private double refundMoney;
	/**
	 * 订单赠送积分
	 */
	private int integral;
	/**
	 * 店铺评论状态
	 */
	
	private String shopState;
	/**
	 * 晒单状态
	 */
	
	private String sunState;
	/**
	 * 评论状态
	 */
	
	private String appraiseState;
	/**
	 * 退货状态
	 */
	
	private String returnState;
	/**
	 * 换货状态
	 */
	
	private String changeState;
	/**
	 * 退款状态
	 */
	
	private String refundState;
	/**
	 * 结算状态 0未开始，1完成，2未完成
	 */
	
	private String accountState;
	/**
	 * 支付状态
	 */
	
	private String payState;

	/**
	 * 赠送积分状态
	 */
	
	private String sendPointsState;

	/**
	 * 赠送优惠券状态
	 */
	
	private String sendCouponState;

	/**
	 * 延迟天数
	 */
	private int delayDays;
	/**
	 * 延迟原因
	 */
	
	private String delayReason;
	/**
	 * 介绍
	 */
	
	private String note;
	/**
	 * 配送方式
	 */
	
	private String shipType;
	/**
	 * 支付方式
	 */
	
	private String payType;
	/**
	 * 商户名称
	 */
	
	private String storeName;
	/**
	 * 用户名称
	 */
	
	private String customerName;
	
	
	/**
	 * 银行支付码
	 */
	private String bankCode;
	
	/**
	 * 支付产品码
	 */
	private String payProductCode;
	
	/**
	 * 商户分账比例快照
	*/
	private double storeSplitRatio;

	/**
	 * 订单永久删除标志 与 delFlag 状态一致
	 */
	private int permanentDel = 1;
	
	/**
	 * 是否来源积分商城，0：否，1：是、
	 */
	
	private String isFromIntegral="0";
	
	/**
	 * 积分支付
	 */
	private int payIntegral;
	/**
	 * 收货人
	 */
	
	private   String receive;

	/**
	 * 联系电话
	 */
	
	private   String mobile;

	/**
	 * 收货地址
	 */
	
	private  String receiveAddress;

	/**
	 * 订单明细信息
	 */
	
	private List<OrderDetailDTO> orderDetailList = new ArrayList<OrderDetailDTO>();



	/**
	 * 是否展示售后按钮（系统设置超期时间判断）
	 */
	
	private  boolean afterSaleButton=false;

	/**
	 * 会员中心订单列表页面的订单详情所占行数
	 */
	
	private  int detailRowspan;

	/**
	 * 订单类型中文
	 */
	
	private   String orderTypeName = "";
	/**
	 * 订单配送方式中文
	 */
	
	private   String shipTypeName = "";
	/**
	 * 订单状态中文
	 */
	
	private   String orderStatusName;

	/**
	 * 支付方式中文
	 */
	
	private   String payTypeName = "";

	/**
	 * 商品总金额
	 */
	
	private  double totalAmountPrice=0.0;

	/**
	 * 售后单类型
	 */
	
	private  String serviceTypeName;
	/**
	 * 售后单类型
	 */
	
	private   String serviceType;
	/**
	 * 售后uuid
	 */
	
	private String serviceUuid;
	/**
	 * 商品总金额
	 */
	
	private String productMoney;

	/**
	 * 商品总金额
	 */
	
	private String totalMoney;
	
	/**
	 * 商品名称
	 */
	private String productName;
	
	/**
	 * 商品编号
	 */
	private String productNo;

	private String createOpeTime2;
	/**
	 * 海尔订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
	 */
	private Integer orderStateh;

	/**
	 * 发票信息
	 */
	private PurchaserInvoice purchaserInvoice = new PurchaserInvoice();

	/**
	 * 会员发票uuid
	 */
	private String customerAddInvoiceUuid;
	/**
	 * 发票类型名称
	 */
	private String invoiceTypeName;
	/**
	 * 订单收货地址
	 */
	private OrderAddressDTO orderAddressDTO = new OrderAddressDTO();
	/**
	 * 会员收货地址
	 */
	private PurchaserAddress purchaserAddress = new PurchaserAddress();
	/**
	 * 订单物流信息
	 */
	private List<CloudWarehouseDTO> cloudWarehouseDTOList = new ArrayList<CloudWarehouseDTO>();
	/**
	 * 物流单号
	 */
	private String warehouseCode;


	//送达方编码

	private String shipToPartyCode;

	//送达方名称

	private String shipToPartyName;

	//售达方编码

	private String billToPartyCode;

	//售达方名称

	private String billToPartyName;

	//付款方编码

	private String paymentCode;

	//付款方名称
	private String paymentName;


	//开票方编码

	private String billingCode;

	//开票方名称

	private String billingName;
	/**
	 * 发票状态
	 */
	private String invoiceState;
	private String invoiceStateName;
	/**
	 * 支付状态中文
	 */
	private String payStateName = "";

	/**
	 * 订单日志
	 */
	private List<OrderLogDTO> orderLogDTO = new ArrayList<OrderLogDTO>();
	/**
	 * 订单流水号
	 */
	private String orderDetailId;
	/**
	 * 订单审核状态  0未审核   1审核通过    2审核不通过
	 */
	private String checkedStatus;
	/**
	 * 订单审核时间
	 */
	private String checkedOrderTime;


	public String getCheckedStatus() {
		return checkedStatus;
	}

	public void setCheckedStatus(String checkedStatus) {
		this.checkedStatus = checkedStatus;
	}

	public String getCheckedOrderTime() {
		return checkedOrderTime;
	}

	public void setCheckedOrderTime(String checkedOrderTime) {
		this.checkedOrderTime = checkedOrderTime;
	}


	@ExcelField(title="订单流水号", align=2, sort=2)
	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(String opeTime) {
		this.opeTime = opeTime;
	}

	public String getCreateOper() {
		return createOper;
	}

	public void setCreateOper(String createOper) {
		this.createOper = createOper;
	}
	@ExcelField(title="下单时间", align=2, sort=3)
	public String getCreateOpeTime() {
		return createOpeTime;
	}

	public void setCreateOpeTime(String createOpeTime) {
		this.createOpeTime = createOpeTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public Map<String, Integer> getMapCondition() {
		return mapCondition;
	}

	public void setMapCondition(Map<String, Integer> mapCondition) {
		this.mapCondition = mapCondition;
	}

	public String getOrderGroupUuid() {
		return orderGroupUuid;
	}

	public void setOrderGroupUuid(String orderGroupUuid) {
		this.orderGroupUuid = orderGroupUuid;
	}
	@ExcelField(title="订单编号", align=2, sort=1)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	@ExcelField(title="确认收货时间", align=2, sort=11)
	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getStoreUuid() {
		return storeUuid;
	}

	public void setStoreUuid(String storeUuid) {
		this.storeUuid = storeUuid;
	}

	public String getCustomerUuid() {
		return customerUuid;
	}

	public void setCustomerUuid(String customerUuid) {
		this.customerUuid = customerUuid;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getOrderFreePrice() {
		return orderFreePrice;
	}

	public void setOrderFreePrice(double orderFreePrice) {
		this.orderFreePrice = orderFreePrice;
	}

	public double getTotalFreePrice() {
		return totalFreePrice;
	}

	public void setTotalFreePrice(double totalFreePrice) {
		this.totalFreePrice = totalFreePrice;
	}
	@ExcelField(title="订单金额", align=2, sort=5)
	public double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(double payPrice) {
		this.payPrice = payPrice;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public double getTempFreight() {
		return tempFreight;
	}

	public void setTempFreight(double tempFreight) {
		this.tempFreight = tempFreight;
	}

	public double getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(double refundMoney) {
		this.refundMoney = refundMoney;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getShopState() {
		return shopState;
	}

	public void setShopState(String shopState) {
		this.shopState = shopState;
	}

	public String getSunState() {
		return sunState;
	}

	public void setSunState(String sunState) {
		this.sunState = sunState;
	}

	public String getAppraiseState() {
		return appraiseState;
	}

	public void setAppraiseState(String appraiseState) {
		this.appraiseState = appraiseState;
	}

	public String getReturnState() {
		return returnState;
	}

	public void setReturnState(String returnState) {
		this.returnState = returnState;
	}

	public String getChangeState() {
		return changeState;
	}

	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getSendPointsState() {
		return sendPointsState;
	}

	public void setSendPointsState(String sendPointsState) {
		this.sendPointsState = sendPointsState;
	}

	public String getSendCouponState() {
		return sendCouponState;
	}

	public void setSendCouponState(String sendCouponState) {
		this.sendCouponState = sendCouponState;
	}

	public int getDelayDays() {
		return delayDays;
	}

	public void setDelayDays(int delayDays) {
		this.delayDays = delayDays;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPayProductCode() {
		return payProductCode;
	}

	public void setPayProductCode(String payProductCode) {
		this.payProductCode = payProductCode;
	}

	public double getStoreSplitRatio() {
		return storeSplitRatio;
	}

	public void setStoreSplitRatio(double storeSplitRatio) {
		this.storeSplitRatio = storeSplitRatio;
	}

	public int getPermanentDel() {
		return permanentDel;
	}

	public void setPermanentDel(int permanentDel) {
		this.permanentDel = permanentDel;
	}

	public String getIsFromIntegral() {
		return isFromIntegral;
	}

	public void setIsFromIntegral(String isFromIntegral) {
		this.isFromIntegral = isFromIntegral;
	}

	public int getPayIntegral() {
		return payIntegral;
	}

	public void setPayIntegral(int payIntegral) {
		this.payIntegral = payIntegral;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public List<OrderDetailDTO> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetailDTO> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public boolean isAfterSaleButton() {
		return afterSaleButton;
	}

	public void setAfterSaleButton(boolean afterSaleButton) {
		this.afterSaleButton = afterSaleButton;
	}

	public int getDetailRowspan() {
		return detailRowspan;
	}

	public void setDetailRowspan(int detailRowspan) {
		this.detailRowspan = detailRowspan;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getShipTypeName() {
		return shipTypeName;
	}

	public void setShipTypeName(String shipTypeName) {
		this.shipTypeName = shipTypeName;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}
	@ExcelField(title="支付方式", align=2, sort=6)
	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public double getTotalAmountPrice() {
		return totalAmountPrice;
	}

	public void setTotalAmountPrice(double totalAmountPrice) {
		this.totalAmountPrice = totalAmountPrice;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceUuid() {
		return serviceUuid;
	}

	public void setServiceUuid(String serviceUuid) {
		this.serviceUuid = serviceUuid;
	}

	public String getProductMoney() {
		return productMoney;
	}

	public void setProductMoney(String productMoney) {
		this.productMoney = productMoney;
	}
	@ExcelField(title="商品总价格", align=2, sort=4)
	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

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

	public String getCreateOpeTime2() {
		return createOpeTime2;
	}

	public void setCreateOpeTime2(String createOpeTime2) {
		this.createOpeTime2 = createOpeTime2;
	}
	@ExcelField(title="订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;", dictType="order_state", align=2, sort=9)
	public Integer getOrderStateh() {
		return orderStateh;
	}

	public void setOrderStateh(Integer orderStateh) {
		this.orderStateh = orderStateh;
	}

	public PurchaserInvoice getPurchaserInvoice() {
		return purchaserInvoice;
	}

	public void setPurchaserInvoice(PurchaserInvoice purchaserInvoice) {
		this.purchaserInvoice = purchaserInvoice;
	}

	public String getCustomerAddInvoiceUuid() {
		return customerAddInvoiceUuid;
	}

	public void setCustomerAddInvoiceUuid(String customerAddInvoiceUuid) {
		this.customerAddInvoiceUuid = customerAddInvoiceUuid;
	}

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	public OrderAddressDTO getOrderAddressDTO() {
		return orderAddressDTO;
	}

	public void setOrderAddressDTO(OrderAddressDTO orderAddressDTO) {
		this.orderAddressDTO = orderAddressDTO;
	}

	public PurchaserAddress getPurchaserAddress() {
		return purchaserAddress;
	}

	public void setPurchaserAddress(PurchaserAddress purchaserAddress) {
		this.purchaserAddress = purchaserAddress;
	}

	public List<CloudWarehouseDTO> getCloudWarehouseDTOList() {
		return cloudWarehouseDTOList;
	}

	public void setCloudWarehouseDTOList(List<CloudWarehouseDTO> cloudWarehouseDTOList) {
		this.cloudWarehouseDTOList = cloudWarehouseDTOList;
	}
	@ExcelField(title="运单号", align=2, sort=10)
	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getShipToPartyCode() {
		return shipToPartyCode;
	}

	public void setShipToPartyCode(String shipToPartyCode) {
		this.shipToPartyCode = shipToPartyCode;
	}

	public String getShipToPartyName() {
		return shipToPartyName;
	}

	public void setShipToPartyName(String shipToPartyName) {
		this.shipToPartyName = shipToPartyName;
	}

	public String getBillToPartyCode() {
		return billToPartyCode;
	}

	public void setBillToPartyCode(String billToPartyCode) {
		this.billToPartyCode = billToPartyCode;
	}
	@ExcelField(title="售达方", align=2, sort=7)
	public String getBillToPartyName() {
		return billToPartyName;
	}

	public void setBillToPartyName(String billToPartyName) {
		this.billToPartyName = billToPartyName;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getBillingCode() {
		return billingCode;
	}

	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}

	public String getBillingName() {
		return billingName;
	}

	public void setBillingName(String billingName) {
		this.billingName = billingName;
	}

	public String getInvoiceState() {
		return invoiceState;
	}

	public void setInvoiceState(String invoiceState) {
		this.invoiceState = invoiceState;
	}

	public String getInvoiceStateName() {
		if(StringUtils.isNotBlank(this.getInvoiceState())){
			return InoiceStatusEnum.getName(this.getInvoiceState());
		}
		return "";
	}

	public void setInvoiceStateName(String invoiceStateName) {
		this.invoiceStateName = invoiceStateName;
	}
	@ExcelField(title="支付状态", align=2, sort=8)
	public String getPayStateName() {
		return payStateName;
	}

	public void setPayStateName(String payStateName) {
		this.payStateName = payStateName;
	}

	public List<OrderLogDTO> getOrderLogDTO() {
		return orderLogDTO;
	}

	public void setOrderLogDTO(List<OrderLogDTO> orderLogDTO) {
		this.orderLogDTO = orderLogDTO;
	}
}

