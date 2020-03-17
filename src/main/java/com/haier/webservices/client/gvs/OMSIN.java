
package com.haier.webservices.client.gvs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>OMS_IN complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="OMS_IN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="NAME1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KKBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="KLIMK" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="KNKLI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SKFOR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="SSOBL" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="ABSBT" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="CTLPC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UEDAT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OEIKW" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="CMWAE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OLIKW" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="OFAKW" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="CMWAE1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OMS_IN", propOrder = {
    "kunnr",
    "name1",
    "kkber",
    "klimk",
    "knkli",
    "skfor",
    "ssobl",
    "absbt",
    "ctlpc",
    "uedat",
    "oeikw",
    "cmwae",
    "olikw",
    "ofakw",
    "cmwae1"
})
public class OMSIN {

    @XmlElement(name = "KUNNR", required = true)
    protected String kunnr;
    @XmlElement(name = "NAME1", required = true)
    protected String name1;
    @XmlElement(name = "KKBER", required = true)
    protected String kkber;
    @XmlElement(name = "KLIMK")
    protected BigDecimal klimk;
    @XmlElement(name = "KNKLI")
    protected String knkli;
    @XmlElement(name = "SKFOR")
    protected BigDecimal skfor;
    @XmlElement(name = "SSOBL")
    protected BigDecimal ssobl;
    @XmlElement(name = "ABSBT")
    protected BigDecimal absbt;
    @XmlElement(name = "CTLPC")
    protected String ctlpc;
    @XmlElement(name = "UEDAT")
    protected String uedat;
    @XmlElement(name = "OEIKW")
    protected BigDecimal oeikw;
    @XmlElement(name = "CMWAE")
    protected String cmwae;
    @XmlElement(name = "OLIKW")
    protected BigDecimal olikw;
    @XmlElement(name = "OFAKW")
    protected BigDecimal ofakw;
    @XmlElement(name = "CMWAE1")
    protected String cmwae1;

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
     * 获取name1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNAME1() {
        return name1;
    }

    /**
     * 设置name1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNAME1(String value) {
        this.name1 = value;
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

    /**
     * 获取klimk属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKLIMK() {
        return klimk;
    }

    /**
     * 设置klimk属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKLIMK(BigDecimal value) {
        this.klimk = value;
    }

    /**
     * 获取knkli属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKNKLI() {
        return knkli;
    }

    /**
     * 设置knkli属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKNKLI(String value) {
        this.knkli = value;
    }

    /**
     * 获取skfor属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSKFOR() {
        return skfor;
    }

    /**
     * 设置skfor属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSKFOR(BigDecimal value) {
        this.skfor = value;
    }

    /**
     * 获取ssobl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSSOBL() {
        return ssobl;
    }

    /**
     * 设置ssobl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSSOBL(BigDecimal value) {
        this.ssobl = value;
    }

    /**
     * 获取absbt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getABSBT() {
        return absbt;
    }

    /**
     * 设置absbt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setABSBT(BigDecimal value) {
        this.absbt = value;
    }

    /**
     * 获取ctlpc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCTLPC() {
        return ctlpc;
    }

    /**
     * 设置ctlpc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCTLPC(String value) {
        this.ctlpc = value;
    }

    /**
     * 获取uedat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUEDAT() {
        return uedat;
    }

    /**
     * 设置uedat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUEDAT(String value) {
        this.uedat = value;
    }

    /**
     * 获取oeikw属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOEIKW() {
        return oeikw;
    }

    /**
     * 设置oeikw属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOEIKW(BigDecimal value) {
        this.oeikw = value;
    }

    /**
     * 获取cmwae属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCMWAE() {
        return cmwae;
    }

    /**
     * 设置cmwae属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCMWAE(String value) {
        this.cmwae = value;
    }

    /**
     * 获取olikw属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOLIKW() {
        return olikw;
    }

    /**
     * 设置olikw属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOLIKW(BigDecimal value) {
        this.olikw = value;
    }

    /**
     * 获取ofakw属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOFAKW() {
        return ofakw;
    }

    /**
     * 设置ofakw属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOFAKW(BigDecimal value) {
        this.ofakw = value;
    }

    /**
     * 获取cmwae1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCMWAE1() {
        return cmwae1;
    }

    /**
     * 设置cmwae1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCMWAE1(String value) {
        this.cmwae1 = value;
    }

}
