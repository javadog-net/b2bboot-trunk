
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "strategyProductDTO", propOrder = {
    "beValid",
    "contId",
    "count",
    "createdById",
    "id",
    "lastModifiedById",
    "price",
    "productCode",
    "productGroupCode",
    "productGroupName",
    "productModel",
    "productName",
    "totalPrice"
})
public class StrategyProductDTO
    extends BasicDto
{

    protected Boolean beValid;
    protected String contId;
    protected Integer count;
    protected String createdById;
    protected String id;
    protected String lastModifiedById;
    protected BigDecimal price;
    protected String productCode;
    protected String productGroupCode;
    protected String productGroupName;
    protected String productModel;
    protected String productName;
    protected BigDecimal totalPrice;

     
    public Boolean isBeValid() {
        return beValid;
    }

     
    public void setBeValid(Boolean value) {
        this.beValid = value;
    }

     
    public String getContId() {
        return contId;
    }

     
    public void setContId(String value) {
        this.contId = value;
    }

     
    public Integer getCount() {
        return count;
    }

     
    public void setCount(Integer value) {
        this.count = value;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
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

     
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

     
    public void setTotalPrice(BigDecimal value) {
        this.totalPrice = value;
    }

}
