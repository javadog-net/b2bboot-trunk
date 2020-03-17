
package com.haier.webservices.client.hps.project;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectSaveParam", propOrder = {
    "contractSaveParam",
    "productList",
    "projectRFiveSaveParam",
    "projectRFourSaveParam",
    "projectROneSaveParam",
    "projectRSixSaveParam",
    "projectRThreeSaveParam",
    "projectRTwoSaveParam",
    "quoteSaveParam"
})
public class ProjectSaveParam
    extends ToString
{

    protected ContractSaveParam contractSaveParam;
    @XmlElement(nillable = true)
    protected List<StrategyProductDTO> productList;
    protected ProjectRFiveSaveParam projectRFiveSaveParam;
    protected ProjectRFourSaveParam projectRFourSaveParam;
    protected ProjectROneSaveParam projectROneSaveParam;
    protected ProjectRSixSaveParam projectRSixSaveParam;
    protected ProjectRThreeSaveParam projectRThreeSaveParam;
    protected ProjectRTwoSaveParam projectRTwoSaveParam;
    protected QuoteSaveParam quoteSaveParam;

     
    public ContractSaveParam getContractSaveParam() {
        return contractSaveParam;
    }

     
    public void setContractSaveParam(ContractSaveParam value) {
        this.contractSaveParam = value;
    }

     
    public List<StrategyProductDTO> getProductList() {
        if (productList == null) {
            productList = new ArrayList<StrategyProductDTO>();
        }
        return this.productList;
    }

     
    public ProjectRFiveSaveParam getProjectRFiveSaveParam() {
        return projectRFiveSaveParam;
    }

     
    public void setProjectRFiveSaveParam(ProjectRFiveSaveParam value) {
        this.projectRFiveSaveParam = value;
    }

     
    public ProjectRFourSaveParam getProjectRFourSaveParam() {
        return projectRFourSaveParam;
    }

     
    public void setProjectRFourSaveParam(ProjectRFourSaveParam value) {
        this.projectRFourSaveParam = value;
    }

     
    public ProjectROneSaveParam getProjectROneSaveParam() {
        return projectROneSaveParam;
    }

     
    public void setProjectROneSaveParam(ProjectROneSaveParam value) {
        this.projectROneSaveParam = value;
    }

     
    public ProjectRSixSaveParam getProjectRSixSaveParam() {
        return projectRSixSaveParam;
    }

     
    public void setProjectRSixSaveParam(ProjectRSixSaveParam value) {
        this.projectRSixSaveParam = value;
    }

     
    public ProjectRThreeSaveParam getProjectRThreeSaveParam() {
        return projectRThreeSaveParam;
    }

     
    public void setProjectRThreeSaveParam(ProjectRThreeSaveParam value) {
        this.projectRThreeSaveParam = value;
    }

     
    public ProjectRTwoSaveParam getProjectRTwoSaveParam() {
        return projectRTwoSaveParam;
    }

     
    public void setProjectRTwoSaveParam(ProjectRTwoSaveParam value) {
        this.projectRTwoSaveParam = value;
    }

     
    public QuoteSaveParam getQuoteSaveParam() {
        return quoteSaveParam;
    }

     
    public void setQuoteSaveParam(QuoteSaveParam value) {
        this.quoteSaveParam = value;
    }

}
