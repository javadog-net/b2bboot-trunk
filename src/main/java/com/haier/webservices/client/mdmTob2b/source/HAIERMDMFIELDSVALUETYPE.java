
package com.haier.webservices.client.mdmTob2b.source;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HAIERMDM.FIELDS_VALUE_TYPE complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HAIERMDM.FIELDS_VALUE_TYPE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FIELD_NAME" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="500"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FIELD_VALUE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="500"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="FIELD_QUERY_TYPE" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="10"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HAIERMDM.FIELDS_VALUE_TYPE", propOrder = {
    "fieldname",
    "fieldvalue",
    "fieldquerytype"
})
public class HAIERMDMFIELDSVALUETYPE {

    @XmlElementRef(name = "FIELD_NAME", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fieldname;
    @XmlElementRef(name = "FIELD_VALUE", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fieldvalue;
    @XmlElementRef(name = "FIELD_QUERY_TYPE", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fieldquerytype;

    /**
     * 获取fieldname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFIELDNAME() {
        return fieldname;
    }

    /**
     * 设置fieldname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFIELDNAME(JAXBElement<String> value) {
        this.fieldname = value;
    }

    /**
     * 获取fieldvalue属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFIELDVALUE() {
        return fieldvalue;
    }

    /**
     * 设置fieldvalue属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFIELDVALUE(JAXBElement<String> value) {
        this.fieldvalue = value;
    }

    /**
     * 获取fieldquerytype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFIELDQUERYTYPE() {
        return fieldquerytype;
    }

    /**
     * 设置fieldquerytype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFIELDQUERYTYPE(JAXBElement<String> value) {
        this.fieldquerytype = value;
    }

}
