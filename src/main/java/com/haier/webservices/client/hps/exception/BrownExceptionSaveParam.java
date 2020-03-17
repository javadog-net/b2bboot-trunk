
package com.haier.webservices.client.hps.exception;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "brownExceptionSaveParam", propOrder = {
    "approver",
    "brownCode",
    "brownId",
    "contactPerson",
    "fileId",
    "id",
    "messageNum",
    "param",
    "phone",
    "proposeDate",
    "questioner",
    "reasonFlag",
    "reasonInfo",
    "status"
})
public class BrownExceptionSaveParam
    extends ToString
{

    protected String approver;
    protected String brownCode;
    protected String brownId;
    protected String contactPerson;
    protected String fileId;
    protected String id;
    protected String messageNum;
    @XmlElement(nillable = true)
    protected List<ExceptionProductSaveParam> param;
    protected String phone;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar proposeDate;
    protected String questioner;
    protected String reasonFlag;
    protected String reasonInfo;
    protected String status;

     
    public String getApprover() {
        return approver;
    }

     
    public void setApprover(String value) {
        this.approver = value;
    }

     
    public String getBrownCode() {
        return brownCode;
    }

     
    public void setBrownCode(String value) {
        this.brownCode = value;
    }

     
    public String getBrownId() {
        return brownId;
    }

     
    public void setBrownId(String value) {
        this.brownId = value;
    }

     
    public String getContactPerson() {
        return contactPerson;
    }

     
    public void setContactPerson(String value) {
        this.contactPerson = value;
    }

     
    public String getFileId() {
        return fileId;
    }

     
    public void setFileId(String value) {
        this.fileId = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getMessageNum() {
        return messageNum;
    }

     
    public void setMessageNum(String value) {
        this.messageNum = value;
    }

     
    public List<ExceptionProductSaveParam> getParam() {
        if (param == null) {
            param = new ArrayList<ExceptionProductSaveParam>();
        }
        return this.param;
    }

     
    public String getPhone() {
        return phone;
    }

     
    public void setPhone(String value) {
        this.phone = value;
    }

     
    public XMLGregorianCalendar getProposeDate() {
        return proposeDate;
    }

     
    public void setProposeDate(XMLGregorianCalendar value) {
        this.proposeDate = value;
    }

     
    public String getQuestioner() {
        return questioner;
    }

     
    public void setQuestioner(String value) {
        this.questioner = value;
    }

     
    public String getReasonFlag() {
        return reasonFlag;
    }

     
    public void setReasonFlag(String value) {
        this.reasonFlag = value;
    }

     
    public String getReasonInfo() {
        return reasonInfo;
    }

     
    public void setReasonInfo(String value) {
        this.reasonInfo = value;
    }

     
    public String getStatus() {
        return status;
    }

     
    public void setStatus(String value) {
        this.status = value;
    }

}
