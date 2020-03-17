
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "brownCancelVO", propOrder = {
    "beValid",
    "businessId",
    "businessType",
    "createdByCode",
    "createdDate",
    "deliverySystemResult",
    "lastModifiedByCode",
    "lastModifiedDate",
    "processId",
    "processState",
    "reason",
    "returnMessage",
    "versionNum"
})
public class BrownCancelVO
    extends ToString
{

    protected Boolean beValid;
    protected String businessId;
    protected String businessType;
    protected String createdByCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    protected String deliverySystemResult;
    protected String lastModifiedByCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    protected String processId;
    protected String processState;
    protected String reason;
    protected String returnMessage;
    protected String versionNum;

     
    public Boolean isBeValid() {
        return beValid;
    }

     
    public void setBeValid(Boolean value) {
        this.beValid = value;
    }

     
    public String getBusinessId() {
        return businessId;
    }

     
    public void setBusinessId(String value) {
        this.businessId = value;
    }

     
    public String getBusinessType() {
        return businessType;
    }

     
    public void setBusinessType(String value) {
        this.businessType = value;
    }

     
    public String getCreatedByCode() {
        return createdByCode;
    }

     
    public void setCreatedByCode(String value) {
        this.createdByCode = value;
    }

     
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

     
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

     
    public String getDeliverySystemResult() {
        return deliverySystemResult;
    }

     
    public void setDeliverySystemResult(String value) {
        this.deliverySystemResult = value;
    }

     
    public String getLastModifiedByCode() {
        return lastModifiedByCode;
    }

     
    public void setLastModifiedByCode(String value) {
        this.lastModifiedByCode = value;
    }

     
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

     
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

     
    public String getProcessId() {
        return processId;
    }

     
    public void setProcessId(String value) {
        this.processId = value;
    }

     
    public String getProcessState() {
        return processState;
    }

     
    public void setProcessState(String value) {
        this.processState = value;
    }

     
    public String getReason() {
        return reason;
    }

     
    public void setReason(String value) {
        this.reason = value;
    }

     
    public String getReturnMessage() {
        return returnMessage;
    }

     
    public void setReturnMessage(String value) {
        this.returnMessage = value;
    }

     
    public String getVersionNum() {
        return versionNum;
    }

     
    public void setVersionNum(String value) {
        this.versionNum = value;
    }

}
