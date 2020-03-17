
package com.haier.webservices.client.hps.file;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>resInfoFile complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="resInfoFile"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fileManage" type="{http://file.manage.hps.com/}fileManageVO" minOccurs="0"/&gt;
 *         &lt;element name="msg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resInfoFile", propOrder = {
    "code",
    "fileManage",
    "msg"
})
public class ResInfoFile {

    protected String code;
    protected FileManageVO fileManage;
    protected String msg;

    /**
     * ???code????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * ????code????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * ???fileManage????????
     * 
     * @return
     *     possible object is
     *     {@link FileManageVO }
     *     
     */
    public FileManageVO getFileManage() {
        return fileManage;
    }

    /**
     * ????fileManage????????
     * 
     * @param value
     *     allowed object is
     *     {@link FileManageVO }
     *     
     */
    public void setFileManage(FileManageVO value) {
        this.fileManage = value;
    }

    /**
     * ???msg????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsg() {
        return msg;
    }

    /**
     * ????msg????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsg(String value) {
        this.msg = value;
    }

}
