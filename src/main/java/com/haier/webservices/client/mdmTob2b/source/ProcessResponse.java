
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
 *         &lt;element name="OUT_RESULT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OUT_RETMSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OUT_RETCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "outresult",
    "outretmsg",
    "outretcode"
})
@XmlRootElement(name = "processResponse")
public class ProcessResponse {

    @XmlElementRef(name = "OUT_RESULT", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<String> outresult;
    @XmlElementRef(name = "OUT_RETMSG", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<String> outretmsg;
    @XmlElementRef(name = "OUT_RETCODE", namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", type = JAXBElement.class, required = false)
    protected JAXBElement<String> outretcode;

    /**
     * 获取outresult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTRESULT() {
        return outresult;
    }

    /**
     * 设置outresult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTRESULT(JAXBElement<String> value) {
        this.outresult = value;
    }

    /**
     * 获取outretmsg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTRETMSG() {
        return outretmsg;
    }

    /**
     * 设置outretmsg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTRETMSG(JAXBElement<String> value) {
        this.outretmsg = value;
    }

    /**
     * 获取outretcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTRETCODE() {
        return outretcode;
    }

    /**
     * 设置outretcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTRETCODE(JAXBElement<String> value) {
        this.outretcode = value;
    }

}
