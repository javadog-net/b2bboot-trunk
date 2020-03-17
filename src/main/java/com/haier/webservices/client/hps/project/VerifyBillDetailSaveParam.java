
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verifyBillDetailSaveParam", propOrder = {
    "address",
    "cbillCode",
    "createdById",
    "id",
    "lastModifiedById",
    "memo",
    "modelCode",
    "modelName",
    "productGroupCode",
    "productGroupName",
    "projectCode",
    "quantity",
    "verifyBillId"
})
public class VerifyBillDetailSaveParam
    extends ToString
{

    protected String address;
    protected String cbillCode;
    protected String createdById;
    protected String id;
    protected String lastModifiedById;
    protected String memo;
    protected String modelCode;
    protected String modelName;
    protected String productGroupCode;
    protected String productGroupName;
    protected String projectCode;
    protected Integer quantity;
    protected String verifyBillId;

     
    public String getAddress() {
        return address;
    }

     
    public void setAddress(String value) {
        this.address = value;
    }

     
    public String getCbillCode() {
        return cbillCode;
    }

     
    public void setCbillCode(String value) {
        this.cbillCode = value;
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

     
    public String getMemo() {
        return memo;
    }

     
    public void setMemo(String value) {
        this.memo = value;
    }

     
    public String getModelCode() {
        return modelCode;
    }

     
    public void setModelCode(String value) {
        this.modelCode = value;
    }

     
    public String getModelName() {
        return modelName;
    }

     
    public void setModelName(String value) {
        this.modelName = value;
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

     
    public String getProjectCode() {
        return projectCode;
    }

     
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

     
    public Integer getQuantity() {
        return quantity;
    }

     
    public void setQuantity(Integer value) {
        this.quantity = value;
    }

     
    public String getVerifyBillId() {
        return verifyBillId;
    }

     
    public void setVerifyBillId(String value) {
        this.verifyBillId = value;
    }

}
