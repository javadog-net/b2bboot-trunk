package com.jhmis.modules.customer.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

public class BrownException {
    protected String brownCode;
    protected String brownId;
    protected String contactPerson;
    protected String fileId;
    protected String id;
    protected String messageNum;
    protected List<BrownExceptionProduct> param;
    protected String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date proposeDate;
    protected String questioner;
    protected String reasonFlag;
    protected String reasonInfo;
    protected String quantityStr;

    public String getQuantityStr() {
        return quantityStr;
    }

    public void setQuantityStr(String quantityStr) {
        this.quantityStr = quantityStr;
    }

    public Date getProposeDate() {
        return proposeDate;
    }

    public void setProposeDate(Date proposeDate) {
        this.proposeDate = proposeDate;
    }

    public String getBrownCode() {
        return brownCode;
    }

    public void setBrownCode(String brownCode) {
        this.brownCode = brownCode;
    }

    public String getBrownId() {
        return brownId;
    }

    public void setBrownId(String brownId) {
        this.brownId = brownId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(String messageNum) {
        this.messageNum = messageNum;
    }

    public List<BrownExceptionProduct> getParam() {
        return param;
    }

    public void setParam(List<BrownExceptionProduct> param) {
        this.param = param;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQuestioner() {
        return questioner;
    }

    public void setQuestioner(String questioner) {
        this.questioner = questioner;
    }

    public String getReasonFlag() {
        return reasonFlag;
    }

    public void setReasonFlag(String reasonFlag) {
        this.reasonFlag = reasonFlag;
    }

    public String getReasonInfo() {
        return reasonInfo;
    }

    public void setReasonInfo(String reasonInfo) {
        this.reasonInfo = reasonInfo;
    }
}
