
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "brownItemVO", propOrder = {
    "actualOrderQuantity",
    "avePolicy",
    "brownId",
    "cinvmUnit",
    "createdById",
    "curPolicy",
    "id",
    "iinvrCost",
    "itaifan",
    "izhikou",
    "lastModifiedById",
    "orderQuantity",
    "price",
    "productCode",
    "productId",
    "productModel",
    "productName",
    "profit"
})
public class BrownItemVO
    extends ToString
{

    protected Long actualOrderQuantity;
    protected BigDecimal avePolicy;
    protected String brownId;
    protected String cinvmUnit;
    protected String createdById;
    protected BigDecimal curPolicy;
    protected String id;
    protected BigDecimal iinvrCost;
    protected BigDecimal itaifan;
    protected BigDecimal izhikou;
    protected String lastModifiedById;
    protected Long orderQuantity;
    protected BigDecimal price;
    protected String productCode;
    protected String productId;
    protected String productModel;
    protected String productName;
    protected BigDecimal profit;

     
    public Long getActualOrderQuantity() {
        return actualOrderQuantity;
    }

     
    public void setActualOrderQuantity(Long value) {
        this.actualOrderQuantity = value;
    }

     
    public BigDecimal getAvePolicy() {
        return avePolicy;
    }

     
    public void setAvePolicy(BigDecimal value) {
        this.avePolicy = value;
    }

     
    public String getBrownId() {
        return brownId;
    }

     
    public void setBrownId(String value) {
        this.brownId = value;
    }

     
    public String getCinvmUnit() {
        return cinvmUnit;
    }

     
    public void setCinvmUnit(String value) {
        this.cinvmUnit = value;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public BigDecimal getCurPolicy() {
        return curPolicy;
    }

     
    public void setCurPolicy(BigDecimal value) {
        this.curPolicy = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public BigDecimal getIinvrCost() {
        return iinvrCost;
    }

     
    public void setIinvrCost(BigDecimal value) {
        this.iinvrCost = value;
    }

     
    public BigDecimal getItaifan() {
        return itaifan;
    }

     
    public void setItaifan(BigDecimal value) {
        this.itaifan = value;
    }

     
    public BigDecimal getIzhikou() {
        return izhikou;
    }

     
    public void setIzhikou(BigDecimal value) {
        this.izhikou = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public Long getOrderQuantity() {
        return orderQuantity;
    }

     
    public void setOrderQuantity(Long value) {
        this.orderQuantity = value;
    }

     
    public BigDecimal getPrice() {
        return price;
    }

     
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

     
    public String getProductCode() {
        return productCode;
    }

     
    public void setProductCode(String value) {
        this.productCode = value;
    }

     
    public String getProductId() {
        return productId;
    }

     
    public void setProductId(String value) {
        this.productId = value;
    }

     
    public String getProductModel() {
        return productModel;
    }

     
    public void setProductModel(String value) {
        this.productModel = value;
    }

     
    public String getProductName() {
        return productName;
    }

     
    public void setProductName(String value) {
        this.productName = value;
    }

     
    public BigDecimal getProfit() {
        return profit;
    }

     
    public void setProfit(BigDecimal value) {
        this.profit = value;
    }

}
