package com.jhmis.modules.customer.entity;

import java.math.BigDecimal;

public class CustomerQuotes {
	private String  id;
	private String  msgId;
	private String  productModel;//型号
	private String 	domainType;//产业
	private String 	productGroup;//产品组
	private String 	brand;//品牌
	private BigDecimal 	productQuote;//报价
	private String 	productCode;//产品编码
	private Integer 	demandQuantity;//数量
	private Boolean  beWisdom;//是否智慧
	
	
	
	public CustomerQuotes() {
		super();
	}
	public CustomerQuotes(String id, String msgId, String productModel, String domainType, String productGroup,
			String brand, BigDecimal productQuote, String productCode, Integer demandQuantity, Boolean beWisdom) {
		super();
		this.id = id;
		this.msgId = msgId;
		this.productModel = productModel;
		this.domainType = domainType;
		this.productGroup = productGroup;
		this.brand = brand;
		this.productQuote = productQuote;
		this.productCode = productCode;
		this.demandQuantity = demandQuantity;
		this.beWisdom = beWisdom;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getProductModel() {
		return productModel;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public String getDomainType() {
		return domainType;
	}
	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}
	public String getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public BigDecimal getProductQuote() {
		return productQuote;
	}
	public void setProductQuote(BigDecimal productQuote) {
		this.productQuote = productQuote;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Integer getDemandQuantity() {
		return demandQuantity;
	}
	public void setDemandQuantity(Integer demandQuantity) {
		this.demandQuantity = demandQuantity;
	}
	public Boolean getBeWisdom() {
		return beWisdom;
	}
	public void setBeWisdom(Boolean beWisdom) {
		this.beWisdom = beWisdom;
	}
	
	
}
