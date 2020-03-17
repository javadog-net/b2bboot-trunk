
package com.haier.webservices.client.mdmTob2b.partner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IN_SYS_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IN_MASTER_TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IN_TABLE_NAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IN_STARTDATE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IN_ENDDATE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IN_PAGE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IN_BATCH_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "insysname",
    "inmastertype",
    "intablename",
    "instartdate",
    "inenddate",
    "inpage",
    "inbatchid"
})
@XmlRootElement(name = "process")
public class Process {

    @XmlElement(name = "IN_SYS_NAME", required = true)
    protected String insysname;
    @XmlElement(name = "IN_MASTER_TYPE", required = true)
    protected String inmastertype;
    @XmlElement(name = "IN_TABLE_NAME", required = true)
    protected String intablename;
    @XmlElement(name = "IN_STARTDATE", required = true)
    protected String instartdate;
    @XmlElement(name = "IN_ENDDATE", required = true)
    protected String inenddate;
    @XmlElement(name = "IN_PAGE", required = true)
    protected String inpage;
    @XmlElement(name = "IN_BATCH_ID", required = true)
    protected String inbatchid;

    /**
     * 获取insysname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSYSNAME() {
        return insysname;
    }

    /**
     * 设置insysname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSYSNAME(String value) {
        this.insysname = value;
    }

    /**
     * 获取inmastertype属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINMASTERTYPE() {
        return inmastertype;
    }

    /**
     * 设置inmastertype属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINMASTERTYPE(String value) {
        this.inmastertype = value;
    }

    /**
     * 获取intablename属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINTABLENAME() {
        return intablename;
    }

    /**
     * 设置intablename属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINTABLENAME(String value) {
        this.intablename = value;
    }

    /**
     * 获取instartdate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINSTARTDATE() {
        return instartdate;
    }

    /**
     * 设置instartdate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINSTARTDATE(String value) {
        this.instartdate = value;
    }

    /**
     * 获取inenddate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINENDDATE() {
        return inenddate;
    }

    /**
     * 设置inenddate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINENDDATE(String value) {
        this.inenddate = value;
    }

    /**
     * 获取inpage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINPAGE() {
        return inpage;
    }

    /**
     * 设置inpage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINPAGE(String value) {
        this.inpage = value;
    }

    /**
     * 获取inbatchid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINBATCHID() {
        return inbatchid;
    }

    /**
     * 设置inbatchid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINBATCHID(String value) {
        this.inbatchid = value;
    }

}
