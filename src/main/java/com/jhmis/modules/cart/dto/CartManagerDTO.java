package com.jhmis.modules.cart.dto;

import java.util.ArrayList;
import java.util.List;

public class CartManagerDTO {

	//	产品组编码
	private String productGroupCode;
	//	产品组名称
	private String productGroupName;

	private List<CartCookieProductDTO> listDirectCart;

	/**
	 * 商户uuid
	 */
	
	private String storeUuid;
	/**
	 * 会员uuid
	 */
	
	private String customerUuid;
	/**
	 * 商户名称
	 */
	private String storeName;
	/**
	 * 发票抬头
	 */
	
	private String orderInvoiceTitle;
	/**
	 * 发票内容
	 */
	
	private String orderInvoiceContent;
	/**
	 * 发票类型
	 */
	
	private String orderInvoiceType;
	/**
	 * 发票种类  个人、公司
	 */
	
	private String orderInvoiceCate;
	/**
	 * 发票税号
	 */
	private String orderInvoiceTaxCode;
	/**
	 * 会员增值税发票uuid
	 */
	
	private String customerAddInvoiceUuid;
	/**
	 * 配送方式
	 */
	
	private String shipType="1";
	/**
	 * 支付方式
	 */
	
	private String payType;
	
	/**
	 * 主仓
	 */
	private String mainHouse;
	
	/**
	 * 产品组编码信息
	 */
	private String prodLineCode;
	
	/**
	 * 销售工厂
	 */
	private String salesFactory;

	/**
	 * 会员名称
	 */
	
	private String customerName;
	/**
	 *  自提点编号
	 *  */
	
	private String stationUuid;

	/**
	 *  自提时间
	 *  */
	
	private String pickUpTime;
	/**
	 * 总金额
	 */
	
	private double totalMoney;

	/**
	 * 运费
	 */
	
	private double affix = 0;

	/**
	 * 支付金额
	 */
	
	private double payMoney;
	
	
	private String payMoneyStr;

	/**
	 * 商户参与的服务
	 */
	
	private List storeProtions = new ArrayList();

	/**
	 * 购物车明细
	 */
	
	private List<CartManagerDetailDTO> cartManagerDetailList = new ArrayList<CartManagerDetailDTO>();
	
	
	/**
	 * 商户店铺LOGO
	 */
	
	private String storeLogo;
	
	
	/**
	 * 判断店铺选中按钮是否选中
	 */
	
	private boolean checked = true;
	/**
	 * 去结算按钮是否显示  true：显示，false:不显示
	 */
	
	private boolean showToBlance = true;
	/**
	 * 是否支持货到付款
	 */
	
	private boolean suppotCod = false;

	/**
	 * 订单获得总赠送积分
	 */
	
	private int totalIntegral;

	/**
	 * 是否满足免运费 1：满足 2：不满足 PromotionInteactiveModel 常量值
	 */
	
	private String isOrderFreeFreight="2";
	/**
	 * 商户发票类型
	 */
	
	private String[] invoiceType;
	/**
	 * 商户发票内容
	 */
	
	private String[] invoiceContent;
	/**
	 * 商户发票种类
	 */
	private String[] invoiceCate;
	/**
	 * 买家留言
	 */
	private String storeNote;

	/**
	 * 店铺商品ID和数量，多个商品以逗号隔开
	 */
	private String storeGoodsIds;
	/**
	 * 地址ID
	 */
	private String purchaserAddressId;
	/**
	 * 发票ID
	 */
	private String purchaserInvoiceId;
	/**
	 * 是否需要开发票
	 */
	private String isApplyInvoice;

	//送达方编码

	private String shipToPartyCode;

	//送达方名称

	private String shipToPartyName;

	//售达方编码

	private String billToPartyCode;

	//售达方名称

	private String billToPartyName;

	//付款方编码

	private String paymentName;

	//付款方名称

	private String paymentCode;

	//开票方编码

	private String billingCode;

	//开票方名称

	private String billingName;
	/**
	 * 期待收货时间
	 */
	private String receiveTime;

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getOrderInvoiceTaxCode() {
		return orderInvoiceTaxCode;
	}

	public void setOrderInvoiceTaxCode(String orderInvoiceTaxCode) {
		this.orderInvoiceTaxCode = orderInvoiceTaxCode;
	}

	public String getStoreGoodsIds() {
		return storeGoodsIds;
	}

	public void setStoreGoodsIds(String storeGoodsIds) {
		this.storeGoodsIds = storeGoodsIds;
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

	public String getBillToPartyName() {
		return billToPartyName;
	}

	public void setBillToPartyName(String billToPartyName) {
		this.billToPartyName = billToPartyName;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
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
	
	

	public String getMainHouse() {
		return mainHouse;
	}

	public void setMainHouse(String mainHouse) {
		this.mainHouse = mainHouse;
	}

	public String getProdLineCode() {
		return prodLineCode;
	}

	public void setProdLineCode(String prodLineCode) {
		this.prodLineCode = prodLineCode;
	}

	public String getSalesFactory() {
		return salesFactory;
	}

	public void setSalesFactory(String salesFactory) {
		this.salesFactory = salesFactory;
	}

	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public List<CartCookieProductDTO> getListDirectCart() {
		return listDirectCart;
	}

	public void setListDirectCart(List<CartCookieProductDTO> listDirectCart) {
		this.listDirectCart = listDirectCart;
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getOrderInvoiceTitle() {
		return orderInvoiceTitle;
	}

	public void setOrderInvoiceTitle(String orderInvoiceTitle) {
		this.orderInvoiceTitle = orderInvoiceTitle;
	}

	public String getOrderInvoiceContent() {
		return orderInvoiceContent;
	}

	public void setOrderInvoiceContent(String orderInvoiceContent) {
		this.orderInvoiceContent = orderInvoiceContent;
	}

	public String getOrderInvoiceType() {
		return orderInvoiceType;
	}

	public void setOrderInvoiceType(String orderInvoiceType) {
		this.orderInvoiceType = orderInvoiceType;
	}

	public String getOrderInvoiceCate() {
		return orderInvoiceCate;
	}

	public void setOrderInvoiceCate(String orderInvoiceCate) {
		this.orderInvoiceCate = orderInvoiceCate;
	}

	public String getCustomerAddInvoiceUuid() {
		return customerAddInvoiceUuid;
	}

	public void setCustomerAddInvoiceUuid(String customerAddInvoiceUuid) {
		this.customerAddInvoiceUuid = customerAddInvoiceUuid;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStationUuid() {
		return stationUuid;
	}

	public void setStationUuid(String stationUuid) {
		this.stationUuid = stationUuid;
	}

	public String getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public double getAffix() {
		return affix;
	}

	public void setAffix(double affix) {
		this.affix = affix;
	}

	public double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public String getPayMoneyStr() {
		return payMoneyStr;
	}

	public void setPayMoneyStr(String payMoneyStr) {
		this.payMoneyStr = payMoneyStr;
	}

	public List getStoreProtions() {
		return storeProtions;
	}

	public void setStoreProtions(List storeProtions) {
		this.storeProtions = storeProtions;
	}

	public List<CartManagerDetailDTO> getCartManagerDetailList() {
		return cartManagerDetailList;
	}

	public void setCartManagerDetailList(List<CartManagerDetailDTO> cartManagerDetailList) {
		this.cartManagerDetailList = cartManagerDetailList;
	}

	public String getStoreLogo() {
		return storeLogo;
	}

	public void setStoreLogo(String storeLogo) {
		this.storeLogo = storeLogo;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isShowToBlance() {
		return showToBlance;
	}

	public void setShowToBlance(boolean showToBlance) {
		this.showToBlance = showToBlance;
	}

	public boolean isSuppotCod() {
		return suppotCod;
	}

	public void setSuppotCod(boolean suppotCod) {
		this.suppotCod = suppotCod;
	}

	public int getTotalIntegral() {
		return totalIntegral;
	}

	public void setTotalIntegral(int totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	public String getIsOrderFreeFreight() {
		return isOrderFreeFreight;
	}

	public void setIsOrderFreeFreight(String isOrderFreeFreight) {
		this.isOrderFreeFreight = isOrderFreeFreight;
	}

	public String[] getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String[] invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String[] getInvoiceContent() {
		return invoiceContent;
	}

	public void setInvoiceContent(String[] invoiceContent) {
		this.invoiceContent = invoiceContent;
	}

	public String[] getInvoiceCate() {
		return invoiceCate;
	}

	public void setInvoiceCate(String[] invoiceCate) {
		this.invoiceCate = invoiceCate;
	}

	public String getStoreNote() {
		return storeNote;
	}

	public void setStoreNote(String storeNote) {
		this.storeNote = storeNote;
	}
}

