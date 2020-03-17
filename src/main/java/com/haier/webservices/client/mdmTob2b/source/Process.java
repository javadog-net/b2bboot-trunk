
package com.haier.webservices.client.mdmTob2b.source;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IN_SYS_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_MASTER_TYPE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_TABLE_NAME" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IN_FIELDS_VALUE_TABLE" type="{http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew}HAIERMDM.FIELDS_VALUE_TABLE" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "insysname",
    "inmastertype",
    "intablename",
    "infieldsvaluetable"
})
@XmlRootElement(name = "process")
public class Process {

    @XmlElementRef(name = "IN_SYS_NAME", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<String> insysname;
    @XmlElementRef(name = "IN_MASTER_TYPE", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<String> inmastertype;
    @XmlElementRef(name = "IN_TABLE_NAME", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<String> intablename;
    @XmlElementRef(name = "IN_FIELDS_VALUE_TABLE", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<HAIERMDMFIELDSVALUETABLE> infieldsvaluetable;

    /**
     * 获取insysname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINSYSNAME() {
        return insysname;
    }

    /**
     * 设置insysname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINSYSNAME(JAXBElement<String> value) {
        this.insysname = value;
    }

    /**
     * 获取inmastertype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINMASTERTYPE() {
        return inmastertype;
    }

    /**
     * 设置inmastertype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINMASTERTYPE(JAXBElement<String> value) {
        this.inmastertype = value;
    }

    /**
     * 获取intablename属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getINTABLENAME() {
        return intablename;
    }

    /**
     * 设置intablename属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setINTABLENAME(JAXBElement<String> value) {
        this.intablename = value;
    }

    /**
     * 获取infieldsvaluetable属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMFIELDSVALUETABLE }{@code >}
     *     
     */
    public JAXBElement<HAIERMDMFIELDSVALUETABLE> getINFIELDSVALUETABLE() {
        return infieldsvaluetable;
    }

    /**
     * 设置infieldsvaluetable属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link HAIERMDMFIELDSVALUETABLE }{@code >}
     *     
     */
    public void setINFIELDSVALUETABLE(JAXBElement<HAIERMDMFIELDSVALUETABLE> value) {
        this.infieldsvaluetable = value;
    }

}
