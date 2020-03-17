
package com.haier.webservices.client.hps.trans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>transCodeResponse complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="transCodeResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://hps.server.webservices.haier.com/}ajaxJson" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "transCodeResponse", propOrder = {
    "_return"
})
public class TransCodeResponse {

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
