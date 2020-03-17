/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.haier.order.dto.OrderMainAppraiseDTO;
import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 商品评价Entity
 * @author tity
 * @version 2018-07-22
 */
public class GoodsEvaluate extends DataEntity<GoodsEvaluate> {
	
	private static final long serialVersionUID = 1L;
	private String orderId;		// 订单ID
	private String orderSn;		// 订单编码
	private String goodsCode;		// 商品编码
	private String goodsName;		// 商品名称
	private String goodsPrice;		// 商品价格
	private String mainPicUrl;		// 商品图片
	private String storeId;		// 店铺ID
	private String storeName;		// 店铺名称
	private String purchaserAccountId;		// 采购商
	private String content;		// 评价内容
	private Date addtime;		// 首次评价时间
	private String isanonymous;		// 是否匿名
	private String explain;		// 解释内容
	private String contentAgain;		// 追加评价内容
	private Date addtimeAgain;		// 追加评价时间
	private String imageAgain;		// 追加评价图片
	private String explainAgain;		// 追加解释内容
	private String productQuality;		// 产品质量评分
	private String demandResponse;		// 需求响应评分
	private String deliveryCredit;		// 物流配送评分
	private String supplySpeed;		// 供货速度评分
	private String customerService;		// 售后服务评分
	private String orderGoodsId;        //订单商品唯一id
	private String loginName; //评价人名称
	private String logoUrl; //评价人logo
	private OrderMainAppraiseDTO orderMainAppraiseDTO;  //订单评价实体类
	
	public OrderMainAppraiseDTO getOrderMainAppraiseDTO() {
		return orderMainAppraiseDTO;
	}

	public void setOrderMainAppraiseDTO(OrderMainAppraiseDTO orderMainAppraiseDTO) {
		this.orderMainAppraiseDTO = orderMainAppraiseDTO;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getOrderGoodsId() {
		return orderGoodsId;
	}

	public void setOrderGoodsId(String orderGoodsId) {
		this.orderGoodsId = orderGoodsId;
	}

	public String getProductQuality() {
		return productQuality;
	}

	public void setProductQuality(String productQuality) {
		this.productQuality = productQuality;
	}

	public String getDemandResponse() {
		return demandResponse;
	}

	public void setDemandResponse(String demandResponse) {
		this.demandResponse = demandResponse;
	}

	public String getDeliveryCredit() {
		return deliveryCredit;
	}

	public void setDeliveryCredit(String deliveryCredit) {
		this.deliveryCredit = deliveryCredit;
	}

	public String getSupplySpeed() {
		return supplySpeed;
	}

	public void setSupplySpeed(String supplySpeed) {
		this.supplySpeed = supplySpeed;
	}

	public String getCustomerService() {
		return customerService;
	}

	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}

	public GoodsEvaluate() {
		super();
	}

	public GoodsEvaluate(String id){
		super(id);
	}

	@ExcelField(title="订单ID", align=2, sort=1)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@ExcelField(title="订单编码", align=2, sort=2)
	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@ExcelField(title="商品编码", align=2, sort=3)
	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	@ExcelField(title="商品名称", align=2, sort=4)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@ExcelField(title="商品价格", align=2, sort=5)
	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	@ExcelField(title="商品图片", align=2, sort=6)
	public String getMainPicUrl() {
		return mainPicUrl;
	}

	public void setMainPicUrl(String mainPicUrl) {
		this.mainPicUrl = mainPicUrl;
	}
	
	@ExcelField(title="店铺ID", align=2, sort=7)
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@ExcelField(title="店铺名称", align=2, sort=8)
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@ExcelField(title="采购商", align=2, sort=9)
	public String getPurchaserAccountId() {
		return purchaserAccountId;
	}

	public void setPurchaserAccountId(String purchaserAccountId) {
		this.purchaserAccountId = purchaserAccountId;
	}
	
	@ExcelField(title="评价内容", align=2, sort=11)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="首次评价时间", align=2, sort=12)
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@ExcelField(title="是否匿名", dictType="yes_no", align=2, sort=13)
	public String getIsanonymous() {
		return isanonymous;
	}

	public void setIsanonymous(String isanonymous) {
		this.isanonymous = isanonymous;
	}
	
	@ExcelField(title="解释内容", align=2, sort=14)
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	@ExcelField(title="追加评价内容", align=2, sort=15)
	public String getContentAgain() {
		return contentAgain;
	}

	public void setContentAgain(String contentAgain) {
		this.contentAgain = contentAgain;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="追加评价时间不能为空")
	@ExcelField(title="追加评价时间", align=2, sort=16)
	public Date getAddtimeAgain() {
		return addtimeAgain;
	}

	public void setAddtimeAgain(Date addtimeAgain) {
		this.addtimeAgain = addtimeAgain;
	}
	
	@ExcelField(title="追加评价图片", align=2, sort=17)
	public String getImageAgain() {
		return imageAgain;
	}

	public void setImageAgain(String imageAgain) {
		this.imageAgain = imageAgain;
	}
	
	@ExcelField(title="追加解释内容", align=2, sort=18)
	public String getExplainAgain() {
		return explainAgain;
	}

	public void setExplainAgain(String explainAgain) {
		this.explainAgain = explainAgain;
	}
	
}