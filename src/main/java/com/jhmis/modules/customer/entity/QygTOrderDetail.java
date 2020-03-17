/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 巨商会订单details非中转表Entity
 * @author hdx
 * @version 2020-02-12
 */
public class QygTOrderDetail extends DataEntity<QygTOrderDetail> {
	
	private static final long serialVersionUID = 1L;
	private Long orderInfoId;		// t_order_info主键
	private String orderNo;		// 订单号
	private String orderType;		// 订单类型
	private String productCode;		// 产品编码
	private String productName;		// 产品名称
	private String productImg;		// 产品图片
	private String productModel;		// 产品型号
	private String productGroup;		// 产品组
	private String productType;		// 产品类型(1:单品,	2:组合,
	private String productBrand;		// 品牌编码
	private String seriesCode;		// 产品系列
	private String unit;		// 产品单位
	private String farWeekly;		// far_weekly
	private Date farWeeklyDate;		// 远周次日期
	private String creditModel;		// 是否使用信用模式/款先(0:否,1:是)
	private String transferVersion;		// 调货版本号
	private String isTransferVersion;		// is_transfer_version
	private String priceType;		// 价格类型(PT:普通价格,TJ:特价,GC:工程,YJCY:样机出样,MFJK:免费机壳,MFYJ:免费样机,MFYJJS:免费样机结算,YPJ:样品机)
	private String priceVersion;		// 价格版本号
	private String priceSource;		// price_source
	private String supplyPrice;		// 供价
	private String profitPrice;		// 保利价
	private String actPrice;		// 执行价
	private String invoicePrice;		// 开票价
	private String rebateRate;		// 直扣率/扣点
	private String rebateMoney;		// 台返
	private String perLoss;		// 工程单台损失金额
	private String difference;		// 特价版本差额/特价单台差金额
	private String discount;		// 样机,样品机折扣
	private String rebatePolicy;		// 返利标识/返利政策
	private String isBb;		// 商空标志(0不是1是)
	private String recommendSalePrice;		// 建议零售价
	private String qty;		// 购买数量
	private String amount;		// 金额
	private String whCode;		// 仓库编码
	private String storeType;		// 1主2规划3辅仓
	private String salesFactory;		// 销售工厂
	private String shares;		// 股份
	private String budgetCode;		// 预算体
	private String machineCode;		// 样机机编
	private String isPop;		// is_pop
	private String bigdataBatchUpdate;		// 大数据更新用的字段
	private Date createdTime;		// 创建时间
	private String createdBy;		// 创建人
	private Date updateTime;		// 更新时间
	private String rowkey;		// rowkey
	private String sapReorderCount;		// 返单数量
	private String sapReorderDate;		// 返单时间
	private String sapKbetrZg01;		// sap136订单接口 价格( 条件金额或百分数 )
	private String sapKbetrZgk2;		// sap136订单接口 价格( 条件金额或百分数 )
	private String sapKwmeng;		// sap136订单接口 以销售单位表示的累计订单数量
	private String sapLgort;		// sap136订单接口 库存地点
	private String sapMatnr;		// sap136订单接口 型号
	private String sapPosnr;		// sap136订单接口 物料号
	private String sapSpart;		// sap136订单接口 产品组
	private String sapZieme;		// sap136订单接口 单位
	private String sapZsdgContract;		// sap136订单接口 工程审批单
	private String sapZsdgPriceeditio;		// sap136订单接口 价格版本号
	private String updateTimeSap;		// sap更新时间
	private String updateTimeBinlog;		// binlog更新时间
	private String updateTimeAll;		// 整条更新时间
	private String bstnk;		// 采购订单编号
	private String jshdOrderNo;		// 订单号
	
	public QygTOrderDetail() {
		super();
	}

	public QygTOrderDetail(String id){
		super(id);
	}

	@ExcelField(title="t_order_info主键", align=2, sort=0)
	public Long getOrderInfoId() {
		return orderInfoId;
	}

	public void setOrderInfoId(Long orderInfoId) {
		this.orderInfoId = orderInfoId;
	}
	
	@ExcelField(title="订单号", align=2, sort=1)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@ExcelField(title="订单类型", align=2, sort=2)
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@ExcelField(title="产品编码", align=2, sort=3)
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@ExcelField(title="产品名称", align=2, sort=4)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@ExcelField(title="产品图片", align=2, sort=5)
	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	
	@ExcelField(title="产品型号", align=2, sort=6)
	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	
	@ExcelField(title="产品组", align=2, sort=7)
	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	
	@ExcelField(title="产品类型(1:单品,	2:组合,", align=2, sort=8)
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@ExcelField(title="品牌编码", align=2, sort=9)
	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	
	@ExcelField(title="产品系列", align=2, sort=10)
	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	
	@ExcelField(title="产品单位", align=2, sort=11)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="far_weekly", align=2, sort=12)
	public String getFarWeekly() {
		return farWeekly;
	}

	public void setFarWeekly(String farWeekly) {
		this.farWeekly = farWeekly;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="远周次日期", align=2, sort=13)
	public Date getFarWeeklyDate() {
		return farWeeklyDate;
	}

	public void setFarWeeklyDate(Date farWeeklyDate) {
		this.farWeeklyDate = farWeeklyDate;
	}
	
	@ExcelField(title="是否使用信用模式/款先(0:否,1:是)", align=2, sort=14)
	public String getCreditModel() {
		return creditModel;
	}

	public void setCreditModel(String creditModel) {
		this.creditModel = creditModel;
	}
	
	@ExcelField(title="调货版本号", align=2, sort=15)
	public String getTransferVersion() {
		return transferVersion;
	}

	public void setTransferVersion(String transferVersion) {
		this.transferVersion = transferVersion;
	}
	
	@ExcelField(title="is_transfer_version", align=2, sort=16)
	public String getIsTransferVersion() {
		return isTransferVersion;
	}

	public void setIsTransferVersion(String isTransferVersion) {
		this.isTransferVersion = isTransferVersion;
	}
	
	@ExcelField(title="价格类型(PT:普通价格,TJ:特价,GC:工程,YJCY:样机出样,MFJK:免费机壳,MFYJ:免费样机,MFYJJS:免费样机结算,YPJ:样品机)", align=2, sort=17)
	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	@ExcelField(title="价格版本号", align=2, sort=18)
	public String getPriceVersion() {
		return priceVersion;
	}

	public void setPriceVersion(String priceVersion) {
		this.priceVersion = priceVersion;
	}
	
	@ExcelField(title="price_source", align=2, sort=19)
	public String getPriceSource() {
		return priceSource;
	}

	public void setPriceSource(String priceSource) {
		this.priceSource = priceSource;
	}
	
	@ExcelField(title="供价", align=2, sort=20)
	public String getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(String supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	
	@ExcelField(title="保利价", align=2, sort=21)
	public String getProfitPrice() {
		return profitPrice;
	}

	public void setProfitPrice(String profitPrice) {
		this.profitPrice = profitPrice;
	}
	
	@ExcelField(title="执行价", align=2, sort=22)
	public String getActPrice() {
		return actPrice;
	}

	public void setActPrice(String actPrice) {
		this.actPrice = actPrice;
	}
	
	@ExcelField(title="开票价", align=2, sort=23)
	public String getInvoicePrice() {
		return invoicePrice;
	}

	public void setInvoicePrice(String invoicePrice) {
		this.invoicePrice = invoicePrice;
	}
	
	@ExcelField(title="直扣率/扣点", align=2, sort=24)
	public String getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(String rebateRate) {
		this.rebateRate = rebateRate;
	}
	
	@ExcelField(title="台返", align=2, sort=25)
	public String getRebateMoney() {
		return rebateMoney;
	}

	public void setRebateMoney(String rebateMoney) {
		this.rebateMoney = rebateMoney;
	}
	
	@ExcelField(title="工程单台损失金额", align=2, sort=26)
	public String getPerLoss() {
		return perLoss;
	}

	public void setPerLoss(String perLoss) {
		this.perLoss = perLoss;
	}
	
	@ExcelField(title="特价版本差额/特价单台差金额", align=2, sort=27)
	public String getDifference() {
		return difference;
	}

	public void setDifference(String difference) {
		this.difference = difference;
	}
	
	@ExcelField(title="样机,样品机折扣", align=2, sort=28)
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	@ExcelField(title="返利标识/返利政策", align=2, sort=29)
	public String getRebatePolicy() {
		return rebatePolicy;
	}

	public void setRebatePolicy(String rebatePolicy) {
		this.rebatePolicy = rebatePolicy;
	}
	
	@ExcelField(title="商空标志(0不是1是)", align=2, sort=30)
	public String getIsBb() {
		return isBb;
	}

	public void setIsBb(String isBb) {
		this.isBb = isBb;
	}
	
	@ExcelField(title="建议零售价", align=2, sort=31)
	public String getRecommendSalePrice() {
		return recommendSalePrice;
	}

	public void setRecommendSalePrice(String recommendSalePrice) {
		this.recommendSalePrice = recommendSalePrice;
	}
	
	@ExcelField(title="购买数量", align=2, sort=32)
	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}
	
	@ExcelField(title="金额", align=2, sort=33)
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@ExcelField(title="仓库编码", align=2, sort=34)
	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	
	@ExcelField(title="1主2规划3辅仓", align=2, sort=35)
	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	
	@ExcelField(title="销售工厂", align=2, sort=36)
	public String getSalesFactory() {
		return salesFactory;
	}

	public void setSalesFactory(String salesFactory) {
		this.salesFactory = salesFactory;
	}
	
	@ExcelField(title="股份", align=2, sort=37)
	public String getShares() {
		return shares;
	}

	public void setShares(String shares) {
		this.shares = shares;
	}
	
	@ExcelField(title="预算体", align=2, sort=38)
	public String getBudgetCode() {
		return budgetCode;
	}

	public void setBudgetCode(String budgetCode) {
		this.budgetCode = budgetCode;
	}
	
	@ExcelField(title="样机机编", align=2, sort=39)
	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	
	@ExcelField(title="is_pop", align=2, sort=40)
	public String getIsPop() {
		return isPop;
	}

	public void setIsPop(String isPop) {
		this.isPop = isPop;
	}
	
	@ExcelField(title="大数据更新用的字段", align=2, sort=41)
	public String getBigdataBatchUpdate() {
		return bigdataBatchUpdate;
	}

	public void setBigdataBatchUpdate(String bigdataBatchUpdate) {
		this.bigdataBatchUpdate = bigdataBatchUpdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=42)
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@ExcelField(title="创建人", align=2, sort=43)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新时间", align=2, sort=44)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@ExcelField(title="rowkey", align=2, sort=47)
	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}
	
	@ExcelField(title="返单数量", align=2, sort=48)
	public String getSapReorderCount() {
		return sapReorderCount;
	}

	public void setSapReorderCount(String sapReorderCount) {
		this.sapReorderCount = sapReorderCount;
	}
	
	@ExcelField(title="返单时间", align=2, sort=49)
	public String getSapReorderDate() {
		return sapReorderDate;
	}

	public void setSapReorderDate(String sapReorderDate) {
		this.sapReorderDate = sapReorderDate;
	}
	
	@ExcelField(title="sap136订单接口 价格( 条件金额或百分数 )", align=2, sort=50)
	public String getSapKbetrZg01() {
		return sapKbetrZg01;
	}

	public void setSapKbetrZg01(String sapKbetrZg01) {
		this.sapKbetrZg01 = sapKbetrZg01;
	}
	
	@ExcelField(title="sap136订单接口 价格( 条件金额或百分数 )", align=2, sort=51)
	public String getSapKbetrZgk2() {
		return sapKbetrZgk2;
	}

	public void setSapKbetrZgk2(String sapKbetrZgk2) {
		this.sapKbetrZgk2 = sapKbetrZgk2;
	}
	
	@ExcelField(title="sap136订单接口 以销售单位表示的累计订单数量", align=2, sort=52)
	public String getSapKwmeng() {
		return sapKwmeng;
	}

	public void setSapKwmeng(String sapKwmeng) {
		this.sapKwmeng = sapKwmeng;
	}
	
	@ExcelField(title="sap136订单接口 库存地点", align=2, sort=53)
	public String getSapLgort() {
		return sapLgort;
	}

	public void setSapLgort(String sapLgort) {
		this.sapLgort = sapLgort;
	}
	
	@ExcelField(title="sap136订单接口 型号", align=2, sort=54)
	public String getSapMatnr() {
		return sapMatnr;
	}

	public void setSapMatnr(String sapMatnr) {
		this.sapMatnr = sapMatnr;
	}
	
	@ExcelField(title="sap136订单接口 物料号", align=2, sort=55)
	public String getSapPosnr() {
		return sapPosnr;
	}

	public void setSapPosnr(String sapPosnr) {
		this.sapPosnr = sapPosnr;
	}
	
	@ExcelField(title="sap136订单接口 产品组", align=2, sort=56)
	public String getSapSpart() {
		return sapSpart;
	}

	public void setSapSpart(String sapSpart) {
		this.sapSpart = sapSpart;
	}
	
	@ExcelField(title="sap136订单接口 单位", align=2, sort=57)
	public String getSapZieme() {
		return sapZieme;
	}

	public void setSapZieme(String sapZieme) {
		this.sapZieme = sapZieme;
	}
	
	@ExcelField(title="sap136订单接口 工程审批单", align=2, sort=58)
	public String getSapZsdgContract() {
		return sapZsdgContract;
	}

	public void setSapZsdgContract(String sapZsdgContract) {
		this.sapZsdgContract = sapZsdgContract;
	}
	
	@ExcelField(title="sap136订单接口 价格版本号", align=2, sort=59)
	public String getSapZsdgPriceeditio() {
		return sapZsdgPriceeditio;
	}

	public void setSapZsdgPriceeditio(String sapZsdgPriceeditio) {
		this.sapZsdgPriceeditio = sapZsdgPriceeditio;
	}
	
	@ExcelField(title="sap更新时间", align=2, sort=60)
	public String getUpdateTimeSap() {
		return updateTimeSap;
	}

	public void setUpdateTimeSap(String updateTimeSap) {
		this.updateTimeSap = updateTimeSap;
	}
	
	@ExcelField(title="binlog更新时间", align=2, sort=61)
	public String getUpdateTimeBinlog() {
		return updateTimeBinlog;
	}

	public void setUpdateTimeBinlog(String updateTimeBinlog) {
		this.updateTimeBinlog = updateTimeBinlog;
	}
	
	@ExcelField(title="整条更新时间", align=2, sort=62)
	public String getUpdateTimeAll() {
		return updateTimeAll;
	}

	public void setUpdateTimeAll(String updateTimeAll) {
		this.updateTimeAll = updateTimeAll;
	}
	
	@ExcelField(title="采购订单编号", align=2, sort=63)
	public String getBstnk() {
		return bstnk;
	}

	public void setBstnk(String bstnk) {
		this.bstnk = bstnk;
	}
	
	@ExcelField(title="订单号", align=2, sort=64)
	public String getJshdOrderNo() {
		return jshdOrderNo;
	}

	public void setJshdOrderNo(String jshdOrderNo) {
		this.jshdOrderNo = jshdOrderNo;
	}
	
}