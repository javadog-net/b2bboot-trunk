
package com.haier.webservices.client.acg;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>user complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="user"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://acg.server.webservices.haier.com/}dataEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="company" type="{http://acg.server.webservices.haier.com/}office" minOccurs="0"/&gt;
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="loginFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loginIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loginName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="newPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="no" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="office" type="{http://acg.server.webservices.haier.com/}office" minOccurs="0"/&gt;
 *         &lt;element name="oldLoginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="oldLoginIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oldLoginName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="photo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="qrCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="role" type="{http://acg.server.webservices.haier.com/}role" minOccurs="0"/&gt;
 *         &lt;element name="roleIdList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="roleList" type="{http://acg.server.webservices.haier.com/}role" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="sign" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
    "company",
    "email",
    "loginDate",
    "loginFlag",
    "loginIp",
    "loginName",
    "mobile",
    "name",
    "newPassword",
    "no",
    "office",
    "oldLoginDate",
    "oldLoginIp",
    "oldLoginName",
    "password",
    "phone",
    "photo",
    "qrCode",
    "role",
    "roleIdList",
    "roleList",
    "sign"
})
public class User
    extends DataEntity
{

    protected Office company;
    protected String email;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar loginDate;
    protected String loginFlag;
    protected String loginIp;
    protected String loginName;
    protected String mobile;
    protected String name;
    protected String newPassword;
    protected String no;
    protected Office office;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar oldLoginDate;
    protected String oldLoginIp;
    protected String oldLoginName;
    protected String password;
    protected String phone;
    protected String photo;
    protected String qrCode;
    protected Role role;
    @XmlElement(nillable = true)
    protected List<String> roleIdList;
    @XmlElement(nillable = true)
    protected List<Role> roleList;
    protected String sign;

    /**
     * ???company????????
     * 
     * @return
     *     possible object is
     *     {@link Office }
     *     
     */
    public Office getCompany() {
        return company;
    }

    /**
     * ????company????????
     * 
     * @param value
     *     allowed object is
     *     {@link Office }
     *     
     */
    public void setCompany(Office value) {
        this.company = value;
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
     * ???loginDate????????
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLoginDate() {
        return loginDate;
    }

    /**
     * ????loginDate????????
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLoginDate(XMLGregorianCalendar value) {
        this.loginDate = value;
    }

    /**
     * ???loginFlag????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginFlag() {
        return loginFlag;
    }

    /**
     * ????loginFlag????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginFlag(String value) {
        this.loginFlag = value;
    }

    /**
     * ???loginIp????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * ????loginIp????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginIp(String value) {
        this.loginIp = value;
    }

    /**
     * ???loginName????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * ????loginName????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginName(String value) {
        this.loginName = value;
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
     * ???name????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * ????name????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * ???newPassword????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * ????newPassword????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPassword(String value) {
        this.newPassword = value;
    }

    /**
     * ???no????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNo() {
        return no;
    }

    /**
     * ????no????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNo(String value) {
        this.no = value;
    }

    /**
     * ???office????????
     * 
     * @return
     *     possible object is
     *     {@link Office }
     *     
     */
    public Office getOffice() {
        return office;
    }

    /**
     * ????office????????
     * 
     * @param value
     *     allowed object is
     *     {@link Office }
     *     
     */
    public void setOffice(Office value) {
        this.office = value;
    }

    /**
     * ???oldLoginDate????????
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOldLoginDate() {
        return oldLoginDate;
    }

    /**
     * ????oldLoginDate????????
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOldLoginDate(XMLGregorianCalendar value) {
        this.oldLoginDate = value;
    }

    /**
     * ???oldLoginIp????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldLoginIp() {
        return oldLoginIp;
    }

    /**
     * ????oldLoginIp????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldLoginIp(String value) {
        this.oldLoginIp = value;
    }

    /**
     * ???oldLoginName????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldLoginName() {
        return oldLoginName;
    }

    /**
     * ????oldLoginName????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldLoginName(String value) {
        this.oldLoginName = value;
    }

    /**
     * ???password????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * ????password????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * ???phone????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * ????phone????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * ???photo????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * ????photo????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoto(String value) {
        this.photo = value;
    }

    /**
     * ???qrCode????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQrCode() {
        return qrCode;
    }

    /**
     * ????qrCode????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQrCode(String value) {
        this.qrCode = value;
    }

    /**
     * ???role????????
     * 
     * @return
     *     possible object is
     *     {@link Role }
     *     
     */
    public Role getRole() {
        return role;
    }

    /**
     * ????role????????
     * 
     * @param value
     *     allowed object is
     *     {@link Role }
     *     
     */
    public void setRole(Role value) {
        this.role = value;
    }

    /**
     * Gets the value of the roleIdList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleIdList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleIdList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRoleIdList() {
        if (roleIdList == null) {
            roleIdList = new ArrayList<String>();
        }
        return this.roleIdList;
    }

    /**
     * Gets the value of the roleList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Role }
     * 
     * 
     */
    public List<Role> getRoleList() {
        if (roleList == null) {
            roleList = new ArrayList<Role>();
        }
        return this.roleList;
    }

    /**
     * ???sign????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSign() {
        return sign;
    }

    /**
     * ????sign????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSign(String value) {
        this.sign = value;
    }

}
