
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
@XmlType(name = "projectRFourSaveParam", propOrder = {
    "actualInstallationAmount",
    "actualTenderTime",
    "advanceFundCode",
    "advanceFundName",
    "afterSalesBidding",
    "beAdvanceFund",
    "beSendWithOne",
    "beSinceSupervision",
    "business",
    "businessProfits",
    "competiorAnalysisList",
    "createdById",
    "firstBidDocument",
    "id",
    "lastModifiedById",
    "money",
    "needDirectDeliverySite",
    "poolOrgRatio",
    "projectId",
    "technicalManager",
    "technicalManagerName",
    "technicalManagerStandardPost",
    "technicalProposal",
    "technicalProposalTime",
    "tender",
    "wbsCode"
})
public class ProjectRFourSaveParam
    extends ToString
{

    protected BigDecimal actualInstallationAmount;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualTenderTime;
    protected String advanceFundCode;
    protected String advanceFundName;
    protected Boolean afterSalesBidding;
    protected Boolean beAdvanceFund;
    protected Boolean beSendWithOne;
    protected Boolean beSinceSupervision;
    protected String business;
    protected BigDecimal businessProfits;
    @XmlElement(nillable = true)
    protected List<CompetiorAnalysisTechnologySaveParam> competiorAnalysisList;
    protected String createdById;
    protected String firstBidDocument;
    protected String id;
    protected String lastModifiedById;
    protected BigDecimal money;
    protected Boolean needDirectDeliverySite;
    protected Double poolOrgRatio;
    protected String projectId;
    protected String technicalManager;
    protected String technicalManagerName;
    protected String technicalManagerStandardPost;
    protected String technicalProposal;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar technicalProposalTime;
    protected String tender;
    protected String wbsCode;

     
    public BigDecimal getActualInstallationAmount() {
        return actualInstallationAmount;
    }

     
    public void setActualInstallationAmount(BigDecimal value) {
        this.actualInstallationAmount = value;
    }

     
    public XMLGregorianCalendar getActualTenderTime() {
        return actualTenderTime;
    }

     
    public void setActualTenderTime(XMLGregorianCalendar value) {
        this.actualTenderTime = value;
    }

     
    public String getAdvanceFundCode() {
        return advanceFundCode;
    }

     
    public void setAdvanceFundCode(String value) {
        this.advanceFundCode = value;
    }

     
    public String getAdvanceFundName() {
        return advanceFundName;
    }

     
    public void setAdvanceFundName(String value) {
        this.advanceFundName = value;
    }

     
    public Boolean isAfterSalesBidding() {
        return afterSalesBidding;
    }

     
    public void setAfterSalesBidding(Boolean value) {
        this.afterSalesBidding = value;
    }

     
    public Boolean isBeAdvanceFund() {
        return beAdvanceFund;
    }

     
    public void setBeAdvanceFund(Boolean value) {
        this.beAdvanceFund = value;
    }

     
    public Boolean isBeSendWithOne() {
        return beSendWithOne;
    }

     
    public void setBeSendWithOne(Boolean value) {
        this.beSendWithOne = value;
    }

     
    public Boolean isBeSinceSupervision() {
        return beSinceSupervision;
    }

     
    public void setBeSinceSupervision(Boolean value) {
        this.beSinceSupervision = value;
    }

     
    public String getBusiness() {
        return business;
    }

     
    public void setBusiness(String value) {
        this.business = value;
    }

     
    public BigDecimal getBusinessProfits() {
        return businessProfits;
    }

     
    public void setBusinessProfits(BigDecimal value) {
        this.businessProfits = value;
    }

     
    public List<CompetiorAnalysisTechnologySaveParam> getCompetiorAnalysisList() {
        if (competiorAnalysisList == null) {
            competiorAnalysisList = new ArrayList<CompetiorAnalysisTechnologySaveParam>();
        }
        return this.competiorAnalysisList;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public String getFirstBidDocument() {
        return firstBidDocument;
    }

     
    public void setFirstBidDocument(String value) {
        this.firstBidDocument = value;
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

     
    public BigDecimal getMoney() {
        return money;
    }

     
    public void setMoney(BigDecimal value) {
        this.money = value;
    }

     
    public Boolean isNeedDirectDeliverySite() {
        return needDirectDeliverySite;
    }

     
    public void setNeedDirectDeliverySite(Boolean value) {
        this.needDirectDeliverySite = value;
    }

     
    public Double getPoolOrgRatio() {
        return poolOrgRatio;
    }

     
    public void setPoolOrgRatio(Double value) {
        this.poolOrgRatio = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public String getTechnicalManager() {
        return technicalManager;
    }

     
    public void setTechnicalManager(String value) {
        this.technicalManager = value;
    }

     
    public String getTechnicalManagerName() {
        return technicalManagerName;
    }

     
    public void setTechnicalManagerName(String value) {
        this.technicalManagerName = value;
    }

     
    public String getTechnicalManagerStandardPost() {
        return technicalManagerStandardPost;
    }

     
    public void setTechnicalManagerStandardPost(String value) {
        this.technicalManagerStandardPost = value;
    }

     
    public String getTechnicalProposal() {
        return technicalProposal;
    }

     
    public void setTechnicalProposal(String value) {
        this.technicalProposal = value;
    }

     
    public XMLGregorianCalendar getTechnicalProposalTime() {
        return technicalProposalTime;
    }

     
    public void setTechnicalProposalTime(XMLGregorianCalendar value) {
        this.technicalProposalTime = value;
    }

     
    public String getTender() {
        return tender;
    }

     
    public void setTender(String value) {
        this.tender = value;
    }

     
    public String getWbsCode() {
        return wbsCode;
    }

     
    public void setWbsCode(String value) {
        this.wbsCode = value;
    }

}
