
package com.haier.webservices.client.acg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>pushContractorResponse complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="pushContractorResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://acg.server.webservices.haier.com/}ajaxJson" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pushContractorResponse", propOrder = {
    "_return"
})
public class PushContractorResponse {

    @XmlElement(name = "return")
    protected AjaxJson _return;

    /**
     * ???return????????
     * 
     * @return
     *     possible object is
     *     {@link AjaxJson }
     *     
     */
    public AjaxJson getReturn() {
        return _return;
    }

    /**
     * ????return????????
     * 
     * @param value
     *     allowed object is
     *     {@link AjaxJson }
     *     
     */
    public void setReturn(AjaxJson value) {
        this._return = value;
    }

}
