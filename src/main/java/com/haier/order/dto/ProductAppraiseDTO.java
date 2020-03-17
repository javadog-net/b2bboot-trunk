package com.haier.order.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductAppraiseDTO extends BaseModel {

	
	public static final long serialVersionUID = -668113083910664186L;
	
	/**
	 * 全部评论
	 */
	public static final String ALLAPPRAISE="0";
	/**
	 * 好评
	 */
	public static final String GOODAPPRAISE="1";
	/**
	 * 中评
	 */
	public static final String MIDDLEAPPRAISE="2";
	
	/**
	 * 差评
	 */
	public static final String BADAPPRAISE="3";
	/**
	 * 晒单
	 */
	public static final String SHOWORDER = "4";
	
	
	/**
	 * 审核状态 1未审核
	 */
	public static final String STATE_REVIEW_NOT = "1";
	/**
	 * 审核状态 2审核通过
	 */
	public static final String STATE_REVIEW_SUCCESS = "2";
	
	/**
	 * 审核状态 3审核不通过
	 */
	public static final String STATE_REVIEW_FAIL = "3";
	
	/**
	 * 是否已晒单 0否
	 */
	public static final String SHOWPIC_NO = "0";
	
	/**
	 * 是否已晒单 1是
	 */
	public static final String SHOWPIC_YES = "1";	
	
	
	/**
	 * 商品评价对否需要审核运行参数的key
	 */
	public static final String PRODUCT_APP_AUDIT = "product.app.audit";
	
	/**
	 * 商品评价不需要审核
	 */
	public static final String PRODUCT_APP_AUDIT_NO = "0";
	
	/**
	 * 商品评价需要审核
	 */
	public static final String PRODUCT_APP_AUDIT_YES = "1";


	/**
	 * 评价内容
	*/
	private String appContent;
	/**
	 * 评价得分
	*/
	private int appScore;
	/**
	 * 评价标签
	*/
	private String appTag;
	/**
	 * 评价时间
	*/
	private String appTime;
	/**
	 * 会员姓名
	*/
	private String customerName;
	/**
	 * 会员uuid
	*/
	private String customerUuid;
	/**
	 * 订单详情uuid
	*/
	private String orderDetailUuid;
	
	/**
	 * 订单uuid
	*/
	private String orderUuid;
	/**
	 * sku
	*/
	private String productSkuNo;
	/**
	 * 商品uuid
	*/
	private String productUuid;
	/**
	 * 审核描述
	*/
	private String reviewDesc;
	/**
	 * 审核时间
	*/
	private String reviewTime;
	/**
	 * 审核人uuid
	*/
	private String reviewUserUuid;
	/**
	 * 审核状态 1未审核 2审核通过 3审核不通过
	*/
	private String state;
	/**
	 * 商品编号 
	 */
	private String productNo = "";
	/**
	 * 商品名称
	 */
	private String productName = "";
	private String mainPicUrl = "";
	/**
	 * 订单编号
	 */
	private String orderId = "";
	/**
	 * 标签集合
	 */
	private List<String> tags = new ArrayList<String>();
	/**
	 * 恢复人名称
	 */
	private String reviewUserName = "";
	/**
	 * 是否晒单：1-已晒单;0-未晒单
	 */
	private String hasShowPic="0";

	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}

	public String getAppContent() {
		return appContent;
	}

	public void setAppContent(String appContent) {
		this.appContent = appContent;
	}

	public int getAppScore() {
		return appScore;
	}

	public void setAppScore(int appScore) {
		this.appScore = appScore;
	}

	public String getAppTag() {
		return appTag;
	}

	public void setAppTag(String appTag) {
		this.appTag = appTag;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerUuid() {
		return customerUuid;
	}

	public void setCustomerUuid(String customerUuid) {
		this.customerUuid = customerUuid;
	}

	public String getOrderDetailUuid() {
		return orderDetailUuid;
	}

	public void setOrderDetailUuid(String orderDetailUuid) {
		this.orderDetailUuid = orderDetailUuid;
	}

	public String getOrderUuid() {
		return orderUuid;
	}

	public void setOrderUuid(String orderUuid) {
		this.orderUuid = orderUuid;
	}

	public String getProductSkuNo() {
		return productSkuNo;
	}

	public void setProductSkuNo(String productSkuNo) {
		this.productSkuNo = productSkuNo;
	}

	public String getProductUuid() {
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	public String getReviewDesc() {
		return reviewDesc;
	}

	public void setReviewDesc(String reviewDesc) {
		this.reviewDesc = reviewDesc;
	}

	public String getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getReviewUserUuid() {
		return reviewUserUuid;
	}

	public void setReviewUserUuid(String reviewUserUuid) {
		this.reviewUserUuid = reviewUserUuid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getReviewUserName() {
		return reviewUserName;
	}

	public void setReviewUserName(String reviewUserName) {
		this.reviewUserName = reviewUserName;
	}

	public String getHasShowPic() {
		return hasShowPic;
	}

	public void setHasShowPic(String hasShowPic) {
		this.hasShowPic = hasShowPic;
	}

	@Override
	public String toString() {
		return "ProductAppraiseDTO{" +
				"appContent='" + appContent + '\'' +
				", appScore=" + appScore +
				", appTag='" + appTag + '\'' +
				", appTime='" + appTime + '\'' +
				", customerName='" + customerName + '\'' +
				", customerUuid='" + customerUuid + '\'' +
				", orderDetailUuid='" + orderDetailUuid + '\'' +
				", orderUuid='" + orderUuid + '\'' +
				", productSkuNo='" + productSkuNo + '\'' +
				", productUuid='" + productUuid + '\'' +
				", reviewDesc='" + reviewDesc + '\'' +
				", reviewTime='" + reviewTime + '\'' +
				", reviewUserUuid='" + reviewUserUuid + '\'' +
				", state='" + state + '\'' +
				", productNo='" + productNo + '\'' +
				", productName='" + productName + '\'' +
				", orderId='" + orderId + '\'' +
				", tags=" + tags +
				", reviewUserName='" + reviewUserName + '\'' +
				", hasShowPic='" + hasShowPic + '\'' +
				'}';
	}
}

