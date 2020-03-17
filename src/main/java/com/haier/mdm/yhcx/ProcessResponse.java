
package com.haier.mdm.yhcx;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element name="bankTable"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="bankItem" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="bankCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="bankBranchCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="bankName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="streetRoom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="cityStreetRoom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="bankCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="extendKey1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="extendKey2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="extendKey3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                             &lt;element name="extendKey4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="retCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="retMst" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "bankTable",
    "retCode",
    "retMst"
})
@XmlRootElement(name = "processResponse")
public class ProcessResponse {

    @XmlElement(required = true)
    protected BankTable bankTable;
    @XmlElement(required = true)
    protected String retCode;
    @XmlElement(required = true)
    protected String retMst;

    /**
     *    bankTable     ֵ
     * 
     * @return
     *     possible object is
     *     {@link BankTable }
     *     
     */
    public BankTable getBankTable() {
        return bankTable;
    }

    /**
     *     bankTable     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link BankTable }
     *     
     */
    public void setBankTable(BankTable value) {
        this.bankTable = value;
    }

    /**
     *    retCode     ֵ
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetCode() {
        return retCode;
    }

    /**
     *     retCode     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetCode(String value) {
        this.retCode = value;
    }

    /**
     *    retMst     ֵ
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRetMst() {
        return retMst;
    }

    /**
     *     retMst     ֵ
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRetMst(String value) {
        this.retMst = value;
    }


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
     *         &lt;element name="bankItem" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="bankCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="bankBranchCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="bankName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="streetRoom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="cityStreetRoom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="bankCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="extendKey1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="extendKey2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="extendKey3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                   &lt;element name="extendKey4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
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
        "bankItem"
    })
    public static class BankTable {

        protected List<BankItem> bankItem;

        /**
         * Gets the value of the bankItem property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the bankItem property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBankItem().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BankItem }
         * 
         * 
         */
        public List<BankItem> getBankItem() {
            if (bankItem == null) {
                bankItem = new ArrayList<BankItem>();
            }
            return this.bankItem;
        }


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
         *         &lt;element name="bankCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="bankBranchCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="bankName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="streetRoom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="cityStreetRoom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="bankCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="extendKey1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="extendKey2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="extendKey3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
         *         &lt;element name="extendKey4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
            "bankCountry",
            "bankBranchCode",
            "bankName",
            "region",
            "streetRoom",
            "cityStreetRoom",
            "bankCategory",
            "extendKey1",
            "extendKey2",
            "extendKey3",
            "extendKey4"
        })
        public static class BankItem {

            protected String bankCountry;
            protected String bankBranchCode;
            protected String bankName;
            protected String region;
            protected String streetRoom;
            protected String cityStreetRoom;
            protected String bankCategory;
            protected String extendKey1;
            protected String extendKey2;
            protected String extendKey3;
            protected String extendKey4;

            /**
             *    bankCountry     ֵ
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBankCountry() {
                return bankCountry;
            }

            /**
             *     bankCountry     ֵ
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBankCountry(String value) {
                this.bankCountry = value;
            }

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
             *    streetRoom     ֵ
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStreetRoom() {
                return streetRoom;
            }

            /**
             *     streetRoom     ֵ
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStreetRoom(String value) {
                this.streetRoom = value;
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

            /**
             *    extendKey1     ֵ
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExtendKey1() {
                return extendKey1;
            }

            /**
             *     extendKey1     ֵ
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExtendKey1(String value) {
                this.extendKey1 = value;
            }

            /**
             *    extendKey2     ֵ
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExtendKey2() {
                return extendKey2;
            }

            /**
             *     extendKey2     ֵ
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExtendKey2(String value) {
                this.extendKey2 = value;
            }

            /**
             *    extendKey3     ֵ
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExtendKey3() {
                return extendKey3;
            }

            /**
             *     extendKey3     ֵ
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExtendKey3(String value) {
                this.extendKey3 = value;
            }

            /**
             *    extendKey4     ֵ
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getExtendKey4() {
                return extendKey4;
            }

            /**
             *     extendKey4     ֵ
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setExtendKey4(String value) {
                this.extendKey4 = value;
            }

        }

    }

}
