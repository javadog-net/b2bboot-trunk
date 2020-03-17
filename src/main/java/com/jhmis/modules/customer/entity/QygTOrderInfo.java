/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 巨商会订单info非中转表Entity
 * @author hdx
 * @version 2020-02-12
 */
public class QygTOrderInfo extends DataEntity<QygTOrderInfo> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单号
	private String orderType;		// 订单类型一般是三位字母
	private String orderTypeName;		// 订单类型名称
	private String orderTypeNo;		// 订单类型和订单号拼接的
	private String omsOrderType;		// oms订单类型B普通单
	private String orderFlowNo;		// 订单流水号
	private Long groupingFlowId;		// 组合表t_grouping_flow主键
	private String groupingNo;		// 组合号
	private String activityId;		// 活动id
	private String activityName;		// 活动名称
	private String stockNo;		// 占用库存返回的单号
	private String stockType;		// 库存类型(RRS:自有库存(日日顺)，TC:TC库存(B2B)，TM:天猫共享库存，ZT:在途，PTD:PTD，PC:排产，ZCN周承诺，JD:基地（生活家电）
	private String planInDate;		// 预计到货时间
	private String yuncangCode;		// 异地云仓编码
	private String tctpConfirm;		// 统仓统配确认(0初始状态1已确认2已取消)
	private String deliveryType;		// delivery_type
	private String deliveryAddr;		// 配送地址（工程异地/商空直发/异地云仓）
	private String deliveryCode;		// 配送地址县级编码
	private String deliveryYd;		// 异地/跨区域配送标识（0:否,
	private String deliveryName;		// 工程异地地址信息，传值：姓名_手机号_身份证号
	private String saletoCode;		// 售达方编码
	private String saletoName;		// 售达方名称
	private String paytoCode;		// 付款方编码
	private String paytoName;		// 付款方名称
	private String paytoType;		// 付款方类型(例如98,99等)
	private String syncScfStatus;		// sync_scf_status
	private String sendtoCode;		// 送达方编码
	private String sendtoName;		// 送达方名称
	private String sendtoAddress;		// 送达方地址
	private String manageCustomerCode;		// 管理客户编码
	private String manageCustomerName;		// 管理客户名称
	private String regionCode;		// 区域编码
	private String mainChannelCode;		// 大渠道
	private String subChannelCode;		// 小渠道
	private String orderSource;		// order_source
	private String tradeCode;		// 工贸编码
	private String saleOrgCode;		// 销售组织编码
	private String orderAmount;		// 订单金额
	private String orderDate;		// 创建订单时间
	private String omsOrderNo;		// 传统渠道oms订单号，这个字段是最终显示给用户的)
	private String gvsSoOrderNo;		// so最终的订单号(存so1或so5单号,这个字段是最终显示给用户的)
	private String so1OrderNo;		// so1_order_no
	private String dn1OrderNo;		// dn1单号(gvs返回的)
	private String so5OrderNo;		// so5订单号(gvs返回的)
	private String dn5OrderNo;		// dn5单号(gvs返回的)
	private String syncDnStatus;		// sync_dn_status
	private String gvsOrderType;		// GVS订单类型(需要把类型转换成gvs的)
	private String orderChannel;		// order_channel
	private String procurementMethod;		// B2B客户集采地采标识，jicai/dicai
	private String conCode;		// B2B客户采购订单编号
	private String billNo;		// B2B开发票方
	private String billName;		// 开票方名称
	private String comment;		// 备注
	private String orderStatus;		// 订单状态(0:待确认支付,1:扣款中,2:支付失败,3:用户取消,4:超时未支付,5:审批拒绝,6:sap物流拒单
	private String orderGvsStatus;		// order_gvs_status
	private String errorMsg;		// 错误信息
	private String payTime;		// 支付时间
	private Long orderNoSeq;		// 订单号序列每次加一
	private Long placeOrderSeq;		// 下单顺序(数越小级别越高)
	private String bigdataBatchUpdate;		// 大数据更新用的
	private String createdTime;		// 创建时间
	private String createdBy;		// 创建人
	private String updateTime;		// update_time
	private String rowkey;		// 主键，业务无关
	private String bstnk;		// bstnk
	private String sapSo;		// sap_so
	private String sapDn;		// sap_dn
	private String sapDndate;		// sap_dndate
	private String sapSo1;		// sap_so1
	private String sapSo5;		// sap_so5
	private String sapDn1;		// 一次物流dn
	private String sapDn5;		// 二次物流dn
	private String sapDn1date;		// 一次物流时间
	private String sapDn5date;		// 二次物流时间
	private String sapTaxInvoiceNo;		// 金税发票号
	private String sapTaxInvoiceTime;		// 系统发票时间
	private String sapSysInvoiceNo;		// 系统发票号
	private String sapSysInvoiceTime;		// 系统发票时间
	private String sapCenterRecieveDate;		// 中心收货时间
	private String sapPtdSendDate;		// ptd计划发货时间
	private String sapJudgeDate;		// 评审时间
	private String sapJudgeStatus;		// 评审状态
	private String sapJudgeReason;		// 评审拒绝原因
	private String sapReorderDate;		// 返单时间
	private String sapSend5Reason;		// 二次物流拒绝发货原因
	private String sapTransoutDate;		// 调货出库时间
	private String sapTransinDate;		// 调货入库时间
	private String etlSource;		// 数据来源（1巨商汇，2sap，3巨商汇老系统
	private String productCodeAll;		// product_code_all
	private String productGroupAll;		// product_group_all
	private String productBrandAll;		// product_brand_all
	private String productModelAll;		// product_model_all
	private String priceTypeAll;		// price_type_all
	private String productNameAll;		// product_name_all
	private String farWeeklyAll;		// far_weekly_all
	private String creditModelAll;		// credit_model_all
	private String isTransferVersionAll;		// is_transfer_version_all
	private String transferVersionAll;		// transfer_version_all
	private String productTypeAll;		// product_type_all
	private String sapSended1;		// 标记是否一次物流已发货（初始0，是1）
	private String sapSended5;		// 标记是否二次物流已发货（初始0，是1，拒绝2）
	private String sapJudged;		// 标记是否已评审（初始0，是1，拒绝2）
	private String sapReorderd;		// 标记是否已返单（初始0，是1）
	private String sapInvoiced;		// 标记是否已开发票（初始0，是1）
	private String sapPtded;		// 是否制定发货计划（初始0，是1）
	private String sapCenterRecieved;		// 是否中心收货（初始0，是1）
	private String sapErdat;		// 136订单接口 记录的创建日期 
	private String sapErzet;		// 136订单接口 记录的创建时间
	private String sapVbeln;		// 136订单接口 销售凭证 
	private String sapPosnr;		// 136订单接口 行项
	private String sapVkorg;		// 136订单接口 销售组织 
	private String sapVkorgType;		// 136订单接口 销售组织开头第一位
	private String sapZsdgVaTh;		// 136订单接口 提货方式
	private String sapAuart;		// 136订单接口 销售凭证类型 
	private String sapVbelnVl;		// 136订单接口 交货
	private String jshdzIblKorderno;		// bstnk
	private String jshdzIblTdlnr;		// 车队编码
	private String jshdzIblSigni;		// 车牌号
	private String jshdzIblExti1;		// 司机联系电话
	private Integer jshdzIblHbdh;		// 车次号
	private String updateTimeSap;		// sap更新时间
	private String updateTimeBinlog;		// binlog更新时间
	private String updateTimeAll;		// 整条更新时间
	
	public QygTOrderInfo() {
		super();
	}

	public QygTOrderInfo(String id){
		super(id);
	}

	@ExcelField(title="订单号", align=2, sort=0)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@ExcelField(title="订单类型一般是三位字母", align=2, sort=1)
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	@ExcelField(title="订单类型名称", align=2, sort=2)
	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}
	
	@ExcelField(title="订单类型和订单号拼接的", align=2, sort=3)
	public String getOrderTypeNo() {
		return orderTypeNo;
	}

	public void setOrderTypeNo(String orderTypeNo) {
		this.orderTypeNo = orderTypeNo;
	}
	
	@ExcelField(title="oms订单类型B普通单", align=2, sort=4)
	public String getOmsOrderType() {
		return omsOrderType;
	}

	public void setOmsOrderType(String omsOrderType) {
		this.omsOrderType = omsOrderType;
	}
	
	@ExcelField(title="订单流水号", align=2, sort=5)
	public String getOrderFlowNo() {
		return orderFlowNo;
	}

	public void setOrderFlowNo(String orderFlowNo) {
		this.orderFlowNo = orderFlowNo;
	}
	
	@ExcelField(title="组合表t_grouping_flow主键", align=2, sort=6)
	public Long getGroupingFlowId() {
		return groupingFlowId;
	}

	public void setGroupingFlowId(Long groupingFlowId) {
		this.groupingFlowId = groupingFlowId;
	}
	
	@ExcelField(title="组合号", align=2, sort=7)
	public String getGroupingNo() {
		return groupingNo;
	}

	public void setGroupingNo(String groupingNo) {
		this.groupingNo = groupingNo;
	}
	
	@ExcelField(title="活动id", align=2, sort=8)
	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	
	@ExcelField(title="活动名称", align=2, sort=9)
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	@ExcelField(title="占用库存返回的单号", align=2, sort=10)
	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	
	@ExcelField(title="库存类型(RRS:自有库存(日日顺)，TC:TC库存(B2B)，TM:天猫共享库存，ZT:在途，PTD:PTD，PC:排产，ZCN周承诺，JD:基地（生活家电）", align=2, sort=11)
	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}
	
	@ExcelField(title="预计到货时间", align=2, sort=12)
	public String getPlanInDate() {
		return planInDate;
	}

	public void setPlanInDate(String planInDate) {
		this.planInDate = planInDate;
	}
	
	@ExcelField(title="异地云仓编码", align=2, sort=13)
	public String getYuncangCode() {
		return yuncangCode;
	}

	public void setYuncangCode(String yuncangCode) {
		this.yuncangCode = yuncangCode;
	}
	
	@ExcelField(title="统仓统配确认(0初始状态1已确认2已取消)", align=2, sort=14)
	public String getTctpConfirm() {
		return tctpConfirm;
	}

	public void setTctpConfirm(String tctpConfirm) {
		this.tctpConfirm = tctpConfirm;
	}
	
	@ExcelField(title="delivery_type", align=2, sort=15)
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	@ExcelField(title="配送地址（工程异地/商空直发/异地云仓）", align=2, sort=16)
	public String getDeliveryAddr() {
		return deliveryAddr;
	}

	public void setDeliveryAddr(String deliveryAddr) {
		this.deliveryAddr = deliveryAddr;
	}
	
	@ExcelField(title="配送地址县级编码", align=2, sort=17)
	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}
	
	@ExcelField(title="异地/跨区域配送标识（0:否,", align=2, sort=18)
	public String getDeliveryYd() {
		return deliveryYd;
	}

	public void setDeliveryYd(String deliveryYd) {
		this.deliveryYd = deliveryYd;
	}
	
	@ExcelField(title="工程异地地址信息，传值：姓名_手机号_身份证号", align=2, sort=19)
	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	
	@ExcelField(title="售达方编码", align=2, sort=20)
	public String getSaletoCode() {
		return saletoCode;
	}

	public void setSaletoCode(String saletoCode) {
		this.saletoCode = saletoCode;
	}
	
	@ExcelField(title="售达方名称", align=2, sort=21)
	public String getSaletoName() {
		return saletoName;
	}

	public void setSaletoName(String saletoName) {
		this.saletoName = saletoName;
	}
	
	@ExcelField(title="付款方编码", align=2, sort=22)
	public String getPaytoCode() {
		return paytoCode;
	}

	public void setPaytoCode(String paytoCode) {
		this.paytoCode = paytoCode;
	}
	
	@ExcelField(title="付款方名称", align=2, sort=23)
	public String getPaytoName() {
		return paytoName;
	}

	public void setPaytoName(String paytoName) {
		this.paytoName = paytoName;
	}
	
	@ExcelField(title="付款方类型(例如98,99等)", align=2, sort=24)
	public String getPaytoType() {
		return paytoType;
	}

	public void setPaytoType(String paytoType) {
		this.paytoType = paytoType;
	}
	
	@ExcelField(title="sync_scf_status", align=2, sort=25)
	public String getSyncScfStatus() {
		return syncScfStatus;
	}

	public void setSyncScfStatus(String syncScfStatus) {
		this.syncScfStatus = syncScfStatus;
	}
	
	@ExcelField(title="送达方编码", align=2, sort=26)
	public String getSendtoCode() {
		return sendtoCode;
	}

	public void setSendtoCode(String sendtoCode) {
		this.sendtoCode = sendtoCode;
	}
	
	@ExcelField(title="送达方名称", align=2, sort=27)
	public String getSendtoName() {
		return sendtoName;
	}

	public void setSendtoName(String sendtoName) {
		this.sendtoName = sendtoName;
	}
	
	@ExcelField(title="送达方地址", align=2, sort=28)
	public String getSendtoAddress() {
		return sendtoAddress;
	}

	public void setSendtoAddress(String sendtoAddress) {
		this.sendtoAddress = sendtoAddress;
	}
	
	@ExcelField(title="管理客户编码", align=2, sort=29)
	public String getManageCustomerCode() {
		return manageCustomerCode;
	}

	public void setManageCustomerCode(String manageCustomerCode) {
		this.manageCustomerCode = manageCustomerCode;
	}
	
	@ExcelField(title="管理客户名称", align=2, sort=30)
	public String getManageCustomerName() {
		return manageCustomerName;
	}

	public void setManageCustomerName(String manageCustomerName) {
		this.manageCustomerName = manageCustomerName;
	}
	
	@ExcelField(title="区域编码", align=2, sort=31)
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	@ExcelField(title="大渠道", align=2, sort=32)
	public String getMainChannelCode() {
		return mainChannelCode;
	}

	public void setMainChannelCode(String mainChannelCode) {
		this.mainChannelCode = mainChannelCode;
	}
	
	@ExcelField(title="小渠道", align=2, sort=33)
	public String getSubChannelCode() {
		return subChannelCode;
	}

	public void setSubChannelCode(String subChannelCode) {
		this.subChannelCode = subChannelCode;
	}
	
	@ExcelField(title="order_source", align=2, sort=34)
	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	
	@ExcelField(title="工贸编码", align=2, sort=35)
	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	
	@ExcelField(title="销售组织编码", align=2, sort=36)
	public String getSaleOrgCode() {
		return saleOrgCode;
	}

	public void setSaleOrgCode(String saleOrgCode) {
		this.saleOrgCode = saleOrgCode;
	}
	
	@ExcelField(title="订单金额", align=2, sort=37)
	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	@ExcelField(title="创建订单时间", align=2, sort=38)
	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	@ExcelField(title="传统渠道oms订单号，这个字段是最终显示给用户的)", align=2, sort=39)
	public String getOmsOrderNo() {
		return omsOrderNo;
	}

	public void setOmsOrderNo(String omsOrderNo) {
		this.omsOrderNo = omsOrderNo;
	}
	
	@ExcelField(title="so最终的订单号(存so1或so5单号,这个字段是最终显示给用户的)", align=2, sort=40)
	public String getGvsSoOrderNo() {
		return gvsSoOrderNo;
	}

	public void setGvsSoOrderNo(String gvsSoOrderNo) {
		this.gvsSoOrderNo = gvsSoOrderNo;
	}
	
	@ExcelField(title="so1_order_no", align=2, sort=41)
	public String getSo1OrderNo() {
		return so1OrderNo;
	}

	public void setSo1OrderNo(String so1OrderNo) {
		this.so1OrderNo = so1OrderNo;
	}
	
	@ExcelField(title="dn1单号(gvs返回的)", align=2, sort=42)
	public String getDn1OrderNo() {
		return dn1OrderNo;
	}

	public void setDn1OrderNo(String dn1OrderNo) {
		this.dn1OrderNo = dn1OrderNo;
	}
	
	@ExcelField(title="so5订单号(gvs返回的)", align=2, sort=43)
	public String getSo5OrderNo() {
		return so5OrderNo;
	}

	public void setSo5OrderNo(String so5OrderNo) {
		this.so5OrderNo = so5OrderNo;
	}
	
	@ExcelField(title="dn5单号(gvs返回的)", align=2, sort=44)
	public String getDn5OrderNo() {
		return dn5OrderNo;
	}

	public void setDn5OrderNo(String dn5OrderNo) {
		this.dn5OrderNo = dn5OrderNo;
	}
	
	@ExcelField(title="sync_dn_status", align=2, sort=45)
	public String getSyncDnStatus() {
		return syncDnStatus;
	}

	public void setSyncDnStatus(String syncDnStatus) {
		this.syncDnStatus = syncDnStatus;
	}
	
	@ExcelField(title="GVS订单类型(需要把类型转换成gvs的)", align=2, sort=46)
	public String getGvsOrderType() {
		return gvsOrderType;
	}

	public void setGvsOrderType(String gvsOrderType) {
		this.gvsOrderType = gvsOrderType;
	}
	
	@ExcelField(title="order_channel", align=2, sort=47)
	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	
	@ExcelField(title="B2B客户集采地采标识，jicai/dicai", align=2, sort=48)
	public String getProcurementMethod() {
		return procurementMethod;
	}

	public void setProcurementMethod(String procurementMethod) {
		this.procurementMethod = procurementMethod;
	}
	
	@ExcelField(title="B2B客户采购订单编号", align=2, sort=49)
	public String getConCode() {
		return conCode;
	}

	public void setConCode(String conCode) {
		this.conCode = conCode;
	}
	
	@ExcelField(title="B2B开发票方", align=2, sort=50)
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	@ExcelField(title="开票方名称", align=2, sort=51)
	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}
	
	@ExcelField(title="备注", align=2, sort=52)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@ExcelField(title="订单状态(0:待确认支付,1:扣款中,2:支付失败,3:用户取消,4:超时未支付,5:审批拒绝,6:sap物流拒单", align=2, sort=53)
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@ExcelField(title="order_gvs_status", align=2, sort=54)
	public String getOrderGvsStatus() {
		return orderGvsStatus;
	}

	public void setOrderGvsStatus(String orderGvsStatus) {
		this.orderGvsStatus = orderGvsStatus;
	}
	
	@ExcelField(title="错误信息", align=2, sort=55)
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	@ExcelField(title="支付时间", align=2, sort=56)
	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	@ExcelField(title="订单号序列每次加一", align=2, sort=57)
	public Long getOrderNoSeq() {
		return orderNoSeq;
	}

	public void setOrderNoSeq(Long orderNoSeq) {
		this.orderNoSeq = orderNoSeq;
	}
	
	@ExcelField(title="下单顺序(数越小级别越高)", align=2, sort=58)
	public Long getPlaceOrderSeq() {
		return placeOrderSeq;
	}

	public void setPlaceOrderSeq(Long placeOrderSeq) {
		this.placeOrderSeq = placeOrderSeq;
	}
	
	@ExcelField(title="大数据更新用的", align=2, sort=59)
	public String getBigdataBatchUpdate() {
		return bigdataBatchUpdate;
	}

	public void setBigdataBatchUpdate(String bigdataBatchUpdate) {
		this.bigdataBatchUpdate = bigdataBatchUpdate;
	}
	
	@ExcelField(title="创建时间", align=2, sort=61)
	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	@ExcelField(title="创建人", align=2, sort=62)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@ExcelField(title="update_time", align=2, sort=63)
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	@ExcelField(title="主键，业务无关", align=2, sort=65)
	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}
	
	@ExcelField(title="bstnk", align=2, sort=66)
	public String getBstnk() {
		return bstnk;
	}

	public void setBstnk(String bstnk) {
		this.bstnk = bstnk;
	}
	
	@ExcelField(title="sap_so", align=2, sort=67)
	public String getSapSo() {
		return sapSo;
	}

	public void setSapSo(String sapSo) {
		this.sapSo = sapSo;
	}
	
	@ExcelField(title="sap_dn", align=2, sort=68)
	public String getSapDn() {
		return sapDn;
	}

	public void setSapDn(String sapDn) {
		this.sapDn = sapDn;
	}
	
	@ExcelField(title="sap_dndate", align=2, sort=69)
	public String getSapDndate() {
		return sapDndate;
	}

	public void setSapDndate(String sapDndate) {
		this.sapDndate = sapDndate;
	}
	
	@ExcelField(title="sap_so1", align=2, sort=70)
	public String getSapSo1() {
		return sapSo1;
	}

	public void setSapSo1(String sapSo1) {
		this.sapSo1 = sapSo1;
	}
	
	@ExcelField(title="sap_so5", align=2, sort=71)
	public String getSapSo5() {
		return sapSo5;
	}

	public void setSapSo5(String sapSo5) {
		this.sapSo5 = sapSo5;
	}
	
	@ExcelField(title="一次物流dn", align=2, sort=72)
	public String getSapDn1() {
		return sapDn1;
	}

	public void setSapDn1(String sapDn1) {
		this.sapDn1 = sapDn1;
	}
	
	@ExcelField(title="二次物流dn", align=2, sort=73)
	public String getSapDn5() {
		return sapDn5;
	}

	public void setSapDn5(String sapDn5) {
		this.sapDn5 = sapDn5;
	}
	
	@ExcelField(title="一次物流时间", align=2, sort=74)
	public String getSapDn1date() {
		return sapDn1date;
	}

	public void setSapDn1date(String sapDn1date) {
		this.sapDn1date = sapDn1date;
	}
	
	@ExcelField(title="二次物流时间", align=2, sort=75)
	public String getSapDn5date() {
		return sapDn5date;
	}

	public void setSapDn5date(String sapDn5date) {
		this.sapDn5date = sapDn5date;
	}
	
	@ExcelField(title="金税发票号", align=2, sort=76)
	public String getSapTaxInvoiceNo() {
		return sapTaxInvoiceNo;
	}

	public void setSapTaxInvoiceNo(String sapTaxInvoiceNo) {
		this.sapTaxInvoiceNo = sapTaxInvoiceNo;
	}
	
	@ExcelField(title="系统发票时间", align=2, sort=77)
	public String getSapTaxInvoiceTime() {
		return sapTaxInvoiceTime;
	}

	public void setSapTaxInvoiceTime(String sapTaxInvoiceTime) {
		this.sapTaxInvoiceTime = sapTaxInvoiceTime;
	}
	
	@ExcelField(title="系统发票号", align=2, sort=78)
	public String getSapSysInvoiceNo() {
		return sapSysInvoiceNo;
	}

	public void setSapSysInvoiceNo(String sapSysInvoiceNo) {
		this.sapSysInvoiceNo = sapSysInvoiceNo;
	}
	
	@ExcelField(title="系统发票时间", align=2, sort=79)
	public String getSapSysInvoiceTime() {
		return sapSysInvoiceTime;
	}

	public void setSapSysInvoiceTime(String sapSysInvoiceTime) {
		this.sapSysInvoiceTime = sapSysInvoiceTime;
	}
	
	@ExcelField(title="中心收货时间", align=2, sort=80)
	public String getSapCenterRecieveDate() {
		return sapCenterRecieveDate;
	}

	public void setSapCenterRecieveDate(String sapCenterRecieveDate) {
		this.sapCenterRecieveDate = sapCenterRecieveDate;
	}
	
	@ExcelField(title="ptd计划发货时间", align=2, sort=81)
	public String getSapPtdSendDate() {
		return sapPtdSendDate;
	}

	public void setSapPtdSendDate(String sapPtdSendDate) {
		this.sapPtdSendDate = sapPtdSendDate;
	}
	
	@ExcelField(title="评审时间", align=2, sort=82)
	public String getSapJudgeDate() {
		return sapJudgeDate;
	}

	public void setSapJudgeDate(String sapJudgeDate) {
		this.sapJudgeDate = sapJudgeDate;
	}
	
	@ExcelField(title="评审状态", align=2, sort=83)
	public String getSapJudgeStatus() {
		return sapJudgeStatus;
	}

	public void setSapJudgeStatus(String sapJudgeStatus) {
		this.sapJudgeStatus = sapJudgeStatus;
	}
	
	@ExcelField(title="评审拒绝原因", align=2, sort=84)
	public String getSapJudgeReason() {
		return sapJudgeReason;
	}

	public void setSapJudgeReason(String sapJudgeReason) {
		this.sapJudgeReason = sapJudgeReason;
	}
	
	@ExcelField(title="返单时间", align=2, sort=85)
	public String getSapReorderDate() {
		return sapReorderDate;
	}

	public void setSapReorderDate(String sapReorderDate) {
		this.sapReorderDate = sapReorderDate;
	}
	
	@ExcelField(title="二次物流拒绝发货原因", align=2, sort=86)
	public String getSapSend5Reason() {
		return sapSend5Reason;
	}

	public void setSapSend5Reason(String sapSend5Reason) {
		this.sapSend5Reason = sapSend5Reason;
	}
	
	@ExcelField(title="调货出库时间", align=2, sort=87)
	public String getSapTransoutDate() {
		return sapTransoutDate;
	}

	public void setSapTransoutDate(String sapTransoutDate) {
		this.sapTransoutDate = sapTransoutDate;
	}
	
	@ExcelField(title="调货入库时间", align=2, sort=88)
	public String getSapTransinDate() {
		return sapTransinDate;
	}

	public void setSapTransinDate(String sapTransinDate) {
		this.sapTransinDate = sapTransinDate;
	}
	
	@ExcelField(title="数据来源（1巨商汇，2sap，3巨商汇老系统", align=2, sort=89)
	public String getEtlSource() {
		return etlSource;
	}

	public void setEtlSource(String etlSource) {
		this.etlSource = etlSource;
	}
	
	@ExcelField(title="product_code_all", align=2, sort=90)
	public String getProductCodeAll() {
		return productCodeAll;
	}

	public void setProductCodeAll(String productCodeAll) {
		this.productCodeAll = productCodeAll;
	}
	
	@ExcelField(title="product_group_all", align=2, sort=91)
	public String getProductGroupAll() {
		return productGroupAll;
	}

	public void setProductGroupAll(String productGroupAll) {
		this.productGroupAll = productGroupAll;
	}
	
	@ExcelField(title="product_brand_all", align=2, sort=92)
	public String getProductBrandAll() {
		return productBrandAll;
	}

	public void setProductBrandAll(String productBrandAll) {
		this.productBrandAll = productBrandAll;
	}
	
	@ExcelField(title="product_model_all", align=2, sort=93)
	public String getProductModelAll() {
		return productModelAll;
	}

	public void setProductModelAll(String productModelAll) {
		this.productModelAll = productModelAll;
	}
	
	@ExcelField(title="price_type_all", align=2, sort=94)
	public String getPriceTypeAll() {
		return priceTypeAll;
	}

	public void setPriceTypeAll(String priceTypeAll) {
		this.priceTypeAll = priceTypeAll;
	}
	
	@ExcelField(title="product_name_all", align=2, sort=95)
	public String getProductNameAll() {
		return productNameAll;
	}

	public void setProductNameAll(String productNameAll) {
		this.productNameAll = productNameAll;
	}
	
	@ExcelField(title="far_weekly_all", align=2, sort=96)
	public String getFarWeeklyAll() {
		return farWeeklyAll;
	}

	public void setFarWeeklyAll(String farWeeklyAll) {
		this.farWeeklyAll = farWeeklyAll;
	}
	
	@ExcelField(title="credit_model_all", align=2, sort=97)
	public String getCreditModelAll() {
		return creditModelAll;
	}

	public void setCreditModelAll(String creditModelAll) {
		this.creditModelAll = creditModelAll;
	}
	
	@ExcelField(title="is_transfer_version_all", align=2, sort=98)
	public String getIsTransferVersionAll() {
		return isTransferVersionAll;
	}

	public void setIsTransferVersionAll(String isTransferVersionAll) {
		this.isTransferVersionAll = isTransferVersionAll;
	}
	
	@ExcelField(title="transfer_version_all", align=2, sort=99)
	public String getTransferVersionAll() {
		return transferVersionAll;
	}

	public void setTransferVersionAll(String transferVersionAll) {
		this.transferVersionAll = transferVersionAll;
	}
	
	@ExcelField(title="product_type_all", align=2, sort=100)
	public String getProductTypeAll() {
		return productTypeAll;
	}

	public void setProductTypeAll(String productTypeAll) {
		this.productTypeAll = productTypeAll;
	}
	
	@ExcelField(title="标记是否一次物流已发货（初始0，是1）", align=2, sort=101)
	public String getSapSended1() {
		return sapSended1;
	}

	public void setSapSended1(String sapSended1) {
		this.sapSended1 = sapSended1;
	}
	
	@ExcelField(title="标记是否二次物流已发货（初始0，是1，拒绝2）", align=2, sort=102)
	public String getSapSended5() {
		return sapSended5;
	}

	public void setSapSended5(String sapSended5) {
		this.sapSended5 = sapSended5;
	}
	
	@ExcelField(title="标记是否已评审（初始0，是1，拒绝2）", align=2, sort=103)
	public String getSapJudged() {
		return sapJudged;
	}

	public void setSapJudged(String sapJudged) {
		this.sapJudged = sapJudged;
	}
	
	@ExcelField(title="标记是否已返单（初始0，是1）", align=2, sort=104)
	public String getSapReorderd() {
		return sapReorderd;
	}

	public void setSapReorderd(String sapReorderd) {
		this.sapReorderd = sapReorderd;
	}
	
	@ExcelField(title="标记是否已开发票（初始0，是1）", align=2, sort=105)
	public String getSapInvoiced() {
		return sapInvoiced;
	}

	public void setSapInvoiced(String sapInvoiced) {
		this.sapInvoiced = sapInvoiced;
	}
	
	@ExcelField(title="是否制定发货计划（初始0，是1）", align=2, sort=106)
	public String getSapPtded() {
		return sapPtded;
	}

	public void setSapPtded(String sapPtded) {
		this.sapPtded = sapPtded;
	}
	
	@ExcelField(title="是否中心收货（初始0，是1）", align=2, sort=107)
	public String getSapCenterRecieved() {
		return sapCenterRecieved;
	}

	public void setSapCenterRecieved(String sapCenterRecieved) {
		this.sapCenterRecieved = sapCenterRecieved;
	}
	
	@ExcelField(title="136订单接口 记录的创建日期 ", align=2, sort=108)
	public String getSapErdat() {
		return sapErdat;
	}

	public void setSapErdat(String sapErdat) {
		this.sapErdat = sapErdat;
	}
	
	@ExcelField(title="136订单接口 记录的创建时间", align=2, sort=109)
	public String getSapErzet() {
		return sapErzet;
	}

	public void setSapErzet(String sapErzet) {
		this.sapErzet = sapErzet;
	}
	
	@ExcelField(title="136订单接口 销售凭证 ", align=2, sort=110)
	public String getSapVbeln() {
		return sapVbeln;
	}

	public void setSapVbeln(String sapVbeln) {
		this.sapVbeln = sapVbeln;
	}
	
	@ExcelField(title="136订单接口 行项", align=2, sort=111)
	public String getSapPosnr() {
		return sapPosnr;
	}

	public void setSapPosnr(String sapPosnr) {
		this.sapPosnr = sapPosnr;
	}
	
	@ExcelField(title="136订单接口 销售组织 ", align=2, sort=112)
	public String getSapVkorg() {
		return sapVkorg;
	}

	public void setSapVkorg(String sapVkorg) {
		this.sapVkorg = sapVkorg;
	}
	
	@ExcelField(title="136订单接口 销售组织开头第一位", align=2, sort=113)
	public String getSapVkorgType() {
		return sapVkorgType;
	}

	public void setSapVkorgType(String sapVkorgType) {
		this.sapVkorgType = sapVkorgType;
	}
	
	@ExcelField(title="136订单接口 提货方式", align=2, sort=114)
	public String getSapZsdgVaTh() {
		return sapZsdgVaTh;
	}

	public void setSapZsdgVaTh(String sapZsdgVaTh) {
		this.sapZsdgVaTh = sapZsdgVaTh;
	}
	
	@ExcelField(title="136订单接口 销售凭证类型 ", align=2, sort=115)
	public String getSapAuart() {
		return sapAuart;
	}

	public void setSapAuart(String sapAuart) {
		this.sapAuart = sapAuart;
	}
	
	@ExcelField(title="136订单接口 交货", align=2, sort=116)
	public String getSapVbelnVl() {
		return sapVbelnVl;
	}

	public void setSapVbelnVl(String sapVbelnVl) {
		this.sapVbelnVl = sapVbelnVl;
	}
	
	@ExcelField(title="bstnk", align=2, sort=117)
	public String getJshdzIblKorderno() {
		return jshdzIblKorderno;
	}

	public void setJshdzIblKorderno(String jshdzIblKorderno) {
		this.jshdzIblKorderno = jshdzIblKorderno;
	}
	
	@ExcelField(title="车队编码", align=2, sort=118)
	public String getJshdzIblTdlnr() {
		return jshdzIblTdlnr;
	}

	public void setJshdzIblTdlnr(String jshdzIblTdlnr) {
		this.jshdzIblTdlnr = jshdzIblTdlnr;
	}
	
	@ExcelField(title="车牌号", align=2, sort=119)
	public String getJshdzIblSigni() {
		return jshdzIblSigni;
	}

	public void setJshdzIblSigni(String jshdzIblSigni) {
		this.jshdzIblSigni = jshdzIblSigni;
	}
	
	@ExcelField(title="司机联系电话", align=2, sort=120)
	public String getJshdzIblExti1() {
		return jshdzIblExti1;
	}

	public void setJshdzIblExti1(String jshdzIblExti1) {
		this.jshdzIblExti1 = jshdzIblExti1;
	}
	
	@ExcelField(title="车次号", align=2, sort=121)
	public Integer getJshdzIblHbdh() {
		return jshdzIblHbdh;
	}

	public void setJshdzIblHbdh(Integer jshdzIblHbdh) {
		this.jshdzIblHbdh = jshdzIblHbdh;
	}
	
	@ExcelField(title="sap更新时间", align=2, sort=122)
	public String getUpdateTimeSap() {
		return updateTimeSap;
	}

	public void setUpdateTimeSap(String updateTimeSap) {
		this.updateTimeSap = updateTimeSap;
	}
	
	@ExcelField(title="binlog更新时间", align=2, sort=123)
	public String getUpdateTimeBinlog() {
		return updateTimeBinlog;
	}

	public void setUpdateTimeBinlog(String updateTimeBinlog) {
		this.updateTimeBinlog = updateTimeBinlog;
	}
	
	@ExcelField(title="整条更新时间", align=2, sort=124)
	public String getUpdateTimeAll() {
		return updateTimeAll;
	}

	public void setUpdateTimeAll(String updateTimeAll) {
		this.updateTimeAll = updateTimeAll;
	}

	@Override
	public String toString() {
		return "QygTOrderInfo{" +
				"orderNo='" + orderNo + '\'' +
				", orderType='" + orderType + '\'' +
				", orderTypeName='" + orderTypeName + '\'' +
				", orderTypeNo='" + orderTypeNo + '\'' +
				", omsOrderType='" + omsOrderType + '\'' +
				", orderFlowNo='" + orderFlowNo + '\'' +
				", groupingFlowId=" + groupingFlowId +
				", groupingNo='" + groupingNo + '\'' +
				", activityId='" + activityId + '\'' +
				", activityName='" + activityName + '\'' +
				", stockNo='" + stockNo + '\'' +
				", stockType='" + stockType + '\'' +
				", planInDate='" + planInDate + '\'' +
				", yuncangCode='" + yuncangCode + '\'' +
				", tctpConfirm='" + tctpConfirm + '\'' +
				", deliveryType='" + deliveryType + '\'' +
				", deliveryAddr='" + deliveryAddr + '\'' +
				", deliveryCode='" + deliveryCode + '\'' +
				", deliveryYd='" + deliveryYd + '\'' +
				", deliveryName='" + deliveryName + '\'' +
				", saletoCode='" + saletoCode + '\'' +
				", saletoName='" + saletoName + '\'' +
				", paytoCode='" + paytoCode + '\'' +
				", paytoName='" + paytoName + '\'' +
				", paytoType='" + paytoType + '\'' +
				", syncScfStatus='" + syncScfStatus + '\'' +
				", sendtoCode='" + sendtoCode + '\'' +
				", sendtoName='" + sendtoName + '\'' +
				", sendtoAddress='" + sendtoAddress + '\'' +
				", manageCustomerCode='" + manageCustomerCode + '\'' +
				", manageCustomerName='" + manageCustomerName + '\'' +
				", regionCode='" + regionCode + '\'' +
				", mainChannelCode='" + mainChannelCode + '\'' +
				", subChannelCode='" + subChannelCode + '\'' +
				", orderSource='" + orderSource + '\'' +
				", tradeCode='" + tradeCode + '\'' +
				", saleOrgCode='" + saleOrgCode + '\'' +
				", orderAmount='" + orderAmount + '\'' +
				", orderDate='" + orderDate + '\'' +
				", omsOrderNo='" + omsOrderNo + '\'' +
				", gvsSoOrderNo='" + gvsSoOrderNo + '\'' +
				", so1OrderNo='" + so1OrderNo + '\'' +
				", dn1OrderNo='" + dn1OrderNo + '\'' +
				", so5OrderNo='" + so5OrderNo + '\'' +
				", dn5OrderNo='" + dn5OrderNo + '\'' +
				", syncDnStatus='" + syncDnStatus + '\'' +
				", gvsOrderType='" + gvsOrderType + '\'' +
				", orderChannel='" + orderChannel + '\'' +
				", procurementMethod='" + procurementMethod + '\'' +
				", conCode='" + conCode + '\'' +
				", billNo='" + billNo + '\'' +
				", billName='" + billName + '\'' +
				", comment='" + comment + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				", orderGvsStatus='" + orderGvsStatus + '\'' +
				", errorMsg='" + errorMsg + '\'' +
				", payTime='" + payTime + '\'' +
				", orderNoSeq=" + orderNoSeq +
				", placeOrderSeq=" + placeOrderSeq +
				", bigdataBatchUpdate='" + bigdataBatchUpdate + '\'' +
				", createdTime='" + createdTime + '\'' +
				", createdBy='" + createdBy + '\'' +
				", updateTime='" + updateTime + '\'' +
				", rowkey='" + rowkey + '\'' +
				", bstnk='" + bstnk + '\'' +
				", sapSo='" + sapSo + '\'' +
				", sapDn='" + sapDn + '\'' +
				", sapDndate='" + sapDndate + '\'' +
				", sapSo1='" + sapSo1 + '\'' +
				", sapSo5='" + sapSo5 + '\'' +
				", sapDn1='" + sapDn1 + '\'' +
				", sapDn5='" + sapDn5 + '\'' +
				", sapDn1date='" + sapDn1date + '\'' +
				", sapDn5date='" + sapDn5date + '\'' +
				", sapTaxInvoiceNo='" + sapTaxInvoiceNo + '\'' +
				", sapTaxInvoiceTime='" + sapTaxInvoiceTime + '\'' +
				", sapSysInvoiceNo='" + sapSysInvoiceNo + '\'' +
				", sapSysInvoiceTime='" + sapSysInvoiceTime + '\'' +
				", sapCenterRecieveDate='" + sapCenterRecieveDate + '\'' +
				", sapPtdSendDate='" + sapPtdSendDate + '\'' +
				", sapJudgeDate='" + sapJudgeDate + '\'' +
				", sapJudgeStatus='" + sapJudgeStatus + '\'' +
				", sapJudgeReason='" + sapJudgeReason + '\'' +
				", sapReorderDate='" + sapReorderDate + '\'' +
				", sapSend5Reason='" + sapSend5Reason + '\'' +
				", sapTransoutDate='" + sapTransoutDate + '\'' +
				", sapTransinDate='" + sapTransinDate + '\'' +
				", etlSource='" + etlSource + '\'' +
				", productCodeAll='" + productCodeAll + '\'' +
				", productGroupAll='" + productGroupAll + '\'' +
				", productBrandAll='" + productBrandAll + '\'' +
				", productModelAll='" + productModelAll + '\'' +
				", priceTypeAll='" + priceTypeAll + '\'' +
				", productNameAll='" + productNameAll + '\'' +
				", farWeeklyAll='" + farWeeklyAll + '\'' +
				", creditModelAll='" + creditModelAll + '\'' +
				", isTransferVersionAll='" + isTransferVersionAll + '\'' +
				", transferVersionAll='" + transferVersionAll + '\'' +
				", productTypeAll='" + productTypeAll + '\'' +
				", sapSended1='" + sapSended1 + '\'' +
				", sapSended5='" + sapSended5 + '\'' +
				", sapJudged='" + sapJudged + '\'' +
				", sapReorderd='" + sapReorderd + '\'' +
				", sapInvoiced='" + sapInvoiced + '\'' +
				", sapPtded='" + sapPtded + '\'' +
				", sapCenterRecieved='" + sapCenterRecieved + '\'' +
				", sapErdat='" + sapErdat + '\'' +
				", sapErzet='" + sapErzet + '\'' +
				", sapVbeln='" + sapVbeln + '\'' +
				", sapPosnr='" + sapPosnr + '\'' +
				", sapVkorg='" + sapVkorg + '\'' +
				", sapVkorgType='" + sapVkorgType + '\'' +
				", sapZsdgVaTh='" + sapZsdgVaTh + '\'' +
				", sapAuart='" + sapAuart + '\'' +
				", sapVbelnVl='" + sapVbelnVl + '\'' +
				", jshdzIblKorderno='" + jshdzIblKorderno + '\'' +
				", jshdzIblTdlnr='" + jshdzIblTdlnr + '\'' +
				", jshdzIblSigni='" + jshdzIblSigni + '\'' +
				", jshdzIblExti1='" + jshdzIblExti1 + '\'' +
				", jshdzIblHbdh=" + jshdzIblHbdh +
				", updateTimeSap='" + updateTimeSap + '\'' +
				", updateTimeBinlog='" + updateTimeBinlog + '\'' +
				", updateTimeAll='" + updateTimeAll + '\'' +
				", remarks='" + remarks + '\'' +
				", createBy=" + createBy +
				", createDate=" + createDate +
				", updateBy=" + updateBy +
				", updateDate=" + updateDate +
				", delFlag='" + delFlag + '\'' +
				", id='" + id + '\'' +
				", currentUser=" + currentUser +
				", page=" + page +
				", dataScope='" + dataScope + '\'' +
				", isNewRecord=" + isNewRecord +
				", IdType='" + IdType + '\'' +
				'}';
	}
}