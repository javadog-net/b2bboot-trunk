
package com.haier.mdm.khzj;

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
 *         &lt;element name="OUT_CUSTOMERCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RETCODE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RETMSG" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "outcustomercode",
    "retcode",
    "retmsg"
})
@XmlRootElement(name = "processResponse")
public class ProcessResponse {

    @XmlElementRef(name = "OUT_CUSTOMERCODE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> outcustomercode;
    @XmlElementRef(name = "RETCODE", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> retcode;
    @XmlElementRef(name = "RETMSG", namespace = "http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", type = JAXBElement.class, required = false)
    protected JAXBElement<String> retmsg;

    /**
     *    outcustomercode     ֵ
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOUTCUSTOMERCODE() {
        return outcustomercode;
    }

    /**
     *     outcustomercode     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOUTCUSTOMERCODE(JAXBElement<String> value) {
        this.outcustomercode = value;
    }

    /**
     *    retcode     ֵ
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRETCODE() {
        return retcode;
    }

    /**
     *     retcode     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRETCODE(JAXBElement<String> value) {
        this.retcode = value;
    }

    /**
     *    retmsg     ֵ
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRETMSG() {
        return retmsg;
    }

    /**
     *     retmsg     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRETMSG(JAXBElement<String> value) {
        this.retmsg = value;
    }

}
