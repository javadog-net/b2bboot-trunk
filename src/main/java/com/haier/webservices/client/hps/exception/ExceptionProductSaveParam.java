
package com.haier.webservices.client.hps.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exceptionProductSaveParam", propOrder = {
    "drownApplyQuantity",
    "id",
    "productCode",
    "productName",
    "quantityUnavailable"
})
public class ExceptionProductSaveParam
    extends ToString
{

    protected Long drownApplyQuantity;
    protected String id;
    protected String productCode;
    protected String productName;
    protected Long quantityUnavailable;

     
    public Long getDrownApplyQuantity() {
        return drownApplyQuantity;
    }

     
    public void setDrownApplyQuantity(Long value) {
        this.drownApplyQuantity = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
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

     
    public Long getQuantityUnavailable() {
        return quantityUnavailable;
    }

     
    public void setQuantityUnavailable(Long value) {
        this.quantityUnavailable = value;
    }

}
