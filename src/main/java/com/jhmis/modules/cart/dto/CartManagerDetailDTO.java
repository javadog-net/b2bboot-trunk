package com.jhmis.modules.cart.dto;

public class CartManagerDetailDTO{


	/**
	 * 购买的库存不足警告
	 */
	public static final String PRODUCT_WARNING_NO_ENOUGH_STOCK = "1";
	/**
	 * 购买的库存紧张警告
	 */
	public static final String PRODUCT_WARNING_LITTLE_STOCK = "2";
	/**
	 * 购物车未选中状态
	 */
	public static final int PRODUCT_CHECKED_FALSE = 0;
	/**
	 * 购物车明细选中状态
	 */
	public static final int PRODUCT_CHECKED_TRUE = 1;

	/**
	 * 购物车信息uuid
	 */
	
	private String cartManagerUuid;
	/**
	 * 商品skuNo
	 */
	private String skuNo;
	/**
	 * 购买数量
	 */
	private int buyNum;
	/**
	 * 当前促销
	 */
	
	private String nowPromotionUuid;
	/**
	 * 排序
	 */
	private int position;
	/**
	 * 是否选中
	 */
	private int checked;


	/**
	 * 商品uuid
	 */
	
	private String productUuid;
	/**
	 * 商品类型
	 */
	
	private String type = "";
	/**
	 * 商品名称
	 */
	
	private String productName;
	/**
	 * 总价格
	 */
	
	private double totalPrice;

	/**
	 * 商品市场价
	 */
	
	private double marketPrice;

	/**
	 * 商品基础价格
	 */
	
	private double basePrice;

	/**
	 * 当前明细总价格
	 */
	
	private double nowPrice;
	/**
	 * 促销优惠金额
	 */
	
	private double promotionOfferMoney;
	/**
	 * 节省多少钱（市场价减去当前的价格）
	 */
	
	private double discountAmountPrice;

	/**
	 * 商品图片
	 */
	
	private String productImg;

	/**
	 * 商品在前台的提示信息，比如库存紧张，库存不足等信息
	 * 1:库存不足
	 * 2:库存紧张
	 */
	
	private String productWarning;

	/**
	 * 库存可售数量
	 */
	
	private int soldNum;

	/**
	 * 是否初始化到数据库--cookie 数据判断
	 */
	
	private boolean esistedInDb;
	/**
	 * 赠送积分数量
	 */
	
	private int integral;

	/**
	 *  sku上下架状态 ProductSkuModel中的常量值
	 */
	
	private String skuState;
	/**
	 * 商品购买规格属性
	 */
	
	private String spec;

	/**
	 * 重量
	 */
	
	private double weight;
	/**
	 * 长
	 */
	
	private double length;
	/**
	 * 宽
	 */
	
	private double width;
	/**
	 * 高
	 */
	
	private double height;
	private String productGroupCode;
	private String productGroupName;

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

	public String getCartManagerUuid() {
		return cartManagerUuid;
	}

	public void setCartManagerUuid(String cartManagerUuid) {
		this.cartManagerUuid = cartManagerUuid;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public String getNowPromotionUuid() {
		return nowPromotionUuid;
	}

	public void setNowPromotionUuid(String nowPromotionUuid) {
		this.nowPromotionUuid = nowPromotionUuid;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public String getProductUuid() {
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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

	public double getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(double nowPrice) {
		this.nowPrice = nowPrice;
	}

	public double getPromotionOfferMoney() {
		return promotionOfferMoney;
	}

	public void setPromotionOfferMoney(double promotionOfferMoney) {
		this.promotionOfferMoney = promotionOfferMoney;
	}

	public double getDiscountAmountPrice() {
		return discountAmountPrice;
	}

	public void setDiscountAmountPrice(double discountAmountPrice) {
		this.discountAmountPrice = discountAmountPrice;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getProductWarning() {
		return productWarning;
	}

	public void setProductWarning(String productWarning) {
		this.productWarning = productWarning;
	}

	public int getSoldNum() {
		return soldNum;
	}

	public void setSoldNum(int soldNum) {
		this.soldNum = soldNum;
	}

	public boolean isEsistedInDb() {
		return esistedInDb;
	}

	public void setEsistedInDb(boolean esistedInDb) {
		this.esistedInDb = esistedInDb;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getSkuState() {
		return skuState;
	}

	public void setSkuState(String skuState) {
		this.skuState = skuState;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}

