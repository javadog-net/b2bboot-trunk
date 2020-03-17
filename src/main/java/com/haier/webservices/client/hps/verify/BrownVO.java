
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
@XmlType(name = "brownVO", propOrder = {
    "addressCity",
    "addressCounty",
    "addressDetail",
    "addressProvince",
    "billQuantity",
    "brownCancelVO",
    "brownCode",
    "brownDelayVO",
    "center",
    "centerName",
    "createdDate",
    "custLinkman",
    "custLinkmanPhone",
    "dealerCode",
    "dealerName",
    "domainCode",
    "expireTime",
    "gpmsSuccessStr",
    "hasContract",
    "id",
    "identify",
    "industryCategory",
    "isCheck",
    "item",
    "lastModifiedDate",
    "openSystem",
    "openSystemStatusStr",
    "openSystemTime",
    "orderQuantitySum",
    "productGroupCode",
    "productGroupName",
    "projectAddress",
    "projectCode",
    "projectDate",
    "projectId",
    "projectLinkman",
    "projectName",
    "projectPhone",
    "receivedQuantitySum",
    "type",
    "typeName"
})
public class BrownVO
    extends ToString
{

    protected String addressCity;
    protected String addressCounty;
    protected String addressDetail;
    protected String addressProvince;
    protected Integer billQuantity;
    protected BrownCancelVO brownCancelVO;
    protected String brownCode;
    protected BrownDelayVO brownDelayVO;
    protected String center;
    protected String centerName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    protected String custLinkman;
    protected String custLinkmanPhone;
    protected String dealerCode;
    protected String dealerName;
    protected String domainCode;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expireTime;
    protected String gpmsSuccessStr;
    protected String hasContract;
    protected String id;
    protected String identify;
    protected String industryCategory;
    protected Boolean isCheck;
    @XmlElement(nillable = true)
    protected List<BrownItemVO> item;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    protected String openSystem;
    protected String openSystemStatusStr;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar openSystemTime;
    protected Long orderQuantitySum;
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
    protected BigDecimal receivedQuantitySum;
    protected String type;
    protected String typeName;

     
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

     
    public Integer getBillQuantity() {
        return billQuantity;
    }

     
    public void setBillQuantity(Integer value) {
        this.billQuantity = value;
    }

     
    public BrownCancelVO getBrownCancelVO() {
        return brownCancelVO;
    }

     
    public void setBrownCancelVO(BrownCancelVO value) {
        this.brownCancelVO = value;
    }

     
    public String getBrownCode() {
        return brownCode;
    }

     
    public void setBrownCode(String value) {
        this.brownCode = value;
    }

     
    public BrownDelayVO getBrownDelayVO() {
        return brownDelayVO;
    }

     
    public void setBrownDelayVO(BrownDelayVO value) {
        this.brownDelayVO = value;
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

     
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

     
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
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

     
    public String getDealerCode() {
        return dealerCode;
    }

     
    public void setDealerCode(String value) {
        this.dealerCode = value;
    }

     
    public String getDealerName() {
        return dealerName;
    }

     
    public void setDealerName(String value) {
        this.dealerName = value;
    }

     
    public String getDomainCode() {
        return domainCode;
    }

     
    public void setDomainCode(String value) {
        this.domainCode = value;
    }

     
    public XMLGregorianCalendar getExpireTime() {
        return expireTime;
    }

     
    public void setExpireTime(XMLGregorianCalendar value) {
        this.expireTime = value;
    }

     
    public String getGpmsSuccessStr() {
        return gpmsSuccessStr;
    }

     
    public void setGpmsSuccessStr(String value) {
        this.gpmsSuccessStr = value;
    }

     
    public String getHasContract() {
        return hasContract;
    }

     
    public void setHasContract(String value) {
        this.hasContract = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getIdentify() {
        return identify;
    }

     
    public void setIdentify(String value) {
        this.identify = value;
    }

     
    public String getIndustryCategory() {
        return industryCategory;
    }

     
    public void setIndustryCategory(String value) {
        this.industryCategory = value;
    }

     
    public Boolean isIsCheck() {
        return isCheck;
    }

     
    public void setIsCheck(Boolean value) {
        this.isCheck = value;
    }

     
    public List<BrownItemVO> getItem() {
        if (item == null) {
            item = new ArrayList<BrownItemVO>();
        }
        return this.item;
    }

     
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

     
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

     
    public String getOpenSystem() {
        return openSystem;
    }

     
    public void setOpenSystem(String value) {
        this.openSystem = value;
    }

     
    public String getOpenSystemStatusStr() {
        return openSystemStatusStr;
    }

     
    public void setOpenSystemStatusStr(String value) {
        this.openSystemStatusStr = value;
    }

     
    public XMLGregorianCalendar getOpenSystemTime() {
        return openSystemTime;
    }

     
    public void setOpenSystemTime(XMLGregorianCalendar value) {
        this.openSystemTime = value;
    }

     
    public Long getOrderQuantitySum() {
        return orderQuantitySum;
    }

     
    public void setOrderQuantitySum(Long value) {
        this.orderQuantitySum = value;
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

     
    public BigDecimal getReceivedQuantitySum() {
        return receivedQuantitySum;
    }

     
    public void setReceivedQuantitySum(BigDecimal value) {
        this.receivedQuantitySum = value;
    }

     
    public String getType() {
        return type;
    }

     
    public void setType(String value) {
        this.type = value;
    }

     
    public String getTypeName() {
        return typeName;
    }

     
    public void setTypeName(String value) {
        this.typeName = value;
    }

}
