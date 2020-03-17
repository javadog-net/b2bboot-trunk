
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "competiorAnalysisTechnologySaveParam", propOrder = {
    "brand",
    "createdById",
    "id",
    "installationCostComparison",
    "lastModifiedById",
    "materialCostComparison",
    "modelType",
    "price",
    "productCode",
    "productName",
    "projectId"
})
public class CompetiorAnalysisTechnologySaveParam
    extends ToString
{

    protected String brand;
    protected String createdById;
    protected String id;
    protected String installationCostComparison;
    protected String lastModifiedById;
    protected String materialCostComparison;
    protected String modelType;
    protected BigDecimal price;
    protected String productCode;
    protected String productName;
    protected String projectId;

     
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

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getInstallationCostComparison() {
        return installationCostComparison;
    }

     
    public void setInstallationCostComparison(String value) {
        this.installationCostComparison = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public String getMaterialCostComparison() {
        return materialCostComparison;
    }

     
    public void setMaterialCostComparison(String value) {
        this.materialCostComparison = value;
    }

     
    public String getModelType() {
        return modelType;
    }

     
    public void setModelType(String value) {
        this.modelType = value;
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

     
    public String getProductName() {
        return productName;
    }

     
    public void setProductName(String value) {
        this.productName = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

}
