
package com.haier.webservices.client.acg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>baseEntity complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="baseEntity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isNewRecord" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseEntity", propOrder = {
    "id",
    "idType",
    "isNewRecord"
})
@XmlSeeAlso({
    DataEntity.class
})
public abstract class BaseEntity {

    protected String id;
    protected String idType;
    protected boolean isNewRecord;

    /**
     * ???id????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * ????id????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * ???idType????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdType() {
        return idType;
    }

    /**
     * ????idType????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdType(String value) {
        this.idType = value;
    }

    /**
     * ???isNewRecord????????
     * 
     */
    public boolean isIsNewRecord() {
        return isNewRecord;
    }

    /**
     * ????isNewRecord????????
     * 
     */
    public void setIsNewRecord(boolean value) {
        this.isNewRecord = value;
    }

}
