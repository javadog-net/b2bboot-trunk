
package com.haier.webservices.client.acg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>dataEntity complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="dataEntity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://acg.server.webservices.haier.com/}baseEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="createBy" type="{http://acg.server.webservices.haier.com/}user" minOccurs="0"/&gt;
 *         &lt;element name="createDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="delFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="remarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="updateBy" type="{http://acg.server.webservices.haier.com/}user" minOccurs="0"/&gt;
 *         &lt;element name="updateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataEntity", propOrder = {
    "createBy",
    "createDate",
    "delFlag",
    "remarks",
    "updateBy",
    "updateDate"
})
@XmlSeeAlso({
    Dealer.class,
    User.class,
    TreeEntity.class,
    Role.class,
    DataRule.class,
    Menu.class
})
public abstract class DataEntity
    extends BaseEntity
{

    protected User createBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDate;
    protected String delFlag;
    protected String remarks;
    protected User updateBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateDate;

    /**
     * ???createBy????????
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getCreateBy() {
        return createBy;
    }

    /**
     * ????createBy????????
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setCreateBy(User value) {
        this.createBy = value;
    }

    /**
     * ???createDate????????
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * ????createDate????????
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDate(XMLGregorianCalendar value) {
        this.createDate = value;
    }

    /**
     * ???delFlag????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * ????delFlag????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDelFlag(String value) {
        this.delFlag = value;
    }

    /**
     * ???remarks????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * ????remarks????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * ???updateBy????????
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUpdateBy() {
        return updateBy;
    }

    /**
     * ????updateBy????????
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUpdateBy(User value) {
        this.updateBy = value;
    }

    /**
     * ???updateDate????????
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateDate() {
        return updateDate;
    }

    /**
     * ????updateDate????????
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateDate(XMLGregorianCalendar value) {
        this.updateDate = value;
    }

}
