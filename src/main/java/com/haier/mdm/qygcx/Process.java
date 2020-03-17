
package com.haier.mdm.qygcx;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>anonymous complex type   Java  ࡣ
 * 
 * <p>        ָ
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
     *    insysname     ֵ
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
     *     insysname     ֵ
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
     *    inmastertype     ֵ
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
     *     inmastertype     ֵ
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
     *    intablename     ֵ
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
     *     intablename     ֵ
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
     *    infieldsvaluetable     ֵ
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
     *     infieldsvaluetable     ֵ
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
