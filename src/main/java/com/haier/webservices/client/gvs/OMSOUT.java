
package com.haier.webservices.client.gvs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>OMS_OUT complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="OMS_OUT"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KKBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OMS_OUT", propOrder = {
    "kunnr",
    "kkber"
})
public class OMSOUT {

    @XmlElement(name = "KUNNR", required = true)
    protected String kunnr;
    @XmlElement(name = "KKBER", required = true)
    protected String kkber;

    /**
     * 获取kunnr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKUNNR() {
        return kunnr;
    }

    /**
     * 设置kunnr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKUNNR(String value) {
        this.kunnr = value;
    }

    /**
     * 获取kkber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKKBER() {
        return kkber;
    }

    /**
     * 设置kkber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKKBER(String value) {
        this.kkber = value;
    }

}
