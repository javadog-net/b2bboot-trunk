
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "quoteMaterialSaveParam", propOrder = {
    "beLowerPrice",
    "costPrice",
    "demandQuantity",
    "id",
    "materialBrandCode",
    "materialCode",
    "materialGroupCode",
    "materialName",
    "materialSpecCode",
    "productCode",
    "productName",
    "projectCode",
    "projectId",
    "quoteId",
    "totalPrice",
    "unit",
    "unitPrice"
})
public class QuoteMaterialSaveParam
    extends ToString
{

    protected Boolean beLowerPrice;
    protected BigDecimal costPrice;
    protected Integer demandQuantity;
    protected String id;
    protected String materialBrandCode;
    protected String materialCode;
    protected String materialGroupCode;
    protected String materialName;
    protected String materialSpecCode;
    protected String productCode;
    protected String productName;
    protected String projectCode;
    protected String projectId;
    protected String quoteId;
    protected BigDecimal totalPrice;
    protected String unit;
    protected BigDecimal unitPrice;

     
    public Boolean isBeLowerPrice() {
        return beLowerPrice;
    }

     
    public void setBeLowerPrice(Boolean value) {
        this.beLowerPrice = value;
    }

     
    public BigDecimal getCostPrice() {
        return costPrice;
    }

     
    public void setCostPrice(BigDecimal value) {
        this.costPrice = value;
    }

     
    public Integer getDemandQuantity() {
        return demandQuantity;
    }

     
    public void setDemandQuantity(Integer value) {
        this.demandQuantity = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getMaterialBrandCode() {
        return materialBrandCode;
    }

     
    public void setMaterialBrandCode(String value) {
        this.materialBrandCode = value;
    }

     
    public String getMaterialCode() {
        return materialCode;
    }

     
    public void setMaterialCode(String value) {
        this.materialCode = value;
    }

     
    public String getMaterialGroupCode() {
        return materialGroupCode;
    }

     
    public void setMaterialGroupCode(String value) {
        this.materialGroupCode = value;
    }

     
    public String getMaterialName() {
        return materialName;
    }

     
    public void setMaterialName(String value) {
        this.materialName = value;
    }

     
    public String getMaterialSpecCode() {
        return materialSpecCode;
    }

     
    public void setMaterialSpecCode(String value) {
        this.materialSpecCode = value;
    }

     
    public String getProductCode() {
        return productCode;
    }

     
    public void setProductCode(String value) {
        this.productCode = value;
    }

     
    public String getProductName() {
        return productName;
    }

     
    public void setProductName(String value) {
        this.productName = value;
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

     
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

     
    public void setTotalPrice(BigDecimal value) {
        this.totalPrice = value;
    }

     
    public String getUnit() {
        return unit;
    }

     
    public void setUnit(String value) {
        this.unit = value;
    }

     
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

     
    public void setUnitPrice(BigDecimal value) {
        this.unitPrice = value;
    }

}
