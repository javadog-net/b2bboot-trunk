package com.haier.oms;

import java.io.Serializable;
import java.util.Date;

public class ParamsObjectOmsCreate implements Serializable {
    //订单类型ZKH2:授信支付/ZKH4:在线支付
    private String soTypeCode;

    //客户单号
    private String custOrderCode;

    //产品组编码
    private String prodLineCode;

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

    //型号编码
    private String productCode;

    //数量
    private int orderQty;

    //价格
    private double price;

    //总金额
    private double totalMount;

    //销售组织
    private String saleOrgId;

    //销售工厂
    private String salesFactory;

    //期望到货日期
    private Date orderRevDate;

    //主仓编码
    private String mainHouse;

    //来源系统
    private String fromSystem;

    public String getSoTypeCode() {
        return soTypeCode;
    }

    public void setSoTypeCode(String soTypeCode) {
        this.soTypeCode = soTypeCode;
    }

    public String getCustOrderCode() {
        return custOrderCode;
    }

    public void setCustOrderCode(String custOrderCode) {
        this.custOrderCode = custOrderCode;
    }

    public String getProdLineCode() {
        return prodLineCode;
    }

    public void setProdLineCode(String prodLineCode) {
        this.prodLineCode = prodLineCode;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalMount() {
        return totalMount;
    }

    public void setTotalMount(double totalMount) {
        this.totalMount = totalMount;
    }

    public String getSaleOrgId() {
        return saleOrgId;
    }

    public void setSaleOrgId(String saleOrgId) {
        this.saleOrgId = saleOrgId;
    }

    public String getSalesFactory() {
        return salesFactory;
    }

    public void setSalesFactory(String salesFactory) {
        this.salesFactory = salesFactory;
    }

    public Date getOrderRevDate() {
        return orderRevDate;
    }

    public void setOrderRevDate(Date orderRevDate) {
        this.orderRevDate = orderRevDate;
    }

    public String getMainHouse() {
        return mainHouse;
    }

    public void setMainHouse(String mainHouse) {
        this.mainHouse = mainHouse;
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem;
    }
}
