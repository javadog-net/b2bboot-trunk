package com.haier.order.dto;

import javax.persistence.Column;

public class OrderDetailDTO{
	
	/**
	 * 更新完成字段判断 ---更新评论
	 */
	public static final String UPDATE_APPRAISE_STATE = "appraiseState";
	/**
	 * 更新完成字段判断 ---更新晒单
	 */
	public static final String UPDATE_SUN_STATE = "sunState";
	/**
	 * 状态已完成（评论，晒单，明细 状态）
	 */
	public static final String STATE_FINISH = "1";
	/**
	 * 状态未完成（评论，晒单，明细 状态）
	 */
	public static final String STATE_UNFINISH = "2";

	/**
	 * 订单主信息uuid
	 */
	
	private String orderMainUuid;
	/**
	 * 商品skuNo
	 */
	
	private String skuNo;
	/**
	 * 商品名称
	 */
	
	private String productName;
	/**
	 * 商品UUid
	 */
	
	private String productUuid;
	/**
	 * 商品购买规格
	 */
	private String spec;
	/**
	 * 商品市场价
	 */
	private double marketPrice;
	/**
	 * 商品商城价
	 */
	
	private double basePrice;
	/**
	 * 促销价格
	 */
	
	private double promotionPrice;
	/**
	 * 总金额
	 */
	
	private double totalPrice;
	/**
	 * 明细应付金额
	 */
	
	private double payPrice;
	/**
	 * 优惠金额
	 */
	
	private double freeMoney;
	/**
	 * 优惠总金额
	 */
	
	private double totalFreePrice;
	/**
	 * 退款金额
	 */
	
	private double refundMoney;
	/**
	 * 商品赠送积分
	 */
	private int integral;
	/**
	 * 退货数量
	 */
	private int returnNum;
	/**
	 * 购买数量
	 */
	private int buyNum;
	/**
	 * 晒单状态
	 */
	
	private String sunState;
	/**
	 * 评论状态
	 */
	
	private String appraiseState;
	/**
	 * 明细状态
	 */
	
	private String detailState;
	/**
	 * 介绍
	 */
	
	private String note;
	
	/**
	 * sku分账比例快照
	*/
	private double skuSplitRatio;
	
	/**
	 * 商品主图
	 */
	
	private String productMainImageKey;

	/**
	 * 修改订单价格，临时存放的价格
	 */
	
	private double tempPrice = 0;


	/**
	 * 售后类型|售后状态
	 */
	
	private String afterServiceName;

	/**
	 * 售后UUid
	 */
	
	private String afterServiceUuid;
	/**
	 * 红色 小码
	 */
	
	private String specValue="";
	/**
	 * 完整的url http开头
	 */
	
	private String productMainImageUrl="";
	
	/**
	 * 是否展示 售后按钮
	 */
	
	private boolean showAfterButton;  
	/**
	 * 是否展示 售后按钮
	 */
	
	private double returnMoney;

	/**
	 * 订单编号   为了对接海尔锁库接口
	 */
	private String orderDetailId;

	public String getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(String orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getOrderMainUuid() {
		return orderMainUuid;
	}

	public void setOrderMainUuid(String orderMainUuid) {
		this.orderMainUuid = orderMainUuid;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductUuid() {
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public double getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(double payPrice) {
		this.payPrice = payPrice;
	}

	public double getFreeMoney() {
		return freeMoney;
	}

	public void setFreeMoney(double freeMoney) {
		this.freeMoney = freeMoney;
	}

	public double getTotalFreePrice() {
		return totalFreePrice;
	}

	public void setTotalFreePrice(double totalFreePrice) {
		this.totalFreePrice = totalFreePrice;
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

	public int getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(int returnNum) {
		this.returnNum = returnNum;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
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

	public String getDetailState() {
		return detailState;
	}

	public void setDetailState(String detailState) {
		this.detailState = detailState;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getSkuSplitRatio() {
		return skuSplitRatio;
	}

	public void setSkuSplitRatio(double skuSplitRatio) {
		this.skuSplitRatio = skuSplitRatio;
	}

	public String getProductMainImageKey() {
		return productMainImageKey;
	}

	public void setProductMainImageKey(String productMainImageKey) {
		this.productMainImageKey = productMainImageKey;
	}

	public double getTempPrice() {
		return tempPrice;
	}

	public void setTempPrice(double tempPrice) {
		this.tempPrice = tempPrice;
	}

	public String getAfterServiceName() {
		return afterServiceName;
	}

	public void setAfterServiceName(String afterServiceName) {
		this.afterServiceName = afterServiceName;
	}

	public String getAfterServiceUuid() {
		return afterServiceUuid;
	}

	public void setAfterServiceUuid(String afterServiceUuid) {
		this.afterServiceUuid = afterServiceUuid;
	}

	public String getSpecValue() {
		return specValue;
	}

	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}

	public String getProductMainImageUrl() {
		return productMainImageUrl;
	}

	public void setProductMainImageUrl(String productMainImageUrl) {
		this.productMainImageUrl = productMainImageUrl;
	}

	public boolean isShowAfterButton() {
		return showAfterButton;
	}

	public void setShowAfterButton(boolean showAfterButton) {
		this.showAfterButton = showAfterButton;
	}

	public double getReturnMoney() {
		return returnMoney;
	}

	public void setReturnMoney(double returnMoney) {
		this.returnMoney = returnMoney;
	}
}

