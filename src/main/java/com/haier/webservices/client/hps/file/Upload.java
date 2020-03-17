
package com.haier.webservices.client.hps.file;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>upload complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="upload"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="arg0" type="{http://file.manage.hps.com/}fileWrapper" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "upload", propOrder = {
    "arg0"
})
public class Upload {

    protected FileWrapper arg0;

    /**
     * ???arg0????????
     * 
     * @return
     *     possible object is
     *     {@link FileWrapper }
     *     
     */
    public FileWrapper getArg0() {
        return arg0;
    }

    /**
     * ????arg0????????
     * 
     * @param value
     *     allowed object is
     *     {@link FileWrapper }
     *     
     */
    public void setArg0(FileWrapper value) {
        this.arg0 = value;
    }

}
