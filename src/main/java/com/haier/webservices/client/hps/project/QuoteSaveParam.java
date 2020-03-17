
package com.haier.webservices.client.hps.project;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "quoteSaveParam", propOrder = {
    "beContracted",
    "beMaterialPriceSingle",
    "contractId",
    "domainModel",
    "domainType",
    "funnelStage",
    "id",
    "materialTaxRate",
    "needApproval",
    "projectCode",
    "projectId",
    "quoteMaterialList",
    "quoteProductList",
    "quoteServeList",
    "quoteStage",
    "quoteType",
    "servicePriceType",
    "versionIndex"
})
public class QuoteSaveParam
    extends ToString
{

    protected Boolean beContracted;
    protected Boolean beMaterialPriceSingle;
    protected String contractId;
    protected String domainModel;
    protected String domainType;
    protected String funnelStage;
    protected String id;
    protected Double materialTaxRate;
    protected Boolean needApproval;
    protected String projectCode;
    protected String projectId;
    @XmlElement(nillable = true)
    protected List<QuoteMaterialSaveParam> quoteMaterialList;
    @XmlElement(nillable = true)
    protected List<QuoteProductSaveParam> quoteProductList;
    @XmlElement(nillable = true)
    protected List<QuoteServeSaveParam> quoteServeList;
    protected String quoteStage;
    protected String quoteType;
    protected String servicePriceType;
    protected Integer versionIndex;

     
    public Boolean isBeContracted() {
        return beContracted;
    }

     
    public void setBeContracted(Boolean value) {
        this.beContracted = value;
    }

     
    public Boolean isBeMaterialPriceSingle() {
        return beMaterialPriceSingle;
    }

     
    public void setBeMaterialPriceSingle(Boolean value) {
        this.beMaterialPriceSingle = value;
    }

     
    public String getContractId() {
        return contractId;
    }

     
    public void setContractId(String value) {
        this.contractId = value;
    }

     
    public String getDomainModel() {
        return domainModel;
    }

     
    public void setDomainModel(String value) {
        this.domainModel = value;
    }

     
    public String getDomainType() {
        return domainType;
    }

     
    public void setDomainType(String value) {
        this.domainType = value;
    }

     
    public String getFunnelStage() {
        return funnelStage;
    }

     
    public void setFunnelStage(String value) {
        this.funnelStage = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public Double getMaterialTaxRate() {
        return materialTaxRate;
    }

     
    public void setMaterialTaxRate(Double value) {
        this.materialTaxRate = value;
    }

     
    public Boolean isNeedApproval() {
        return needApproval;
    }

     
    public void setNeedApproval(Boolean value) {
        this.needApproval = value;
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

     
    public List<QuoteMaterialSaveParam> getQuoteMaterialList() {
        if (quoteMaterialList == null) {
            quoteMaterialList = new ArrayList<QuoteMaterialSaveParam>();
        }
        return this.quoteMaterialList;
    }

     
    public List<QuoteProductSaveParam> getQuoteProductList() {
        if (quoteProductList == null) {
            quoteProductList = new ArrayList<QuoteProductSaveParam>();
        }
        return this.quoteProductList;
    }

     
    public List<QuoteServeSaveParam> getQuoteServeList() {
        if (quoteServeList == null) {
            quoteServeList = new ArrayList<QuoteServeSaveParam>();
        }
        return this.quoteServeList;
    }

     
    public String getQuoteStage() {
        return quoteStage;
    }

     
    public void setQuoteStage(String value) {
        this.quoteStage = value;
    }

     
    public String getQuoteType() {
        return quoteType;
    }

     
    public void setQuoteType(String value) {
        this.quoteType = value;
    }

     
    public String getServicePriceType() {
        return servicePriceType;
    }

     
    public void setServicePriceType(String value) {
        this.servicePriceType = value;
    }

     
    public Integer getVersionIndex() {
        return versionIndex;
    }

     
    public void setVersionIndex(Integer value) {
        this.versionIndex = value;
    }

}
