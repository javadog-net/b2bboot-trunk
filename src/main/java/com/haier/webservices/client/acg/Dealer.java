
package com.haier.webservices.client.acg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>dealer complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="dealer"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://acg.server.webservices.haier.com/}dataEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="acgPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="areaInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="auditDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="auditState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="auditTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="auditor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="channelName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cityId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companyNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="companyTel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="contacts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="detailAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="districtId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="electronicLicense" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="electronicUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="gmId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="gmName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idCardUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isClosed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isSelf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isStore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="kjtAccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="legalPersonIdCard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="legalPersonName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="logoUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="provinceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="taxCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="tel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="undertakeArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="zipCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dealer", propOrder = {
    "acgPassword",
    "areaInfo",
    "auditDesc",
    "auditState",
    "auditTime",
    "auditor",
    "channelName",
    "cityId",
    "companyCode",
    "companyName",
    "companyNum",
    "companyTel",
    "contacts",
    "detailAddress",
    "districtId",
    "electronicLicense",
    "electronicUrl",
    "email",
    "gmId",
    "gmName",
    "idCardUrl",
    "isClosed",
    "isSelf",
    "isStore",
    "kjtAccount",
    "legalPersonIdCard",
    "legalPersonName",
    "logoUrl",
    "mobile",
    "provinceId",
    "taxCode",
    "tel",
    "undertakeArea",
    "zipCode"
})
public class Dealer
    extends DataEntity
{

    protected String acgPassword;
    protected String areaInfo;
    protected String auditDesc;
    protected String auditState;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar auditTime;
    protected String auditor;
    protected String channelName;
    protected String cityId;
    protected String companyCode;
    protected String companyName;
    protected String companyNum;
    protected String companyTel;
    protected String contacts;
    protected String detailAddress;
    protected String districtId;
    protected String electronicLicense;
    protected String electronicUrl;
    protected String email;
    protected String gmId;
    protected String gmName;
    protected String idCardUrl;
    protected String isClosed;
    protected String isSelf;
    protected String isStore;
    protected String kjtAccount;
    protected String legalPersonIdCard;
    protected String legalPersonName;
    protected String logoUrl;
    protected String mobile;
    protected String provinceId;
    protected String taxCode;
    protected String tel;
    protected String undertakeArea;
    protected String zipCode;

    /**
     * ???acgPassword????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcgPassword() {
        return acgPassword;
    }

    /**
     * ????acgPassword????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcgPassword(String value) {
        this.acgPassword = value;
    }

    /**
     * ???areaInfo????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaInfo() {
        return areaInfo;
    }

    /**
     * ????areaInfo????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaInfo(String value) {
        this.areaInfo = value;
    }

    /**
     * ???auditDesc????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditDesc() {
        return auditDesc;
    }

    /**
     * ????auditDesc????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditDesc(String value) {
        this.auditDesc = value;
    }

    /**
     * ???auditState????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditState() {
        return auditState;
    }

    /**
     * ????auditState????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditState(String value) {
        this.auditState = value;
    }

    /**
     * ???auditTime????????
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAuditTime() {
        return auditTime;
    }

    /**
     * ????auditTime????????
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAuditTime(XMLGregorianCalendar value) {
        this.auditTime = value;
    }

    /**
     * ???auditor????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * ????auditor????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditor(String value) {
        this.auditor = value;
    }

    /**
     * ???channelName????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * ????channelName????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelName(String value) {
        this.channelName = value;
    }

    /**
     * ???cityId????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * ????cityId????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityId(String value) {
        this.cityId = value;
    }

    /**
     * ???companyCode????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * ????companyCode????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyCode(String value) {
        this.companyCode = value;
    }

    /**
     * ???companyName????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * ????companyName????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * ???companyNum????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyNum() {
        return companyNum;
    }

    /**
     * ????companyNum????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyNum(String value) {
        this.companyNum = value;
    }

    /**
     * ???companyTel????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyTel() {
        return companyTel;
    }

    /**
     * ????companyTel????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyTel(String value) {
        this.companyTel = value;
    }

    /**
     * ???contacts????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * ????contacts????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContacts(String value) {
        this.contacts = value;
    }

    /**
     * ???detailAddress????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailAddress() {
        return detailAddress;
    }

    /**
     * ????detailAddress????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailAddress(String value) {
        this.detailAddress = value;
    }

    /**
     * ???districtId????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictId() {
        return districtId;
    }

    /**
     * ????districtId????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictId(String value) {
        this.districtId = value;
    }

    /**
     * ???electronicLicense????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElectronicLicense() {
        return electronicLicense;
    }

    /**
     * ????electronicLicense????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElectronicLicense(String value) {
        this.electronicLicense = value;
    }

    /**
     * ???electronicUrl????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElectronicUrl() {
        return electronicUrl;
    }

    /**
     * ????electronicUrl????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElectronicUrl(String value) {
        this.electronicUrl = value;
    }

    /**
     * ???email????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * ????email????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * ???gmId????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGmId() {
        return gmId;
    }

    /**
     * ????gmId????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGmId(String value) {
        this.gmId = value;
    }

    /**
     * ???gmName????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGmName() {
        return gmName;
    }

    /**
     * ????gmName????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGmName(String value) {
        this.gmName = value;
    }

    /**
     * ???idCardUrl????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCardUrl() {
        return idCardUrl;
    }

    /**
     * ????idCardUrl????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCardUrl(String value) {
        this.idCardUrl = value;
    }

    /**
     * ???isClosed????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsClosed() {
        return isClosed;
    }

    /**
     * ????isClosed????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsClosed(String value) {
        this.isClosed = value;
    }

    /**
     * ???isSelf????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSelf() {
        return isSelf;
    }

    /**
     * ????isSelf????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSelf(String value) {
        this.isSelf = value;
    }

    /**
     * ???isStore????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsStore() {
        return isStore;
    }

    /**
     * ????isStore????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsStore(String value) {
        this.isStore = value;
    }

    /**
     * ???kjtAccount????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKjtAccount() {
        return kjtAccount;
    }

    /**
     * ????kjtAccount????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKjtAccount(String value) {
        this.kjtAccount = value;
    }

    /**
     * ???legalPersonIdCard????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalPersonIdCard() {
        return legalPersonIdCard;
    }

    /**
     * ????legalPersonIdCard????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalPersonIdCard(String value) {
        this.legalPersonIdCard = value;
    }

    /**
     * ???legalPersonName????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalPersonName() {
        return legalPersonName;
    }

    /**
     * ????legalPersonName????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalPersonName(String value) {
        this.legalPersonName = value;
    }

    /**
     * ???logoUrl????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogoUrl() {
        return logoUrl;
    }

    /**
     * ????logoUrl????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogoUrl(String value) {
        this.logoUrl = value;
    }

    /**
     * ???mobile????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * ????mobile????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * ???provinceId????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * ????provinceId????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinceId(String value) {
        this.provinceId = value;
    }

    /**
     * ???taxCode????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * ????taxCode????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxCode(String value) {
        this.taxCode = value;
    }

    /**
     * ???tel????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTel() {
        return tel;
    }

    /**
     * ????tel????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTel(String value) {
        this.tel = value;
    }

    /**
     * ???undertakeArea????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUndertakeArea() {
        return undertakeArea;
    }

    /**
     * ????undertakeArea????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUndertakeArea(String value) {
        this.undertakeArea = value;
    }

    /**
     * ???zipCode????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * ????zipCode????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipCode(String value) {
        this.zipCode = value;
    }

}
