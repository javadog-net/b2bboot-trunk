
package com.haier.webservices.client.hps.file;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>uploadResponse complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="uploadResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://file.manage.hps.com/}resInfoFile" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadResponse", propOrder = {
    "_return"
})
public class UploadResponse {

    @XmlElement(name = "return")
    protected ResInfoFile _return;

    /**
     * ???return????????
     * 
     * @return
     *     possible object is
     *     {@link ResInfoFile }
     *     
     */
    public ResInfoFile getReturn() {
        return _return;
    }

    /**
     * ????return????????
     * 
     * @param value
     *     allowed object is
     *     {@link ResInfoFile }
     *     
     */
    public void setReturn(ResInfoFile value) {
        this._return = value;
    }

}
