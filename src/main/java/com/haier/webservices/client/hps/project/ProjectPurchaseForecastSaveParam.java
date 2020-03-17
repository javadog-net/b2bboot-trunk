
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectPurchaseForecastSaveParam", propOrder = {
    "beWisdom",
    "brand",
    "createdById",
    "estimatedQuantity",
    "id",
    "industryLine",
    "lastModifiedById",
    "productGroup",
    "projectId",
    "purchaseBudget"
})
public class ProjectPurchaseForecastSaveParam
    extends ToString
{

    protected Boolean beWisdom;
    protected String brand;
    protected String createdById;
    protected Long estimatedQuantity;
    protected String id;
    protected String industryLine;
    protected String lastModifiedById;
    protected String productGroup;
    protected String projectId;
    protected BigDecimal purchaseBudget;

     
    public Boolean isBeWisdom() {
        return beWisdom;
    }

     
    public void setBeWisdom(Boolean value) {
        this.beWisdom = value;
    }

     
    public String getBrand() {
        return brand;
    }

     
    public void setBrand(String value) {
        this.brand = value;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public Long getEstimatedQuantity() {
        return estimatedQuantity;
    }

     
    public void setEstimatedQuantity(Long value) {
        this.estimatedQuantity = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getIndustryLine() {
        return industryLine;
    }

     
    public void setIndustryLine(String value) {
        this.industryLine = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public String getProductGroup() {
        return productGroup;
    }

     
    public void setProductGroup(String value) {
        this.productGroup = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public BigDecimal getPurchaseBudget() {
        return purchaseBudget;
    }

     
    public void setPurchaseBudget(BigDecimal value) {
        this.purchaseBudget = value;
    }

}
