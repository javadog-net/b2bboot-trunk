
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
     *    outresult     ֵ
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
     *     outresult     ֵ
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
     *    outretmsg     ֵ
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
     *     outretmsg     ֵ
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
     *    outretcode     ֵ
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
     *     outretcode     ֵ
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
