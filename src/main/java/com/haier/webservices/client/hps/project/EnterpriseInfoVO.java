
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enterpriseInfoVO", propOrder = {
    "addressCity",
    "addressCounty",
    "addressDetail",
    "addressProvince",
    "addressStr",
    "beValid",
    "belongOrg",
    "bizScope",
    "checkDate",
    "city",
    "contactAddress",
    "contactEmail",
    "contactTelephone",
    "createdById",
    "createdDate",
    "creditNo",
    "domains",
    "econKind",
    "endDate",
    "entName",
    "id",
    "industryCode",
    "industryCodeStr",
    "isQuoted",
    "lastModifiedById",
    "operName",
    "orgNo",
    "province",
    "regNo",
    "registCapi",
    "startDate",
    "status",
    "termEnd",
    "termStart"
})
public class EnterpriseInfoVO
    extends ToString
{

    protected String addressCity;
    protected String addressCounty;
    protected String addressDetail;
    protected String addressProvince;
    protected String addressStr;
    protected Boolean beValid;
    protected String belongOrg;
    protected String bizScope;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar checkDate;
    protected String city;
    protected String contactAddress;
    protected String contactEmail;
    protected String contactTelephone;
    protected String createdById;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    protected String creditNo;
    protected String domains;
    protected String econKind;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    protected String entName;
    protected String id;
    protected String industryCode;
    protected String industryCodeStr;
    protected String isQuoted;
    protected String lastModifiedById;
    protected String operName;
    protected String orgNo;
    protected String province;
    protected String regNo;
    protected String registCapi;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    protected String status;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar termEnd;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar termStart;

     
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

     
    public String getAddressStr() {
        return addressStr;
    }

     
    public void setAddressStr(String value) {
        this.addressStr = value;
    }

     
    public Boolean isBeValid() {
        return beValid;
    }

     
    public void setBeValid(Boolean value) {
        this.beValid = value;
    }

     
    public String getBelongOrg() {
        return belongOrg;
    }

     
    public void setBelongOrg(String value) {
        this.belongOrg = value;
    }

     
    public String getBizScope() {
        return bizScope;
    }

     
    public void setBizScope(String value) {
        this.bizScope = value;
    }

     
    public XMLGregorianCalendar getCheckDate() {
        return checkDate;
    }

     
    public void setCheckDate(XMLGregorianCalendar value) {
        this.checkDate = value;
    }

     
    public String getCity() {
        return city;
    }

     
    public void setCity(String value) {
        this.city = value;
    }

     
    public String getContactAddress() {
        return contactAddress;
    }

     
    public void setContactAddress(String value) {
        this.contactAddress = value;
    }

     
    public String getContactEmail() {
        return contactEmail;
    }

     
    public void setContactEmail(String value) {
        this.contactEmail = value;
    }

     
    public String getContactTelephone() {
        return contactTelephone;
    }

     
    public void setContactTelephone(String value) {
        this.contactTelephone = value;
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

     
    public String getCreditNo() {
        return creditNo;
    }

     
    public void setCreditNo(String value) {
        this.creditNo = value;
    }

     
    public String getDomains() {
        return domains;
    }

     
    public void setDomains(String value) {
        this.domains = value;
    }

     
    public String getEconKind() {
        return econKind;
    }

     
    public void setEconKind(String value) {
        this.econKind = value;
    }

     
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

     
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

     
    public String getEntName() {
        return entName;
    }

     
    public void setEntName(String value) {
        this.entName = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getIndustryCode() {
        return industryCode;
    }

     
    public void setIndustryCode(String value) {
        this.industryCode = value;
    }

     
    public String getIndustryCodeStr() {
        return industryCodeStr;
    }

     
    public void setIndustryCodeStr(String value) {
        this.industryCodeStr = value;
    }

     
    public String getIsQuoted() {
        return isQuoted;
    }

     
    public void setIsQuoted(String value) {
        this.isQuoted = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public String getOperName() {
        return operName;
    }

     
    public void setOperName(String value) {
        this.operName = value;
    }

     
    public String getOrgNo() {
        return orgNo;
    }

     
    public void setOrgNo(String value) {
        this.orgNo = value;
    }

     
    public String getProvince() {
        return province;
    }

     
    public void setProvince(String value) {
        this.province = value;
    }

     
    public String getRegNo() {
        return regNo;
    }

     
    public void setRegNo(String value) {
        this.regNo = value;
    }

     
    public String getRegistCapi() {
        return registCapi;
    }

     
    public void setRegistCapi(String value) {
        this.registCapi = value;
    }

     
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

     
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

     
    public String getStatus() {
        return status;
    }

     
    public void setStatus(String value) {
        this.status = value;
    }

     
    public XMLGregorianCalendar getTermEnd() {
        return termEnd;
    }

     
    public void setTermEnd(XMLGregorianCalendar value) {
        this.termEnd = value;
    }

     
    public XMLGregorianCalendar getTermStart() {
        return termStart;
    }

     
    public void setTermStart(XMLGregorianCalendar value) {
        this.termStart = value;
    }

}
