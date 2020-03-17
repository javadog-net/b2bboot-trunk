
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
 *         &lt;element name="OUT_PAGE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUT_RESULT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUT_RETCODE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUT_ALL_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUT_PAGE_CON" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUT_ALL_COUNT" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUT_RETMSG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OUT_BATCH_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "outpage",
    "outresult",
    "outretcode",
    "outallnum",
    "outpagecon",
    "outallcount",
    "outretmsg",
    "outbatchid"
})
@XmlRootElement(name = "processResponse")
public class ProcessResponse {

    @XmlElement(name = "OUT_PAGE", required = true)
    protected String outpage;
    @XmlElement(name = "OUT_RESULT", required = true)
    protected String outresult;
    @XmlElement(name = "OUT_RETCODE", required = true)
    protected String outretcode;
    @XmlElement(name = "OUT_ALL_NUM", required = true)
    protected String outallnum;
    @XmlElement(name = "OUT_PAGE_CON", required = true)
    protected String outpagecon;
    @XmlElement(name = "OUT_ALL_COUNT", required = true)
    protected String outallcount;
    @XmlElement(name = "OUT_RETMSG", required = true)
    protected String outretmsg;
    @XmlElement(name = "OUT_BATCH_ID", required = true)
    protected String outbatchid;

    /**
     * 获取outpage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTPAGE() {
        return outpage;
    }

    /**
     * 设置outpage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTPAGE(String value) {
        this.outpage = value;
    }

    /**
     * 获取outresult属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTRESULT() {
        return outresult;
    }

    /**
     * 设置outresult属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTRESULT(String value) {
        this.outresult = value;
    }

    /**
     * 获取outretcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTRETCODE() {
        return outretcode;
    }

    /**
     * 设置outretcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTRETCODE(String value) {
        this.outretcode = value;
    }

    /**
     * 获取outallnum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTALLNUM() {
        return outallnum;
    }

    /**
     * 设置outallnum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTALLNUM(String value) {
        this.outallnum = value;
    }

    /**
     * 获取outpagecon属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTPAGECON() {
        return outpagecon;
    }

    /**
     * 设置outpagecon属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTPAGECON(String value) {
        this.outpagecon = value;
    }

    /**
     * 获取outallcount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTALLCOUNT() {
        return outallcount;
    }

    /**
     * 设置outallcount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTALLCOUNT(String value) {
        this.outallcount = value;
    }

    /**
     * 获取outretmsg属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTRETMSG() {
        return outretmsg;
    }

    /**
     * 设置outretmsg属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTRETMSG(String value) {
        this.outretmsg = value;
    }

    /**
     * 获取outbatchid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOUTBATCHID() {
        return outbatchid;
    }

    /**
     * 设置outbatchid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOUTBATCHID(String value) {
        this.outbatchid = value;
    }

}
