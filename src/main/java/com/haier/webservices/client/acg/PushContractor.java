
package com.haier.webservices.client.acg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>pushContractor complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="pushContractor"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="arg0" type="{http://acg.server.webservices.haier.com/}dealer" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pushContractor", propOrder = {
    "arg0"
})
public class PushContractor {

    protected Dealer arg0;

    /**
     * ???arg0????????
     * 
     * @return
     *     possible object is
     *     {@link Dealer }
     *     
     */
    public Dealer getArg0() {
        return arg0;
    }

    /**
     * ????arg0????????
     * 
     * @param value
     *     allowed object is
     *     {@link Dealer }
     *     
     */
    public void setArg0(Dealer value) {
        this.arg0 = value;
    }

}
