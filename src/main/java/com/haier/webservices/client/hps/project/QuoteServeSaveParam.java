
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "quoteServeSaveParam", propOrder = {
    "cleanFee",
    "cloudStrageFee",
    "elevatorFee",
    "embedmentFee",
    "financeCost",
    "householdFee",
    "id",
    "loadFee",
    "openingFee",
    "otherFee",
    "projectCode",
    "projectId",
    "quoteId",
    "specialTransFee",
    "totalPackageFee"
})
public class QuoteServeSaveParam
    extends ToString
{

    protected BigDecimal cleanFee;
    protected BigDecimal cloudStrageFee;
    protected BigDecimal elevatorFee;
    protected BigDecimal embedmentFee;
    protected Double financeCost;
    protected BigDecimal householdFee;
    protected String id;
    protected BigDecimal loadFee;
    protected BigDecimal openingFee;
    protected BigDecimal otherFee;
    protected String projectCode;
    protected String projectId;
    protected String quoteId;
    protected BigDecimal specialTransFee;
    protected Double totalPackageFee;

     
    public BigDecimal getCleanFee() {
        return cleanFee;
    }

     
    public void setCleanFee(BigDecimal value) {
        this.cleanFee = value;
    }

     
    public BigDecimal getCloudStrageFee() {
        return cloudStrageFee;
    }

     
    public void setCloudStrageFee(BigDecimal value) {
        this.cloudStrageFee = value;
    }

     
    public BigDecimal getElevatorFee() {
        return elevatorFee;
    }

     
    public void setElevatorFee(BigDecimal value) {
        this.elevatorFee = value;
    }

     
    public BigDecimal getEmbedmentFee() {
        return embedmentFee;
    }

     
    public void setEmbedmentFee(BigDecimal value) {
        this.embedmentFee = value;
    }

     
    public Double getFinanceCost() {
        return financeCost;
    }

     
    public void setFinanceCost(Double value) {
        this.financeCost = value;
    }

     
    public BigDecimal getHouseholdFee() {
        return householdFee;
    }

     
    public void setHouseholdFee(BigDecimal value) {
        this.householdFee = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public BigDecimal getLoadFee() {
        return loadFee;
    }

     
    public void setLoadFee(BigDecimal value) {
        this.loadFee = value;
    }

     
    public BigDecimal getOpeningFee() {
        return openingFee;
    }

     
    public void setOpeningFee(BigDecimal value) {
        this.openingFee = value;
    }

     
    public BigDecimal getOtherFee() {
        return otherFee;
    }

     
    public void setOtherFee(BigDecimal value) {
        this.otherFee = value;
    }

     
    public String getProjectCode() {
        return projectCode;
    }

     
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public String getQuoteId() {
        return quoteId;
    }

     
    public void setQuoteId(String value) {
        this.quoteId = value;
    }

     
    public BigDecimal getSpecialTransFee() {
        return specialTransFee;
    }

     
    public void setSpecialTransFee(BigDecimal value) {
        this.specialTransFee = value;
    }

     
    public Double getTotalPackageFee() {
        return totalPackageFee;
    }

     
    public void setTotalPackageFee(Double value) {
        this.totalPackageFee = value;
    }

}
