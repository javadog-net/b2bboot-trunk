
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contractReceivableSaveParam", propOrder = {
    "batchDate",
    "contractId",
    "creProId",
    "createdBy",
    "createdById",
    "createdDate",
    "deleted",
    "id",
    "lastModifiedBy",
    "lastModifiedById",
    "lastModifiedDate",
    "modProId",
    "nodeTitle",
    "paymentAmount",
    "paymentRatio",
    "paymentTime"
})
public class ContractReceivableSaveParam
    extends ToString
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar batchDate;
    protected String contractId;
    protected String creProId;
    protected String createdBy;
    protected String createdById;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    protected Boolean deleted;
    protected String id;
    protected String lastModifiedBy;
    protected String lastModifiedById;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    protected String modProId;
    protected String nodeTitle;
    protected BigDecimal paymentAmount;
    protected BigDecimal paymentRatio;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar paymentTime;

     
    public XMLGregorianCalendar getBatchDate() {
        return batchDate;
    }

     
    public void setBatchDate(XMLGregorianCalendar value) {
        this.batchDate = value;
    }

     
    public String getContractId() {
        return contractId;
    }

     
    public void setContractId(String value) {
        this.contractId = value;
    }

     
    public String getCreProId() {
        return creProId;
    }

     
    public void setCreProId(String value) {
        this.creProId = value;
    }

     
    public String getCreatedBy() {
        return createdBy;
    }

     
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

     
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

     
    public Boolean isDeleted() {
        return deleted;
    }

     
    public void setDeleted(Boolean value) {
        this.deleted = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

     
    public void setLastModifiedBy(String value) {
        this.lastModifiedBy = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

     
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

     
    public String getModProId() {
        return modProId;
    }

     
    public void setModProId(String value) {
        this.modProId = value;
    }

     
    public String getNodeTitle() {
        return nodeTitle;
    }

     
    public void setNodeTitle(String value) {
        this.nodeTitle = value;
    }

     
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

     
    public void setPaymentAmount(BigDecimal value) {
        this.paymentAmount = value;
    }

     
    public BigDecimal getPaymentRatio() {
        return paymentRatio;
    }

     
    public void setPaymentRatio(BigDecimal value) {
        this.paymentRatio = value;
    }

     
    public XMLGregorianCalendar getPaymentTime() {
        return paymentTime;
    }

     
    public void setPaymentTime(XMLGregorianCalendar value) {
        this.paymentTime = value;
    }

}
