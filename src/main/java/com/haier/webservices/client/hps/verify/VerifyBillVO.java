
package com.haier.webservices.client.hps.verify;

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
@XmlType(name = "verifyBillVO", propOrder = {
    "addressCity",
    "addressCounty",
    "addressDetail",
    "addressProvince",
    "againApplyReason",
    "againFailFile",
    "againFailFileName",
    "againFailReason",
    "appeal",
    "appealOverDate",
    "appealStartDate",
    "applyDate",
    "applyId",
    "applyer",
    "approvalStatus",
    "beginDate",
    "billQuantity",
    "cbillCode",
    "cbillId",
    "center",
    "centerName",
    "contractApprovalStatusName",
    "createdBy",
    "createdById",
    "createdDate",
    "custCode",
    "custLinkman",
    "custLinkmanPhone",
    "custName",
    "domain",
    "endDate",
    "firstFailFile",
    "firstFailFileName",
    "firstFailReason",
    "firstGvsQuantity",
    "firstQuantity",
    "firstRate",
    "firstVerifyQuantity",
    "freezeDate",
    "freezeReault",
    "gvsQuantity",
    "id",
    "installer",
    "installerLinkman",
    "installerLinkmanPhone",
    "invoiceCode",
    "invoiceDate",
    "invoiceFile",
    "invoiceFileName",
    "invoiceNumber",
    "isFreeze",
    "isSend",
    "list",
    "marketGrade",
    "memo",
    "productGroupCode",
    "productGroupName",
    "projectAddress",
    "projectCode",
    "projectDate",
    "projectId",
    "projectLinkman",
    "projectName",
    "projectPhone",
    "provinceCityCountyDetail",
    "quality",
    "quantity",
    "saleMoney",
    "sendDate",
    "sendReault",
    "taxId",
    "taxMoney",
    "tempText1",
    "tempText2",
    "verifyNum",
    "verifyQuantity",
    "verifyRate",
    "verifyStatus",
    "verifyStatusDesc",
    "yanshoujiezhiDate"
})
public class VerifyBillVO
    extends ToString
{

    protected String addressCity;
    protected String addressCounty;
    protected String addressDetail;
    protected String addressProvince;
    protected String againApplyReason;
    protected String againFailFile;
    protected String againFailFileName;
    protected String againFailReason;
    protected String appeal;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar appealOverDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar appealStartDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar applyDate;
    protected String applyId;
    protected String applyer;
    protected String approvalStatus;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginDate;
    protected Integer billQuantity;
    protected String cbillCode;
    protected String cbillId;
    protected String center;
    protected String centerName;
    protected String contractApprovalStatusName;
    protected String createdBy;
    protected String createdById;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    protected String custCode;
    protected String custLinkman;
    protected String custLinkmanPhone;
    protected String custName;
    protected String domain;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected String firstFailFile;
    protected String firstFailFileName;
    protected String firstFailReason;
    protected String firstGvsQuantity;
    protected String firstQuantity;
    protected String firstRate;
    protected String firstVerifyQuantity;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar freezeDate;
    protected String freezeReault;
    protected Integer gvsQuantity;
    protected String id;
    protected String installer;
    protected String installerLinkman;
    protected String installerLinkmanPhone;
    protected String invoiceCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invoiceDate;
    protected String invoiceFile;
    protected String invoiceFileName;
    protected String invoiceNumber;
    protected String isFreeze;
    protected String isSend;
    @XmlElement(nillable = true)
    protected List<VerifyBillDetailVO> list;
    protected String marketGrade;
    protected String memo;
    protected String productGroupCode;
    protected String productGroupName;
    protected String projectAddress;
    protected String projectCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar projectDate;
    protected String projectId;
    protected String projectLinkman;
    protected String projectName;
    protected String projectPhone;
    protected String provinceCityCountyDetail;
    protected String quality;
    protected Integer quantity;
    protected BigDecimal saleMoney;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sendDate;
    protected String sendReault;
    protected String taxId;
    protected BigDecimal taxMoney;
    protected String tempText1;
    protected String tempText2;
    protected Integer verifyNum;
    protected Integer verifyQuantity;
    protected BigDecimal verifyRate;
    protected String verifyStatus;
    protected String verifyStatusDesc;
    protected String yanshoujiezhiDate;

     
    public String getAddressCity() {
        return addressCity;
    }

     
    public void setAddressCity(String value) {
        this.addressCity = value;
    }

     
    public String getAddressCounty() {
        return addressCounty;
    }

     
    public void setAddressCounty(String value) {
        this.addressCounty = value;
    }

     
    public String getAddressDetail() {
        return addressDetail;
    }

     
    public void setAddressDetail(String value) {
        this.addressDetail = value;
    }

     
    public String getAddressProvince() {
        return addressProvince;
    }

     
    public void setAddressProvince(String value) {
        this.addressProvince = value;
    }

     
    public String getAgainApplyReason() {
        return againApplyReason;
    }

     
    public void setAgainApplyReason(String value) {
        this.againApplyReason = value;
    }

     
    public String getAgainFailFile() {
        return againFailFile;
    }

     
    public void setAgainFailFile(String value) {
        this.againFailFile = value;
    }

     
    public String getAgainFailFileName() {
        return againFailFileName;
    }

     
    public void setAgainFailFileName(String value) {
        this.againFailFileName = value;
    }

     
    public String getAgainFailReason() {
        return againFailReason;
    }

     
    public void setAgainFailReason(String value) {
        this.againFailReason = value;
    }

     
    public String getAppeal() {
        return appeal;
    }

     
    public void setAppeal(String value) {
        this.appeal = value;
    }

     
    public XMLGregorianCalendar getAppealOverDate() {
        return appealOverDate;
    }

     
    public void setAppealOverDate(XMLGregorianCalendar value) {
        this.appealOverDate = value;
    }

     
    public XMLGregorianCalendar getAppealStartDate() {
        return appealStartDate;
    }

     
    public void setAppealStartDate(XMLGregorianCalendar value) {
        this.appealStartDate = value;
    }

     
    public XMLGregorianCalendar getApplyDate() {
        return applyDate;
    }

     
    public void setApplyDate(XMLGregorianCalendar value) {
        this.applyDate = value;
    }

     
    public String getApplyId() {
        return applyId;
    }

     
    public void setApplyId(String value) {
        this.applyId = value;
    }

     
    public String getApplyer() {
        return applyer;
    }

     
    public void setApplyer(String value) {
        this.applyer = value;
    }

     
    public String getApprovalStatus() {
        return approvalStatus;
    }

     
    public void setApprovalStatus(String value) {
        this.approvalStatus = value;
    }

     
    public XMLGregorianCalendar getBeginDate() {
        return beginDate;
    }

     
    public void setBeginDate(XMLGregorianCalendar value) {
        this.beginDate = value;
    }

     
    public Integer getBillQuantity() {
        return billQuantity;
    }

     
    public void setBillQuantity(Integer value) {
        this.billQuantity = value;
    }

     
    public String getCbillCode() {
        return cbillCode;
    }

     
    public void setCbillCode(String value) {
        this.cbillCode = value;
    }

     
    public String getCbillId() {
        return cbillId;
    }

     
    public void setCbillId(String value) {
        this.cbillId = value;
    }

     
    public String getCenter() {
        return center;
    }

     
    public void setCenter(String value) {
        this.center = value;
    }

     
    public String getCenterName() {
        return centerName;
    }

     
    public void setCenterName(String value) {
        this.centerName = value;
    }

     
    public String getContractApprovalStatusName() {
        return contractApprovalStatusName;
    }

     
    public void setContractApprovalStatusName(String value) {
        this.contractApprovalStatusName = value;
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

     
    public String getCustCode() {
        return custCode;
    }

     
    public void setCustCode(String value) {
        this.custCode = value;
    }

     
    public String getCustLinkman() {
        return custLinkman;
    }

     
    public void setCustLinkman(String value) {
        this.custLinkman = value;
    }

     
    public String getCustLinkmanPhone() {
        return custLinkmanPhone;
    }

     
    public void setCustLinkmanPhone(String value) {
        this.custLinkmanPhone = value;
    }

     
    public String getCustName() {
        return custName;
    }

     
    public void setCustName(String value) {
        this.custName = value;
    }

     
    public String getDomain() {
        return domain;
    }

     
    public void setDomain(String value) {
        this.domain = value;
    }

     
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

     
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

     
    public String getFirstFailFile() {
        return firstFailFile;
    }

     
    public void setFirstFailFile(String value) {
        this.firstFailFile = value;
    }

     
    public String getFirstFailFileName() {
        return firstFailFileName;
    }

     
    public void setFirstFailFileName(String value) {
        this.firstFailFileName = value;
    }

     
    public String getFirstFailReason() {
        return firstFailReason;
    }

     
    public void setFirstFailReason(String value) {
        this.firstFailReason = value;
    }

     
    public String getFirstGvsQuantity() {
        return firstGvsQuantity;
    }

     
    public void setFirstGvsQuantity(String value) {
        this.firstGvsQuantity = value;
    }

     
    public String getFirstQuantity() {
        return firstQuantity;
    }

     
    public void setFirstQuantity(String value) {
        this.firstQuantity = value;
    }

     
    public String getFirstRate() {
        return firstRate;
    }

     
    public void setFirstRate(String value) {
        this.firstRate = value;
    }

     
    public String getFirstVerifyQuantity() {
        return firstVerifyQuantity;
    }

     
    public void setFirstVerifyQuantity(String value) {
        this.firstVerifyQuantity = value;
    }

     
    public XMLGregorianCalendar getFreezeDate() {
        return freezeDate;
    }

     
    public void setFreezeDate(XMLGregorianCalendar value) {
        this.freezeDate = value;
    }

     
    public String getFreezeReault() {
        return freezeReault;
    }

     
    public void setFreezeReault(String value) {
        this.freezeReault = value;
    }

     
    public Integer getGvsQuantity() {
        return gvsQuantity;
    }

     
    public void setGvsQuantity(Integer value) {
        this.gvsQuantity = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getInstaller() {
        return installer;
    }

     
    public void setInstaller(String value) {
        this.installer = value;
    }

     
    public String getInstallerLinkman() {
        return installerLinkman;
    }

     
    public void setInstallerLinkman(String value) {
        this.installerLinkman = value;
    }

     
    public String getInstallerLinkmanPhone() {
        return installerLinkmanPhone;
    }

     
    public void setInstallerLinkmanPhone(String value) {
        this.installerLinkmanPhone = value;
    }

     
    public String getInvoiceCode() {
        return invoiceCode;
    }

     
    public void setInvoiceCode(String value) {
        this.invoiceCode = value;
    }

     
    public XMLGregorianCalendar getInvoiceDate() {
        return invoiceDate;
    }

     
    public void setInvoiceDate(XMLGregorianCalendar value) {
        this.invoiceDate = value;
    }

     
    public String getInvoiceFile() {
        return invoiceFile;
    }

     
    public void setInvoiceFile(String value) {
        this.invoiceFile = value;
    }

     
    public String getInvoiceFileName() {
        return invoiceFileName;
    }

     
    public void setInvoiceFileName(String value) {
        this.invoiceFileName = value;
    }

     
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

     
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

     
    public String getIsFreeze() {
        return isFreeze;
    }

     
    public void setIsFreeze(String value) {
        this.isFreeze = value;
    }

     
    public String getIsSend() {
        return isSend;
    }

     
    public void setIsSend(String value) {
        this.isSend = value;
    }

     
    public List<VerifyBillDetailVO> getList() {
        if (list == null) {
            list = new ArrayList<VerifyBillDetailVO>();
        }
        return this.list;
    }

     
    public String getMarketGrade() {
        return marketGrade;
    }

     
    public void setMarketGrade(String value) {
        this.marketGrade = value;
    }

     
    public String getMemo() {
        return memo;
    }

     
    public void setMemo(String value) {
        this.memo = value;
    }

     
    public String getProductGroupCode() {
        return productGroupCode;
    }

     
    public void setProductGroupCode(String value) {
        this.productGroupCode = value;
    }

     
    public String getProductGroupName() {
        return productGroupName;
    }

     
    public void setProductGroupName(String value) {
        this.productGroupName = value;
    }

     
    public String getProjectAddress() {
        return projectAddress;
    }

     
    public void setProjectAddress(String value) {
        this.projectAddress = value;
    }

     
    public String getProjectCode() {
        return projectCode;
    }

     
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

     
    public XMLGregorianCalendar getProjectDate() {
        return projectDate;
    }

     
    public void setProjectDate(XMLGregorianCalendar value) {
        this.projectDate = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public String getProjectLinkman() {
        return projectLinkman;
    }

     
    public void setProjectLinkman(String value) {
        this.projectLinkman = value;
    }

     
    public String getProjectName() {
        return projectName;
    }

     
    public void setProjectName(String value) {
        this.projectName = value;
    }

     
    public String getProjectPhone() {
        return projectPhone;
    }

     
    public void setProjectPhone(String value) {
        this.projectPhone = value;
    }

     
    public String getProvinceCityCountyDetail() {
        return provinceCityCountyDetail;
    }

     
    public void setProvinceCityCountyDetail(String value) {
        this.provinceCityCountyDetail = value;
    }

     
    public String getQuality() {
        return quality;
    }

     
    public void setQuality(String value) {
        this.quality = value;
    }

     
    public Integer getQuantity() {
        return quantity;
    }

     
    public void setQuantity(Integer value) {
        this.quantity = value;
    }

     
    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

     
    public void setSaleMoney(BigDecimal value) {
        this.saleMoney = value;
    }

     
    public XMLGregorianCalendar getSendDate() {
        return sendDate;
    }

     
    public void setSendDate(XMLGregorianCalendar value) {
        this.sendDate = value;
    }

     
    public String getSendReault() {
        return sendReault;
    }

     
    public void setSendReault(String value) {
        this.sendReault = value;
    }

     
    public String getTaxId() {
        return taxId;
    }

     
    public void setTaxId(String value) {
        this.taxId = value;
    }

     
    public BigDecimal getTaxMoney() {
        return taxMoney;
    }

     
    public void setTaxMoney(BigDecimal value) {
        this.taxMoney = value;
    }

     
    public String getTempText1() {
        return tempText1;
    }

     
    public void setTempText1(String value) {
        this.tempText1 = value;
    }

     
    public String getTempText2() {
        return tempText2;
    }

     
    public void setTempText2(String value) {
        this.tempText2 = value;
    }

     
    public Integer getVerifyNum() {
        return verifyNum;
    }

     
    public void setVerifyNum(Integer value) {
        this.verifyNum = value;
    }

     
    public Integer getVerifyQuantity() {
        return verifyQuantity;
    }

     
    public void setVerifyQuantity(Integer value) {
        this.verifyQuantity = value;
    }

     
    public BigDecimal getVerifyRate() {
        return verifyRate;
    }

     
    public void setVerifyRate(BigDecimal value) {
        this.verifyRate = value;
    }

     
    public String getVerifyStatus() {
        return verifyStatus;
    }

     
    public void setVerifyStatus(String value) {
        this.verifyStatus = value;
    }

     
    public String getVerifyStatusDesc() {
        return verifyStatusDesc;
    }

     
    public void setVerifyStatusDesc(String value) {
        this.verifyStatusDesc = value;
    }

     
    public String getYanshoujiezhiDate() {
        return yanshoujiezhiDate;
    }

     
    public void setYanshoujiezhiDate(String value) {
        this.yanshoujiezhiDate = value;
    }

}
