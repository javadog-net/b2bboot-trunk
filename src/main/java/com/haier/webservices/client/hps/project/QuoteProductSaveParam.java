
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "quoteProductSaveParam", propOrder = {
    "beLowerPrice",
    "beWisdom",
    "brandCode",
    "brandName",
    "demandQuantity",
    "domainCode",
    "domainName",
    "id",
    "pointDeduction",
    "productCode",
    "productGroupCode",
    "productGroupName",
    "productName",
    "productQuote",
    "projectCode",
    "projectId",
    "quoteId",
    "reservePrice",
    "returnAmount",
    "sumPrice",
    "supplyPrice"
})
public class QuoteProductSaveParam
    extends ToString
{

    protected Boolean beLowerPrice;
    protected Boolean beWisdom;
    protected String brandCode;
    protected String brandName;
    protected Integer demandQuantity;
    protected String domainCode;
    protected String domainName;
    protected String id;
    protected Double pointDeduction;
    protected String productCode;
    protected String productGroupCode;
    protected String productGroupName;
    protected String productName;
    protected BigDecimal productQuote;
    protected String projectCode;
    protected String projectId;
    protected String quoteId;
    protected BigDecimal reservePrice;
    protected BigDecimal returnAmount;
    protected BigDecimal sumPrice;
    protected BigDecimal supplyPrice;

     
    public Boolean isBeLowerPrice() {
        return beLowerPrice;
    }

     
    public void setBeLowerPrice(Boolean value) {
        this.beLowerPrice = value;
    }

     
    public Boolean isBeWisdom() {
        return beWisdom;
    }

     
    public void setBeWisdom(Boolean value) {
        this.beWisdom = value;
    }

     
    public String getBrandCode() {
        return brandCode;
    }

     
    public void setBrandCode(String value) {
        this.brandCode = value;
    }

     
    public String getBrandName() {
        return brandName;
    }

     
    public void setBrandName(String value) {
        this.brandName = value;
    }

     
    public Integer getDemandQuantity() {
        return demandQuantity;
    }

     
    public void setDemandQuantity(Integer value) {
        this.demandQuantity = value;
    }

     
    public String getDomainCode() {
        return domainCode;
    }

     
    public void setDomainCode(String value) {
        this.domainCode = value;
    }

     
    public String getDomainName() {
        return domainName;
    }

     
    public void setDomainName(String value) {
        this.domainName = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public Double getPointDeduction() {
        return pointDeduction;
    }

     
    public void setPointDeduction(Double value) {
        this.pointDeduction = value;
    }

     
    public String getProductCode() {
        return productCode;
    }

     
    public void setProductCode(String value) {
        this.productCode = value;
    }

     
    public String getProductGroupCode() {
        return productGroupCode;
    }

     
    public void setProductGroupCode(String value) {
        this.productGroupCode = value;
    }

     
    public String getProductGroupName() {
        return productGroupName;
    }

     
    public void setProductGroupName(String value) {
        this.productGroupName = value;
    }

     
    public String getProductName() {
        return productName;
    }

     
    public void setProductName(String value) {
        this.productName = value;
    }

     
    public BigDecimal getProductQuote() {
        return productQuote;
    }

     
    public void setProductQuote(BigDecimal value) {
        this.productQuote = value;
    }

     
    public String getProjectCode() {
        return projectCode;
    }

     
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public String getQuoteId() {
        return quoteId;
    }

     
    public void setQuoteId(String value) {
        this.quoteId = value;
    }

     
    public BigDecimal getReservePrice() {
        return reservePrice;
    }

     
    public void setReservePrice(BigDecimal value) {
        this.reservePrice = value;
    }

     
    public BigDecimal getReturnAmount() {
        return returnAmount;
    }

     
    public void setReturnAmount(BigDecimal value) {
        this.returnAmount = value;
    }

     
    public BigDecimal getSumPrice() {
        return sumPrice;
    }

     
    public void setSumPrice(BigDecimal value) {
        this.sumPrice = value;
    }

     
    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

     
    public void setSupplyPrice(BigDecimal value) {
        this.supplyPrice = value;
    }

}
