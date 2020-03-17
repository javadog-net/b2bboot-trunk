
package com.haier.mdm.yhcx;

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
 *         &lt;element name="bankBranchCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="bankName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cityStreetRoom" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="bankCategory" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "bankBranchCode",
    "bankName",
    "region",
    "cityStreetRoom",
    "bankCategory"
})
@XmlRootElement(name = "process")
public class Process {

    @XmlElement(required = true)
    protected String bankBranchCode;
    @XmlElement(required = true)
    protected String bankName;
    @XmlElement(required = true)
    protected String region;
    @XmlElement(required = true)
    protected String cityStreetRoom;
    @XmlElement(required = true)
    protected String bankCategory;

    /**
     *    bankBranchCode     ֵ
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankBranchCode() {
        return bankBranchCode;
    }

    /**
     *     bankBranchCode     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankBranchCode(String value) {
        this.bankBranchCode = value;
    }

    /**
     *    bankName     ֵ
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName() {
        return bankName;
    }

    /**
     *     bankName     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    /**
     *    region     ֵ
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     *     region     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     *    cityStreetRoom     ֵ
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityStreetRoom() {
        return cityStreetRoom;
    }

    /**
     *     cityStreetRoom     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityStreetRoom(String value) {
        this.cityStreetRoom = value;
    }

    /**
     *    bankCategory     ֵ
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCategory() {
        return bankCategory;
    }

    /**
     *     bankCategory     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCategory(String value) {
        this.bankCategory = value;
    }

}
