
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VerifyBillSaveVo
{

    protected String addressCity;
    protected String addressCounty;
    protected String addressDetail;
    protected String addressProvince;
    protected String againApplyReason;
    protected String againFailFile;
    protected String againFailReason;
    protected XMLGregorianCalendar appealOverDate;
    protected XMLGregorianCalendar appealStartDate;
    protected XMLGregorianCalendar applyDate;
    protected String applyId;
    protected String applyer;
    protected XMLGregorianCalendar beginDate;
    protected Integer billQuantity;
    protected String cbillCode;
    protected String cbillId;
    protected String center;
    protected String centerName;
    protected String createdBy;
    protected String createdById;
    protected XMLGregorianCalendar createdDate;
    protected String custCode;
    protected String custLinkman;
    protected String custLinkmanPhone;
    protected String custName;
    protected String domain;
    protected XMLGregorianCalendar endDate;
    protected String firstFailFile;
    protected String firstFailReason;
    protected XMLGregorianCalendar freezeDate;
    protected String freezeReault;
    protected Integer gvsQuantity;
    protected String id;
    protected String installer;
    protected String installerLinkman;
    protected String installerLinkmanPhone;
    protected String invoiceCode;
    protected XMLGregorianCalendar invoiceDate;

    protected String invoiceDateStr;

    public String getInvoiceDateStr() {
        return invoiceDateStr;
    }

    public void setInvoiceDateStr(String invoiceDateStr) {
        this.invoiceDateStr = invoiceDateStr;
    }

    protected String invoiceFile;
    protected String invoiceNumber;
    protected String isFreeze;
    protected String isSend;
    protected List<VerifyBillDetailSaveParamVo> list;
    protected String marketGrade;
    protected String memo;
    protected String productGroupCode;
    protected String productGroupName;
    protected String projectAddress;
    protected String projectCode;
    protected XMLGregorianCalendar projectDate;
    protected String projectId;
    protected String projectLinkman;
    protected String projectName;
    protected String projectPhone;
    protected String qualityStr;

    public String getQualityStr() {
        return qualityStr;
    }

    public void setQualityStr(String qualityStr) {
        this.qualityStr = qualityStr;
    }

    protected Integer quantity;
    protected BigDecimal saleMoney;
    protected XMLGregorianCalendar sendDate;
    protected String sendReault;
    protected String taxId;
    protected BigDecimal taxMoney;
    protected Integer verifyNum;
    protected Integer verifyQuantity;
    protected BigDecimal verifyRate;
    protected String verifyStatus;

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCounty() {
        return addressCounty;
    }

    public void setAddressCounty(String addressCounty) {
        this.addressCounty = addressCounty;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAgainApplyReason() {
        return againApplyReason;
    }

    public void setAgainApplyReason(String againApplyReason) {
        this.againApplyReason = againApplyReason;
    }

    public String getAgainFailFile() {
        return againFailFile;
    }

    public void setAgainFailFile(String againFailFile) {
        this.againFailFile = againFailFile;
    }

    public String getAgainFailReason() {
        return againFailReason;
    }

    public void setAgainFailReason(String againFailReason) {
        this.againFailReason = againFailReason;
    }

    public XMLGregorianCalendar getAppealOverDate() {
        return appealOverDate;
    }

    public void setAppealOverDate(XMLGregorianCalendar appealOverDate) {
        this.appealOverDate = appealOverDate;
    }

    public XMLGregorianCalendar getAppealStartDate() {
        return appealStartDate;
    }

    public void setAppealStartDate(XMLGregorianCalendar appealStartDate) {
        this.appealStartDate = appealStartDate;
    }

    public XMLGregorianCalendar getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(XMLGregorianCalendar applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }

    public XMLGregorianCalendar getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(XMLGregorianCalendar beginDate) {
        this.beginDate = beginDate;
    }

    public Integer getBillQuantity() {
        return billQuantity;
    }

    public void setBillQuantity(Integer billQuantity) {
        this.billQuantity = billQuantity;
    }

    public String getCbillCode() {
        return cbillCode;
    }

    public void setCbillCode(String cbillCode) {
        this.cbillCode = cbillCode;
    }

    public String getCbillId() {
        return cbillId;
    }

    public void setCbillId(String cbillId) {
        this.cbillId = cbillId;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(XMLGregorianCalendar createdDate) {
        this.createdDate = createdDate;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustLinkman() {
        return custLinkman;
    }

    public void setCustLinkman(String custLinkman) {
        this.custLinkman = custLinkman;
    }

    public String getCustLinkmanPhone() {
        return custLinkmanPhone;
    }

    public void setCustLinkmanPhone(String custLinkmanPhone) {
        this.custLinkmanPhone = custLinkmanPhone;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(XMLGregorianCalendar endDate) {
        this.endDate = endDate;
    }

    public String getFirstFailFile() {
        return firstFailFile;
    }

    public void setFirstFailFile(String firstFailFile) {
        this.firstFailFile = firstFailFile;
    }

    public String getFirstFailReason() {
        return firstFailReason;
    }

    public void setFirstFailReason(String firstFailReason) {
        this.firstFailReason = firstFailReason;
    }

    public XMLGregorianCalendar getFreezeDate() {
        return freezeDate;
    }

    public void setFreezeDate(XMLGregorianCalendar freezeDate) {
        this.freezeDate = freezeDate;
    }

    public String getFreezeReault() {
        return freezeReault;
    }

    public void setFreezeReault(String freezeReault) {
        this.freezeReault = freezeReault;
    }

    public Integer getGvsQuantity() {
        return gvsQuantity;
    }

    public void setGvsQuantity(Integer gvsQuantity) {
        this.gvsQuantity = gvsQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstaller() {
        return installer;
    }

    public void setInstaller(String installer) {
        this.installer = installer;
    }

    public String getInstallerLinkman() {
        return installerLinkman;
    }

    public void setInstallerLinkman(String installerLinkman) {
        this.installerLinkman = installerLinkman;
    }

    public String getInstallerLinkmanPhone() {
        return installerLinkmanPhone;
    }

    public void setInstallerLinkmanPhone(String installerLinkmanPhone) {
        this.installerLinkmanPhone = installerLinkmanPhone;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public XMLGregorianCalendar getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(XMLGregorianCalendar invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceFile() {
        return invoiceFile;
    }

    public void setInvoiceFile(String invoiceFile) {
        this.invoiceFile = invoiceFile;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getIsFreeze() {
        return isFreeze;
    }

    public void setIsFreeze(String isFreeze) {
        this.isFreeze = isFreeze;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public List<VerifyBillDetailSaveParamVo> getList() {
        return list;
    }

    public void setList(List<VerifyBillDetailSaveParamVo> list) {
        this.list = list;
    }

    public String getMarketGrade() {
        return marketGrade;
    }

    public void setMarketGrade(String marketGrade) {
        this.marketGrade = marketGrade;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getProductGroupCode() {
        return productGroupCode;
    }

    public void setProductGroupCode(String productGroupCode) {
        this.productGroupCode = productGroupCode;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public XMLGregorianCalendar getProjectDate() {
        return projectDate;
    }

    public void setProjectDate(XMLGregorianCalendar projectDate) {
        this.projectDate = projectDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectLinkman() {
        return projectLinkman;
    }

    public void setProjectLinkman(String projectLinkman) {
        this.projectLinkman = projectLinkman;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPhone() {
        return projectPhone;
    }

    public void setProjectPhone(String projectPhone) {
        this.projectPhone = projectPhone;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(BigDecimal saleMoney) {
        this.saleMoney = saleMoney;
    }

    public XMLGregorianCalendar getSendDate() {
        return sendDate;
    }

    public void setSendDate(XMLGregorianCalendar sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendReault() {
        return sendReault;
    }

    public void setSendReault(String sendReault) {
        this.sendReault = sendReault;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public BigDecimal getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(BigDecimal taxMoney) {
        this.taxMoney = taxMoney;
    }

    public Integer getVerifyNum() {
        return verifyNum;
    }

    public void setVerifyNum(Integer verifyNum) {
        this.verifyNum = verifyNum;
    }

    public Integer getVerifyQuantity() {
        return verifyQuantity;
    }

    public void setVerifyQuantity(Integer verifyQuantity) {
        this.verifyQuantity = verifyQuantity;
    }

    public BigDecimal getVerifyRate() {
        return verifyRate;
    }

    public void setVerifyRate(BigDecimal verifyRate) {
        this.verifyRate = verifyRate;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
}
