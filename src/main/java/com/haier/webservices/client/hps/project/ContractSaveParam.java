
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contractSaveParam", propOrder = {
    "approvalStatus",
    "beCash",
    "beChanging",
    "contractAttachement",
    "contractCode",
    "contractEndTime",
    "contractName",
    "contractOtherFile",
    "contractStartTime",
    "contractTotalAmount",
    "contractType",
    "createdById",
    "id",
    "lastModifiedById",
    "listP",
    "listR",
    "operator",
    "projectId",
    "signFirstParty",
    "signFirstPartyCode",
    "signFirstPartyId",
    "signSecondParty",
    "signSecondPartyCode",
    "signSecondPartyId",
    "signTime",
    "versionIndex",
    "versionName"
})
public class ContractSaveParam
    extends ToString
{

    protected String approvalStatus;
    protected Boolean beCash;
    protected Boolean beChanging;
    protected String contractAttachement;
    protected String contractCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndTime;
    protected String contractName;
    protected String contractOtherFile;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractStartTime;
    protected BigDecimal contractTotalAmount;
    protected String contractType;
    protected String createdById;
    protected String id;
    protected String lastModifiedById;
    @XmlElement(nillable = true)
    protected List<HashMap> listP;
    @XmlElement(nillable = true)
    protected List<ContractReceivableSaveParam> listR;
    protected String operator;
    protected String projectId;
    protected String signFirstParty;
    protected String signFirstPartyCode;
    protected String signFirstPartyId;
    protected String signSecondParty;
    protected String signSecondPartyCode;
    protected String signSecondPartyId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar signTime;
    protected Integer versionIndex;
    protected String versionName;

     
    public String getApprovalStatus() {
        return approvalStatus;
    }

     
    public void setApprovalStatus(String value) {
        this.approvalStatus = value;
    }

     
    public Boolean isBeCash() {
        return beCash;
    }

     
    public void setBeCash(Boolean value) {
        this.beCash = value;
    }

     
    public Boolean isBeChanging() {
        return beChanging;
    }

     
    public void setBeChanging(Boolean value) {
        this.beChanging = value;
    }

     
    public String getContractAttachement() {
        return contractAttachement;
    }

     
    public void setContractAttachement(String value) {
        this.contractAttachement = value;
    }

     
    public String getContractCode() {
        return contractCode;
    }

     
    public void setContractCode(String value) {
        this.contractCode = value;
    }

     
    public XMLGregorianCalendar getContractEndTime() {
        return contractEndTime;
    }

     
    public void setContractEndTime(XMLGregorianCalendar value) {
        this.contractEndTime = value;
    }

     
    public String getContractName() {
        return contractName;
    }

     
    public void setContractName(String value) {
        this.contractName = value;
    }

     
    public String getContractOtherFile() {
        return contractOtherFile;
    }

     
    public void setContractOtherFile(String value) {
        this.contractOtherFile = value;
    }

     
    public XMLGregorianCalendar getContractStartTime() {
        return contractStartTime;
    }

     
    public void setContractStartTime(XMLGregorianCalendar value) {
        this.contractStartTime = value;
    }

     
    public BigDecimal getContractTotalAmount() {
        return contractTotalAmount;
    }

     
    public void setContractTotalAmount(BigDecimal value) {
        this.contractTotalAmount = value;
    }

     
    public String getContractType() {
        return contractType;
    }

     
    public void setContractType(String value) {
        this.contractType = value;
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

     
    public List<HashMap> getListP() {
        if (listP == null) {
            listP = new ArrayList<HashMap>();
        }
        return this.listP;
    }

     
    public List<ContractReceivableSaveParam> getListR() {
        if (listR == null) {
            listR = new ArrayList<ContractReceivableSaveParam>();
        }
        return this.listR;
    }

     
    public String getOperator() {
        return operator;
    }

     
    public void setOperator(String value) {
        this.operator = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public String getSignFirstParty() {
        return signFirstParty;
    }

     
    public void setSignFirstParty(String value) {
        this.signFirstParty = value;
    }

     
    public String getSignFirstPartyCode() {
        return signFirstPartyCode;
    }

     
    public void setSignFirstPartyCode(String value) {
        this.signFirstPartyCode = value;
    }

     
    public String getSignFirstPartyId() {
        return signFirstPartyId;
    }

     
    public void setSignFirstPartyId(String value) {
        this.signFirstPartyId = value;
    }

     
    public String getSignSecondParty() {
        return signSecondParty;
    }

     
    public void setSignSecondParty(String value) {
        this.signSecondParty = value;
    }

     
    public String getSignSecondPartyCode() {
        return signSecondPartyCode;
    }

     
    public void setSignSecondPartyCode(String value) {
        this.signSecondPartyCode = value;
    }

     
    public String getSignSecondPartyId() {
        return signSecondPartyId;
    }

     
    public void setSignSecondPartyId(String value) {
        this.signSecondPartyId = value;
    }

     
    public XMLGregorianCalendar getSignTime() {
        return signTime;
    }

     
    public void setSignTime(XMLGregorianCalendar value) {
        this.signTime = value;
    }

     
    public Integer getVersionIndex() {
        return versionIndex;
    }

     
    public void setVersionIndex(Integer value) {
        this.versionIndex = value;
    }

     
    public String getVersionName() {
        return versionName;
    }

     
    public void setVersionName(String value) {
        this.versionName = value;
    }

}
