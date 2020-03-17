package com.haier.order.dto;

public class OrderMainAppraiseDTO extends BaseModel {
	
	/**
	 *  店铺评分类型 1 商家评分
	 */
	public static final String SCORE_STORE = "1";
	/**
	 *  店铺评分类型 2 发货速度评分
	 */
	public static final String SCORE_SPEED = "2";
	/**
	 *  店铺评分类型 3 如实描述评分
	 */
	public static final String SCORE_DESC = "3";
	/**
	 *  店铺评分类型 4 服务态度评分
	 */
	public static final String SCORE_SERVICE = "4";

	/**
	 * 评价时间
	*/
	
	private String appraiseTime;
	/**
	 * 评价内容
	*/
	
	private String content;
	/**
	 * 评价人名称
	*/
	
	private String customerName;
	/**
	 * 店家对客户的评分
	*/
	
	private int customerScore;
	/**
	 * 会员编号
	*/
	
	private String customerUuid;
	/**
	 * 如实描述评分
	*/
	
	private int descScore;
	/**
	 * 订单编号
	*/
	
	private String orderMainUuid;
	/**
	 * 服务态度评分
	*/
	
	private int serviceScore;
	/**
	 * 发货速度评分
	*/
	
	private int speedScore;
	/**
	 * 店铺回复内容
	*/
	
	private String storeReply;
	/**
	 * 店铺评分
	*/
	
	private int storeScore;
	/**
	 * 店铺编号
	*/
	
	private String storeUuid;


	public String getAppraiseTime() {
		return appraiseTime;
	}

	public void setAppraiseTime(String appraiseTime) {
		this.appraiseTime = appraiseTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getCustomerScore() {
		return customerScore;
	}

	public void setCustomerScore(int customerScore) {
		this.customerScore = customerScore;
	}

	public String getCustomerUuid() {
		return customerUuid;
	}

	public void setCustomerUuid(String customerUuid) {
		this.customerUuid = customerUuid;
	}

	public int getDescScore() {
		return descScore;
	}

	public void setDescScore(int descScore) {
		this.descScore = descScore;
	}

	public String getOrderMainUuid() {
		return orderMainUuid;
	}

	public void setOrderMainUuid(String orderMainUuid) {
		this.orderMainUuid = orderMainUuid;
	}

	public int getServiceScore() {
		return serviceScore;
	}

	public void setServiceScore(int serviceScore) {
		this.serviceScore = serviceScore;
	}

	public int getSpeedScore() {
		return speedScore;
	}

	public void setSpeedScore(int speedScore) {
		this.speedScore = speedScore;
	}

	public String getStoreReply() {
		return storeReply;
	}

	public void setStoreReply(String storeReply) {
		this.storeReply = storeReply;
	}

	public int getStoreScore() {
		return storeScore;
	}

	public void setStoreScore(int storeScore) {
		this.storeScore = storeScore;
	}

	public String getStoreUuid() {
		return storeUuid;
	}

	public void setStoreUuid(String storeUuid) {
		this.storeUuid = storeUuid;
	}

	@Override
	public String toString() {
		return "OrderMainAppraiseDTO{" +
				"appraiseTime='" + appraiseTime + '\'' +
				", content='" + content + '\'' +
				", customerName='" + customerName + '\'' +
				", customerScore=" + customerScore +
				", customerUuid='" + customerUuid + '\'' +
				", descScore=" + descScore +
				", orderMainUuid='" + orderMainUuid + '\'' +
				", serviceScore=" + serviceScore +
				", speedScore=" + speedScore +
				", storeReply='" + storeReply + '\'' +
				", storeScore=" + storeScore +
				", storeUuid='" + storeUuid + '\'' +
				'}';
	}
}

